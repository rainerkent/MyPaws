package com.google.tbuilding;

import android.content.*;
import android.support.v7.widget.*;
import android.view.*;
import android.view.View.*;
import android.widget.*;
import java.util.*;
import com.squareup.picasso.Picasso;

public class ClaimAdapter extends RecyclerView.Adapter<ClaimAdapter.ViewHolder>
{
    private ArrayList<Pet> mDataset;

    public static class ViewHolder extends RecyclerView.ViewHolder
	{
        public ImageView mPetImage;
		public TextView mPetName;

        public ViewHolder(View v)
		{
            super(v);
            mPetName = (TextView) v.findViewById(R.id.pet_name);
			mPetImage = (ImageView) v.findViewById(R.id.pet_image);
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public ClaimAdapter()
	{
        mDataset = new ArrayList<>();
		// mDataset.add(new Pet("Muning", R.drawable.muning));
		// mDataset.add(new Pet("Tukoy", R.drawable.tukoy));
    }

    @Override
    public ClaimAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
	{
        View v = LayoutInflater.from(parent.getContext())
			.inflate(R.layout.layout_claim, parent, false);
        return new ViewHolder(v);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, final int position)
	{
        // holder.mPetImage.setImage(mDataset.get(position).getImage());
        Picasso.with(holder.itemView.getContext()).load(mDataset.get(position).getImage()).into(holder.mPetImage);
		holder.mPetName.setText(mDataset.get(position).getName());

		holder.itemView.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View view){
				Toast.makeText(view.getContext(), mDataset.get(position).getName() + " clicked", Toast.LENGTH_SHORT).show();
                Intent i = new Intent(view.getContext(), SubmitClaimActivity.class);
                i.putExtra("PET_NAME", mDataset.get(position).getName());
                i.putExtra("PET_IMAGE", mDataset.get(position).getImage());
                view.getContext().startActivity(i);
			}
		});
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount()
	{
        return mDataset.size();
    }
}
