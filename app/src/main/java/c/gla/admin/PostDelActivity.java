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

public class PostDelActivity extends AppCompatActivity {

    private DatabaseReference firebaseDatabase;
    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;

    private List<Post> list = new ArrayList<>();
    private AdapterPostremove adapterpostremove;
    private Post post;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_del);

        getSupportActionBar().setTitle("Remove Post");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        list.clear();
        recyclerView = findViewById(R.id.post_rem_recycler_view);
        linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        firebaseDatabase = FirebaseDatabase.getInstance().getReference("POST");


    }

    @Override
    protected void onStart() {
        super.onStart();

        list.clear();
        firebaseDatabase.child("").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {

                    post = dataSnapshot1.getValue(Post.class);
                    list.add(post);
                    Toast.makeText(PostDelActivity.this, "" +
                            dataSnapshot1.getKey(), Toast.LENGTH_SHORT).show();


                }
                adapterpostremove = new AdapterPostremove(PostDelActivity.this, list);
                recyclerView.setLayoutManager(linearLayoutManager);
                recyclerView.setAdapter(adapterpostremove);


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
}
