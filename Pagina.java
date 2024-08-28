import javax.swing.*;
import java.io.IOException;

public class Pagina {
    private Sistema sistema;

    public Pagina() throws IOException {
        this.sistema = new Sistema();
        new Dados(sistema);
        createAndShowGUI();
    }

    private void createAndShowGUI() throws IOException {
        JFrame frame = new JFrame("Gerenciador de Projetos de Pesquisa Científica");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);

        JTabbedPane tabbedPane = new JTabbedPane();
        InicioTela inicioTela = new InicioTela(sistema, tabbedPane);

        tabbedPane.addTab("Início", inicioTela);
        tabbedPane.addTab("Projetos", new ProjetoTela(sistema, inicioTela));
        tabbedPane.addTab("Pesquisadores", new PesquisadorTela(sistema, inicioTela));

        frame.add(tabbedPane);
        frame.setVisible(true);
    }
}
