package mx.itesm.edu.tidprueba;


import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;


/**
 * A simple {@link Fragment} subclass.
 */
public class Recordatorios extends Fragment {


    public Recordatorios() {
        // Required empty public constructor
    }

    DatePicker pickerDate;
    TimePicker pickerTime;
    ImageButton buttonSetAlarm;
   // TextView info;

    final static int RQS_1 =1;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_recordatorios, container, false);
        pickerDate = (DatePicker)view.findViewById(R.id.datePicker);
        pickerTime = (TimePicker)view.findViewById(R.id.timePicker);

        Calendar now = Calendar.getInstance();
        pickerDate.init(
                now.get(Calendar.YEAR),
                now.get(Calendar.MONTH),
                now.get(Calendar.DAY_OF_MONTH),
                null);
        pickerTime.setCurrentHour(
                now.get(Calendar.HOUR_OF_DAY)
        );
        pickerTime.setCurrentMinute(
                now.get(Calendar.MINUTE)
        );

        buttonSetAlarm = (ImageButton)view.findViewById(R.id.imageButton2);
        buttonSetAlarm.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View v) {
                  Calendar current = Calendar.getInstance();
                  Calendar cal = Calendar.getInstance();
                  cal.set(pickerDate.getYear(),
                          pickerDate.getMonth(),
                          pickerDate.getDayOfMonth(),
                          pickerTime.getCurrentHour(),
                          pickerTime.getCurrentMinute(),
                          00);
                  if(cal.compareTo(current)<=0){
                      Toast.makeText(getActivity().getApplicationContext(),
                              R.string.errorRecordatorio,
                              Toast.LENGTH_LONG).show();
                  }else{
                      setAlarm(cal);
                  }
              }
          }
        );

        return view;
    }
private void setAlarm(Calendar targetCal){
//    info.setText("\n\n***\n"+"Alarm is set@ "+targetCal.getTime() +"\n***\n" );
    Intent intent = new Intent(getActivity().getBaseContext(),AlarmReceiver.class);
    PendingIntent pendingIntent = PendingIntent.getBroadcast(getActivity().getBaseContext(),
            RQS_1,intent,0);
    AlarmManager alarmManager = (AlarmManager) getActivity().getSystemService(Context.ALARM_SERVICE);
    alarmManager.set(AlarmManager.RTC_WAKEUP,targetCal.getTimeInMillis(),pendingIntent);

}


}
