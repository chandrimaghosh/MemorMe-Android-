package syedamanalam.madcourse.neu.edu.numad17s_syedamanalam.finalProject;

import android.app.IntentService;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v7.app.NotificationCompat;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.location.ActivityRecognitionResult;
import com.google.android.gms.location.DetectedActivity;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import syedamanalam.madcourse.neu.edu.numad17s_syedamanalam.R;

import static syedamanalam.madcourse.neu.edu.numad17s_syedamanalam.trickiestPart.UserActivityDetection.typeOfActivity;
import static syedamanalam.madcourse.neu.edu.numad17s_syedamanalam.trickiestPart.UserActivityDetection.v1;


public class ActivityRecognizedService extends IntentService {


    public static int globalTiltCountForRestlessNight = 0;
    public static int globalTiltCountForAfterOfficeExercise = 0;
    public static int globalStillCountForLateSleep = 0;
    public static int globalFootCountForOffice = 0;

    //Get Date, Hours and Minutes for all checks
    Date d1 = new Date();

    //Uses 24 hour format
    int getHours = d1.getHours();
    int getMinutes = d1.getMinutes();
    int getDay = d1.getDay();
    MemorMeDataHandler db = new MemorMeDataHandler(this);

    SimpleDateFormat s = new SimpleDateFormat("ddMMyyyyhhmm");
    String format = s.format(new Date());


    public static HashMap<String, Boolean> questionsHashMap = new HashMap<>();

    public ActivityRecognizedService() {
        super("ActivityRecognizedService");
    }

    public ActivityRecognizedService(String name) {
        super(name);
    }


    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onHandleIntent(Intent intent) {
        if (ActivityRecognitionResult.hasResult(intent)) {
            ActivityRecognitionResult result = ActivityRecognitionResult.extractResult(intent);
            handleDetectedActivities(result.getProbableActivities());
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void handleDetectedActivities(List<DetectedActivity> probableActivities) {

        for (DetectedActivity activity : probableActivities) {
            switch (activity.getType()) {

                case DetectedActivity.IN_VEHICLE: {

                    db.addActicityStampReal(new ActivityStamp("InVehicle",format,"home",activity.getConfidence()));

                    break;
                }
                case DetectedActivity.ON_BICYCLE: {
                    db.addActicityStampReal(new ActivityStamp("ONBICYCLE",format,"home",activity.getConfidence()));
                    break;
                }


                case DetectedActivity.ON_FOOT: {

                    db.addActicityStampReal(new ActivityStamp("ONFOOT",format,"home",activity.getConfidence()));


                    break;
                }


                case DetectedActivity.RUNNING: {
                    db.addActicityStampReal(new ActivityStamp("RUNNING",format,"home",activity.getConfidence()));

                    break;
                }


                case DetectedActivity.STILL: {

                    db.addActicityStampReal(new ActivityStamp("STILL",format,"home",activity.getConfidence()));


                    break;
                }


                case DetectedActivity.TILTING: {
                    db.addActicityStampReal(new ActivityStamp("TILTING",format,"home",activity.getConfidence()));
                    break;
                }//END OF TILTING CASE


                case DetectedActivity.WALKING: {

                    db.addActicityStampReal(new ActivityStamp("WALKING","200420171745","home",activity.getConfidence()));
                    break;
                }


                case DetectedActivity.UNKNOWN: {

                    break;
                }

            }
        }//End of For Loop


    }//End of handleDetectedActivities


}// End Of Class