package il.ac.huji.todolistmanager;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Paz on 4/19/2015.
 */
public class myCursorAdapter extends CursorAdapter {
    private LayoutInflater mInflater;
    public myCursorAdapter(Context context, Cursor c, int flags) {
        super(context, c, flags);
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        int id = cursor.getInt(cursor.getColumnIndex("_id"));
        //myListIDs.add(cursor.getInt(cursor.getColumnIndex("_id")));
        View v = mInflater.inflate(R.layout.todo_list_item, parent, false);
        v.setId(id);
        return v;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {

        TextView title = (TextView) view.findViewById(R.id.txtTodoTitle);
        title.setText(cursor.getString(cursor.getColumnIndex("title")));
        TextView dueDate = (TextView) view.findViewById(R.id.txtTodoDueDate);
        long dueLong = cursor.getLong(cursor.getColumnIndex("due"));
        Date d = new Date(dueLong);
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        dueDate.setText(sdf.format(d));

        if (d.before(new Date())){
            title.setTextColor(Color.RED);
            dueDate.setTextColor(Color.RED);
        } else{
            title.setTextColor(Color.BLACK);
            dueDate.setTextColor(Color.BLACK);
        }

        view.setId(cursor.getInt(cursor.getColumnIndex("_id")));
    }

}
