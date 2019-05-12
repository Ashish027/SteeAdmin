package c.gla.admin;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class DonationDelActivity extends AppCompatActivity {


    private DatabaseReference firebaseDatabase;
    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;


    private List<Donation> list = new ArrayList<>();
    private AdapterDonremove adapterdonremove;
    private Donation donation;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donation_del);

        getSupportActionBar().setTitle("Donation Dashboard");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        list.clear();
        recyclerView = findViewById(R.id.don_recycler_view);
        linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        firebaseDatabase = FirebaseDatabase.getInstance().getReference("Donation");
    }

    @Override
    protected void onStart() {
        super.onStart();
        list.clear();
        firebaseDatabase.child("").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {

                    donation = dataSnapshot1.getValue(Donation.class);
                    list.add(donation);
                    Toast.makeText(DonationDelActivity.this, "" +
                            dataSnapshot1.getKey(), Toast.LENGTH_SHORT).show();


                }
                adapterdonremove = new AdapterDonremove(DonationDelActivity.this, list);
                recyclerView.setLayoutManager(linearLayoutManager);
                recyclerView.setAdapter(adapterdonremove);


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }


}
