package com.rajnikant.dell.attendancemanagement.activity.AdminSession;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.rajnikant.dell.attendancemanagement.BeanClass.FacultyBean;
import com.rajnikant.dell.attendancemanagement.DB.DBAdapter;
import com.rajnikant.dell.attendancemanagement.R;
import com.rajnikant.dell.attendancemanagement.activity.AdminMenuActivity;

public class AddFacultyActivity extends Activity {

    Button registerButton;
    EditText textFirstName;
    EditText textLastName;
    EditText textemail;
    EditText textcontact;
    EditText textaddress;
    EditText textusername;
    EditText textpassword;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addfaculty);


        textFirstName =  findViewById(R.id.editTextFirstName);
        textLastName =  findViewById(R.id.editTextLastName);
        textcontact =  findViewById(R.id.editTextPhone);
        textaddress =  findViewById(R.id.editTextaddr);
        textusername =  findViewById(R.id.editTextUserName);
        textpassword =  findViewById(R.id.editTextPassword);
        registerButton =  findViewById(R.id.RegisterButton);

        registerButton.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub

                String first_name = textFirstName.getText().toString();
                String last_name = textLastName.getText().toString();
                String phone_no = textcontact.getText().toString();
                String address = textaddress.getText().toString();
                String userName = textusername.getText().toString();
                String passWord = textpassword.getText().toString();

                if (TextUtils.isEmpty(first_name)) {
                    textFirstName.setError("please enter firstname");
                } else if (TextUtils.isEmpty(last_name)) {
                    textLastName.setError("please enter lastname");
                } else if (TextUtils.isEmpty(phone_no)) {
                    textcontact.setError("please enter phoneno");
                } else if (TextUtils.isEmpty(address)) {
                    textaddress.setError("enter address");
                } else if (TextUtils.isEmpty(userName)) {
                    textcontact.setError("please enter username");
                } else if (TextUtils.isEmpty(passWord)) {
                    textaddress.setError("enter password");
                } else {

                    FacultyBean facultyBean = new FacultyBean();
                    facultyBean.setFaculty_firstname(first_name);
                    facultyBean.setFaculty_lastname(last_name);
                    facultyBean.setFaculty_mobilenumber(phone_no);
                    facultyBean.setFaculty_address(address);
                    facultyBean.setFaculty_username(userName);
                    facultyBean.setFaculty_password(passWord);

                    DBAdapter dbAdapter = new DBAdapter(AddFacultyActivity.this);
                    dbAdapter.addFaculty(facultyBean);

                    Intent intent = new Intent(AddFacultyActivity.this, AdminMenuActivity.class);
                    startActivity(intent);
                    Toast.makeText(getApplicationContext(), "Faculty added successfully", Toast.LENGTH_SHORT).show();

                }

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
