package jp.ac.utsunomiyau.android.alarmclock;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class AlarmClock extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.alarm_clock);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.alarm_clock, menu);
        return true;
    }
}
