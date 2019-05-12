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

public class EventDelActivity extends AppCompatActivity {


    private DatabaseReference firebaseDatabase;
    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;

    private List<Event> list = new ArrayList<>();
    private AdapterEventremove adapterEventremove;

    private Event event;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_del);


        getSupportActionBar().setTitle("Remove Events");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);


        list.clear();
        recyclerView = findViewById(R.id.event_rem_recycler_view);
        linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        firebaseDatabase = FirebaseDatabase.getInstance().getReference("event");
    }

    @Override
    protected void onStart() {
        super.onStart();

        list.clear();
        firebaseDatabase.child("").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {

                    event = dataSnapshot1.getValue(Event.class);
                    list.add(event);
                    Toast.makeText(EventDelActivity.this, "" +
                            dataSnapshot1.getKey(), Toast.LENGTH_SHORT).show();


                }
                adapterEventremove = new AdapterEventremove(EventDelActivity.this, list);
                recyclerView.setLayoutManager(linearLayoutManager);
                recyclerView.setAdapter(adapterEventremove);


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }


}
