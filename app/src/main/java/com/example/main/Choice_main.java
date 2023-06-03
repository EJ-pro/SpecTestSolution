package com.example.main;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class Choice_main extends AppCompatActivity {
    int rand;
    //    문제 : choice_question
    AutoCompleteTextView choice_question;
    //    정답 : choice_answer1~4
    Button choice_answer[];
    //    다음문제 버튼 : choice_next
    Button choice_next;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.choice_main);

        choice_question = findViewById(R.id.choice_question);

//        int rand = (int)(Math.random()*10);
        choice_answer = new Button[4];
        choice_answer[0] = findViewById(R.id.choice_answer1);
        choice_answer[1] = findViewById(R.id.choice_answer2);
        choice_answer[2] = findViewById(R.id.choice_answer3);
        choice_answer[3] = findViewById(R.id.choice_answer4);

        choice_next = findViewById(R.id.choice_next);

        Intent intentsss = getIntent();
        String str = intentsss.getStringExtra("checkBox");
        String spl[] = str.split(",");
        GetProblem pro = new GetProblem();
        if (spl[0].equals("T")) {
            pro.insertProblem(getApplicationContext().getResources().openRawResource(R.raw.ch1_data_1));
        }
        if (spl[1].equals("T")) {
            pro.insertProblem(getApplicationContext().getResources().openRawResource(R.raw.ch1_data_2));
        }
        if (spl[2].equals("T")) {
            pro.insertProblem(getApplicationContext().getResources().openRawResource(R.raw.ch1_data_3));
        }
        if (spl[3].equals("T")) {
            pro.insertProblem(getApplicationContext().getResources().openRawResource(R.raw.ch1_data_4));
        }
        if (spl[4].equals("T")) {
            pro.insertProblem(getApplicationContext().getResources().openRawResource(R.raw.ch1_data_5));
        }
        LinkedList list = pro.getRandomList();

        rand = (int) (Math.random() * 4);
        choice_question.setText(list.front.problem);
        choice_answer[(0 + rand) % 4].setText(list.front.answer0);
        choice_answer[(1 + rand) % 4].setText(list.front.answer1);
        choice_answer[(2 + rand) % 4].setText(list.front.answer2);
        choice_answer[(3 + rand) % 4].setText(list.front.answer3);

        choice_answer[0].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goaAndBack(view, list);
                choice_answer[0].setBackgroundColor(R.drawable.o_color);
            }
        });
        choice_answer[1].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goaAndBack(view, list);
            }
        });
        choice_answer[2].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goaAndBack(view, list);
            }
        });
        choice_answer[3].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goaAndBack(view, list);
            }
        });
        choice_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                go(list);
            }
        });
    }

    public void go(LinkedList list) {
        if (list.nextNotNull()) {
            list.goNext();
            rand = (int) (Math.random() * 4);
            choice_question.setText(list.front.problem);
            choice_answer[(0 + rand) % 4].setText(list.front.answer0);
            choice_answer[(1 + rand) % 4].setText(list.front.answer1);
            choice_answer[(2 + rand) % 4].setText(list.front.answer2);
            choice_answer[(3 + rand) % 4].setText(list.front.answer3);
        }
    }

    public void goaAndBack(View view, LinkedList list) {
        AlertDialog.Builder builder = new AlertDialog.Builder(Choice_main.this);
        builder.setTitle(((Button) view).getText().toString().equals(list.front.correct) ? "정답입니다" : "오답입니다");
        builder.setMessage(((Button) view).getText().toString().equals(list.front.correct) ? "" : "정답은 : \n" + list.front.correct);
        builder.setPositiveButton("yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (list.nextNotNull()) {
                    list.goNext();
                    rand = (int) (Math.random() * 4);
                    choice_question.setText(list.front.problem);
                    choice_answer[(0 + rand) % 4].setText(list.front.answer0);
                    choice_answer[(1 + rand) % 4].setText(list.front.answer1);
                    choice_answer[(2 + rand) % 4].setText(list.front.answer2);
                    choice_answer[(3 + rand) % 4].setText(list.front.answer3);
                }
            }
        });
        builder.setNegativeButton("no", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Toast.makeText(Choice_main.this, "취소!", Toast.LENGTH_SHORT).show();
            }
        });
        AlertDialog alert = builder.create();
        alert.show();
    }
}
