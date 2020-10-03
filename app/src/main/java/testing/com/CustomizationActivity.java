package testing.com;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class CustomizationActivity extends AppCompatActivity {
    public static String FILE_NAME = "scales.txt";
    Spinner spinner1; Spinner spinner2; Spinner spinner3; Spinner spinner4;
    String spinString1; String spinString2; String spinString3; String spinString4;
    Button addButton;
    Button customBack;
    Button resetButton;
    String scale;
    TextView txt;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customization);

        txt = (TextView) findViewById(R.id.ChooseScales);

        spinner1 = (Spinner)findViewById(R.id.spinner1);
        spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                spinString1 = parent.getItemAtPosition(position).toString();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                spinString1 = "";
            }
        });

        spinner2 = (Spinner)findViewById(R.id.spinner2);
        spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                spinString2 = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                spinString2 = "";
            }
        });

        spinner3 = (Spinner)findViewById(R.id.spinner3);
        spinner3.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(parent.getItemAtPosition(position).toString().equals("---")){
                    spinString3 = null;
                }else {
                    spinString3 = parent.getItemAtPosition(position).toString();
                }
                }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                spinString3 = "";
            }
        });

        spinner4 = (Spinner)findViewById(R.id.spinner4);
        spinner4.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                spinString4 = parent.getItemAtPosition(position).toString();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                spinString4 = "";
            }
        });

        addButton = (Button)findViewById(R.id.AddScale);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(spinString3 !=null){
                    scale = spinString1 + " " + spinString2 + " " + spinString3 + " " + spinString4;
                }else{
                    scale = spinString1 + " " + spinString2 + " " + spinString4;
                }

                if(exists(scale) == false){
                    txt.setText(scale);
                    writeFile(scale);
                }else{
                    Toast.makeText(getBaseContext(),"Scale Already Added",Toast.LENGTH_SHORT).show();
                }
            }
        });

        customBack = (Button)findViewById(R.id.CustomBack);
        customBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openMainActivity();
            }
        });

        resetButton = (Button)findViewById(R.id.Reset);
        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetFile();
            }
        });

    }

    public void openMainActivity(){
        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);
    }

    public void writeFile(String scale){
        try{
            OutputStreamWriter out = new OutputStreamWriter(openFileOutput(FILE_NAME, getBaseContext().MODE_APPEND));
            out.write(scale);
            out.write('\n');
            out.close();
            Toast.makeText(this,"Added",Toast.LENGTH_SHORT).show();
        }catch(IOException e){
            e.printStackTrace();
        }
    }

    public void resetFile(){
        try{
            OutputStreamWriter out = new OutputStreamWriter(openFileOutput(FILE_NAME, MODE_PRIVATE));
            out.write("");
            out.close();
            Toast.makeText(this,"Reset",Toast.LENGTH_SHORT).show();
        }catch(IOException e){
            e.printStackTrace();
        }
    }

    public boolean exists(String scale){
        try{
            FileInputStream fileInputStream = openFileInput("scales.txt");
            InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);

            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            StringBuffer stringBuffer = new StringBuffer();

            String lines;
            while((lines = bufferedReader.readLine()) !=null ){
                if(scale.equals(lines)){
                    return true;
                }
            }
        }catch(IOException e){
            e.printStackTrace();
            System.out.println("There is no file with that name");
        }
        return false;
    }


}