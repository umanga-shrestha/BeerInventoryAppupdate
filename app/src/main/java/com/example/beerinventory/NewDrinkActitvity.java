package com.example.beerinventory;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class NewDrinkActitvity extends AppCompatActivity {
    private static final String TAG = NewDrinkActitvity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_drink);
    }

    public void addThisDrink(View view) {

        final EditText editText_1 = findViewById(R.id.editText_1);
        final EditText editText_10 = findViewById(R.id.editText_10);
        final EditText editText_2 = findViewById(R.id.editText_2);
        final EditText editText_4 = findViewById(R.id.editText_4);
        final EditText editText_7 = findViewById(R.id.editText_7);
        final EditText editText_9 = findViewById(R.id.editText_9);
        final EditText editText_12 = findViewById(R.id.editText_12);
        final EditText editText_11 = findViewById(R.id.editText_11);
        boolean check = (editText_1.getText().toString().isEmpty()
                | editText_2.getText().toString().isEmpty()
                | editText_4.getText().toString().isEmpty()
                | editText_7.getText().toString().isEmpty()
                | editText_9.getText().toString().isEmpty()
                | editText_11.getText().toString().isEmpty()
                | editText_12.getText().toString().isEmpty()
                | editText_10.getText().toString().isEmpty());

    }
}
