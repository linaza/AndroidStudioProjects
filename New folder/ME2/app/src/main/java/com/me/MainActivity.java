package com.me;
import androidx.appcompat.app.AppCompatActivity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.Calendar;
public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    Spinner spinner;
    ListView todo;
    ListView processing;
    ListView done;
    Button add;
    EditText addText;

    ArrayAdapter todoadapter;
    ArrayAdapter processingadapter;
    ArrayAdapter doneadapter;
    NotificationReciever a;
    Button msg ;
    ArrayList <String> todolist= new ArrayList<>();
    ArrayList <String> processinglist= new ArrayList<>();
    ArrayList <String> donelist= new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        a = new NotificationReciever();
        Calendar calendar = Calendar.getInstance();
        //set notification time:
        calendar.set(Calendar.HOUR_OF_DAY,13);
        calendar.set(Calendar.MINUTE,48);
        calendar.set(Calendar.SECOND,1);
        spinner = findViewById(R.id.spinner2);
        todo = findViewById(R.id.shape1);
        processing = findViewById(R.id.shape2);
        done = findViewById(R.id.shape3);
        add = findViewById(R.id.button5);
        addText = findViewById(R.id.addText);
        ArrayAdapter <CharSequence> adapter= ArrayAdapter.createFromResource(this,R.array.spinner,android.R.layout.simple_spinner_item);
        spinner.setAdapter(adapter);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
       spinner.setOnItemSelectedListener(this);
       //------------------------------------------------
        //creating lists adapters :
        todoadapter = new ArrayAdapter(this,android.R.layout.simple_list_item_1,todolist);
        processingadapter = new ArrayAdapter(this,android.R.layout.simple_list_item_1,processinglist);
        doneadapter = new ArrayAdapter(this,android.R.layout.simple_list_item_1,donelist);
        Intent intent = new Intent(getApplicationContext(),NotificationReciever.class);
        PendingIntent pendingIntent= PendingIntent.getBroadcast(getApplicationContext(),100,intent,PendingIntent.FLAG_UPDATE_CURRENT);
        AlarmManager alarmManager=(AlarmManager) getSystemService(ALARM_SERVICE);
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,calendar.getTimeInMillis(),AlarmManager.INTERVAL_DAY,pendingIntent);
       /* addText.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                addText.setText("");
            }
        });*/
        msg = findViewById(R.id.button4);
        msg .setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),Repeat_page.class);
                startActivity(intent);
            }
        });

    }

    @Override
    public void onItemSelected(final AdapterView<?> parent, View view, final int position, long id) {
        final String item = parent.getItemAtPosition(position).toString();
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(item.equals("ToDo") && !addText.getText().equals("")){
                    todolist.add(addText.getText().toString());
                    Toast.makeText(getApplicationContext(),"added to todo list",Toast.LENGTH_SHORT).show();
                    addText.setText("");
                    todo.setAdapter(todoadapter);

                }
                if(item.equals("Processing") && !addText.getText().equals("")){
                    processinglist.add(addText.getText().toString());
                    Toast.makeText(getApplicationContext(),"added to processing list",Toast.LENGTH_SHORT).show();
                    addText.setText("");
                    processing.setAdapter(processingadapter);
                }
                if(item.equals("Done")&& !addText.getText().equals("")){
                    donelist.add(addText.getText().toString());
                    Toast.makeText(getApplicationContext(),"added to done list",Toast.LENGTH_SHORT).show();
                    addText.setText("");
                    done.setAdapter(doneadapter);
                }

                }
        });

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
