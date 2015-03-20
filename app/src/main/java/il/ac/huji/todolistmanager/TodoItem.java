package il.ac.huji.todolistmanager;

import java.util.Calendar;

/**
 * Created by Paz on 3/20/2015.
 */
public class TodoItem {
    Calendar myDate;
    String myText;

    public TodoItem(Calendar date, String text){
        myDate = date;
        myText = text;
    }

    public Calendar getDate() {
        return myDate;
    }

    public String getText() {
        return myText;
    }
}
