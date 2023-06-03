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

public class Miss_main extends AppCompatActivity {
    int rand,cnt,max_cnt;
    //    진행도 : miss_cnt
    TextView miss_cnt;
    //    문제 : miss_question
    AutoCompleteTextView miss_question;
    //    정답 : miss_answer1~4
    Button miss_answer[];
    //    다음문제 버튼 : miss_next
    Button miss_next;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.miss_main);

        miss_cnt = findViewById(R.id.miss_cnt);
        miss_question = findViewById(R.id.miss_question);

        miss_answer = new Button[4];
        miss_answer[0] = findViewById(R.id.miss_answer1);
        miss_answer[1] = findViewById(R.id.miss_answer2);
        miss_answer[2] = findViewById(R.id.miss_answer3);
        miss_answer[3] = findViewById(R.id.miss_answer4);

        miss_next = findViewById(R.id.miss_next);
        GetProblem pro = new GetProblem();
        Context con = getApplicationContext();
        pro.insertMissProblem(con);
        LinkedList list = pro.getRandomList();


        cnt = 1;
        max_cnt = list.getNodeCount();
        miss_cnt.setText(cnt+"/"+max_cnt);

        rand=(int)(Math.random()*4);
        miss_question.setText(list.front.problem);
        miss_answer[(0+rand)%4].setText(list.front.answer0);
        miss_answer[(1+rand)%4].setText(list.front.answer1);
        miss_answer[(2+rand)%4].setText(list.front.answer2);
        miss_answer[(3+rand)%4].setText(list.front.answer3);

        miss_answer[0].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goaAndBack(view,list,con);
            }
        });
        miss_answer[1].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goaAndBack(view,list,con);
            }
        });
        miss_answer[2].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goaAndBack(view,list,con);
            }
        });
        miss_answer[3].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goaAndBack(view,list,con);
            }
        });
        miss_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                go(list,con);
            }
        });
    }
    public void go(LinkedList list, Context con){
        if(list.nextNotNull()){
            list.goNext();
            cnt++;
            rand=(int)(Math.random()*4);
            miss_cnt.setText(cnt+"/"+max_cnt);
            miss_question.setText(list.front.problem);
            miss_answer[(0+rand)%4].setText(list.front.answer0);
            miss_answer[(1+rand)%4].setText(list.front.answer1);
            miss_answer[(2+rand)%4].setText(list.front.answer2);
            miss_answer[(3+rand)%4].setText(list.front.answer3);
        }
    }
    public void goaAndBack(View view, LinkedList list,Context con){
        AlertDialog.Builder builder = new AlertDialog.Builder(Miss_main.this);
        builder.setTitle( ((Button)view).getText().toString().equals(list.front.correct) ? "정답입니다" : "오답입니다");
        builder.setMessage(((Button)view).getText().toString().equals(list.front.correct) ? "" : "정답은 : \n"+list.front.correct);
        builder.setPositiveButton("yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if(list.nextNotNull()){
                    cnt++;
                    list.goNext();
                    rand=(int)(Math.random()*4);
                    miss_cnt.setText(cnt+"/"+max_cnt);
                    miss_question.setText(list.front.problem);
                    miss_answer[(0+rand)%4].setText(list.front.answer0);
                    miss_answer[(1+rand)%4].setText(list.front.answer1);
                    miss_answer[(2+rand)%4].setText(list.front.answer2);
                    miss_answer[(3+rand)%4].setText(list.front.answer3);
                }
            }
        });
        builder.setNegativeButton("no", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Toast.makeText(Miss_main.this,"취소!",Toast.LENGTH_SHORT).show();
            }
        });
        AlertDialog alert = builder.create();
        alert.show();
    }
}
