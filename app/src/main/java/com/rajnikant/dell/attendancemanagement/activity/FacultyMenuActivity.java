package com.rajnikant.dell.attendancemanagement.activity;

import java.util.ArrayList;
import java.util.Calendar;


import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.rajnikant.dell.attendancemanagement.BeanClass.AttendanceBean;
import com.rajnikant.dell.attendancemanagement.BeanClass.AttendanceSessionBean;
import com.rajnikant.dell.attendancemanagement.BeanClass.FacultyBean;
import com.rajnikant.dell.attendancemanagement.BeanClass.StudentBean;
import com.rajnikant.dell.attendancemanagement.DB.DBAdapter;
import com.rajnikant.dell.attendancemanagement.R;
import com.rajnikant.dell.attendancemanagement.activity.FacultySession.AddAttendanceActivity;
import com.rajnikant.dell.attendancemanagement.activity.FacultySession.ViewAttendanceByFacultyActivity;
import com.rajnikant.dell.attendancemanagement.context.ApplicationContext;

public class FacultyMenuActivity extends Activity {

    private ImageView date;
    private Calendar cal;
    private int day, month, dyear;
    private EditText dateEditText;
    Button addAttendancebtn;
    Button viewAttendance;
    Button viewTotalAttendance;
    Spinner spinnerbranch, spinneryear, spinnerSubject;
    String branch = "cse";
    String year = "SE";
    String subject = "SC";

    private String[] branchString = new String[]{"cse"};
    private String[] yearString = new String[]{"SE", "TE", "BE"};
    private String[] subjectSEString = new String[]{"SC", "MC"};
    private String[] subjectTEString = new String[]{"GT", "CN"};
    private String[] subjectBEString = new String[]{"DS", "NS"};

