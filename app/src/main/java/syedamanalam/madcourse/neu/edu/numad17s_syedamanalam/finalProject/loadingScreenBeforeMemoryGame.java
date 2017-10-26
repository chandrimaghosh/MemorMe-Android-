package syedamanalam.madcourse.neu.edu.numad17s_syedamanalam.finalProject;

import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.ActivityRecognition;

import syedamanalam.madcourse.neu.edu.numad17s_syedamanalam.R;
import syedamanalam.madcourse.neu.edu.numad17s_syedamanalam.trickiestPart.*;

public class loadingScreenBeforeMemoryGame extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {
    public GoogleApiClient mApiClient;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading_screen_before_memory_game);

        TextView addWaitingText = (TextView) findViewById(R.id.waitingScreenText);
        //Add the variable globalWaitingTime here which will be an estimated waiting time
        //globalWaitingTime
        addWaitingText.setText("Preparing personal stories for your diary" +
                "Be Prepared for a memory game first\n" +
                //Add the variable here
                // globalWaitingTime +
                "Days left for Memory game");


        mApiClient = new GoogleApiClient.Builder(this)
                .addApi(ActivityRecognition.API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();

        mApiClient.connect();
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        Intent intent = new Intent( this, syedamanalam.madcourse.neu.edu.numad17s_syedamanalam.trickiestPart.ActivityRecognizedService.class );
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