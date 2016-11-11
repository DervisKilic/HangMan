package com.example.dervis.hangman;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;

import java.io.File;
import java.io.IOException;
import java.util.Date;

/**
 * @author Created by Dervis Kilic on 2016-10-31
 */

/**
 * the main menu screen
 */
public class MainActivity extends AppCompatActivity {
    private Uri photoPath;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
        } else if (id == R.id.action_play) {
            Intent intentPlay = new Intent(this, PlayGameActivity.class);
            startActivity(intentPlay);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * @param view displays a about button, if pressed takes the player to the
     *             about screen
     */
    public void about_button(View view) {
        Intent intent = new Intent(this, AboutActivity.class);
        startActivity(intent);
    }

    /**
     * @param view displays a play button, if pressed takes the player to the
     *             play game screen
     */
    public void play_game_button(View view) {
        Intent intent = new Intent(this, PlayGameActivity.class);
        startActivity(intent);
    }

    //CAMRA
    private static final int CAMERA_REQUEST_CODE = 1;

    public void showCamera(View view) throws IOException {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        if (intent.resolveActivity(getPackageManager()) != null) {
            File photo = createFile();
            photoPath = Uri.fromFile((photo));
            intent.putExtra(MediaStore.EXTRA_OUTPUT, photoPath);
            startActivityForResult(intent, CAMERA_REQUEST_CODE);
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CAMERA_REQUEST_CODE) {

            if (resultCode == RESULT_OK) {
                showImage();
            }
        }
    }
    private File createFile() throws IOException{
        File dir = getDirectory();
        File file = new File(dir, "iths.jpg" + (new Date().getTime()));
        return file;
    }

    private File getDirectory() {
        File photoDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        File ithsDir = new File(photoDir, "ithsPics");

        if (!ithsDir.exists()){
            ithsDir.mkdirs();
        }
        return ithsDir;
    }

    private void showImage() {
        ImageView iv = (ImageView)findViewById(R.id.hej);
        int vWidth = iv.getWidth();
        int vHeight = iv.getHeight();

        BitmapFactory.Options opt = new BitmapFactory.Options();
        opt.inJustDecodeBounds = true;

        BitmapFactory.decodeFile(photoPath.getPath(), opt);

        double scaleFactor = Math.min(opt.outWidth / vWidth, opt.outHeight/vHeight);


        opt = new BitmapFactory.Options();
        opt.inSampleSize = (int)scaleFactor;

        Bitmap image = BitmapFactory.decodeFile(photoPath.getPath(), opt);

        iv.setImageBitmap(image);

    }

}
