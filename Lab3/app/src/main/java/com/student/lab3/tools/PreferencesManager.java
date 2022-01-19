package com.student.lab3.tools;

import static com.student.lab3.constants.PreferencesKey.KEY_PREFS_SAVES_MODEL;

import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.student.lab3.model.SavesModel;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class PreferencesManager {

    //Сохраняем список в sharedPreferences
    public void saveList(ArrayList<SavesModel> list, SharedPreferences.Editor editor) {
        Gson gson = new Gson();
        //сериализуем данные в Json строку
        String json = gson.toJson(list);
        //запихиваем строку в префсы
        editor.putString(KEY_PREFS_SAVES_MODEL, json).apply();
    }

    //Загружаем список из sharedPreferences
    public ArrayList<SavesModel> loadList(SharedPreferences preferences) {
        ArrayList<SavesModel> arrayItems;
        //берем строку из префсов
        String serializedObject = preferences.getString(KEY_PREFS_SAVES_MODEL, null);
        if (serializedObject != null) {
            Gson gson = new Gson();
            Type type = new TypeToken<ArrayList<SavesModel>>() {
            }.getType();
            //десериализуем строку в список моделей
            arrayItems = gson.fromJson(serializedObject, type);
            //возвращаем список с паролями
            return arrayItems;
        }
        //возвращаем пустой список если префсы ничего не вернули
        return new ArrayList<SavesModel>();
    }
}
