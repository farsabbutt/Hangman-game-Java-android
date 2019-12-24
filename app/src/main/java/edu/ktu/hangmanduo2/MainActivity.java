package edu.ktu.hangmanduo2;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

/**
 * Created by Qosmio on 3/5/2018.
 */

public class MainActivity extends Activity {
    private ImageView singlePlayerButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        setButtonOnClicks();
    }

    private void setButtonOnClicks() {
        this.singlePlayerButton = (ImageView) findViewById(R.id.singlePlayerButton);
        this.singlePlayerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, PlayerName.class);
                startActivity(intent);
                finish();
            }
        });
    }
}
