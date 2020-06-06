package com.kingshark.classapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.FirebaseApp;
import com.kingshark.classapp.Fragments.NotesFragment;
import com.kingshark.classapp.Fragments.TimeTableFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.bottom_nav)
    BottomNavigationView bottomNav;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FirebaseApp.initializeApp(this);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        loadFragment(new TimeTableFragment());

        bottomNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            Fragment fragment = null;
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (item.getItemId() == R.id.action_home)
                    fragment = new TimeTableFragment();
                else if (item.getItemId() == R.id.action_notes)
                    fragment =  new NotesFragment();
                else if (item.getItemId() == R.id.action_todo)
                    Toast.makeText(MainActivity.this, "Todo's ", Toast.LENGTH_SHORT).show();
                    //startActivity(new Intent(MainActivity.this, NotesActivity.class));
                else if (item.getItemId() == R.id.action_dash)
                    Toast.makeText(MainActivity.this, "Dashboard", Toast.LENGTH_SHORT).show();

                return loadFragment(fragment);
            }
        });
    }

    private boolean loadFragment(Fragment fragment) {
        if (fragment != null)
        {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,fragment).commit();
            return true;
        }
        else
            return  false;
    }
}