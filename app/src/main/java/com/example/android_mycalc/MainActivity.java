package com.example.android_mycalc;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {


    EditText textView1; //поле для ввода числа
    EditText textView2;  // текстовое поле для вывода знака операции
    TextView infoTextView;  // вывод результата

    //Результат операции будет попадать в переменную operand
    Double operand = null; // операнд операции
    String lastOperation = "="; // последняя операция

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.constrayte_layout);



        // получаем все поля по id из activity_main в которых текст будет меняться
        textView1 = (EditText) findViewById(R.id.textView1);
        textView2 = (EditText) findViewById(R.id.textView2);
        infoTextView = (TextView) findViewById(R.id.infoTextView);
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
}