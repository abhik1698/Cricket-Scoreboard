package cricket.h;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class SplashScreen extends AppCompatActivity {


    public static EditText teamA, teamB;

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
                        Toast.makeText(SplashScreen.this, "See you soon...", Toast.LENGTH_LONG).show();
                    }

                })
                .setNegativeButton("No", null)
                .show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        final Button done = (Button) findViewById(R.id.done);
        TeamAActivity.teamAscore = 0;
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                setContentView(R.layout.team_setup);
                teamA = (EditText) findViewById(R.id.teamA);
                teamB = (EditText) findViewById(R.id.teamB);
                teamA.setText(teamA.getText().toString());
                teamB.setText(teamB.getText().toString());
            }
        }, 1300);


    }
    public void done(View view) {
        Intent splash = new Intent(SplashScreen.this, TeamAActivity.class);
        startActivity(splash);
        finish();
    }
}
