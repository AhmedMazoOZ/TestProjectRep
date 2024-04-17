package com.example.filereader;

import android.graphics.Color;
import android.os.Bundle;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.text.style.UnderlineSpan;
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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    TextView txt_tv,txt_t_one;
    ImageView zoomin, zoomout, textcolor, backgroundcolor, confirm_color;
    Animation animation, animation2;

    LinearLayout edit_color_lay;
    float ratio = 15f;
    boolean Tcolor = false;
    boolean isFont = false;
    SeekBar progressbar_red, progressbar_green, progressbar_blue;
    int red_value, green_value, blue_value, color_value;
    RelativeLayout main;
    String hex, LineValue;

    List<String> Headlines,newHeadlines;

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
        txt_t_one=(TextView)findViewById(R.id.txt_t_one);
        String text = "", line = "";
        Headlines = new ArrayList<>();
        Headlines.clear();
        newHeadlines = new ArrayList<>();
        newHeadlines.clear();
        red_value = progressbar_red.getProgress();
        green_value = progressbar_green.getProgress();
        blue_value = progressbar_blue.getProgress();
        hex = String.format("#%02X%02X%02X", red_value, green_value, blue_value);
        try (BufferedReader br = new BufferedReader(new InputStreamReader(getAssets().open("testfile.txt")))) {

            boolean firstHeadingFound = false;
            while ((line = br.readLine()) != null) {
                if (!firstHeadingFound) {
                    Log.d("fdfgert", line);
                    Log.d("fdfgert", "10");
                    firstHeadingFound = true;
                    processHeading(line);
                }
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        try (BufferedReader br = new BufferedReader(new InputStreamReader(getAssets().open("testfile.txt")))) {
            while ((line = br.readLine()) != null) {
                Headlines.add(line);
            }
            checklines(Headlines);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

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
        //txt_tv.setText(Html.fromHtml(text));

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

    private void checklines(List<String> headlines) {
        Log.d("ewrwerwerwer", String.valueOf(headlines.size()));
        for (int i=0;i<headlines.size();i++){
            if (startsWithNumber(headlines.get(i))) {
                LineValue = headlines.get(i);
                newHeadlines.add(LineValue);

            }
        }
        processHeadlines(newHeadlines);
    }


    private void processHeading(String line) {

        SpannableStringBuilder sb = new SpannableStringBuilder(line);
        sb.setSpan(new ForegroundColorSpan(Color.RED), 0, line.length(), 0);
        sb.setSpan(new UnderlineSpan(), 0, line.length(), 0);
        TextView textView = findViewById(R.id.txt_header);
        textView.setText(sb);

    }

    private void processHeadlines(List<String> line) {


        LinearLayout hLinesCont = findViewById(R.id.hLinesCont);
        Log.d("sdfsdfsdfsdf", String.valueOf(line.size()));
        for (int i = 0; i < line.size(); i++) {

            // Create a new TextView
            SpannableStringBuilder sb = new SpannableStringBuilder(line.get(i));
            sb.setSpan(new ForegroundColorSpan(Color.BLUE), 0, line.get(i).length(), 0);
            sb.setSpan(new UnderlineSpan(), 0, line.get(i).length(), 0);
            TextView textView = new TextView(this);

            // Set the text for the TextView
            textView.setText(sb);

            // Set layout params (optional)
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT);
            textView.setLayoutParams(params);

            // Add the TextView to the LinearLayout
            hLinesCont.addView(textView);
        }

    }

    public static boolean startsWithNumber(String str) {
        return str != null && str.length() > 0 && Character.isDigit(str.charAt(0));
    }
}