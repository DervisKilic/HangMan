package com.example.dervis.hangman;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

/**
 * the result screen
 */
public class ResultActivity extends AppCompatActivity {

    /**
     * the result message from play game activity.
     */
    public final static String MESSAGE = "Result message";
    /**
     * the tries left message from play game activity.
     */
    public final static String TRIES = "Tries left";
    /**
     * the correct word message from play game activity.
     */
    public final static String CORRECT_WORD = "Correct word";

    private TextView winOrLose;
    private TextView triesLeft;
    private TextView correctWord;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        winOrLose = (TextView) findViewById(R.id.win_loss_textview);
        winOrLose.setText(getIntent().getStringExtra(MESSAGE));

        triesLeft = (TextView) findViewById(R.id.tries_left);
        triesLeft.setText(getIntent().getStringExtra(TRIES));

        correctWord = (TextView) findViewById(R.id.correct_word);
        correctWord.setText(getIntent().getStringExtra(CORRECT_WORD));

    }

    /**
     * @param view a button that will take the player to the main menu
     */
    public void back_to_main(View view) {
        Intent intentAbout = new Intent(this, MainActivity.class);
        startActivity(intentAbout);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    /**
     * @param item displays menu item about icon and if pressed takes you to the about screen.
     * @return
     */
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_about) {
            Intent intentAbout = new Intent(this, AboutActivity.class);
            startActivity(intentAbout);
            return true;

        } else if (id == R.id.action_play) {
            Intent intentPlay = new Intent(this, PlayGameActivity.class);
            startActivity(intentPlay);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
