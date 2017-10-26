package syedamanalam.madcourse.neu.edu.numad17s_syedamanalam.finalProject;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TimePicker;

import syedamanalam.madcourse.neu.edu.numad17s_syedamanalam.R;

public class OfficeStartTime extends AppCompatActivity {

    public static int startOfficeHour, startOfficeMin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_office_start_time);
        setTitle("Office Start Time");


        //NOW RETRIEVE TIME FROM TIME_PICKER AND SAVE IT TO DATABASE
        //START OFFICE TIME
        final TimePicker tpStart = (TimePicker) findViewById(R.id.timePickerStartTime);


        Button submitOfficeTime = (Button) findViewById(R.id.submitOfficeStartTime);
        submitOfficeTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //Add these timings to database
                startOfficeHour = tpStart.getCurrentHour();
                startOfficeMin = tpStart.getCurrentMinute();
//                Log.e("Office Start",String.valueOf(h1));

                //After the office timings, get user's home location
                Intent goToOfficeEndTimingScreen = new Intent(OfficeStartTime.this, OfficeEndTime.class);
                startActivity(goToOfficeEndTimingScreen);
                finish();

            }
        });

    }//End of onCreate

    public void onBackPressed(){
        Intent i = new Intent(OfficeStartTime.this,MapsActivity.class);
        startActivity(i);
        finish();
    }


}