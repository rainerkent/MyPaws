package com.google.tbuilding;

import android.support.v7.widget.RecyclerView;
import android.app.*;
import android.os.*;
import android.support.v7.widget.*;

public class NewClaimActivity extends Activity
{
	private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_claim);
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        mRecyclerView.setHasFixedSize(true);

        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
		
        mAdapter = new ClaimAdapter();
        mRecyclerView.setAdapter(mAdapter);
	}
}
