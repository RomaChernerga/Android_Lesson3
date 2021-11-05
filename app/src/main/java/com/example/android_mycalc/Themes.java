package com.example.android_mycalc;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

public class Themes extends AppCompatActivity {
    //задаем константы
    public static final String PREFERENCES_NAME = "main";
    public static final String THEME_NAME = "theme";

    //задаем номера темам
    public static final int AppThemeCodeStyle = 0;
    public static final int AppThemeLightCodeStyle = 1;
    public static final int AppThemeNightCodeStyle = 2;
    public static final int AppThemeDefault = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.themes);

        findViewById(R.id.buttonBack).setOnClickListener(v -> {             //кнопка назад
            Intent refresh = new Intent(this,MainActivity.class);
            startActivity(refresh);                                         // обновляем предыдущее активити
            finish();                                                       // закрываем активити

        });

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

    private int codeStyleToStyleId(int codeStyle){  //применение тем
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

    public int loadAppTheme(){
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
}