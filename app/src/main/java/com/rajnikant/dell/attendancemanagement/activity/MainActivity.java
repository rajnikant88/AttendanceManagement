package com.rajnikant.dell.attendancemanagement.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.rajnikant.dell.attendancemanagement.BeanClass.FacultyBean;
import com.rajnikant.dell.attendancemanagement.DB.DBAdapter;
import com.rajnikant.dell.attendancemanagement.R;
import com.rajnikant.dell.attendancemanagement.context.ApplicationContext;

public class MainActivity extends Activity {

    private Button login;
    private EditText username, password;
    private Spinner spinner;
    private String userrole;
    private static final String[] userRoleString = {"admin", "faculty"};
    private RadioGroup radioGroup;
    private RadioButton radioButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        login = findViewById(R.id.buttonlogin);
        username = findViewById(R.id.eusername);
        password = findViewById(R.id.epassword);
//        spinner = findViewById(R.id.spinner);
        radioGroup = findViewById(R.id.radioGroup);
        username.setText("");
        password.setText("");

        final int selectionid = radioGroup.getCheckedRadioButtonId();
        radioButton = findViewById(selectionid);
        userrole = (String) radioButton.getText();
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton rb = group.findViewById(checkedId);
                userrole = (String) rb.getText();
                username.setText("");
                password.setText("");
                username.requestFocus();
            }
        });

        /*spinner.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                userrole = userRoleString[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });*/

//        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, userRoleString);
//
//        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        spinner.setAdapter(adapter);

        login.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "Select admin or faculty first", Toast.LENGTH_SHORT).show();

                if (selectionid == -1) {
                    Toast.makeText(MainActivity.this, "Select admin or faculty first", Toast.LENGTH_SHORT).show();
                }
                // TODO Auto-generated method stub
                else {
                    if (userrole.equals("admin")) {

                        String user_name = username.getText().toString();
                        String pass_word = password.getText().toString();
                        Toast.makeText(getBaseContext(), user_name + "" + pass_word, Toast.LENGTH_SHORT).show();


                        if (TextUtils.isEmpty(user_name)) {
                            username.setError("Invalid User Name");
                        } else if (TextUtils.isEmpty(pass_word)) {
                            password.setError("enter password");
                        } else {
                            if (user_name.equals("admin") & pass_word.equals("admin@123")) {
                                Intent intent = new Intent(getBaseContext(), AdminMenuActivity.class);
                                startActivity(intent);
                                Toast.makeText(getApplicationContext(), "Login successful", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(getApplicationContext(), "Login failed", Toast.LENGTH_SHORT).show();

                            }
                        }
                    } else {

                        String user_name = username.getText().toString();
                        String pass_word = password.getText().toString();

                        if (TextUtils.isEmpty(user_name)) {
                            username.setError("Invalid User Name");
                        }
                        if (TextUtils.isEmpty(pass_word)) {
                            password.setError("enter password");
                        }
//                        Toast.makeText(getBaseContext(),userrole,Toast.LENGTH_SHORT).show();

                        DBAdapter dbAdapter = new DBAdapter(getBaseContext());
                        FacultyBean facultyBean = dbAdapter.validateFaculty(user_name, pass_word);

                        if (facultyBean != null) {
                            Intent intent = new Intent(getBaseContext(), FacultyMenuActivity.class);
                            intent.putExtra("facultyName",user_name);
                            startActivity(intent);

                            ((ApplicationContext)MainActivity.this.getApplicationContext()).setFacultyBean(facultyBean);
                            Toast.makeText(getApplicationContext(), "Login successful", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(getApplicationContext(), "Login failed", Toast.LENGTH_SHORT).show();

                        }
                    }
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
