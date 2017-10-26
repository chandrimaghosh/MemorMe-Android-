package syedamanalam.madcourse.neu.edu.numad17s_syedamanalam.finalProject;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import java.text.SimpleDateFormat;
import java.util.Date;

import syedamanalam.madcourse.neu.edu.numad17s_syedamanalam.R;

public class MainScreenAfterAnimation extends AppCompatActivity {
    MemorMeDataHandler db;
    SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_screen_after_animation);
        setTitle("Play Memor-Me");
        Button play = (Button) findViewById(R.id.mainplayButton);
        db = new MemorMeDataHandler(this);
        Button playw = (Button) findViewById(R.id.viewDataButton);
        playw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Intent i =new Intent(MainScreenAfterAnimation.this,DisplayRealtimeActivity.class);
                startActivity(i);
            }
            });



        play.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {

                                        SimpleDateFormat s = new SimpleDateFormat("ddMMyyyyhhmmss");
                                        String format = s.format(new Date());

                                        Log.d("Insert: ", "Inserting ..");
                                        // 2 days of sample data
                                        db.addActicityStamp(new ActivityStamp("InVehicle", "200420171745", "home", 100));
                                        db.addActicityStamp(new ActivityStamp("InVehicle", "200420171730", "unknow", 100));
                                        db.addActicityStamp(new ActivityStamp("InVehicle", "200420171715", "office", 100));
                                        db.addActicityStamp(new ActivityStamp("walk", "200420171701", "office", 100));
                                        db.addActicityStamp(new ActivityStamp("Walk", "200420171045", "office", 100));
                                        db.addActicityStamp(new ActivityStamp("still", "200420171030", "office", 100));
                                        db.addActicityStamp(new ActivityStamp("Walk", "200420171015", "office", 100));
                                        db.addActicityStamp(new ActivityStamp("Walk", "200420171001", "office", 100));
                                        db.addActicityStamp(new ActivityStamp("InVehicle", "200420170935", "office", 100));//earlier to work
                                        db.addActicityStamp(new ActivityStamp("InVehicle", "200420170930", "unknown", 100));
                                        db.addActicityStamp(new ActivityStamp("InVehicle", "200420170915", "home", 100));
                                        db.addActicityStamp(new ActivityStamp("InVehicle", "200420170901", "home", 100));
                                        db.addActicityStamp(new ActivityStamp("Tilt", "200420170845", "home", 100));
                                        db.addActicityStamp(new ActivityStamp("Still", "200420170830", "home", 100));
                                        db.addActicityStamp(new ActivityStamp("Walk", "200420170815", "home", 100));
                                        db.addActicityStamp(new ActivityStamp("Tilt", "200420170801", "home", 100));
                                        db.addActicityStamp(new ActivityStamp("Still", "200420170745", "home", 100));
                                        db.addActicityStamp(new ActivityStamp("Still", "200420170730", "home", 100));
                                        db.addActicityStamp(new ActivityStamp("Still", "200420170715", "home", 100));
                                        db.addActicityStamp(new ActivityStamp("Still", "200420170701", "home", 100));
                                        db.addActicityStamp(new ActivityStamp("InVehicle", "190420171745", "home", 100));
                                        db.addActicityStamp(new ActivityStamp("InVehicle", "190420171730", "unknow", 100));
                                        db.addActicityStamp(new ActivityStamp("InVehicle", "190420171715", "office", 100));
                                        db.addActicityStamp(new ActivityStamp("walk", "190420171701", "office", 100));
                                        db.addActicityStamp(new ActivityStamp("still", "190420171045", "office", 100));
                                        db.addActicityStamp(new ActivityStamp("still", "190420171030", "office", 100));
                                        db.addActicityStamp(new ActivityStamp("still", "190420171015", "office", 100));
                                        db.addActicityStamp(new ActivityStamp("walk", "190420171001", "office", 100));
                                        db.addActicityStamp(new ActivityStamp("InVehicle", "190420170945", "office", 100));//late to work
                                        db.addActicityStamp(new ActivityStamp("InVehicle", "190420170930", "unknown", 100));
                                        db.addActicityStamp(new ActivityStamp("InVehicle", "190420170915", "home", 100));
                                        db.addActicityStamp(new ActivityStamp("InVehicle", "190420170901", "home", 100));
                                        db.addActicityStamp(new ActivityStamp("Tilt", "190420170845", "home", 100));
                                        db.addActicityStamp(new ActivityStamp("Still", "190420170830", "home", 100));
                                        db.addActicityStamp(new ActivityStamp("Walk", "190420170815", "home", 100));
                                        db.addActicityStamp(new ActivityStamp("Tilt", "190420170801", "home", 100));
                                        db.addActicityStamp(new ActivityStamp("Still", "190420170745", "home", 100));
                                        db.addActicityStamp(new ActivityStamp("Still", "190420170730", "home", 100));
                                        db.addActicityStamp(new ActivityStamp("Still", "190420170715", "home", 100));
                                        db.addActicityStamp(new ActivityStamp("Still", "190420170701", "home", 100));

                                        Log.d("Insert: ", "Inserting completed..");
                                        System.out.println(db.getActivityStampCount());
                                        preferences = PreferenceManager.getDefaultSharedPreferences(MainScreenAfterAnimation.this);
                                        SharedPreferences.Editor editor = preferences.edit();
                                        editor.putString("StorySoFar", "Dear Diary, ");
                                        editor.apply();
                                        Intent i = new Intent(MainScreenAfterAnimation.this, questionTemplate.class);
                                        i.putExtra("currentQuestionNumber", 0);
                                        startActivity(i);
                                        finish();
                                    }
                                }
        );
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

}
