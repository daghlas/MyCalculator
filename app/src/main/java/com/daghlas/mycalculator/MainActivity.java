package com.daghlas.mycalculator;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.button.MaterialButton;

import org.mozilla.javascript.Context;
import org.mozilla.javascript.Scriptable;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    TextView arithmetic, result;
    MaterialButton clear, open, close, one, two, three, four, five, six, seven, eight, nine, zero;
    MaterialButton divide, multiply, add, subtract, equals, delete, decimal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.statusBar));

        arithmetic = findViewById(R.id.arithmetic);
        result = findViewById(R.id.result);

        assignId(clear, R.id.btn_clear);
        assignId(open, R.id.btn_open);
        assignId(close, R.id.btn_close);

        assignId(one, R.id.btn_one);
        assignId(two, R.id.btn_two);
        assignId(three, R.id.btn_three);
        assignId(four, R.id.btn_four);
        assignId(five, R.id.btn_five);
        assignId(six, R.id.btn_six);
        assignId(seven, R.id.btn_seven);
        assignId(eight, R.id.btn_eight);
        assignId(nine, R.id.btn_nine);
        assignId(zero, R.id.btn_zero);

        assignId(divide, R.id.btn_divide);
        assignId(multiply, R.id.btn_muiltiply);
        assignId(add, R.id.btn_add);
        assignId(subtract, R.id.btn_subtract);
        assignId(equals, R.id.btn_equals);
        assignId(delete, R.id.btn_delete);
        assignId(decimal, R.id.btn_decimal);

    }

    void assignId(MaterialButton btn, int id){
        btn = findViewById(id);
        btn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        MaterialButton button = (MaterialButton) v;
        String buttonValue = button.getText().toString();
        String arithmeticValues = arithmetic.getText().toString();

        if(buttonValue.equals("C")){
            arithmetic.setText("");
            result.setText("0");
            return;
        }
        if(buttonValue.equals("=")){
            arithmetic.setText(result.getText());
            return;
        }
        if(buttonValue.equals("del") && arithmeticValues.length()>0){
            arithmeticValues = arithmeticValues.substring(0, arithmeticValues.length()-1);
        }else {
            arithmeticValues = arithmeticValues+buttonValue;
        }
        arithmetic.setText(arithmeticValues);
        String finalResult = getResult(arithmeticValues);
        if(!finalResult.equals("Error")){
            result.setText(finalResult);
        }
    }
    String getResult(String data){
        try {
            Context context = Context.enter();
            context.setOptimizationLevel(-1);
            Scriptable scriptable = context.initStandardObjects();
            String finalResult = context.evaluateString(scriptable, data, "Java",1,null).toString();
            //clear decimal if ends with .0
            if(finalResult.endsWith(".0")){
                finalResult = finalResult.replace(".0", "");
            }
            return finalResult;
        }catch (Exception exception){
            return "Error";
        }
    }
}