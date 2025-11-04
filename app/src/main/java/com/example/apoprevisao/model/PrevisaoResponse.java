package com.example.apoprevisao.model;

import com.google.gson.annotations.SerializedName;

public class PrevisaoResponse {

    @SerializedName("results")
    private Resultados resultados;

    public Resultados getResultados() { return resultados; }
}