package timetable.insectiousapp.com.timetable.activities;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import timetable.insectiousapp.com.timetable.R;
import timetable.insectiousapp.com.timetable.fragments.AllClassroomFragment;
import timetable.insectiousapp.com.timetable.fragments.CreateNewClassFragment;
import timetable.insectiousapp.com.timetable.fragments.DefaultTimetableFragment;
import timetable.insectiousapp.com.timetable.fragments.ManageTimeTableFragment;
import timetable.insectiousapp.com.timetable.fragments.SetYourClassFragment;
import timetable.insectiousapp.com.timetable.fragments.WeekTimeTableFragment;
import timetable.insectiousapp.com.timetable.others.SharedPreferencesFiles;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    TextView tvClassId;
    NavigationView navigationView;
<<<<<<< HEAD
    int counter,i;
=======
      int gitVar;
>>>>>>> upstream/master

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);



        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

       // tvClassId=(TextView)navigationView.findViewById(R.id.nav_header_main_tv_classid);

        View headerView = LayoutInflater.from(this).inflate(R.layout.nav_header_main, navigationView, false);
        navigationView.addHeaderView(headerView);

        tvClassId = (TextView)headerView.findViewById(R.id.nav_header_main_tv_classid);

        SharedPreferencesFiles sf=new SharedPreferencesFiles();
        SharedPreferences sharedPreferences=getSharedPreferences(sf.getSPClassId(),0);
        String key=sharedPreferences.getString(sf.getClassId(),"Not Yet Set");

        if(key=="Not Yet Set"){
            SetYourClassFragment fragment=new SetYourClassFragment();
            getSupportFragmentManager().beginTransaction().replace(R.id.mainactivity_framelayout,fragment).commit();
            setTitle("Set Your Class");
            counter=0;

        }
        else{
            DefaultTimetableFragment fragment=new DefaultTimetableFragment();
            getSupportFragmentManager().beginTransaction().replace(R.id.mainactivity_framelayout,fragment).commit();
            setTitle("Default Timetable");
            counter=0;

        }


    }

    @Override
    protected void onResume() {
        super.onResume();

        SharedPreferencesFiles sharedPreferencesFiles=new SharedPreferencesFiles();
        SharedPreferences sharedPreferences=getSharedPreferences(sharedPreferencesFiles.getSPClassId(), 0);
        String classId=sharedPreferences.getString(sharedPreferencesFiles.getClassId(), "Not set Yet");

        //View nav_header = LayoutInflater.from(this).inflate(R.layout.nav_header_main, null);
        //((TextView) nav_header.findViewById(R.id.nav_header_main_tv_classid)).setText(classId);
        //navigationView.addHeaderView(nav_header);

        tvClassId.setText(classId);

    }

    @Override
    public void onBackPressed() {
        if(counter==0){
            super.onBackPressed();
        }

        else {
            SharedPreferencesFiles sf=new SharedPreferencesFiles();
            SharedPreferences sharedPreferences=getSharedPreferences(sf.getSPClassId(),0);
            String key=sharedPreferences.getString(sf.getClassId(),"Not Yet Set");

            if(key=="Not Yet Set"){
                if(i==0){
                    super.onBackPressed();
                }
                SetYourClassFragment fragment=new SetYourClassFragment();
                getSupportFragmentManager().beginTransaction().replace(R.id.mainactivity_framelayout,fragment).commit();
                setTitle("Set Your Class");
                counter=0;
            }
            else{
                if(i==1){
                    super.onBackPressed();
                }

                DefaultTimetableFragment fragment=new DefaultTimetableFragment();
                getSupportFragmentManager().beginTransaction().replace(R.id.mainactivity_framelayout,fragment).commit();
                setTitle("Default Timetable");
                counter=0;

            }


        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

         if (id == R.id.nav_create_new_fragment) {
            CreateNewClassFragment createNewClientFragment=new CreateNewClassFragment();
            getSupportFragmentManager().beginTransaction().replace(R.id.mainactivity_framelayout, createNewClientFragment).commit();
            setTitle("Create new classroom");
             counter=1;
        }
        else if(id ==R.id.nav_set_your_class) {
            SetYourClassFragment setYourClassFragment=new SetYourClassFragment();
            getSupportFragmentManager().beginTransaction().replace(R.id.mainactivity_framelayout, setYourClassFragment).commit();
            setTitle("Set Your class room");
             counter=1;
             i=0;
        }
        else if(id ==R.id.nav_default_timetable)
         {
             DefaultTimetableFragment defaultTimetableFragment=new DefaultTimetableFragment();
             getSupportFragmentManager().beginTransaction().replace(R.id.mainactivity_framelayout, defaultTimetableFragment).commit();
             setTitle("Default Timetable");
             counter=1;
             i=1;
         }
        else if(id==R.id.nav_manage_timetable)
         {
             ManageTimeTableFragment manageTimeTableFragment=new ManageTimeTableFragment();
             getSupportFragmentManager().beginTransaction().replace(R.id.mainactivity_framelayout, manageTimeTableFragment).commit();
             setTitle("Update Timetable");
             counter=1;
         }
        else if(id ==R.id.nav_all_classroom)
         {
             AllClassroomFragment allClassroomFragment = new AllClassroomFragment();
             getSupportFragmentManager().beginTransaction().replace(R.id.mainactivity_framelayout, allClassroomFragment).commit();
             setTitle("All classrooms of DTU");
             counter=1;

         }
        else if(id==R.id.nav_week_timetable)
         {
             WeekTimeTableFragment weekTimeTableFragment=new WeekTimeTableFragment();
             getSupportFragmentManager().beginTransaction().replace(R.id.mainactivity_framelayout, weekTimeTableFragment).commit();
             setTitle("This week's timetable");
             counter=1;
         }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
