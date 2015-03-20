package il.ac.huji.todolistmanager;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import java.util.ArrayList;
import java.util.GregorianCalendar;


public class TodoListManagerActivity extends ActionBarActivity {
    public ArrayList<TodoItem> listItems = new ArrayList<>();
    public myEx3CustomAdapter<TodoItem> todoListAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todo_list_manager);
        ListView lstTodoItems = (ListView)findViewById(R.id.lstTodoItems);
        registerForContextMenu(lstTodoItems);
        todoListAdapter = new myEx3CustomAdapter<>(
                this, R.layout.todo_list_item, listItems);
        lstTodoItems.setAdapter(todoListAdapter);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v,ContextMenu.ContextMenuInfo menuInfo) {
        if (v.getId()==R.id.lstTodoItems) {
            getMenuInflater().inflate(R.menu.menu_item_select,menu);
            AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;
            String itemTitle = listItems.get(info.position).getText();
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
        if (title.toLowerCase().contains("call")){
            num = title.substring(title.toLowerCase().indexOf("call") + 5);
        }
        return num;
    }


    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo)item.getMenuInfo();
        int menuItemIndex = item.getItemId();
        if (menuItemIndex == R.id.menuItemDelete)
        {
            listItems.remove(info.position);
            todoListAdapter.notifyDataSetChanged();
        }
        else{
            Intent call = new Intent(Intent.ACTION_CALL,
                    Uri.parse("tel:" + getPhoneNum(listItems.get(info.position).getText())));
            startActivity(call);
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
            boolean isOK = data.getBooleanExtra("isOK", false);
            if (isOK){
                String itemText = data.getStringExtra("itemText");
                int[] selectedDateVals = data.getIntArrayExtra("dateVals");
                GregorianCalendar selectedDate = new GregorianCalendar(selectedDateVals[0], selectedDateVals[1], selectedDateVals[2]);
                listItems.add(new TodoItem(selectedDate,itemText));
                todoListAdapter.notifyDataSetChanged();
                Log.w("myApp",selectedDate.getTime().toString());
            }
        }
    }
}
