package jp.ac.utsunomiyau.android.repeatsetup;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class RepeatSetup extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.repeat_setup);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.repeat_setup, menu);
        return true;
    }
}
