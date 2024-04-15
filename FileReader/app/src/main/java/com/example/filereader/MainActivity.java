package com.example.filereader;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;
import java.io.InputStream;

public class MainActivity extends AppCompatActivity {
    TextView txt_tv;
    ImageView zoomin, zoomout, textcolor, backgroundcolor;
    Animation animation, animation2;

    LinearLayout edit_color_lay;
    float ratio = 15f;
    boolean Tcolor=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        txt_tv = (TextView) findViewById(R.id.txt_tv);
        zoomin = (ImageView) findViewById(R.id.zoomin);
        zoomout = (ImageView) findViewById(R.id.zoomout);
        textcolor = (ImageView) findViewById(R.id.textcolor);
        backgroundcolor = (ImageView) findViewById(R.id.backgroundcolor);
        edit_color_lay= (LinearLayout) findViewById(R.id.edit_color_lay);

        String text = "";

        try {
            InputStream is = getAssets().open("testfile.txt");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            text = new String(buffer);
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        txt_tv.setText(text);
        zoomin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                txt_tv.setTextSize(ratio);
                ratio += 5;
            }
        });
        zoomout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                txt_tv.setTextSize(ratio);
                ratio -= 5;

            }
        });
        textcolor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Tcolor){
                    edit_color_lay.setVisibility(View.VISIBLE);
                    Tcolor=false;
                }else {
                    edit_color_lay.setVisibility(View.GONE);
                    Tcolor=true;
                }

            }
        });
        backgroundcolor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Tcolor){
                    edit_color_lay.setVisibility(View.VISIBLE);
                    Tcolor=false;
                }else {
                    edit_color_lay.setVisibility(View.GONE);
                    Tcolor=true;
                }
            }
        });

    }

}