package com.example.trekkersdenv3;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements View.OnClickListener {

    private static final int TYPE=1;
    private final Context context;
    private final List<Object> listRecyclerItem;


    public RecyclerAdapter(Context context, List<Object> listRecyclerItem) {
        this.context = context;
        this.listRecyclerItem = listRecyclerItem;
    }

    @Override
    public void onClick(View v) {

    }


    public class ItemViewHolder extends RecyclerView.ViewHolder{

        private TextView name;
        private TextView state;
        private String location;
        private TextView diff;

        public ItemViewHolder(@NonNull  View itemView) {
            super(itemView);
            name=(TextView) itemView.findViewById(R.id.name);
            state=(TextView) itemView.findViewById(R.id.state);
            location="";
            diff=(TextView)itemView.findViewById(R.id.diff);
        }


    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup,int i) {
        switch (i) {
            case TYPE:

            default:

                View layoutView = LayoutInflater.from(viewGroup.getContext()).inflate(
                        R.layout.list_item, viewGroup, false);

                return new ItemViewHolder((layoutView));
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {

        int viewType = getItemViewType(i);

        switch (viewType) {
            case TYPE:
            default:

                ItemViewHolder itemViewHolder = (ItemViewHolder) viewHolder;
                treks trekloc = (treks) listRecyclerItem.get(i);

                itemViewHolder.name.setText(trekloc.getName());
                itemViewHolder.state.setText(trekloc.getState());
                itemViewHolder.diff.setText("Difficulty : "+trekloc.getDiff());

                // added this
                viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String location= trekloc.getLocation();
                        String name=trekloc.getName();
                        Intent intent = new Intent(context,Pop.class);
                        intent.putExtra("msg_key1",location);
                        intent.putExtra("msg_key2",name);
                        context.startActivity(intent);
                    }
                });
        }
    }

    @Override
    public int getItemCount() {
        return listRecyclerItem.size();
    }
}

