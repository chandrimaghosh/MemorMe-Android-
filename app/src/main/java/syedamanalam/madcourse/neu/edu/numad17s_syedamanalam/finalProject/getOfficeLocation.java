package syedamanalam.madcourse.neu.edu.numad17s_syedamanalam.finalProject;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;

import syedamanalam.madcourse.neu.edu.numad17s_syedamanalam.R;

public class getOfficeLocation extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener {

    public int PLACE_PICKER_REQUEST = 1;

    public TextView officeLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_office_location);
        setTitle("Office Timings");

        Button selectOfficeLoctn = (Button) findViewById(R.id.selectOfficeLocation);
        selectOfficeLoctn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ContextCompat.checkSelfPermission(getOfficeLocation.this, Manifest.permission.ACCESS_FINE_LOCATION)
                        + ContextCompat.checkSelfPermission(getOfficeLocation.this, Manifest.permission.ACCESS_COARSE_LOCATION)

                        !=

                        PackageManager.PERMISSION_GRANTED) {
                    if (ActivityCompat.shouldShowRequestPermissionRationale
                            (getOfficeLocation.this, Manifest.permission.ACCESS_COARSE_LOCATION) ||
                            ActivityCompat.shouldShowRequestPermissionRationale
                                    (getOfficeLocation.this, Manifest.permission.ACCESS_FINE_LOCATION)) {
                        Snackbar.make(findViewById(android.R.id.content),
                                "Please Grant Permissions",
                                Snackbar.LENGTH_INDEFINITE).setAction("ENABLE",
                                new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        ActivityCompat.requestPermissions(getOfficeLocation.this,
                                                new String[]{Manifest.permission
                                                        .ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION},
                                                10);
                                    }
                                }).show();
                    } else {
                        ActivityCompat.requestPermissions(getOfficeLocation.this,
                                new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION},
                                10);
                    }
                } else {
                    final PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();

                    try {
                        startActivityForResult(builder.build(getOfficeLocation.this), PLACE_PICKER_REQUEST);
                    } catch (GooglePlayServicesRepairableException e) {
                        e.printStackTrace();
                    } catch (GooglePlayServicesNotAvailableException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        Button goToOfficeTimingsScreen = (Button) findViewById(R.id.submitOfficeLocation);
        goToOfficeTimingsScreen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getOfficeLocation.this, OfficeStartTime.class);
                startActivity(i);
                finish();
            }
        });

    }//End Of OnCreate


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PLACE_PICKER_REQUEST) {
            if (resultCode == RESULT_OK) {

                if (checkConnectionToInternet()) {
                    Place p1 = PlacePicker.getPlace(data, this);
                    officeLocation = (TextView) findViewById(R.id.updateTextViewWithOfficeLocation);
                    officeLocation.setText(p1.getAddress()
//                            + "\n" + p1.getLatLng()
                    );

                    ////////////////////////////////////////////////////////////////////
                    // TO-DO: SAVE THIS p1.getAddress AS OFFICE LOCATION IN THE DATABASE
                    ////////////////////////////////////////////////////////////////////
                }
            }
        }

    }


    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Toast.makeText(this, "Some error has occurred. Please try again", Toast.LENGTH_LONG).show();
        Intent i = new Intent(this, getOfficeLocation.class);
        startActivity(i);
        finish();
    }


    //Check for Internet
    public boolean checkConnectionToInternet() {
        //Get Connectivity Manager
        ConnectivityManager cm1 = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        //Get Active Network Info
        NetworkInfo connected = cm1.getActiveNetworkInfo();
        return connected != null && connected.isConnected();
    }


}
