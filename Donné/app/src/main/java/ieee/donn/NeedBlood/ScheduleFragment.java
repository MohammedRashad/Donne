package ieee.donn.NeedBlood;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import ieee.donn.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;



/**
 * Created by rashad on 6/8/16.
 */

public class ScheduleFragment extends Fragment {

    int mYear, mMonth, mDay;
    TextView last, red, platelets, plasma, whole, times;
    Button setDate, minus, plus, share;
    Date date;
    Calendar c;
    SharedPreferences.Editor edit;
    SharedPreferences spf;
    String dateStr;
    DatePickerDialog dialog;

    int i = 0;


    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.schedule, container, false);

        setDate = (Button) root.findViewById(R.id.setDate);
        plus = (Button) root.findViewById(R.id.plus);
        share = (Button) root.findViewById(R.id.share);
        minus = (Button) root.findViewById(R.id.minus);

        last = (TextView) root.findViewById(R.id.last);
        red = (TextView) root.findViewById(R.id.red);
        platelets = (TextView) root.findViewById(R.id.platelets);
        plasma = (TextView) root.findViewById(R.id.plasma);
        whole = (TextView) root.findViewById(R.id.whole);
        times = (TextView) root.findViewById(R.id.times);


        spf = PreferenceManager.getDefaultSharedPreferences(getActivity());

        last.setText(spf.getString("last", "not set yet"));
        red.setText(spf.getString("red", "not set yet"));
        plasma.setText(spf.getString("plasma", "not set yet"));
        platelets.setText(spf.getString("platelets", "not set yet"));
        whole.setText(spf.getString("whole", "not set yet"));
        times.setText(spf.getString("times", "0") + " times");


        setDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);

                dialog = new DatePickerDialog(getActivity(), new mDateSetListener(), mYear, mMonth, mDay);
                dialog.show();

            }
        });


        plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                i = Integer.parseInt(spf.getString("times", "0"));
                times.setText(++i + " times");
                save("times", "" + i);

            }
        });


        minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                i = Integer.parseInt(spf.getString("times", "0"));

                if (i > 0) {

                    times.setText(--i + " times");
                    save("times", "" + i);

                }
            }
        });


        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                share("I've donated my blood for " + spf.getString("times", "0") +
                        " times\n#NeedBlood helped me to record my progress, " +
                        "you can get it here and help to spread awareness about blood donation : .............");

            }
        });

        return root;
    }


    class mDateSetListener implements DatePickerDialog.OnDateSetListener {

        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

            mYear = year;
            mMonth = monthOfYear;
            mDay = dayOfMonth;

            ///////////////////////////////////

            SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMMM yyyy");

            ///////////////////////////////////

            c.set(Calendar.YEAR, mYear);
            c.set(Calendar.MONTH, mMonth);
            c.set(Calendar.DAY_OF_MONTH, mDay);

            date = c.getTime();
            dateStr = dateFormat.format(date);

            last.setText(dateStr);

            save("last", dateStr);

            ///////////////////////////////////

            c.set(Calendar.YEAR, mYear);
            c.set(Calendar.MONTH, mMonth);
            c.set(Calendar.DAY_OF_MONTH, mDay);

            c.add(Calendar.DAY_OF_MONTH, 7);
            date = c.getTime();
            dateStr = dateFormat.format(date);

            platelets.setText(dateStr);

            save("platelets", dateStr);

            ///////////////////////////////////

            c.set(Calendar.YEAR, mYear);
            c.set(Calendar.MONTH, mMonth);
            c.set(Calendar.DAY_OF_MONTH, mDay);

            c.add(Calendar.DAY_OF_MONTH, 56);
            date = c.getTime();
            dateStr = dateFormat.format(date);

            whole.setText(dateStr);

            save("whole", dateStr);

            setAlarm(c);

            ///////////////////////////////////

            c.set(Calendar.YEAR, mYear);
            c.set(Calendar.MONTH, mMonth);
            c.set(Calendar.DAY_OF_MONTH, mDay);

            c.add(Calendar.DAY_OF_MONTH, 112);
            date = c.getTime();
            dateStr = dateFormat.format(date);

            red.setText(dateStr);

            save("red", dateStr);

            ///////////////////////////////////

            c.set(Calendar.YEAR, mYear);
            c.set(Calendar.MONTH, mMonth);
            c.set(Calendar.DAY_OF_MONTH, mDay);

            c.add(Calendar.DAY_OF_MONTH, 3);
            date = c.getTime();
            dateStr = dateFormat.format(date);

            plasma.setText(dateStr);

            save("plasma", dateStr);

            ///////////////////////////////////


        }
    }


    public void share(String message) {

        Intent share = new Intent(android.content.Intent.ACTION_SEND);
        share.setType("text/plain");

        share.putExtra(android.content.Intent.EXTRA_TEXT, message);
        startActivity(Intent.createChooser(share, "Share it"));


    }


    public void save(String key, String value) {

        spf = PreferenceManager.getDefaultSharedPreferences(getActivity());
        edit = spf.edit();
        edit.putString(key, value);
        edit.commit();

    }


    private void setAlarm(Calendar targetCal) {

        Intent intent = new Intent(getActivity(), AlarmReciever.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(getActivity(), 1, intent, 0);
        AlarmManager alarmManager = (AlarmManager) getActivity().getSystemService(Context.ALARM_SERVICE);
        alarmManager.set(AlarmManager.RTC_WAKEUP, targetCal.getTimeInMillis(), pendingIntent);

        Toast.makeText(getActivity() , "Date Set!\nYou'll be notified upon the next donation time." , Toast.LENGTH_LONG).show();
    }

}
