package jp.ac.utsunomiyau.android.soundsetup;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class SoundSetup extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sound_setup);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.sound_setup, menu);
        return true;
    }
}
