package c.gla.admin;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class AboutusActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aboutus);
        getActionBar().setTitle("About Us");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

    }
}
