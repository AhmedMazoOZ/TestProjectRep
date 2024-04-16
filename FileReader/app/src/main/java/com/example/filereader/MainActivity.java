package com.example.filereader;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;
import java.io.InputStream;

public class MainActivity extends AppCompatActivity {
    TextView txt_tv;
    ImageView zoomin, zoomout, textcolor, backgroundcolor, confirm_color;
    Animation animation, animation2;

    LinearLayout edit_color_lay;
    float ratio = 15f;
    boolean Tcolor = false;
    boolean isFont = false;
    SeekBar progressbar_red, progressbar_green, progressbar_blue;
    int red_value, green_value, blue_value, color_value;
    RelativeLayout main;
    String hex;

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
        edit_color_lay = (LinearLayout) findViewById(R.id.edit_color_lay);
        progressbar_red = (SeekBar) findViewById(R.id.progressbar_red);
        progressbar_green = (SeekBar) findViewById(R.id.progressbar_green);
        progressbar_blue = (SeekBar) findViewById(R.id.progressbar_blue);
        confirm_color = (ImageView) findViewById(R.id.confirm_color);
        main = (RelativeLayout) findViewById(R.id.main);
        String text = "";

        red_value = progressbar_red.getProgress();
        green_value = progressbar_green.getProgress();
        blue_value = progressbar_blue.getProgress();
        hex = String.format("#%02X%02X%02X", red_value, green_value, blue_value);
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
        confirm_color.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                color_value = Color.parseColor(hex);
                Log.d("sdfsdfsdf", String.valueOf(color_value));
                if (isFont) {
                    txt_tv.setTextColor(color_value);
                } else {
                    main.setBackgroundColor(color_value);
                }
            }
        });
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
                isFont = true;
                if (Tcolor) {
                    edit_color_lay.setVisibility(View.VISIBLE);
                    Tcolor = false;
                } else {
                    edit_color_lay.setVisibility(View.GONE);
                    Tcolor = true;
                }

            }
        });
        backgroundcolor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isFont = false;
                if (Tcolor) {
                    edit_color_lay.setVisibility(View.VISIBLE);
                    Tcolor = false;
                } else {
                    edit_color_lay.setVisibility(View.GONE);
                    Tcolor = true;
                }
            }
        });

        progressbar_red.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int seekvalue, boolean b) {
                red_value = seekvalue;
                Log.d("sdfsdfsdf", "red_" + String.valueOf(red_value));
                hex = String.format("#%02X%02X%02X", red_value, green_value, blue_value);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        progressbar_green.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int seekvalue, boolean b) {
                green_value = seekvalue;
                hex = String.format("#%02X%02X%02X", red_value, green_value, blue_value);
                Log.d("sdfsdfsdf", "red_" + String.valueOf(green_value));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        progressbar_blue.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int seekvalue, boolean b) {
                blue_value = seekvalue;
                hex = String.format("#%02X%02X%02X", red_value, green_value, blue_value);
                Log.d("sdfsdfsdf", "red_" + String.valueOf(blue_value));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

}