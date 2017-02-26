package com.example.faranak.mine_seeker;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.Window;
import android.view.WindowManager;
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
        TextView aboutAuthor = (TextView) findViewById(R.id.tvAboutAuthor);
        aboutAuthor.setClickable(true);
        aboutAuthor.setText(Html.fromHtml("This is program is written by <a href=\"http://www.cs.sfu.ca/CourseCentral/276/bfraser/\">Faranak Nobakhtian</a>."));
        aboutAuthor.setMovementMethod(LinkMovementMethod.getInstance());
        TextView instruction = (TextView) findViewById(R.id.tvHelpText);
        instruction.setText("You are about to enter into Minion world where they \n" +
                            " should be saved from hidden and dangerous mines. \n" +
                            " The mines are behind table's cells and it is your \n" +
                            "objective to find them. Every time that a non-mine \n" +
                            "cell is tapped (also called scan), a minion gets \n" +
                            "injured so try to find all mines with least possible \n" +
                            "scans. To assist you, each scan reveals number of \n" +
                            "mines hidden in corresponding row and column. As \n" +
                            "note, keep in mind that first tap on a cell does \n" +
                            "not count as scan but the second does count.");
    }
}
