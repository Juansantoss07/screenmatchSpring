package br.com.alura.screenmatch.menu;

import br.com.alura.screenmatch.model.DadosSerie;
import br.com.alura.screenmatch.service.ConsumoAPI;

import java.util.Scanner;

public class Menu {

    private final String ENDERECO = "https://www.omdbapi.com/?t=";
    private final String API_KEY = "&apikey=7b19864c";
    ConsumoAPI consumo = new ConsumoAPI();

    public void exibeMenu(){

        Scanner scanner = new Scanner(System.in);
        System.out.println("Digite o nome da série para buscar informações sobre ela:");
        var nomeSerie = scanner.nextLine();
        var json = consumo.obterDados(ENDERECO + nomeSerie.replace(" ", "+") + API_KEY);
    }
}
