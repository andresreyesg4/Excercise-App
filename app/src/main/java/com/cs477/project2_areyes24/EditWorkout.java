package com.cs477.project2_areyes24;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

public class EditWorkout extends AppCompatActivity {
    Button add;
    ListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_workout);
        listView = findViewById(R.id.current_exercises);

        // set the button click listener for new exercises
        add = findViewById(R.id.add);
        add.setOnClickListener(this::onAdd);
        Intent intent = new Intent(this, UpdateExercise.class);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                startActivity(intent);
            }
        });


    }

    // function called when the add button is called.
    public void onAdd(View view){
        // start the new activity
        Intent intent = new Intent(this, CreateExercise.class);
        startActivity(intent);
    }
}