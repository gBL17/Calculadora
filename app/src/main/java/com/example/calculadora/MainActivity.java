package com.example.calculadora;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import io.realm.Realm;
import io.realm.RealmConfiguration;

public class MainActivity extends AppCompatActivity {
    private EditText valueA, valueB;
    private Button sum,subtraction,multiply,division,result;
    private ImageButton history;
    private Operators operator;
    private String realmName = "OperationsDB";

    RealmConfiguration config;
    Realm backgroundThreadRealm;



    private void selectButton(Button btn){
        sum.setBackgroundColor(getColor(R.color.orange));
        subtraction.setBackgroundColor(getColor(R.color.orange));
        multiply.setBackgroundColor(getColor(R.color.orange));
        division.setBackgroundColor(getColor(R.color.orange));

        btn.setBackgroundColor(getColor(R.color.dark_orange));
    }

    private void insert(String number1, String number2, Operators operator, Double result ){
        backgroundThreadRealm.executeTransaction (transactionRealm -> {
            Number maxId = backgroundThreadRealm.where(Operations.class).max("id");
            int nextId = (maxId == null) ? 1 : maxId.intValue() + 1;
            Date date = new Date();
            Operations op = new Operations(nextId,Double.parseDouble(number1), Double.parseDouble(number2), operator.getOperator(), result,date);
            transactionRealm.insert(op);
        });
        Toast.makeText(MainActivity.this, "Cálculo armazenado com sucesso", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Realm.init(this);
        config = new RealmConfiguration.Builder().name(realmName).allowWritesOnUiThread(true).allowQueriesOnUiThread(true).build();
        backgroundThreadRealm = Realm.getInstance(config);
        setContentView(R.layout.activity_main);

        history = findViewById(R.id.history);
        valueA = findViewById(R.id.value_a);
        valueB = findViewById(R.id.value_b);
        sum = findViewById(R.id.btn_sum);
        subtraction = findViewById(R.id.btn_subtraction);
        multiply = findViewById(R.id.btn_multiply);
        division = findViewById(R.id.btn_division);
        result = findViewById(R.id.btn_result);
        operator = Operators.SUM;

        selectButton(sum);

        history.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Intent intent = new Intent(MainActivity.this, HistoryActivity.class);
                startActivity(intent);
            }
        });

        sum.setOnClickListener(v -> {
            operator = Operators.SUM;
            selectButton(sum);
        });

        subtraction.setOnClickListener(v -> {
            operator = Operators.SUBTRACTION;
            selectButton(subtraction);
        });

        multiply.setOnClickListener(v -> {
            operator = Operators.MULTIPLY;
            selectButton(multiply);
        });

        division.setOnClickListener(view -> {
            operator = Operators.DIVISION;
            selectButton(division);
        });

        result.setOnClickListener(v -> {
            String a = valueA.getText().toString();
            String b = valueB.getText().toString();
            double result = 0.0;

            if (!a.isEmpty() && !b.isEmpty()){
                double num1 = Double.parseDouble(a);
                double num2 = Double.parseDouble(b);
                valueA.setText("");
                valueB.setText("");
                switch (operator){
                    case SUM:
                        result = num1 + num2;
                        break;
                    case SUBTRACTION:
                        result = num1 - num2;
                        break;
                    case MULTIPLY:
                        result = num1 * num2;
                        break;
                    case DIVISION:
                        if(num2 != 0){
                            result = num1 / num2;
                        } else {
                            Toast.makeText(MainActivity.this, "Operação impossível", Toast.LENGTH_SHORT).show();
                        }
                        break;
                }
                insert(a,b,operator,result);
            } else {
                Toast.makeText(MainActivity.this, "Por favor, preencha ambos os campos.", Toast.LENGTH_SHORT).show();
            }
        });
    }
}