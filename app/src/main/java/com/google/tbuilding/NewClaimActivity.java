package com.google.tbuilding;

import android.app.*;
import android.content.*;
import android.os.*;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.*;
import android.view.*;
import android.widget.*;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class NewClaimActivity extends AppCompatActivity
{
	private RecyclerView mRecyclerView;
    private DatabaseReference mDatabase;
    private FirebaseRecyclerAdapter<Pet, PetViewHolder> firebaseRecyclerAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_claim);

        mDatabase = FirebaseDatabase.getInstance().getReference();

        mRecyclerView = findViewById(R.id.recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        // mAdapter = new ClaimAdapter();
        // mRecyclerView.setAdapter(mAdapter);
        // mDatabase.keepSynced(true);
	}

    @Override
    protected void onStart() {
        super.onStart();
        SharedPreferences sp = getSharedPreferences(getPackageName(), Activity.MODE_PRIVATE);

        String userUid = sp.getString("USER_UID", null);
        Toast.makeText(getApplicationContext(), "User UID: " + userUid, Toast.LENGTH_SHORT).show();
        Query query = mDatabase.child("pets").orderByChild("owner").equalTo(userUid);
        FirebaseRecyclerOptions<Pet> options = new FirebaseRecyclerOptions.Builder<Pet>()
            .setQuery(query, Pet.class)
            .build();

        firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<Pet, PetViewHolder>(options) {
            @Override
            public PetViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.layout_claim, parent, false);

                return new PetViewHolder(view);
            }

            @Override
            protected void onBindViewHolder(PetViewHolder holder, int position, final Pet model) {
                holder.setPetName(model.getName());
                holder.setImage(getApplicationContext(), model.getImage());

                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Toast.makeText(view.getContext(), model.getName() + " clicked", Toast.LENGTH_SHORT).show();

                        Intent i = new Intent(view.getContext(), SubmitClaimActivity.class);
                        i.putExtra("PET_NAME", model.getName());
                        i.putExtra("PET_IMAGE", model.getImage());

                        view.getContext().startActivity(i);
                    }
                });
            }

            @Override
            public void onError(DatabaseError e) {
                Toast.makeText(getApplicationContext(), "Database Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        };

        mRecyclerView.setAdapter(firebaseRecyclerAdapter);
        firebaseRecyclerAdapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        if(firebaseRecyclerAdapter != null) {
            firebaseRecyclerAdapter.stopListening();
        }
    }

    public class PetViewHolder extends RecyclerView.ViewHolder{

        View mView;
        ImageView img;

        public PetViewHolder(View v) {
            super(v);
            mView = v;
            img = v.findViewById(R.id.pet_image);
        }

        public void setPetName(String petName){
            TextView pname = mView.findViewById(R.id.pet_name);
            pname.setText(petName);
        }

        public void setImage(Context applicationContext, String image){
            ImageView post_image = mView.findViewById(R.id.pet_image);
            Picasso.with(applicationContext).load(image).into(post_image);
        }
    }
}
