package Busca;

import modelo.Grafo;
import modelo.Vertice;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;


public class BuscaLargura {

    private final Grafo grafo;

    public BuscaLargura(Grafo grafo) {
        this.grafo = grafo;
    }

    public List<Vertice> buscarCaminho(String rotuloOrigem, String rotuloDestino) {
        Vertice origem = grafo.getVertice(rotuloOrigem);
        Vertice destino = grafo.getVertice(rotuloDestino);

        Queue<Vertice> fila = new LinkedList<>();
        Set<Vertice> visitados = new HashSet<>();
        Map<Vertice, Vertice> anteriores = new HashMap<>();

        fila.add(origem);
        visitados.add(origem);

        while (!fila.isEmpty()) {
            Vertice atual = fila.poll();

            if (atual.equals(destino)) {
                return reconstruirCaminho(anteriores, origem, destino);
            }

            for (Vertice vizinho : grafo.getVizinhos(atual)) {
                if (!visitados.contains(vizinho)) {
                    visitados.add(vizinho);
                    anteriores.put(vizinho, atual);
                    fila.add(vizinho);
                }
            }
        }

        return new ArrayList<>();
    }

    private List<Vertice> reconstruirCaminho(
            Map<Vertice, Vertice> anteriores,
            Vertice origem,
            Vertice destino
    ) {
        LinkedList<Vertice> caminho = new LinkedList<>();
        Vertice atual = destino;

        while (atual != null) {
            caminho.addFirst(atual);

            if (atual.equals(origem)) {
                break;
            }

            atual = anteriores.get(atual);
        }

        if (!caminho.isEmpty() && caminho.getFirst().equals(origem)) {
            return caminho;
        }

        return new ArrayList<>();
    }
}