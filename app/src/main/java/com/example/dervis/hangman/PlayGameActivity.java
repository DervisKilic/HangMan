package com.example.dervis.hangman;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Random;

/**
 * The game where the it will be played
 */
public class PlayGameActivity extends AppCompatActivity {

    private ArrayList<Boolean> visible = new ArrayList<>();
    private ArrayList<Character> characters = new ArrayList<>();
    private ArrayList<String> addLetters = new ArrayList<>();
    private ArrayList<String> correctLetterArray = new ArrayList<>();
    private EditText inputLetter;
    private String letter;
    private TextView guessedLetters;
    private TextView triesLeft;
    private TextView displayLetters;
    private ImageView changeHang;
    private String correctWord;
    private String guessedWrong = "";
    private int getImg;
    private ArrayList<String> wrongLetters = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_game);


        generateWord();
        createBoolean(generateWord());
        triesLeft = (TextView) findViewById(R.id.tries_left);
        inputLetter = (EditText) findViewById(R.id.guess_letter);
        letter = inputLetter.getText().toString();
        guessedLetters = (TextView) findViewById(R.id.letters_guessed);
        triesLeft.setText(10 - wrongLetters.size() + " försök kvar");
        changeHang = (ImageView) findViewById(R.id.hang_img);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        menu.getItem(0).setVisible(false);
        return true;
    }

    @Override
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

        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * @return generates a random word
     */
    public String generateWord() {
        String words = "KATT:RABIESHUND:CHRISTER:PETTERSSON:HELVETE:SATAN:FREDRIKGEMIGVG:PLEASE:FREDRIKMYHERO:SKOJABARA";
        String[] wordsAsArray = words.split(":");
        int i = new Random().nextInt(wordsAsArray.length);
        correctWord = wordsAsArray[i];
        return wordsAsArray[i];
    }

    /**
     * @param randomWord receives a random word and makes every letter to a boolean, and sets the value to false.
     *                   And every letter is set to a underline.
     */
    public void createBoolean(String randomWord) {
        displayLetters = (TextView) findViewById(R.id.correct_letters_et);
        String underline = "";
        for (int j = 0; j < randomWord.length(); j++) {
            visible.add(false);
            characters.add(randomWord.charAt(j));
            underline += "-";

        }
        displayLetters.setText(underline);
    }

    /**
     * @param view for every time the guess button is pressed, the method checks if it has a correct input and displays
     *             a message for incorrect letter or puts the letter in the right place of the word.
     */
    public void guessButton(View view) {
        displayLetters = (TextView) findViewById(R.id.correct_letters_et);
        inputLetter = (EditText) findViewById(R.id.guess_letter);
        letter = inputLetter.getText().toString().toUpperCase();
        String regexStr = "[a-zA-Z]";
        String correctLetterCheck = "";

        if (letter.isEmpty()) {
            Toast.makeText(this, "Du måste skriva in en bokstav", Toast.LENGTH_SHORT).show();
        } else if (letter.length() > 1) {
            Toast.makeText(this, "Skriv endast in en bokstav", Toast.LENGTH_SHORT).show();
        } else if (!letter.matches(regexStr)) {
            Toast.makeText(this, "Skriv en bokstav inte en siffra eller tecken", Toast.LENGTH_SHORT).show();

        } else if (guessedLetters(letter) || alreadyGuessedLetter()) {
            Toast.makeText(this, "Du hare redan gissat på bokstaven", Toast.LENGTH_SHORT).show();
        } else {
            boolean isRightLetter = false;
            for (int j = 0; j < characters.size(); j++) {

                if (letter.equals(String.valueOf(characters.get(j)))) {
                    visible.set(j, true);
                    isRightLetter = true;
                    correctLetterArray.add(letter);
                }
            }
            if (!isRightLetter) {
                wrongLetters.add(letter);
                displayWrongLetters();
                changeHangImg();
            }
            guessedLetters(letter);
            triesLeft.setText(10 - wrongLetters.size() + " försök kvar");
        }

        for (int i = 0; i < visible.size(); i++) {
            if (visible.get(i)) {
                correctLetterCheck += characters.get(i).toString();
            } else {
                correctLetterCheck += "-";
            }
        }
        displayLetters.setText(correctLetterCheck);
        showResult();
        inputLetter.setText("");
    }

    /**
     *
     * @param letter the input character from the player
     * @return returns true or false depending on if the player already guessed the wrong letter.
     */
    public boolean guessedLetters(String letter) {
        boolean doubleLetter = false;
        for (int i = 0; i < wrongLetters.size(); i++) {
            if (letter.equals(wrongLetters.get(i))) {
                doubleLetter = true;

            }
        }
        return doubleLetter;
    }

    /**
     * sends the player to the result activity. If the player has 0 guesses left and lost the game a message with tries left,
     * and the correct word is sent to the result screen. If the player won the gama a win screen appears instead.
     */
    public void showResult() {

        if (wrongLetters.size() > 9) {
            Intent intentResult = new Intent(this, ResultActivity.class);
            intentResult.putExtra(ResultActivity.TRIES, "Antalet försök kvar: 0");
            intentResult.putExtra(ResultActivity.MESSAGE, "Du Förlorade");
            intentResult.putExtra(ResultActivity.CORRECT_WORD, "Ordet var " + correctWord);
            startActivity(intentResult);
            finish();

        } else if (displayLetters.getText().toString().equals(correctWord)) {
            Intent intentResult = new Intent(this, ResultActivity.class);
            intentResult.putExtra(ResultActivity.TRIES, "Antalet försök kvar: " + (10 - wrongLetters.size()));
            intentResult.putExtra(ResultActivity.MESSAGE, "Du Vann!");
            intentResult.putExtra(ResultActivity.CORRECT_WORD, "Ordet var " + correctWord);
            startActivity(intentResult);
            finish();
        }
    }

    /**
     * displays and adds wrong letters for the player
     */
    public void displayWrongLetters() {
        String wrongLettersString = "";
        for (int i = 0; i < wrongLetters.size(); i++) {
            wrongLettersString += wrongLetters.get(i);

        }
        guessedLetters.setText("Gissade bokstäver: " + wrongLettersString);
    }

    /**
     * changes the hangman image every time the player guessed wrong
     */
    public void changeHangImg() {
        guessedWrong += letter + ",";
        guessedLetters.setText("Gissade bokstäver: " + guessedWrong);
        getImg = getResources().getIdentifier("hang" + (10 - (wrongLetters.size())), "drawable", getPackageName());
        changeHang.setImageResource(getImg);
    }

    /**
     *
     * @return returns true or false depending on if correct letter is already guessed
     */
    public boolean alreadyGuessedLetter() {
        Boolean alreadyInput = false;
        for (int i = 0; i < correctLetterArray.size(); i++) {
            if (correctLetterArray.get(i).equals(letter)) {
                alreadyInput = true;
            }
        }
        return alreadyInput;
    }

}