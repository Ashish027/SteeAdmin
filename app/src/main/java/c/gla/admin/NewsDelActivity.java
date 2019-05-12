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

public class NewsDelActivity extends AppCompatActivity {

    private DatabaseReference firebaseDatabase;
    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;


    private List<News> list = new ArrayList<>();
    private Adapterremove adapterremove;
    private News news;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_del);

        getSupportActionBar().setTitle("Remove News Feed");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        list.clear();
        recyclerView = findViewById(R.id.recycle);
        linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL,
                false);
        firebaseDatabase = FirebaseDatabase.getInstance().getReference("News");


    }


    @Override
    protected void onStart() {
        super.onStart();

        list.clear();
        firebaseDatabase.child("").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {


                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {

                    news = dataSnapshot1.getValue(News.class);
                    list.add(news);
                    Toast.makeText(NewsDelActivity.this, "" + dataSnapshot1.getKey(), Toast.LENGTH_SHORT).show();

                }

                adapterremove = new Adapterremove(NewsDelActivity.this, list);
                recyclerView.setLayoutManager(linearLayoutManager);
                recyclerView.setAdapter(adapterremove);


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }
}
