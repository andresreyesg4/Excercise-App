package com.cs477.project2_areyes24;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class CreateExercise extends AppCompatActivity {
    private EditText name_edit, reps_edit, sets_edit, weight_edit, notes_edit;
    private String name, reps, sets, weight, notes;
    private Button add_to_workout, cancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_exercise);
        // initiate the buttons
        add_to_workout = findViewById(R.id.add_workout);
        add_to_workout.setOnClickListener(this::onAdd);
        cancel = findViewById(R.id.cancel_button);
        cancel.setOnClickListener(this::onCancel);

        // initiate the eit text
        name_edit = findViewById(R.id.name);
        reps_edit = findViewById(R.id.reps);
        sets_edit = findViewById(R.id.sets);
        weight_edit = findViewById(R.id.weight);
        notes_edit = findViewById(R.id.notes);
    }

    public void onAdd(View view){
        name = name_edit.getText().toString();
        if(!name.isEmpty()) {
            reps = reps_edit.getText().toString();
            sets = sets_edit.getText().toString();
            weight = weight_edit.getText().toString();
            notes = notes_edit.getText().toString();
            try {
                Intent intent = new Intent();
                intent.putExtra("name", name);
                if (!reps.isEmpty()) {
                    intent.putExtra("reps", Integer.parseInt(reps));
                }
                if (!sets.isEmpty()) {
                    intent.putExtra("sets", Integer.parseInt(sets));
                }
                if (!weight.isEmpty()) {
                    intent.putExtra("weight", Integer.parseInt(weight));
                }
                if (!notes.isEmpty()) {
                    intent.putExtra("notes", notes);
                }
                setResult(EditWorkout.ADDED, intent);
                finish();
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }else{
            Toast toast = Toast.makeText(getApplicationContext(), "You forgot to add a name!", Toast.LENGTH_SHORT);
            toast.show();
        }
    }

    public void onCancel(View view){
        finish();
    }
}