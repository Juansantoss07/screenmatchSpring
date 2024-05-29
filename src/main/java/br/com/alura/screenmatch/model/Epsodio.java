package br.com.alura.screenmatch.model;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

public class Epsodio {

    private Integer temporada;
    private String titulo;
    private Integer numeroEpsodio;
    private Double avaliacao;
    private LocalDate dataLancamento;

    public Epsodio(Integer numeroTemporada, DadosEpsodio dadosEpsodio){
        this.temporada = numeroTemporada;
        this.titulo = dadosEpsodio.titulo();
        this.numeroEpsodio = dadosEpsodio.numeroDoEps();
        try {
            this.avaliacao = Double.valueOf(dadosEpsodio.avaliacao());
        }catch (NumberFormatException e){
            this.avaliacao = 0.0;
        }

        try{
            this.dataLancamento = LocalDate.parse(dadosEpsodio.dataLancamento());
        }catch (DateTimeParseException e){
            this.dataLancamento = null;
        }

    }

    public Integer getTemporada() {
        return temporada;
    }

    public void setTemporada(Integer temporada) {
        this.temporada = temporada;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public Integer getNumeroEpsodio() {
        return numeroEpsodio;
    }

    public void setNumeroEpsodio(Integer numeroEpsodio) {
        this.numeroEpsodio = numeroEpsodio;
    }

    public Double getAvaliacao() {
        return avaliacao;
    }

    public void setAvaliacao(Double avaliacao) {
        this.avaliacao = avaliacao;
    }

    public LocalDate getDataLancamento() {
        return dataLancamento;
    }

    public void setDataLancamento(LocalDate dataLancamento) {
        this.dataLancamento = dataLancamento;
    }

    @Override
    public String toString() {
        return "temporada=" + temporada +
                ", titulo='" + titulo + '\'' +
                ", numeroEpsodio=" + numeroEpsodio +
                ", avaliacao=" + avaliacao +
                ", dataLancamento=" + dataLancamento;
    }
}
