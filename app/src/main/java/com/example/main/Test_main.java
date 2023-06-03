package com.example.main;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class Test_main extends AppCompatActivity {
    int rand,cnt,max_cnt;
    //    진행도 : test_cnt
    TextView test_cnt;
    //    문제 : test_question
    AutoCompleteTextView test_question;
    //    정답 : test_answer1~4
    Button test_answer[];
    //    다음문제 버튼 : test_next
    Button test_next;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test_main);
        test_cnt = findViewById(R.id.test_cnt);
        test_question = findViewById(R.id.test_question);

        test_answer = new Button[4];
        test_answer[0] = findViewById(R.id.test_answer1);
        test_answer[1] = findViewById(R.id.test_answer2);
        test_answer[2] = findViewById(R.id.test_answer3);
        test_answer[3] = findViewById(R.id.test_answer4);

        test_next = findViewById(R.id.test_next);

        GetProblem pro = new GetProblem();
        pro.insertProblem(getApplicationContext().getResources().openRawResource(R.raw.ch1_data_1));
        pro.insertProblem(getApplicationContext().getResources().openRawResource(R.raw.ch1_data_2));
        pro.insertProblem(getApplicationContext().getResources().openRawResource(R.raw.ch1_data_3));
        pro.insertProblem(getApplicationContext().getResources().openRawResource(R.raw.ch1_data_4));
        pro.insertProblem(getApplicationContext().getResources().openRawResource(R.raw.ch1_data_5));
        LinkedList list = pro.getRandomList();
        list.delCount(50);

        Context con = getApplicationContext();
        WriteTextFile("Chxx<<11",con);

        cnt = 1;
        max_cnt = list.getNodeCount();
        test_cnt.setText(cnt+"/"+max_cnt);

        rand=(int)(Math.random()*4);
        test_question.setText(list.front.problem);
        test_answer[(0+rand)%4].setText(list.front.answer0);
        test_answer[(1+rand)%4].setText(list.front.answer1);
        test_answer[(2+rand)%4].setText(list.front.answer2);
        test_answer[(3+rand)%4].setText(list.front.answer3);

        test_answer[0].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goaAndBack(view,list,con);
            }
        });
        test_answer[1].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goaAndBack(view,list,con);
            }
        });
        test_answer[2].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goaAndBack(view,list,con);
            }
        });
        test_answer[3].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goaAndBack(view,list,con);
            }
        });
        test_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                go(list,con);
            }
        });
    }

    public void go(LinkedList list, Context con){
        String str = cnt+"<<"+list.front.problem+"<<"+list.front.answer0+"<<"+list.front.answer1+"<<"+list.front.answer2+"<<"+list.front.answer3+"<<"+list.front.correct+"<<\n";
        WriteAppendTextFile(str,con);

        if(list.nextNotNull()){
            list.goNext();
            cnt++;
            rand=(int)(Math.random()*4);
            test_cnt.setText(cnt+"/"+max_cnt);
            test_question.setText(list.front.problem);
            test_answer[(0+rand)%4].setText(list.front.answer0);
            test_answer[(1+rand)%4].setText(list.front.answer1);
            test_answer[(2+rand)%4].setText(list.front.answer2);
            test_answer[(3+rand)%4].setText(list.front.answer3);
        }
    }
    public void goaAndBack(View view, LinkedList list,Context con){
        if(!((Button)view).getText().toString().equals(list.front.correct)){
            String str = cnt+"<<"+list.front.problem+"<<"+list.front.answer0+"<<"+list.front.answer1+"<<"+list.front.answer2+"<<"+list.front.answer3+"<<"+list.front.correct+"<<\n";
            WriteAppendTextFile(str,con);
        }
        AlertDialog.Builder builder = new AlertDialog.Builder(Test_main.this);
        builder.setTitle( ((Button)view).getText().toString().equals(list.front.correct) ? "정답입니다" : "오답입니다");
        builder.setMessage(((Button)view).getText().toString().equals(list.front.correct) ? "" : "정답은 : \n"+list.front.correct);
        builder.setPositiveButton("yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if(list.nextNotNull()){
                    cnt++;
                    list.goNext();
                    rand=(int)(Math.random()*4);
                    test_cnt.setText(cnt+"/"+max_cnt);
                    test_question.setText(list.front.problem);
                    test_answer[(0+rand)%4].setText(list.front.answer0);
                    test_answer[(1+rand)%4].setText(list.front.answer1);
                    test_answer[(2+rand)%4].setText(list.front.answer2);
                    test_answer[(3+rand)%4].setText(list.front.answer3);
                }
            }
        });
        builder.setNegativeButton("no", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Toast.makeText(Test_main.this,"취소!",Toast.LENGTH_SHORT).show();
            }
        });
        AlertDialog alert = builder.create();
        alert.show();
    }
    public void WriteTextFile(String data, Context context){
        try {
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(context.openFileOutput("ErrProblem.txt", Context.MODE_PRIVATE));
            outputStreamWriter.write(data);
            outputStreamWriter.close();
        }
        catch (IOException e) {
            Log.e("Exception", "File write failed: " + e.toString());
        }
    }

    public void WriteAppendTextFile(String data, Context context){
        String str =  readFromFile(context);
        str = str + data;
        try {
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(context.openFileOutput("ErrProblem.txt", Context.MODE_PRIVATE));
            outputStreamWriter.write(str);
            outputStreamWriter.close();
        }
        catch (IOException e) {
            Log.e("Exception", "File write failed: " + e.toString());
        }
    }

    private String readFromFile(Context context) {

        String ret = "";

        try {
            InputStream inputStream = context.openFileInput("ErrProblem.txt");

            if ( inputStream != null ) {
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String receiveString = "";
                StringBuilder stringBuilder = new StringBuilder();

                while ( (receiveString = bufferedReader.readLine()) != null ) {
                    stringBuilder.append(receiveString).append("\n");
                }

                inputStream.close();
                ret = stringBuilder.toString();
            }
        }
        catch (FileNotFoundException e) {
            Log.e("Exception", "File not found: " + e.toString());
        } catch (IOException e) {
            Log.e("Exception", "Can not read file: " + e.toString());
        }
        return ret;
    }
}