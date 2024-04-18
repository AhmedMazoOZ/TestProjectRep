package com.example.filereader;

import static android.text.Spannable.SPAN_EXCLUSIVE_EXCLUSIVE;

import android.content.ClipboardManager;
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
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    TextView txt_tv, txt_t_one;
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

    List<String> Headlines, newHeadlines;
    String text = "", line = "";
    public static final int MENU_RESOURCE_ID = R.menu.text_selection;

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


        Headlines = new ArrayList<>();
        Headlines.clear();
        newHeadlines = new ArrayList<>();
        newHeadlines.clear();
        red_value = progressbar_red.getProgress();
        green_value = progressbar_green.getProgress();
        blue_value = progressbar_blue.getProgress();
        hex = String.format("#%02X%02X%02X", red_value, green_value, blue_value);

        readFile();
        readTitle();
        readdectionary();
        Onclick();
    }

    public void Onclick() {
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

    public void readTitle() {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(getAssets().open("testfile.txt")))) {
            boolean firstHeadingFound = false;
            while ((line = br.readLine()) != null) {
                if (!firstHeadingFound) {
                    firstHeadingFound = true;
                    processHeading(line);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void readdectionary() {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(getAssets().open("testfile.txt")))) {
            while ((line = br.readLine()) != null) {
                Headlines.add(line);
            }
            checklines(Headlines);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void readFile() {
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
    }

    private void checklines(List<String> headlines) {
        Log.d("ewrwerwerwer", String.valueOf(headlines.size()));
        for (int i = 0; i < headlines.size(); i++) {
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
        sb.setSpan(new StyleSpan(Typeface.BOLD_ITALIC), 0, line.length(), SPAN_EXCLUSIVE_EXCLUSIVE);
        TextView textView = findViewById(R.id.txt_header);
        textView.setText(sb);

    }

    private void processHeadlines(List<String> line) {
        LinearLayout hLinesCont = findViewById(R.id.hLinesCont);
        LinearLayout sections = findViewById(R.id.sections);
        StringBuilder Paragraph;
        Log.d("sdfsdfsdfsdf", String.valueOf(line.size()));
        for (int i = 0; i < line.size(); i++) {

            // Create a new TextView
            SpannableStringBuilder sb = new SpannableStringBuilder(line.get(i));
            sb.setSpan(new ForegroundColorSpan(Color.BLUE), 0, line.get(i).length(), 0);
            sb.setSpan(new UnderlineSpan(), 0, line.get(i).length(), 0);
            sb.setSpan(new StyleSpan(Typeface.BOLD_ITALIC), 0, line.get(i).length(), SPAN_EXCLUSIVE_EXCLUSIVE);


            Log.d("asdasdasd", line.get(i).substring(3, line.get(i).length()).trim());
            String ASD1 = line.get(i).substring(3, line.get(i).length()).trim();
            SpannableStringBuilder sbtit = new SpannableStringBuilder(ASD1);
            sbtit.setSpan(new ForegroundColorSpan(Color.RED), 0, ASD1.length(), 0);


            Log.d("asdasdasd", line.get(i).substring(3, line.get(i).length()).trim());
            String ASD = line.get(i).substring(3, line.get(i).length()).trim();
            String ASD2 = null;

            ASD2 = line.get(i).substring(3, line.get(i).length()).trim();


            try {
                Paragraph = new StringBuilder(getParagraphAfterTitle(ASD, ASD2));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            SpannableStringBuilder sbpara = new SpannableStringBuilder(Paragraph);
            sbpara.setSpan(new ForegroundColorSpan(Color.BLACK), 0, Paragraph.length(), 0);


            TextView textView = new TextView(this);
            TextView subTitle = new TextView(this);
            TextView paragraph = new TextView(this);

//            textView.setOnLongClickListener(new View.OnLongClickListener() {
//                @Override
//                public boolean onLongClick(View v) {
//                    // Get the text selection functionality
////                    ((TextView) v).selectAll();
//                    showPopupMenu((TextView) v);
//                    return true; // Consume the long press event
//                }
//            });
//            subTitle.setOnLongClickListener(new View.OnLongClickListener() {
//                @Override
//                public boolean onLongClick(View v) {
//                    // Get the text selection functionality
////                    ((TextView) v).selectAll();
//                    showPopupMenu((TextView) v);
//                    return true; // Consume the long press event
//                }
//            });
//            paragraph.setOnLongClickListener(new View.OnLongClickListener() {
//                @Override
//                public boolean onLongClick(View v) {
//                    // Get the text selection functionality
////                    ((TextView) v).selectAll();
//                    showPopupMenu((TextView) v);
//                    return true; // Consume the long press event
//                }
//            });
            subTitle.setGravity(Gravity.CENTER);
            paragraph.setGravity(Gravity.CENTER);
            // Set the text for the TextView
            textView.setText(sb);
            subTitle.setText(sbtit);
            paragraph.setText(sbpara);

            // Set layout params (optional)
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT);

            textView.setLayoutParams(params);
            textView.setTextIsSelectable(true);
            textView.setHighlightColor(getResources().getColor(R.color.yellow));

            subTitle.setLayoutParams(params);
            subTitle.setTextIsSelectable(true);
            subTitle.setHighlightColor(getResources().getColor(R.color.yellow));

            subTitle.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    showPopupMenu((TextView)view );
                    return false;
                }
            });
            paragraph.setLayoutParams(params);
            paragraph.setTextIsSelectable(true);
            paragraph.setHighlightColor(getResources().getColor(R.color.yellow));

            // Add the TextView to the LinearLayout
            hLinesCont.addView(textView);
            sections.addView(subTitle);
            sections.addView(paragraph);
        }

    }

    private void highlightSelectedText(TextView textView, int start, int end) {
        SpannableStringBuilder ssb = new SpannableStringBuilder(textView.getText());
        ssb.setSpan(new BackgroundColorSpan(Color.YELLOW), start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE); // Set background color for highlighting
        textView.setText(ssb);
    }

    private void showPopupMenu(final TextView textView) {
        PopupMenu popupMenu = new PopupMenu(this, textView);
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
                    ClipboardManager clipboard = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
                    clipboard.setText(selectedText);
                    Toast.makeText(MainActivity.this, "Text copied!", Toast.LENGTH_SHORT).show();
                    return true;
                } else if (id == R.id.action_highlight) {
                    highlightSelectedText(textView,startSelection,endSelection);
                } else if (id == R.id.action_share) {

                } else if (id == R.id.action_selectall) {

                } else if (id == R.id.action_bookmark) {

                }
                return false;
            }
        });

        popupMenu.show();
    }

    // Optional menu resource (menu/text_selection.xml)


    public static boolean startsWithNumber(String str) {
        return str != null && str.length() > 0 && Character.isDigit(str.charAt(0));
    }

    public String getParagraphAfterTitle(String title, String title2) throws IOException {
        StringBuilder paragraph = new StringBuilder();
        boolean foundTitle = false;

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(getAssets().open("testfile.txt")))) {
            String line;
            while ((line = reader.readLine()) != null) {
                // Check if the line matches the title (case-insensitive)

                if (line.trim().equalsIgnoreCase(title)) {
                    foundTitle = true;
                    Log.d("sdfsdfsdfsdf", "100");
                    Log.d("sdfsdfsdfsdf", line.trim());
                } else if (foundTitle) {
                    // Skip empty lines after the title
                    Log.d("sdfsdfsdfsdf", "200");
                    Log.d("sdfsdfsdfsdf", line.trim());
                    if (!line.isEmpty() && !line.trim().equalsIgnoreCase(title2)) {
                        paragraph.append(line).append("\n"); // Add line with newline
                    }
                }

            }
        }

        // Return paragraph or empty string if title not found or no paragraph after
        return paragraph.toString().trim();
    }
}