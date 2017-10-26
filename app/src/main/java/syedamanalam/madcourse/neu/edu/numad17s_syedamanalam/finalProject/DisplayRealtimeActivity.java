package syedamanalam.madcourse.neu.edu.numad17s_syedamanalam.finalProject;

import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Iterator;

import syedamanalam.madcourse.neu.edu.numad17s_syedamanalam.R;

public class DisplayRealtimeActivity extends AppCompatActivity {
    ArrayList<String> activity=new ArrayList<>();
    ArrayList<ActivityStamp> file=new ArrayList<>();
    MemorMeDataHandler db=new MemorMeDataHandler(this);
    ArrayAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_realtime);
        activity.add("DataCollection");

        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,activity);
        ListView listView = (ListView) findViewById(R.id.activityListView);
        Button refresh = (Button) findViewById(R.id.refreshRealTimeActivity);
        listView.setAdapter(adapter);
        refresh.setText("Refresh");
        refresh.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        refreshData ();
                    }
                }
        );

        refreshData ();

    }




    public void refreshData ()
    {
        file= (ArrayList<ActivityStamp>) db.getAllRealData();

        Iterator<ActivityStamp> wordIterator = file.iterator();
        while (wordIterator.hasNext()) {
            ActivityStamp cn=wordIterator.next();
            String log = "Id: "+cn.getId()+" ,Name: " + cn.getActivityDetail() + " ,Phone: " + cn.getActivityTimeStamp()+"location"+cn.getLocationCloseTo();

            adapter.add(log);
            adapter.notifyDataSetChanged();
        }
    }




}
