package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class InventoryFragment extends Fragment {
    RecyclerView recyclerView;
    InventoryAdapter adapter;
    private InventoryAdapter mAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_inventory, container, false);
        recyclerView = view.findViewById(R.id.recyclerview_inventory);

        ArrayList<String> list = new ArrayList<>();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Inventory");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                list.clear();
                for(DataSnapshot snapshot: dataSnapshot.getChildren())
                    list.add(snapshot.getKey().toString());
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        adapter = new InventoryAdapter(list);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(container.getContext(), 2, GridLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setAdapter(adapter);


        adapter.setOnItemClickListener(new InventoryAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Intent intent = new Intent(view.getContext(), Dashboard.class);
                intent.putExtra("inner", true);
                intent.putExtra("string", list.get(position));
                startActivity(intent);
            }
        });



        Button button_add = (Button) view.findViewById(R.id.button_add);
        button_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText editText = (EditText) view.findViewById(R.id.edittext_new_category);
                String edittext = editText.getText().toString();
                reference.child(edittext).setValue(edittext);
                Intent intent = new Intent(view.getContext(), Dashboard.class);
                intent.putExtra("inner", false);
                intent.putExtra("string", "bad");
                startActivity(intent);
            }
        });

        Button button_remove = (Button) view.findViewById(R.id.button_remove);
        button_remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText editText = (EditText) view.findViewById(R.id.edittext_remove_category);
                String edittext = editText.getText().toString();
                reference.child(edittext).removeValue();
                Intent intent = new Intent(view.getContext(), Dashboard.class);
                intent.putExtra("inner", false);
                intent.putExtra("string", "bad");
                startActivity(intent);
            }
        });

        return view;
    }
}