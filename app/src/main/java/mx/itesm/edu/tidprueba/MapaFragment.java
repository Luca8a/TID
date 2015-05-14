package mx.itesm.edu.tidprueba;


import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;


/**
 * A simple {@link android.app.Fragment} subclass.
 */
public class MapaFragment extends Fragment {

    private static GoogleMap googleMap;

    public MapaFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View vista = inflater.inflate(R.layout.fragment_mapa, container, false);

        if (googleMap != null)
            cargaMapa();
        if (googleMap == null) {
            googleMap = ((MapFragment) getFragmentManager().findFragmentById(R.id.mapa)).getMap();
            if (googleMap != null) {
                cargaMapa();
            }
        }

        return vista;
    }


    private  void cargaMapa() {
        googleMap.setMyLocationEnabled(true);
        googleMap.addMarker(new MarkerOptions().position(new LatLng(19.3582137, -99.1380953)).title("Consultorio"));
        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(19.3582137, -99.1380953), 12.0f));
    } 


    public void cambiaMapaSatellite(){
       googleMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}