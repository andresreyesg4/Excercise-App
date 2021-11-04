package com.cs477.project2_areyes24;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

public class EditWorkout extends AppCompatActivity {
    Button add;
    ListView listView;
    DatabaseOpenHelper helper;
    static final int UPDATED = 200;
    static final int ADDED = 201;
    private SimpleCursorAdapter adapter;
    SQLiteDatabase database;
    Cursor cursor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_workout);
        listView = findViewById(R.id.current_exercises);
        helper = new DatabaseOpenHelper(this);
        cursor = helper.readItems();

        // set the button click listener for new exercises
        add = findViewById(R.id.add);
        add.setOnClickListener(this::onAdd);
        Intent intent = new Intent(this, UpdateExercise.class);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                cursor.moveToPosition(i);
                int id = cursor.getInt(0);
                intent.putExtra("_id", id);
                intent.putExtra("index", i);
                startActivityForResult(intent, UPDATED);
            }
        });
    }

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
            String[] columns = new String[]{"_id", helper.COL_NAME, helper.COL_REPS, helper.COL_SETS, helper.COL_WEIGHT, helper.COL_NOTES};
            return database.query(helper.TABLE_NAME, columns, null, null, null, null, null);
        }
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Bundle extras = null;
        if(requestCode == UPDATED){
            if(data != null) {
                extras = data.getExtras();
            }
            if(extras != null){
                int reps = extras.getInt("reps");
                int sets = extras.getInt("sets");
                int weight = extras.getInt("weight");
                String notes = extras.getString("notes");
                String name = extras.getString("name");
                int id = extras.getInt("_id");
                ContentValues contentValues = new ContentValues();
                contentValues.put(helper.COL_REPS, reps);
                contentValues.put(helper.COL_SETS, sets);
                contentValues.put(helper.COL_WEIGHT, weight);
                contentValues.put(helper.COL_NOTES, notes);
                database.update(helper.TABLE_NAME, contentValues, "_id=? AND " + helper.COL_NAME + "=?", new String[]{Integer.toString(id), name});
                cursor = helper.readItems();
                adapter.swapCursor(cursor);
            }
        }else if(requestCode == ADDED){
            if(data != null) {
                extras = data.getExtras();
            }
            if(extras != null){
                String name = extras.getString("name");
                String notes = extras.getString("notes");
                int reps = extras.getInt("reps");
                int sets = extras.getInt("sets");
                int weight = extras.getInt("weight");
                ContentValues contentValues = new ContentValues();
                contentValues.put(helper.COL_REPS, reps);
                contentValues.put(helper.COL_SETS, sets);
                contentValues.put(helper.COL_WEIGHT, weight);
                contentValues.put(helper.COL_NOTES, notes);
                contentValues.put(helper.COL_NAME, name);
                // check if the exercise exists already
                String sql = "SELECT " + helper.COL_NAME + " FROM " + helper.TABLE_NAME
                        + " WHERE " + helper.COL_NAME + "=?";
                Cursor temp = database.rawQuery(sql, new String[]{name});
                if(temp.getCount() > 0){
                    // we cannot add the workout.
                    Toast toast = Toast.makeText(getApplicationContext(), "Workout already exists.", Toast.LENGTH_SHORT);
                    toast.show();
                }else   {
                    database.insert(helper.TABLE_NAME, null, contentValues);
                    Toast toast = Toast.makeText(getApplicationContext(), "New workout added!", Toast.LENGTH_SHORT);
                    toast.show();
                    cursor = helper.readItems();
                    adapter.swapCursor(cursor);
                }
            }
        }
    }

    public void onResume(){
        super.onResume();
        EditWorkout.LoadDB load = new EditWorkout.LoadDB();
        load.execute();
    }

    // function called when the add button is called.
    public void onAdd(View view){
        // start the new activity
        Intent intent = new Intent(this, CreateExercise.class);
        startActivityForResult(intent, ADDED);
    }
}