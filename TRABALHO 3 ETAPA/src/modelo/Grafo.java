package modelo;

import java.util.ArrayList;
import java.util.List;

public class Grafo {

    private final List<Vertice> vertices;
    private boolean[][] matrizAdjacencia;
    private static final int CAPACIDADE_INICIAL = 10;

    public Grafo() {
        this.vertices = new ArrayList<>();
        this.matrizAdjacencia = new boolean[CAPACIDADE_INICIAL][CAPACIDADE_INICIAL];
    }

    public void adicionarVertice(String rotulo) {

        if (existeVertice(rotulo)) {
            throw new IllegalArgumentException(
                    "Já existe um vértice com o rótulo '" + rotulo + "'.");
        }

        Vertice novo = new Vertice(rotulo);

        vertices.add(novo);

        if (vertices.size() > matrizAdjacencia.length) {
            redimensionarMatriz();
        }
    }

    public boolean existeVertice(String rotulo) {
        return indiceDoVertice(rotulo) != -1;
    }

    private int indiceDoVertice(String rotulo) {

        for (int i = 0; i < vertices.size(); i++) {

            if (vertices.get(i).getRotulo().equalsIgnoreCase(rotulo)) {
                return i;
            }

        }

        return -1;
    }

    private void redimensionarMatriz() {
        int novaCapacidade = matrizAdjacencia.length * 2;
        boolean[][] novaMatriz = new boolean[novaCapacidade][novaCapacidade];

        for (int i = 0; i < matrizAdjacencia.length; i++) {
            System.arraycopy(matrizAdjacencia[i], 0, novaMatriz[i], 0, matrizAdjacencia[i].length);

        }
        matrizAdjacencia = novaMatriz;
    }

    public void adicionarAresta(String rotuloOrigem, String rotuloDestino) {
        int indiceOrigem = indiceDoVertice(rotuloOrigem);
        int indiceDestino = indiceDoVertice(rotuloDestino);

        if (indiceOrigem == -1 || indiceDestino == -1) {
            throw new IllegalArgumentException("Vértice de origem ou destino não encontrado.");
        }

        matrizAdjacencia[indiceOrigem][indiceDestino] = true;
        matrizAdjacencia[indiceDestino][indiceOrigem] = true;
    }

    public boolean existeAresta(String rotuloOrigem, String rotuloDestino) {
        int indiceOrigem = indiceDoVertice(rotuloOrigem);
        int indiceDestino = indiceDoVertice(rotuloDestino);

        if (indiceOrigem == -1 || indiceDestino == -1) {
            return false;
        }

        return matrizAdjacencia[indiceOrigem][indiceDestino];
    }

    public List<Vertice> getVertices() {
        return new ArrayList<>(vertices);
    }

    public Vertice getVertice(String rotulo) {
        int indice = indiceDoVertice(rotulo);

        if (indice == -1) {
            throw new IllegalArgumentException("Vértice não encontrado: " + rotulo);
        }

        return vertices.get(indice);
    }

    public List<Vertice> getVizinhos(Vertice vertice) {
        int indice = indiceDoVertice(vertice.getRotulo());

        if (indice == -1) {
            throw new IllegalArgumentException("Vértice não encontrado: " + vertice.getRotulo());
        }

        List<Vertice> vizinhos = new ArrayList<>();

        for (int i = 0; i < vertices.size(); i++) {
            if (matrizAdjacencia[indice][i]) {
                vizinhos.add(vertices.get(i));
            }
        }

        return vizinhos;
    }




}