    private String[] subjectFinal = new String[]{"M3", "DS", "M4", "CN", "M5", "NS"};
    AttendanceSessionBean attendanceSessionBean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_faculty_session);

        //Assume subject will be SE
        //subjectFinal = subjectSEString;

        spinnerbranch =  findViewById(R.id.spinner1);
        spinneryear =  findViewById(R.id.spinneryear);
        spinnerSubject =  findViewById(R.id.spinnerSE);

        ArrayAdapter<String> adapter_branch = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, branchString);
        adapter_branch.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerbranch.setAdapter(adapter_branch);
        spinnerbranch.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View view,
                                       int arg2, long arg3) {
                // TODO Auto-generated method stub
                ((TextView) arg0.getChildAt(0)).setTextColor(Color.BLACK);
                branch = (String) spinnerbranch.getSelectedItem();
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub
            }
        });

        ///......................spinner2
        ArrayAdapter<String> adapter_year = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, yearString);
        adapter_year.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinneryear.setAdapter(adapter_year);
        spinneryear.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View view,
                                       int arg2, long arg3) {
                // TODO Auto-generated method stub
                ((TextView) arg0.getChildAt(0)).setTextColor(Color.BLACK);
                year = (String) spinneryear.getSelectedItem();
                Toast.makeText(getApplicationContext(), "year:" + year, Toast.LENGTH_SHORT).show();

				/*if(year.equalsIgnoreCase("se"))
				{
					subjectFinal = subjectSEString;
				}
				else if(year.equalsIgnoreCase("te"))
				{
					subjectFinal = subjectTEString;
				}
				else if(year.equalsIgnoreCase("be"))
				{
					subjectFinal = subjectBEString;
				}*/

            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub
            }
        });

        ArrayAdapter<String> adapter_subject = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, subjectFinal);
        adapter_subject.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerSubject.setAdapter(adapter_subject);
        spinnerSubject.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View view,
                                       int arg2, long arg3) {
                // TODO Auto-generated method stub
                ((TextView) arg0.getChildAt(0)).setTextColor(Color.BLACK);
                subject = (String) spinnerSubject.getSelectedItem();

            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub
            }
        });


        date = findViewById(R.id.DateImageView);
        cal = Calendar.getInstance();
        day = cal.get(Calendar.DAY_OF_MONTH);
        month = cal.get(Calendar.MONTH);
        dyear = cal.get(Calendar.YEAR);
        dateEditText =  findViewById(R.id.DateEditText);
        date.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                showDialog(0);

            }
        });

        addAttendancebtn = findViewById(R.id.addAttendancebtn);
        addAttendancebtn.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                String datestring = dateEditText.getText().toString();
                if (datestring.isEmpty()) {
                    Toast.makeText(getBaseContext(), "Select date first", Toast.LENGTH_SHORT).show();
                } else {
                    String d = dateEditText.getText().toString();

                    /*AttendanceSessionBean attendanceSessionBean = new AttendanceSessionBean();

                    FacultyBean bean = ((ApplicationContext) FacultyMenuActivity.this.getApplicationContext()).getFacultyBean();

                    attendanceSessionBean.setAttendance_session_faculty_id(bean.getFaculty_id());
                    attendanceSessionBean.setAttendance_session_department(branch);
                    attendanceSessionBean.setAttendance_session_class(year);
                    attendanceSessionBean.setAttendance_session_date(d);
                    attendanceSessionBean.setAttendance_session_subject(subject);

                    DBAdapter dbAdapter = new DBAdapter(FacultyMenuActivity.this);
                    int sessionId = dbAdapter.addAttendanceSession(attendanceSessionBean);

                    ArrayList<StudentBean> studentBeanList = dbAdapter.getAllStudentByBranchYear(branch, year);
                    ((ApplicationContext) FacultyMenuActivity.this.getApplicationContext()).setStudentBeanList(studentBeanList);*/

                    Intent intent = new Intent(FacultyMenuActivity.this, AddAttendanceActivity.class);
                    intent.putExtra("b", branch);
                    intent.putExtra("y", year);
                    intent.putExtra("s", subject);
                    intent.putExtra("d", d);
//                    intent.putExtra("sessionId",sessionId);
                    startActivity(intent);
                }
            }
        });

        viewAttendance = findViewById(R.id.viewAttendancebutton);
        viewAttendance.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {

                AttendanceSessionBean attendanceSessionBean = new AttendanceSessionBean();
                FacultyBean bean = ((ApplicationContext) FacultyMenuActivity.this.getApplicationContext()).getFacultyBean();

                attendanceSessionBean.setAttendance_session_faculty_id(bean.getFaculty_id());
                attendanceSessionBean.setAttendance_session_department(branch);
                attendanceSessionBean.setAttendance_session_class(year);
                attendanceSessionBean.setAttendance_session_date(dateEditText.getText().toString());
                attendanceSessionBean.setAttendance_session_subject(subject);

                DBAdapter dbAdapter = new DBAdapter(FacultyMenuActivity.this);

                ArrayList<AttendanceBean> attendanceBeanList = dbAdapter.getAttendanceBySessionID(attendanceSessionBean);
                ((ApplicationContext) FacultyMenuActivity.this.getApplicationContext()).setAttendanceBeanList(attendanceBeanList);

                Intent intent = new Intent(FacultyMenuActivity.this, ViewAttendanceByFacultyActivity.class);
                startActivity(intent);

            }
        });

        viewTotalAttendance =  findViewById(R.id.viewTotalAttendanceButton);
        viewTotalAttendance.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                AttendanceSessionBean attendanceSessionBean = new AttendanceSessionBean();
                FacultyBean bean = ((ApplicationContext) FacultyMenuActivity.this.getApplicationContext()).getFacultyBean();

                attendanceSessionBean.setAttendance_session_faculty_id(bean.getFaculty_id());
                attendanceSessionBean.setAttendance_session_department(branch);
                attendanceSessionBean.setAttendance_session_class(year);
                attendanceSessionBean.setAttendance_session_subject(subject);

                DBAdapter dbAdapter = new DBAdapter(FacultyMenuActivity.this);

                ArrayList<AttendanceBean> attendanceBeanList = dbAdapter.getTotalAttendanceBySessionID(attendanceSessionBean);
                ((ApplicationContext) FacultyMenuActivity.this.getApplicationContext()).setAttendanceBeanList(attendanceBeanList);

                Intent intent = new Intent(FacultyMenuActivity.this, ViewAttendanceByFacultyActivity.class);
                startActivity(intent);

            }
        });
    }

    @Override
    @Deprecated
    protected Dialog onCreateDialog(int id) {
        return new DatePickerDialog(this, datePickerListener, dyear, month, day);
    }

    private DatePickerDialog.OnDateSetListener datePickerListener = new DatePickerDialog.OnDateSetListener() {
        public void onDateSet(DatePicker view, int selectedYear,
                              int selectedMonth, int selectedDay) {
            dateEditText.setText(selectedDay + " / " + (selectedMonth + 1) + " / "
                    + selectedYear);
        }
    };

    @Override
    public void onBackPressed() {
        startActivity(new Intent(getBaseContext(),MainActivity.class));
    }
}
