package com.example.chan1.attendance;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    TextView txt1;
    ArrayList<String> name;
    Button b1;
    EditText ed;
    InputStream is;
    boolean flag;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ed = (EditText) findViewById(R.id.ed1);
        txt1 = (TextView) findViewById(R.id.tv);
        b1 = (Button) findViewById(R.id.btn);
        is = getResources().openRawResource(R.raw.attendance);
        name=new ArrayList<String>();
    }
    public void onClick(View view) {
        flag = false;
        String st = ed.getText().toString();
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        String line = "";
        String absent="";
        try {
            reader.mark(0);
            line=reader.readLine();
            String arr[]= line.split(",");
            mainfor:
            for(int i=2;i<8;i++){
                if (arr[i].equals(st)) {
                    System.out.println("*****Debug****"+arr[i]+"\t\t"+st);
                    flag=true;
                    while ((line=reader.readLine())!=null) {
                        System.out.println("Line"+"\t\t\t"+line);
                        String names[] = line.split(",");
                        if(names[i].equals("a")){
                        name.add(names[1]);}
                    }
                    break mainfor;
                }
            }
            reader.close();
            is = getResources().openRawResource(R.raw.attendance);
            reader = new BufferedReader(new InputStreamReader(is));
            String output="";
            for(String s:name){
                if(s!="" || s!=null){
                    output+=s+"\n";
                }
            }

            txt1.setText(output);
            output="";
            name.clear();

        } catch (IOException e) {
            Toast.makeText(getBaseContext(), e.getMessage(), Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }
        if (!flag)
            Toast.makeText(getBaseContext(), "Record not found", Toast.LENGTH_LONG).show();
    };
};

