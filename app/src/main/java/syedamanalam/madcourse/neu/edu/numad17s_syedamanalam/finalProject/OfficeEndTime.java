package syedamanalam.madcourse.neu.edu.numad17s_syedamanalam.finalProject;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TimePicker;

import syedamanalam.madcourse.neu.edu.numad17s_syedamanalam.R;

public class OfficeEndTime extends AppCompatActivity {

    public static int  endOfficeHour, endOfficeMin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_office_end_time);
        setTitle("Office End Time");


        //END OFFICE TIME
        final TimePicker tpEnd = (TimePicker) findViewById(R.id.timePickerEndTime);
//        tpEnd.setIs24HourView(true);

        Button getHomeLocationButton = (Button) findViewById(R.id.submitOfficeEndTime);
        getHomeLocationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //Add the office end timings to database
                endOfficeHour = tpEnd.getCurrentHour();
                endOfficeMin = tpEnd.getCurrentMinute();

                //After the office timings, get user's home location
                Intent getHomeLocationScreen = new Intent(OfficeEndTime.this, animation.class);
                startActivity(getHomeLocationScreen);
                finish();
            }
        });
    }

    public void onBackPressed(){
        Intent i = new Intent(OfficeEndTime.this,OfficeStartTime.class);
        startActivity(i);
        finish();
    }

}
