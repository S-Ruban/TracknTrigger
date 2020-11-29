package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ChecklistFragment extends Fragment {
    RecyclerView recyclerView;
    InventoryAdapter adapter;
    private InventoryAdapter mAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.checklist_layout, container, false);
        recyclerView = view.findViewById(R.id.recyclerview_checklist);

        ArrayList<String> list = new ArrayList<>();
        ArrayList<String> keys = new ArrayList<>();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Checklist");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                list.clear();
                for(DataSnapshot snapshot: dataSnapshot.getChildren()) {
                    keys.add(snapshot.getKey().toString());
                    list.add(snapshot.getValue().toString());
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        adapter = new InventoryAdapter(list);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(container.getContext(), 1, GridLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setAdapter(adapter);


        Button button_add = (Button) view.findViewById(R.id.button_add_1);
        button_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText editText = (EditText) view.findViewById(R.id.edittext_new_category_1);
                String edittext = editText.getText().toString();
                String key = reference.push().getKey();
                reference.child(key).setValue(edittext);
                adapter.notifyDataSetChanged();
            }
        });

        Button button_remove = (Button) view.findViewById(R.id.button_remove_1);
        button_remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText editText = (EditText) view.findViewById(R.id.edittext_remove_category_1);
                int position = Integer.parseInt(editText.getText().toString());
                position--;
                if(position != RecyclerView.NO_POSITION) {
                    reference.child(keys.get(position)).removeValue();
                    keys.remove(position);
                    list.remove(position);
                    adapter.notifyDataSetChanged();
                }
            }
        });

        return view;
    }
}