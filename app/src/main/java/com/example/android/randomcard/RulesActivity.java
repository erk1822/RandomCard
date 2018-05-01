package com.example.android.randomcard;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

public class RulesActivity extends AppCompatActivity {

    private TextView outputMyCards;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rules);

        outputMyCards=findViewById(R.id.outputMyCards);

        outputMyCards.setText(getIntent().getIntExtra("CARDS", -1)+"");


        if (savedInstanceState !=null) {
            outputMyCards.setText(savedInstanceState.getString("MyCards"));
        }

    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putString("MyCards", outputMyCards.getText().toString());
    }
}
