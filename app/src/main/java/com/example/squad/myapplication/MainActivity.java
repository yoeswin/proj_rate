package com.example.squad.myapplication;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private List<String> values, empty;
    private List<String> count, uuid, uid;
    RecyclerView recyclerView;
    String c;
    MyAdapter a;
    RecyclerView.Adapter mAdapter;
    int temp;

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        empty = new ArrayList<>();
        values = new ArrayList<>();
        count = new ArrayList<>();
        uuid = new ArrayList<>();
        uid = new ArrayList<>();
        temp = 0;
        recyclerView = findViewById(R.id.my_recycler_view);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);


        FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference myRef = database.getReference().child("root");

        myRef.child("user").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.

                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    String value = postSnapshot.child("name").getValue(String.class);
                    values.add(value);
                    Long values = postSnapshot.child("commentno").getValue(Long.class);
                    count.add(values.toString());
//                    Toast.makeText(getBaseContext(), postSnapshot.getValue().toString(), Toast.LENGTH_LONG).show();

                    c = postSnapshot.child("uid").getValue(String.class);
                    uuid.add(c);
                }


                myRef.child("Comment").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        // This method is called once with the initial value and again
                        // whenever data at this location is updated.

                        StringBuilder ss = new StringBuilder();
                        for (int i = 0; i < uuid.size(); i++) {

                            for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                                if (postSnapshot.child("uid").getValue(String.class).equals(uuid.get(i))) {
                                    String value = postSnapshot.child("text").getValue(String.class);
                                    ss.append("* ").append(value).append("\n");
//                                    Toast.makeText(getBaseContext(), ss.toString(), Toast.LENGTH_SHORT).show();
                                }
                            }
                            uid.add(ss.toString());
                            ss.setLength(0);
                        }
                        Toast.makeText(getBaseContext(), "completed", Toast.LENGTH_SHORT).show();
                        a = new MyAdapter(values, count, uid);
                        recyclerView.setAdapter(a);
                    }


                    @Override
                    public void onCancelled(DatabaseError error) {
                        // Failed to read value
                    }
                });
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
            }
        });


        myRef.child("Comment").orderByKey().limitToLast(1).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                if (!(temp == 0)) {
                    Toast.makeText(getBaseContext(), dataSnapshot.child("text").getValue().toString(), Toast.LENGTH_SHORT).show();
                    for (int x = 1; x <= uid.size(); x++) {
                        if (dataSnapshot.child("uid").getValue().toString().equals("uid0" + x)) {

                            StringBuilder tempo = new StringBuilder(uid.get(x));
                            tempo.append("* ").append(dataSnapshot.child("text").getValue().toString()).append("\n");
                            uid.set(x-1, tempo.toString());
                            String kija=Integer.toString(x);
                            Toast.makeText(getBaseContext(), uid.get(x), Toast.LENGTH_SHORT).show();

                            a = new MyAdapter(empty, empty, empty);
                            recyclerView.setAdapter(a);
                            a = new MyAdapter(values, count, uid);
                            recyclerView.setAdapter(a);
                        }
                    }
                }
                temp = 1;
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                Toast.makeText(getBaseContext(), dataSnapshot.getValue().toString(), Toast.LENGTH_SHORT).show();


                //                StringBuilder ss = new StringBuilder();
//                for (int i = 0; i < uuid.size(); i++) {
//                    int x = 1;
//                    for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
//                        if (postSnapshot.child("uid").getValue(String.class).equals(uuid.get(i))) {
//                            String value = postSnapshot.child("text").getValue(String.class);
//                            ss.append(x).append(".").append(value).append("\n");
////                                    Toast.makeText(getBaseContext(), ss.toString(), Toast.LENGTH_SHORT).show();
//                            x++;
//                        }
//                    }
//                    uid.add(ss.toString());
//                    ss.setLength(0);
//                }
//
//                a = new MyAdapter(values, count, uid);
//                MyAdapter x = new MyAdapter();
//                a.add(2, "robert");
//                recyclerView.setAdapter(a);
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }

    public void hlo(View view) {

        a = new MyAdapter(empty, empty, empty);
        recyclerView.setAdapter(a);
    }
}
