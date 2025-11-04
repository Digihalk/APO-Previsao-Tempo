package com.example.apoprevisao.model;

import com.google.gson.annotations.SerializedName;

public class ItemPrevisao {

    @SerializedName("date")
    private String data;

    @SerializedName("weekday")
    private String diaSemana;

    @SerializedName("max")
    private int max;

    @SerializedName("min")
    private int min;

    @SerializedName("description")
    private String descricao;

    public String getData() { return data; }
    public String getDiaSemana() { return diaSemana; }
    public int getMax() { return max; }
    public int getMin() { return min; }
    public String getDescricao() { return descricao; }
}