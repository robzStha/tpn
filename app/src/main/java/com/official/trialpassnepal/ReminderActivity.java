package com.official.trialpassnepal;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;

import com.official.trialpassnepal.db.DbReminders;
import com.official.trialpassnepal.objects.ReminderObject;
import com.official.trialpassnepal.service.ReminderReceiver;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Iterator;

public class ReminderActivity extends BaseActivity {
    ImageView ivFab;
    ListView lvRemainders;
    ArrayList<ReminderObject> reminderObjects = new ArrayList<>();
    MyAdapter adapter;
    LayoutInflater inflater;
    TextView tvNoRemMsg;
    String newTitle = "temp", newDate = "temp";
    long newId;
    Button btnSetReminder;
    DbReminders dbReminders;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_remainder;
    }

    @Override
    protected String actionBarTitle() {
        return getString(R.string.remainder);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ivFab = (ImageView) findViewById(R.id.fab_add_remainder);
        lvRemainders = (ListView) findViewById(R.id.lv_remainders);
        adapter = new MyAdapter();
        tvNoRemMsg = (TextView) findViewById(R.id.tv_empty_msg);
        lvRemainders.setAdapter(adapter);
        btnSetReminder = (Button) findViewById(R.id.btn_setRem);
        dbReminders = new DbReminders(getApplicationContext());

        reminderObjects = dbReminders.getReminders();

        inflater = (LayoutInflater) getSystemService
                (Context.LAYOUT_INFLATER_SERVICE);
        ivFab.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        v.setAlpha(0.5f);
                        break;
                    case MotionEvent.ACTION_UP:
                        v.setAlpha(1f);
                        addNewRemainderView();
                        break;
                }
                return true;
            }
        });

        btnSetReminder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (newDate.isEmpty()) {
                    showAlertMsg("Please enter reminder date");
                } else {
                    initReminder();
                }
            }
        });

    }

    public void initReminder() {
        if (reminderObjects.size() > 0) {
            for (ReminderObject remObj : reminderObjects) {
                System.out.println("Rem obj: " + remObj.getDate() + " -- " + remObj.getRemainderType() + " -- " + remObj.getFullDate().getTime());
                int index = reminderObjects.indexOf(remObj);
                int notificationId = setReminderFor(remObj);
                if (notificationId == 0) {
                    return;
                }
                remObj.setNotificationId(notificationId);
                reminderObjects.set(index, remObj);
                System.out.println("Rabin is testing: Notify: set notification Id: " + notificationId);
            }
            removeEarlierReminders();
            dbReminders.insertReminders(reminderObjects);
            reminderObjects = new ArrayList<>();
            reminderObjects = dbReminders.getReminders();
            adapter.notifyDataSetChanged();
            showAlertMsg("Reminder saved successfully");
        }
    }

    public void initReminder(ArrayList<ReminderObject> reminderObjects, Context context) {
        if (reminderObjects.size() > 0) {
            for (ReminderObject remObj : reminderObjects) {
                System.out.println("Rem obj: " + remObj.getDate() + " -- " + remObj.getRemainderType() + " -- " + remObj.getFullDate().getTime());
                int index = reminderObjects.indexOf(remObj);
                int notificationId = setReminderFor(remObj, context);
                if (notificationId == 0) {
                    return;
                }
                remObj.setNotificationId(notificationId);
                reminderObjects.set(index, remObj);
                System.out.println("Rabin is testing: Notify: set notification Id: " + notificationId);
            }
            removeEarlierReminders();
            dbReminders.insertReminders(reminderObjects);
//            reminderObjects = new ArrayList<>();
//            reminderObjects = dbReminders.getReminders();
            adapter.notifyDataSetChanged();
        }
    }

    //get old reminders and remove the alarm manager
    private void removeEarlierReminders() {
        ArrayList<ReminderObject> reminderObjects = dbReminders.getReminders();
        for (ReminderObject remObj : reminderObjects) {
            int notificationId = remObj.getNotificationId();
            System.out.println("Rabin is testing: Notify: del notification Id: " + notificationId);
            deleteAlarmReminder(notificationId);
        }
    }

    private int setReminderFor(ReminderObject remObj) {
        String mNotiId = Long.toString(System.currentTimeMillis());
        int mNotificationId = Integer.parseInt(mNotiId.substring(mNotiId.length() - 6));
        String[] remTypes = (getResources().getStringArray(R.array.rem_types));
        String msg;
        if (remObj.getRemainderType().equals(remTypes[0])) {
            msg = "Its time for your vehicle servicing.";
        } else if (remObj.getRemainderType().equals(remTypes[1])) {
            msg = "Its time to change mobil of your vehicle.";
        } else if (remObj.getRemainderType().equals(remTypes[2])) {
            msg = "Your license is going to expire.";
        } else if (remObj.getRemainderType().equals(remTypes[3])) {
            msg = "Your blue book is going to expire.";
        } else /*(remObj.getRemainderType().equals(remTypes[4]))*/ {
            msg = "Your vehicle insurance is going to expire.";
        }

        if (remObj.getFullDate().getTimeInMillis() > System.currentTimeMillis())
            setReminder(remObj, mNotificationId, msg);
        else {
            showAlertMsg("Please select future date");
            return 0;
        }
        return mNotificationId;
    }
    private int setReminderFor(ReminderObject remObj, Context context) {
        String mNotiId = Long.toString(System.currentTimeMillis());
        int mNotificationId = Integer.parseInt(mNotiId.substring(mNotiId.length() - 6));
        String[] remTypes = (context.getResources().getStringArray(R.array.rem_types));
        String msg;
        if (remObj.getRemainderType().equals(remTypes[0])) {
            msg = "Its time for your vehicle servicing.";
        } else if (remObj.getRemainderType().equals(remTypes[1])) {
            msg = "Its time to change mobil of your vehicle.";
        } else if (remObj.getRemainderType().equals(remTypes[2])) {
            msg = "Your license is going to expire.";
        } else if (remObj.getRemainderType().equals(remTypes[3])) {
            msg = "Your blue book is going to expire.";
        } else /*(remObj.getRemainderType().equals(remTypes[4]))*/ {
            msg = "Your vehicle insurance is going to expire.";
        }

//        if (remObj.getFullDate().getTimeInMillis() > System.currentTimeMillis())
            setReminder(remObj, mNotificationId, msg);
//        else {
//            showAlertMsg("Please select future date");
//            return 0;
//        }
        return mNotificationId;
    }

    public void deleteAlarmReminder(int mNotificationId) {
        Intent i = new Intent(ReminderActivity.this, ReminderReceiver.class);
//        i.putExtra("notification_id", mNotificationId);
        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(ReminderActivity.this, mNotificationId, i, PendingIntent.FLAG_UPDATE_CURRENT);
        alarmManager.cancel(pendingIntent);
    }

    private void setReminder(ReminderObject remObj, int mNotificationId, String msg) {
        Intent i = new Intent(ReminderActivity.this, ReminderReceiver.class);
        i.putExtra("msg", msg);
        i.putExtra("notification_id", mNotificationId);

        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(ReminderActivity.this, mNotificationId, i, PendingIntent.FLAG_UPDATE_CURRENT);

        Calendar calendar = remObj.getFullDate();
        System.out.println("Rabin is testing: Remainder@ " + calendar.getTime() + " -- " + calendar.getTimeInMillis() + " -- " + System.currentTimeMillis());
//        if (calendar.getTimeInMillis() > System.currentTimeMillis()) {
        alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
//        } else {
//            System.out.println("Alarm expired " + calendar.getTime());
//        }
        System.out.println("Rabin is testing: Remainder@ set to -- " + calendar.getTimeInMillis() + " -- " + System.currentTimeMillis());
    }

    private void deleteReminder(final int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(ReminderActivity.this);
        builder.setMessage("Are you sure, you want to delete this reminder?");
        builder.setTitle("Delete");
        builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (reminderObjects.get(position).getNotificationId() != 0) {
                    deleteAlarmReminder(reminderObjects.get(position).getNotificationId());
                }
                System.out.println("Rabin is testing: Delete id: " + reminderObjects.get(position).getId());
                dbReminders.delete(reminderObjects.get(position).getId());
                reminderObjects.remove(position);
                adapter.notifyDataSetChanged();
            }
        }).setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void addNewRemainderView() {

        if (newDate.isEmpty()) {
            showAlertMsg("Please enter reminder date");
        } else {
            ReminderObject reminderObject = new ReminderObject("", "", 0);
            reminderObjects.add(reminderObject);
            adapter.notifyDataSetChanged();
            newDate = "";
        }

    }

    private void showAlertMsg(String msg) {
        AlertDialog.Builder builder = new AlertDialog.Builder(ReminderActivity.this);
        builder.setMessage(msg);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.create().show();
    }

    class MyAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return reminderObjects.size();
        }

        @Override
        public ReminderObject getItem(int position) {
            return reminderObjects.get(position);
        }

        @Override
        public long getItemId(int position) {
            return reminderObjects.get(position).getId();
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            if (getCount() == 0) {
                tvNoRemMsg.setVisibility(View.VISIBLE);
            } else {
                tvNoRemMsg.setVisibility(View.GONE);
            }
            if (convertView == null) {
                convertView = inflater.inflate(R.layout.remainder_list_item, parent, false);
            }

            final Spinner spinner = (Spinner) convertView.findViewById(R.id.sType);
            final TextView tvDate = (TextView) convertView.findViewById(R.id.tv_date);
            final ImageView ivDel = (ImageView) convertView.findViewById(R.id.iv_del);
            spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int itemPos, long id) {
                    newTitle = spinner.getSelectedItem().toString();
                    reminderObjects.get(position).setRemainderType(newTitle);
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });

            String[] spinnerItem = getResources().getStringArray(R.array.rem_types);

            spinner.setSelection(Arrays.asList(spinnerItem).indexOf(getItem(position).getRemainderType()));
            tvDate.setText(getItem(position).getDate());

            tvDate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showDateTimePicker(tvDate, position);
                }
            });

            ivDel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    deleteReminder(position);
                    newDate = "temp";
                }
            });
            return convertView;
        }

        Calendar date;

        public void showDateTimePicker(final TextView tv, final int position) {
            Calendar oldDate = (Calendar) tv.getTag();
            if (oldDate != null) {
                System.out.println("Old date: " + oldDate.getTime());
            } else {
                System.out.println("Old date: is null");
            }
            final Calendar currentDate = (oldDate != null) ? oldDate : Calendar.getInstance();
            date = Calendar.getInstance();
            new DatePickerDialog(ReminderActivity.this, new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                    date.set(year, monthOfYear, dayOfMonth);
                    new TimePickerDialog(ReminderActivity.this, new TimePickerDialog.OnTimeSetListener() {
                        @Override
                        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                            date.set(Calendar.HOUR_OF_DAY, hourOfDay);
                            date.set(Calendar.MINUTE, minute);
                            int hour = date.get(Calendar.HOUR_OF_DAY);
                            int month = date.get(Calendar.MONTH)+1;
                            int dayOfMonth = date.get(Calendar.DAY_OF_MONTH);
                            int min = date.get(Calendar.MINUTE);
                            hour = hour == 0 ? 12 : hour;
                            hour = (hour > 12 ? (hour - 12) : hour);
                            String sH = hour < 10 ? "0" + hour : hour + "";
                            String sM = month < 10 ? "0" + month : month + "";
                            String sD = dayOfMonth < 10 ? "0" + dayOfMonth : dayOfMonth + "";
                            String sMin = min < 10 ? "0" + min : min + "";

                            Log.v("Tag", "The chosen one " + date.getTime());
                            String sdate = date.get(Calendar.YEAR) + "/" + sM + "/" + sD +
                                    " " + sH +
                                    ":" + sMin +
                                    " " + (date.get(Calendar.AM_PM) == 0 ? "AM" : "PM");
                            tv.setText(sdate);
                            newDate = sdate;
                            tv.setTag(date);
                            reminderObjects.get(position).setDate(sdate);
                            reminderObjects.get(position).setFullDate(date);
                        }
                    }, currentDate.get(Calendar.HOUR_OF_DAY), currentDate.get(Calendar.MINUTE), false).show();
                }
            }, currentDate.get(Calendar.YEAR), currentDate.get(Calendar.MONTH), currentDate.get(Calendar.DATE)).show();
        }
    }


}
