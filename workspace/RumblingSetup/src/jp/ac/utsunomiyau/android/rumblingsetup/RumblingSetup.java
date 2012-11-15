package jp.ac.utsunomiyau.android.rumblingsetup;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class RumblingSetup extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.rumbling_setup);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.rumbling_setup, menu);
        return true;
    }
}
