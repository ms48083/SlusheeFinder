package com.mstechsoloutions.slushee;

import com.admob.android.ads.AdManager;
import com.admob.android.ads.AdView;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.Toast;
import android.location.*;
//import java.util.Map;

public class SlusheeFinder extends Activity {
    /** Called when the activity is first created. */
    // Acquire a reference to the system Location Manager
	LocationListener locationListener;
	LocationManager locationManager;
	Location myLocation;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        final Button go_button = (Button) findViewById(R.id.go_button);
        go_button.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                // Perform action on clicks
            	Context context = getApplicationContext();
                Toast.makeText(context, "Searching near " + getMyShortLocation(), Toast.LENGTH_LONG).show();

                stopMyLocationListener(); // turn off GPS signal
                // open map and tell it where to center
                Intent intent = new Intent(SlusheeFinder.this, com.mstechsoloutions.slushee.HelloWorld.class);
                Bundle c = new Bundle();  // pass a value of location to display            
                c.putString("location", getMyLocation());//This is the value I want to pass
                intent.putExtras(c);
                startActivity(intent);
            }
        });
        final RadioButton opt_myloc = (RadioButton) findViewById(R.id.opt_myloc);
        final RadioButton opt_specify = (RadioButton) findViewById(R.id.opt_specify);
        opt_myloc.setOnClickListener(radio_listener);
        opt_specify.setOnClickListener(radio_listener);

        startMyLocationListener();
    	//showLocation(myLocation);
        AdManager.setTestDevices( new String[] { AdManager.TEST_EMULATOR, "9BA1C6AE496C0C170128CF133A507A08" });
    
        AdView adView = (AdView)findViewById(R.id.ad);
        adView.requestFreshAd();
    }
    private void showLocation(Location myLoc) {
    	String mystring;
    	mystring = myLoc.getProvider() + ": " + String.valueOf(myLoc.getLatitude()).substring(0,8) + ", " + String.valueOf(myLoc.getLongitude()).substring(0,8);
    	Context context = getApplicationContext();
        Toast.makeText(context, mystring, Toast.LENGTH_SHORT).show();    	
    }
    private void startMyLocationListener()
    {
        locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        // create a location object and set a default or last location
        Criteria crit = new Criteria();
        crit.setAccuracy(Criteria.ACCURACY_FINE);
        String provider = locationManager.getBestProvider(crit, true);
    	myLocation = new Location(locationManager.getLastKnownLocation(provider));
        // Define a listener that responds to location updates
        locationListener = new LocationListener() {
            public void onLocationChanged(Location location) {
            	// Called when a new location is found by the network location provider.
            	// is this location better than previous?
            	// save the location for later use
        		myLocation.set(location);
        		showLocation(myLocation);                
            }
            public void onStatusChanged(String provider, int status, Bundle extras) {}
            public void onProviderEnabled(String provider) {}
            public void onProviderDisabled(String provider) {}
          };
          
        // Register the listener with the Location Manager to receive location updates
        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 1000, 20, locationListener);
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 20, locationListener);
        	
    }
    // Define a method to stop the listener
    private void stopMyLocationListener() {
        locationManager.removeUpdates(locationListener);
    }
    private String getMyLocation() {
    	return String.valueOf(myLocation.getLatitude()) + ", " + String.valueOf(myLocation.getLongitude());
    }
    private String getMyShortLocation() {
    	return String.valueOf(myLocation.getLatitude()).substring(0,7) + ", " + String.valueOf(myLocation.getLongitude()).substring(0,8);
    }
    
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.slushee_menu, menu);
        return true;
    }
//    public RadioGroup.OnCheckedChangeListener(RadioGroup group, int checkedId) {
//    }
    private OnClickListener radio_listener = new OnClickListener() {
        public void onClick(View v) {
            // Perform action on clicks
            RadioButton rb = (RadioButton) v;
            Toast.makeText(getApplicationContext(), rb.getText(), Toast.LENGTH_SHORT).show();
            // toggle enable state of the text box for specifying a location
            EditText et = (EditText) findViewById(R.id.entry);
        	et.setEnabled(rb.getId()==R.id.opt_specify);
        	et.setClickable(rb.getId()==R.id.opt_specify);
        	//et.setFocusable(rb.getId()==R.id.opt_specify);
        		
        }
    };
    
    LinearLayout mLinearLayout;
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
        case R.id.find_near_by:
        	Context context = getApplicationContext();
        	CharSequence text = "I will get right on that!";
        	int duration = Toast.LENGTH_SHORT;
        	Toast.makeText(context, text, duration).show();
        	return true;
        case R.id.find_on_route:
            // Create a LinearLayout in which to add the ImageView
            mLinearLayout = new LinearLayout(this);

            // Instantiate an ImageView and define its properties
            ImageView i = new ImageView(this);
            i.setImageResource(R.drawable.slurpee);
            i.setAdjustViewBounds(true); // set the ImageView bounds to match the Drawable's dimensions
            //i.setLayoutParams(new Gallery.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));

            // Add the ImageView to the layout and set the layout as the content view
            mLinearLayout.addView(i);
            setContentView(mLinearLayout);
            return true;
        case R.id.quit:
            return true;
        default:
            return super.onOptionsItemSelected(item);
        }
    }
}

