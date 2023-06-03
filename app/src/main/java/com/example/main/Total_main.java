package com.example.main;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
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

public class Total_main extends AppCompatActivity {
    int rand;
    //    문제 : total_question
    AutoCompleteTextView total_question;
//    정답 : total_answer1~4
    Button total_answer[];
//    다음문제 버튼 : total_next
    Button total_next;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.total_main);
        total_question = findViewById(R.id.total_question);

        total_answer = new Button[4];
        total_answer[0] = findViewById(R.id.total_answer1);
        total_answer[1] = findViewById(R.id.total_answer2);
        total_answer[2] = findViewById(R.id.total_answer3);
        total_answer[3] = findViewById(R.id.total_answer4);

        total_next = findViewById(R.id.total_next);

        GetProblem pro = new GetProblem();
        pro.insertProblem(getApplicationContext().getResources().openRawResource(R.raw.ch1_data_1));
        pro.insertProblem(getApplicationContext().getResources().openRawResource(R.raw.ch1_data_2));
        pro.insertProblem(getApplicationContext().getResources().openRawResource(R.raw.ch1_data_3));
        pro.insertProblem(getApplicationContext().getResources().openRawResource(R.raw.ch1_data_4));
        pro.insertProblem(getApplicationContext().getResources().openRawResource(R.raw.ch1_data_5));
        LinkedList list = pro.getRandomList();

        rand=(int)(Math.random()*4);
        total_question.setText(list.front.problem);
        total_answer[(0+rand)%4].setText(list.front.answer0);
        total_answer[(1+rand)%4].setText(list.front.answer1);
        total_answer[(2+rand)%4].setText(list.front.answer2);
        total_answer[(3+rand)%4].setText(list.front.answer3);

        total_answer[0].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goaAndBack(view,list);
            }
        });
        total_answer[1].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goaAndBack(view,list);
            }
        });
        total_answer[2].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goaAndBack(view,list);
            }
        });
        total_answer[3].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goaAndBack(view,list);
            }
        });
        total_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                go(list);
            }
        });
    }
    public void go(LinkedList list){
        if(list.nextNotNull()){
            list.goNext();
            rand=(int)(Math.random()*4);
            total_question.setText(list.front.problem);
            total_answer[(0+rand)%4].setText(list.front.answer0);
            total_answer[(1+rand)%4].setText(list.front.answer1);
            total_answer[(2+rand)%4].setText(list.front.answer2);
            total_answer[(3+rand)%4].setText(list.front.answer3);
        }
    }
    public void goaAndBack(View view, LinkedList list){
        AlertDialog.Builder builder = new AlertDialog.Builder(Total_main.this);
        builder.setTitle( ((Button)view).getText().toString().equals(list.front.correct) ? "정답입니다" : "오답입니다");
        builder.setMessage(((Button)view).getText().toString().equals(list.front.correct) ? "" : "정답은 : \n"+list.front.correct);
        builder.setPositiveButton("yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if(list.nextNotNull()){
                    list.goNext();
                    rand=(int)(Math.random()*4);
                    total_question.setText(list.front.problem);
                    total_answer[(0+rand)%4].setText(list.front.answer0);
                    total_answer[(1+rand)%4].setText(list.front.answer1);
                    total_answer[(2+rand)%4].setText(list.front.answer2);
                    total_answer[(3+rand)%4].setText(list.front.answer3);
                }
            }
        });
        builder.setNegativeButton("no", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Toast.makeText(Total_main.this,"취소!",Toast.LENGTH_SHORT).show();
            }
        });
        AlertDialog alert = builder.create();
        alert.show();
    }

}