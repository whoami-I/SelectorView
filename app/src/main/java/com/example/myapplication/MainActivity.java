package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Matrix;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final View viewById = findViewById(R.id.tv);
        viewById.setOnClickListener(new View.OnClickListener() {
            boolean isSelected = false;

            @Override
            public void onClick(View v) {
                isSelected = !isSelected;
                if (isSelected) {
                    viewById.setSelected(true);
                } else {
                    viewById.setSelected(false);
                }
            }
        });
    }
}
