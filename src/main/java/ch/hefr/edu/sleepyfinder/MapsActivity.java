package ch.hefr.edu.sleepyfinder;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import ch.hefr.edu.sleepyfinder.ch.hefr.edu.sleepyfinder.data.PlaceDownloadCallback;
import ch.hefr.edu.sleepyfinder.ch.hefr.edu.sleepyfinder.data.PlaceInfo;
import ch.hefr.edu.sleepyfinder.ch.hefr.edu.sleepyfinder.data.PlaceInfoTask;
import ch.hefr.edu.sleepyfinder.ch.hefr.edu.sleepyfinder.data.WebPlaceUtilities;
import ch.hefr.edu.sleepyfinder.ch.rangeEnum.Range;



public class MapsActivity extends FragmentActivity implements PlaceDownloadCallback,GoogleMap.OnCameraChangeListener, OnMapReadyCallback, GoogleApiClient.OnConnectionFailedListener,GoogleMap.OnMarkerClickListener, AdapterView.OnItemSelectedListener {


    private GoogleMap mMap;
    private GoogleApiClient mGoogleApiClient;
    private static final int PLACE_PICKER_REQUEST = 1;
    private HashMap<Marker, Place> markerPlaceHashMap = new HashMap<Marker,Place>();

    private HashMap<String, String> params = new HashMap<String, String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        params.put("radius", "500");
        params.put("type", "lodging");
        mGoogleApiClient = new GoogleApiClient
                .Builder(this)
                .addApi(Places.GEO_DATA_API)
                .addApi(Places.PLACE_DETECTION_API)
                .enableAutoManage(this, this)
                .build();


        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        Spinner spinner = (Spinner) findViewById(R.id.choose_range);
        spinner.setAdapter(new ArrayAdapter<Range>(this,android.R.layout.simple_spinner_item,Range.values()));
        spinner.setOnItemSelectedListener(this);
        mapFragment.getMapAsync(this);

    }

    @Override
    protected void onStart() {
        super.onStart();
        /*int PLACE_PICKER_REQUEST = 1;
        PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();

        try {
            startActivityForResult(builder.build(this), PLACE_PICKER_REQUEST);
        } catch (GooglePlayServicesRepairableException e) {
            e.printStackTrace();
        } catch (GooglePlayServicesNotAvailableException e) {
            e.printStackTrace();
        }*/
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PLACE_PICKER_REQUEST) {
            if (resultCode == RESULT_OK) {
                Place place = PlacePicker.getPlace(data, this);
                String toastMsg = String.format("Place: %s", place.getName());
                Toast.makeText(this, toastMsg, Toast.LENGTH_LONG).show();
                LatLng pos = place.getLatLng();

                mMap.addMarker(new MarkerOptions().position(pos).title("Marker in Sydney"));
                mMap.moveCamera(CameraUpdateFactory.newLatLng(pos));
                
            }
        }
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setOnCameraChangeListener(this);
        mMap.setOnMarkerClickListener(this);

        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        Criteria criteria = new Criteria();

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        Location location = locationManager.getLastKnownLocation(locationManager.getBestProvider(criteria, false));
        if (location != null)
        {
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(location.getLatitude(), location.getLongitude()), 13));

            CameraPosition cameraPosition = new CameraPosition.Builder()
                    .target(new LatLng(location.getLatitude(), location.getLongitude()))      // Sets the center of the map to location user
                    .zoom(17)                 // Sets the tilt of the camera to 30 degrees
                    .build();                   // Creates a CameraPosition from the builder
            mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
        }
    }

    public void showResult(List<Place> result){
        for(Place place : result){
            if(markerPlaceHashMap.containsValue(place))continue;
            if(place == null)continue;
            Log.i("MapsActivity",place.getLatLng().latitude +" "+place.getLatLng().longitude);
            markerPlaceHashMap.put(mMap.addMarker(new MarkerOptions()
                    .position(place.getLatLng())
                    .title(place.getName() != null ? place.getName().toString() : "Missing name")),place);
        }

    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        Place p = markerPlaceHashMap.get(marker);

        new PlaceInfoTask(mGoogleApiClient){
            @Override
            protected void onPostExecute(PlaceInfo place) {
                PlaceInfoDialogFragment dialog = new PlaceInfoDialogFragment();
                dialog.setPlace(place);
                dialog.show(getFragmentManager(),"info");
            }
        }.execute(p.getId());
        return false;
    }



    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Toast.makeText(this,"Cannot connect to Google map",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        Range range = (Range) parent.getItemAtPosition(position);
        int rangeValue = range.getRange();
        Toast.makeText(this,rangeValue+"m",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {}


    public void onCameraChange(CameraPosition cameraPosition) {
        Log.i("MapsActivity","update map");
        LatLng pos = mMap.getCameraPosition().target;
        params.put("location",pos.latitude+","+pos.longitude);
        try {
            WebPlaceUtilities.request(this,params,this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void doCallback(List<Place> list) {
        final List<Place> temp = new ArrayList<Place>(list);
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                showResult(temp);
            }
        });

    }
}
