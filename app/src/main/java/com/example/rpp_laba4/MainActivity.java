package com.example.rpp_laba4;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.CalendarView;
import android.widget.CalendarView.OnDateChangeListener;
import android.widget.TextView;
import android.widget.Toast;


import java.util.Calendar;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    // View
    CalendarView calendar;
    TextView textDay;

    // Все связанное со временем
    Calendar currantDate;
    Calendar changeDate;

    // Widget
    AppWidgetManager appWidgetManager;
    int appWidgetIds[];

    // Alarm
    AlarmManager alarmManager;
    PendingIntent pendingIntent;
    Intent alarmIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Создаем и настраиваем alarm
        alarmIntent = new Intent(this, CallDataSend.class);
        pendingIntent = PendingIntent.getBroadcast(this, 0, alarmIntent, 0);
        alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);

        // Получаем информацию и ссылки на widget
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

        // Устанавливаем слушателя на выбор даты в календаре
        calendar.setOnDateChangeListener(new OnDateChangeListener(){
            // Описываем метод выбора даты в календаре:
            @Override
            public void onSelectedDayChange(CalendarView view, int year,int month, int dayOfMonth) {

                // Вычисляем разницу в днях
                changeDate.set(year, month, dayOfMonth);
                long millisecond = changeDate.getTimeInMillis() - currantDate.getTimeInMillis();
                int days = (int)(millisecond/(24*60*60*1000));

                // Устанавливаем значение в View
                textDay.setText("Осталось дней: "+days);

                // Передаем информацию в Widget
                ExampleAppWidgetProvider.info(days);
                ExampleAppWidgetProvider.update(MainActivity.this, appWidgetManager, appWidgetIds);

                // Включаем alarm, сработает через 5 секунд после нажатия
                alarmManager.set(AlarmManager.RTC_WAKEUP, System.currentTimeMillis()+5000, pendingIntent);

                // Программа, которая будет показывать уведомление в 9 часов утра выбранного дня
                //alarmManager.set(AlarmManager.RTC_WAKEUP, changeDate.getTimeInMillis()+(60*60*1000+(51*60*1000)), pendingIntent);

                // Возможность установить собственное время вручную
                /*Calendar myTest = Calendar.getInstance();
                myTest.set(Calendar.YEAR, 2020);
                myTest.set(Calendar.MONTH, 3);
                myTest.set(Calendar.DAY_OF_MONTH, 13);
                myTest.set(Calendar.HOUR_OF_DAY, 1);
                myTest.set(Calendar.MINUTE, 58);
                alarmManager.set(AlarmManager.RTC_WAKEUP, myTest.getTimeInMillis(), pendingIntent);*/
            }});
    }
}
