package com.example.ht;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.lang.String;
import java.util.HashMap;
import java.util.Map;

public class askActivity extends AppCompatActivity {

    //private DatabaseReference databaseReference;
    private DatabaseReference proRef = FirebaseDatabase.getInstance().getReference("problem");


    public static final String TITLE_KEY ="title_problem";
    public static final String CONTENT_KEY ="content_problem";
    public static final String CATEGORY_KEY ="category_problem";
    public static final String TIME_KEY ="time_problem";
    public static final String STAGE_KEY ="stage";
    public static final String PROID_KEY ="id_problem";
    public static final String REPORT_KEY ="been_reported_problem";
    public static final String INAPPRO_KEY ="inappropriate_content_problem";
    public static final String TAG ="AskingQuestion";

    TextView hottea;
    Button noticebutton,menubutton,sendbutton;
    ImageButton backimageButton;
    EditText questitle,quescontent;
    Spinner type;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ask);

        hottea = (TextView)findViewById(R.id.hottea);
        noticebutton = (Button)findViewById(R.id.noticebutton);
        menubutton = (Button)findViewById(R.id.menubutton);
        sendbutton = (Button)findViewById(R.id.sendbutton);
        backimageButton = (ImageButton) findViewById(R.id.backimageButton);
        questitle = (EditText)findViewById(R.id.questitle);
        quescontent = (EditText)findViewById(R.id.quescontent);
        type = (Spinner)findViewById(R.id.type);

    }

    public void gotohome(View v) {
        finish();
    }
    public void gotonotice(View v) {
        Intent it = new Intent(this, noticeActivity.class);

        startActivity(it);
    }
    public void gotomenu(View v) {
        Intent it = new Intent(this, menuActivity.class);

        startActivity(it);
    }

    public void sendAsk(View v){

        //產生獨一無二 id_problem
        proRef.push();

        // ProblemId (Function)
        String proid= proRef.push().getKey();



        //Title
        String askTitle = questitle.getText().toString();

        //Type
        int askTypenum = type.getSelectedItemPosition();
        String askType = "";

        switch (askTypenum){
            case 0:
                askType = "感情";
                break;
            case 1:
                askType = "家庭";
                break;
            case 2:
                askType = "課業";
                break;
            case 3:
                askType = "未來";
                break;
            case 4:
                askType = "生活";
                break;
        }

        //Content
        String askCont = quescontent.getText().toString();

        //Time
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date sendTime = new Date(System.currentTimeMillis());
        String askTime = formatter.format(sendTime);

        //Stage
        int stage = 1;

        //Id (Function)
        int id = 001;

        //Been Reported (Function)
        Boolean reported = false;

        //Inappropriate Content (Function)
        Boolean inappro = false;

        //Testing
        System.out.println(askTitle);
        System.out.println(askType);
        System.out.println(askCont);
        System.out.println(askTime);
        System.out.println(stage);

        //寫入db
        if (askTitle.isEmpty() || askCont.isEmpty()) { return; }
        Map<String, Object> dataToSave = new HashMap<String, Object>();
        dataToSave.put(TITLE_KEY,askTitle);
        dataToSave.put(CONTENT_KEY,askCont);
        dataToSave.put(CATEGORY_KEY,askType);
        dataToSave.put(TIME_KEY,askTime);
        dataToSave.put(STAGE_KEY,stage);
        dataToSave.put(INAPPRO_KEY,reported);
        dataToSave.put(REPORT_KEY,inappro);
        proRef.child(proid).setValue(dataToSave).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Log.d(TAG,"Document has been saved!");
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.w(TAG,"Document was not saved!",e);
            }
        });

        questitle.setText("");
        quescontent.setText("");


    }

}
