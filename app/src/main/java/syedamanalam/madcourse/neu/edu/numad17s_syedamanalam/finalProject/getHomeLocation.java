package syedamanalam.madcourse.neu.edu.numad17s_syedamanalam.finalProject;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;

import syedamanalam.madcourse.neu.edu.numad17s_syedamanalam.R;

/**
 * Created by Dark Horse on 16-04-2017.
 */

public class getHomeLocation extends AppCompatActivity {

    int REQUEST_CODE = 1;
    public TextView updateHomeLocationTextView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_home_location);
        setTitle("Home Location");

        Button selectHomeLoctn = (Button) findViewById(R.id.selectHomeLocation);
        selectHomeLoctn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ContextCompat.checkSelfPermission(getHomeLocation.this, Manifest.permission.ACCESS_FINE_LOCATION)
                        + ContextCompat.checkSelfPermission(getHomeLocation.this, Manifest.permission.ACCESS_COARSE_LOCATION)

                        !=

                        PackageManager.PERMISSION_GRANTED) {
                    if (ActivityCompat.shouldShowRequestPermissionRationale
                            (getHomeLocation.this, Manifest.permission.ACCESS_COARSE_LOCATION) ||
                            ActivityCompat.shouldShowRequestPermissionRationale
                                    (getHomeLocation.this, Manifest.permission.ACCESS_FINE_LOCATION)) {
                        Snackbar.make(findViewById(android.R.id.content),
                                "Please Grant Permissions",
                                Snackbar.LENGTH_INDEFINITE).setAction("ENABLE",
                                new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        ActivityCompat.requestPermissions(getHomeLocation.this,
                                                new String[]{Manifest.permission
                                                        .ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION},
                                                10);
                                    }
                                }).show();
                    } else {
                        ActivityCompat.requestPermissions(getHomeLocation.this,
                                new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION},
                                10);
                    }
                } else {


                    //Call whatever you want
                    PlacePicker.IntentBuilder p1 = new PlacePicker.IntentBuilder();
                    try {
                        startActivityForResult(p1.build(getHomeLocation.this), REQUEST_CODE);
                    } catch (GooglePlayServicesRepairableException e) {
                        e.printStackTrace();
                    } catch (GooglePlayServicesNotAvailableException e) {
                        e.printStackTrace();
                    }
                }

            }
        });


        //Submit Home Location
        Button submitHomeLoctn = (Button) findViewById(R.id.submitHomeLocation);
        submitHomeLoctn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getHomeLocation.this, DiaryPeek.class);
                startActivity(i);
                finish();
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                Place placePick = PlacePicker.getPlace(data, this);
                //Toast.makeText(getHomeLocation.this, "HOME LOCATION: " + placePick.getAddress(), Toast.LENGTH_LONG);
                //userHomeLocation = placePick.getAddress().toString();
                updateHomeLocationTextView = (TextView) findViewById(R.id.updateTextViewWithHomeLocation);
                updateHomeLocationTextView.setText(placePick.getAddress());

            }
        }
    }
}
