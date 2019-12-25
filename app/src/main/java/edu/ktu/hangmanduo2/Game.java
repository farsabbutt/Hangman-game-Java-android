package edu.ktu.hangmanduo2;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Animatable;
import android.graphics.drawable.Animatable2;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.provider.MediaStore;
import android.support.annotation.RequiresApi;
import android.util.LayoutDirection;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.Transformation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

/**
 * Created by Qosmio on 3/5/2018.
 */
// single player
public class Game extends Activity {
    private ArrayList<String> words;
    private ArrayList<String> hints = new ArrayList<>();
    private int remainingChances, correctlyGuessed, randomIntWordsIndex;
    private String wordToGuess;
    private String inputWord;
    private ImageView
            letterA, letterB,letterC,letterD,letterE,letterF,letterG,letterH,letterI,
            letterJ,letterK,letterL,letterM,letterN,letterO,letterP,letterQ,letterR,letterS,letterT,
            letterU,letterV,letterW,letterX,letterY,letterZ,
            mImgCheck,correct, wrong;

    private TextView inputWordText, hint, playerName;
    final ArrayList<TextView> textViews = new ArrayList<>();
    final ArrayList<ImageView> healthViews = new ArrayList<>();
    final ArrayList<String> alphabets = new ArrayList<>();
    final HashMap<String, ImageView> alphabetsImageViewHashMap = new HashMap<>();
    final HashMap<String, Integer> pressedAlphabetsHashMap = new HashMap<>();
    MediaPlayer bgMusic;
    MediaPlayer otherMedias;

    private Button anim;

    private String playerNameStr;

