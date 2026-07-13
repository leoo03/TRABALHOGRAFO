package visao;

import Busca.BuscaLargura;
import Busca.BuscaProfundidade;
import modelo.Grafo;
import modelo.Vertice;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JanelaPrincipal extends JFrame {

    private final Grafo grafo;
    private final BuscaLargura buscaLargura;
    private final BuscaProfundidade buscaProfundidade;

    private final JTextField campoVertice;
    private final JTextField campoOrigemAresta;
    private final JTextField campoDestinoAresta;
    private final JTextField campoOrigemBusca;
    private final JTextField campoDestinoBusca;
    private final JTextArea areaResultado;
    private final PainelGrafo painelGrafo;

    public JanelaPrincipal() {
        this.grafo = new Grafo();
        this.buscaLargura = new BuscaLargura(grafo);
        this.buscaProfundidade = new BuscaProfundidade(grafo);

        setTitle("Sistema de Grafos - BFS e DFS");
        setSize(1000, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        campoVertice = new JTextField(5);
        campoOrigemAresta = new JTextField(5);
        campoDestinoAresta = new JTextField(5);
        campoOrigemBusca = new JTextField(5);
        campoDestinoBusca = new JTextField(5);

        areaResultado = new JTextArea(6, 30);
        areaResultado.setEditable(false);
        areaResultado.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        areaResultado.setLineWrap(true);
        areaResultado.setWrapStyleWord(true);

        painelGrafo = new PainelGrafo();

        add(criarPainelControles(), BorderLayout.NORTH);
        add(painelGrafo, BorderLayout.CENTER);
        add(new JScrollPane(areaResultado), BorderLayout.SOUTH);
    }

    private JPanel criarPainelControles() {
        JPanel painel = new JPanel(new BorderLayout(10, 10));
        painel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        painel.setBackground(new Color(245, 247, 250));

        JLabel titulo = new JLabel("Sistema de Grafos - BFS e DFS", SwingConstants.CENTER);
        titulo.setFont(new Font("Segoe UI", Font.BOLD, 24));
        titulo.setForeground(new Color(40, 40, 40));

        JButton botaoAdicionarVertice = criarBotao("Adicionar vértice");
        JButton botaoAdicionarAresta = criarBotao("Adicionar aresta");
        JButton botaoBuscar = criarBotao("Executar BFS e DFS");

        botaoAdicionarVertice.addActionListener(e -> adicionarVertice());
        botaoAdicionarAresta.addActionListener(e -> adicionarAresta());
        botaoBuscar.addActionListener(e -> executarBuscas());

        JPanel cards = new JPanel(new GridLayout(1, 3, 15, 0));
        cards.setOpaque(false);

        cards.add(criarCard(
                "Vértice",
                new JLabel("Rótulo:"),
                campoVertice,
                botaoAdicionarVertice
        ));

        cards.add(criarCard(
                "Aresta",
                new JLabel("Origem:"),
                campoOrigemAresta,
                new JLabel("Destino:"),
                campoDestinoAresta,
                botaoAdicionarAresta
        ));

        cards.add(criarCard(
                "Busca",
                new JLabel("Origem:"),
                campoOrigemBusca,
                new JLabel("Destino:"),
                campoDestinoBusca,
                botaoBuscar
        ));

        painel.add(titulo, BorderLayout.NORTH);
        painel.add(cards, BorderLayout.CENTER);

        return painel;
    }

    private JPanel criarCard(String titulo, JComponent... componentes) {
        JPanel card = new JPanel();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(220, 225, 230), 1, true),
                BorderFactory.createEmptyBorder(12, 12, 12, 12)
        ));

        JLabel labelTitulo = new JLabel(titulo);
        labelTitulo.setFont(new Font("Segoe UI", Font.BOLD, 16));
        labelTitulo.setForeground(new Color(50, 50, 50));
        labelTitulo.setAlignmentX(Component.LEFT_ALIGNMENT);

        card.add(labelTitulo);
        card.add(Box.createVerticalStrut(8));

        for (JComponent componente : componentes) {
            componente.setAlignmentX(Component.LEFT_ALIGNMENT);

            if (componente instanceof JTextField) {
                componente.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
                componente.setFont(new Font("Segoe UI", Font.PLAIN, 14));
            }

            if (componente instanceof JLabel) {
                componente.setFont(new Font("Segoe UI", Font.PLAIN, 13));
            }

            card.add(componente);
            card.add(Box.createVerticalStrut(6));
        }

        return card;
    }

    private JButton criarBotao(String texto) {
        JButton botao = new JButton(texto);

        botao.setFocusPainted(false);
        botao.setCursor(new Cursor(Cursor.HAND_CURSOR));
        botao.setFont(new Font("Segoe UI", Font.BOLD, 13));
        botao.setBackground(new Color(25, 118, 210));
        botao.setForeground(Color.WHITE);
        botao.setBorder(BorderFactory.createEmptyBorder(8, 12, 8, 12));

        botao.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                botao.setBackground(new Color(21, 101, 192));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                botao.setBackground(new Color(25, 118, 210));
            }
        });

        return botao;
    }

    private void adicionarVertice() {
        try {
            String rotulo = campoVertice.getText().trim();

            if (rotulo.isEmpty()) {
                throw new IllegalArgumentException("Informe o rótulo do vértice.");
            }

            grafo.adicionarVertice(rotulo);
            campoVertice.setText("");
            painelGrafo.repaint();

        } catch (IllegalArgumentException erro) {
            JOptionPane.showMessageDialog(this, erro.getMessage());
        }
    }

    private void adicionarAresta() {
        try {
            String origem = campoOrigemAresta.getText().trim();
            String destino = campoDestinoAresta.getText().trim();

            if (origem.isEmpty() || destino.isEmpty()) {
                throw new IllegalArgumentException("Informe a origem e o destino da aresta.");
            }

            grafo.adicionarAresta(origem, destino);

            campoOrigemAresta.setText("");
            campoDestinoAresta.setText("");
            painelGrafo.repaint();

        } catch (IllegalArgumentException erro) {
            JOptionPane.showMessageDialog(this, erro.getMessage());
        }
    }

    private void executarBuscas() {
        try {
            String origem = campoOrigemBusca.getText().trim();
            String destino = campoDestinoBusca.getText().trim();

            if (origem.isEmpty() || destino.isEmpty()) {
                throw new IllegalArgumentException("Informe a origem e o destino da busca.");
            }

            List<Vertice> caminhoBfs = buscaLargura.buscarCaminho(origem, destino);
            List<Vertice> caminhoDfs = buscaProfundidade.buscarCaminho(origem, destino);

            areaResultado.setText("");
            areaResultado.append("Busca em Largura (BFS): " + formatarCaminho(caminhoBfs) + "\n");
            areaResultado.append("Busca em Profundidade (DFS): " + formatarCaminho(caminhoDfs));

        } catch (IllegalArgumentException erro) {
            JOptionPane.showMessageDialog(this, erro.getMessage());
        }
    }

    private String formatarCaminho(List<Vertice> caminho) {
        if (caminho.isEmpty()) {
            return "Não existe caminho entre os vértices informados.";
        }

        StringBuilder resultado = new StringBuilder();

        for (int i = 0; i < caminho.size(); i++) {
            resultado.append(caminho.get(i).getRotulo());

            if (i < caminho.size() - 1) {
                resultado.append(" -> ");
            }
        }

        return resultado.toString();
    }

    private class PainelGrafo extends JPanel {

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);

            Graphics2D desenho = (Graphics2D) g;
            desenho.setRenderingHint(
                    RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON
            );

            List<Vertice> listaVertices = grafo.getVertices();

            if (listaVertices.isEmpty()) {
                desenho.setFont(new Font("Segoe UI", Font.PLAIN, 16));
                desenho.drawString("Adicione vértices para visualizar o grafo.", 30, 30);
                return;
            }

            Map<Vertice, Point> posicoes = calcularPosicoes(listaVertices);

            desenharArestas(desenho, listaVertices, posicoes);
            desenharVertices(desenho, listaVertices, posicoes);
        }

        private Map<Vertice, Point> calcularPosicoes(List<Vertice> listaVertices) {
            Map<Vertice, Point> posicoes = new HashMap<>();

            int centroX = getWidth() / 2;
            int centroY = getHeight() / 2;
            int raio = Math.min(getWidth(), getHeight()) / 2 - 70;

            for (int i = 0; i < listaVertices.size(); i++) {
                double angulo = 2 * Math.PI * i / listaVertices.size();

                int x = centroX + (int) (Math.cos(angulo) * raio);
                int y = centroY + (int) (Math.sin(angulo) * raio);

                posicoes.put(listaVertices.get(i), new Point(x, y));
            }

            return posicoes;
        }

        private void desenharArestas(
                Graphics2D desenho,
                List<Vertice> listaVertices,
                Map<Vertice, Point> posicoes
        ) {
            desenho.setColor(Color.DARK_GRAY);
            desenho.setStroke(new BasicStroke(2));

            for (int i = 0; i < listaVertices.size(); i++) {
                for (int j = i + 1; j < listaVertices.size(); j++) {
                    Vertice origem = listaVertices.get(i);
                    Vertice destino = listaVertices.get(j);

                    if (grafo.existeAresta(origem.getRotulo(), destino.getRotulo())) {
                        Point pontoOrigem = posicoes.get(origem);
                        Point pontoDestino = posicoes.get(destino);

                        desenho.drawLine(
                                pontoOrigem.x,
                                pontoOrigem.y,
                                pontoDestino.x,
                                pontoDestino.y
                        );
                    }
                }
            }
        }

        private void desenharVertices(
                Graphics2D desenho,
                List<Vertice> listaVertices,
                Map<Vertice, Point> posicoes
        ) {
            int tamanho = 50;

            for (Vertice vertice : listaVertices) {
                Point ponto = posicoes.get(vertice);

                int x = ponto.x - tamanho / 2;
                int y = ponto.y - tamanho / 2;

                desenho.setColor(new Color(70, 130, 180));
                desenho.fillOval(x, y, tamanho, tamanho);

                desenho.setColor(Color.BLACK);
                desenho.drawOval(x, y, tamanho, tamanho);

                desenho.setColor(Color.WHITE);
                desenho.setFont(new Font("Segoe UI", Font.BOLD, 15));

                FontMetrics medidas = desenho.getFontMetrics();

                String texto = vertice.getRotulo();

                int textoX = ponto.x - medidas.stringWidth(texto) / 2;
                int textoY = ponto.y + medidas.getAscent() / 2 - 4;

                desenho.drawString(texto, textoX, textoY);
            }
        }
    }
}