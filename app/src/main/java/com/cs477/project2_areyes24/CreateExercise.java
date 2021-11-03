package com.cs477.project2_areyes24;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class CreateExercise extends AppCompatActivity {
    private EditText name_edit;
    private EditText reps_edit;
    private EditText sets_edit;
    private EditText weight_edit;
    private EditText notes;
    Button add_to_workout;
    Button cancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_exercise);
        // initiate the buttons
        add_to_workout = findViewById(R.id.add_workout);
        add_to_workout.setOnClickListener(this::onAdd);
        cancel = findViewById(R.id.cancel_button);
        cancel.setOnClickListener(this::onCancel);
    }

    public void onAdd(View view){

    }

    public void onCancel(View view){
        finish();
    }
}