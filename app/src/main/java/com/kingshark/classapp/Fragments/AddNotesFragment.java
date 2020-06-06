package com.kingshark.classapp.Fragments;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;


import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.NavUtils;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.FirebaseApp;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.kingshark.classapp.Models.Notes;
import com.kingshark.classapp.R;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import dmax.dialog.SpotsDialog;


public class AddNotesFragment extends Fragment {


    Unbinder unbinder;
    @BindView(R.id.add_toolbar)
    Toolbar toolbar;
    @BindView(R.id.edt_addTitle)
    EditText edt_title;
    @BindView(R.id.edt_content)
    EditText edt_content;
    @BindView(R.id.save_fab)
    FloatingActionButton save_fab;


    public AddNotesFragment() {
        // Required empty public constructor
    }

    static AddNotesFragment instance;

    public static AddNotesFragment getInstance() {
        if (instance == null)
            instance = new AddNotesFragment();
        return instance;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_add_notes, container, false);
        unbinder = ButterKnife.bind(this,view);
        FirebaseApp.initializeApp(getActivity());

        init();
        initView();
        return view;
    }

    private void init() {

    }


    private void initView() {

        toolbar.setNavigationOnClickListener(v -> {
            Fragment fragment = new NotesFragment();

            assert getFragmentManager() != null;
            FragmentTransaction ft = getFragmentManager().beginTransaction().replace(R.id.fragment_container,fragment)
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE).addToBackStack(null);
            ft.commit();


        });

        save_fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Notes notes = new Notes();
                notes.setTitle(edt_title.getText().toString());
                notes.setTitle(edt_content.getText().toString());

                if (TextUtils.isEmpty(edt_title.toString())){
                    Toast.makeText(getContext(), "Cannot Save Without Title", Toast.LENGTH_SHORT).show();
                }else if (TextUtils.isEmpty(edt_content.toString())){
                    Toast.makeText(getContext(), "Cannot Save Empty Note", Toast.LENGTH_SHORT).show();
                }
                else{
                    DocumentReference noteRef = FirebaseFirestore.getInstance().collection("notes").document();
                    noteRef.set(notes)
                            .addOnSuccessListener(aVoid -> {
                                Toast.makeText(getContext(), "Note Created", Toast.LENGTH_SHORT).show();
                            }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(getContext(), "Error Occurred" +e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });


                }

            }
        });
    }
}