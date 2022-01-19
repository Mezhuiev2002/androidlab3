package com.student.lab3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.student.lab3.ui.input.InputFragment;

public class MainActivity extends AppCompatActivity {

    private FloatingActionButton fabSaves;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (savedInstanceState == null) openFragment();
        findViews();
        initVariables();
        setClicks();
        openFragment();
    }

    private void findViews() {
        fabSaves = findViewById(R.id.fab_open_saves);
    }

    private void initVariables() {
    }

    private void setClicks() {
        //На клик открываем активность с сохранениями
        fabSaves.setOnClickListener(v -> {
            startActivity(new Intent(this, SavesActivity.class));
        });
    }

    //Открываем Первый фрагмент
    private void openFragment() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, InputFragment.newInstance())
                .commitNow();
    }
}