package syedamanalam.madcourse.neu.edu.numad17s_syedamanalam.finalProject;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import syedamanalam.madcourse.neu.edu.numad17s_syedamanalam.R;

public class AppDescriptionScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_description_screen2);
        setTitle("Application Description");

        TextView appDescriptionScreenText = (TextView) findViewById(R.id.appDescriptionScreenTextView);
        appDescriptionScreenText.setText("Application Name: \nMemor-Me\n \nWon’t it be exciting to reflect back on your past week in the form of a memory Quiz Game?\n" +
                "\n" +
                "What is even more exciting is, as you recollect or retrospect your last week through the quiz," +
                " our App builds you a Beautiful story with those answers simultaneously and effortlessly." +
                "So \"Memor\"-Me is like your \"Memoir\" as well as your \"Memory\" test."+
                "\n\nTeam Member 1: Syed Aman Alam MS Computer Science Spring 2017\nEmail: alam.sy@husky.neu.edu\n" +
                "\nTeam Member 2: Chandrima Ghosh MS Computer Science Fall 2015\nEmail: ghosh.c@husky.neu.edu\n");

        Button launchP = (Button) findViewById(R.id.launchFinalProjectFromDescrp);
        launchP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent (AppDescriptionScreen.this, splashScreenFinalProject.class);
                startActivity(i);
                finish();
            }
        });

        Button launchAckFinal = (Button) findViewById(R.id.ackButtonDescrScreen);
        launchAckFinal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent (AppDescriptionScreen.this, finalProjectAcknow.class);
                startActivity(i);
            }
        });


    }

}
