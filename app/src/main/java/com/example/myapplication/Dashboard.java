package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class Dashboard extends AppCompatActivity {
    private static final String TAG = "lol";
    private boolean innerFrag = false;
    private String innerString = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        Bundle bundle = getIntent().getExtras();
        if(bundle != null) {
            innerFrag = bundle.getBoolean("inner", false);
            innerString = bundle.getString("string", null);
        }
        // Log.i(TAG, "onCreate: dashboard " + innerFrag + " " + innerString);

        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);
        bottomNav.setOnNavigationItemSelectedListener(navListener);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                    new InventoryFragment()).commit();
        }
    }

    private BottomNavigationView.OnNavigationItemSelectedListener navListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    Fragment selectedFragment = null;
                    switch (item.getItemId()) {
                        case R.id.nav_inventory:
                            if(innerFrag) {
                                selectedFragment = new InventoryFragmentInner();
                                Bundle args = new Bundle();
                                args.putString("name", innerString);
                                selectedFragment.setArguments(args);
                                Toast.makeText(getApplicationContext(),"innerfrag", Toast.LENGTH_LONG).show();
                            } else {
                                selectedFragment = new InventoryFragment();
                                Toast.makeText(getApplicationContext(),"outerfrag", Toast.LENGTH_LONG).show();
                            }
                            break;
                        case R.id.nav_checklist:
                            selectedFragment = new ChecklistFragment();
                            break;
                        case R.id.nav_notes:
                            selectedFragment = new NotesFragment();
                            break;
                    }
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                            selectedFragment).commit();
                    return true;
                }
            };
}