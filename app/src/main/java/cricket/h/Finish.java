package cricket.h;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import static cricket.h.SplashScreen.teamA;
import static cricket.h.SplashScreen.teamB;
import static cricket.h.TeamBActivity.minutesA;

public class Finish extends AppCompatActivity {

    private static int mteam;
    TextView winner, winnerName, loser, loserName;
    MediaPlayer draw, win;
    private MediaPlayer.OnCompletionListener mCompletionListener = new MediaPlayer.OnCompletionListener() {
        @Override
        public void onCompletion(MediaPlayer mediaPlayer) {
            releaseMediaPlayer();
        }
    };

    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle("<scorer.h>")
                .setMessage("Do you want to exit?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        byeToast();
                        finish();
                    }

                })
                .setNegativeButton("No", null)
                .show();
    }

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finish);
        getActionBar().setDisplayHomeAsUpEnabled(true);
        winner = (TextView) findViewById(R.id.winner);
        loser = (TextView) findViewById(R.id.loser);
        winnerName = (TextView) findViewById(R.id.winnerName);
        loserName = (TextView) findViewById(R.id.loserName);

        double minutesB = (TeamBActivity.endTimeB - TeamBActivity.startTimeB);
        minutesB = (minutesB / 1000000000) / 60;
        minutesB = Math.round(minutesB * 100.0) / 100.0;

        draw = MediaPlayer.create(this, R.raw.setting);
        win = MediaPlayer.create(this, R.raw.csk_whistle);

        if (mteam == 2) {

            winnerName.setText(teamB.getText().toString());
            loserName.setText(teamA.getText().toString());
            winner.setText(TeamBActivity.teamBscore + " -" + TeamBActivity.out + "\n" + TeamBActivity.over + "." + (6 - (int) TeamBActivity.balls) + " over(s)\n@(" + TeamBActivity.rpoCalc + ") r.p.o\n" + minutesB + " minute(s)");
            loser.setText(TeamAActivity.teamAscore + " -" + TeamAActivity.out + "\n" + TeamAActivity.over + "." + (6 - (int) TeamAActivity.balls) + " over(s)\n@(" + TeamAActivity.rpoCalc + ") r.p.o\n" + minutesA + " minute(s)");
            win.start();

        } else if (mteam == 1) {

            winnerName.setText(teamA.getText().toString());
            loserName.setText(teamB.getText().toString());
            win.start();
            win.setOnCompletionListener(mCompletionListener);


            loser.setText(TeamBActivity.teamBscore + " -" + TeamBActivity.out + "\n" + TeamBActivity.over + "." + (6 - (int) TeamBActivity.balls) + " over(s)\n@(" + TeamBActivity.rpoCalc + ") r.p.o\n" + minutesB + " minute(s)");
            winner.setText(TeamAActivity.teamAscore + " -" + TeamAActivity.out + "\n" + TeamAActivity.over + "." + (6 - (int) TeamAActivity.balls) + " over(s)\n@(" + TeamAActivity.rpoCalc + ") r.p.o\n" + minutesA + " minute(s)");

        } else if (mteam == 3 && minutesB < minutesA) {
            draw.start();
            winnerName.setText(teamB.getText().toString());
            loserName.setText(teamA.getText().toString());
            winner.setText(TeamBActivity.teamBscore + " -" + TeamBActivity.out + "\n" + TeamBActivity.over + "." + (6 - (int) TeamBActivity.balls) + " over(s)\n@(" + TeamBActivity.rpoCalc + ") r.p.o\n" + minutesB + " minute(s)");
            loser.setText(TeamAActivity.teamAscore + " -" + TeamAActivity.out + "\n" + TeamAActivity.over + "." + (6 - (int) TeamAActivity.balls) + " over(s)\n@(" + TeamAActivity.rpoCalc + ") r.p.o\n" + minutesA + " minute(s)");
        } else {
            draw.start();
            winnerName.setText(teamA.getText().toString());
            loserName.setText(teamB.getText().toString());
            loser.setText(TeamBActivity.teamBscore + " -" + TeamBActivity.out + "\n" + TeamBActivity.over + "." + (6 - (int) TeamBActivity.balls) + " over(s)\n@(" + TeamBActivity.rpoCalc + ") r.p.o\n" + minutesB + " minute(s)");
            winner.setText(TeamAActivity.teamAscore + " -" + TeamAActivity.out + "\n" + TeamAActivity.over + "." + (6 - (int) TeamAActivity.balls) + " over(s)\n@(" + TeamAActivity.rpoCalc + ") r.p.o\n" + minutesA + " minute(s)");
        }
    }

    private void releaseMediaPlayer() {
        // If the media player is not null, then it may be currently playing a sound.
        if (win != null) {
            // Regardless of the current state of the media player, release its resources
            // because we no longer need it.
            win.release();

            // Set the media player back to null. For our code, we've decided that
            // setting the media player to null is an easy way to tell that the media player
            // is not configured to play an audio file at the moment.
            win = null;
        }
    }

    public static void getReport(int team) {
        mteam = team;
    }

    public void restart(View view) {
        win.stop();
        draw.stop();

        Intent i = getBaseContext().getPackageManager()
                .getLaunchIntentForPackage(getBaseContext().getPackageName());
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(i);

        finish();

    }

    public void exit(View view) {

        AlertDialog.Builder builder = new AlertDialog.Builder(Finish.this);
        builder.setTitle(R.string.app_name);
        builder.setIcon(R.mipmap.ic_launcher);
        builder.setMessage("Do you want to exit?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        byeToast();
                        finish();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();


    }

    public void byeToast() {
        Toast.makeText(Finish.this, "See you soon...", Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onPause() {

        super.onPause();
        win.pause();
        draw.pause();
        releaseMediaPlayer();
    }


}
