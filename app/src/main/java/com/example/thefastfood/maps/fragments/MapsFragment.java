package com.example.thefastfood.maps.fragments;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.thefastfood.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.text.DecimalFormat;

///**
// * A simple {@link Fragment} subclass.
// * Use the {@link MapsFragment#newInstance} factory method to
// * create an instance of this fragment.
// */
public class MapsFragment extends Fragment implements LocationListener {

    // Gestion de la localisation
    private LocationManager locationManager;

    // Gestion de la carte
    private SupportMapFragment mapFragment;
    private GoogleMap googleMap;

    // Position de l'utilisateur
    private LatLng userLatLng;

    // Distance de l'utilisateur avec Fast Food
    private double userDistance;

    // Request code pour gerer les permissions
    private static final int PERMS_ID = 1999;

    // Position du FastFood
    private static final LatLng FAST_FOOD_LAT_LNG = new LatLng(-20.905484263583407, 55.50019844285975);




    public MapsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Création de la vue principale
        View view = inflater.inflate(R.layout.fragment_maps, container, false);

        // Recuperation du SupportMapFragment
        mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.mapFrag);
        Log.d("testeur405", String.valueOf(mapFragment));

        // Inflate the layout for this fragment
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d("testeur405", String.valueOf(mapFragment));

        // Verifie les permission
        checkPermission();
    }

    @Override
    public void onPause() {
        super.onPause();
        if (locationManager != null) {
            locationManager.removeUpdates(this);
        }
    }

    /**
     * Verifie si les permission nécessaire à la localisation sont accordés sinon les demandes + chargement de la carte
     */
    private void checkPermission(){
        // Récupères l'activité appelante
        Activity activity = getActivity();

        // Gestion des permission
        locationManager = (LocationManager) activity.getSystemService(activity.LOCATION_SERVICE);
        if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            if (ActivityCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                    && ActivityCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

                ActivityCompat.requestPermissions(activity, new String[]{
                        Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.ACCESS_COARSE_LOCATION
                }, PERMS_ID);
                return;
            }
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 0, this);
        }
        if (locationManager.isProviderEnabled(LocationManager.PASSIVE_PROVIDER)){
            locationManager.requestLocationUpdates(LocationManager.PASSIVE_PROVIDER, 5000, 0, this);
        }
        if (locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)){
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 5000, 0, this);
        }

        // Charge la carte
        loadMap();
    }

    /**
     * Traitement des permission
     * @param requestCode code de la requete de permission
     * @param permissions les permission demandées
     * @param grantResults les permissions accordées
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMS_ID) {
            checkPermission();
        }
    }

    /**
     * Charge la carte googleMap et l'initialise
     */
    private void loadMap(){
        // Carte prête
        mapFragment.getMapAsync(new OnMapReadyCallback() {
            @SuppressLint("MissingPermission")
            @Override
            public void onMapReady(GoogleMap googleMap) {
                // On récupère la map
                MapsFragment.this.googleMap = googleMap;



//                googleMap.setMinZoomPreference(100);
//                googleMap.setMaxZoomPreference(1000);

                // Permettre le centrage sur la position du telephone
                googleMap.setMyLocationEnabled(true);

                // Type de carte
                googleMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);

                // Placement de la caméra et zoom
                googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(FAST_FOOD_LAT_LNG, 15f));

                // Marker localisation de FastFood
                final MarkerOptions mFastfood = new MarkerOptions()
                        .position(FAST_FOOD_LAT_LNG)
                        .title("The Fast Food")
                        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));
                googleMap.addMarker(mFastfood);

                // Gestion des clic sur le marquer de FastFood ==> Affichage du clic sur l'icone
                googleMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener(){
                    @Override
                    public boolean onMarkerClick(Marker marker) {
                        if(userLatLng != null && marker.getTitle().equals("The Fast Food") ){
                            if(  userDistance >= 1000){
                                DecimalFormat df = new DecimalFormat("###.###");
                                Toast.makeText(getActivity(), String.format("Vous êtes à %s km", df.format(userDistance/1000)) , Toast.LENGTH_LONG  ).show();
                            }else{
                                Toast.makeText(getActivity(), String.format("Vous êtes à %d m", (int) userDistance), Toast.LENGTH_LONG  ).show();
                            }
                        }

                        return false;
                    }
                });
            }
        });
    }

    /**
     * Called when the location has changed.
     *
     * @param location the updated location
     */
    @Override
    public void onLocationChanged(@NonNull Location location) {
        // Mises à jour de la position de l'utilisateur
        double lat = location.getLatitude();
        double lng = location.getLongitude();
        userLatLng = new LatLng(lat, lng);

        // Distance de l'utilisateur au fastfood
        userDistance = getDistance(FAST_FOOD_LAT_LNG, userLatLng);

    }

    /**
     * Calcule la distance en deux LatLng
     * @param l1 LatLng 1
     * @param l2 LatLng 2
     * @return distance entre LatLng 1 et LatLng 2
     */
    public double getDistance(LatLng l1, LatLng l2){
        Location loc1 = new Location("");
        loc1.setLatitude(l1.latitude);
        loc1.setLongitude(l1.longitude);

        Location loc2 = new Location("");
        loc2.setLatitude(l2.latitude);
        loc2.setLongitude(l2.longitude);

        return loc1.distanceTo(loc2);
    }

    /**
     * This callback will never be invoked on Android Q and above, and providers can be considered
     * as always in the state.
     *
     * @param provider
     * @param status
     * @param extras
     * @deprecated This callback will never be invoked on Android Q and above.
     */
    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    /**
     * Called when the provider is enabled by the user.
     *
     * @param provider the name of the location provider that has become enabled
     */
    @Override
    public void onProviderEnabled(@NonNull String provider) {

    }

    /**
     * Called when the provider is disabled by the user. If requestLocationUpdates
     * is called on an already disabled provider, this method is called
     * immediately.
     *
     * @param provider the name of the location provider that has become disabled
     */
    @Override
    public void onProviderDisabled(@NonNull String provider) {

    }



}