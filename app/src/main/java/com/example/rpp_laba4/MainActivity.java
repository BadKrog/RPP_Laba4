package com.example.rpp_laba4;

import androidx.appcompat.app.AppCompatActivity;

import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.CalendarView;
import android.widget.CalendarView.OnDateChangeListener;
import android.widget.TextView;
import android.widget.Toast;


import java.util.Calendar;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    CalendarView calendar;
    Calendar currantDate;
    Calendar changeDate;
    TextView textDay;
    Intent intent;
    AppWidgetManager appWidgetManager;
    int appWidgetIds[];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        appWidgetManager = AppWidgetManager.getInstance(this);
        appWidgetIds = appWidgetManager.getAppWidgetIds(
                                        new ComponentName
                                            (this.getApplicationContext().getPackageName(),
                                                    ExampleAppWidgetProvider.class.getName()
                                            )
                                        );


        // Находим View
        calendar = (CalendarView) findViewById(R.id.calendarView2);
        textDay = (TextView) findViewById(R.id.textView2);

        // Создаем календари
        currantDate = Calendar.getInstance();
        changeDate = Calendar.getInstance();

        // Инициализируем текущее время
        currantDate.setTimeInMillis(calendar.getDate());

        // Устанавливаем минимальную дату для выбора
        calendar.setMinDate(calendar.getDate());

        intent = new Intent(this, ExampleAppWidgetProvider.class);

        // Устанавливаем слушателя на выбор даты в календаре
        calendar.setOnDateChangeListener(new OnDateChangeListener(){
            // Описываем метод выбора даты в календаре:
            @Override
            public void onSelectedDayChange(CalendarView view, int year,int month, int dayOfMonth) {

                changeDate.set(year, month, dayOfMonth);
                long millisecond = changeDate.getTimeInMillis() - currantDate.getTimeInMillis();
                int days = (int)(millisecond/(24*60*60*1000));
                textDay.setText("Осталось дней: "+days);

                ExampleAppWidgetProvider.info(days);
                ExampleAppWidgetProvider.update(MainActivity.this, appWidgetManager, appWidgetIds);

                // При выборе любой даты отображаем Toast сообщение с данными о выбранной дате (Год, Месяц, День):
                Toast.makeText(getApplicationContext(),
                        "Год: " + year + "\n" +
                                "Месяц: " + month + "\n" +
                                "День: " + dayOfMonth,
                        Toast.LENGTH_SHORT).show();
            }});
    }
}
