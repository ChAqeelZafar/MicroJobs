package com.AZDeveloper.microjobs;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.tapjoy.TJActionRequest;
import com.tapjoy.TJAwardCurrencyListener;
import com.tapjoy.TJConnectListener;
import com.tapjoy.TJEarnedCurrencyListener;
import com.tapjoy.TJError;
import com.tapjoy.TJGetCurrencyBalanceListener;
import com.tapjoy.TJPlacement;
import com.tapjoy.TJPlacementListener;
import com.tapjoy.TJSetUserIDListener;
import com.tapjoy.TJSpendCurrencyListener;
import com.tapjoy.Tapjoy;
import androidx.appcompat.app.AppCompatActivity;

import com.tapjoy.TapjoyConnectFlag;

import java.util.Hashtable;

public class Temporary extends AppCompatActivity implements TJPlacementListener, TJConnectListener {
    Button getBtn, spendBtn, awardBtn, earnBtn;

    TJPlacement placement;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_temporary);

        getBtn = findViewById(R.id.temp_getcurrency);
        spendBtn = findViewById(R.id.temp_spend);
        awardBtn = findViewById(R.id.temp_award);
        earnBtn = findViewById(R.id.temp_earned);

        Tapjoy.setDebugEnabled(true);

        Hashtable<String,Object> connectFlags = new Hashtable<String,Object>();
        connectFlags.put(TapjoyConnectFlag.ENABLE_LOGGING, "true");

        Tapjoy.connect(this.getApplicationContext(), "CyPEXhpET9qPqUC5zB6-qQECff3Kca8Afp4KMBaHl2vyVXY2lbpt8wgO6mhr", connectFlags,  this);



        Tapjoy.setActivity(this);


        getBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                placement.requestContent();
            }
        });




//        Tapjoy.setGcmSender("448314699006");
//
//        TJPlacementListener placementListener = this;
//        TJPlacement p = Tapjoy.getPlacement("StageFailed", placementListener);
//        if(p.isContentReady()) {
//            p.showContent();
//        }
//        else {
//            //handle situation where there is no content to show, or it has not yet downloaded.
//        }
//        if(Tapjoy.isConnected()) {
//            p.requestContent();
//        } else {
//            //Log.d("Micro Jobs", "Tapjoy SDK must finish connecting before requesting content.");
//        }


// Get notifications whenever Tapjoy currency is earned.
        Tapjoy.setEarnedCurrencyListener(new TJEarnedCurrencyListener() {
            @Override
            public void onEarnedCurrency(String currencyName, int amount) {
                Toast.makeText(Temporary.this,  "You've just earned " + amount + " " + currencyName, Toast.LENGTH_LONG).show();
            }
        });



        spendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Tapjoy.spendCurrency(25, new TJSpendCurrencyListener() {
                    @Override
                    public void onSpendCurrencyResponse(String currencyName, int balance) {
                        //Toast.makeText(Temporary.this,  currencyName + ": " + balance, Toast.LENGTH_LONG).show();

                    }

                    @Override
                    public void onSpendCurrencyResponseFailure(String error) {
                        //Toast.makeText(Temporary.this,  "spendCurrency error: " + error, Toast.LENGTH_LONG).show();

                    }
                });
            }
        });


        awardBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Tapjoy.awardCurrency(10, new TJAwardCurrencyListener() {
                    @Override
                    public void onAwardCurrencyResponseFailure(String error) {
                        //Toast.makeText(Temporary.this,  "awardCurrency error: " + error, Toast.LENGTH_LONG).show();

                    }

                    @Override
                    public void onAwardCurrencyResponse(String currencyName, int balance) {
                        //Toast.makeText(Temporary.this, currencyName + ": " + balance, Toast.LENGTH_LONG).show();

                    }
                });
            }
        });










    }

    //session start
    @Override
    protected void onStart() {
        super.onStart();
        Tapjoy.onActivityStart(this);
    }

    //session end
    @Override
    protected void onStop() {
        Tapjoy.onActivityStop(this);
        super.onStop();
    }

    @Override
    public void onRequestSuccess(TJPlacement tjPlacement) {

    }

    @Override
    public void onRequestFailure(TJPlacement tjPlacement, TJError tjError) {
        Toast.makeText(this, tjError.message, Toast.LENGTH_LONG).show();
        Log.d("TAPJOY",tjError.message);
    }

    @Override
    public void onContentReady(TJPlacement tjPlacement) {
        placement.showContent();
    }

    @Override
    public void onContentShow(TJPlacement tjPlacement) {

    }

    @Override
    public void onContentDismiss(TJPlacement tjPlacement) {

    }

    @Override
    public void onPurchaseRequest(TJPlacement tjPlacement, TJActionRequest tjActionRequest, String s) {

    }

    @Override
    public void onRewardRequest(TJPlacement tjPlacement, TJActionRequest tjActionRequest, String s, int i) {

    }

    @Override
    public void onConnectSuccess() {

        Tapjoy.setUserID(FirebaseAuth.getInstance().getUid(), new TJSetUserIDListener() {
            @Override
            public void onSetUserIDSuccess() {
                placement = Tapjoy.getPlacement("Wall", Temporary.this);
                placement.requestContent();
                Toast.makeText(Temporary.this, Tapjoy.getUserToken(), Toast.LENGTH_LONG).show();
            }

            @Override
            public void onSetUserIDFailure(String s) {
                Toast.makeText(Temporary.this, "FAILED", Toast.LENGTH_LONG).show();
            }
        });

    }

    @Override
    public void onConnectFailure() {

    }
}
