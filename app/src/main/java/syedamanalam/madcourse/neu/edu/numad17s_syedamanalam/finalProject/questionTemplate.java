package syedamanalam.madcourse.neu.edu.numad17s_syedamanalam.finalProject;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.IdRes;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.text.DateFormatSymbols;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import syedamanalam.madcourse.neu.edu.numad17s_syedamanalam.Main;
import syedamanalam.madcourse.neu.edu.numad17s_syedamanalam.R;

public class questionTemplate extends AppCompatActivity {
    int day1stepCounter = 0;
    int day2stepCounter = 0;
    String dayOne;
    String dayTwo;
    Date lastDriveToWorkDay1;
    Date lastDriveToWorkDay12;
    public static HashMap<Integer, String> questions = new HashMap<>();
    public static HashMap<Integer, String> answers = new HashMap<>();
    public static HashMap<String, String> options = new HashMap<>();
    public static HashMap<Integer, String[]> tags;
    public static int memorMeUserScore = 0;
    public static int memorMeMaxScore = 0;
    RadioButton rb1;
    RadioButton rb2;
    TextView questionText;
    SharedPreferences preferences;

    // Setting a String varibale which will get the
    // text of the selected radio button as answer
    // The text is then checked against the answer from hashmap
    public static String selectedRadioButtonText = "";
    public static int tallyOfQuestions = 3;
    public static int questionNumber = 0;
    public static double extraMilesWalked = 1.2;
    public HashMap<String, Integer> dayNumber = new HashMap<>();
    public HashMap<Integer, String> hintTextHashMap = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question_template);
        setTitle("Question");
        selectedRadioButtonText="";
        tags = new HashMap<Integer, String[]>();

        //////////////////////////////////////////////////////////////////////////////////////////
        //
        // We have a variable tallyOfQuestion which is basically the total question templates
        // For now it is set to 3
        //
        // We have another variable called questionNumber
        // Initially question number is 0
        // So we will pick up the 0th value from the questions, answers, options and tags hashmap
        // In the end when user submits the answer, we will validate the answer
        // and send an intent to the specialNoteScreen
        // In the end of special note screen, when user pressed next question
        // An intent is sent back to question template activity
        // with an intent Extra with updated question number
        //
        // Afterwards when the tally of question is complete
        // we just make the next question button "GONE"
        // And the publish story button visible
        // This way, when we add new question templates, we just have to update the tallyOfQuestions
        //
        //////////////////////////////////////////////////////////////////////////////////////////


        //Get the extras from intent ie updated question
        Bundle b = getIntent().getExtras();
        if (b != null) {
            questionNumber = b.getInt("currentQuestionNumber");
        }


        //Add Question here to the Text View
        questionText = (TextView) findViewById(R.id.questionSpace);


        //Add options for the question here for the two radio buttons
        //radioButtonDay1 and radioButtonDay2 are their IDs
        rb1 = (RadioButton) findViewById(R.id.radioButtonDay1);
        rb2 = (RadioButton) findViewById(R.id.radioButtonDay2);


        RadioGroup radioButtonGroup = (RadioGroup) findViewById(R.id.radioButtonGroup);
        radioButtonGroup.setOnClickListener(new RadioGroup.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Toast.makeText(questionTemplate.this, "Wont crash now", Toast.LENGTH_LONG).show();
            }
        });

        radioButtonGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, @IdRes int i) {
                int radioButtonId = radioGroup.getCheckedRadioButtonId();
                View radioButton = radioGroup.findViewById(radioButtonId);
                int indexButton = radioGroup.indexOfChild(radioButton);

                RadioButton rbSelected = (RadioButton) radioGroup.getChildAt(indexButton);
                selectedRadioButtonText = rbSelected.getText().toString();
//                Toast.makeText(questionTemplate.this,selectedRadioButtonText,Toast.LENGTH_LONG).show();

            }
        });


        MemorMeDataHandler db = new MemorMeDataHandler(this);
        SimpleDateFormat s = new SimpleDateFormat("ddMMyyyyhhmmss");
        String format = s.format(new Date());


        Log.d("Reading: ", "Reading all Logs.");
        List<ActivityStamp> activityStamps = db.getAllContacts();

        dayOne = activityStamps.get(0).activityTimeStamp.substring(0, 1);

        dayTwo = activityStamps.get(activityStamps.size() - 1).activityTimeStamp.substring(0, 1);
        Log.d("Reading days: ", dayOne + dayTwo);


        for (ActivityStamp cn : activityStamps) {
            String log = "Id: " + cn.getId() + " ,Name: " + cn.getActivityDetail() + " ,Phone: " + cn.getActivityTimeStamp() + "location" + cn.getLocationCloseTo();
            // Writing Contacts to log

            options.put(dayFromDate(cn.getActivityTimeStamp()), dayFromDate(cn.getActivityTimeStamp()));


            //counting steps
            if (cn.getActivityDetail().equalsIgnoreCase("Walk")) {
                countSteps(cn.getActivityDetail().substring(0, 1));
            }
            if (cn.getActivityDetail().equalsIgnoreCase("InVehicle") && cn.getLocationCloseTo().equalsIgnoreCase("office") &&
                    isDay(cn.getActivityTimeStamp())) {
                countSteps(cn.getActivityDetail().substring(0, 1));
            }
        }


        int key = 0;


        questions.put(0, "You walked more steps on:");
        hintTextHashMap.put(0, "You walked" + extraMilesWalked + " miles more on this day");
