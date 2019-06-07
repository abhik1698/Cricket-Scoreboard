package cricket.h;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class OverLogs extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_over_logs);


        ArrayAdapter<String> overLogAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,TeamAActivity.overLog);
        ListView list = (ListView) findViewById(R.id.list);
        list.setAdapter(overLogAdapter);
    }
}
