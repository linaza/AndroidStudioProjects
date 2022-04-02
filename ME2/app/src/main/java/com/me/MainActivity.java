package com.me;
import androidx.appcompat.app.AppCompatActivity;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.Toast;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.messaging.RemoteMessage;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

import static com.me.MyFirebaseMessagingService.share_pref;
import static com.me.MyFirebaseMessagingService.text;



public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    File file1;
    File file2;
    File file3;
    Spinner spinner;
    ListView todo;
    ListView processing;
    ListView done;
    Button add;
    EditText addText;
    ArrayAdapter<String> todoadapter;
    ArrayAdapter<String> processingadapter;
    ArrayAdapter<String> doneadapter;
    Button msg ;
    ArrayList <String> todolist= new ArrayList<>();
    ArrayList <String> processinglist= new ArrayList<>();
    ArrayList <String> donelist= new ArrayList<>();
    String fileName;
    public static String share_pref1="shared";
    public static final String text1="text";
    String message=null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
         file1= new File(getFilesDir(),"/todofile.txt");
         file2 = new File(getFilesDir(),"profile.txt");
         file3 = new File(getFilesDir(),"donefile.txt");
        fileName = file1.getName();
        //===========================================
        spinner = findViewById(R.id.spinner2);
        todo = findViewById(R.id.shape1);
        processing = findViewById(R.id.shape2);
        done = findViewById(R.id.shape3);
        add = findViewById(R.id.button5);
        addText = findViewById(R.id.addText);
        Intent intent = new Intent();
        intent.setAction("com.tutorialspoint.CUSTOM_INTENT"); sendBroadcast(intent);

        if(getIntent().getExtras()!=null)
        {


            for(String key :getIntent().getExtras().keySet())
            {

                if(key.equals(("title"))) {
                    String title=getIntent().getExtras().getString(key);
                    message=title;
                }
                if(key.equals("message")) {
                   String message1=getIntent().getExtras().getString(key);
                   message=message+ "\n" + message1;
                }

                //save(message);

            }
        }


        //Spinner:-------------------------------------
        ArrayAdapter <CharSequence> adapter= ArrayAdapter.createFromResource(this,R.array.spinner,android.R.layout.simple_spinner_item);
        spinner.setAdapter(adapter);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
       spinner.setOnItemSelectedListener(this);
       //------------------------------------------------
        //message :
        Button b = findViewById(R.id.button3);
       // b.setText(mes);


        //------------------------------------------------
        //creating lists adapters :
        todoadapter = new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,todolist);
        processingadapter = new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,processinglist);
        doneadapter = new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,donelist);
        ReadBtn(todolist,file1);
        todo.setAdapter(todoadapter);
        ReadBtn(processinglist,file2);
        processing.setAdapter(processingadapter);
        ReadBtn(donelist,file3);
        done.setAdapter(doneadapter);
        msg = findViewById(R.id.button4);
        msg .setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(message!=null)
                {
                Intent intent = new Intent(getApplicationContext(),Repeat_page.class);
                intent.putExtra("message", message);
                startActivity(intent);}
                else
                {
                    Intent intent = new Intent(getApplicationContext(),Repeat_page.class);
                    startActivity(intent);}
            }
        });
        //-------------------------------------------------------------------------------------
       // ReadBtn(todolist,file1);


        todo.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                final int wich_item = i;
                new AlertDialog.Builder(MainActivity.this)
                        .setIcon(android.R.drawable.ic_delete)
                        .setTitle("Yossi, Are you sure?")
                        .setMessage("Do you want to delete this item?")
                        .setPositiveButton("Yes!", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                todolist.remove(wich_item);
                                todoadapter.notifyDataSetChanged();
                                try {
                                    removeLine(file1,wich_item);
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                                Toast.makeText(MainActivity.this, "delete successfully", Toast.LENGTH_SHORT).show();

                            }
                        }).setNegativeButton("No..",null).show();


                return true;
            }
        });

        processing.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                final int wich_item1 = i;
                new AlertDialog.Builder(MainActivity.this)
                        .setIcon(android.R.drawable.ic_delete)
                        .setTitle("Yossi, Are you sure?")
                        .setMessage("Do you want to delete this item?")
                        .setPositiveButton("Yes!", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                processinglist.remove(wich_item1);
                                processingadapter.notifyDataSetChanged();
                                try {
                                    removeLine(file2,wich_item1);
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                                Toast.makeText(MainActivity.this, "delete successfully", Toast.LENGTH_SHORT).show();

                            }
                        }).setNegativeButton("No..",null).show();
                return true;
            }
        });

        done.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                final int wich_item = i;
                new AlertDialog.Builder(MainActivity.this)
                        .setIcon(android.R.drawable.ic_delete)
                        .setTitle("Yossi, Are you sure?")
                        .setMessage("Do you want to delete this item?")
                        .setPositiveButton("Yes!", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                donelist.remove(wich_item);
                                doneadapter.notifyDataSetChanged();
                                try {
                                    removeLine(file3,wich_item);
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                                Toast.makeText(MainActivity.this, "delete successfully", Toast.LENGTH_SHORT).show();

                            }
                        }).setNegativeButton("No..",null).show();

                return true;
            }
        });
    }


