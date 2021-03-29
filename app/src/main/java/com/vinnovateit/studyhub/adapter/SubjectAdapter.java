package com.vinnovateit.studyhub.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import androidx.transition.AutoTransition;
import androidx.transition.TransitionManager;

import com.vinnovateit.studyhub.R;
import com.vinnovateit.studyhub.model.Subject;

import java.util.List;

public class SubjectAdapter extends RecyclerView.Adapter<SubjectAdapter.SubjectViewHolder> {

    Context context;
    List<Subject> subjectList;

    public SubjectAdapter(Context context, List<Subject> subjectList) {
        this.context = context;
        this.subjectList = subjectList;
    }

    @NonNull
    @Override
    public SubjectViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.subject, parent, false);
        return new SubjectViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SubjectViewHolder holder, int position) {

        holder.module.setText(subjectList.get(position).getModule());

        holder.more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(holder.modulesDesc.getVisibility()==View.VISIBLE){
                    TransitionManager.beginDelayedTransition(holder.subLayout,
                            new AutoTransition());
                    holder.modulesDesc.setVisibility(View.GONE);
                    holder.more.setImageResource(R.drawable.ic_add);
                }
                else {
                    TransitionManager.beginDelayedTransition(holder.subLayout,
                            new AutoTransition());
                    holder.description.setText(subjectList.get(position).getDescription());
                    holder.modulesDesc.setVisibility(View.VISIBLE);
                    holder.more.setImageResource(R.drawable.ic_minimize);
                }
            }
        });
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(holder.modulesDesc.getVisibility()==View.VISIBLE){
                    TransitionManager.beginDelayedTransition(holder.subLayout,
                            new AutoTransition());
                    holder.modulesDesc.setVisibility(View.GONE);
                    holder.more.setImageResource(R.drawable.ic_add);
                }
                else {
                    TransitionManager.beginDelayedTransition(holder.subLayout,
                            new AutoTransition());
                    holder.description.setText(subjectList.get(position).getDescription());
                    holder.modulesDesc.setVisibility(View.VISIBLE);
                    holder.more.setImageResource(R.drawable.ic_minimize);
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return subjectList.size();
    }

    public static final class SubjectViewHolder extends RecyclerView.ViewHolder {
        CardView modulesDesc,moduleNo;
        TextView module, description;
        ImageView more;
        CardView subLayout;

        public SubjectViewHolder(@NonNull View itemView) {
            super(itemView);
            modulesDesc=itemView.findViewById(R.id.modulesDesc);
            moduleNo=itemView.findViewById(R.id.card);
            module=itemView.findViewById(R.id.moduleTextView);
            description=itemView.findViewById(R.id.description);
            more=itemView.findViewById(R.id.expandPlease);
            subLayout=itemView.findViewById(R.id.subLayout);
        }
    }
}
