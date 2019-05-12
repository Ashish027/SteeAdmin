package c.gla.admin;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class AdapterPostremove extends RecyclerView.Adapter<AdapterPostremove.ViewholderRemover> {

    private String stringId;
    private Post post;
    private Context context;
    private List<Post> list = new ArrayList<>();
    private DatabaseReference firebaseDatabase;

    public AdapterPostremove(Context context, List<Post> list) {
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

        post = list.get(i);

        firebaseDatabase = FirebaseDatabase.getInstance().getReference("POST");
        viewholderRemover.textView.setText(post.getPostid());
        Picasso.with(context).load(post.getUri()).into(viewholderRemover.imageView);

        viewholderRemover.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                firebaseDatabase.child("" + list.get(viewholderRemover.getAdapterPosition())
                        .getPostid()).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
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
