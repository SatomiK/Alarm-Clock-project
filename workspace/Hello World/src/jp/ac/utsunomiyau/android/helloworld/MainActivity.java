package jp.ac.utsunomiyau.android.helloworld;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.widget.Button;

public class MainActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        Button button = (Button) findViewById(R.id.button1);
        button.setOnClickListener(buttonListener);
        
   
    public void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);
    	setContentView(R.layout.activity_main);
    	button.setOnClickListener(buttonListener);
    }
    private View.OnClickListener buttonListener = new View.OnClickListener() {
    	public void onClick(View v) {
    		Button thisButton = (Button) v;
    		thisButton.setText("‚Ù‚°‚Ù‚°");
    	}
    };
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }
}