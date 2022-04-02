package com.norhan.ex3;
import androidx.appcompat.app.AppCompatActivity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;
import java.util.ArrayList;

import static com.norhan.ex3.R.id.btnShowContactsID;
public class MainActivity extends AppCompatActivity implements View.OnClickListener
{
    public static final String MY_DB_NAME = "contacts.db";
    private SQLiteDatabase contactsDB = null;
    private Button btnAddContact, btnShowContacts;
    private EditText name, phone ;
    ArrayList <String> array;
    ListView list;
    ArrayAdapter <String> arrayAdapter ;
    int first = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        array=new ArrayList<String>();// array list
        arrayAdapter = new ArrayAdapter(this ,android.R.layout.simple_list_item_1,array);
        btnAddContact = findViewById(R.id.btnAddContactID);
        btnShowContacts = findViewById(btnShowContactsID);
        list=findViewById(R.id.list1);  //list View
        list.setAdapter(arrayAdapter);
        name = findViewById(R.id.nameID);
        phone = findViewById(R.id.phoneID);
        createDB();
        //-----------------------------------------------------------------------------
        // A Cursor provides read and write access to database results
        String sql = "SELECT * FROM contacts";
        Cursor cursor = contactsDB.rawQuery(sql, null);

        // Get the index for the column name provided
        int nameColumn = cursor.getColumnIndex("name");
        int phoneColumn = cursor.getColumnIndex("phone");
        String contactList = "";
        // Move to the first row of results & Verify that we have results
        if (cursor.moveToFirst()) {
            do {
                // Get the results and store them in a String
                String name = cursor.getString(nameColumn);
                String phone = cursor.getString(phoneColumn);
                contactList = name + "    " + phone + "\n";
                array.add(contactList);
                // Keep getting results as long as they exist
            } while (cursor.moveToNext());
        }
        //-------------------------------------------------------------------
        btnAddContact.setOnClickListener(this);
        btnShowContacts.setOnClickListener(this);
    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.btnAddContactID:
            {
                addContact();
                break;
            }

            case btnShowContactsID:
            {
                showContacts();
                break;
            }
        }
    }

    public void createDB()
    {
        try
        {
            // Opens a current database or creates it
            // Pass the database name, designate that only this app can use it
            // and a DatabaseErrorHandler in the case of database corruption
            contactsDB = openOrCreateDatabase(MY_DB_NAME, MODE_PRIVATE, null);
            // build an SQL statement to create 'contacts' table (if not exists)
            String sql = "CREATE TABLE IF NOT EXISTS contacts (name VARCHAR primary key , phone VARCHAR );";
            contactsDB.execSQL(sql);
        }

        catch (Exception e)
        {
            Log.d("debug", "Error Creating Database");
        }

        // Make buttons clickable since the database was created
        btnAddContact.setEnabled(true);
        btnShowContacts.setEnabled(true);
    }

    public void addContact() {

        // Get the contact name and phone entered
        String contactName = name.getText().toString();
        String contactPhone = phone.getText().toString();
        //-------------------------------------------------------------------------------------------------------------------------------
        if (!name.getText().toString().equals("")){
            String sql2 = "SELECT * FROM contacts";
            Cursor cursor = contactsDB.rawQuery(sql2, null);
            int nameColumn = cursor.getColumnIndex("name"), i=0;
            int phoneColumn = cursor.getColumnIndex("phone");
            // Move to the first row of results & Verify that we have results
            boolean isIn = false;
            if (cursor.moveToFirst()) {
                do {
                    // Get the results and store them in a String
                    String nameis = cursor.getString(nameColumn);
                    String phoneis = cursor.getString(phoneColumn);

                    if(nameis.equals(contactName)){
                        String x = phone.getText().toString();
                        String y = this.name.getText().toString();
                        Toast.makeText(this, contactName + "  is already in! ", Toast.LENGTH_SHORT).show();
                        isIn = true;
                        array.set(i,contactName+" "+contactPhone);
//                         sql2 = "DELETE FROM contacts WHERE name = " + nameis + ";";
//                        contactsDB.execSQL(sql2);
//                         sql2 = "INSERT INTO contacts (name,phone) VALUES ('" + contactName + "', '" + contactPhone + "');";
//                        contactsDB.execSQL(sql2);
                        arrayAdapter.notifyDataSetChanged();
                    }
                    i++;
                    // Keep getting results as long as they exist
                } while (cursor.moveToNext());
            }
            if (isIn == false) {
                // Execute SQL statement to insert new data
                String sql = "INSERT INTO contacts (name,phone) VALUES ('" + contactName + "', '" + contactPhone + "');";
                contactsDB.execSQL(sql);
                array.add(contactName + "           " + contactPhone);
                arrayAdapter.notifyDataSetChanged();
                Toast.makeText(this, contactName + " is added! ", Toast.LENGTH_SHORT).show();
            }
            //-------------------------------------------------------------------------------------------------------------------------------
        }else if(name.getText().toString().equals("")){
            Toast.makeText(this, "Please enter a name!", Toast.LENGTH_SHORT).show();
        }

    }

    public void showContacts()
    {
        // A Cursor provides read and write access to database results
        String sql = "SELECT * FROM contacts";
        Cursor cursor = contactsDB.rawQuery(sql, null);
        boolean is=false;
        // Get the index for the column name provided
        int nameColumn = cursor.getColumnIndex("name");
        int phoneColumn = cursor.getColumnIndex("phone");
        int count=0;
        String contactName = name.getText().toString();
        String contactPhone = phone.getText().toString();
        String contactList = "";
        String str="";
        // Move to the first row of results & Verify that we have results
        if (cursor.moveToFirst())
        {
            do {
                // Get the results and store them in a String
                String nameis = cursor.getString(nameColumn);
                String phoneis = cursor.getString(phoneColumn);
                contactList = contactList + nameis + "  " + phoneis + "\n";
                is=isin(contactName,nameis);
                if(is){
                    Toast.makeText(this, nameis +" is sub of "+ contactName, Toast.LENGTH_SHORT).show();

                }
                is=false;
                //String sql2="SELECT * FROM contacts  WHERE "+ name + "LIKE '%"+contactName+"%' ";
               // cursor = contactsDB.rawQuery(sql2, null);
                // Keep getting results as long as they exist
            } while (cursor.moveToNext());
            Toast.makeText(this, "Results to Show  : \n" + str, Toast.LENGTH_SHORT).show();
        }
        else
            Toast.makeText(this, "No Results to Show", Toast.LENGTH_SHORT).show();
    }
    public boolean isin(String str1, String str2)
    {
        char str1c = str1.charAt(0), str2c;
        int k = 0;
        for(int i = 0; i < str2.length(); i++)
        {
            str2c = str2.charAt(i);
            if(str2c==str1c)
            {
                while(str2c==str1c && (i+k)>str2.length() && k>str1.length())
                {
                    k++;
                    str2c = str2.charAt(i+k);
                    str1c = str1.charAt(k);
                }
                if(i+k <= str2.length())
                    return true;
            }
        }return false;
    }
}