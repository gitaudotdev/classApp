package com.kingshark.classapp.Fragments;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.NavUtils;
import androidx.fragment.app.Fragment;

import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.kingshark.classapp.Models.EventBus.NotesEvent;
import com.kingshark.classapp.R;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.w3c.dom.Text;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;


public class NoteDetailsFragment extends Fragment {

    private Unbinder unbinder;

    @BindView(R.id.note_toolbar)
    Toolbar toolbar;
    @BindView(R.id.note_content)
    TextView note_content;
    @BindView(R.id.tv_note_title)
    TextView note_title;


    public NoteDetailsFragment() {
        // Required empty public constructor
    }

    static NoteDetailsFragment instance;

    public static NoteDetailsFragment getInstance(){
        if (instance == null)
            instance = new NoteDetailsFragment();
        return instance;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_note_details, container, false);
        unbinder = ButterKnife.bind(this,view);
        initView();
        return view;
    }

    private void initView() {
        toolbar.setNavigationOnClickListener(v -> NavUtils.navigateUpFromSameTask(getActivity()));
        note_content.setMovementMethod(new ScrollingMovementMethod());
    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        EventBus.getDefault().unregister(this);
        super.onStop();
    }

    @Subscribe(sticky = true,threadMode =  ThreadMode.MAIN)
    public void setNoteAdapter(NotesEvent event)
    {
        note_content.setText(event.getNotesList().getContent());
        note_title.setText(event.getNotesList().getTitle());
        note_content.setBackgroundColor(getResources().getColor(event.getNotesList().getColor_code()));
    }
}