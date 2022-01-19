package com.student.lab3.ui.input;

import static com.student.lab3.constants.ConstantsKey.KEY_PASSWORD_BUNDLE;

import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.student.lab3.R;
import com.student.lab3.ui.result.ResultFragment;

public class InputFragment extends Fragment {

    private ConstraintLayout root;
    private TextInputEditText editPassword;
    private MaterialButton buttonShowResult;
    private RadioGroup rg;
    private RadioButton r1;
    private RadioButton r2;

    public static InputFragment newInstance() {
        return new InputFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_input, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        findViews(view);
        initVariables();
        setClicks();

        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (i) {
                    case R.id.r1:
                        editPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                        break;
                    case R.id.r2:
                        editPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());

                        break;
                }

            }
        });
    }

    private void findViews(View view) {
        root = view.findViewById(R.id.root_input);
        rg = view.findViewById(R.id.rg);
        editPassword = view.findViewById(R.id.input_edit_password);
        buttonShowResult = view.findViewById(R.id.material_button_show_result);
    }

    private void initVariables() {

    }

    private void setClicks() {
        buttonShowResult.setOnClickListener(v -> {
            resultPassword();
        });
    }

    private void resultPassword() {

        if (editPassword.getText() == null) {
            showInputError("editPassword.getText() == null");
            return;
        }

        String password = editPassword.getText().toString();
        if (password.isEmpty()) {
            showInputError("Enter password first");
            return;
        }

        openFragment(ResultFragment.newInstance(), password);
    }

    // метод который открывает следующий фрагмент с результатом нашего ввода данных
    private void openFragment(Fragment fragment, String password) {
        Bundle bundle = new Bundle();
        bundle.putString(KEY_PASSWORD_BUNDLE, password); // добавляем пароль в bundle
        getParentFragmentManager().beginTransaction()
                .setCustomAnimations(R.anim.slide_in, R.anim.slide_out, R.anim.slide_in, R.anim.slide_out) //Простая анимация переходов между фрагментами
                .add(R.id.container, fragment.getClass(), bundle) // добавляем фрагмент из параметров в FragmentManager и заодно передаем Bundle в который мы перед этим положили строку с паролем
                .addToBackStack(fragment.getClass().getSimpleName()) // добавляем фрагмент в BackStack, что бы мы могли вернуться в предыдущий фрагмент
                .commit(); // открываем фрагмент
    }
    // Снэк с надписью для ошибки (вместо диалога)
    private void showInputError(String cause) {
        Snackbar snackbar = Snackbar.make(requireContext(), root, cause, Snackbar.LENGTH_LONG);
        snackbar.show();
    }
}