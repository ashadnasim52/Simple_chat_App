package dsfsfsfafdfdf.chatapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class Chatroom extends AppCompatActivity implements MyRecyclerViewAdapter.ItemClickListener{

    EditText chataddtext;
    Button chatrromadd;
    ListView shwchat;
    FirebaseUser user= FirebaseAuth.getInstance().getCurrentUser();
    FirebaseDatabase database=FirebaseDatabase.getInstance();
    DatabaseReference dbchat=database.getReference().child("chats");
    MyRecyclerViewAdapter adapter;
    ArrayList<String> animalNames;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chatroom);
        chataddtext=findViewById(R.id.chatroom);
        chatrromadd=findViewById(R.id.addchatroom);
        chatrromadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!chataddtext.getText().toString().trim().equals(""))
                {
                   dbchat.child(chataddtext.getText().toString()).child("createdby").setValue(user.getDisplayName());
                }
            }
        });











        animalNames = new ArrayList<>();

        animalNames.add("Horse");
        animalNames.add("Cow");
        animalNames.add("Camel");


        // set up the RecyclerView
        RecyclerView recyclerView = findViewById(R.id.chatshow);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new MyRecyclerViewAdapter(this, animalNames);
        adapter.setClickListener(this);
        recyclerView.setAdapter(adapter);

//        recyclerView.addItemDecoration(
//                new DividerItemDecoration(getApplicationContext(), R.drawable.my_divider));
//        recyclerView.addItemDecoration(new DividerItemDecoration(getAct,R.drawable.my_divider));

        //todo change createdby to date

        DatabaseReference chatroomname=database.getReference().child("chats");
        chatroomname.orderByChild("createdby").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Log.i("adadasdad","is   datasnap "+dataSnapshot);

                if (dataSnapshot.exists())
                {
                    animalNames.add(dataSnapshot.getKey());

                    adapter.notifyDataSetChanged();

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



    @Override
    public void onItemClick(View view, int position) {
        Toast.makeText(getApplicationContext(),"clicked on"+position,Toast.LENGTH_LONG).show();

        Intent chat=new Intent(getApplicationContext(),ChatActivity.class);
        chat.putExtra("chatroom",animalNames.get(position));
        startActivity(chat);
    }
}
