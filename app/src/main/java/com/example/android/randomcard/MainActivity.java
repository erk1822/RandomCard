package com.example.android.randomcard;

import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private ImageView myImage;
    private ImageView yourImage;
    private ArrayList<String> cards;
    private ArrayList<String> yourCards;
    private ArrayList<String> myWarCardList;
    private ArrayList<String> yourWarCardList;
    private ArrayList<String> tiedCards;
    private TextView outputMyWins;
    private TextView outputYourWins;
    private TextView outputMyCards;
    private TextView outputYourCards;
    int myWins = 0;
    int yourWins = 0;
    int myCardCount = 52;
    int yourCardCount = 52;
    private Handler handler;
    int my = 0;
    int yours = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        myImage = findViewById(R.id.myImage);
        yourImage = findViewById(R.id.yourImage);
        outputMyWins = findViewById(R.id.outputMyWins);
        outputYourWins = findViewById(R.id.outputYourWins);
        outputMyCards = findViewById(R.id.outputMyCards);
        outputYourCards = findViewById(R.id.outputYourCards);
        handler = new Handler();

        myWarCardList = new ArrayList<String>();
        yourWarCardList = new ArrayList<String>();
        tiedCards = new ArrayList<String>();
        cards = new ArrayList<String>();

        AssetManager manager = getAssets();
        try {
            for (String filename : manager.list("Cards")) {
                cards.add(filename);
            }
        } catch (IOException e) {
            Toast.makeText(this, "BAD ASSETS!", Toast.LENGTH_LONG).show();
        }
        yourCards = (ArrayList<String>) cards.clone();
        Collections.shuffle(yourCards);
        Collections.shuffle(cards);
        System.out.println(cards);
        System.out.println(yourCards);


        if (savedInstanceState !=null) {
            outputMyCards.setText(savedInstanceState.getString("MyCards"));
            myCardCount=Integer.parseInt(outputMyCards.getText().toString());
            outputYourCards.setText(savedInstanceState.getString("YourCards"));
            yourCardCount=Integer.parseInt(outputYourCards.getText().toString());
            outputMyWins.setText(savedInstanceState.getString("MyWins"));
            myWins=Integer.parseInt(outputMyWins.getText().toString());
            outputYourWins.setText(savedInstanceState.getString("YourWins"));
            yourWins=Integer.parseInt(outputMyWins.getText().toString());
        }

    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putString("MyCards", outputMyCards.getText().toString());
        savedInstanceState.putString("YourCards", outputYourCards.getText().toString());
        savedInstanceState.putString("MyWins", outputMyWins.getText().toString());
        savedInstanceState.putString("YourWins", outputYourWins.getText().toString());
    }


    public void myImagePressed(View v) throws IOException {

        if (yourCardCount >= 1 && myCardCount >= 1) {

            myImage.setImageBitmap(BitmapFactory.decodeStream(getAssets().open("Cards/" + cards.get(my))));
            yourImage.setImageBitmap(BitmapFactory.decodeStream(getAssets().open("Cards/" + yourCards.get(yours))));
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
                myWins += 1;
                outputMyWins.setText(myWins + "");
                cards.add(yourCards.get(yours));
                myCardCount += 1;
                outputMyCards.setText(myCardCount + "");
                yourCards.remove(yourCards.get(yours));
                yourCardCount -= 1;
                outputYourCards.setText(yourCardCount + "");
                my += 1;
                if (my>cards.size()) {
                    my = 0;
                } if (yours>yourCards.size()) {
                    yours = 0;
                }

            } else if (myValueInt < yourValueInt) {
                Toast.makeText(this, "You Lose", Toast.LENGTH_SHORT).show();
                yourWins += 1;
                outputYourWins.setText(yourWins + "");
                yourCards.add(cards.get(my));
                yourCardCount += 1;
                outputYourCards.setText(yourCardCount + "");
                cards.remove(cards.get(my));
                myCardCount -= 1;
                outputMyCards.setText(myCardCount + "");
                yours += 1;
                if (my>cards.size()) {
                    my = 0;
                } if (yours>yourCards.size()) {
                    yours = 0;
                }

            } else if (myValueInt == yourValueInt) {
                Toast.makeText(this, "It's War!!", Toast.LENGTH_SHORT).show();

                tiedCards.add(cards.get(my));
                tiedCards.add(yourCards.get(yours));

                handler.postDelayed(new Update1(0), 1000);
            }

        } else {
            if (yourCardCount<1) {
                Toast.makeText(this, "Game Over, You Lose!!", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(this, "Game Over, You Lose!!", Toast.LENGTH_LONG).show();
            }
        }

    }

    private class Update1 implements Runnable {
        int counter;
        public Update1(int c) {
            counter = c;
        }

        @Override
        public void run() {

            int myWarCard = my+1;
            try {
                myImage.setImageBitmap(BitmapFactory.decodeStream(getAssets().open("Cards/" + cards.get(myWarCard))));
            } catch (Exception e) {

            }
            myWarCardList.add(cards.get(myWarCard));
            my+=1;


            int yourWarCard = yours+1;
            try {
                yourImage.setImageBitmap(BitmapFactory.decodeStream(getAssets().open("Cards/" + yourCards.get(yourWarCard))));
            } catch (Exception e) {

            }
            yourWarCardList.add(yourCards.get(yourWarCard));
            yours+=1;

            if (my>cards.size()) {
                my = 0;
            } if (yours>yourCards.size()) {
                yours = 0;
            }

            if (counter < 3) {
                handler.postDelayed(new Update1(counter + 1), 1000);
            } else {

                int my2=my;
                try {
                    myImage.setImageBitmap(BitmapFactory.decodeStream(getAssets().open("Cards/" + cards.get(my2))));
                } catch (Exception e){

                }
                int yours2=yours;
                try {
                    yourImage.setImageBitmap(BitmapFactory.decodeStream(getAssets().open("Cards/" + yourCards.get(yours2))));
                } catch (Exception e) {

                }

                int myValueInt2;
                int yourValueInt2;

                if (Character.isDigit(cards.get(my2).charAt(1))) {
                    String myValue2 = cards.get(my2).charAt(0) + cards.get(my2).charAt(1) + "";
                    myValueInt2 = Integer.parseInt(myValue2);
                } else {
                    if (cards.get(my2).charAt(0) == '1' && !Character.isDigit(cards.get(my2).charAt(1))) {
                        myValueInt2 = 14;
                    } else {
                        myValueInt2 = cards.get(my2).charAt(0) - '0';
                    }
                }

                if (Character.isDigit(yourCards.get(yours2).charAt(1))) {
                    String yourValue2 = yourCards.get(yours2).charAt(0) + yourCards.get(yours2).charAt(1) + "";
                    yourValueInt2 = Integer.parseInt(yourValue2);
                } else {
                    if (yourCards.get(yours2).charAt(0) == '1' && !Character.isDigit(yourCards.get(yours2).charAt(1))) {
                        yourValueInt2 = 14;
                    } else {
                        yourValueInt2 = yourCards.get(yours2).charAt(0) - '0';
                    }
                }


                if (myValueInt2 > yourValueInt2) {
                    Toast.makeText(getApplicationContext(), "You Win", Toast.LENGTH_SHORT).show();
                    myWins += 1;
                    outputMyWins.setText(myWins + "");
                    cards.add(yourCards.get(yours2));

                    for (int i = 0; i < 2; i++) {
                        cards.add(tiedCards.get(i));
                    }

                    for (int i = 0; i < 3; i++) {
                        cards.add(myWarCardList.get(i));
                        cards.add(yourWarCardList.get(i));
                    }

                    myCardCount += 5;
                    outputMyCards.setText(myCardCount + "");
                    yourCards.remove(yourCards.get(yours2));

                    yourCardCount -= 5;
                    outputYourCards.setText(yourCardCount + "");
                    my+=1;
                    if (my>cards.size()) {
                        my = 0;
                    } if (yours>yourCards.size()) {
                        yours = 0;
                    }

                } else if (myValueInt2 < yourValueInt2) {
                    Toast.makeText(getApplicationContext(), "You Lose", Toast.LENGTH_SHORT).show();
                    yourWins += 1;
                    outputYourWins.setText(yourWins + "");
                    yourCards.add(cards.get(my2));

                    for (int i = 0; i < 2; i++) {
                        yourCards.add(tiedCards.get(i));
                    }

                    for (int i = 0; i < 3; i++) {
                        yourCards.add(myWarCardList.get(i));
                        yourCards.add(yourWarCardList.get(i));
                    }

                    yourCardCount += 5;
                    outputYourCards.setText(yourCardCount + "");
                    cards.remove(cards.get(my2));

                    myCardCount -= 5;
                    outputMyCards.setText(myCardCount + "");
                    yours+=1;
                    if (my>cards.size()) {
                        my = 0;
                    } if (yours>yourCards.size()) {
                        yours = 0;
                    }
                }

            }
        }
    }
    public void rulesButtonPressed (View v) {
        Intent i = new Intent(this, RulesActivity.class);
        i.putExtra("CARDS", myCardCount);
        startActivity(i);
    }
}