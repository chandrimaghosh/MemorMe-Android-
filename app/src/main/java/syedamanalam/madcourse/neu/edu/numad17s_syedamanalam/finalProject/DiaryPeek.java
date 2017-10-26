package syedamanalam.madcourse.neu.edu.numad17s_syedamanalam.finalProject;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import syedamanalam.madcourse.neu.edu.numad17s_syedamanalam.R;

import static syedamanalam.madcourse.neu.edu.numad17s_syedamanalam.finalProject.getUserName.userNameFromEditTextBox;

public class DiaryPeek extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diary_peek);
        setTitle("Sneak Peak");


        TextView displayUserNameOnDiary = (TextView) findViewById(R.id.userNameOnDiary);
        Typeface tf = Typeface.createFromAsset(getAssets(), "fonts/la_belle_aurore.ttf");
        displayUserNameOnDiary.setTypeface(tf);



        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            displayUserNameOnDiary.setElevation(30);
        }
        displayUserNameOnDiary.setText(userNameFromEditTextBox + "'s\n Diary");

        Button openPreface = (Button) findViewById(R.id.openPrefaceToDiary);
        openPreface.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //Show animation which leads to index and hence preface
                Intent i = new Intent(DiaryPeek.this, animation.class);
                startActivity(i);

            }
        });

    }
}
