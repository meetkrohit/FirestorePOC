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
import com.google.firebase.storage.FirebaseStorage;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import javax.annotation.Nullable;

public class MainActivity extends AppCompatActivity {

    private RecyclerView rv;
    private LinearLayoutManager layoutManager;
    private FirebaseAdapter firebaseAdapter;
    DocumentReference reference;
    DocumentReference ref;
    DocumentReference update;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        rv = (RecyclerView) findViewById(R.id.vds_rv_channels);
        layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        rv.setLayoutManager(layoutManager);
        getData();
    }

    private void getData() {
        reference = FirebaseFirestore.getInstance().collection("TestTeam")
                .document("dev").collection("teamChannels").document("iC6RDVzwDULR1X");
        reference.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {

                if (documentSnapshot != null && documentSnapshot.exists()) {
                    try {
                        ArrayList jsonArray = (ArrayList) documentSnapshot.getData().get("channels");
                        for (int i = 0; i < jsonArray.size(); i++) {
                            ref = (DocumentReference) jsonArray.get(i);
                            ref.addSnapshotListener(new EventListener<DocumentSnapshot>() {
                                @Override
                                public void onEvent(@Nullable DocumentSnapshot documentSnapshot,
                                                    @Nullable FirebaseFirestoreException e) {

                                    JSONObject jsonObject = new JSONObject(documentSnapshot.getData());
                                    try {
                                        JSONObject jsonObject1 = jsonObject.getJSONObject("channelDetails");
                                        Log.d("Reference", "" + jsonObject1.optString("name"));
                                    } catch (JSONException e1) {
                                        e1.printStackTrace();
                                    }
                                }
                            });
                            //getDataRefernce(ref.getPath());

                        }

                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }

            }
        });

        setFirebaseAdapter();
    }

    private void getDataRefernce(String path) {

    }

    private void setFirebaseAdapter() {
        firebaseAdapter = new FirebaseAdapter(this);
        firebaseAdapter.setData();
        rv.setAdapter(firebaseAdapter);
        firebaseAdapter.notifyDataSetChanged();

    }
}