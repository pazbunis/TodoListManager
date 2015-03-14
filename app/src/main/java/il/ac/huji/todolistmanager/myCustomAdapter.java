package il.ac.huji.todolistmanager;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Paz on 3/14/2015.
 */
public class myCustomAdapter<T> extends ArrayAdapter {
    public myCustomAdapter(Context context, int resource, ArrayList<T> list) {
        super(context, resource, list);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = super.getView(position, convertView, parent);
        TextView tvName = (TextView)view.findViewById(android.R.id.text1);
        if (position % 2 == 0) {
            tvName.setTextColor(Color.RED);
        } else {
            tvName.setTextColor(Color.BLUE);
        }
        return view;
    }

}
