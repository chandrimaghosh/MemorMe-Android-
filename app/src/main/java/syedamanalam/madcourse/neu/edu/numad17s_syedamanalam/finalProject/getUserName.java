package syedamanalam.madcourse.neu.edu.numad17s_syedamanalam.finalProject;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.Vibrator;
import android.preference.PreferenceManager;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;

import java.util.ArrayList;
import java.util.List;

import syedamanalam.madcourse.neu.edu.numad17s_syedamanalam.Main;
import syedamanalam.madcourse.neu.edu.numad17s_syedamanalam.R;

import static syedamanalam.madcourse.neu.edu.numad17s_syedamanalam.R.id.getUsernameEditTextBox;

public class getUserName extends AppCompatActivity {

    //Static String to save username
    public static String userNameFromEditTextBox = "";

    public Vibrator vb1;
    MemorMeDataHandler db = new MemorMeDataHandler(this);
    List<UserClass> users = new ArrayList<UserClass>();
    public static EditText getUserName;
    String name;
    SharedPreferences preferences;
    String sharePrefUserName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_user_name);
        int userCount = db.getUserClassCount();
        getUserName = (EditText) findViewById(getUsernameEditTextBox);

        preferences = PreferenceManager.getDefaultSharedPreferences(getUserName.this);

        sharePrefUserName = preferences.getString("userName", null);

        if (sharePrefUserName != null) {
            name = sharePrefUserName;
            setTitle("Hey " + name);
            getUserName.setText(name);
            userNameFromEditTextBox = name;

        } else {
            setTitle("Hey Awesome");

        }


        vb1 = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);

        Button testing = (Button) findViewById(R.id.testingButton);
        testing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getUserName.this, animation.class);
                startActivity(i);
                finish();
            }
        });


        getUserName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                userNameFromEditTextBox = getUserName.getText().toString().toLowerCase();

            }
        });


        //Save userName on submit
        Button saveUserName = (Button) findViewById(R.id.saveUserName);
        saveUserName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                //If user does not enter anything, toast a message
                if (userNameFromEditTextBox.length() == 0) {
                    customSnack("Please enter something first", 0);


                } else if (userNameFromEditTextBox.length() <= 2 && userNameFromEditTextBox.length() > 0) {
                    customSnack("Please enter more than 2 characters", 0);

                } else {
                    // Check Connection to internet
                    // If not connected, show alert box
                    // If connected to internet, send the intent
                    final WifiManager wifiSwitch = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);

                    // Now check for internet
                    // If not connected to Internet, show an alertBox
                    if (!checkConnectionToInternet()) {
                        //Show a dialog box to change Wifi State
                        vb1.vibrate(300);

                        final AlertDialog.Builder b1 = new AlertDialog.Builder(getUserName.this);
                        b1.setMessage("Turn Wifi On?");
                        b1.setCancelable(false);

                        b1.setPositiveButton("Yes",
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {

                                        //Turn Wifi On
                                        wifiSwitch.setWifiEnabled(true);
                                    }
                                });


                        b1.setNegativeButton("No",
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        //Send an Intent to Main Screen with Toast that we can't go forward without Internet
                                        dialogInterface.cancel();
                                        customSnack("We need internet access for the first time only", 1);
                                    }
                                });


                        final Snackbar snack = Snackbar.make(findViewById(android.R.id.content), "Please connect to Internet First", Snackbar.LENGTH_LONG);
                        View view1 = snack.getView();
                        FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) view1.getLayoutParams();
                        params.gravity = Gravity.CENTER_VERTICAL;
                        snack.setCallback(new Snackbar.Callback() {

                            @Override
                            public void onDismissed(Snackbar snackbar, int event) {
                                if (event == Snackbar.Callback.DISMISS_EVENT_TIMEOUT) {
                                    // When Snack Bar closes, show the alert box
                                    AlertDialog customAlert = b1.create();
                                    customAlert.show();

                                }
                            }

                            @Override
                            public void onShown(Snackbar snackbar) {
                            }
                        });

                        snack.show();


                    }//End Of ConnectedToInternetCheck


                    // If connected, move forward
                    // If user enters correct name, toast his name and a message
                    // If connected to Internet, simply send an intent
                    else {
                        preferences = PreferenceManager.getDefaultSharedPreferences(getUserName.this);
                        SharedPreferences.Editor editor = preferences.edit();
                        editor.putString("userName", userNameFromEditTextBox);
                        editor.apply();
                        final Intent openMapsToGetOfficeLocation = new Intent(getUserName.this, MapsActivity.class);
                        startActivity(openMapsToGetOfficeLocation);
                        finish();

                    }
                }

            }
        });

    }


    //Check for Internet
    public boolean checkConnectionToInternet() {
        //Get Connectivity Manager
        ConnectivityManager cm1 = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        //Get Active Network Info
        NetworkInfo connected = cm1.getActiveNetworkInfo();

        return connected != null && connected.isConnected();
    }


    //Show custom SnackBars
    public void customSnack(String stringToDisplay, int code) {

        final Snackbar snack = Snackbar.make(findViewById(android.R.id.content), stringToDisplay, Snackbar.LENGTH_LONG);
        View view1 = snack.getView();
        FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) view1.getLayoutParams();
        params.gravity = Gravity.CENTER_VERTICAL;

        if (code == 0) {
            snack.setAction("Dismiss", new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    snack.dismiss();
                }
            });
        }

        if (code == 1) {
            snack.setCallback(new Snackbar.Callback() {

                @Override
                public void onDismissed(Snackbar snackbar, int event) {
                    if (event == Snackbar.Callback.DISMISS_EVENT_TIMEOUT) {
                        // When Snack Bar closes, show the alert box
                        Intent goToUserNameScreen = new Intent(getUserName.this, Main.class);
                        startActivity(goToUserNameScreen);
                        finish();
                    }
                }

                @Override
                public void onShown(Snackbar snackbar) {
                }
            });

        }

        snack.show();

    }


    public void onBackPressed() {
        Intent i = new Intent(getUserName.this, Main.class);
        startActivity(i);
        finish();
    }

}
