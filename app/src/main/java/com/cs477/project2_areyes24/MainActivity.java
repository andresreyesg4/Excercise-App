package com.cs477.project2_areyes24;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    Button workout;
    Button edit;
    DatabaseOpenHelper workout_db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // initiate the buttons on the screen
        workout = findViewById(R.id.workout);
        edit = findViewById(R.id.edit_mode);

        // initiate the database
        workout_db = new DatabaseOpenHelper(this);
    }

    // function to be called when the button workout is clicked.
    public void onWorkout(View view){
        // initialize a new activity
        Intent intent = new Intent(this, Workout.class);

        //Start the activity
        startActivity(intent);

    }

    // function to be called when the button edit is clicked
    public void onEdit(View view){
        // initialize a new activity
        Intent intent = new Intent(this, EditWorkout.class);

        //Start the activity
        startActivity(intent);

    }

}