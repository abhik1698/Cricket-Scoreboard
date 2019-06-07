package cricket.h;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class HomeScreen_Activity extends AppCompatActivity {

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
                        Toast.makeText(HomeScreen_Activity.this, "See you soon...", Toast.LENGTH_LONG).show();
                    }

                })
                .setNegativeButton("No", null)
                .show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen_);


        Button start = (Button) findViewById(R.id.start);


        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Toast.makeText(HomeScreen_Activity.this, "Buckle up, Let's get Started!", Toast.LENGTH_SHORT).show();
                Intent splash = new Intent(HomeScreen_Activity.this, SplashScreen.class);
                startActivity(splash);
                finish();

            }
        });

    }


}
