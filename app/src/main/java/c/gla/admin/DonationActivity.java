package c.gla.admin;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class DonationActivity extends AppCompatActivity {

    private EditText donname,donemail,donoccupation,donphone,donfood,doncloth,donstationary;
    private Button donsavebtn;
    private EditText donmoney;




    DatabaseReference databaseReference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donation);

        getSupportActionBar().setTitle("Donation");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        FirebaseApp.initializeApp(this);

        databaseReference = FirebaseDatabase.getInstance().getReference("Donation");

        donname = findViewById(R.id.don_name);
        donemail = findViewById(R.id.don_email);
        donoccupation = findViewById(R.id.don_occupation);
        donfood = findViewById(R.id.don_food);
        doncloth = findViewById(R.id.don_cloth);
        donstationary = findViewById(R.id.don_stationay);
        donphone = findViewById(R.id.don_phone);
        donmoney = findViewById(R.id.don_money);

        donsavebtn = findViewById(R.id.don_submit);




        donsavebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddDonation();
            }
        });

    }

    private void AddDonation() {

        String dname = donname.getText().toString();
        String demail = donemail.getText().toString();
        String doccupation = donoccupation.getText().toString();
        String dfood = donfood.getText().toString();
        String dcloth = doncloth.getText().toString();
        String dstationary = donstationary.getText().toString();
        String dphone = donphone.getText().toString();
        String dmoney = donmoney.getText().toString();


        if (!TextUtils.isEmpty(dname) && !TextUtils.isEmpty(demail) && !TextUtils.isEmpty(doccupation) && !TextUtils.isEmpty(dphone)||
                (!TextUtils.isEmpty(dfood) || !TextUtils.isEmpty(dcloth) || !TextUtils.isEmpty(dstationary) || !TextUtils.isEmpty(dmoney))) {


            String did = databaseReference.push().getKey();
            Donation donation = new Donation(did, dname, demail, dphone, doccupation, dmoney, dfood, dcloth, dstationary);
            databaseReference.child(did).setValue(donation);
            donname.setText("");
            donemail.setText("");
            donphone.setText("");
            donfood.setText("");
            donoccupation.setText("");
            donmoney.setText("");
            doncloth.setText("");
            donstationary.setText("");

        }
        else{
            Toast.makeText(this, "Please fill the Entities", Toast.LENGTH_SHORT).show();
        }




    }
}