//------------------------------------------------------------------------------------------------------------------
    @Override
    public void onItemSelected(final AdapterView<?> parent, View view, final int position, long id) {
        final String item = parent.getItemAtPosition(position).toString();
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(item.equals("ToDo")){
                    fileName = file1.getName();
                    WriteBtn(addText.getText().toString(),file1,todolist);
                    //todolist.add(addText.getText().toString());
                    //Toast.makeText(getApplicationContext(),"added to todo list",Toast.LENGTH_SHORT).show();
                    addText.setText("");
                    todo.setAdapter(todoadapter);

                }
                if(item.equals("Processing")){
                    fileName = file2.getName();
                    WriteBtn(addText.getText().toString(),file2,processinglist);
                   // processinglist.add(addText.getText().toString());
                   // Toast.makeText(getApplicationContext(),"added to processing list",Toast.LENGTH_SHORT).show();
                    addText.setText("");
                    processing.setAdapter(processingadapter);
                }
                if(item.equals("Done")){
                    fileName = file3.getName();
                    WriteBtn(addText.getText().toString(),file3,donelist);
                   // donelist.add(addText.getText().toString());
                   // Toast.makeText(getApplicationContext(),"added to done list",Toast.LENGTH_SHORT).show();
                    addText.setText("");
                    done.setAdapter(doneadapter);
                }
                }
        });

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
    //---------------------------------------------------------------------------------
    // write text to file
    public void WriteBtn(String addThis,File fileName , ArrayList arrayList) {
        // add-write text into file
        try {

            FileOutputStream fileout=openFileOutput(fileName.getName(), MODE_APPEND);
            OutputStreamWriter outputWriter=new OutputStreamWriter(fileout);


            outputWriter.append( addThis);
            outputWriter.append( "\n");
            // outputWriter.write("nohan");
            arrayList.add(addThis);
            outputWriter.close();
            //display file saved message
            Toast.makeText(getBaseContext(), "File saved successfully!",
                    Toast.LENGTH_SHORT).show();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    //---------------------------------------------------------------------------------
    // Read text from file
   public void ReadBtn(ArrayList arrayList , File fileName) {
        FileInputStream fis = null;
        try {
            fis = openFileInput(fileName.getName());
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader br = new BufferedReader(isr);
            StringBuilder sb = new StringBuilder();
            String text;
            while((text = br.readLine())!=null){
                 arrayList.add( text.toString());
            }
        }catch(Exception e){
        }
       Toast.makeText(getBaseContext(), "read",
               Toast.LENGTH_SHORT).show();
    }
    //----------------------------------------------------------------------


    public void removeLine(final File file, final int lineIndex) throws IOException{
        FileInputStream fis = null;
        final List<String> lines = new LinkedList<>();

        try {
            fis = openFileInput(file.getName());
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader br = new BufferedReader(isr);
            StringBuilder sb = new StringBuilder();
            Toast.makeText(getBaseContext(), "at delete ",
                    Toast.LENGTH_SHORT).show();
            String text;
            while((text = br.readLine())!=null)
                lines.add(text.toString());
            assert lineIndex >= 0 && lineIndex <= lines.size() - 1;
            lines.remove(lineIndex);
            final BufferedWriter writer = new BufferedWriter(new FileWriter(file, false));
            for(final String line : lines) {
                writer.write(line);
                writer.newLine();
            }
                writer.flush();
            writer.close();
        }catch(Exception e){
        }
    }

    public void save(String s){
        SharedPreferences sharedPreferences=getSharedPreferences(share_pref,MODE_PRIVATE);
        SharedPreferences.Editor editor1 = sharedPreferences.edit();
        editor1.clear();
        editor1.putString(text,s);
        editor1.commit();

    }

}
