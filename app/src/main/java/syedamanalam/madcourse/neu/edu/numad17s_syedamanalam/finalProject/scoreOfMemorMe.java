package syedamanalam.madcourse.neu.edu.numad17s_syedamanalam.finalProject;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutCompat;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import syedamanalam.madcourse.neu.edu.numad17s_syedamanalam.Main;
import syedamanalam.madcourse.neu.edu.numad17s_syedamanalam.R;

import static android.support.design.widget.Snackbar.make;
import static android.widget.Toast.LENGTH_LONG;
import static syedamanalam.madcourse.neu.edu.numad17s_syedamanalam.R.id.rootViewScoreScreen;
import static syedamanalam.madcourse.neu.edu.numad17s_syedamanalam.finalProject.questionTemplate.memorMeMaxScore;
import static syedamanalam.madcourse.neu.edu.numad17s_syedamanalam.finalProject.questionTemplate.memorMeUserScore;

public class scoreOfMemorMe extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score_of_memor_me);
        setTitle("Score");

        RelativeLayout changeColorOfLayout = (RelativeLayout) findViewById(R.id.rootViewScoreScreen);

        TextView scoreDisplayText = (TextView) findViewById(R.id.scoreDisplayXmlTextView);
        scoreDisplayText.setText(memorMeUserScore + "/" + memorMeMaxScore);
        if (memorMeUserScore == 12) {
            scoreDisplayText.setText("Good job, all questions correct");
        }

        ImageView highScoreDisplayImages = (ImageView) findViewById(R.id.highScoreDisplay);

        if (memorMeUserScore == 0) {
            changeColorOfLayout.setBackgroundColor(getResources().getColor(R.color.red_color));
//            highScoreDisplayImages.setImageResource(R.drawable.zero);
        } else if (memorMeUserScore == 4) {
            changeColorOfLayout.setBackgroundColor(getResources().getColor(R.color.red_color));
//            highScoreDisplayImages.setImageResource(R.drawable.four);
        } else if (memorMeUserScore == 8) {
            changeColorOfLayout.setBackgroundColor(Color.parseColor("#FFEB3B"));
//            highScoreDisplayImages.setImageResource(R.drawable.eight);
        } else if (memorMeUserScore == 12) {
            changeColorOfLayout.setBackgroundColor(Color.parseColor("#4CAF50"));
//            highScoreDisplayImages.setImageResource(R.drawable.twelve);
        }

        Button backToHome = (Button) findViewById(R.id.intentBackToHome);
        backToHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                memorMeMaxScore = 0;
                memorMeUserScore = 0;
                Intent i = new Intent(scoreOfMemorMe.this, Main.class);
                startActivity(i);
                finish();
            }
        });

        Button backToDiary = (Button) findViewById(R.id.viewStoryScoreScreen);
        backToDiary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                make(findViewById(rootViewScoreScreen), "No story made as you skipped it", LENGTH_LONG);
                Intent i = new Intent(scoreOfMemorMe.this, indexOfDiary.class);
                startActivity(i);
                finish();
            }
        });


    }//End of onCreate

    //For Action Bar Menu
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main2, menu);
        return true;
    }

    //On clicking on the item in the drawer
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.myDiarySpinner:
                startMyDiaryFromSpinner();
                break;

            //Add future buttons here and switch based on itemId
            case R.id.developerFeedback:
                giveFeedback();
                break;

        }
        return true;
    }

    public void startMyDiaryFromSpinner() {
        Intent i = new Intent(this, indexOfDiary.class);
        startActivity(i);
        finish();
    }

    public void giveFeedback() {


        AlertDialog.Builder b1 = new AlertDialog.Builder(scoreOfMemorMe.this);
        b1.setMessage("Your Feedback is very valuable to us. Please rate us");
        b1.setCancelable(true);

        LinearLayout ll = new LinearLayout(scoreOfMemorMe.this);
        ll.setOrientation(LinearLayout.VERTICAL);
        ll.setPadding(10, 10, 10, 10);


        RatingBar ratingBar = new RatingBar(getApplicationContext());
        ratingBar.setLayoutParams(new LinearLayout.LayoutParams(LinearLayoutCompat.LayoutParams.WRAP_CONTENT, LinearLayoutCompat.LayoutParams.WRAP_CONTENT));
        ratingBar.setMax(5);
//        ratingBar.setNumStars(5);
        ratingBar.setRating(5);
        ratingBar.setBackgroundColor(getResources().getColor(R.color.blue_color));
        ratingBar.layout(20, 20, 20, 20);


        EditText editT = new EditText(scoreOfMemorMe.this);
        editT.setHint("Please enter feedback");
        editT.layout(20, 20, 20, 20);

        ll.addView(ratingBar);
        ll.addView(editT);

        b1.setView(ll);


        b1.setPositiveButton("Submit feedback",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        make(getWindow().getDecorView(), "Thank you very much", Snackbar.LENGTH_LONG).show();
                    }
                });


        b1.setNegativeButton("Not now",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //Send an Intent to Main Screen with Toast that we can't go forward without Internet
                        final Snackbar sb1 = Snackbar.make(getWindow().getDecorView(), "No problem, maybe some other time", Snackbar.LENGTH_LONG);
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
                    }
                });

        AlertDialog alert = b1.create();
        alert.show();
    }

    public void onBackPressed() {
        Intent i = new Intent(scoreOfMemorMe.this, Main.class);
        startActivity(i);
        finish();
    }


}