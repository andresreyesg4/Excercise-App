package com.cs477.project2_areyes24;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class UpdateExercise extends AppCompatActivity {
    private Button cancel;
    private TextView name;
    private EditText reps, sets, weight, notes;
    private Button update;
    DatabaseOpenHelper helper;
    Cursor cursor;
    private int id;
    private String workout;
    private String n;
    private int reps_, sets_, weight_;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_exercise);
        // initiate the update button
        update = findViewById(R.id.update_workout);
        update.setOnClickListener(this::onUpdate);
        // initiate the cancel button
        cancel = findViewById(R.id.cancel_button);
        cancel.setOnClickListener(this::onCancel);
        // initiate the text view
        name = findViewById(R.id.name);
        // initiate the edit text's
        reps = findViewById(R.id.reps);
        sets = findViewById(R.id.sets);
        weight = findViewById(R.id.weight);
        notes = findViewById(R.id.notes);
        helper = new DatabaseOpenHelper(this);
        cursor = helper.readItems();
        Bundle extras = getIntent().getExtras();
        id = 0 ;
        int index = 0;
        if(extras != null){
            id = extras.getInt("_id");
            index = extras.getInt("index");
        }
        cursor.moveToPosition(index);
        name.setText(cursor.getString(1));
        workout = cursor.getString(1);
        reps_ = cursor.getInt(2);
        sets_ = cursor.getInt(3);
        weight_ = cursor.getInt(4);
        n = cursor.getString(5);
        reps.setHint(Integer.toString(reps_));
        sets.setHint(Integer.toString(sets_));
        weight.setHint(Integer.toString(weight_));
        notes.setHint(n);
    }

    // implement an update button
    public void onUpdate(View view){
        String repetitions = reps.getText().toString();
        String s = sets.getText().toString();
        String w = weight.getText().toString();
        String n_ = notes.getText().toString();
        int counter = 0;
        try {
            if(!repetitions.isEmpty()){
                reps_ = Integer.parseInt(repetitions);
            }else{
                counter++;
            }
            if(!s.isEmpty()) {
                sets_ = Integer.parseInt(s);
            }else{
                counter++;
            }
            if(!w.isEmpty()) {
                weight_ = Integer.parseInt(w);
            }else{
                counter++;
            }
            if(!n_.isEmpty()){
                n = n_;
            }else{
                counter++;
            }
        }catch (NumberFormatException e){
            e.printStackTrace();
        }
        if(counter < 4) {
            Intent intent = new Intent();
            intent.putExtra("reps", reps_);
            intent.putExtra("sets", sets_);
            intent.putExtra("weight", weight_);
            intent.putExtra("notes", n);
            intent.putExtra("name", workout);
            intent.putExtra("_id", id);
            setResult(EditWorkout.UPDATED, intent);
            finish();
        }else{
            Toast toast = Toast.makeText(getApplicationContext(), "No changes made! If you " +
                    "don't need any changes, click Cancel", Toast.LENGTH_LONG);
            toast.show();
        }
    }

    // implement a return to the previous activity without saving content.
    public void onCancel(View view){
        finish();
    }
}