package com.example.android.randomcard;

import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private ImageView myImage;
    private ImageView yourImage;
    private ArrayList<String> cards;
    private ArrayList<String> yourCards;
    private Random rand;
    private TextView outputMyWins;
    private TextView outputYourWins;
    int myWins=0;
    int yourWins=0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        myImage = findViewById(R.id.myImage);
        yourImage = findViewById(R.id.yourImage);
        outputMyWins=findViewById(R.id.outputMyWins);
        outputYourWins=findViewById(R.id.outputYourWins);

        cards = new ArrayList<String>();
        rand = new Random();

        AssetManager manager = getAssets();
        try {
            for (String filename : manager.list("")) {
                cards.add(filename);
            }
        } catch (IOException e) {
            Toast.makeText(this, "BAD ASSETS!", Toast.LENGTH_LONG).show();
        }
        yourCards = (ArrayList<String>)cards.clone();
    }

    public void myImagePressed(View v) throws IOException {
        int my = rand.nextInt(cards.size());
        myImage.setImageBitmap(BitmapFactory.decodeStream(getAssets().open(cards.get(my))));
        int yours = rand.nextInt(yourCards.size());
        yourImage.setImageBitmap(BitmapFactory.decodeStream(getAssets().open(yourCards.get(yours))));
        int myValueInt;
        int yourValueInt;

        if (Character.isDigit(cards.get(my).charAt(1))) {
            String myValue = cards.get(my).charAt(0) + cards.get(my).charAt(1) + "";
            myValueInt = Integer.parseInt(myValue);
        } else {
            if (cards.get(my).charAt(0) == '1' && !Character.isDigit(cards.get(my).charAt(1))) {
                myValueInt = 14;
            } else {
                myValueInt = cards.get(my).charAt(0) - '0';
            }
        }

        if (Character.isDigit(yourCards.get(yours).charAt(1))) {
            String yourValue = yourCards.get(yours).charAt(0) + yourCards.get(yours).charAt(1) + "";
            yourValueInt = Integer.parseInt(yourValue);
        } else {
            if (yourCards.get(yours).charAt(0) == '1' && !Character.isDigit(yourCards.get(yours).charAt(1))) {
                yourValueInt = 14;
            } else {
                yourValueInt = yourCards.get(yours).charAt(0) - '0';
            }
        }


        if (myValueInt > yourValueInt) {
            Toast.makeText(this, "You Win", Toast.LENGTH_SHORT).show();
            myWins+=1;
            outputMyWins.setText(myWins+"");

        } else if (myValueInt < yourValueInt){
            Toast.makeText(this, "You Lose", Toast.LENGTH_SHORT).show();
            yourWins+=1;
            outputYourWins.setText(yourWins+"");

        } else {
            Toast.makeText(this, "It's War!!", Toast.LENGTH_SHORT).show();
        }
    }

    public void yourImagePressed(View v) throws IOException {
        int r = rand.nextInt(cards.size());
        myImage.setImageBitmap(BitmapFactory.decodeStream(getAssets().open(cards.get(r))));
        int s = rand.nextInt(yourCards.size());
        yourImage.setImageBitmap(BitmapFactory.decodeStream(getAssets().open(yourCards.get(s))));
    }
}