package com.example.roshambo;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AnticipateOvershootInterpolator;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    Roshambo game = new Roshambo();
    ImageView playerImg;
    ImageView oppoImg;
    TextView winLabel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        playerImg = findViewById(R.id.imageViewYourMove); //Reference the players image view
        oppoImg = findViewById(R.id.imageViewOpponentMove); //Reference the opponent image view
        winLabel = findViewById(R.id.textViewResults); //Reference the text view that displays the game outcome
    }

    /**
     * Click event for the Rock Image View
     * @param view
     */
    public void pickRock(View view) {
        playerImg.setImageResource(R.drawable.rock);
        game.playerMakesMove(Roshambo.ROCK);
        opponentTurn();
        animate(playerImg, oppoImg);
        determineWinner();
    }

    /**
     * Click event for the Paper Image View
     * @param view
     */
    public void pickPaper(View view) {
        playerImg.setImageResource(R.drawable.paper);
        game.playerMakesMove(Roshambo.PAPER);
        opponentTurn();
        animate(playerImg, oppoImg);
        determineWinner();
    }

    /**
     * Click event for the Scissors Image View
     * @param view
     */
    public void pickScissors(View view) {
        playerImg.setImageResource(R.drawable.scissors);
        game.playerMakesMove(Roshambo.SCISSOR);
        opponentTurn();
        animate(playerImg, oppoImg);
        determineWinner();
    }

    /**
     * Animation for the icons
     * @param playerImg Player Image View
     * @param oppoImg Opponent Image View
     */
    private void animate(ImageView playerImg, ImageView oppoImg){
        ObjectAnimator animatorPlayer = ObjectAnimator.ofFloat(playerImg,
                "rotationX", 0f, 360f)
                .setDuration(500); //Animates the player icon, making it spins 360 degrees on the X axis

        ObjectAnimator animatorGame = ObjectAnimator.ofFloat(oppoImg,
                "rotationY", 0f, 360f)
                .setDuration(500); //Animates the opponent icon, making it spin 360 degrees on the Y axis

        AnimatorSet set = new AnimatorSet();
        set.playTogether(animatorGame, animatorPlayer);
        set.setInterpolator(new AnticipateOvershootInterpolator());
        set.start(); //Starts the animation
    }

    /**
     * Get the opponents move, and changes the opponents image view to reflect this
     */
    private void opponentTurn(){
        int opTurn = game.getGameMove();
        if(opTurn == Roshambo.ROCK){
            setOpponentImage("rock");
        }else if(opTurn == Roshambo.PAPER){
            setOpponentImage("paper");
        }else if(opTurn == Roshambo.SCISSOR){
            setOpponentImage("scissors");
        }
    }

    /**
     * Changes the opponents Image View image based on a string thats passed in
     * @param image The image to set the view to
     */
    private void setOpponentImage(String image){
        switch (image){
            case "rock":
                oppoImg.setImageResource(R.drawable.rock);
                break;
            case "paper":
                oppoImg.setImageResource(R.drawable.paper);
                break;
            case "scissors":
                oppoImg.setImageResource(R.drawable.scissors);
                break;
        }
    }

    /**
     * Gets the winner from the game, and changes the label to reflect if you have won, lost, or drew
     */
    private void determineWinner(){
        int result = game.winLoseOrDraw();
        if(result == Roshambo.DRAW){
            winLabel.setText("Draw");
        }
        else if(result == Roshambo.LOSE){
            winLabel.setText("You Lose");
        }
        else if(result == Roshambo.WIN){
            winLabel.setText("You Win!");
        }
    }
}
