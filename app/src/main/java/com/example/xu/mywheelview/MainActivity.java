package com.example.xu.mywheelview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    TextView textView, selectSex;
    private String selectYear = ChangeDatePopwindow.getYear();
    private String selectMonth = ChangeDatePopwindow.getMonth();
    private String selectDay = ChangeDatePopwindow.getDay();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = findViewById(R.id.selectDate);
        selectSex = findViewById(R.id.selectSex);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectDate();
            }
        });

        selectSex.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectSex();
            }
        });
    }

    private String[] selectDate() {
        Toast.makeText(MainActivity.this, selectYear + "-" + selectMonth + "-" + selectDay, Toast.LENGTH_LONG).show();
        final String[] str = new String[10];
        ChangeDatePopwindow mChangeBirthDialog = new ChangeDatePopwindow(
                this);
        mChangeBirthDialog.setDate(selectYear, selectMonth, selectDay, true);
        mChangeBirthDialog.showAtLocation(textView, Gravity.BOTTOM, 0, 0);
        mChangeBirthDialog.setBirthdayListener(new ChangeDatePopwindow.OnBirthListener() {
            @Override
            public void onClick(String year, String month, String day) {
                Toast.makeText(MainActivity.this, year + "-" + month + "-" + day, Toast.LENGTH_LONG).show();
                StringBuilder sb = new StringBuilder();
                sb.append(year.substring(0, year.length() - 1)).append("-").append(month.substring(0, month.length() - 1)).append("-").append(day.substring(0, day.length() - 1));

                selectYear = year.substring(0, year.length() - 1);
                selectMonth = month.substring(0, month.length() - 1);
                selectDay = day.substring(0, day.length() - 1);

                str[0] = year + "-" + month + "-" + day;
                str[1] = sb.toString();
                textView.setText(year + "-" + month + "-" + day);
                textView.setText(str[1]);
            }
        });
        return str;
    }

    private void selectSex() {
        ArrayList<String> list = new ArrayList<>();
        list.add("请选择性别");
        list.add("男");
        list.add("女");

        ChangeSexPopwindow changeDatePopwindow = new ChangeSexPopwindow(this, list);
        changeDatePopwindow.setCurrentItem(1);
        changeDatePopwindow.showAtLocation(selectSex, Gravity.BOTTOM, 0, 0);
        changeDatePopwindow.setBirthdayListener(new ChangeSexPopwindow.OnBirthListener() {
            @Override
            public void onClick(String date) {
                selectSex.setText(date);
            }
        });
    }

}
