package org.example.project3;

import androidx.appcompat.app.AppCompatActivity;

import org.mariuszgromada.math.mxparser.*;

import android.graphics.PorterDuff;
import android.os.Bundle;
import android.text.SpannableStringBuilder;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import org.example.project3.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    ActivityMainBinding binding;
    private EditText display;
    private TextView resultsTV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        display = findViewById(R.id.tvInputAngka);
        resultsTV = findViewById(R.id.resultTextView);
        display.setShowSoftInputOnFocus(false);

        binding.tvInputAngka.setOnClickListener(view -> {
            if (getString(R.string.InputAngka).equals(display.getText().toString())) {
                display.setText("");
            }
        });

        setToButton(binding.button0,"0");
        setToButton(binding.button1,"1");
        setToButton(binding.button2,"2");
        setToButton(binding.button3,"3");
        setToButton(binding.button4,"4");
        setToButton(binding.button5,"5");
        setToButton(binding.button6,"6");
        setToButton(binding.button7,"7");
        setToButton(binding.button8,"8");
        setToButton(binding.button9,"9");

        setToButton(binding.btnDecimal,".");
        setToButton(binding.buttonPlus,"+");
        setToButton(binding.buttonMin,"-");
        setToButton(binding.buttonMultiplication,"X");
        setToButton(binding.buttonDiv,"÷");
        setToButton(binding.btnPercent,"%");

        buttonEffect(binding.btnAnswer);
        buttonEffect(binding.btnDelete);
        buttonEffect(binding.btnClear);

        binding.btnClear.setOnClickListener(v -> {
            display.setText("");
            resultsTV.setText("");
        });

        binding.btnDelete.setOnClickListener(v -> {
            toDelete();
        });

        binding.btnAnswer.setOnClickListener(v -> {
            toAnswer();
        });
    }

    private void updateText(String strToAdd) {
        String oldStr = display.getText().toString();
        int cursorPos = display.getSelectionStart();
        String leftStr = oldStr.substring(0, cursorPos);
        String rightStr = oldStr.substring(cursorPos);
        if (getString(R.string.InputAngka).equals(display.getText().toString())) {
            display.setText(strToAdd);
        } else {
            display.setText(String.format("%s%s%s", leftStr, strToAdd, rightStr));
        }
        display.setSelection(cursorPos + 1);
    }

    private  void toAnswer(){
        int txtLen = display.getText().length();

        if (txtLen <= 1){
            resultsTV.setText(display.getText().toString());
        }else{
            String userExp = display.getText().toString();

            userExp = userExp.replaceAll("÷", "/");
            userExp = userExp.replaceAll("X", "*");

            Expression exp = new Expression(userExp);

            String result = String.valueOf(exp.calculate());

            //display.setSelection(result.length());
            resultsTV.setText(result);
        }
    }

    public void toDelete() {
        int cursorPos = display.getSelectionStart();
        int textlen = display.getText().length();

        if (cursorPos != 0 && textlen != 0) {
            SpannableStringBuilder selection = (SpannableStringBuilder) display.getText();
            selection.replace(cursorPos - 1, cursorPos, "");
            display.setText(selection);
            display.setSelection(cursorPos - 1);
        }
    }


    public static void buttonEffect(View button){
        button.setOnTouchListener(new View.OnTouchListener() {

            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN: {
                        v.getBackground().setColorFilter(0xe0f47521, PorterDuff.Mode.SRC_ATOP);
                        v.invalidate();
                        break;
                    }
                    case MotionEvent.ACTION_UP: {
                        v.getBackground().clearColorFilter();
                        v.invalidate();
                        break;
                    }
                }
                return false;
            }
        });
    }

    public void setToButton(View view, String string){
        buttonEffect(view);
        view.setOnClickListener(v -> updateText(string));
    }
}