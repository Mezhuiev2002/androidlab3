package com.student.lab3.ui.result;

import static com.student.lab3.constants.ConstantsKey.KEY_PASSWORD_BUNDLE;
import static com.student.lab3.constants.PreferencesKey.KEY_PREFERENCES_NAME;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import com.google.android.material.snackbar.Snackbar;
import com.student.lab3.R;
import com.student.lab3.model.SavesModel;
import com.student.lab3.tools.PreferencesManager;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class ResultFragment extends Fragment {

    private ConstraintLayout root;
    private TextView textPasswordResult;

    private SharedPreferences preferences;
    private SharedPreferences.Editor preferencesEditor;

    public static ResultFragment newInstance() {
        return new ResultFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_result, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        findViews(view);
        initVariables();
        setClicks();
        retrieveBundle();
    }

    private void findViews(View view) {
        root = view.findViewById(R.id.root_result);
        textPasswordResult = view.findViewById(R.id.text_password_result);
    }

    private void initVariables() {
        preferences = requireActivity().getSharedPreferences(KEY_PREFERENCES_NAME, Context.MODE_PRIVATE);
        preferencesEditor = requireActivity().getSharedPreferences(KEY_PREFERENCES_NAME, Context.MODE_PRIVATE).edit();
    }

    private void setClicks() {

    }

    private void retrieveBundle() {
        Bundle bundle = getArguments();
        if (bundle == null) {
            getParentFragmentManager().popBackStack();
            return;
        }

        String password = bundle.getString(KEY_PASSWORD_BUNDLE);
        //Смотрим пустой ли пароль или нет
        if (password.isEmpty()) {
            showErrorSnack();
        }

        showResult(password);
        savePassword(password);

    }

    //Сохраняем пароль в Shared Preferences
    private void savePassword(String password) {
        //берем текущую дату
        DateFormat df = new SimpleDateFormat("yyyy.MM.dd '|' HH:mm:ss");
        String date = df.format(Calendar.getInstance().getTime());
        // Заполняем модель данными
        SavesModel model = new SavesModel();
        model.setPassword(password);
        model.setDate(date);

        PreferencesManager preferencesManager = new PreferencesManager();
        //Загружаем БД
        ArrayList<SavesModel> list = preferencesManager.loadList(preferences);
        //Добавляем наш элемент в БД
        list.add(model);
        //Сохраняем список
        preferencesManager.saveList(list, preferencesEditor);
    }

    private void showResult(String password) {
        textPasswordResult.setText(password);
    }

    private void showErrorSnack() {
        Snackbar snackbar = Snackbar.make(requireContext(), root, "Password is empty :/", Snackbar.LENGTH_LONG);
        snackbar.show();
    }
}