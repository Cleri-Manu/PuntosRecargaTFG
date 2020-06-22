package es.usal.tfg1.ViewC.fragmentos.mapa;

import android.content.Intent;
import android.location.Location;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.util.ArrayList;

import es.usal.tfg1.R;
import es.usal.tfg1.ViewC.StartActivity;
import es.usal.tfg1.databinding.FragmentMapaBinding;
import es.usal.tfg1.model.PuntoRecarga;
import es.usal.tfg1.vm.VM;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link mapa#newInstance} factory method to
 * create an instance of this fragment.
 */
public class mapa extends Fragment implements OnMapReadyCallback {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;
    private FragmentMapaBinding binding;
    private VM myVM;
    private GoogleMap myMap;
    private MapView myMapView;
    private ArrayList<Marker> myMarkers;
    private boolean isMod = false;
    private double currentLat;
    private double currentLon;
    public mapa() { }
    public static mapa newInstance(String param1, String param2) {
        mapa fragment = new mapa();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentMapaBinding.inflate(inflater, container, false);
        myVM = new ViewModelProvider(requireActivity()).get(VM.class);
        binding.setMyVM(myVM);
        binding.setLifecycleOwner(this);
        return binding.getRoot();
    }

    public void logOutUser(View view) {
        final Intent myIntent = new Intent(getActivity(), StartActivity.class);
        AuthUI.getInstance()
                .signOut(getActivity())
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    public void onComplete(@NonNull Task<Void> task) {
                        startActivity(myIntent);
                    }
                });
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        myMapView = getView().findViewById(R.id.map_view);
        if(myMapView != null) {
            myMapView.onCreate(null);
            myMapView.onResume();
            myMapView.getMapAsync(this);
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        MapsInitializer.initialize(getContext());
        myMap = googleMap;
        myMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(40.416775, -3.103790), 5.35f));
        if(myMarkers == null) {
            myMarkers = new ArrayList<Marker>();
        }
        //Se cargan los puntos de recarga
        FusedLocationProviderClient myClient = LocationServices.getFusedLocationProviderClient(getContext());
        myClient.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
            @Override
            public void onComplete(@NonNull Task<Location> task) {
                myVM.loadRecyclerListComplete(task.getResult());
            }
        });
        myVM.getRecyclerListCompleteData().observe(getViewLifecycleOwner(), new Observer<ArrayList<PuntoRecarga>>() {
            @Override
            public void onChanged(ArrayList<PuntoRecarga> puntoRecargas) {  //Si ya existen se borran
                if(myMarkers != null) {
                    if(myMarkers.size() > 0) {
                        for (Marker m: myMarkers) {
                            m.remove();
                        }
                    }
                }
                myMarkers.clear();
                for (PuntoRecarga p: puntoRecargas) {   //Se añaden al mapa
                    if(myMap != null) {
                        myMarkers.add(myMap.addMarker(new MarkerOptions().position(new LatLng(p.getParada().getLatitud(), p.getParada().getLongitud())).title(p.getNombre())));
                    }
                }
                isMod = myVM.getModSelected();
                if(isMod) {
                    myMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(myVM.getInfoPR().getValue().getParada().getLatitud(), myVM.getInfoPR().getValue().getParada().getLongitud()), 16.0f));
                }
            }
        });
    }
}
