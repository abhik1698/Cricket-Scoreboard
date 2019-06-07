package cricket.h;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import static cricket.h.TeamAActivity.teamAscore;

public class TeamBActivity extends AppCompatActivity {


    TextView runs, wickets, overs, ballsView, target;
    Button one, two, three, four, six, wide, noball, wicket;
    public static int teamBscore = 0, out = 0, ballCount = 1, over = 0;
    public static double minutesA;
    private int mvalue;
    public static long startTimeB, endTimeB;
    private TextView rpo;
    public static double rpoCalc , balls = 6, custOver;

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
                        Toast.makeText(TeamBActivity.this, "See you soon...", Toast.LENGTH_LONG).show();
                    }

                })
                .setNegativeButton("No", null)
                .show();
    }

    @SuppressLint({"ResourceAsColor", "SetTextI18n"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.team_b_layout);

        if (!(SplashScreen.teamB.getText().toString().length() > 0))
            SplashScreen.teamB.setText("Team B");

        setTitle(SplashScreen.teamB.getText().toString());

        rpoCalc = 0;
        teamBscore = 0;
        out = 0;
        balls = 6;
        ballCount = 1;
        over = 0;
        minutesA = (TeamAActivity.endTimeA - TeamAActivity.startTimeA);
        minutesA = (minutesA / 1000000000) / 60;
        minutesA = Math.round(minutesA * 100.0) / 100.0;

        TextView scoreA = (TextView) findViewById(R.id.scoreA);
        scoreA.setText(teamAscore + "-" + TeamAActivity.out + "\t→ " + TeamAActivity.over + "." + (6 - (int) TeamAActivity.balls) + " over(s)\t→ " + minutesA + " minute(s)");
        target = (TextView) findViewById(R.id.target);
        target.append(String.valueOf(teamAscore + 1));

    }

    @SuppressLint("SetTextI18n")
    public void action(View view) {

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
                if (teamAscore == teamBscore)
                    mfinish(3);
                else
                    mfinish(1);
                break;
        }


        if (mvalue == 33) {

            Toast.makeText(this, "Add Extra run in Wide-ball, in case!", Toast.LENGTH_SHORT).show();
        } else if (mvalue == 22) {

            Toast.makeText(this, "Add Extra run in No-ball, in case!", Toast.LENGTH_SHORT).show();
        }
    }


    @SuppressLint("SetTextI18n")
    private void show(int val, int value) {

        runs = (TextView) findViewById(R.id.runs);
        wickets = (TextView) findViewById(R.id.wickets);
        overs = (TextView) findViewById(R.id.overs);

        if (value != -1)
            teamBscore += value;

        rpo();
        mvalue = val;

        runs.setText(String.valueOf(teamBscore));
        wickets.setText("-" + String.valueOf(out));

        if (ballCount == 1) {

            balls--;
            if (balls == 0) {
                balls = 6;
                over++;
                overs.setText("(" + over + ".0) over(s)");
            } else
                overs.setText("(" + over + "." + (6 - (int) balls) + ") over(s)");
            anim();
        } else {
            ballCount++;
            Toast.makeText(this, "Add Extra run to No-ball", Toast.LENGTH_SHORT).show();
        }

        if (teamBscore > teamAscore)
            mfinish(2);

    }

    @SuppressLint("SetTextI18n")
    private void rpo() {

        rpo = (TextView) findViewById(R.id.rpo);
        if (balls == 1)
            custOver = over + 1;
        else
            custOver = over + ((7 - balls) / 10);

        if (over == 0)
            rpoCalc = teamBscore;
        else
            rpoCalc = teamBscore / custOver;

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

    @SuppressLint("SetTextI18n")
    public void mfinish(int team) {
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

        six.setEnabled(false);
        four.setEnabled(false);
        three.setEnabled(false);
        two.setEnabled(false);
        one.setEnabled(false);
        wide.setEnabled(false);
        noball.setEnabled(false);
        dotball.setEnabled(false);
        wicket.setEnabled(false);
        allout.setEnabled(false);

        Finish.getReport(team);
        endTimeB = System.nanoTime();
        Intent mmfinish = new Intent(this, Finish.class);
        startActivity(mmfinish);
        finish();
    }

    @Override
    protected void onStart() {
        Log.d("tag", "StartB");
        startTimeB = System.nanoTime();
        super.onStart();

    }

    @Override
    protected void onResume() {
        Log.d("tag", "ResumeB");
        super.onResume();
    }

    @Override
    protected void onPause() {
        Log.d("tag", "PauseB");
        super.onPause();
    }


    @Override
    protected void onStop() {

        Log.d("tag", "StopB");
        super.onStop();

    }

    @Override
    protected void onDestroy() {
        Log.d("tag", "DestroyB");
        super.onDestroy();
    }
}
