package com.Gregor.storage;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import android.app.ActivityManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
class obj{
    char index;
    obj(char v){
        this.index = v ;
    }
}
public class MainActivity extends AppCompatActivity {
    TextView TotalMem;
    TextView spaceFree;
    TextView Val;
    EditText address;
    char[] intArray;
    Button But;
    obj [] objects;
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TotalMem = findViewById(R.id.spaceValue);
        spaceFree =findViewById(R.id.freeValue);
        Val = findViewById(R.id.ValueOfAddress);
        address = findViewById(R.id.address);
        But = findViewById(R.id.search);
        //Get the Memory size:
        ActivityManager actManager = (ActivityManager) getSystemService(ACTIVITY_SERVICE);
        ActivityManager.MemoryInfo memInfo = new ActivityManager.MemoryInfo();
        actManager.getMemoryInfo(memInfo);
        long totalMemory =memInfo.totalMem/1048576L;
       TotalMem.setText(totalMemory +" MB");
       //Get free space in memory:
        getAvailableRam();
        intArray = new char[]{ 'a','b','c','d','e','f','g','h','i' ,'j','k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z'};
         objects = new obj [26];
        for (int j=0;j<objects.length;j++){
            objects[j]=new obj(intArray[j]);
        }
        WriteBtn();
        But.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ReadBtn();
            }
        });
    }
    private void getAvailableRam() {
        ActivityManager.MemoryInfo mi = new ActivityManager.MemoryInfo();
        ActivityManager activityManager = (ActivityManager) getSystemService(ACTIVITY_SERVICE);
        activityManager.getMemoryInfo(mi);
        long availableMegs = mi.availMem / 1048576L;
        spaceFree.setText(availableMegs + " MB");
    }
    // write text to file
    public void WriteBtn() {
        // add-write text into file
        try {
            FileOutputStream fileout=openFileOutput("mytextfile.txt", MODE_PRIVATE);
            OutputStreamWriter outputWriter=new OutputStreamWriter(fileout);
            for(int i=0;i<objects.length;i++)
                outputWriter.write(objects[i].index);
            outputWriter.close();

            //display file saved message
            Toast.makeText(getBaseContext(), "File saved successfully!",
                    Toast.LENGTH_SHORT).show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    // Read text from file
    public void ReadBtn() {
        //reading text from file
        try {
            FileInputStream fileIn=openFileInput("mytextfile.txt");
            InputStreamReader InputRead= new InputStreamReader(fileIn);
            char[] inputBuffer= new char[1];
            String s="";
            int charRead;
            boolean find =false;
            while ((charRead=InputRead.read(inputBuffer))>0) {
                // char to string conversion
                String readstring=String.copyValueOf(inputBuffer,0,charRead);
                for(int k=0;k<objects.length;k++) {
                    if(address.getText().charAt(0)==objects[k].index && readstring.toString().equals(address.getText().toString())) {
                        find= true;
                        s = "@" + Integer.toHexString(System.identityHashCode(objects[k]));
                        break;
                    }
                }

            }
            InputRead.close();
            if(!find) {
                Val.setText("Invalid value");
            }else
            Val.setText(s);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
