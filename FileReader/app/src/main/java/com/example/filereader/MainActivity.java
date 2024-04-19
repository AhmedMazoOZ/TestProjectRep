package com.example.filereader;

import static android.text.Spannable.SPAN_EXCLUSIVE_EXCLUSIVE;

import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.BackgroundColorSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import android.text.style.UnderlineSpan;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.Scroller;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    TextView txt_tv, StoryTitles,txt_test;
    ImageView zoomin, zoomout, textcolor, backgroundcolor, confirm_color;
    TextFile textFile;

    LinearLayout edit_color_lay;
    float ratio = 30f;
    boolean Tcolor = false;
    boolean isFont = false;
    SeekBar progressbar_red, progressbar_green, progressbar_blue;
    int red_value, green_value, blue_value, color_value;
    RelativeLayout main;
    String hex_color, LineValue;

    List<String> Headlines, newHeadlines;
    String text = "", line = "";
    ScrollView scrl1;
    int customId = 0;
    static Context context;
    public static final int MENU_RESOURCE_ID = R.menu.text_selection;
    SpannableStringBuilder sb, sbtit, sbpara;

    public static LinearLayout hLinesCont,sections;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();
         textFile = new TextFile("testfile.txt");
        txt_tv.setText(textFile.readFile(context));

        if (textFile.HasTitle(context) != null) {
            StoryTitles.setText(textFile.FileTilte(textFile.HasTitle(context)));
        }

        Headlines = textFile.ReadDectionaryLine(context);
        if (textFile.checklines(Headlines)!=null){
            newHeadlines=textFile.checklines(Headlines);
            textFile.processHeadlines(context,newHeadlines,hex_color,ratio);
        }
        Onclick();
    }

    public void init() {
        context = this;
        txt_test= (TextView) findViewById(R.id.txt_test);
        StoryTitles = (TextView) findViewById(R.id.txt_header);
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
        scrl1 = (ScrollView) findViewById(R.id.scrl1);
        hLinesCont = findViewById(R.id.hLinesCont);
        sections = findViewById(R.id.sections);
        Headlines = new ArrayList<>();
        newHeadlines = new ArrayList<>();

        Headlines.clear();
        newHeadlines.clear();

        red_value = progressbar_red.getProgress();
        green_value = progressbar_green.getProgress();
        blue_value = progressbar_blue.getProgress();
        hex_color = String.format("#%02X%02X%02X", red_value, green_value, blue_value);
    }
    public void Onclick() {
        confirm_color.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                color_value = Color.parseColor(hex_color);
                Log.d("sdfsdfsdf", String.valueOf(color_value));
                if (isFont) {
                    textFile.processHeadlines(context,newHeadlines, hex_color,ratio);

                } else {
                    main.setBackgroundColor(color_value);
                }
            }
        });
        zoomin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                textFile.processHeadlines(context,newHeadlines, hex_color,ratio);
                txt_tv.setTextSize(ratio);
                ratio += 5;
            }
        });
        zoomout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                textFile.processHeadlines(context,newHeadlines, hex_color,ratio);
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
                hex_color = String.format("#%02X%02X%02X", red_value, green_value, blue_value);
                color_value = Color.parseColor(hex_color);
                if (isFont) {
                    txt_test.setTextColor(color_value);
                }else {
                    txt_test.setBackgroundColor(color_value);
                }
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
                hex_color = String.format("#%02X%02X%02X", red_value, green_value, blue_value);
                Log.d("sdfsdfsdf", "red_" + String.valueOf(green_value));
                color_value = Color.parseColor(hex_color);
                if (isFont) {
                    txt_test.setTextColor(color_value);
                }else {
                    txt_test.setBackgroundColor(color_value);
                }
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
                hex_color = String.format("#%02X%02X%02X", red_value, green_value, blue_value);
                Log.d("sdfsdfsdf", "red_" + String.valueOf(blue_value));
                color_value = Color.parseColor(hex_color);
                if (isFont) {
                    txt_test.setTextColor(color_value);
                }else {
                    txt_test.setBackgroundColor(color_value);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }
    private static void highlightSelectedText(TextView textView, int start, int end) {
        SpannableStringBuilder ssb = new SpannableStringBuilder(textView.getText());
        ssb.setSpan(new BackgroundColorSpan(Color.YELLOW), start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE); // Set background color for highlighting
        textView.setText(ssb);
    }
    public static void showPopupMenu(final TextView textView) {
        PopupMenu popupMenu = new PopupMenu(context, textView);
        popupMenu.getMenuInflater().inflate(R.menu.text_selection, popupMenu.getMenu());

        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                int id = item.getItemId();
                int startSelection = textView.getSelectionStart();
                int endSelection = textView.getSelectionEnd();
                if (id == R.id.action_copy) {
                    // Get the selected text
                    String selectedText = textView.getText().toString().substring(startSelection, endSelection);

                    // Copy to clipboard
                    ClipboardManager clipboard = (ClipboardManager) context.getSystemService(CLIPBOARD_SERVICE);
                    clipboard.setText(selectedText);
                    Toast.makeText(context, "Text copied!", Toast.LENGTH_SHORT).show();
                    return true;
                } else if (id == R.id.action_highlight) {
                    highlightSelectedText(textView, startSelection, endSelection);
                } else if (id == R.id.action_share) {
                    String selectedText = textView.getText().toString().substring(textView.getSelectionStart(), textView.getSelectionEnd());

                    Intent shareIntent = new Intent(Intent.ACTION_SEND);
                    shareIntent.setType("text/plain");
                    shareIntent.putExtra(Intent.EXTRA_TEXT, selectedText);
                    context.startActivity(Intent.createChooser(shareIntent, "Share to:"));
                } else if (id == R.id.action_selectall) {
                    highlightSelectedText(textView, 0, textView.getText().length());
                } else if (id == R.id.action_bookmark) {

                }
                return false;
            }
        });

        popupMenu.show();
    }


}