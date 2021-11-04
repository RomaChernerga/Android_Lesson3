package com.example.android_mycalc;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    //задаем константы
    public static final String PREFERENCES_NAME = "main";
    public static final String THEME_NAME = "theme";
    //задаем номера темам
    public static final int AppThemeCodeStyle = 0;
    public static final int AppThemeLightCodeStyle = 1;
    public static final int AppThemeNightCodeStyle = 2;
    public static final int AppThemeDefault = 3;



    EditText textView1; //поле для ввода числа
    EditText textView2;  // текстовое поле для вывода знака операции
    TextView infoTextView;  // вывод результата

    //Результат операции будет попадать в переменную operand
    Double operand = null; // операнд операции
    String lastOperation = "="; // последняя операция

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // тема должна быть назначена раньше setContentView
        setTheme(loadAppTheme()); // назначить тему

        setContentView(R.layout.constrayte_layout);


        // получаем все поля по id из activity_main в которых текст будет меняться
        textView1 = (EditText) findViewById(R.id.textView1);
        textView2 = (EditText) findViewById(R.id.textView2);
        infoTextView = (TextView) findViewById(R.id.infoTextView);



        //настройка кнопок для перекл. стилей
        findViewById(R.id.radioButtonMaterialDark).setOnClickListener(v -> {
            SaveAppTheme(AppThemeNightCodeStyle);  //Мы создаем метод, в который будем передавать тему при нажатии на кнопки..
            recreate();                         //пересоздаем Активити
        });
        findViewById(R.id.radioButtonMaterialLight).setOnClickListener(v -> {
            SaveAppTheme(AppThemeLightCodeStyle); //Мы создаем метод, в который будем передавать тему при нажатии на кнопки..
            recreate();                         //пересоздаем Активити
        });
        findViewById(R.id.radioButtonMaterialDarkAction).setOnClickListener(v -> {
            SaveAppTheme(AppThemeCodeStyle);  //Мы создаем метод, в который будем передавать тему при нажатии на кнопки..
            recreate();                     //пересоздаем Активити
        });
        findViewById(R.id.radioButtonMyCoolStyle).setOnClickListener(v -> {
            SaveAppTheme(AppThemeDefault);  //Мы создаем метод, в который будем передавать тему при нажатии на кнопки..
            recreate();                     //пересоздаем Активити
        });

    }

    private int codeStyleToStyleId(int codeStyle){
        switch (codeStyle){
            case AppThemeCodeStyle:
                return R.style.LightTheme_2;
            case AppThemeLightCodeStyle:
                return R.style.LightTheme_1;
            case AppThemeNightCodeStyle:
                return R.style.AppThemeDark;
            case AppThemeDefault:
            default:
                return R.style.Theme_Android_MyCalc;
        }
    }

    private int loadAppTheme(){
        int codeTheme = getSharedPreferences(PREFERENCES_NAME, MODE_PRIVATE)
                .getInt(THEME_NAME, AppThemeDefault); //берем ранее созданные данные по сохранению темы из файла операндом get

        return codeStyleToStyleId(codeTheme);
    }

    private void SaveAppTheme(int code) {                // имя файла, где будет храниться и МОД передаем
        SharedPreferences sharedPreferences = getSharedPreferences(PREFERENCES_NAME, MODE_PRIVATE);
        sharedPreferences.edit()                        //операнд редактирования
                                .putInt(THEME_NAME, code) // операнд размещения
                                .apply();               // операнд применения(обязательно)
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putString("OPERATION", lastOperation);
        if (operand != null)
            outState.putDouble("OPERAND", operand);
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        lastOperation = savedInstanceState.getString("OPERATION");
        operand = savedInstanceState.getDouble("OPERAND");
        infoTextView.setText(operand.toString());
        textView2.setText(lastOperation);
    }

    public void onNumberClick(View view) {
        Button button = (Button) view;
        textView1.append(button.getText());

        if (lastOperation.equals("=") && operand != null) {
            operand = null;
        }
    }

    public void onOperationClick(View view) {
        //методе onOperationClick происходит обработка нажатия на кнопку со знаком операции
        Button button = (Button) view;
        String op = button.getText().toString();
        String number = textView1.getText().toString();

        // если введенно что-нибудь
        if (number.length() > 0) {
            number = number.replace(',', '.');
            try {
                performOperation(Double.valueOf(number), op);
            } catch (NumberFormatException ex) {
                textView1.setText("");
            }
        }


        lastOperation = op;
        textView2.setText(lastOperation);
    }

    private void performOperation(Double number, String op) {

        // если операнд ранее не был установлен (при вводе самой первой операции)
        if (operand == null) { //если операнд не установлен, выставляем его
            operand = number;
        } else {
            if (lastOperation.equals("=")) {
                lastOperation = op;
            }
            switch (lastOperation) {
                case "=":
                    operand = number;
                    break;
                case "/":
                    if (number == 0) {
                        operand = 0.0;
                    } else {
                        operand /= number;
                    }
                    break;
                case "*":
                    operand *= number;
                    break;
                case "+":
                    operand += number;
                    break;

                case "-":
                    operand -= number;
                    break;
            }
        }
        infoTextView.setText(operand.toString().replace('.', ','));
        textView1.setText("");


    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    public void button_clear(View view) {
        textView1.setText("");
        textView2.setText("");
        infoTextView.setText("");

    }
}