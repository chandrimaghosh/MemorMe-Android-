package syedamanalam.madcourse.neu.edu.numad17s_syedamanalam.finalProject;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.ActivityRecognition;

import syedamanalam.madcourse.neu.edu.numad17s_syedamanalam.R;
import syedamanalam.madcourse.neu.edu.numad17s_syedamanalam.trickiestPart.ActivityRecognizedService;
import syedamanalam.madcourse.neu.edu.numad17s_syedamanalam.trickiestPart.activityRecoAck;

public class UserActivityDetection extends AppCompatActivity  implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    public GoogleApiClient mApiClient;
    public static String typeOfActivity=" ";
    public static Vibrator v1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_detection);
        setTitle("Trickiest Part");

        mApiClient = new GoogleApiClient.Builder(this)
                .addApi(ActivityRecognition.API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();

        mApiClient.connect();


        Button activityShowView= (Button) findViewById(R.id.activityShow);
        activityShowView.setText("CHECK NOTIFICATIONS");

        Button activityRecogAck = (Button) findViewById(R.id.activityAck);
        activityRecogAck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(UserActivityDetection.this, activityRecoAck.class);
                startActivity(i);
                finish();
            }
        });


        v1 = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        Intent intent = new Intent( this, ActivityRecognizedService.class );
        PendingIntent pendingIntent = PendingIntent.getService( this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT );
        ActivityRecognition.ActivityRecognitionApi.requestActivityUpdates( mApiClient, 1000, pendingIntent );
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
}
