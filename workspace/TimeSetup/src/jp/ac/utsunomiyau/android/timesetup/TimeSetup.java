package jp.ac.utsunomiyau.android.timesetup;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class TimeSetup extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.time_setup);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.time_setup, menu);
        return true;
    }
}
