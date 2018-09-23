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
import android.widget.TextView;
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
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

public class ChatActivity extends AppCompatActivity {

    EditText message;
    TextView createdbby;
    Button sendmessage;
//        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    FirebaseUser user=FirebaseAuth.getInstance().getCurrentUser();
    FirebaseDatabase database=FirebaseDatabase.getInstance();
    DatabaseReference dbchat=database.getReference().child("chats");
    String chatroom;
    HashMap<String,String> chatstosend=new HashMap<>();
    String chatuuidofuser,chatby,chats,createdby;
    ArrayList<String> chatcreatedbyuser,chatnamea,chatuuid,chatmessages;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        message=findViewById(R.id.message);
        sendmessage=findViewById(R.id.sendmessage);
        chatcreatedbyuser=new ArrayList<>();
        chatnamea=new ArrayList<>();
        chatuuid=new ArrayList<>();
        chatmessages=new ArrayList<>();
        createdbby=findViewById(R.id.createdbby);
        Intent i=getIntent();

        chatroom=i.getStringExtra("chatroom");
        Log.i("ashadlog","is "+chatroom);



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


        dbchat.child(chatroom).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
//                Log.i("datasnapsadhotis"," isvalue  "+dataSnapshot);

//
//                createdby=dataSnapshot.getValue(String.class);
//                createdbby.setText(createdby);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        dbchat.child(chatroom).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {


//                chatOCntent reatedby=new chatOCntent();
//
//                Log.i("ashadlog","is "+reatedby);
//

                Log.i("datasnapsadhotis"," is child"+dataSnapshot);
                if (dataSnapshot.exists())
                {
                    chatOCntent chatOCntentchats=new chatOCntent();
//                    Log.i("datasnapsadhotis"," is chatOCntentchats "+chatOCntentchats);


                    createdby=chatOCntentchats.getCreatedby();
                    chats=chatOCntentchats.getMessage();
                    chatby=chatOCntentchats.getSendername();
                    chatuuidofuser=chatOCntentchats.getUuid();
                    createdbby.setText(createdby);
                    Log.i("datasnapsadhotis"," is chatOCntentchats "+createdby);
                    Log.i("datasnapsadhotis"," is chatOCntentchats "+chats);
                    Log.i("datasnapsadhotis"," is chatOCntentchats "+chatby);
                    Log.i("datasnapsadhotis"," is chatOCntentchats "+chatuuidofuser);

                }

                chatcreatedbyuser.add(createdby);
                chatnamea.add(chatby);
                chatuuid.add(chatuuidofuser);
                chatmessages.add(chats);

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
