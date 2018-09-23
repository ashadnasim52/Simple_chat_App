package dsfsfsfafdfdf.chatapp;

import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;

public class ChatActivity extends AppCompatActivity {

    EditText message;
    Button sendmessage;
//        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    FirebaseUser user=FirebaseAuth.getInstance().getCurrentUser();
    FirebaseDatabase database=FirebaseDatabase.getInstance();
    DatabaseReference dbchat=database.getReference().child("chats");

    HashMap<String,String> chatstosend=new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        message=findViewById(R.id.message);
        sendmessage=findViewById(R.id.sendmessage);


        Intent i=getIntent();
        final String chatroom=i.getStringExtra("chatroom");
        sendmessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!message.getText().toString().trim().equals(""))
                {


                    String messags=message.getText().toString();
                    String uuid=user.getUid();
                    String sendername=user.getDisplayName();
                    chatstosend.put("content",messags);
                    String pusshkey=dbchat.child(chatroom).push().getKey();
                    dbchat.child(chatroom).child(pusshkey).child("message").setValue(messags);
                    dbchat.child(chatroom).child(pusshkey).child("sendername").setValue(sendername);
                    dbchat.child(chatroom).child(pusshkey).child("uuid").setValue(uuid);

                }
            }
        });


        dbchat.equalTo(chatroom).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                if (dataSnapshot.exists())
                {
                    chatOCntent chatOCntentchats=new chatOCntent();
                    String createdby=chatOCntentchats.getCreatedby();


                }


            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });





    }
}
