package com.kingshark.classapp.Adapters;

import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.kingshark.classapp.Fragments.NoteDetailsFragment;
import com.kingshark.classapp.Interface.IRecyclerViewListener;
import com.kingshark.classapp.Models.EventBus.NotesEvent;
import com.kingshark.classapp.Models.Notes;
import com.kingshark.classapp.R;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;

public class NotesAdapter extends RecyclerView.Adapter<NotesAdapter.NotesViewHolder> {

    Context context;
    List<Notes> notesList;
    Notes notes;
    NoteDetailsFragment fragment;


    public NotesAdapter(Context context, List<Notes> notesList) {
        this.context = context;
        this.notesList = notesList;
    }

    public NotesAdapter(Context context, List<Notes> notesList, NoteDetailsFragment fragment) {
        this.context = context;
        this.notesList = notesList;
        this.fragment = fragment;
    }

    @NonNull
    @Override
    public NotesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.notes_layout,parent,false);
        return new NotesViewHolder(itemView);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onBindViewHolder(@NonNull NotesViewHolder holder, int position) {

        holder.tv_content.setText(notesList.get(position).getContent());
        holder.tv_title.setText(notesList.get(position).getTitle());
        holder.noteCard.setCardBackgroundColor(context.getResources().getColor(getRandomColor(),null));

        holder.setListener((view, position1) -> {
            EventBus.getDefault().postSticky(new NotesEvent(notes));
            AppCompatActivity activity = (AppCompatActivity) view.getContext();
            fragment = new NoteDetailsFragment();
            activity.getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, fragment).addToBackStack(null).commit();

        });

    }

    private int getRandomColor() {
        List<Integer> colorCode = new ArrayList<>();
        colorCode.add(R.color.blue);
        colorCode.add(R.color.yellow);
        colorCode.add(R.color.skyblue);
        colorCode.add(R.color.lightPurple);
        colorCode.add(R.color.lightGreen);
        colorCode.add(R.color.gray);
        colorCode.add(R.color.pink);
        colorCode.add(R.color.red);
        colorCode.add(R.color.notgreen);

        Random randomColor = new Random();
        int number = randomColor.nextInt(colorCode.size());
        return colorCode.get(number);
    }

    @Override
    public int getItemCount() {
        return notesList.size();
    }

    public class NotesViewHolder  extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.tv_content)
        TextView tv_content;
        @BindView(R.id.tv_note_title)
        TextView tv_title;
        @BindView(R.id.note_card)
        CardView noteCard;

        IRecyclerViewListener listener;

        public NotesViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(context,itemView);

            itemView.setOnClickListener(this);
        }

        public void setListener(IRecyclerViewListener listener) {
            this.listener = listener;
        }

        @Override
        public void onClick(View v) {
            listener.onClick(v,getAdapterPosition());
        }
    }
}
