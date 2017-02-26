package com.example.faranak.mine_seeker;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;

public class HelpUI extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_help_ui);
        makeHelpText();
    }

    private void makeHelpText() {
        TextView helpText = (TextView) findViewById(R.id.tvHelpText);
        helpText.setText("This is Hepl\n You should find me\n I can help you with numbers");
    }
}
