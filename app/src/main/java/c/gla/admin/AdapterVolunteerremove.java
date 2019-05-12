package c.gla.admin;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

class AdapterVolunteerremove  extends RecyclerView.Adapter<AdapterVolunteerremove.ViewholderRemover> {

    private String stringId;
    private Volunteer volunteer;
    private Context context;
    private List<Volunteer> list = new ArrayList<>();
    private DatabaseReference firebaseDatabase;

    public AdapterVolunteerremove(Context context, List<Volunteer> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public AdapterVolunteerremove.ViewholderRemover onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View view = LayoutInflater.from(context).inflate(R.layout.event_card_view, viewGroup, false);


        return new AdapterVolunteerremove.ViewholderRemover(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final AdapterVolunteerremove.ViewholderRemover viewholderRemover, int i) {

        volunteer = list.get(i);

        firebaseDatabase = FirebaseDatabase.getInstance().getReference("Volunteer");
        viewholderRemover.textView.setText(volunteer.getVolemail());
        viewholderRemover.textView2.setText(volunteer.getVolname());


        viewholderRemover.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                firebaseDatabase.child("" + list.get(viewholderRemover.getAdapterPosition())
                        .getVolid()).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
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
        private TextView textView2;

        private Button button;

        public ViewholderRemover(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.eid);
            textView2 = itemView.findViewById(R.id.edtitle);


            button = itemView.findViewById(R.id.btnrem);


        }
    }


}


