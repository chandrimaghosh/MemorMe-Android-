package syedamanalam.madcourse.neu.edu.numad17s_syedamanalam.finalProject;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.os.Vibrator;
import android.preference.PreferenceManager;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;

import syedamanalam.madcourse.neu.edu.numad17s_syedamanalam.Main;
import syedamanalam.madcourse.neu.edu.numad17s_syedamanalam.R;

import static syedamanalam.madcourse.neu.edu.numad17s_syedamanalam.finalProject.questionTemplate.answers;
import static syedamanalam.madcourse.neu.edu.numad17s_syedamanalam.finalProject.questionTemplate.memorMeMaxScore;
import static syedamanalam.madcourse.neu.edu.numad17s_syedamanalam.finalProject.questionTemplate.questionNumber;
import static syedamanalam.madcourse.neu.edu.numad17s_syedamanalam.finalProject.questionTemplate.tags;

public class specialNoteScreen extends AppCompatActivity {

    public static int countOfStory = 0;
    ArrayList tagsForQuestions;
    public Vibrator vb1;
    public static ArrayList<String> selectedTagsByUser = new ArrayList<>();
    SharedPreferences preferences;
    HashMap<Integer, String> activityName;
    public String textFromEditText = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_special_note_screen);
        setTitle("Special Note");

        activityName = new LinkedHashMap<>();

        //Initialize Vibrator
        vb1 = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);

        TextView resultTxt = (TextView) findViewById(R.id.resultTextView);

        // Get the extras from intent and later on send an intent with updated question
        Bundle b = getIntent().getExtras();
