package com.example.main;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class Choice_sub extends AppCompatActivity {
    //    체크박스 : choice_checkbox1~4
    CheckBox checkBox[];

    //    시작 버튼 : choice_start
    Button choice_start;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.choice_sub);
        choice_start = findViewById(R.id.choice_start);

        checkBox = new CheckBox[5];
        checkBox[0] = findViewById(R.id.choice_checkbox1);
        checkBox[1] = findViewById(R.id.choice_checkbox2);
        checkBox[2] = findViewById(R.id.choice_checkbox3);
        checkBox[3] = findViewById(R.id.choice_checkbox4);
        checkBox[4] = findViewById(R.id.choice_checkbox5);

        choice_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String str = "";
                for(int i=0;i<checkBox.length;i++){
                    if(checkBox[i].isChecked()){
                        str=str+"T,";
                    }else {
                        str=str+"F,";
                    }
                }
                Intent intentss = new Intent(getApplicationContext(), Choice_main.class);
                intentss.putExtra("checkBox",str);
                startActivity(intentss);
            }
        });
    }
}