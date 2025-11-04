package com.example.apoprevisao.model;

import com.google.gson.annotations.SerializedName;
import java.util.List;

public class Resultados {

    @SerializedName("city_name")
    private String nomeCidade;

    @SerializedName("forecast")
    private List<ItemPrevisao> previsao;

    // --- ADICIONE ESTAS LINHAS ---
    @SerializedName("latitude")
    private double latitude;

    @SerializedName("longitude")
    private double longitude;
    // ----------------------------

    // Getters
    public String getNomeCidade() { return nomeCidade; }
    public List<ItemPrevisao> getPrevisao() { return previsao; }

    // --- ADICIONE ESTES GETTERS ---
    public double getLatitude() { return latitude; }
    public double getLongitude() { return longitude; }
    // ------------------------------
}