package edu.ktu.hangmanduo2;
import java.io.IOException;
import java.io.InputStream;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Bundle;
import android.widget.ImageView;
public class AnimatedCharacter extends Activity{


    // frame width
    private static final int FRAME_W = 85;
    // frame height
    private static final int FRAME_H = 121;
    // number of frames
    private static final int NB_FRAMES = 14;
    // nb of frames in x
    private static final int COUNT_X = 5;
    // nb of frames in y
    private static final int COUNT_Y = 3;
    // frame duration
    // we can slow animation by changing frame duration
    private static final int FRAME_DURATION = 2000; // in ms !
    // scale factor for each frame
    private static final int SCALE_FACTOR = 2;
    private ImageView img;
    // stores each frame
    private Bitmap[] bmps;

    @SuppressLint("NewApi") @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.character_anim);
        img = (ImageView) findViewById(R.id.img);

        // load bitmap from assets
       // Bitmap birdBmp = getBitmapFromAssets(this, "grossini_dance.png");
        Bitmap birdBmp = BitmapFactory.decodeResource(getResources(), R.drawable.grossini_dance);

        if (birdBmp != null) {
            // cut bitmaps from bird bmp to array of bitmaps
            bmps = new Bitmap[NB_FRAMES];
            int currentFrame = 0;

            for (int i = 0; i < COUNT_Y; i++) {
                for (int j = 0; j < COUNT_X; j++) {
                    bmps[currentFrame] = Bitmap.createBitmap(birdBmp, FRAME_W
                            * j, FRAME_H * i, FRAME_W, FRAME_H);

                    // apply scale factor
                    bmps[currentFrame] = Bitmap.createScaledBitmap(
                            bmps[currentFrame], FRAME_W * SCALE_FACTOR, FRAME_H
                                    * SCALE_FACTOR, true);

                    if (++currentFrame >= NB_FRAMES) {
                        break;
                    }
                }
            }

            // create animation programmatically
            final AnimationDrawable animation = new AnimationDrawable();
            animation.setOneShot(false); // repeat animation

            for (int i = 0; i < NB_FRAMES; i++) {
                animation.addFrame(new BitmapDrawable(getResources(), bmps[i]),
                        FRAME_DURATION);
            }

            // load animation on image
            if (Build.VERSION.SDK_INT < 16) {
                img.setBackgroundDrawable(animation);
            } else {
                img.setBackground(animation);
            }

            // start animation on image
            img.post(new Runnable() {

                @Override
                public void run() {
                    animation.start();
                }

            });

        }
    }

    private Bitmap getBitmapFromAssets(AnimatedCharacter animatedCharacterActivity,
                                       String filepath) {
        AssetManager assetManager = animatedCharacterActivity.getAssets();
        InputStream istr = null;
        Bitmap bitmap = null;

        try {
            istr = assetManager.open(filepath);
            bitmap = BitmapFactory.decodeStream(istr);
        } catch (IOException ioe) {
            // manage exception
        } finally {
            if (istr != null) {
                try {
                    istr.close();
                } catch (IOException e) {
                }
            }
        }

        return bitmap;
    }
}
