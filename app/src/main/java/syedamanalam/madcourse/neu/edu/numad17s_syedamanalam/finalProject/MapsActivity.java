package syedamanalam.madcourse.neu.edu.numad17s_syedamanalam.finalProject;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocompleteFragment;
import com.google.android.gms.location.places.ui.PlaceSelectionListener;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import syedamanalam.madcourse.neu.edu.numad17s_syedamanalam.R;

import static syedamanalam.madcourse.neu.edu.numad17s_syedamanalam.R.id.map;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        LocationListener {

    private GoogleMap mMap;
    GoogleApiClient mGoogleApiClient;
    Location mLastLocation;
    Marker mCurrLocationMarker;
    LocationRequest mLocationRequest;
    public Button homeLocationFromPin;
    public Button officeLocationFromPin;
    public static double homeLatitude;
    public static double officeLatitude;
    public static double homeLongitude;
    public static double officeLongitude;
    public int count = 0;
    public MarkerOptions placedHomeMarker;
    public MarkerOptions placedOfficeMarker;
    public static final int LOCATION_REQUEST_CODE = 99;
    public SupportMapFragment mapFragment;
    public Location onClickLocation = new Location("");
    public float zoomLevel = 13;
    public TextView textOnMap;
    public PlaceAutocompleteFragment autocompleteFragment;
    public double finalHomeLat, finalHomeLon = 0;
    public static String userHomeLocation = "Boston";
    public static String userOfficeLocation = "Boston";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        requestPermission(Manifest.permission.ACCESS_FINE_LOCATION, LOCATION_REQUEST_CODE);

        textOnMap = (TextView) findViewById(R.id.textOnMapTop);
        textOnMap.setText("Tap anywhere on map to select your Home Location\nor find your exact location by typing the name of location above");

        autocompleteFragment = (PlaceAutocompleteFragment) getFragmentManager().findFragmentById(R.id.place_autocomplete_fragment);


        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(map);
        mapFragment.getMapAsync(this);


        homeLocationFromPin = (Button) findViewById(R.id.confirmHomeLocation);
        homeLocationFromPin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (String.valueOf(homeLongitude) != null && String.valueOf(homeLatitude) != null) {
                    //Get nearby Places here based on Latitude and Longitude
//                    Toast.makeText(MapsActivity.this, getAddress(homeLatitude, homeLongitude), Toast.LENGTH_LONG).show();
                    if (!checkConnectionToInternet()) {
//                        Snackbar.make(getWindow().getDecorView(), "Please connect to internet", Snackbar.LENGTH_LONG).show();
                        customSnack("Please connect to internet",1);
                    } else {
                        userHomeLocation = getNameOnly(homeLatitude, homeLongitude);
                        showAlertBoxForHomeLocationConfirmation(getAddress(homeLatitude, homeLongitude));
                    }
                } else {
//                    Toast.makeText(MapsActivity.this, "Please drop pin to home location first", Toast.LENGTH_SHORT).show();
                    if (!checkConnectionToInternet()) {
//                        Snackbar.make(getWindow().getDecorView(), "Please connect to internet", Snackbar.LENGTH_LONG).show();
                        customSnack("Please connect to internet",1);
                    } else {
//                        Snackbar.make(getWindow().getDecorView(), "Please drop pin to home location first", Snackbar.LENGTH_SHORT).show();
                        customSnack("Please drop pin to home location first",0);
                    }
                }
            }
        });


        officeLocationFromPin = (Button) findViewById(R.id.confirmOfficeLocation);
        officeLocationFromPin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (String.valueOf(officeLatitude) != null && String.valueOf(officeLongitude) != null) {
                    //Get nearby Places here based on Latitude and Longitude

                    if (!checkConnectionToInternet()) {
//                        Snackbar.make(getWindow().getDecorView(), "Please connect to internet", Snackbar.LENGTH_LONG).show();
                        customSnack("Please connect to internet",1);
                    }
//                    Toast.makeText(MapsActivity.this, getAddress(homeLatitude, homeLongitude), Toast.LENGTH_LONG).show();
                    else {
                        userOfficeLocation = getNameOnly(officeLatitude, officeLongitude);
                        showAlertBoxForOfficeLocationConfirmation(getAddress(officeLatitude, officeLongitude));
                    }
                } else {
                    if (!checkConnectionToInternet()) {
//                        Snackbar.make(getWindow().getDecorView(), "Please connect to internet", Snackbar.LENGTH_LONG).show();
                        customSnack("Please connect to internet",1);
                    } else {
//                    Toast.makeText(MapsActivity.this, "Please drop pin to office location first", Toast.LENGTH_SHORT).show();
//                        Snackbar.make(getWindow().getDecorView(), "Please drop pin to office location first", Snackbar.LENGTH_SHORT).show();
                        customSnack("Please drop pin to office location first",0);
                    }
                }
            }
        });


    }//End Of OnCreate

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
//        mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
        final LatLng boston = new LatLng(42.3601, -71.0589);
        mMap.moveCamera(CameraUpdateFactory.newLatLng(boston));
        mMap.animateCamera(CameraUpdateFactory.zoomTo(zoomLevel));

        placedHomeMarker = new MarkerOptions().draggable(true);
        placedHomeMarker.title("Home Location");
        placedHomeMarker.snippet("This will be set as home location");

        placedOfficeMarker = new MarkerOptions().draggable(true);
        placedOfficeMarker.title("Office Location");
        placedOfficeMarker.snippet("This will be set as office location");


        //Change Icons for Markers for better
        placedHomeMarker.icon(BitmapDescriptorFactory.fromResource(R.drawable.homepin));
        placedOfficeMarker.icon(BitmapDescriptorFactory.fromResource(R.drawable.officepin));


        showAlertBox("Home");

        if (ActivityCompat.checkSelfPermission
                (this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission
                (this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {

            mMap.setMyLocationEnabled(true);
            mMap.getUiSettings().setMyLocationButtonEnabled(false);

        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermission(Manifest.permission.ACCESS_FINE_LOCATION, LOCATION_REQUEST_CODE);
            }
        }


        autocompleteFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(Place place) {
                if (!checkConnectionToInternet()) {
//                    Snackbar.make(getWindow().getDecorView(), "Please connect to internet", Snackbar.LENGTH_LONG).show();
                    customSnack("Please connect to internet",1);
                } else {
                    if (count == 0) {
                        homeLocationFromPin.setVisibility(View.VISIBLE);
                        mMap.clear();
                        homeLatitude = place.getLatLng().latitude;
                        homeLongitude = place.getLatLng().longitude;
                        mMap.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(homeLatitude, homeLongitude)));
                        onClickLocation.setLatitude(homeLatitude);
                        onClickLocation.setLongitude(homeLongitude);
                        onLocationChanged(onClickLocation);
//                    mMap.animateCamera(CameraUpdateFactory.zoomTo(15));
                        placedHomeMarker.position(new LatLng(homeLatitude, homeLongitude));
                        mMap.addMarker(placedHomeMarker).showInfoWindow();
//                    Toast.makeText(MapsActivity.this, getNameOnly(homeLatitude, homeLongitude), Toast.LENGTH_SHORT).show();
//                        Snackbar.make(getWindow().getDecorView(), "Selected Home Location: " + getNameOnly(homeLatitude, homeLongitude), Snackbar.LENGTH_SHORT).show();
                        customSnack( getNameOnly(homeLatitude, homeLongitude),0);

                    } else if (count == 3) {

                        placedHomeMarker.draggable(false);


                        mMap.clear();
                        MarkerOptions localFinalHome = new MarkerOptions().position(new LatLng(finalHomeLat, finalHomeLon));
                        localFinalHome.title("Home Location");
                        localFinalHome.draggable(false);
                        localFinalHome.snippet("This will be set as home location");
                        localFinalHome.icon(BitmapDescriptorFactory.fromResource(R.drawable.homepin));
                        mMap.addMarker(localFinalHome).showInfoWindow();
//                        Toast.makeText(MapsActivity.this,"count 3 loop",Toast.LENGTH_LONG).show();

                        officeLocationFromPin.setVisibility(View.VISIBLE);
                        officeLatitude = place.getLatLng().latitude;
                        officeLongitude = place.getLatLng().longitude;
                        mMap.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(officeLatitude, officeLongitude)));
                        placedOfficeMarker.position(new LatLng(officeLatitude, officeLongitude));
                        mMap.addMarker(placedOfficeMarker).showInfoWindow();

//                        onClickLocation.setLatitude(officeLatitude);
//                        onClickLocation.setLongitude(officeLongitude);
//                        onLocationChanged(onClickLocation);
//                    Toast.makeText(MapsActivity.this, getNameOnly(officeLatitude, officeLongitude), Toast.LENGTH_SHORT).show();
//                        Snackbar.make(getWindow().getDecorView(), "Selected Office Location: " + getNameOnly(officeLatitude, officeLongitude), Snackbar.LENGTH_SHORT).show();
                        customSnack( getNameOnly(officeLatitude, officeLongitude),0);
                    }
                }
            }

            @Override
            public void onError(Status status) {
                // TODO: Handle the error.
                Log.e("TAG", "Google error code " + status + "\nPlease Try Again");
            }
        });


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                //    here to request the missing permissions, and then overriding
                //    public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //    int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                mMap.setMyLocationEnabled(true);
                mMap.getUiSettings().setMyLocationButtonEnabled(false);
                mMap.animateCamera(CameraUpdateFactory.zoomTo(15));

            }

            buildGoogleApiClient();

            mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
                @Override
                public void onMapClick(LatLng latLng) {
                    if (!checkConnectionToInternet()) {
//                        Snackbar.make(getWindow().getDecorView(), "Please connect to internet", Snackbar.LENGTH_LONG).show();
                        customSnack("Please connect to internet",1);
                    } else {

                        if (count == 0) {
                            //Make the confirm button available on first tap
                            homeLocationFromPin.setVisibility(View.VISIBLE);

                            //add(point);
                            mMap.clear();

                            placedHomeMarker.position(latLng);

                            onClickLocation.setLatitude(latLng.latitude);
                            onClickLocation.setLongitude(latLng.longitude);
                            mMap.addMarker(placedHomeMarker).showInfoWindow();
                            homeLatitude = latLng.latitude;
                            homeLongitude = latLng.longitude;
                            onLocationChanged(onClickLocation);
//                        Toast.makeText(MapsActivity.this, getNameOnly(homeLatitude, homeLongitude), Toast.LENGTH_SHORT).show();
//                            Snackbar.make(getWindow().getDecorView(), "Selected Home Location: " + getNameOnly(homeLatitude, homeLongitude), Snackbar.LENGTH_SHORT).show();
                            customSnack( getNameOnly(homeLatitude, homeLongitude),0);
                        } else if (count == 3)

                        {
                            officeLocationFromPin.setVisibility(View.VISIBLE);
                            mMap.clear();
                            MarkerOptions localFinalHome = new MarkerOptions().position(new LatLng(finalHomeLat, finalHomeLon));
                            localFinalHome.title("Home Location");
                            localFinalHome.draggable(false);
                            localFinalHome.snippet("This will be set as home location");
                            localFinalHome.icon(BitmapDescriptorFactory.fromResource(R.drawable.homepin));
                            mMap.addMarker(localFinalHome).showInfoWindow();


                            placedOfficeMarker.position(latLng);
                            mMap.addMarker(placedOfficeMarker).showInfoWindow();

                            officeLatitude = latLng.latitude;
                            officeLongitude = latLng.longitude;
                            onClickLocation.setLatitude(latLng.latitude);
                            onClickLocation.setLongitude(latLng.longitude);
                            onLocationChanged(onClickLocation);
//                        Toast.makeText(MapsActivity.this, getNameOnly(officeLatitude, officeLongitude), Toast.LENGTH_SHORT).show();
//                            Snackbar.make(getWindow().getDecorView(), "Selected Office Location: " + getNameOnly(officeLatitude, officeLongitude), Snackbar.LENGTH_SHORT).show();
                            customSnack( getNameOnly(officeLatitude, officeLongitude),0);
                        }
                    }
                }
            });
        }//Ends for Version greater than M
        else {

            buildGoogleApiClient();
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                mMap.setMyLocationEnabled(true);
                mMap.getUiSettings().setMyLocationButtonEnabled(false);
            }

            mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
                @Override
                public void onMapClick(LatLng latLng) {
                    if (!checkConnectionToInternet()) {
//                        Snackbar.make(getWindow().getDecorView(), "Please connect to internet", Snackbar.LENGTH_LONG).show();
                        customSnack("Please connect to internet",1);
                    } else {

                        if (count == 0) {
                            //Make the confirm button available on first tap
                            homeLocationFromPin.setVisibility(View.VISIBLE);
                            //add(point);
                            mMap.clear();

                            placedHomeMarker.position(latLng);

                            onClickLocation.setLatitude(latLng.latitude);
                            onClickLocation.setLongitude(latLng.longitude);
                            mMap.addMarker(placedHomeMarker).showInfoWindow();
                            homeLatitude = latLng.latitude;
                            homeLongitude = latLng.longitude;
                            onLocationChanged(onClickLocation);
//                        Toast.makeText(MapsActivity.this, getNameOnly(homeLatitude, homeLongitude), Toast.LENGTH_SHORT).show();
//                            Snackbar.make(getWindow().getDecorView(), "Selected Home Location: " + getNameOnly(homeLatitude, homeLongitude), Snackbar.LENGTH_SHORT).show();
                            customSnack( getNameOnly(homeLatitude, homeLongitude),0);

                        } else if (count == 3) {

                            officeLocationFromPin.setVisibility(View.VISIBLE);

                            mMap.clear();
                            MarkerOptions localFinalHome = new MarkerOptions().position(new LatLng(finalHomeLat, finalHomeLon));
                            localFinalHome.title("Home Location");
                            localFinalHome.draggable(false);
                            localFinalHome.snippet("This will be set as home location");
                            localFinalHome.icon(BitmapDescriptorFactory.fromResource(R.drawable.homepin));
                            mMap.addMarker(localFinalHome).showInfoWindow();


                            placedOfficeMarker.position(latLng);
                            mMap.addMarker(placedOfficeMarker).showInfoWindow();

                            officeLatitude = latLng.latitude;
                            officeLongitude = latLng.longitude;
                            onClickLocation.setLatitude(latLng.latitude);
                            onClickLocation.setLongitude(latLng.longitude);
                            onLocationChanged(onClickLocation);
//                        Toast.makeText(MapsActivity.this, getNameOnly(homeLatitude, homeLongitude), Toast.LENGTH_SHORT).show();
//                        Toast.makeText(MapsActivity.this, getNameOnly(officeLatitude, officeLongitude), Toast.LENGTH_SHORT).show();
//                            Snackbar.make(getWindow().getDecorView(), "Selected Office Location: " + getNameOnly(officeLatitude, officeLongitude), Snackbar.LENGTH_SHORT).show();
                            customSnack(getNameOnly(officeLatitude, officeLongitude),0);
                        }
                    }

                }
            });
        }
    }//End of onMap Ready

    protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
        mGoogleApiClient.connect();
    }

    @Override
    public void onConnected(Bundle bundle) {

        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(1000);
        mLocationRequest.setFastestInterval(1000);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
        }
    }

    @Override
    public void onConnectionSuspended(int i) {
    }


    @Override
    public void onLocationChanged(Location location) {

        mLastLocation = location;
        if (mCurrLocationMarker != null) {
            mCurrLocationMarker.remove();
        }

        //Set a marker drag listener
        mMap.setOnMarkerDragListener(new GoogleMap.OnMarkerDragListener() {
            @Override
            public void onMarkerDragStart(Marker marker) {

            }

            @Override
            public void onMarkerDrag(Marker marker) {
            }

            @Override
            public void onMarkerDragEnd(Marker marker) {
                if (!checkConnectionToInternet()) {
//                    Snackbar.make(getWindow().getDecorView(), "Please connect to internet", Snackbar.LENGTH_LONG).show();
                    customSnack("Please connect to internet",1);
                } else {

                    mMap.animateCamera(CameraUpdateFactory.newLatLng(marker.getPosition()));

                //Get the latitude and longitude of the place where user dropped the marker
//                Toast.makeText(MapsActivity.this, "Latitude:" + String.valueOf(marker.getPosition().latitude), Toast.LENGTH_LONG).show();
                homeLatitude = marker.getPosition().latitude;
                officeLatitude = marker.getPosition().latitude;
//                Toast.makeText(MapsActivity.this, "Longitude:" + String.valueOf(marker.getPosition().longitude), Toast.LENGTH_LONG).show();
                homeLongitude = marker.getPosition().longitude;
                officeLongitude = marker.getPosition().longitude;
//                Toast.makeText(MapsActivity.this, getNameOnly(marker.getPosition().latitude, marker.getPosition().longitude), Toast.LENGTH_SHORT);
//                    Snackbar.make(getWindow().getDecorView(), "Selected Location: " + getNameOnly(marker.getPosition().latitude, marker.getPosition().longitude), Snackbar.LENGTH_SHORT).show();
                    customSnack( getNameOnly(marker.getPosition().latitude, marker.getPosition().longitude),0);
                }
            }
        });


        //stop location updates
        if (mGoogleApiClient != null) {
            LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);
        }

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
    }


    public void showAlertBox(String LocationName) {

        AlertDialog.Builder b1 = new AlertDialog.Builder(MapsActivity.this);
        b1.setMessage("Please select your " + LocationName + " location.\nTap anywhere on the screen to select your "
                + LocationName +
                " location and press button to confirm.");
        b1.setCancelable(false);

        b1.setPositiveButton("Ok",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });

        AlertDialog customAlertBox = b1.create();
        customAlertBox.show();

    }


    public void showAlertBoxForHomeLocationConfirmation(String reverseGeoStr) {

        AlertDialog.Builder b1 = new AlertDialog.Builder(MapsActivity.this);
        b1.setMessage("Confirm your home location or search again\n" + reverseGeoStr);
        b1.setCancelable(false);

        b1.setPositiveButton("Confirm",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        if (!checkConnectionToInternet()) {
                            customSnack("Please connect to internet",1);
//                            Snackbar.make(getWindow().getDecorView(), "Please connect to internet", Snackbar.LENGTH_LONG).show();
                        } else {
                            finalHomeLat = homeLatitude;
                            finalHomeLon = homeLongitude;
                            count = 3;
                            placedHomeMarker.draggable(false);
                            homeLocationFromPin.setVisibility(View.GONE);
                            showAlertBox("Office");
                            textOnMap.setText("Home Location Confirmed. Tap anywhere on map to select your Office Location now\n"
                                    + "or find the exact location by typing in the name");
                        }

                    }
                });

        b1.setNegativeButton("Do it again",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //Toast.makeText(MapsActivity.this, "Place Pin Again", Toast.LENGTH_SHORT).show();
                        customSnack("Place pin again",0);
//                        Snackbar.make(getWindow().getDecorView(), "Place Pin Again", Snackbar.LENGTH_SHORT).show();
                    }
                });
        AlertDialog customAlertBox = b1.create();
        customAlertBox.show();

    }


    public void showAlertBoxForOfficeLocationConfirmation(String reverseGeoStr) {

        AlertDialog.Builder b1 = new AlertDialog.Builder(MapsActivity.this);
        b1.setMessage("Confirm your Office location or search again\n" + reverseGeoStr);
        b1.setCancelable(false);

        b1.setPositiveButton("Confirm",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Intent st = new Intent(MapsActivity.this, OfficeStartTime.class);
                        startActivity(st);
                        finish();
                    }
                });

        b1.setNegativeButton("Do it again",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //Toast.makeText(MapsActivity.this, "Place Pin Again", Toast.LENGTH_SHORT).show();
                        customSnack("Place pin again",0);
//                        Snackbar.make(getWindow().getDecorView(), "Place Pin Again", Snackbar.LENGTH_SHORT).show();
                    }
                });
        AlertDialog customAlertBox = b1.create();
        customAlertBox.show();

    }


    private String getAddress(double latitude, double longitude) {
        if (latitude == 0.0 || longitude == 0.0) {
            //Give default values so it does not returns null
            latitude = 42.3601;
            longitude = -71.0589;
        }
        StringBuilder result = new StringBuilder();
        try {
            Geocoder geocoder = new Geocoder(this, Locale.getDefault());
            List<Address> addresses = geocoder.getFromLocation(latitude, longitude, 1);
            if (addresses.size() > 0) {
                Address address = addresses.get(0);
                if (address.getAddressLine(0) != null) {
                    result.append(address.getAddressLine(0)).append("\n");
                }
                if (address.getLocality() != null) {
                    result.append(address.getLocality()).append("\n");
                }
                if (address.getAdminArea() != null) {
                    result.append(address.getAdminArea()).append("\n");
                }
                if (address.getPostalCode() != null) {
                    result.append(address.getPostalCode()).append("\n");
                }
                if (address.getCountryName() != null) {
                    result.append(address.getCountryName());
                }
            }
        } catch (IOException e) {
            Log.e("tag", e.getMessage());
        }

        if (result.toString() == null){
            result.append(" Unable to fetch Location. Please try again later");
        }
        else{
            return result.toString();
        }

        return result.toString();
    }

    private String getNameOnly(double latitude, double longitude) {
        if (latitude == 0.0 || longitude == 0.0) {
            //Give default values so it does not returns null
            latitude = 42.3601;
            longitude = -71.0589;
        }
        StringBuilder result = new StringBuilder();
        try {
            Geocoder geocoder = new Geocoder(this, Locale.getDefault());
            List<Address> addresses = geocoder.getFromLocation(latitude, longitude, 1);
            if (addresses.size() > 0) {
                Address address = addresses.get(0);
                if (address.getAddressLine(0) != null) {
                    result.append(address.getAddressLine(0)).append(" ");
                }
                if (address.getLocality() != null) {
                    result.append(address.getLocality());
                }
            }
        } catch (IOException e) {
            Log.e("tag", e.getMessage());
        }

        if (result.toString() == null){
            result.append(" Unable to fetch Location. Please try again later");
        }
        else{
            return result.toString();
        }
        return result.toString();
    }


    protected void requestPermission(String permissionType, int requestCode) {
        int permission = ContextCompat.checkSelfPermission(this,
                permissionType);

        if (permission != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{permissionType}, requestCode
            );
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case LOCATION_REQUEST_CODE: {

                if (grantResults.length == 0 || grantResults[0] != PackageManager.PERMISSION_GRANTED) {
//                    Toast.makeText(this, "Unable to show location - permission required", Toast.LENGTH_LONG).show();
//                    Toast.makeText(this, "Permission denied. Can't proceed Further", Toast.LENGTH_LONG).show();
                    return;
                } else if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                            && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                        // TODO: Consider calling
                        //    ActivityCompat#requestPermissions
                        // here to request the missing permissions, and then overriding
                        //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                        //                                          int[] grantResults)
                        // to handle the case where the user grants the permission. See the documentation
                        // for ActivityCompat#requestPermissions for more details.

                        return;
                    }
//                    mMap.setMyLocationEnabled(true);
//                    mMap.getUiSettings().setMyLocationButtonEnabled(false);
//                    mMap.animateCamera(CameraUpdateFactory.zoomTo(zoomLevel));


//                    mapFragment.getMapAsync(this);
                }
                break;
            }
        }
    }


    //Check for Internet
    public boolean checkConnectionToInternet() {
        //Get Connectivity Manager
        ConnectivityManager cm1 = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        //Get Active Network Info
        NetworkInfo connected = cm1.getActiveNetworkInfo();
        return connected != null && connected.isConnected();
    }


    // Show custom snackbars
    // If code==0, show a short Snackbar
    // If code==1, show a long Snackbar
    public void customSnack(String stringToDisplay, int code) {

        int lengthOfSnack = 0;

        if (code == 0) {
            lengthOfSnack = Snackbar.LENGTH_SHORT;
        }
        else if (code==1){
            lengthOfSnack = Snackbar.LENGTH_LONG;
        }

        final Snackbar snack = Snackbar.make(findViewById(android.R.id.content), stringToDisplay, lengthOfSnack);

        View view1 = snack.getView();
        FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) view1.getLayoutParams();
        params.gravity = Gravity.CENTER_VERTICAL;
        snack.setAction("Dismiss", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                snack.dismiss();
            }
        });
        snack.show();
    }

    public void onBackPressed(){
        Intent i = new Intent(MapsActivity.this,getUserName.class);
        startActivity(i);
        finish();
    }

}