//        int question = b.getInt("currentQuestionNumber");
        String result = b.getString("Result");


        //If the answer is correct, setText to Correct else set it to wrong answer
        if (result.equalsIgnoreCase("correct")) {
            resultTxt.setText("CORRECT ANSWER");
        } else {
            resultTxt.setText("WRONG ANSWER");
        }

        activityName.put(0, "Walked more");
        activityName.put(1, "went early to work");
        activityName.put(2, "travelled more");

        //Set the text for special note based on question
        TextView specialNote = (TextView) findViewById(R.id.specialNoteText);
        specialNote.setText("Can you account for " + activityName.get(questionNumber));

        //USING AN ARRAY LIST AND GRID ADAPTER TO CREATE A RECYCLER VIEW
        //Make an ArrayList Of Tags and set it to the GridLayout as in Table
        tagsForQuestions = new ArrayList<>();
        for (String s : tags.get(questionNumber)) {
            tagsForQuestions.add(s);
        }


        final GridView tableForTags = (GridView) findViewById(R.id.tags);
        //Make a custom list view to display it simple_custom_list_grid_view
        tableForTags.setAdapter(new ArrayAdapter<>(this, R.layout.simple_custom_list_grid_view, tagsForQuestions));

        final AlertDialog.Builder b1 = new AlertDialog.Builder(specialNoteScreen.this);
        b1.setMessage("Please enter the reason ");
        b1.setCancelable(false);
        final EditText textBoxInAlert = new EditText(this);
        textBoxInAlert.setHint("Enter specific Reason");
        b1.setView(textBoxInAlert);

        b1.setPositiveButton("Yes",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        textFromEditText = textBoxInAlert.getText().toString();
                        selectedTagsByUser.add(textFromEditText);
//                        Snackbar.make(getWindow().getDecorView(),textFromEditText,Snackbar.LENGTH_LONG).show();
                    }
                });

        b1.setNegativeButton("No",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //Do Nothing
                    }
                });


        final AlertDialog customAlertBox = b1.create();

        //Now making this gridView clickable
        tableForTags.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //Vibrate on touch
                vb1.vibrate(200);

                //Change color of selected item in the grid view
                tableForTags.getChildAt(i).setBackgroundColor(getResources().getColor(R.color.blue_color));
                int a = getResources().getColor(R.color.gray_color);
                if (tableForTags.getChildAt(i).getDrawingCacheBackgroundColor() == a) {
//                Snackbar.make(getWindow().getDecorView(), "aaaaaaaaaaaaaaaaa177", Snackbar.LENGTH_LONG).show();
                    tableForTags.getChildAt(i).setBackgroundResource(R.color.gray_color);
                }
                tableForTags.getChildAt(i).setDrawingCacheBackgroundColor(getResources().getColor(R.color.gray_color));

                //Add the question and the tag in a static array list for story generation



                if (tagsForQuestions.get(i).toString().equals("Other")) {


                    customAlertBox.show();
                }
                else{
                    selectedTagsByUser.add(tagsForQuestions.get(i).toString());

                }
            }
        });


        LinearLayout publishAddStoryButtonV = (LinearLayout) findViewById(R.id.puslishAddStoryButton);

        Button nextQuestion = (Button) findViewById(R.id.nextQuestionButton);
        nextQuestion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                buildStory();
                selectedTagsByUser.clear();
                Intent i = new Intent(specialNoteScreen.this, questionTemplate.class);
                i.putExtra("currentQuestionNumber", ++questionNumber);
                startActivity(i);
                finish();
            }
        });

        //Reload the question screen with new question
        if (memorMeMaxScore >= 12) {
            //Make the Publish story buttons visible
            publishAddStoryButtonV.setVisibility(View.VISIBLE);

            //Make the next question button "GONE"
            nextQuestion.setVisibility(View.GONE);
        }

        preferences = PreferenceManager.getDefaultSharedPreferences(specialNoteScreen.this);
        Button publishStoryButton = (Button) findViewById(R.id.publishStory);
        publishStoryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                buildStory();
                SharedPreferences.Editor editor = preferences.edit();
                editor.putInt("StoryCount", 1);
                editor.apply();
                selectedTagsByUser.clear();
                //Take me to score page and show me the button for story
                Intent addStory = new Intent(specialNoteScreen.this, indexOfDiary.class);
                addStory.putExtra("countOfStory", countOfStory++);
                addStory.putStringArrayListExtra("storyTags", selectedTagsByUser);
                startActivity(addStory);
                finish();
            }
        });

        Button skipStoryButton = (Button) findViewById(R.id.skipPublishing);
        final Button viewStoryScoreScreenButton = (Button) findViewById(R.id.viewStoryScoreScreen);

        skipStoryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                buildStory();
                SharedPreferences.Editor editor = preferences.edit();
                editor.putInt("StoryCount", 1);
                editor.apply();
                selectedTagsByUser.clear();
                //Just show the high score screen
                Intent memorMeScorePage = new Intent(specialNoteScreen.this, scoreOfMemorMe.class);
                startActivity(memorMeScorePage);
                finish();
            }
        });


    }//End onCreate


    public void onBackPressed() {
        AlertDialog.Builder b1 = new AlertDialog.Builder(specialNoteScreen.this);
        b1.setMessage("Are you sure you want to Quit?");
        b1.setCancelable(false);

        b1.setPositiveButton("Yes",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Intent k = new Intent(specialNoteScreen.this, Main.class);
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


    /*
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
        Intent addStory = new Intent(specialNoteScreen.this, indexOfDiary.class);
        addStory.putExtra("countOfStory", countOfStory++);
        addStory.putStringArrayListExtra("storyTags", selectedTagsByUser);
        startActivity(addStory);
        finish();
    }*/


    @Override
    protected void onPause() {
        super.onPause();

    }

    private void buildStory() {
        int index = questionNumber;
        preferences = PreferenceManager.getDefaultSharedPreferences(specialNoteScreen.this);

        if (preferences.getString("StorySoFar", null) != null) {

            String tempStory = preferences.getString("StorySoFar", null);

            String tempStory2 = "I " + activityName.get(index) + " on " + answers.get(index) + " as i ";
            String tags = getStoryFromTags();

            tags = tags + "\n";
            tempStory = tempStory + tempStory2 + tags;
            SharedPreferences.Editor editor = preferences.edit();
            editor.putString("StorySoFar", tempStory);
            editor.apply();
        } else {
            String tempStory2 = "I" + activityName.get(index) + " on " + answers.get(index) + " as i ";
            String tags = getStoryFromTags();
            SharedPreferences.Editor editor = preferences.edit();
            editor.putString("StorySoFar", tempStory2 + tags);
            editor.apply();
        }
    }

    public String  getStoryFromTags()
    {
        String  storyFromTags="";
        if (selectedTagsByUser.size()<1)
        {
            storyFromTags=storyFromTags+"..do not want to disclose reasons here";
        }
        else if (selectedTagsByUser.size()==1)
        {
            storyFromTags=storyFromTags+selectedTagsByUser.get(0)+".";
        }
        else {

            int lastReasonIndex=selectedTagsByUser.size() - 1;
            for (int i = 0; i <lastReasonIndex ; i++) {
                storyFromTags = storyFromTags + selectedTagsByUser.get(i) + " and ";
            }
            storyFromTags=storyFromTags+selectedTagsByUser.get(lastReasonIndex)+".";

        }
        return storyFromTags;
    }


    @Override
    protected void onResume() {
        super.onResume();


    }


}