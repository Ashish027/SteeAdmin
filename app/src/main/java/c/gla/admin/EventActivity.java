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


public class EventActivity extends AppCompatActivity {
    private EditText eName,eDate,eTime;
    private EditText edescription;
    private Button btn;

    DatabaseReference databaseReference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event);

        getSupportActionBar().setTitle("Events");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        FirebaseApp.initializeApp(this);

        databaseReference = FirebaseDatabase.getInstance().getReference("event");

        eName = findViewById(R.id.eteventname);
        eDate = findViewById(R.id.etdate);
        eTime = findViewById(R.id.ettime);
        edescription = findViewById(R.id.etdecrp);
        btn= findViewById(R.id.btnevent);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addevent();
            }
        });

        }
        public void addevent(){

            String eventname = eName.getText().toString();
            String eventdate = eDate.getText().toString();
            String eventtime = eTime.getText().toString();
            String eventDescription = edescription.getText().toString();

            if (!TextUtils.isEmpty(eventname) && !TextUtils.isEmpty(eventdate) && !TextUtils.isEmpty(eventtime) && !TextUtils.isEmpty(eventDescription)) {

                String id = databaseReference.push().getKey();
                Event event = new Event(id,eventname,eventdate,eventtime,eventDescription );
                databaseReference.child(id).setValue(event);
                eName.setText("");
                eDate.setText("");
                eTime.setText("");
                edescription.setText("");
            }
            else{
                Toast.makeText(this, "Please fill the Entities", Toast.LENGTH_SHORT).show();
            }
        }


    }

