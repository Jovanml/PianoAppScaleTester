package testing.com;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Locale;


public class MainActivity extends AppCompatActivity {
    private TextToSpeech mTTS;
    private SeekBar mSpeed;
    private SeekBar mPitch;
    TextView txt;
    Button Generatebtn;
    Button Customizebtn;
    Button Restartbtn;
    ScaleGenerator scales;
    int i;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        i = 0;
        scales = new ScaleGenerator();
        readFile();
        scales.Generator();
        Generatebtn = (Button) findViewById(R.id.GenerateButton);
        Customizebtn = (Button) findViewById(R.id.CustomizeButton);
        txt = (TextView) findViewById(R.id.textView);
        Restartbtn = (Button) findViewById(R.id.restartButton);

        Generatebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String scale = scales.getScaleAtIndex(i);
                txt.setText(scale);
                if(scale != "Finished") {
                    speak(scale);
                }
                i++;
            }
        });

        Customizebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openCustomActivity();
            }
        });

        Restartbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txt.setText("Scale Tester");
                scales.Generator();
                i=0;
            }
        });
        mTTS = new TextToSpeech(this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if(status == TextToSpeech.SUCCESS){
                    int result = mTTS.setLanguage(Locale.CANADA);
                    if(result == TextToSpeech.LANG_MISSING_DATA
                            || result == TextToSpeech.LANG_NOT_SUPPORTED){
                            Log.e("TTS","Language not supported");
                    }
                }else{
                    Log.e("TTS","Initialization failed");
                }
            }
        });

        mPitch = findViewById(R.id.seekBarPitch);
        mSpeed = findViewById(R.id.seekBarSpeed);
    }

    @Override
    protected void onDestroy() {
        if(mTTS != null){
            mTTS.stop();
            mTTS.shutdown();
        }
        super.onDestroy();
    }

    public void speak(String scale){
        float pitch = (float) mPitch.getProgress() / 50;
        float speed = mSpeed.getProgress() / 50;
        if(pitch < 0.1) pitch = 0.1f;
        if(speed < 0.1) speed = 0.1f;

        mTTS.setPitch(pitch);
        mTTS.setSpeechRate(speed);
        mTTS.speak(scale,TextToSpeech.QUEUE_FLUSH,null);
    }

    public void openCustomActivity(){
        Intent intent = new Intent(this, CustomizationActivity.class);
        startActivity(intent);
    }

    public void openMainActivity(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
    public void readFile(){
        try{
            FileInputStream fileInputStream = openFileInput("scales.txt");
            InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);

            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            StringBuffer stringBuffer = new StringBuffer();

            String lines;
            while((lines = bufferedReader.readLine()) !=null ){
                scales.setScalesArray(lines);
            }
        }catch(IOException e){
            e.printStackTrace();
            System.out.println("There is no file with that name");
        }
    }


}