package br.com.alura.screenmatch.menu;

import br.com.alura.screenmatch.model.DadosEpsodio;
import br.com.alura.screenmatch.model.DadosSerie;
import br.com.alura.screenmatch.model.DadosTemporada;
import br.com.alura.screenmatch.service.ConsumoAPI;
import br.com.alura.screenmatch.service.ConverteDados;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Menu {

    private final String ENDERECO = "https://www.omdbapi.com/?t=";
    private final String API_KEY = "&apikey=7b19864c";
    private ConsumoAPI consumo = new ConsumoAPI();
    private ConverteDados conversor = new ConverteDados();
    private List<DadosTemporada> temporadas = new ArrayList<>();

    public void exibeMenu(){

        Scanner scanner = new Scanner(System.in);
        System.out.println("Digite o nome da série para buscar informações sobre ela:");
        var nomeSerie = scanner.nextLine();
        var json = consumo.obterDados(ENDERECO + nomeSerie.replace(" ", "+") + API_KEY);

        DadosSerie dados = conversor.obterDados(json, DadosSerie.class);
        System.out.println(dados.totalTemporadas());
        DadosEpsodio dadosEpsodio = conversor.obterDados(json, DadosEpsodio.class);
        System.out.println(dadosEpsodio);

        for(int i = 1; i <= dados.totalTemporadas(); i++){
            json = consumo.obterDados(ENDERECO + nomeSerie.replace(" ", "+") + "&season=" + i + API_KEY);
            DadosTemporada dadosTemporada = conversor.obterDados(json, DadosTemporada.class);
            temporadas.add(dadosTemporada);
        }
        temporadas.forEach(System.out::println);

        /* for(int i = 0; i <= dados.totalTemporadas(); i++){
            List<DadosEpsodio> epsodiosTemporada = temporadas.get(i).epsodios();
            for (int j = 0; j <= epsodiosTemporada.size(); j++){
                System.out.println(epsodiosTemporada.get(j).titulo());
            }
        } */

        temporadas.forEach(t -> t.epsodios().forEach(e -> System.out.println(e.titulo())));
    }
}
