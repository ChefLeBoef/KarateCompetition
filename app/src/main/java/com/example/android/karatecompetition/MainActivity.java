package com.example.android.karatecompetition;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    final int fiMAX_ROUNDS_TO_WIN = 2;
    final int fiMAX_LIFE = 100;
    final int fiPUNCH_DAMAGE = 5;
    final int fiKICK_DAMAGE = 10;
    final int fiSPECIAL_DAMAGE = 25;
    //final int fiMAX_PLAYERS = 2;
    final int fiPLAYER1 = 0;
    final int fiPLAYER2 = 1;
    final String fstrGameOverTxt = "Game Over!";
    final String fstrGameStartTxt = "Fight!";
    final String fstrLifeTotal = "/100";
    final String fstrPLAYER1 = "Daniel-san";
    final String fstrPLAYER2 = "Johnny";

    int iScorePlayer1 = 0;
    int iScorePlayer2 = 0;
    int iRound = 0;
    int iLifePlayer1 = fiMAX_LIFE;
    int iLifePlayer2 = fiMAX_LIFE;
    String strMoveArray[] = {"Punch", "Kick", "Crane Stance Kick", "Punch", "Kick", "Sweep The Leg"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    //Main functionality is done here
    public void doDamage(View view) {
        /*Handle the fight damage depending on the
        * button pressed*/
        int iPlayer;
        int iDamage;
        int iMoveType;
        String strPlayerWonRound = "";

        //Modify only in case not game over
        if (!boIsGameOver()) {

            switch (view.getId()) {
                case R.id.buttonPunch1:
                    iPlayer = fiPLAYER2;
                    iDamage = fiPUNCH_DAMAGE;
                    iMoveType = 0;
                    break;
                case R.id.buttonKick1:
                    iPlayer = fiPLAYER2;
                    iDamage = fiKICK_DAMAGE;
                    iMoveType = 1;
                    break;
                case R.id.buttonSpecial1:
                    iPlayer = fiPLAYER2;
                    iDamage = fiSPECIAL_DAMAGE;
                    iMoveType = 2;
                    break;
                case R.id.buttonPunch2:
                    iPlayer = fiPLAYER1;
                    iDamage = fiPUNCH_DAMAGE;
                    iMoveType = 3;
                    break;
                case R.id.buttonKick2:
                    iPlayer = fiPLAYER1;
                    iDamage = fiKICK_DAMAGE;
                    iMoveType = 4;
                    break;
                case R.id.buttonSpecial2:
                    iPlayer = fiPLAYER1;
                    iDamage = fiSPECIAL_DAMAGE;
                    iMoveType = 5;
                    break;
                default:
                    iPlayer = fiPLAYER1;
                    iDamage = fiPUNCH_DAMAGE;
                    iMoveType = 0;
                    break;
            }

            updateLifeTotals(iPlayer, iDamage);
            displayAction(strMoveArray[iMoveType]);
            //Check if a player has 0 life
            if (iLifePlayer1 <= 0) doRoundOverAction(fiPLAYER2);
            else if (iLifePlayer2 <= 0) doRoundOverAction(fiPLAYER1);

            if (boIsGameOver()) {
                strPlayerWonRound = (iScorePlayer1 == fiMAX_ROUNDS_TO_WIN) ?
                                    fstrPLAYER1 : fstrPLAYER2;

                displayHistory( String.format(  Locale.ENGLISH,
                                                "%s%s",
                                                strPlayerWonRound,
                                                " won the Match"));
                doGameOverAction();
            }
        } else {
            doGameOverAction();
        }

    }

    /*Decreases life totals for the parameter player */
    void updateLifeTotals(int iPlayer, int iDamage) {
        if (iPlayer == fiPLAYER1) iLifePlayer1 -= iDamage;
        else iLifePlayer2 -= iDamage;
        displayLifeTotal(iPlayer);
    }

    /*Display score in text box*/
    void updateScore(int iPlayer) {
        TextView scoreView;
        int iScore;
        if (iPlayer == fiPLAYER1) {
            scoreView = (TextView) findViewById(R.id.scorePlayer1);
            iScore = iScorePlayer1;
        }
        else {
            scoreView = (TextView) findViewById(R.id.scorePlayer2);
            iScore = iScorePlayer2;
        }
        scoreView.setText(String.valueOf(iScore));
    }

    /*Checks if one player has reached the max
     * number of round wins*/
    boolean boIsGameOver() {
        return ((iScorePlayer1 >= fiMAX_ROUNDS_TO_WIN) ||
                (iScorePlayer2 >= fiMAX_ROUNDS_TO_WIN));
    }
    //Display Game Over text
    void doGameOverAction() {
        displayAction(fstrGameOverTxt);
    }

    void displayAction(String status){
        TextView statusView = (TextView) findViewById(R.id.fightActionTxt);
        statusView.setText(status);
    }

    void doRoundOverAction(int iPLayer) {
        String strPlayerName;
        iLifePlayer1 = fiMAX_LIFE;
        iLifePlayer2 = fiMAX_LIFE;
        iRound++;
        displayLifeTotal(fiPLAYER1);
        displayLifeTotal(fiPLAYER2);

        if (iPLayer == fiPLAYER1) {
            strPlayerName = fstrPLAYER1;
            iScorePlayer1++;
        } else{
            strPlayerName = fstrPLAYER2;
            iScorePlayer2++;
        }

        updateScore(iPLayer);

        displayHistory( String.format(  Locale.ENGLISH,
                                        "%s%s%d",
                                        strPlayerName,
                                        " won Round ",
                                        iRound ));
    }

    void displayLifeTotal(int iPlayer){
        TextView lifeView;
        int iLifeTotal;
        if (iPlayer == fiPLAYER1) {
            lifeView = (TextView) findViewById(R.id.lifeBar1);
            iLifeTotal = iLifePlayer1;
        }
        else {
            lifeView = (TextView) findViewById(R.id.lifeBar2);
            iLifeTotal = iLifePlayer2;
        }

        lifeView.setText(String.format(Locale.ENGLISH, "%d%s", iLifeTotal, fstrLifeTotal));
    }

    void displayHistory(String move){
        TextView statusView = (TextView) findViewById(R.id.fightHistory);
        statusView.setText(move);
    }
    public void rematchFight(View view) {
        iLifePlayer1 = fiMAX_LIFE;
        iLifePlayer2 = fiMAX_LIFE;
        iScorePlayer1 = 0;
        iScorePlayer2 = 0;
        iRound = 0;
        displayAction(fstrGameStartTxt);
        displayHistory("");
        displayLifeTotal(fiPLAYER1);
        displayLifeTotal(fiPLAYER2);
        updateScore(fiPLAYER1);
        updateScore(fiPLAYER2);
        //Other functions
    }
}
