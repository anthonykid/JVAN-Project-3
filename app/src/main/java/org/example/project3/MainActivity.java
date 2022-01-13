package org.example.project3;

import androidx.appcompat.app.AppCompatActivity;

import org.mariuszgromada.math.mxparser.*;

import android.os.Bundle;
import android.text.SpannableStringBuilder;
import android.view.View;
import android.widget.EditText;

import org.example.project3.R;
import org.example.project3.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    ActivityMainBinding binding;
    private EditText display;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        display = binding.tvInputAngka;
        display.setShowSoftInputOnFocus(false);

        binding.tvInputAngka.setOnClickListener(view -> {
            if (getString(R.string.InputAngka).equals(display.getText().toString())) {
                display.setText("");
            }
        });

        binding.button0.setOnClickListener(v -> updateText("0"));
        binding.button1.setOnClickListener(v -> updateText("1"));
        binding.button2.setOnClickListener(v -> updateText("2"));
        binding.button3.setOnClickListener(v -> updateText("3"));
        binding.button4.setOnClickListener(v -> updateText("4"));
        binding.button5.setOnClickListener(v -> updateText("5"));
        binding.button6.setOnClickListener(v -> updateText("6"));
        binding.button7.setOnClickListener(v -> updateText("7"));
        binding.button8.setOnClickListener(v -> updateText("8"));
        binding.button9.setOnClickListener(v -> updateText("9"));
        binding.button0.setOnClickListener(v -> updateText("0"));
        binding.btnDecimal.setOnClickListener(v -> updateText("."));
        binding.buttonPlus.setOnClickListener(v -> updateText("+"));
        binding.buttonMin.setOnClickListener(v -> updateText("-"));
        binding.buttonMultiplication.setOnClickListener(v -> updateText("X"));
        binding.buttonDiv.setOnClickListener(v -> updateText("÷"));
        binding.btnPercent.setOnClickListener(v -> updateText("%"));
        binding.btnAnswer.setOnClickListener(v -> {
            String userExp = display.getText().toString();

            userExp = userExp.replaceAll("÷", "/");
            userExp = userExp.replaceAll("X", "*");

            Expression exp = new Expression(userExp);

            String result = String.valueOf(exp.calculate());
            display.setText(result);
            display.setSelection(result.length());
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

    public void deleteBtn(View view) {
        int cursorPos = display.getSelectionStart();
        int textlen = display.getText().length();

        if (cursorPos != 0 && textlen != 0) {
            SpannableStringBuilder selection = (SpannableStringBuilder) display.getText();
            selection.replace(cursorPos - 1, cursorPos, "");
            display.setText(selection);
            display.setSelection(cursorPos - 1);
        }
    }
}