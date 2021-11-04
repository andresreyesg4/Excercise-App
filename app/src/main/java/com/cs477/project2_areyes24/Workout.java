package com.cs477.project2_areyes24;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;

public class Workout extends AppCompatActivity {
    private ListView listView;
    private DatabaseOpenHelper helper;
    private SQLiteDatabase database;
    private Cursor cursor;
    private SimpleCursorAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workout);

        // initiate the list
        listView = findViewById(R.id.exercise_list);

        // initialize the database open helper.
        helper = new DatabaseOpenHelper(this);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                cursor = helper.readItems();
                cursor.moveToPosition(i);
                String name = cursor.getString(1);
                int reps = cursor.getInt(2);
                int sets = cursor.getInt(3);
                int weight = cursor.getInt(4);
                String notes = cursor.getString(5);
                String workout_information = name + "\n";
                if(reps > 0){
                    workout_information += "Reps: " + reps + " ";
                }
                if(sets > 0){
                    workout_information += "Sets: " + sets + " ";
                }
                if(weight > 0){
                    workout_information += "Weight: " + weight + " ";
                }
                if(!notes.isEmpty()){
                    workout_information += "Notes: " + notes + " ";
                }
                Snackbar snackbar = Snackbar.make((findViewById(R.id.snackbar_text).getRootView()),
                        workout_information, BaseTransientBottomBar.LENGTH_SHORT);
                snackbar.setAnchorView(R.id.snackbar_text);
                snackbar.show();
            }
        });
        // act upon a long click
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                alertView("Single Workout Deletion", i);
                return true;
            }
        });

    }

    // called when a long click happens to delete a workout
    public void alertView(String message, int index){
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setTitle(message).setIcon(R.drawable.ic_launcher_background)
                .setMessage("Are you sure you want to do this?")
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                })
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        cursor = helper.readItems();
                        cursor.moveToPosition(index);
                        int id = cursor.getInt(0);
                        database.delete(helper.TABLE_NAME, "_id=?",
                                new String[]{Integer.toString(id)});
                        cursor = helper.readItems();
                        adapter.swapCursor(cursor);
                    }
                }).show();
    }

    // load the database in the background.
    private final class LoadDB extends AsyncTask<String, Void, Cursor> {
        @Override protected void onPostExecute(Cursor data){
            adapter = new SimpleCursorAdapter(getApplicationContext(), android.R.layout.simple_list_item_1,
                    data,
                    new String[]{"name", "sets", "reps", "weight", "notes"},
                    new int[] {android.R.id.text1}, 0);
            cursor = data;
            listView.setAdapter(adapter);
        }
        @Override
        protected Cursor doInBackground(String... strings) {
            database = helper.getWritableDatabase();
            String[] columns = new String[]{"_id", helper.COL_NAME, helper.COL_REPS,
                    helper.COL_SETS, helper.COL_WEIGHT, helper.COL_NOTES};
            return database.query(helper.TABLE_NAME, columns, null,
                    null, null, null, null);
        }
    }

    // called on Resume to load the database
    public void onResume(){
        super.onResume();
        LoadDB load = new LoadDB();
        load.execute();
    }

    // Called on pause.
    public void onPause(){
        super.onPause();
        if(database != null) database.close();
        cursor.close();
    }
}