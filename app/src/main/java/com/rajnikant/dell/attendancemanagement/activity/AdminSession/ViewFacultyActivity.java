package com.rajnikant.dell.attendancemanagement.activity.AdminSession;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.rajnikant.dell.attendancemanagement.BeanClass.FacultyBean;
import com.rajnikant.dell.attendancemanagement.DB.DBAdapter;
import com.rajnikant.dell.attendancemanagement.R;

import java.util.ArrayList;

public class ViewFacultyActivity extends Activity {

    ArrayList<FacultyBean> facultyBeanList;
    private static int facultyCount=1;
    private ListView listView;
    private ArrayAdapter<String> listAdapter;

    DBAdapter dbAdapter = new DBAdapter(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.listview_layout);


        listView = findViewById(R.id.listview);
        final ArrayList<String> facultyList = new ArrayList<>();

        facultyBeanList = dbAdapter.getAllFaculty();

        for (FacultyBean facultyBean : facultyBeanList) {

            if(facultyBean.getFaculty_firstname().isEmpty()){
                Toast.makeText(getBaseContext(),"Add faculty first!",Toast.LENGTH_SHORT).show();
            }
            else {
                String users = facultyCount+"." + "\t" + facultyBean.getFaculty_firstname() + "\t" + facultyBean.getFaculty_lastname();

                facultyList.add(users);
                facultyCount++;
                Log.d("users: ", users);
            }

        }

        listAdapter = new ArrayAdapter<>(this, R.layout.list_names, R.id.labelN, facultyList);
        listView.setAdapter(listAdapter);

        listView.setOnItemLongClickListener(new OnItemLongClickListener() {

            @Override
            public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
                                           final int position, long arg3) {

                facultyCount=1;
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(ViewFacultyActivity.this);

                alertDialogBuilder.setTitle(getTitle() + "decision");
                alertDialogBuilder.setMessage("Are you sure?");

                alertDialogBuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

//                        facultyList.remove(position);
                        facultyList.clear();
                        listAdapter.notifyDataSetChanged();
                        listAdapter.notifyDataSetInvalidated();

                        dbAdapter.deleteFaculty(facultyBeanList.get(position).getFaculty_id());
                        facultyBeanList = dbAdapter.getAllFaculty();

                        for (FacultyBean facultyBean : facultyBeanList) {

                            if(facultyBean.getFaculty_firstname().isEmpty()){
                                Toast.makeText(getBaseContext(),"Add faculty first!",Toast.LENGTH_SHORT).show();
                            }
                            else {
                                String users = facultyCount+"." + "\t" + facultyBean.getFaculty_firstname() + "\t" + facultyBean.getFaculty_lastname();

                                facultyList.add(users);
                                facultyCount++;
                                Log.d("users: ", users);
                            }

                        }

                    }

                });
                alertDialogBuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // cancel the alert box and put a Toast to the user
                        dialog.cancel();
                        Toast.makeText(getApplicationContext(), "You choose cancel",
                                Toast.LENGTH_LONG).show();
                    }
                });

                AlertDialog alertDialog = alertDialogBuilder.create();
                // show alert
                alertDialog.show();


                return false;
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
        super.onBackPressed();
        facultyCount=1;
    }
}
