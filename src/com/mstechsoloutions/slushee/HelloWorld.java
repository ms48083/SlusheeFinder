/*
 * Copyright (C) 2007 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.mstechsoloutions.slushee;

// Need the following import to get access to the app resources, since this
// class is in a sub-package.
//import android.app.Activity;
import java.util.List;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.mstechsoloutions.slushee.R;
import com.google.android.maps.GeoPoint;
import com.google.android.maps.ItemizedOverlay;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;
import com.google.android.maps.MyLocationOverlay;
import com.google.android.maps.OverlayItem;
import com.mstechsoloutions.slushee.SlusheeItemizedOverlay;

/**
 * Simple example of writing an application Activity.
 * Hello World</a></h3>

<p>This demonstrates the basic code needed to write a Screen activity.</p>

<h4>Demo</h4>
App/Activity/Hello World
 
<h4>Source files</h4>
 * <table class="LinkTable">
 *         <tr>
 *             <td >src/com.example.android.apis/app/HelloWorld.jaHelloWorld.javava</td>
 *             <td >The Hello World Screen implementation</td>
 *         </tr>
 *         <tr>
 *             <td >/res/any/layout/hello_world.xml</td>
 *             <td >Defines contents of the screen</td>
 *         </tr>
 * </table> 
 */
public class HelloWorld extends MapActivity
{
    /**
     * Initialization of the Activity after it is first created.  Must at least
     * call {@link android.app.Activity#setContentView setContentView()} to
     * describe what is to be displayed in the screen.
     */
	MapView mapView;
    MapController mc;
    GeoPoint p;
    MyLocationOverlay mlo;
    
    @Override
	protected void onCreate(Bundle savedInstanceState)
    {
        // Be sure to call the super class.
        super.onCreate(savedInstanceState);

        // See assets/res/any/layout/hello_world.xml for this
        // view layout definition, which is being set here as
        // the content of our screen.
        setContentView(R.layout.hello_world);
        mapView = (MapView) findViewById(R.id.my_map);
        mapView.setBuiltInZoomControls(true);

        // retrieve the default center location
        Bundle c = getIntent().getExtras();  // pass a value of location to display
        String coordinates[] = c.getString("location").split(",");
        mc = mapView.getController();
        //String coordinates[] = {"42.55313", "-83.34"};  // near 48322
        double lat = Double.parseDouble(coordinates[0]);
        double lng = Double.parseDouble(coordinates[1]);
 
        p = new GeoPoint(
            (int) (lat * 1E6), 
            (int) (lng * 1E6));
 
        mc.animateTo(p);
        mc.setZoom(13);
        mapView.invalidate();
    	Context context = getApplicationContext();
        Toast.makeText(context, "Searching " + c.getString("location"), Toast.LENGTH_LONG).show();

        List<Overlay> mapOverlays = mapView.getOverlays();
        Drawable drawable = this.getResources().getDrawable(R.drawable.tpmap_pointer);
        SlusheeItemizedOverlay itemizedoverlay = new SlusheeItemizedOverlay(drawable);        
        OverlayItem overlayitem = new OverlayItem(p, "You are here!", "You are here!!");
        itemizedoverlay.addOverlay(overlayitem);
        mapOverlays.add(itemizedoverlay);
        
        // show MyLocationOverlay
        mlo = new MyLocationOverlay(context, mapView);
        mlo.enableMyLocation();  // should already be enabled??
        mlo.enableCompass();
        mapOverlays.add(mlo);
        
    }
    @Override
    protected boolean isRouteDisplayed() { return false; }
    @Override
    protected void onPause()
    {	super.onPause();
    	mlo.disableMyLocation();
    	mlo.disableCompass();
    }
    /** (re-)enable location and compass updates */
    @Override
    public void onResume() {
        super.onResume();        
        mlo.enableCompass();
        mlo.enableMyLocation();          
        //mlo.followLocation(true);
    }
}
