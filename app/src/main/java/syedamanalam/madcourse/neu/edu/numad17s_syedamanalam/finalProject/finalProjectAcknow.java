package syedamanalam.madcourse.neu.edu.numad17s_syedamanalam.finalProject;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import syedamanalam.madcourse.neu.edu.numad17s_syedamanalam.R;

public class finalProjectAcknow extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_final_project_acknow);
        setTitle("Final project Acknowledgements");

        TextView twoPlayerAckIntille = (TextView) findViewById(R.id.intilleTextViewFinalProject);
        twoPlayerAckIntille.setText("PROFESSOR S. INTILLE \n \n"
                + "I would like to thank: * Professor Intille for helping with the brainstorming in the class and leading us in the right direction. ");

        TextView twoPlayerAckAnirudh = (TextView) findViewById(R.id.anirudhTextViewFinalProject);
        twoPlayerAckAnirudh.setText("TA Anirudh Kapila Devgun \n \n"
                + "I would like to thank Anirudh. Anirudh helped a lot when we got stuck at any point and promptly and happily responded.");

        TextView twoPlayerAckDhara = (TextView) findViewById(R.id.dharaTextViewFinalProject);
        twoPlayerAckDhara.setText("TA Dhara Prakash Bhavsar \n \n"
                + "I would like to thank Dhara Prakash Bhavsar. Dhara helped a lot when we got stuck at any point and promptly and happily responded.");

        TextView twoPlayerAckOther = (TextView) findViewById(R.id.otherSourcesTextViewFinalProject);
        twoPlayerAckOther.setText( "1) The final Project Icon is taken from https://goo.gl/3LXYNX \n"
                + "\n2) The location picker in maps is implemented using google maps api's tutorials \n https://developers.google.com/places/android-api/placepicker \n"
                + "\n3) Th Diary Icon is openSource\n"
                + "\n4) The custom fonts used in diary are downloaded from https://goo.gl/PdeMgI \n"
                + "\n5) The radio button code is based on Stack overflow question: https://goo.gl/PNcnHv and https://goo.gl/kaY5Ua \n"
                + "\n6) The animation display is self made but uses the github repository code from: https://goo.gl/30i28T\n"
                + "\n7) The app has been tested on Xiaomi Mi4i, Samsung Galaxy Note 2, and Nexus 5 API 25 emulator by me \n"
                + "\n8) Runtime Permission in Nexus are based on https://goo.gl/4VnCus\n"
                + "\n9) Internet connectivity code is based on https://goo.gl/T8a5Xv"
                + "\n10) The intent extras code is influenced by https://goo.gl/K7VSaH"
                + "\n11) Chandrima might have taken her code for HashMap iteration from https://goo.gl/V3v8Sh"
                + "\n12) The activity recognition is influenced by https://goo.gl/nQGpfX"
                + "\n13) The maps are based on tutorial provided by https://goo.gl/4hjioS and https://goo.gl/lomT0Y");
    }
}
