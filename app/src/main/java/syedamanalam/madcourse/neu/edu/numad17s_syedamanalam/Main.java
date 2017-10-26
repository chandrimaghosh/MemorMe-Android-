package syedamanalam.madcourse.neu.edu.numad17s_syedamanalam;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import syedamanalam.madcourse.neu.edu.numad17s_syedamanalam.Dictionary.dictionaryActivity;
import syedamanalam.madcourse.neu.edu.numad17s_syedamanalam.finalProject.AppDescriptionScreen;
import syedamanalam.madcourse.neu.edu.numad17s_syedamanalam.scroggle.ScroggleMain;
import syedamanalam.madcourse.neu.edu.numad17s_syedamanalam.tictactoe.MainActivity;
import syedamanalam.madcourse.neu.edu.numad17s_syedamanalam.trickiestPart.UserActivityDetection;
import syedamanalam.madcourse.neu.edu.numad17s_syedamanalam.twoPlayerComm.userDetailsAdd;
import syedamanalam.madcourse.neu.edu.numad17s_syedamanalam.twoPlayerWordGame.splashScreenTwoPlayer;

public class Main extends AppCompatActivity {

    public Intent startDictionaryActivity;
    public Button openDictButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle(R.string.name);


        //Start Scroggle
        TextView wordGame = (TextView) findViewById(R.id.wordGame);
        wordGame.setOnClickListener(new View.OnClickListener() {
                                        public void onClick(View view) {
                                            //On Touching Scroggle button, start scroggle activity
                                            Intent ScroggleStartIntent = new Intent(Main.this, ScroggleMain.class);
                                            startActivity(ScroggleStartIntent);
                                            finish();
                                        }
                                    }
        );


        //Start 2 player communication
        Button buttonTwoPlayerComm = (Button) findViewById(R.id.twoPlayerComm);
        buttonTwoPlayerComm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent startTwoPlayerComm = new Intent(Main.this, userDetailsAdd.class);
                startActivity(startTwoPlayerComm);
            }
        });


        openDictButton = (Button) findViewById(R.id.openDictionaryXmlID);
//        processTask();

        //Start Two Player Game
        Button startTwoPlayerGame = (Button) findViewById(R.id.twoPlayerWordGameXmlButton);
        startTwoPlayerGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent startTwoPlayerGameIntent = new Intent(Main.this, splashScreenTwoPlayer.class);
                startActivity(startTwoPlayerGameIntent);
                //finish();
            }
        });


        //Button to start "Trickiest Part Activity"
        Button startTrickiestPartButton = (Button) findViewById(R.id.startTrickiestPart);
        startTrickiestPartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent startTricky = new Intent(Main.this, UserActivityDetection.class);
                startActivity(startTricky);
            }
        });


        //Button to launch "Final project"
        Button launchFinalProjectButton = (Button) findViewById(R.id.launchFinalProject);
        launchFinalProjectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent startFinalProjectIntent = new Intent(Main.this, AppDescriptionScreen.class);
                startActivity(startFinalProjectIntent);
                finish();
            }
        });


    }//End of onCreate

    /*
    private void processTask(){
        new openDictTask().execute("");
    }


    // Adding dictionary intent inside an Async task and showing progress bar
    public class openDictTask extends AsyncTask<String, String, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onProgressUpdate(String... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected String doInBackground(String... strings) {
            startDictionaryActivity = new Intent(Main.this, dictionaryActivity.class);
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            openDictButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startActivity(startDictionaryActivity);
                }

            });
            finish();
        }
    }*/


    //Start About me Activity
    public void aboutMe(View view) {
        Intent startAboutMeActivity = new Intent(this, AboutMeActivity.class);
        startActivity(startAboutMeActivity);
    }


    //Generate Error
    public void genError(View view) {
        emptyFunction();
    }

    public void emptyFunction() {
        emptyFunction();
        throw new RuntimeException("Illegal Call");
    }


    // Start Tic Tac Toe
    public void callTicTacToe(View view) {
        Intent intentOpenGame = new Intent(this, MainActivity.class);
        startActivity(intentOpenGame);
    }



    //Start Dictionary
    public void openDict(View view) {
        Intent startDictionaryActivity = new Intent(this, dictionaryActivity.class);
        startActivity(startDictionaryActivity);
        finish();
    }


    public void quitApp(View view) {
        System.exit(0);
    }

}