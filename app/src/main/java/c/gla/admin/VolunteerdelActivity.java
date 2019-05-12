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

public class VolunteerdelActivity extends AppCompatActivity {


    private DatabaseReference firebaseDatabase;
    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;

    private List<Volunteer> list = new ArrayList<>();
    private AdapterVolunteerremove adapterVolunteerremove;

    private Volunteer volunteer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_volunteerdel);

        getSupportActionBar().setTitle("Remove Volunteer");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);


        list.clear();
        recyclerView = findViewById(R.id.vol_rem_recycler_view);
        linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);

       firebaseDatabase = FirebaseDatabase.getInstance().getReference("Volunteer");
    }

    @Override
    protected void onStart() {
        super.onStart();

        list.clear();
        firebaseDatabase.child("").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {

                    volunteer = dataSnapshot1.getValue(Volunteer.class);
                    list.add(volunteer);
                    Toast.makeText(VolunteerdelActivity.this, "" +
                            dataSnapshot1.getKey(), Toast.LENGTH_SHORT).show();


                }
                adapterVolunteerremove = new
                        AdapterVolunteerremove(VolunteerdelActivity.this,list);
                recyclerView.setLayoutManager(linearLayoutManager);
                recyclerView.setAdapter(adapterVolunteerremove);


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
}
