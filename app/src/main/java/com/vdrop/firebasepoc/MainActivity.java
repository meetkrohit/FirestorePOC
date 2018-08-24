package com.vdrop.firebasepoc;

import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.util.ArrayList;

import javax.annotation.Nullable;

public class MainActivity extends AppCompatActivity {

    private RecyclerView rv;
    private LinearLayoutManager layoutManager;
    private FirebaseAdapter firebaseAdapter;
    DocumentReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        rv = (RecyclerView) findViewById(R.id.vds_rv_channels);
        layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        rv.setLayoutManager(layoutManager);
        getData("");
    }

    private void getData(String s) {
        reference = FirebaseFirestore.getInstance().collection("Test2")
                .document("nfAWl9uP2EQt85emS7JT");
        reference.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {

                if (documentSnapshot != null && documentSnapshot.exists()) {
                    try {
                        ArrayList jsonArray =  (ArrayList)documentSnapshot.getData().get("details");
                        for (int i =0;i<jsonArray.size();i++){
                            DocumentReference reference = (DocumentReference)jsonArray.get(i);
                            getData(reference.getPath());
                            Log.d("Ref",""+reference.getPath());
                        }

                        /*ref = jsonObject.optString("details");
                        Log.d("docs", "" + documentSnapshot.get("details"));
                        getData(ref);*/



                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }

            }
        });
        setFirebaseAdapter();
    }

    private void setFirebaseAdapter() {
        firebaseAdapter = new FirebaseAdapter(this);
        firebaseAdapter.setData();
        rv.setAdapter(firebaseAdapter);
        firebaseAdapter.notifyDataSetChanged();

    }
}