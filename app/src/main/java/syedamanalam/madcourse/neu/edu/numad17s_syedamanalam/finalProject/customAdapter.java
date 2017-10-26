package syedamanalam.madcourse.neu.edu.numad17s_syedamanalam.finalProject;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import java.util.ArrayList;

/**
 * Created by Dark Horse on 21-04-2017.
 */

public class customAdapter extends ArrayAdapter<String> {


    public customAdapter(indexOfDiary indexOfDiary, int simple_list_item_1, ArrayList<String> contentsOfDiary) {
        super(indexOfDiary, simple_list_item_1, contentsOfDiary);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return super.getView(position, convertView, parent);
    }
}