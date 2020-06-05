package com.kingshark.classapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.kingshark.classapp.Adapters.NotesAdapter;
import com.kingshark.classapp.Common.SpaceItemDecoration;

import butterknife.BindView;
import butterknife.ButterKnife;

public class NotesActivity extends AppCompatActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.notes_recycler)
    RecyclerView notes_recycler;
//    @BindView(R.id.notes_fab)
//    FloatingActionButton notes_fab;
    @BindView(R.id.img_sync)
    ImageView img_sync;
    @BindView(R.id.img_delete)
    ImageView img_delete;

    NotesAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes);
        setSupportActionBar(toolbar);


        initView();
    }

    private void initView() {
        ButterKnife.bind(this);

        notes_recycler.setHasFixedSize(true);
        StaggeredGridLayoutManager gridLayoutManager = new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL);

        notes_recycler.setLayoutManager(gridLayoutManager);
        notes_recycler.addItemDecoration(new SpaceItemDecoration(2));

        loadNotes();
    }

    private void loadNotes() {

    }


}