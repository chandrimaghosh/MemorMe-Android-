package syedamanalam.madcourse.neu.edu.numad17s_syedamanalam.finalProject;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;

import syedamanalam.madcourse.neu.edu.numad17s_syedamanalam.R;

public class indexOfDiary extends AppCompatActivity {

    public String[] leftRight = new String[20];
    public ArrayList<String> tagsToPass = new ArrayList<>();
    public Bundle bun;
    MemorMeDataHandler db;
    SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_index_of_diary);
        setTitle("Index of my Diary");
        db   = new MemorMeDataHandler(this);
        int count = 0;
        //Get the Question and the tags selected by user
        if ((bun = getIntent().getExtras()) != null) {
            if (bun.getStringArrayList("storyTags") != null) {
                for (String s : bun.getStringArrayList("storyTags")) {
//                    leftRight = s.split(":");
                    tagsToPass.add(s);
                    Log.e(String.valueOf(count), tagsToPass.get(count++));
                }
            }
        }


        final ArrayList<String> contentsOfDiary = new ArrayList<String>();
        contentsOfDiary.add("Preface to my diary");

        preferences = PreferenceManager.getDefaultSharedPreferences(indexOfDiary.this);
        int storyCount=preferences.getInt("StoryCount",0);
        if(storyCount>0)
        {
            contentsOfDiary.add("3rd Week Of April");
        }




        /*
        Bundle b1 = getIntent().getExtras();
        if ( b1 != null) {
            for (int k = 0; k <= b1.getInt("countOfStory"); k++) {
                contentsOfDiary.add("My Story Number: " + b1.get("countOfStory"));
            }
        }*/


        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, contentsOfDiary);


        final ListView anchorView = (ListView) findViewById(R.id.anchorListViewId);
        anchorView.setAdapter(adapter);

        anchorView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                Intent mainIntent = new Intent(indexOfDiary.this, prefaceDiary.class);
                mainIntent.putExtra("a", i);
//                if (tagsToPass != null){
                mainIntent.putStringArrayListExtra("newStoryTag", tagsToPass);
//                }
//                  Toast.makeText(indexOfDiary.this,"indecOfDiary Screen" + i,Toast.LENGTH_LONG).show();
                startActivity(mainIntent);
            }
        });


        Button playMemorMe = (Button) findViewById(R.id.playMemorMeFromSpinner);
        playMemorMe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder b1 = new AlertDialog.Builder(indexOfDiary.this);
                b1.setMessage("Play Memor-Me now?");
                b1.setCancelable(false);

                b1.setPositiveButton("Yes",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                                Intent k = new Intent(indexOfDiary.this, MainScreenAfterAnimation.class);
                                k.putExtra("currentQuestionNumber", 0);
                                startActivity(k);
                                finish();
                            }
                        });

                b1.setNegativeButton("No",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Intent k = new Intent(indexOfDiary.this, MainScreenAfterAnimation.class);
                                startActivity(k);
                                finish();
                            }
                        });

                AlertDialog customAlertBox = b1.create();
                customAlertBox.show();
            }
        });

    }//End of onCreate


    public void onBackPressed(){
        Intent i = new Intent(indexOfDiary.this,MainScreenAfterAnimation.class);
        startActivity(i);
        finish();
    }
}
