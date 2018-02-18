package com.google.tbuilding;
import android.os.*;
import android.content.*;
import android.app.*;
import android.widget.*;
import android.view.animation.*;

public class Loading extends Activity
{
	@Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_load);

		ImageView img = (ImageView)findViewById(R.id.imgs);
        TextView text = (TextView)findViewById(R.id.txt);
		Animation fade = AnimationUtils.loadAnimation(this,R.anim.transition);
		img.startAnimation(fade);
		Animation fadeout = AnimationUtils.loadAnimation(this,R.anim.transitionout);
		text.startAnimation(fadeout);
		final Intent  a = new Intent(this,SignInActivity.class);
		Thread timer = new Thread(){
				public void run(){
						try{
								sleep(5000);
							}catch(InterruptedException e){
								e.printStackTrace();
							}
						finally{
						   startActivity(a);
								finish();

							}
					}
			};
		timer.start();
}
}
