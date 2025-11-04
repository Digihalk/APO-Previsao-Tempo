package com.example.apoprevisao;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.apoprevisao.api.HGApiService;
import com.example.apoprevisao.api.RetrofitClient;
import com.example.apoprevisao.model.ItemPrevisao;
import com.example.apoprevisao.model.PrevisaoResponse;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.journeyapps.barcodescanner.ScanContract;
import com.journeyapps.barcodescanner.ScanOptions;

import org.osmdroid.util.GeoPoint;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PrevisaoFragment extends Fragment {


    private RecyclerView recyclerView;
    private PrevisaoAdapter adapter;
    private ProgressBar progressBar;
    private FloatingActionButton fabScan;


    private List<ItemPrevisao> listaDePrevisao = new ArrayList<>();


    public static String woeidAtual;
    public static double latitudeAtual;
    public static double longitudeAtual;
    public static String nomeCidadeAtual;


    private static final Map<String, GeoPoint> mapaDeCidades = new HashMap<>();


    static {

        mapaDeCidades.put("421574", new GeoPoint(-23.8750, -53.9069));


        mapaDeCidades.put("455822", new GeoPoint(-25.4284, -49.2733));


        mapaDeCidades.put("455827", new GeoPoint(-23.5505, -46.6333));

    }


    private final ActivityResultLauncher<ScanOptions> qrCodeLauncher = registerForActivityResult(new ScanContract(),
            result -> {
                if (result.getContents() == null) {
                    Toast.makeText(getContext(), "Escaneamento Cancelado", Toast.LENGTH_SHORT).show();
                } else {
                    String novoWoeid = result.getContents();


                    GeoPoint novasCoords = mapaDeCidades.get(novoWoeid);

                    if (novasCoords != null) {

                        latitudeAtual = novasCoords.getLatitude();
                        longitudeAtual = novasCoords.getLongitude();


                        Log.d("API_CHAMADA", "QR Code lido com sucesso: " + novoWoeid);
                        buscarPrevisao(novoWoeid);
                    } else {

                        Toast.makeText(getContext(), "QR Code inválido ou WOEID não cadastrado no App.", Toast.LENGTH_LONG).show();
                    }
                }
            });


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_previsao, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView = view.findViewById(R.id.recycler_view_previsao);
        progressBar = view.findViewById(R.id.progress_bar);
        fabScan = view.findViewById(R.id.fab_scan_qr);


        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new PrevisaoAdapter(listaDePrevisao);
        recyclerView.setAdapter(adapter);

        fabScan.setOnClickListener(v -> {
            iniciarScanner();
        });


        if (savedInstanceState == null) {
            String woeidInicial = "421574";
            GeoPoint coordsIniciais = mapaDeCidades.get(woeidInicial);

            woeidAtual = woeidInicial;
            latitudeAtual = coordsIniciais.getLatitude();
            longitudeAtual = coordsIniciais.getLongitude();
            nomeCidadeAtual = "Altônia, PR";
        }

        buscarPrevisao(woeidAtual);
    }

    private void iniciarScanner() {
        ScanOptions options = new ScanOptions();
        options.setPrompt("Aponte para um QR Code com o WOEID");
        options.setBeepEnabled(true);
        options.setOrientationLocked(false);
        qrCodeLauncher.launch(options);
    }


    private void buscarPrevisao(String woeidParaBuscar) {
        Log.d("API_CHAMADA", "Iniciando busca para o WOEID: " + woeidParaBuscar);
        progressBar.setVisibility(View.VISIBLE);
        recyclerView.setVisibility(View.GONE);

        HGApiService apiService = RetrofitClient.getApiService();
        Call<PrevisaoResponse> call = apiService.getPrevisaoPorWOEID(woeidParaBuscar);

        call.enqueue(new Callback<PrevisaoResponse>() {
            @Override
            public void onResponse(Call<PrevisaoResponse> call, Response<PrevisaoResponse> response) {
                progressBar.setVisibility(View.GONE);
                recyclerView.setVisibility(View.VISIBLE);

                if (response.isSuccessful() && response.body() != null) {
                    List<ItemPrevisao> previsoes = response.body().getResultados().getPrevisao();
                    listaDePrevisao.clear();
                    listaDePrevisao.addAll(previsoes);
                    adapter.notifyDataSetChanged();

                    nomeCidadeAtual = response.body().getResultados().getNomeCidade();
                    woeidAtual = woeidParaBuscar;


                    if (getActivity() != null) {
                        ((MainActivity) getActivity()).getSupportActionBar().setTitle(nomeCidadeAtual);
                    }
                    Log.d("API_CHAMADA", "Sucesso. API retornou: " + nomeCidadeAtual);

                } else {
                    Toast.makeText(getContext(), "Erro ao buscar dados. WOEID inválido?", Toast.LENGTH_SHORT).show();
                    Log.e("API_ERRO", "Resposta não foi sucesso: " + response.message());
                }
            }

            @Override
            public void onFailure(Call<PrevisaoResponse> call, Throwable t) {
            }
        });
    }
}