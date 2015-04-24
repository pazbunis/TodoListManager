package il.ac.huji.todolistmanager;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;

import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import android.widget.TextView;

import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;


public class TodoListManagerActivity extends ActionBarActivity {

    public myCursorAdapter todoCursorAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todo_list_manager);
        ListView lstTodoItems = (ListView)findViewById(R.id.lstTodoItems);
        registerForContextMenu(lstTodoItems);
        TodoListDBHelper helper = new TodoListDBHelper(this);
        SQLiteDatabase db = helper.getWritableDatabase();
        Cursor cursor = db.query(TodoListDBHelper.TODOLIST_TABLE_NAME,null,null,null,null,null,null);
        todoCursorAdapter = new myCursorAdapter(this, cursor, 0);
        lstTodoItems.setAdapter(todoCursorAdapter);

    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v,ContextMenu.ContextMenuInfo menuInfo) {
        if (v.getId()==R.id.lstTodoItems) {
            getMenuInflater().inflate(R.menu.menu_item_select,menu);
            AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;

            TextView title = (TextView) info.targetView.findViewById(R.id.txtTodoTitle);
            String itemTitle = title.getText().toString();
            menu.setHeaderTitle(itemTitle);
            String phone = getPhoneNum(itemTitle);
            if (phone.equals("")){
                menu.removeItem(R.id.menuItemCall);
            }
            else{
                MenuItem menuCall = menu.getItem(1);
                menuCall.setTitle("Call " + phone);
            }
        }
    }

    private String getPhoneNum(String title){
        String num = "";
        if (title.toLowerCase().startsWith("call ")){
            num = title.substring(title.toLowerCase().indexOf("call ") + 5);
        }
        return num;
    }


    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo)item.getMenuInfo();

        TextView title = (TextView) info.targetView.findViewById(R.id.txtTodoTitle);
        String itemTitle = title.getText().toString();

        int menuItemIndex = item.getItemId();
        if (menuItemIndex == R.id.menuItemDelete)
        {
            int id = info.targetView.getId();
            TodoListDBHelper helper = new TodoListDBHelper(this);
            SQLiteDatabase db = helper.getWritableDatabase();
            db.delete(TodoListDBHelper.TODOLIST_TABLE_NAME, "_id = " + id, null);
            Cursor cursor = db.query(TodoListDBHelper.TODOLIST_TABLE_NAME,null,null,null,null,null,null);
            todoCursorAdapter.changeCursor(cursor);
            todoCursorAdapter.notifyDataSetChanged();
        }
        else{
            Intent dial = new Intent(Intent.ACTION_DIAL,
                    Uri.parse("tel:" + getPhoneNum(itemTitle)));
            startActivity(dial);
        }
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_todo_list_manager, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        if (id == R.id.menuItemAdd) {
            Intent intent = new Intent(this, AddNewTodoItemActivity.class);
            startActivityForResult(intent,1);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK){
            Date dueDate = (Date)data.getSerializableExtra("dueDate");
            if (dueDate != null){
                String itemText = data.getStringExtra("title");
                GregorianCalendar selectedDate = new GregorianCalendar();
                selectedDate.setTime(dueDate);
                //listItems.add(new TodoItem(selectedDate,itemText));
                TodoListDBHelper helper = new TodoListDBHelper(this);
                SQLiteDatabase db = helper.getWritableDatabase();
                ContentValues mytask = new ContentValues();
                mytask.put("title",itemText);
                mytask.put("due", dueDate.getTime());
                db.insert(TodoListDBHelper.TODOLIST_TABLE_NAME,null,mytask);
                Cursor cursor = db.query(TodoListDBHelper.TODOLIST_TABLE_NAME,null,null,null,null,null,null);
                todoCursorAdapter.changeCursor(cursor);
                todoCursorAdapter.notifyDataSetChanged();
            }
        }
    }
}
