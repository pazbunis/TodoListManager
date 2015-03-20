package il.ac.huji.todolistmanager;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;



public class AddNewTodoItemActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_todo_item);
        final EditText edtNewItem = (EditText) findViewById(R.id.edtNewItem);
        Button btnOK = (Button) findViewById(R.id.btnOK);
        Button btnCancel = (Button) findViewById(R.id.btnCancel);
        final DatePicker datePicker = (DatePicker) findViewById(R.id.datePicker);


        btnOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent result = new Intent();
                result.putExtra("isOK",true);
                result.putExtra("itemText",edtNewItem.getText().toString());
                int[] dateVals = {datePicker.getYear(),datePicker.getMonth(),datePicker.getDayOfMonth()};
                result.putExtra("dateVals", dateVals);
                        setResult(RESULT_OK, result);
                finish();
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent result = new Intent();
                result.putExtra("isOK", false);
                setResult(RESULT_OK, result);
                finish();
            }
        });
    }
}
