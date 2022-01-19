package com.student.lab3;

import static com.student.lab3.constants.PreferencesKey.KEY_PREFERENCES_NAME;
import static com.student.lab3.constants.PreferencesKey.KEY_PREFS_SAVES_MODEL;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.student.lab3.adapter.SavesAdapter;
import com.student.lab3.model.SavesModel;
import com.student.lab3.tools.PreferencesManager;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class SavesActivity extends AppCompatActivity {

    private SavesAdapter adapter;
    private RecyclerView recyclerView;
    private SharedPreferences preferences;
    private CardView cardNoSaves;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_base);
        findViews();
        initVariables();
        loadSaves();
    }

    //Ищем всьюхи в xml
    private void findViews() {
        recyclerView = findViewById(R.id.recycler_saves);
        cardNoSaves = findViewById(R.id.card_no_saves);
    }

    // Переопределяем переменные
    private void initVariables() {
        preferences = getSharedPreferences(KEY_PREFERENCES_NAME, MODE_PRIVATE);
        adapter = new SavesAdapter();
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(adapter);
    }

    //Загружаем пароли
    private void loadSaves() {
        PreferencesManager preferencesManager = new PreferencesManager();

        ArrayList<SavesModel> arrayItems = preferencesManager.loadList(preferences);
        //Если список не пустой то показываем результат, в ином случае показываем ошибку
        if (arrayItems != null && !arrayItems.isEmpty()) adapter.setList(arrayItems);
        else showError();
    }

    //Показываем ошибку
    private void showError() {
        cardNoSaves.setVisibility(View.VISIBLE);
        recyclerView.setVisibility(View.GONE);
    }

    //На нажатие назад убиваем активность
    @Override
    public void onBackPressed() {
        finish();
    }
}