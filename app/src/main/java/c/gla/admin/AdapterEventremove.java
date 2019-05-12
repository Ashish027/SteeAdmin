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

class AdapterEventremove extends RecyclerView.Adapter<AdapterEventremove.ViewholderRemover> {


    private String stringId;
    private Event event;
    private Context context;
    private List<Event> list = new ArrayList<>();
    private DatabaseReference firebaseDatabase;

    public AdapterEventremove(Context context, List<Event> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public AdapterEventremove.ViewholderRemover onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View view = LayoutInflater.from(context).inflate(R.layout.event_card_view, viewGroup, false);


        return new AdapterEventremove.ViewholderRemover(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final AdapterEventremove.ViewholderRemover viewholderRemover, int i) {

      event = list.get(i);

        firebaseDatabase = FirebaseDatabase.getInstance().getReference("event");
        viewholderRemover.textView.setText(event.getEventid());
        viewholderRemover.textView2.setText(event.getEventname());


        viewholderRemover.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                firebaseDatabase.child("" + list.get(viewholderRemover.getAdapterPosition())
                        .getEventid()).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
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


