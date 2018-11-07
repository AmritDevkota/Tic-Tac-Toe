package np.com.amritdevkota.tictactoe;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button[][] buttons = new Button[3][3];

    private boolean player1Turn = true;

    private int roundCount; // Count round in a game, max is 9

    private int player1Points; // Point by Player X
    private int player2Points; // Points by Player O
    private int totalGamePlay; //total no of Play

    private TextView textViewPlayer1;
    private TextView textViewPlayer2;

    private Button player1TurnButton;
    private Button player2TurnButton;
    private Button resetBoardButton;

    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        totalGamePlay = 0;

        textViewPlayer1 = findViewById(R.id.text_view_p1);
        textViewPlayer2 = findViewById(R.id.text_view_p2);
        resetBoardButton = findViewById(R.id.resetBoardButton);

        enableClick();

        context = this;

        //To set text of all button to " " when start
        for (int i=0; i<3; i++) {
            for (int j=0; j<3; j++) {
                buttons[i][j].setText(" ");
            }
        }

        Button buttonReset = findViewById(R.id.button_reset);
        buttonReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resetGame();
            }
        });

        player1TurnButton = findViewById(R.id.player1_turn_button);
        player2TurnButton = findViewById(R.id.player2_turn_button);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.mainmenu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.aboutApp:
                Intent aintent = new Intent(context, AboutActivity.class);
                context.startActivity(aintent);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    // Set The reset Board Button Visible when a game finished
    public void showResetBoardButton(){
        resetBoardButton.setVisibility(View.VISIBLE);
    }

    // Hide the reset Board Button when user clicks in it. GONE will not take any space in layout
    public void hideResetBoardButton(){
        resetBoardButton.setVisibility(View.GONE);
    }

    //set the Green Indicator Visible or Invisible based on Player Turn
    public void turnIndicator(){
        if(player1Turn){
            player2TurnButton.setVisibility(View.VISIBLE);
            player1TurnButton.setVisibility(View.INVISIBLE);
        } else {
            player1TurnButton.setVisibility(View.VISIBLE);
            player2TurnButton.setVisibility(View.INVISIBLE);
        }
    }

    // Reset The Player Turn indicator when next game starts
    public void turnIndicatorReset(){
        player1TurnButton.setVisibility(View.VISIBLE);
        player2TurnButton.setVisibility(View.INVISIBLE);
    }

    // Stuffs to update when a Round is Complete ie when draw or win
    private void roundComplete(){
        totalGamePlay++;
        disableClick();
        showResetBoardButton();
        turnIndicatorReset();
        updatePointsText();
    }

    @Override
    public void onClick(View view) {
        if (!((Button) view).getText().toString().equals(" ") ){
            return;
        }
        if (player1Turn){
            ((Button) view).setText("X");
            turnIndicator();
        } else {
            ((Button) view).setText("O");
            turnIndicator();
        }

        roundCount++;

        if (checkForWin()) {
            if(player1Turn) {
                roundComplete();
                player1Wins();
            } else {
                roundComplete();
                player2Wins();
            }
        } else if (roundCount == 9) {
            roundComplete();
            draw();
        } else {
            player1Turn = !player1Turn;
        }
    }

    //Check Win Condition
    private boolean checkForWin() {
        String[][] field = new String [3][3];

        for (int i=0; i<3; i++) {
            for (int j=0; j<3; j++) {
                field[i][j] = buttons[i][j].getText().toString();
            }
        }

        for (int i=0; i<3; i++) {
            if (field[i][0].equals(field[i][1])
                    && field[i][0].equals(field[i][2])
                    && !field[i][0].equals(" ")) {
                return true;
            }
        }

        for (int i=0; i<3; i++) {
            if (field[0][i].equals(field[1][i])
                    && field[0][i].equals(field[2][i])
                    && !field[0][i].equals(" ")) {
                return true;
            }
        }

        if (field[0][0].equals(field[1][1])
                && field[0][0].equals(field[2][2])
                && !field[0][0].equals(" ")) {
            return true;
        }

        if (field[0][2].equals(field[1][1])
                && field[0][2].equals(field[2][0])
                && !field[0][2].equals(" ")) {
            return true;
        }
        return false;
    }

    //Update Player Score, display TOAST message
    private void player1Wins() {
        player1Points++;
        Toast.makeText(this,"Player X Wins", Toast.LENGTH_SHORT).show();
        updatePointsText();
    }

    //Update Player2 Score, display TOAST message
    private void player2Wins() {
        player2Points++;
        Toast.makeText(this, "Player O Wins", Toast.LENGTH_SHORT).show();
        updatePointsText();
    }

    //When A game is draw display message
    private void draw () {
        Toast.makeText(this, "Draw", Toast.LENGTH_SHORT).show();
    }

    //Update Points in the screen
    private void updatePointsText() {
        textViewPlayer1.setText("Player X: " + player1Points + " / " + totalGamePlay);
        textViewPlayer2.setText("Player O: " + player2Points + " / " + totalGamePlay);
    }

    // Reset Board ie clear the buttons
    private void resetBoard() {
        for (int i=0; i<3; i++) {
            for (int j=0; j<3; j++) {
                buttons[i][j].setText(" ");
            }
        }
        roundCount = 0;
        player1Turn = true;
        turnIndicatorReset();
        hideResetBoardButton();
        enableClick();
    }

    // Called When ResetBoard button is clicked will call resetBoard()
    public void resetBoardView(View view){
        resetBoard();
    }

    // Reset The overall game Scores to Zero
    private void resetGame() {
        totalGamePlay = 0;
        player1Points = 0;
        player2Points = 0;
        updatePointsText();
        resetBoard();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putInt("roundCount", roundCount);
        outState.putInt("player1Points", player1Points);
        outState.putInt("player2Points", player2Points);
        outState.putBoolean("player1Turn", player1Turn);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        roundCount = savedInstanceState.getInt("roundCount");
        player1Points = savedInstanceState.getInt("player1Points");
        player2Points = savedInstanceState.getInt("player2Points");
        player1Turn = savedInstanceState.getBoolean("player1Turn");
    }

    // Remove click Listeners in all buttons
    private void disableClick(){
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j <3; j++) {
                String buttonID = "button_" + i + j;
                int resID = getResources().getIdentifier(buttonID,"id",getPackageName());
                buttons[i][j] = findViewById(resID);
                buttons[i][j].setOnClickListener(null);
            }
        }
    }

    // Add click listeners in all buttons
    private void enableClick(){
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j <3; j++) {
                String buttonID = "button_" + i + j;
                int resID = getResources().getIdentifier(buttonID,"id",getPackageName());
                buttons[i][j] = findViewById(resID);
                buttons[i][j].setOnClickListener(this);
            }
        }
    }
}
