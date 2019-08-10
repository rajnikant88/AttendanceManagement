package com.rajnikant.dell.attendancemanagement.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.rajnikant.dell.attendancemanagement.BeanClass.AttendanceBean;
import com.rajnikant.dell.attendancemanagement.DB.DBAdapter;
import com.rajnikant.dell.attendancemanagement.R;
import com.rajnikant.dell.attendancemanagement.activity.AdminSession.AddFacultyActivity;
import com.rajnikant.dell.attendancemanagement.activity.AdminSession.AddStudentActivity;
import com.rajnikant.dell.attendancemanagement.activity.AdminSession.ViewAttendancePerStudentActivity;
import com.rajnikant.dell.attendancemanagement.activity.AdminSession.ViewFacultyActivity;
import com.rajnikant.dell.attendancemanagement.activity.AdminSession.ViewStudentActivity;
import com.rajnikant.dell.attendancemanagement.context.ApplicationContext;

import java.util.ArrayList;

public class AdminMenuActivity extends Activity {

    Button addStudent;
    Button addFaculty;
    Button viewStudent;
    Button viewFaculty;
    Button logout;
    Button attendancePerStudent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_menu);

        addStudent = findViewById(R.id.buttonaddstudent);
        addFaculty = findViewById(R.id.buttonaddfaculty);
        viewStudent = findViewById(R.id.buttonViewstudent);
        viewFaculty = findViewById(R.id.buttonviewfaculty);
        logout = findViewById(R.id.buttonlogout);

        addStudent.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub

                Intent intent = new Intent(AdminMenuActivity.this, AddStudentActivity.class);
                startActivity(intent);
            }
        });

        addFaculty.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub

                Intent intent = new Intent(AdminMenuActivity.this, AddFacultyActivity.class);
                startActivity(intent);
            }
        });

        viewFaculty.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub

                Intent intent = new Intent(AdminMenuActivity.this, ViewFacultyActivity.class);
                startActivity(intent);
            }
        });


        viewStudent.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub

                Intent intent = new Intent(AdminMenuActivity.this, ViewStudentActivity.class);
                startActivity(intent);
            }
        });
        logout.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub

                Intent intent = new Intent(AdminMenuActivity.this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });
        attendancePerStudent = findViewById(R.id.attendancePerStudentButton);

        attendancePerStudent.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {

                DBAdapter dbAdapter = new DBAdapter(AdminMenuActivity.this);
                ArrayList<AttendanceBean> attendanceBeanList = dbAdapter.getAllAttendanceByStudent();
                ((ApplicationContext) AdminMenuActivity.this.getApplicationContext()).setAttendanceBeanList(attendanceBeanList);

                Intent intent = new Intent(AdminMenuActivity.this, ViewAttendancePerStudentActivity.class);
                startActivity(intent);

            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(getBaseContext(), MainActivity.class));

    }
}
