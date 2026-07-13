package Busca;

import modelo.Grafo;
import modelo.Vertice;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


public class BuscaProfundidade {

    private final Grafo grafo;

    public BuscaProfundidade(Grafo grafo) {
        this.grafo = grafo;
    }

    public List<Vertice> buscarCaminho(String rotuloOrigem, String rotuloDestino) {
        Vertice origem = grafo.getVertice(rotuloOrigem);
        Vertice destino = grafo.getVertice(rotuloDestino);

        List<Vertice> caminho = new ArrayList<>();
        Set<Vertice> visitados = new HashSet<>();

        boolean encontrou = buscar(origem, destino, visitados, caminho);

        if (encontrou) {
            return caminho;
        }

        return new ArrayList<>();
    }

    private boolean buscar(
            Vertice atual,
            Vertice destino,
            Set<Vertice> visitados,
            List<Vertice> caminho
    ) {
        visitados.add(atual);
        caminho.add(atual);

        if (atual.equals(destino)) {
            return true;
        }

        for (Vertice vizinho : grafo.getVizinhos(atual)) {
            if (!visitados.contains(vizinho)) {
                boolean encontrou = buscar(vizinho, destino, visitados, caminho);

                if (encontrou) {
                    return true;
                }
            }
        }

        caminho.remove(caminho.size() - 1);
        return false;
    }
}