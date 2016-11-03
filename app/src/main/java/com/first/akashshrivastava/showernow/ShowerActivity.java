package com.first.akashshrivastava.showernow;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.IntentSender;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.Scopes;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.fitness.Fitness;
import com.google.android.gms.fitness.data.DataPoint;
import com.google.android.gms.fitness.data.DataSource;
import com.google.android.gms.fitness.data.DataType;
import com.google.android.gms.fitness.data.Field;
import com.google.android.gms.fitness.data.Value;
import com.google.android.gms.fitness.request.DataSourcesRequest;
import com.google.android.gms.fitness.request.OnDataPointListener;
import com.google.android.gms.fitness.request.SensorRequest;
import com.google.android.gms.fitness.result.DataSourcesResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;
import java.util.concurrent.TimeUnit;

import cn.fanrunqi.waveprogress.WaveProgressView;

/**
 * Created by akashshrivastava on 31/07/16.
 */
public class ShowerActivity extends Activity implements OnDataPointListener,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener  {


    //Fitness API place holder data
    private static final int REQUEST_OAUTH = 1;
    private static final String AUTH_PENDING = "auth_state_pending";
    private boolean authInProgress = false;
    private GoogleApiClient mApiClient;
    //Fitness API

    private PendingIntent pendingIntent;//for alarm manager

