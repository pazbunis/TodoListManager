package il.ac.huji.todolistmanager;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.Objects;

/**
 * Created by Paz on 3/20/2015.
 */
public class myEx3CustomAdapter<T> extends myCustomAdapter{
    ArrayList<TodoItem> myList;
    public myEx3CustomAdapter(Context context, int resource, ArrayList<TodoItem> list) {
        super(context, resource, list);
        myList = list;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) getContext()
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(
                    R.layout.todo_list_item, null);
        }
        TextView tvTitle = (TextView)convertView.findViewById(R.id.txtTodoTitle);
        TextView tvDueDate=(TextView)convertView.findViewById(R.id.txtTodoDueDate);

        Calendar today = Calendar.getInstance(Locale.US);
        TodoItem item = myList.get(position);
        String itemText = item.getText();
        Calendar itemDate = item.getDate();
        SimpleDateFormat format1 = new SimpleDateFormat("dd/MM/yyyy");

        tvTitle.setText(itemText);
        tvDueDate.setText(format1.format(itemDate.getTime()));

        if (item.getDate().before(today)) {
            tvTitle.setTextColor(Color.RED);
            tvDueDate.setTextColor(Color.RED);
        } else {
            tvTitle.setTextColor(Color.BLACK);
            tvDueDate.setTextColor(Color.BLACK);
        }

        return convertView;
    }

    @Override
    public Object getItem(int position) {
        return myList.get(position);
    }

}
