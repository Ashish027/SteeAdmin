package c.gla.admin;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class Adapterremove extends RecyclerView.Adapter<Adapterremove.ViewholderRemover> {

    private String stringId;
    private News news;
    private Context context;
    private List<News> list = new ArrayList<>();
    private DatabaseReference firebaseDatabase;

    public Adapterremove(Context context, List<News> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewholderRemover onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View view = LayoutInflater.from(context).inflate(R.layout.cardremove, viewGroup, false);


        return new ViewholderRemover(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewholderRemover viewholderRemover, int i) {

        news = list.get(i);

        firebaseDatabase = FirebaseDatabase.getInstance().getReference("News");
        viewholderRemover.textView.setText(news.getnId());
        Picasso.with(context).load(news.getImage()).into(viewholderRemover.imageView);

        viewholderRemover.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                firebaseDatabase.child("" + list.get(viewholderRemover.getAdapterPosition()).getnId()).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {

                        Toast.makeText(context,
                                "Success" + viewholderRemover.getAdapterPosition(),
                                Toast.LENGTH_SHORT).show();


                        list.clear();
                        notifyDataSetChanged();
                        ( (Activity)context).finish();


                    }
                });
            }
        });


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewholderRemover extends RecyclerView.ViewHolder {

        private TextView textView;
        private ImageView imageView;
        private Button button;

        public ViewholderRemover(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.idtext);
            imageView = itemView.findViewById(R.id.imageView);
            button = itemView.findViewById(R.id.button);


        }
    }


}