//        Toast.makeText(this,questions.get(1),Toast.LENGTH_LONG).show();

        questions.put(1, "You reached work earlier on:");
        hintTextHashMap.put(1, " You woke up quite early this day");

        questions.put(2, "You travelled Longer in a vehicle on:");
        hintTextHashMap.put(2, "You reached home quite late this night");

        String[] walkedMore = {"Walked My Dog", "Walked To The Grocery Store", "Took Out Lunch", "Walked On The Treadmill", "Played With Daughter", "Other"};
        String[] earlyToOfficeMore = {"Avoided Traffic", " Attended Team Meeting", "Other"};
        String[] vehicleMore = {"Visited  A Friend", "Took A Trip To Grocery Store", "Dropped  My Daughter To School", "Saw The Doctor", "Took The Train", "Other"};
        tags.put(0, walkedMore);
        tags.put(1, earlyToOfficeMore);
        tags.put(2, vehicleMore);

        if (day1stepCounter > day2stepCounter) {
            answers.put(0, dayFromDate(activityStamps.get(0).activityTimeStamp));
        } else {
            answers.put(0, dayFromDate(activityStamps.get(activityStamps.size() - 1).activityTimeStamp));
        }
        answers.put(1, "Thursday");
        answers.put(2, "Wednesday");


        initView();

    }


    private void initView() {
        if (questionNumber == 0) {
            resetScore();
        }

        questionText.setText(questions.get(questionNumber));
        String value1 = (new ArrayList<String>(options.values())).get(0);
        String value2 = (new ArrayList<String>(options.values())).get(1);

        //Set the options on the radio button
        rb1.setText(value1);
        rb2.setText(value2);


        //Set the hint Text here
        String hintText = hintTextHashMap.get(questionNumber);

        AlertDialog.Builder hintPopup = new AlertDialog.Builder(this);
        hintPopup.setMessage("Hint: " + hintText);
        hintPopup.setCancelable(true);
        hintPopup.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                //Do nothing here
            }
        });

        final AlertDialog customAlertBox = hintPopup.create();

        //Set a popup Dialog box on hint button click
        Button hintButton = (Button) findViewById(R.id.hintButton);
        hintButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                customAlertBox.show();
            }
        });


        Button submitAnswer = (Button) findViewById(R.id.submitAnswerButton);
        submitAnswer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //check the answer
                //Keep a track of score
                //memorMeScore

                // If no button is selected
                if (selectedRadioButtonText.equals("")) {
                    final Snackbar sb1 = Snackbar.make(getWindow().getDecorView().getRootView(),"Please select an option first",Snackbar.LENGTH_LONG);
                    View view1 = sb1.getView();
                    FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) view1.getLayoutParams();
                    params.gravity = Gravity.CENTER_VERTICAL;

                    sb1.setAction("Dismiss", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            sb1.dismiss();
                        }
                    });

                    sb1.show();
                } else {
                    if (selectedRadioButtonText.equalsIgnoreCase(answers.get(questionNumber))) {
                        memorMeUserScore = memorMeUserScore + 4;
                        memorMeMaxScore = memorMeMaxScore + 4;
                        preferences = PreferenceManager.getDefaultSharedPreferences(questionTemplate.this);
                        SharedPreferences.Editor editor = preferences.edit();
                        editor.putInt("Player1Score", memorMeUserScore);
                        editor.putInt("TotalScore", memorMeMaxScore);
                        editor.apply();

                        //Take me to special note Activity Screen with correct answer
                        Intent specialNoteActivityIntent = new Intent(questionTemplate.this, specialNoteScreen.class);
                        specialNoteActivityIntent.putExtra("currentQuestionNumber", questionNumber);
                        specialNoteActivityIntent.putExtra("Result", "correct");
                        startActivity(specialNoteActivityIntent);
                        finish();
                    } else {
                        //Just increase total score and not user score

                        memorMeMaxScore = memorMeMaxScore + 4;
                        preferences = PreferenceManager.getDefaultSharedPreferences(questionTemplate.this);
                        SharedPreferences.Editor editor = preferences.edit();
                        editor.putInt("TotalScore", memorMeMaxScore);
                        editor.apply();

                        //Take me to special note Activity Screen
                        Intent specialNoteActivityIntent = new Intent(questionTemplate.this, specialNoteScreen.class);
                        specialNoteActivityIntent.putExtra("currentQuestionNumber", questionNumber);
                        specialNoteActivityIntent.putExtra("Result", "wrong");
                        startActivity(specialNoteActivityIntent);
                        finish();
                    }
                }
            }
        });

    }

    private void resetScore() {
        memorMeMaxScore = 0;
        memorMeUserScore = 0;
    }

    public static void printMap(Map mp) {
        Iterator it = mp.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry) it.next();
            System.out.println(pair.getKey() + " = " + pair.getValue());
            it.remove(); // avoids a ConcurrentModificationException
        }
    }


    public static String dayFromDate(String activitydate) {
        Date activityDate;
        Calendar c = Calendar.getInstance();
        int dayOfWeek = 0;
        String[] namesOfDays = DateFormatSymbols.getInstance().getWeekdays();
        SimpleDateFormat s = new SimpleDateFormat("ddMMyyyyhhmm");
        try {
            activityDate = s.parse(activitydate);
            c.setTime(activityDate);
            dayOfWeek = c.get(Calendar.DAY_OF_WEEK);
            System.out.println(dayOfWeek);
            System.out.println(namesOfDays[dayOfWeek]);


        } catch (ParseException e) {
            e.printStackTrace();
        }
        return namesOfDays[dayOfWeek];
    }

    public void countSteps(String day) {
        if (dayOne.equalsIgnoreCase(day)) {
            day1stepCounter = day1stepCounter++;
        } else {
            day2stepCounter = day2stepCounter++;
        }
    }

    public void countOfficeReached(String day, Date date) {
//        if(dayOne.equalsIgnoreCase(day))
//        {
//            if(date1.compareTo(date2))
//            {
//
//            lastDriveToWorkDay1=date;}
//        }
//        else{
//            lastDriveToWorkDay12=date;
//        }
    }

    public Boolean isDay(String activity) {
        String morn = activity.substring(8, 9);
        int mornInt = Integer.parseInt(morn);
        if (mornInt > 12) {
            return false;
        } else {
            return true;
        }

    }


    public void onBackPressed() {
        AlertDialog.Builder b1 = new AlertDialog.Builder(questionTemplate.this);
        b1.setMessage("Are you sure you want to Quit?");
        b1.setCancelable(false);

        b1.setPositiveButton("Yes",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Intent k = new Intent(questionTemplate.this, Main.class);
                        startActivity(k);
                        finish();
                    }
                });

        b1.setNegativeButton("No",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //Do Nothing
                    }
                });

        AlertDialog customAlertBox = b1.create();
        customAlertBox.show();
    }




}