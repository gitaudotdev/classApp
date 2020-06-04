package com.kingshark.classapp.Fragments;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.kingshark.classapp.Common.SpaceItemDecoration;
import com.kingshark.classapp.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;


public class NotesFragment extends Fragment {

    private Unbinder unbinder;
    @BindView(R.id.notes_recycler)
    RecyclerView notes_recycler;
    @BindView(R.id.toolbar)
    Toolbar toolbar;



    public NotesFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }


    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_notes, container, false);
        unbinder = ButterKnife.bind(this,view);

        init();

        initView();

        return view;
    }

    private void initView() {
        toolbar.inflateMenu(R.menu.toolbar_menu);
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if (item.getItemId() == R.id.action_delete)
                    Toast.makeText(getContext(), "Delete", Toast.LENGTH_SHORT).show();
                else if (item.getItemId() == R.id.action_sync)
                    Toast.makeText(getContext(), "Sync to cloud", Toast.LENGTH_SHORT).show();
                return true;
            }
        });

    }

    private void init() {
        notes_recycler.setHasFixedSize(true);
        StaggeredGridLayoutManager gridLayoutManager = new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL);

        notes_recycler.setLayoutManager(gridLayoutManager);
        notes_recycler.addItemDecoration(new SpaceItemDecoration(2));

        loadNotes();
    }

    private void loadNotes() {

    }
}