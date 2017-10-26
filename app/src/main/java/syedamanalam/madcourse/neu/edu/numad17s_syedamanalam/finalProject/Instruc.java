package syedamanalam.madcourse.neu.edu.numad17s_syedamanalam.finalProject;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;

import syedamanalam.madcourse.neu.edu.numad17s_syedamanalam.R;

public class Instruc extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instruc);
        setTitle("Game play tutorial");

        Button b =(Button) findViewById(R.id.goBack);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String stringToDisplay="Remember that every correct answer will get you 4 points.";
                final Snackbar snack = Snackbar.make(findViewById(android.R.id.content), stringToDisplay, Snackbar.LENGTH_LONG);
                View view1 = snack.getView();
                FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) view1.getLayoutParams();
                params.gravity = Gravity.CENTER_VERTICAL;


                snack.setCallback(new Snackbar.Callback() {
                    @Override
                    public void onDismissed(Snackbar snackbar, int event) {
                        if (event == Snackbar.Callback.DISMISS_EVENT_TIMEOUT) {
                            // When Snack Bar closes, show the alert box
                            Intent i = new Intent(Instruc.this, MainScreenAfterAnimation.class);
                            startActivity(i);
                            finish();
                        }
                    }

                    @Override
                    public void onShown(Snackbar snackbar) {
                    }
                });

                snack.show();


            }
        });
    }

    public void onBackPressed() {
        Intent i = new Intent(this, MainScreenAfterAnimation.class);
        startActivity(i);
        finish();
    }

    }
