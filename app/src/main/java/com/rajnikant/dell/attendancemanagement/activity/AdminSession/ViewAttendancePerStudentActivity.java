package com.rajnikant.dell.attendancemanagement.activity.AdminSession;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.rajnikant.dell.attendancemanagement.BeanClass.AttendanceBean;
import com.rajnikant.dell.attendancemanagement.BeanClass.StudentBean;
import com.rajnikant.dell.attendancemanagement.DB.DBAdapter;
import com.rajnikant.dell.attendancemanagement.R;
import com.rajnikant.dell.attendancemanagement.context.ApplicationContext;

import java.util.ArrayList;

public class ViewAttendancePerStudentActivity extends Activity {

    ArrayList<AttendanceBean> attendanceBeanList;
    private ListView listView;
    private ArrayAdapter<String> listAdapter;

    DBAdapter dbAdapter = new DBAdapter(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.listview_layout);

        listView = findViewById(R.id.listview);
        final ArrayList<String> attendanceList = new ArrayList<String>();
        attendanceList.add("Present Count Per Student");
        attendanceBeanList = ((ApplicationContext) ViewAttendancePerStudentActivity.this.getApplicationContext()).getAttendanceBeanList();

        for (AttendanceBean attendanceBean : attendanceBeanList) {
            String users = "";

            DBAdapter dbAdapter = new DBAdapter(ViewAttendancePerStudentActivity.this);
            StudentBean studentBean = dbAdapter.getStudentById(attendanceBean.getAttendance_student_id());
            if (studentBean.getStudent_firstname() == null) {
                Toast.makeText(getBaseContext(), "No Data Found", Toast.LENGTH_SHORT).show();
            } else {
                users = attendanceBean.getAttendance_student_id() + " \tFirstName: " + studentBean.getStudent_firstname() + "\tLastName: " + studentBean.getStudent_lastname() + "\nCount: " + attendanceBean.getAttendance_session_id();
                attendanceList.add(users);
            }
        }

        listAdapter = new ArrayAdapter<>(this, R.layout.list_attendence, R.id.label, attendanceList);
        listView.setAdapter(listAdapter);

		/*listView.setOnItemLongClickListener(new OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
					final int position, long arg3) {



				AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(ViewAttendanceByFacultyActivity.this);

				alertDialogBuilder.setTitle(getTitle()+"decision");
				alertDialogBuilder.setMessage("Are you sure?");

				alertDialogBuilder.setPositiveButton("Yes",new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog,int id) {

						facultyList.remove(position);
						listAdapter.notifyDataSetChanged();
						listAdapter.notifyDataSetInvalidated();   

						dbAdapter.deleteFaculty(facultyBeanList.get(position).getFaculty_id());
						facultyBeanList=dbAdapter.getAllFaculty();

						for(FacultyBean facultyBean : facultyBeanList)
						{
							String users = " FirstName: " + facultyBean.getFaculty_firstname()+"\nLastname:"+facultyBean.getFaculty_lastname();
							facultyList.add(users);
							Log.d("users: ", users); 

						}
						
					}
					
				});
				alertDialogBuilder.setNegativeButton("No",new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog,int id) {
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
*/


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

}
