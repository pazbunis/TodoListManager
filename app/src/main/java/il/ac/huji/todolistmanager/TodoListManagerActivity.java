package il.ac.huji.todolistmanager;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;


public class TodoListManagerActivity extends ActionBarActivity {
    public ArrayList<String> listItems = new ArrayList<>();
    public myCustomAdapter<String> todoListAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todo_list_manager);
        ListView lstTodoItems = (ListView)findViewById(R.id.lstTodoItems);
        todoListAdapter = new myCustomAdapter<>(
                this, android.R.layout.simple_list_item_1, listItems);
        lstTodoItems.setAdapter(todoListAdapter);
        lstTodoItems.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                String task = listItems.get(position);
                AlertDialog.Builder builder = new AlertDialog.Builder(TodoListManagerActivity.this);
                builder.setTitle(task)
                       .setNegativeButton(R.string.delete, new DialogInterface.OnClickListener() {
                           public void onClick(DialogInterface dialog, int id) {
                               listItems.remove(position);
                               todoListAdapter.notifyDataSetChanged();
                           }
                       });
                AlertDialog dialog = builder.create();
                dialog.show();
                return false;
            }
        });


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
            EditText edtNewItem = (EditText)findViewById(R.id.edtNewItem);
            listItems.add(edtNewItem.getText().toString());
            edtNewItem.setText("");
            todoListAdapter.notifyDataSetChanged();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
