package com.AZDeveloper.microjobs;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.ironsource.adapters.supersonicads.SupersonicConfig;
import com.ironsource.mediationsdk.IronSource;
import com.ironsource.mediationsdk.integration.IntegrationHelper;
import com.ironsource.mediationsdk.logger.IronSourceError;
import com.ironsource.mediationsdk.sdk.OfferwallListener;
import com.ironsource.mediationsdk.sdk.RewardedVideoListener;

public class IronSourceActivity extends AppCompatActivity implements OfferwallListener {

String APP_KEY = "8b109b75";
Button  mOfferwallButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_iron_source);





        //The integrationHelper is used to validate the integration. Remove the integrationHelper before going live!
        IntegrationHelper.validateIntegration(this);

        startIronSourceInitTask();

        mOfferwallButton = (Button) findViewById(R.id.iron_btn_offer);
        mOfferwallButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //show the offerwall
                if (IronSource.isOfferwallAvailable())
                    IronSource.showOfferwall();
            }
        });

        //Network Connectivity Status
        IronSource.shouldTrackNetworkState(this, true);
    }

    private void startIronSourceInitTask(){

        // getting advertiser id should be done on a background thread
        AsyncTask<Void, Void, String> task = new AsyncTask<Void, Void, String>() {
            @Override
            protected String doInBackground(Void... params) {
                return IronSource.getAdvertiserId(IronSourceActivity.this);
            }
            @Override
            protected void onPostExecute(String advertisingId) {
                if (TextUtils.isEmpty(advertisingId)) {
                    advertisingId = FirebaseAuth.getInstance().getUid();
                }
                // we're using an advertisingId as the 'userId'
                initIronSource(APP_KEY, advertisingId);
            }
        };
        task.execute();
    }

    private void initIronSource(String appKey, String userId) {
        // Be sure to set a listener to each product that is being initiated

        // set the IronSource offerwall listener
        IronSource.setOfferwallListener(this);
        // set client side callbacks for the offerwall
        SupersonicConfig.getConfigObj().setClientSideCallbacks(true);

        IronSource.setUserId(FirebaseAuth.getInstance().getUid());

        // init the IronSource SDK
        IronSource.init(this, appKey, IronSource.AD_UNIT.OFFERWALL);

        updateButtonsState();

        // In order to work with IronSourceBanners you need to add Providers who support banner ad unit and uncomment next line
        // createAndloadBanner();
    }

    private void updateButtonsState() {

        handleOfferwallButtonState(IronSource.isOfferwallAvailable());
    }

    /**
     * Set the Rewareded Video button state according to the product's state
     *
     * @param available if the offerwall is available
     */
    public void handleOfferwallButtonState(final boolean available) {
        final String text;
        final int color;
        if (available) {
            color = Color.BLUE;
            text = "Available";
        } else {
            color = Color.BLACK;
            text = "Not Available";
        }
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mOfferwallButton.setTextColor(color);
                mOfferwallButton.setText(text);
                mOfferwallButton.setEnabled(available);

            }
        });

    }






    @Override
    protected void onResume() {
        super.onResume();
        IronSource.onResume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        IronSource.onPause(this);
    }

    @Override
    public void onOfferwallAvailable(boolean b) {
        handleOfferwallButtonState(b);
    }

    @Override
    public void onOfferwallOpened() {

    }

    @Override
    public void onOfferwallShowFailed(IronSourceError ironSourceError) {

    }

    @Override
    public boolean onOfferwallAdCredited(int i, int i1, boolean b) {
        Toast.makeText(IronSourceActivity.this,"onOfferwallAdCredited" + " credits:" + i + " totalCredits:" + i1 + " totalCreditsFlag:" + b, Toast.LENGTH_LONG).show();
        return false;
    }

    @Override
    public void onGetOfferwallCreditsFailed(IronSourceError ironSourceError) {
        Toast.makeText(IronSourceActivity.this, ironSourceError.getErrorMessage(), Toast.LENGTH_LONG).show();

    }

    @Override
    public void onOfferwallClosed() {

    }
}

