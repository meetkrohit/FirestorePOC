package com.vdrop.firebasepoc;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.annotation.Nullable;

public class MainActivity extends AppCompatActivity {

    private RecyclerView rv;
    private LinearLayoutManager layoutManager;
    private FirebaseAdapter firebaseAdapter;
    DocumentReference reference;
    DocumentReference ref;
    DocumentReference update;
    //ChannelDetails channelDetails;
    ArrayList<ChannelDetails> channelDetailsList;
    ArrayList<ChannelDetails> channelDetailsListR;
    ArrayList<String> channelId;

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
                        final ArrayList jsonArray = (ArrayList) documentSnapshot.getData().get("channels");
                        channelDetailsList = new ArrayList<>();
                        for (int i = 0; i < jsonArray.size(); i++) {
                            ref = (DocumentReference) jsonArray.get(i);
                            ref.addSnapshotListener(new EventListener<DocumentSnapshot>() {
                                @Override
                                public void onEvent(@Nullable DocumentSnapshot documentSnapshot,
                                                    @Nullable FirebaseFirestoreException e) {

                                    JSONObject jsonObject = new JSONObject(documentSnapshot.getData());
                                    try {
                                        JSONObject jsonObject1 = jsonObject.getJSONObject("channelDetails");
                                        //Log.d("Reference", "" + jsonObject1.optString("name"));
                                        if (channelDetailsList.size() == jsonArray.size()){
                                            for (int j = 0; j < channelDetailsList.size(); j++){
                                                if (channelDetailsList.get(j).getId().equals(jsonObject1.optString("id"))){
                                                            channelDetailsList.get(j).setName(jsonObject1.optString("name"));
                                                            channelDetailsList.get(j).setDescription(jsonObject1.optString("description"));
                                                            channelDetailsList.get(j).setBannerImage(jsonObject1.optString("bannerImage"));
                                                            setFirebaseAdapter(channelDetailsList, true, j);
                                                }
                                            }

                                        } else {
                                            ChannelDetails channelDetails = new ChannelDetails();
                                            channelDetails.setName(jsonObject1.optString("name"));
                                            channelDetails.setDescription(jsonObject1.optString("description"));
                                            channelDetails.setBannerImage(jsonObject1.optString("bannerImage"));
                                            channelDetails.setId(jsonObject1.optString("id"));
                                            channelDetailsList.add(channelDetails);
                                            Collections.reverse(channelDetailsList);
                                            setFirebaseAdapter(channelDetailsList, false, 0);
                                        }



                                    } catch (JSONException e1) {
                                        e1.printStackTrace();
                                    }
                                }
                            });

                        }


                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }

            }
        });


    }

    private void setFirebaseAdapter(ArrayList<ChannelDetails> channelDetailsListI, boolean status, int pos) {
        if (status){
            firebaseAdapter.setData(channelDetailsListI,pos);
        } else {
            firebaseAdapter = new FirebaseAdapter(this,channelDetailsListI);
            rv.setAdapter(firebaseAdapter);
            firebaseAdapter.notifyDataSetChanged();
        }


    }
}