    private boolean gameWon, gameLost;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.game);
        resetAll();
        setupUIComponents();
        initializeWordsList();
        initializeWordsHints();
        setWordToGuess();
        setAlphabets();
        initializeLetterImages();
        setHashMap();
        setupLetterOnClicks();

        setTextViews();
        setHealthViews();
        setPressedAlphabetsHashMap();

        Intent PlayerNameIntent = getIntent();

        this.playerNameStr = PlayerNameIntent.getStringExtra("userName");
        String playerNameFullStr = "Player: " + this.playerNameStr;
        this.playerName.setText(playerNameFullStr);
    }

    private void setPressedAlphabetsHashMap() {
        this.pressedAlphabetsHashMap.put("a", R.drawable.letter_a_pressed);
        this.pressedAlphabetsHashMap.put("b", R.drawable.letter_b_pressed);
        this.pressedAlphabetsHashMap.put("c", R.drawable.letter_c_pressed);
        this.pressedAlphabetsHashMap.put("d", R.drawable.letter_d_pressed);
        this.pressedAlphabetsHashMap.put("e", R.drawable.letter_e_pressed);
        this.pressedAlphabetsHashMap.put("f", R.drawable.letter_f_pressed);
        this.pressedAlphabetsHashMap.put("g", R.drawable.letter_g_pressed);
        this.pressedAlphabetsHashMap.put("h", R.drawable.letter_h_pressed);
        this.pressedAlphabetsHashMap.put("i", R.drawable.letter_i_pressed);
        this.pressedAlphabetsHashMap.put("j", R.drawable.letter_j_pressed);
        this.pressedAlphabetsHashMap.put("k", R.drawable.letter_k_pressed);
        this.pressedAlphabetsHashMap.put("l", R.drawable.letter_l_pressed);
        this.pressedAlphabetsHashMap.put("m", R.drawable.letter_m_pressed);
        this.pressedAlphabetsHashMap.put("n", R.drawable.letter_n_pressed);
        this.pressedAlphabetsHashMap.put("o", R.drawable.letter_o_pressed);
        this.pressedAlphabetsHashMap.put("p", R.drawable.letter_p_pressed);
        this.pressedAlphabetsHashMap.put("q", R.drawable.letter_q_pressed);
        this.pressedAlphabetsHashMap.put("r", R.drawable.letter_r_pressed);
        this.pressedAlphabetsHashMap.put("s", R.drawable.letter_s_pressed);
        this.pressedAlphabetsHashMap.put("t", R.drawable.letter_t_pressed);
        this.pressedAlphabetsHashMap.put("u", R.drawable.letter_u_pressed);
        this.pressedAlphabetsHashMap.put("v", R.drawable.letter_v_pressed);
        this.pressedAlphabetsHashMap.put("w", R.drawable.letter_w_pressed);
        this.pressedAlphabetsHashMap.put("x", R.drawable.letter_x_pressed);
        this.pressedAlphabetsHashMap.put("y", R.drawable.letter_y_pressed);
        this.pressedAlphabetsHashMap.put("z", R.drawable.letter_z_pressed);
    }

    private void setAlphabets() {
        this.alphabets.add("a");
        this.alphabets.add("b");
        this.alphabets.add("c");
        this.alphabets.add("d");
        this.alphabets.add("e");
        this.alphabets.add("f");
        this.alphabets.add("g");
        this.alphabets.add("h");
        this.alphabets.add("i");
        this.alphabets.add("j");
        this.alphabets.add("k");
        this.alphabets.add("l");
        this.alphabets.add("m");
        this.alphabets.add("n");
        this.alphabets.add("o");
        this.alphabets.add("p");
        this.alphabets.add("q");
        this.alphabets.add("r");
        this.alphabets.add("s");
        this.alphabets.add("t");
        this.alphabets.add("u");
        this.alphabets.add("v");
        this.alphabets.add("w");
        this.alphabets.add("x");
        this.alphabets.add("y");
        this.alphabets.add("z");

    }

    private void initializeLetterImages() {
        letterA = (ImageView) findViewById(R.id.letter_a);
        letterB = (ImageView) findViewById(R.id.letter_b);
        letterC = (ImageView) findViewById(R.id.letter_c);
        letterD = (ImageView) findViewById(R.id.letter_d);
        letterE = (ImageView) findViewById(R.id.letter_e);
        letterF = (ImageView) findViewById(R.id.letter_f);
        letterG = (ImageView) findViewById(R.id.letter_g);
        letterH = (ImageView) findViewById(R.id.letter_h);
        letterI = (ImageView) findViewById(R.id.letter_i);
        letterJ = (ImageView) findViewById(R.id.letter_j);
        letterK = (ImageView) findViewById(R.id.letter_k);
        letterL = (ImageView) findViewById(R.id.letter_l);
        letterM = (ImageView) findViewById(R.id.letter_m);
        letterN = (ImageView) findViewById(R.id.letter_n);
        letterO = (ImageView) findViewById(R.id.letter_o);
        letterP = (ImageView) findViewById(R.id.letter_p);
        letterQ = (ImageView) findViewById(R.id.letter_q);
        letterR = (ImageView) findViewById(R.id.letter_r);
        letterS = (ImageView) findViewById(R.id.letter_s);
        letterT = (ImageView) findViewById(R.id.letter_t);
        letterU = (ImageView) findViewById(R.id.letter_u);
        letterV = (ImageView) findViewById(R.id.letter_v);
        letterW = (ImageView) findViewById(R.id.letter_w);
        letterX = (ImageView) findViewById(R.id.letter_x);
        letterY = (ImageView) findViewById(R.id.letter_y);
        letterZ = (ImageView) findViewById(R.id.letter_z);
    }

    private void setHashMap() {
        alphabetsImageViewHashMap.put("a", letterA);
        alphabetsImageViewHashMap.put("b", letterB);
        alphabetsImageViewHashMap.put("c", letterC);
        alphabetsImageViewHashMap.put("d", letterD);
        alphabetsImageViewHashMap.put("e", letterE);
        alphabetsImageViewHashMap.put("f", letterF);
        alphabetsImageViewHashMap.put("g", letterG);
        alphabetsImageViewHashMap.put("h", letterH);
        alphabetsImageViewHashMap.put("i", letterI);
        alphabetsImageViewHashMap.put("j", letterJ);
        alphabetsImageViewHashMap.put("k", letterK);
        alphabetsImageViewHashMap.put("l", letterL);
        alphabetsImageViewHashMap.put("m", letterM);
        alphabetsImageViewHashMap.put("n", letterN);
        alphabetsImageViewHashMap.put("o", letterO);
        alphabetsImageViewHashMap.put("p", letterP);
        alphabetsImageViewHashMap.put("q", letterQ);
        alphabetsImageViewHashMap.put("r", letterR);
        alphabetsImageViewHashMap.put("s", letterS);
        alphabetsImageViewHashMap.put("t", letterT);
        alphabetsImageViewHashMap.put("u", letterU);
        alphabetsImageViewHashMap.put("v", letterV);
        alphabetsImageViewHashMap.put("w", letterW);
        alphabetsImageViewHashMap.put("x", letterX);
        alphabetsImageViewHashMap.put("y", letterY);
        alphabetsImageViewHashMap.put("z", letterZ);

    }

    private void setHealthViews() {
        final RelativeLayout rl = (RelativeLayout) findViewById(R.id.health_rel_layout);
        for(int x=0; x < this.remainingChances; x++){
            healthViews.add(new ImageView(this));

            healthViews.get(x).setId(x+1);
            healthViews.get(x).setImageResource(R.drawable.heart);
            healthViews.get(x).setPadding(40,24,0,0);


        }

        // adding the image views to the layout
        for(int y = 0; y < healthViews.size(); y++ ){
            RelativeLayout.LayoutParams params=new RelativeLayout.LayoutParams
                    ((int) RelativeLayout.LayoutParams.WRAP_CONTENT,(int) RelativeLayout.LayoutParams.WRAP_CONTENT);


            params.width = 250;
            params.height = 250;
            if(y > 0) {
                params.leftMargin = 20;
                params.addRule(RelativeLayout.RIGHT_OF, healthViews.get(y - 1).getId());
                params.addRule(RelativeLayout.END_OF, healthViews.get(y - 1).getId());
            }
            healthViews.get(y).setLayoutParams(params);
            rl.addView(healthViews.get(y));
        }

    }

    private void setTextViews() {
        final RelativeLayout relative_layout = (RelativeLayout) findViewById(R.id.rel_layout);


        for(int x=0; x< this.wordToGuess.length(); x++){
            textViews.add(new TextView(this));


           // params.topMargin  = x*50;
            textViews.get(x).setId(x+1);
            textViews.get(x).setText("");
            textViews.get(x).setTextSize( (float) 20 );
            textViews.get(x).setTextColor(Color.WHITE);
         //   textViews.get(x).setPadding(20,50,20,50);
            textViews.get(x).setPadding(40,24,0,0);
            textViews.get(x).setTypeface(null, Typeface.BOLD);

            textViews.get(x).setBackgroundResource(R.drawable.letter_button);


        }

       for(int y = 0; y < textViews.size(); y++ ){
           RelativeLayout.LayoutParams params=new RelativeLayout.LayoutParams
                   ((int) RelativeLayout.LayoutParams.WRAP_CONTENT,(int) RelativeLayout.LayoutParams.WRAP_CONTENT);


            params.width = 140;
            params.height = 150;
           if(y > 0) {
               params.leftMargin = 20;
               params.addRule(RelativeLayout.RIGHT_OF, textViews.get(y - 1).getId());
               params.addRule(RelativeLayout.END_OF, textViews.get(y - 1).getId());
           }
           textViews.get(y).setLayoutParams(params);
           relative_layout.addView(textViews.get(y));
       }


    }


    private void setupUIComponents() {

        // set bg music
        bgMusic = MediaPlayer.create(this, R.raw.bgmusicmp3);
        bgMusic.setLooping(true);
        bgMusic.start();

        mImgCheck = (ImageView) findViewById(R.id.imageView);
        correct = (ImageView) findViewById(R.id.correct);
        wrong = (ImageView) findViewById(R.id.wrong);

        correct.bringToFront();
        wrong.bringToFront();

        correct.setVisibility(View.GONE);
        wrong.setVisibility(View.GONE);


       anim = (Button) findViewById(R.id.anim);
        // anim button
        anim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Game.this, AnimatedCharacter.class);
                startActivity(intent);
                finish();
            }
        });


        // hint text
        this.hint = (TextView) findViewById(R.id.hint);
        this.hint.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                Toast.makeText(Game.this, "This is a hint: " + Game.this.hints.get(Game.this.randomIntWordsIndex), Toast.LENGTH_LONG).show();
            }
        });

        this.playerName = (TextView) findViewById(R.id.playerName);
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    private void setupLetterOnClicks() {



        for( int x=0; x<alphabetsImageViewHashMap.size(); x++ ){

            alphabetsImageViewHashMap.get(this.alphabets.get(x)).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String tag = (String) view.getTag();
                    animateLetter(view);
                    evaluateLetter(tag);
                }
            });
        }
    }

    @TargetApi(Build.VERSION_CODES.M)
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    private void animateLetter(View view) {

        // letter pressed animation
        Animation letterZoomAnimation = AnimationUtils.loadAnimation(this, R.anim.test);
        view.startAnimation(letterZoomAnimation);
        // change color
 //       letterA.setImageResource(R.drawable.letter_a_pressed);
        // get tag
        String tag = (String) view.getTag();

        view.setBackgroundResource(this.pressedAlphabetsHashMap.get(tag));
        // disable letter
        view.setOnClickListener(null);


        // check mark animation
//        final Animatable2 anim = (Animatable2) mImgCheck.getDrawable();
//        mImgCheck.setVisibility(View.VISIBLE);
//        anim.start();
//        anim.registerAnimationCallback(new Animatable2.AnimationCallback() {
//            @Override
//            public void onAnimationEnd(Drawable drawable) {
//                mImgCheck.setVisibility(View.INVISIBLE);
//            }
//
//
//        });


    }

    private void evaluateLetter(String tag) {
        // important fix, need to capitalize alphabet.


        int index = this.wordToGuess.indexOf(tag.toUpperCase());
        Log.d("inndex", "" + index);
        ArrayList<Integer> indexes = new ArrayList<>();

        for(int t=0; t < wordToGuess.length(); t++ ){
            if(wordToGuess.charAt(t) == tag.toUpperCase().charAt(0)){
                indexes.add(t);
                // correctly guessed add score
                correctlyGuessed++;
            }
        }

        // if the letter guessed is correct
        if(!indexes.isEmpty()) {

            // play a sound
            otherMedias = MediaPlayer.create(this, R.raw.correct);
            otherMedias.start();

            for (int x = 0; x < indexes.size(); x++) {
                textViews.get(indexes.get(x)).setText(tag.toUpperCase());
            }

            // correct png animation
            Animation correctAnimation = AnimationUtils.loadAnimation(this,R.anim.correct);
            correctAnimation.setAnimationListener(
                    new Animation.AnimationListener() {
                        @Override
                        public void onAnimationStart(Animation animation) {
                            correct.setVisibility(View.VISIBLE);
                        }

                        @Override
                        public void onAnimationEnd(Animation animation) {
                            correct.setVisibility(View.INVISIBLE);
                        }

                        @Override
                        public void onAnimationRepeat(Animation animation) {

                        }
                    }
            );
            correct.startAnimation(correctAnimation);

            // otherwise penalty, minus score
        }else{

            // play a sound
            otherMedias = MediaPlayer.create(this, R.raw.wrong);
            otherMedias.start();

            this.remainingChances--;
            healthViews.get(remainingChances).setVisibility(View.INVISIBLE);
           // Toast.makeText(Game.this,"TEEE",Toast.LENGTH_LONG).show();
            Animation wrongAnimation = AnimationUtils.loadAnimation(this,R.anim.wrong);
            wrongAnimation.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {
                    wrong.setVisibility(View.VISIBLE);
                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    wrong.setVisibility(View.INVISIBLE);
                }

                @Override
                public void onAnimationRepeat(Animation animation) {

                }
            });

            wrong.startAnimation(wrongAnimation);


        }

        checkWinOrLose();

    }

    private void checkWinOrLose(){
        // check loss
        if(remainingChances == 0){
            // game lost.
            Toast.makeText(Game.this, "You lost the game!", Toast.LENGTH_LONG).show();
            Toast.makeText(Game.this, "The correct word was: " + wordToGuess, Toast.LENGTH_LONG).show();

        }else if(correctlyGuessed == wordToGuess.length()){
            // game won.
//            Intent intent = new Intent(Game.this, AnimatedCharacter.class);
//            startActivity(intent);
//            finish();
            Toast.makeText(Game.this, "You won the game!", Toast.LENGTH_LONG).show();
            resetAll();
            Intent intent = new Intent(Game.this, Game.class);
            intent.putExtra("userName", this.playerNameStr);
            startActivity(intent);
            finish();
        }

    };

    private void resetAll() {
        this.remainingChances = 5;
        this.wordToGuess = "";
        this.inputWord = "";
    }

    private void setWordToGuess() {
        Random r = new Random();
        int Low = 0;
        int High = this.words.size();
        this.randomIntWordsIndex = r.nextInt(High-Low) + Low;
        this.wordToGuess = this.words.get(this.randomIntWordsIndex);
        Log.d("wrd" , "Word to guess" + this.wordToGuess);
    }

    private void initializeWordsList() {
        this.words = new ArrayList<>();
        this.words.add("CARROT");
        this.words.add("APPLE");
        this.words.add("AMERICA");
        this.words.add("LAHORE");
        this.words.add("LUNGS");
    }

    private void initializeWordsHints() {
        this.hints.add("A vegetable");
        this.hints.add("A fruit");
        this.hints.add("A country");
        this.hints.add("A city");
        this.hints.add("A body part");
    }






}
