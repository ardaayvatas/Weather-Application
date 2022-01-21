package com.ardaayvatas.weatherapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class WindActivity extends AppCompatActivity {

    TextView speed;
    TextView deg;
    String speedStr;
    String degStr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.windactivity);
        speed = findViewById(R.id.speed);
        deg = findViewById(R.id.deg);
        Intent intent = getIntent();
        speedStr = intent.getStringExtra("speed");
        degStr = intent.getStringExtra("deg");
        speed.setText(speed.getText().toString()+speedStr);
        deg.setText(deg.getText().toString()+degStr);
    }
    public void back(View view)
    {
        Intent intent = new Intent(WindActivity.this,MainActivity.class);
        startActivity(intent);
    }
}