    private DatabaseReference mDatabaseReference;
    private FirebaseAuth mFirebaseAuth;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_shower_activity);


        //Fitness API AUth to Google play services
        if (savedInstanceState != null) {
            authInProgress = savedInstanceState.getBoolean(AUTH_PENDING);
        }

        mApiClient = new GoogleApiClient.Builder(this)
                .addApi(Fitness.SENSORS_API)
                .addScope(new Scope(Scopes.FITNESS_ACTIVITY_READ_WRITE))
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();

        //Fitness


        mDatabaseReference = FirebaseDatabase.getInstance().getReference();
        mFirebaseAuth = FirebaseAuth.getInstance();

        final ImageView genderImage = (ImageView) findViewById(R.id.imageGender);

        genderImage.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) { //set alarm
                /*Intent alarmIntent = new Intent(ShowerActivity.this, AlarmReceiver.class);

                pendingIntent = PendingIntent.getBroadcast(ShowerActivity.this, 0, alarmIntent, 0);

                AlarmManager manager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

                manager.setExact(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + 1000*60, pendingIntent);*/
                Calendar cal = Calendar.getInstance();
                Intent activate = new Intent(ShowerActivity.this, AlarmReceiver.class);
                AlarmManager alarms ;
                PendingIntent alarmIntent = PendingIntent.getBroadcast(ShowerActivity.this, 0, activate, 0);
                alarms = (AlarmManager) getSystemService(ALARM_SERVICE);
                alarms.set(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis()+5000, alarmIntent);

                Toast.makeText(getApplicationContext(), "genderImage clicked",
                        Toast.LENGTH_SHORT).show();

                return false;
            }
        });

        createWave();
        setMenuColor();



        FloatingActionButton fabMenuItem4 = (FloatingActionButton) findViewById(R.id.menu_item4); //edit user information. Goes to main activity
        fabMenuItem4.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "menu clicked",
                        Toast.LENGTH_LONG).show();
                Intent i = new Intent(ShowerActivity.this, MainActivity.class);
                 startActivity(i);
            }
        });

        FloatingActionButton fabMenuItem1 = (FloatingActionButton) findViewById(R.id.menu_item);
        fabMenuItem1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mFirebaseAuth.signOut();
                Intent i = new Intent(ShowerActivity.this, LoginActivity.class);
                startActivity(i);
            }
        });

       mDatabaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snap) {
                if (snap.child("User").child(mFirebaseAuth.getCurrentUser().getUid()).child("gender").getValue().toString().equalsIgnoreCase("female")){
                    genderImage.setImageResource(R.drawable.female_white_outline);
                }else if (snap.child("User").child(mFirebaseAuth.getCurrentUser().getUid()).child("gender").getValue().toString().equalsIgnoreCase("male")){
                    genderImage.setImageResource(R.drawable.male_white_outline);
                }else if (snap.child("User").child(mFirebaseAuth.getCurrentUser().getUid()).child("gender").getValue().toString().equalsIgnoreCase("other")){
                    genderImage.setImageResource(R.drawable.other_white_outline);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
    private void createWave(){//adds the wave effect
        WaveProgressView waveProgressbar = (WaveProgressView) findViewById(R.id.waveProgressbar);
        waveProgressbar.setCurrent(50,"2 hours"); // 77, "788M/1024M"
        waveProgressbar.setMaxProgress(100);
        waveProgressbar.setText("#FFFF00", 41);//"#FFFF00", 41
        waveProgressbar.setWaveColor("#80ddfc"); //"#5b9ef4"

        waveProgressbar.setWave(50,250);//float mWaveHight,float mWaveWidth
        waveProgressbar.setmWaveSpeed(10);//The larger the value, the slower the vibration
    }


    private void setMenuColor(){//sets the right color(seems like it cant be done through xml)
        FloatingActionMenu fabMenu = (FloatingActionMenu) findViewById(R.id.menu);
        fabMenu.setMenuButtonColorNormal(getResources().getColor(R.color.colorAccent));
        fabMenu.setMenuButtonColorPressed(getResources().getColor(R.color.colorAccentPressed));
    }

    //Auto Gen methods with Fitness API
    //Following methods implemented when using FItness API under OnDataPointListener, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener
    @Override
    protected void onStart() {
        super.onStart();
        mApiClient.connect();
    }


    @Override
    public void onConnected(@Nullable Bundle bundle) {

        DataSourcesRequest dataSourceRequest = new DataSourcesRequest.Builder()
                .setDataTypes( DataType.TYPE_STEP_COUNT_CUMULATIVE )

                //TYPE.RAW(Lower accuracy) doesn't work...TYPE.DERIEVE(Higher accuracy) didnt do anything sadly...
                //.setDataSourceTypes( DataSource.TYPE_DERIVED )

                .build();

        ResultCallback<DataSourcesResult> dataSourcesResultCallback = new ResultCallback<DataSourcesResult>() {
            @Override
            public void onResult(DataSourcesResult dataSourcesResult) {
                for( DataSource dataSource : dataSourcesResult.getDataSources() ) {
                    if( DataType.TYPE_STEP_COUNT_CUMULATIVE.equals( dataSource.getDataType() ) ) {
                        registerFitnessDataListener(dataSource, DataType.TYPE_STEP_COUNT_CUMULATIVE);
                    }
                }
            }
        };

        Fitness.SensorsApi.findDataSources(mApiClient, dataSourceRequest)
                .setResultCallback(dataSourcesResultCallback);
    }

    private void registerFitnessDataListener(DataSource dataSource, DataType dataType) {
        Toast.makeText(ShowerActivity.this, "registerFitnessDataListener", Toast.LENGTH_SHORT).show();
        SensorRequest request = new SensorRequest.Builder()
                .setDataSource( dataSource )
                .setDataType( dataType )
                .setSamplingRate( 3, TimeUnit.SECONDS )
                .build();

        Fitness.SensorsApi.add( mApiClient, request, this )
                .setResultCallback(new ResultCallback<Status>() {
                    @Override
                    public void onResult(Status status) {
                        if (status.isSuccess()) {
                            Log.e( "GoogleFit", "SensorApi successfully added" );
                        }
                    }
                });
    }
    @Override
    public void onConnectionSuspended(int i) {

        Toast.makeText(ShowerActivity.this, "Connection suspended", Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Toast.makeText(ShowerActivity.this, "onConnectionFailed", Toast.LENGTH_SHORT).show();
        if( !authInProgress ) {
            try {
                authInProgress = true;
                connectionResult.startResolutionForResult( ShowerActivity.this, REQUEST_OAUTH );
            } catch(IntentSender.SendIntentException e ) {

            }
        } else {
            Log.e( "GoogleFit", "authInProgress" );
        }
    }

    //EVERYTIME a state is changed in the sensor...this method is called! Checks the steps count every 3 seconds
    @Override
    public void onDataPoint(DataPoint dataPoint) {
        Toast.makeText(ShowerActivity.this, "onDataPoint", Toast.LENGTH_SHORT).show();
        for( final Field field : dataPoint.getDataType().getFields() ) {
            final Value value = dataPoint.getValue( field );

            //New thread so the steps count as well as the activity operations run at the same time
            runOnUiThread(new Runnable() {
                @Override
                public void run() {

                    //TODO Implement steps logic here......

                    Toast.makeText(ShowerActivity.this, "Field: " + field.getName() + " Value: " + value, Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    //Releasing the sensor data when the app is shut......#SaveBattery
     /*@Override
    protected void onStop() {
        super.onStop();

        Fitness.SensorsApi.remove(mApiClient, this)
                .setResultCallback(new ResultCallback<Status>() {
                    @Override
                    public void onResult(Status status) {
                        if (status.isSuccess()) {
                            mApiClient.disconnect();
                        }
                    }
                });
    }*/

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean(AUTH_PENDING, authInProgress);
    }
}