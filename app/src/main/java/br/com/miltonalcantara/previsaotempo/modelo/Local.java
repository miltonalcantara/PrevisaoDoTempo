package br.com.miltonalcantara.previsaotempo.modelo;

import java.io.Serializable;

/**
 * Created by Milton Alcântara on 02/06/2016.
 */
public class Local implements Serializable {
    private int id;
    private String nome;
    private String descricao;
    private String temperaturaAtual;
    private String umidadeDoAr;
    private String pressaoDoAr;
    private String iconeDoTempo;
    private String dataEhora;
    private String latitude;
    private String longitude;

    public Local() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getTemperaturaAtual() {
        return temperaturaAtual;
    }

    public void setTemperaturaAtual(String temperaturaAtual) {
        this.temperaturaAtual = temperaturaAtual;
    }

    public String getUmidadeDoAr() {
        return umidadeDoAr;
    }

    public void setUmidadeDoAr(String umidadeDoAr) {
        this.umidadeDoAr = umidadeDoAr;
    }

    public String getPressaoDoAr() {
        return pressaoDoAr;
    }

    public void setPressaoDoAr(String pressaoDoAr) {
        this.pressaoDoAr = pressaoDoAr;
    }

    public String getIconeDoTempo() {
        return iconeDoTempo;
    }

    public void setIconeDoTempo(String iconeDoTempo) {
        this.iconeDoTempo = iconeDoTempo;
    }

    public String getDataEhora() {
        return dataEhora;
    }

    public void setDataEhora(String dataEhora) {
        this.dataEhora = dataEhora;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }
}
