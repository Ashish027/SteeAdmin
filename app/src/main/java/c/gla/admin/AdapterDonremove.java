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

class AdapterDonremove extends RecyclerView.Adapter<AdapterDonremove.ViewholderRemover> {


    private String stringId;
    private Donation donation;
    private Context context;
    private List<Donation> list = new ArrayList<>();
    private DatabaseReference firebaseDatabase;

    public AdapterDonremove(Context context, List<Donation> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewholderRemover onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View view = LayoutInflater.from(context).inflate(R.layout.event_card_view, viewGroup, false);


        return new ViewholderRemover(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewholderRemover viewholderRemover, int i) {

        donation = list.get(i);

        firebaseDatabase = FirebaseDatabase.getInstance().getReference("Donation");
        viewholderRemover.textView.setText(donation.getdId());
        viewholderRemover.textView.setText(donation.getDname());


        viewholderRemover.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                firebaseDatabase.child("" + list.get(viewholderRemover.getAdapterPosition()).getdId()).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
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

        private TextView textView,textView2;

        private Button button;

        public ViewholderRemover(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.eid);
            textView2 = itemView.findViewById(R.id.edtitle);
            button = itemView.findViewById(R.id.btnrem);


        }
    }


}
