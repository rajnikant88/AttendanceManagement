package com.rajnikant.dell.attendancemanagement.activity.FacultySession;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.Toast;

import com.rajnikant.dell.attendancemanagement.BeanClass.AttendanceBean;
import com.rajnikant.dell.attendancemanagement.BeanClass.AttendanceSessionBean;
import com.rajnikant.dell.attendancemanagement.BeanClass.FacultyBean;
import com.rajnikant.dell.attendancemanagement.BeanClass.StudentBean;
import com.rajnikant.dell.attendancemanagement.DB.DBAdapter;
import com.rajnikant.dell.attendancemanagement.R;
import com.rajnikant.dell.attendancemanagement.activity.FacultyMenuActivity;
import com.rajnikant.dell.attendancemanagement.context.ApplicationContext;

import java.util.ArrayList;

public class AddAttendanceActivity extends Activity {

    ArrayList<StudentBean> studentBeanList;
    private ListView listView;
    private ArrayAdapter<String> listAdapter;
    int sessionId = 0;
    String status = "P";
    Button attendanceSubmit;
    DBAdapter dbAdapter = new DBAdapter(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.listview_layout);


        String branch = getIntent().getExtras().getString("b");
        String year = getIntent().getExtras().getString("y");
        String d = getIntent().getExtras().getString("d");
        String subject = getIntent().getExtras().getString("s");

        AttendanceSessionBean attendanceSessionbean = new AttendanceSessionBean();

        FacultyBean fbean = ((ApplicationContext) AddAttendanceActivity.this.getApplicationContext()).getFacultyBean();

        attendanceSessionbean.setAttendance_session_faculty_id(fbean.getFaculty_id());
        attendanceSessionbean.setAttendance_session_department(branch);
        attendanceSessionbean.setAttendance_session_class(year);
        attendanceSessionbean.setAttendance_session_date(d);
        attendanceSessionbean.setAttendance_session_subject(subject);

        DBAdapter dbAdapter = new DBAdapter(AddAttendanceActivity.this);
        sessionId = dbAdapter.addAttendanceSession(attendanceSessionbean);

        ArrayList<StudentBean> studentBeanlist = dbAdapter.getAllStudentByBranchYear(branch, year);
        ((ApplicationContext) AddAttendanceActivity.this.getApplicationContext()).setStudentBeanList(studentBeanlist);


        listView = findViewById(R.id.listview);
        final ArrayList<String> studentList = new ArrayList<String>();

        studentBeanList = ((ApplicationContext) AddAttendanceActivity.this.getApplicationContext()).getStudentBeanList();

        for (StudentBean studentBean : studentBeanList) {
            if (studentBeanList.isEmpty()) {
                Toast.makeText(getBaseContext(), "No data Found", Toast.LENGTH_SHORT).show();
            } else {
                String users = " \t " + studentBean.getStudent_firstname() + " \t " + studentBean.getStudent_lastname();
                Toast.makeText(getBaseContext(), "data Found", Toast.LENGTH_SHORT).show();


                studentList.add(users);
                Log.d("users: ", users);
            }

        }

        listAdapter = new ArrayAdapter<>(this, R.layout.list_names, R.id.labelN, studentList);
        listView.setAdapter(listAdapter);

        listView.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                                    long arg3) {

                arg0.getChildAt(arg2).setBackgroundColor(Color.TRANSPARENT);
                //arg0.setBackgroundColor(234567);
                arg1.setBackgroundColor(Color.GRAY);

                final StudentBean studentBean = studentBeanList.get(arg2);
                final Dialog dialog = new Dialog(AddAttendanceActivity.this);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);//...........
                dialog.setContentView(R.layout.dialog_presnent_absent);
                // set title and cancelable
                RadioGroup radioGroup;
                RadioButton present;
                RadioButton absent;
                radioGroup = dialog.findViewById(R.id.radioGroup);
                present = dialog.findViewById(R.id.PresentradioButton);
                absent = dialog.findViewById(R.id.AbsentradioButton);
                radioGroup.setOnCheckedChangeListener(new OnCheckedChangeListener() {

                    @Override
                    public void onCheckedChanged(RadioGroup group, int checkedId) {
                        if (checkedId == R.id.PresentradioButton) {

                            status = "P";
                        } else if (checkedId == R.id.AbsentradioButton) {

                            status = "A";
                        } else {
                        }
                    }
                });

                attendanceSubmit = dialog.findViewById(R.id.attendanceSubmitButton);
                attendanceSubmit.setOnClickListener(new OnClickListener() {

                    @Override
                    public void onClick(View arg0) {


                        AttendanceBean attendanceBean = new AttendanceBean();

                        attendanceBean.setAttendance_session_id(sessionId);
                        attendanceBean.setAttendance_student_id(studentBean.getStudent_id());
                        attendanceBean.setAttendance_status(status);

                        DBAdapter db = new DBAdapter(AddAttendanceActivity.this);
                        db.addNewAttendance(attendanceBean);

                        dialog.dismiss();

                    }
                });

                dialog.setCancelable(true);
                dialog.show();
            }
        });


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

}
