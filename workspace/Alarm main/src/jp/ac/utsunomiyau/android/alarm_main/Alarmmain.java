package jp.ac.utsunomiyau.android.alarm_main;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class Alarmmain extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.alarm_main);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.alarm_main, menu);
        return true;
    }
}
