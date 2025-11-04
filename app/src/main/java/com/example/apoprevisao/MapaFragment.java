package com.example.apoprevisao;

import android.annotation.SuppressLint;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import androidx.viewpager2.widget.ViewPager2;

import org.osmdroid.api.IMapController;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.Marker;

public class MapaFragment extends Fragment {

    private MapView mapView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_mapa, container, false);
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mapView = view.findViewById(R.id.map_view);
        mapView.setTileSource(TileSourceFactory.MAPNIK);
        mapView.setMultiTouchControls(true);

        final ViewPager2 viewPager = getActivity().findViewById(R.id.view_pager);
        if (viewPager != null) {
            mapView.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    switch (event.getAction()) {
                        case MotionEvent.ACTION_DOWN:
                        case MotionEvent.ACTION_MOVE:
                            viewPager.setUserInputEnabled(false);
                            break;
                        case MotionEvent.ACTION_UP:
                        case MotionEvent.ACTION_CANCEL:
                            viewPager.setUserInputEnabled(true);
                            break;
                    }
                    return mapView.onTouchEvent(event);
                }
            });
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mapView != null) {
            mapView.onResume();


            GeoPoint localizacaoDinamica = new GeoPoint(
                    PrevisaoFragment.latitudeAtual,
                    PrevisaoFragment.longitudeAtual
            );

            String nomeCidade = PrevisaoFragment.nomeCidadeAtual;


            mapView.getOverlays().clear();
            Marker startMarker = new Marker(mapView);
            startMarker.setPosition(localizacaoDinamica);
            startMarker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM);
            startMarker.setTitle(nomeCidade);
            mapView.getOverlays().add(startMarker);


            IMapController mapController = mapView.getController();
            mapController.setZoom(14.0);
            mapController.setCenter(localizacaoDinamica);

            mapView.invalidate();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (mapView != null) {
            mapView.onPause();
        }
    }
}