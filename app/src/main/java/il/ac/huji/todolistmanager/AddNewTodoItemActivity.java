package il.ac.huji.todolistmanager;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

import java.util.Calendar;
import java.util.Date;


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
                result.putExtra("title",edtNewItem.getText().toString());
                Calendar cal = Calendar.getInstance();
                cal.set(Calendar.YEAR, datePicker.getYear());
                cal.set(Calendar.MONTH, datePicker.getMonth());
                cal.set(Calendar.DAY_OF_MONTH, datePicker.getDayOfMonth());
                result.putExtra("dueDate", cal.getTime());
                setResult(RESULT_OK, result);
                finish();
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent result = new Intent();
                result.putExtra("title","");
                result.putExtra("dueDate",(Date)null);
                setResult(RESULT_OK, result);
                finish();
            }
        });
    }
}
