package syedamanalam.madcourse.neu.edu.numad17s_syedamanalam.finalProject;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.ActivityRecognition;

import java.util.ArrayList;
import java.util.List;

import syedamanalam.madcourse.neu.edu.numad17s_syedamanalam.R;

import static syedamanalam.madcourse.neu.edu.numad17s_syedamanalam.finalProject.MapsActivity.userHomeLocation;
import static syedamanalam.madcourse.neu.edu.numad17s_syedamanalam.finalProject.MapsActivity.userOfficeLocation;
import static syedamanalam.madcourse.neu.edu.numad17s_syedamanalam.finalProject.OfficeEndTime.endOfficeHour;
import static syedamanalam.madcourse.neu.edu.numad17s_syedamanalam.finalProject.OfficeStartTime.startOfficeHour;
import static syedamanalam.madcourse.neu.edu.numad17s_syedamanalam.finalProject.getUserName.userNameFromEditTextBox;


public class animation extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    public GoogleApiClient mApiClient;
    private SensorManager mSensorManager;
    private float mAccel;
    private float mAccelCurrent;
    private float mAccelLast;
    private boolean isPhoneShaked = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animation);
        setTitle("Tutorial");

        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        mSensorManager.registerListener(mSensorListener,
                mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
                SensorManager.SENSOR_DELAY_NORMAL);

        Button instruction = (Button) findViewById(R.id.instructionButton);
        instruction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(animation.this,Instruc.class);
                startActivity(i);
                finish();
            }
        });

        mAccel = 0.00f;
        mAccelCurrent = SensorManager.GRAVITY_EARTH;
        mAccelLast = SensorManager.GRAVITY_EARTH;

        mApiClient = new GoogleApiClient.Builder(this)
                .addApi(ActivityRecognition.API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();

        mApiClient.connect();
        MemorMeDataHandler db = new MemorMeDataHandler(this);
        List<UserClass> userList = new ArrayList<UserClass>();
        userList = db.getAllUsers();
        if (userList.size() < 1) {
            UserClass user = new UserClass(userNameFromEditTextBox, userOfficeLocation, userHomeLocation, startOfficeHour, endOfficeHour);
            db.addUser(user);

        }
        Button realTime = (Button) findViewById(R.id.realTimeDataButton);
        realTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent wait = new Intent(animation.this, DisplayRealtimeActivity.class);
                startActivity(wait);
                finish();

            }
        });


        Button playMemor = (Button) findViewById(R.id.playMemorMeAnimationButton);
        playMemor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Send an intent to start memory game
                //Means we do not have enough data for the game
                //Send an intent to start the memory game
                Intent startMemorMe = new Intent(animation.this, questionTemplate.class);
                startActivity(startMemorMe);
                finish();

            }
        });// End of onClickListener

    }//End of onCreate


    private final SensorEventListener mSensorListener = new SensorEventListener() {
        public void onSensorChanged(SensorEvent se) {
            float x = se.values[0];
            float y = se.values[1];
            float z = se.values[2];
            mAccelLast = mAccelCurrent;
            mAccelCurrent = (float) Math.sqrt((double) (x * x + y * y + z * z));
            float delta = mAccelCurrent - mAccelLast;
            mAccel = mAccel * 0.99f + delta;
            if (mAccel > 28) {
                isPhoneShaked = true;
                bringInstructionsOnShake();
                setIsShaked(false);
            }
        }

        public void onAccuracyChanged(Sensor sensor, int accuracy) {
        }
    };

    private void bringInstructionsOnShake() {

//      Toast.makeText(this,"shook",Toast.LENGTH_LONG).show();
        String stringToDisplay = "A sample Memor-Me game play";
        final Snackbar snack = Snackbar.make(findViewById(android.R.id.content), stringToDisplay, Snackbar.LENGTH_LONG);
        View view1 = snack.getView();
        FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) view1.getLayoutParams();
        params.gravity = Gravity.CENTER_VERTICAL;


        snack.setCallback(new Snackbar.Callback() {

            @Override
            public void onDismissed(Snackbar snackbar, int event) {
                if (event == Snackbar.Callback.DISMISS_EVENT_TIMEOUT) {
                    // When Snack Bar closes, show the alert box
                    instructionGifShow();
                }
            }

            @Override
            public void onShown(Snackbar snackbar) {
            }
        });

        snack.show();
    }

    public boolean isPhoneShaked() {
        return this.isPhoneShaked;
    }

    public void setIsShaked(boolean isShaked) {
        isPhoneShaked = isShaked;
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        Intent intent = new Intent(this, syedamanalam.madcourse.neu.edu.numad17s_syedamanalam.finalProject.ActivityRecognizedService.class);
        PendingIntent pendingIntent = PendingIntent.getService(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        ActivityRecognition.ActivityRecognitionApi.requestActivityUpdates(mApiClient, 1000, pendingIntent);
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }


    //For Action Bar Menu
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    //On clicking on the item in the drawer
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.myDiarySpinner:
                startMyDiaryFromSpinner();
                break;

            //Add future buttons here and switch based on itemId

        }
        return true;
    }

    public void startMyDiaryFromSpinner() {
        Intent i = new Intent(this, indexOfDiary.class);
        startActivity(i);
        finish();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mSensorManager.unregisterListener(mSensorListener);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mSensorManager.registerListener(mSensorListener,
                mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
                SensorManager.SENSOR_DELAY_NORMAL);
    }


    public void instructionGifShow() {
        Intent i = new Intent(this, Instruc.class);
        startActivity(i);
        finish();
    }

}
