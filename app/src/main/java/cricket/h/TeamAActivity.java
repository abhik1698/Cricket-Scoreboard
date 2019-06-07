package cricket.h;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class TeamAActivity extends AppCompatActivity {
    private static final String TAG = "history";
    public static MediaPlayer mediaPlayer;
    public static TextView overs;
    public static int teamAscore = 0;
    public static int over = 0, out;
    public static long startTimeA, endTimeA;
    public static double rpoCalc, balls = 6, custOver;
    TextView runs, wickets, ballsView;
    int ballCount = 1, mvalue;
    Button one, two, three, four, six, wicket, wide, noball, dotball;
    private String currentBall, overScore = null;
    private int intro = 1;
    private TextView rpo;
    static ArrayList<String> overLog = new ArrayList<String>();
    private int overIndex;
    String display = "";

    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle("<scorer.h>")
                .setMessage("Do you want to exit?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                        Toast.makeText(TeamAActivity.this, "See you soon...", Toast.LENGTH_LONG).show();
                    }

                })
                .setNegativeButton("No", null)
                .show();
    }

    @SuppressLint("ResourceAsColor")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.team_a_layout);

        if (!(SplashScreen.teamA.getText().toString().length() > 0))
            SplashScreen.teamA.setText("Team A");

        setTitle(SplashScreen.teamA.getText().toString());

        rpoCalc = 0;
        teamAscore = 0;
        out = 0;
        balls = 6;
        ballCount = 1;
        over = 0;
        overIndex = 0;
        currentBall = "";
        display = "";
        mediaPlayer = MediaPlayer.create(this, R.raw.intro);
        if (intro == 1) {
            mediaPlayer.start();
            intro++;
        }

    }

    public void action(View view) {

        Button one = (Button) findViewById(R.id.one);
        Button two = (Button) findViewById(R.id.two);
        Button three = (Button) findViewById(R.id.three);
        Button four = (Button) findViewById(R.id.four);
        Button six = (Button) findViewById(R.id.six);
        Button wicket = (Button) findViewById(R.id.wicket);
        Button wide = (Button) findViewById(R.id.wide);
        Button noball = (Button) findViewById(R.id.noball);
        Button dotball = (Button) findViewById(R.id.dotball);
        Button allout = (Button) findViewById(R.id.allOut);

        switch (view.getId()) {
            case R.id.one:
                show(1, 1);
                break;
            case R.id.two:
                show(2, 2);
                break;
            case R.id.three:
                show(3, 3);
                break;
            case R.id.four:
                show(4, 4);
                break;
            case R.id.six:
                show(6, 6);
                break;
            case R.id.wicket:
                out += 1;
                show(-1, -1);
                break;
            case R.id.wide:
                ballCount = -1;
                show(33, 1);
                break;
            case R.id.noball:
                ballCount = -1;
                show(22, 1);
                break;
            case R.id.dotball:
                show(69, 0);
                break;

            case R.id.allOut:
                six.setEnabled(false);
                four.setEnabled(false);
                three.setEnabled(false);
                two.setEnabled(false);
                one.setEnabled(false);
                wide.setEnabled(false);
                noball.setEnabled(false);
                dotball.setEnabled(false);
                wicket.setEnabled(false);
                endTimeA = System.nanoTime();
                Intent intent = new Intent(TeamAActivity.this, TeamBActivity.class);
                startActivity(intent);
                finish();
                break;
//            case R.id.log:
//                Intent check = new Intent(this, OverLogs.class);
//                startActivity(check);
//                break;
        }


        if (mvalue == 69) {
            currentBall = "dot";
        } else if (mvalue == 1) {
            currentBall = "1";
        } else if (mvalue == 2) {
            currentBall = "2";
        } else if (mvalue == 3) {
            currentBall = "3";
        } else if (mvalue == 4) {
            currentBall = "4";
        } else if (mvalue == 6) {
            currentBall = "6";
        } else if (mvalue == -1) {
            currentBall = "W";
        } else if (mvalue == 33) {
            currentBall = "wd";
            Toast.makeText(this, "Add Extra run in Wide-ball, in case!", Toast.LENGTH_SHORT).show();
        } else if (mvalue == 22) {
            currentBall = "nb";
            Toast.makeText(this, "Add Extra run in No-ball, in case!", Toast.LENGTH_SHORT).show();
        }

        display += currentBall;
        overLog.add(String.valueOf(overIndex));
        if(overIndex < over)
            overIndex++;



    }


    @SuppressLint("SetTextI18n")
    private void show(int val, int value) {

        runs = (TextView) findViewById(R.id.runs);
        wickets = (TextView) findViewById(R.id.wickets);
        overs = (TextView) findViewById(R.id.overs);
        if (value != -1)
            teamAscore += value;

        rpo();
        mvalue = val;

        runs.setText(String.valueOf(teamAscore));
        wickets.setText("-" + String.valueOf(out));

        if (ballCount >= 1) {

            balls--;
            if (balls == 0) {
                balls = 6;
                over++;
                display = "";
                overs.setText("(" + over + ".0) over(s)");
            } else
                overs.setText("(" + over + "." + (6 - (int) balls) + ") over(s)");
            anim();
        } else {
            ballCount++;
        }

    }

    @SuppressLint("SetTextI18n")
    private void rpo() {

        rpo = (TextView) findViewById(R.id.rpo);
        if (balls == 1)
            custOver = over + 1;
        else
            custOver = over + ((7 - balls) / 10);

        if (over == 0)
            rpoCalc = teamAscore;
        else
            rpoCalc = teamAscore / custOver;

        rpoCalc = Math.round(rpoCalc * 100.0) / 100.0;

        rpo.setText("(" + String.valueOf(rpoCalc) + ") r.p.o");
    }

    private void anim() {
        double i;
        ballsView = (TextView) findViewById(R.id.balls);
        ballsView.setText("");
        for (i = (balls); i > 0; i--) {
            ballsView.append("  .  ");
        }

    }

    @Override
    protected void onStart() {
        startTimeA = System.nanoTime();
        super.onStart();

    }

    @Override
    protected void onResume() {
        Log.d("tag", "ResumeA");
        super.onResume();
    }

    @Override
    protected void onPause() {
        mediaPlayer.stop();
        super.onPause();

    }


    @Override
    protected void onStop() {

        Log.d("tag", "StopA");
        super.onStop();

    }

    @Override
    protected void onDestroy() {
        Log.d("tag", "DestroyA");
        super.onDestroy();
    }
}
