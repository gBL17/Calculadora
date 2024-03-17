package com.example.calculadora;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmResults;


public class HistoryActivity extends AppCompatActivity {
    RealmConfiguration config;
    Realm backgroundThreadRealm;
    private TableLayout tableLayout;
    private String realmName = "OperationsDB";

    private List<Operations> getOperationsFromDatabase() {
        RealmResults<Operations> results = backgroundThreadRealm.where(Operations.class).findAll();
        return backgroundThreadRealm.copyFromRealm(results);
    }

    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault());

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        Realm.init(this);

        config = new RealmConfiguration.Builder().name(realmName).allowWritesOnUiThread(true).allowQueriesOnUiThread(true).build();
        backgroundThreadRealm = Realm.getInstance(config);
        tableLayout = findViewById(R.id.operations_database);

        List<Operations> operationsList = getOperationsFromDatabase();

        for (Operations operation : operationsList) {
            TableRow row = new TableRow(this);

            TextView id = new TextView(this);
            id.setTextColor(getResources().getColor(R.color.beige));
            id.setMinWidth(70);
            id.setText(String.valueOf(operation.getId()));

            TextView valueA = new TextView(this);
            valueA.setTextColor(getResources().getColor(R.color.beige));
            valueA.setMinWidth(160);
            valueA.setText(String.valueOf(operation.getValueA()));

            TextView valueB = new TextView(this);
            valueB.setTextColor(getResources().getColor(R.color.beige));
            valueB.setMinWidth(160);
            valueB.setText(String.valueOf(operation.getValueB()));

            TextView op = new TextView(this);
            op.setTextColor(getResources().getColor(R.color.beige));
            op.setMinWidth(70);
            op.setText(String.valueOf(operation.getOp()));

            TextView result = new TextView(this);
            result.setTextColor(getResources().getColor(R.color.beige));
            result.setMinWidth(220);
            result.setText(String.valueOf(operation.getResult()));

            TextView date = new TextView(this);
            date.setTextColor(getResources().getColor(R.color.beige));
            date.setMinWidth(250);
            date.setText(String.valueOf(sdf.format(operation.getDate())));

            row.addView(id);
            row.addView(valueA);
            row.addView(valueB);
            row.addView(op);
            row.addView(result);
            row.addView(date);

            tableLayout.addView(row);
        }
    }
}