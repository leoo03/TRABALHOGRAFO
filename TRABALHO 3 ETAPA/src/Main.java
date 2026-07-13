import visao.JanelaPrincipal;

import javax.swing.SwingUtilities;

public class Main {

    public void main() {
        SwingUtilities.invokeLater(() -> {
            JanelaPrincipal janela = new JanelaPrincipal();
            janela.setVisible(true);
        });
    }
}