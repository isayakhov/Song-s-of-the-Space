package ru.sayakhov.songs_of_the_space.android;

import ru.sayakhov.songs_of_the_space.MyGame;
import ru.sayakhov.songs_of_the_space.managers.IActivityRequestHandler;
import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RelativeLayout;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;

public class AndroidLauncher extends AndroidApplication implements IActivityRequestHandler {

    private final int SHOW_ADS = 1;
    private final int HIDE_ADS = 0;
    
    protected AdView adView;
    protected View gameView;
    
    @SuppressLint("HandlerLeak")
	protected Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch(msg.what) {
            case SHOW_ADS:
                adView.setVisibility(View.VISIBLE);
                break;
            case HIDE_ADS:
                adView.setVisibility(View.GONE);
                break;
            }
        }
    };
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();

        RelativeLayout layout = new RelativeLayout(this);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, 
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);

        View gameView = initializeForView(new MyGame(this), config);
        
        layout.addView(gameView);

        adView = new AdView(this);
        adView.setAdUnitId(getResources().getString(R.string.admob_publisher_id));
        adView.setAdSize(AdSize.BANNER);
        
        AdRequest adRequest = new AdRequest.Builder()
        .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
        .addTestDevice(".............")
        .build();
        adView.loadAd(adRequest);

        RelativeLayout.LayoutParams adParams = 
            new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, 
                    RelativeLayout.LayoutParams.WRAP_CONTENT);
        adParams.addRule(RelativeLayout.ALIGN_PARENT_TOP);
        adParams.addRule(RelativeLayout.CENTER_HORIZONTAL);

        layout.addView(adView, adParams);

        setContentView(layout);
	}
	
	@Override
    public void showAds(boolean show) {
       handler.sendEmptyMessage(show ? SHOW_ADS : HIDE_ADS);
    }
	
	@Override
	public void onResume() {
		super.onResume();
	    if (adView != null) {
	    	adView.resume();
	    }
	}

	@Override
	public void onPause() {
		super.onPause();
	    if (adView != null) {
	    	adView.pause();
	    }
	}

	
	@Override
	public void onDestroy() {
	    if (adView != null) {
	    	adView.destroy();
	    }
	    super.onDestroy();
	}
}