package br.com.alura.screenmatch.menu;

import br.com.alura.screenmatch.model.DadosEpsodio;
import br.com.alura.screenmatch.model.DadosSerie;
import br.com.alura.screenmatch.model.DadosTemporada;
import br.com.alura.screenmatch.model.Epsodio;
import br.com.alura.screenmatch.service.ConsumoAPI;
import br.com.alura.screenmatch.service.ConverteDados;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

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

        List<DadosEpsodio> dadosEpsodios = temporadas.stream()
                .flatMap(t -> t.epsodios().stream())
                .collect(Collectors.toList());

        System.out.println("Top 5 epsódios:");
        dadosEpsodios.stream()
                .filter( e -> !e.avaliacao().equalsIgnoreCase("N/A"))
                .sorted(Comparator.comparing(DadosEpsodio::avaliacao).reversed())
                .limit(5)
                .forEach(System.out::println);

        List<Epsodio> epsodios = temporadas.stream()
                .flatMap(t -> t.epsodios().stream()
                        .map(d -> new Epsodio(t.numero(), d))
                ).collect(Collectors.toList());

        epsodios.forEach(System.out::println);

        System.out.println("A partir de que ano você deseja ver os epsódios?");
        var ano = scanner.nextInt();
        scanner.nextLine();
        LocalDate dataBusca = LocalDate.of(ano, 1, 1);
        DateTimeFormatter formatador = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        epsodios.stream()
                .filter(e -> e.getDataLancamento().isAfter(dataBusca) && e.getDataLancamento() != null)
                .forEach(e -> System.out.println(
                        "Temporada: " + e.getTemporada() +
                                " Epsódio: " + e.getNumeroEpsodio() +
                                " Data de lançamento: " + e.getDataLancamento().format(formatador)
                ));
    }

}
