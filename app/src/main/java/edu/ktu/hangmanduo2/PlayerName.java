package edu.ktu.hangmanduo2;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class PlayerName extends Activity {
    private Button playGameButton;
    private EditText playerNameInputText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.player_name);
        setUiElementClickEvents();
    }

    private void setUiElementClickEvents(){
        this.playGameButton = (Button) findViewById(R.id.playButton);
        this.playerNameInputText = (EditText) findViewById(R.id.playerNameTextInput);
        this.playGameButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userName = PlayerName.this.playerNameInputText.getText().toString();
                if(userName.length() == 0) {
                    Toast.makeText(PlayerName.this, "Please enter a name to play the game", Toast.LENGTH_LONG).show();
                } else {
                    Intent intent = new Intent(PlayerName.this, Game.class);
                    intent.putExtra("userName", userName);
                    startActivity(intent);
                    finish();
                }
            }
        });
    }
}
