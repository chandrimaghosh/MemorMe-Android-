package syedamanalam.madcourse.neu.edu.numad17s_syedamanalam.finalProject;

import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Vibrator;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import syedamanalam.madcourse.neu.edu.numad17s_syedamanalam.R;

import static syedamanalam.madcourse.neu.edu.numad17s_syedamanalam.finalProject.MapsActivity.userHomeLocation;
import static syedamanalam.madcourse.neu.edu.numad17s_syedamanalam.finalProject.MapsActivity.userOfficeLocation;
import static syedamanalam.madcourse.neu.edu.numad17s_syedamanalam.finalProject.OfficeEndTime.endOfficeHour;
import static syedamanalam.madcourse.neu.edu.numad17s_syedamanalam.finalProject.OfficeEndTime.endOfficeMin;
import static syedamanalam.madcourse.neu.edu.numad17s_syedamanalam.finalProject.OfficeStartTime.startOfficeHour;
import static syedamanalam.madcourse.neu.edu.numad17s_syedamanalam.finalProject.OfficeStartTime.startOfficeMin;
import static syedamanalam.madcourse.neu.edu.numad17s_syedamanalam.finalProject.getUserName.userNameFromEditTextBox;

public class prefaceDiary extends AppCompatActivity {

    //This variable corresponds to the waiting time.
    // If the variable is zero, user can start the game.
    // Otherwise waiting screen is shown
    public Vibrator vb1;
    SharedPreferences preferences;


    public Bundle bn;
    public String[] leftRight = new String[20];
    MemorMeDataHandler db=new MemorMeDataHandler(this);
    //The number of templates we have for stories
    public static int numOfQuestions = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preface_diary);
        setTitle("Preface");
        TextView diaryTitle=(TextView)findViewById(R.id.diaryTitle);
//        vb1.vibrate(300);



//        "selectedTagsByUser" Array List contains tags selected by user
//        Use it in story creation

        int count=0;

        //Get the Question and the tags selected by user
        if ((bn = getIntent().getExtras()) != null) {
            if (bn.getStringArrayList("newStoryTag") != null) {
                for (String s : bn.getStringArrayList("newStoryTag")) {
                    leftRight = s.split("\\?");
//                    System.out.print(leftRight[count++]);
                }
            }
        }


//        Adding text to the Page
        TextView contentOfPreface = (TextView) findViewById(R.id.prefaceStoryTextView);


        preferences = PreferenceManager.getDefaultSharedPreferences(prefaceDiary.this);
        int storyCount=preferences.getInt("StoryCount",0);
        if(storyCount>0)
        {
            diaryTitle.setText("3rd week of april");
        }

        //Retrieve Intent and set question accordingly
        Bundle b = getIntent().getExtras();
        if (b.getInt("a") == 0) {


            int userCount=db.getUserClassCount();

            if(userCount>=1){


            //A sample preface
            contentOfPreface.setText("Hi, I am " + userNameFromEditTextBox +
                    ". I am usually a busy bee from " + startOfficeHour + ":" + startOfficeMin +
                    " to " + endOfficeHour + ":" + endOfficeMin +
                    " pursuing my passion at " + userOfficeLocation +
                    ". \n My humble abode is " + userHomeLocation +
                    " where you are always welcome........");

            }

        } else {
            String story=preferences.getString("StorySoFar",null);
            contentOfPreface.setText(story);
        }


        //Changing Fonts
        Typeface type = Typeface.createFromAsset(getAssets(), "fonts/la_belle_aurore.ttf");
        contentOfPreface.setTypeface(type);

        /*
        Button startGameOrSendToWaitingScreen = (Button) findViewById(R.id.startMemorMeButton);
        startGameOrSendToWaitingScreen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //Send an intent to start memory game
                if (globalWaitingTime != 0) {
                    //Means we do not have enough data for the game
                    //Send an intent to waiting screen in such a case
                    Intent wait = new Intent(prefaceDiary.this, loadingScreenBeforeMemoryGame.class);
                    startActivity(wait);
                } else if (globalWaitingTime == 0) {
                    //Send an intent to start the memory game
                    Intent startMemorMe = new Intent(prefaceDiary.this, questionTemplate.class);
                    startActivity(startMemorMe);
                }
            }
        });
        */


    }// End of onCreate

}
