import javax.swing.*;
import java.awt.*;

public class InicioTela extends JPanel {
    private Sistema sistema;
    private JTextArea statsArea;
    private JTabbedPane tabbedPane;

    public InicioTela(Sistema sistema, JTabbedPane tabbedPane) {
        this.sistema = sistema;
        this.tabbedPane = tabbedPane;
        setLayout(new BorderLayout());

        JLabel welcomeLabel = new JLabel("Bem-vindo ao Gerenciador de Projetos de Pesquisa Científica!", JLabel.CENTER);
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 24));

        statsArea = new JTextArea();
        statsArea.setEditable(false);
        statsArea.setFont(new Font("Arial", Font.PLAIN, 16));
        statsArea.setLineWrap(true);
        statsArea.setWrapStyleWord(true);
        atualizarEstatisticas();

        JPanel messagePanel = new JPanel(new BorderLayout());
        messagePanel.add(welcomeLabel, BorderLayout.NORTH);
        messagePanel.add(statsArea, BorderLayout.CENTER);

        JButton projetosButton = new JButton("Projetos");
        projetosButton.setFont(new Font("Arial", Font.BOLD, 18));
        projetosButton.addActionListener(e -> tabbedPane.setSelectedIndex(1));

        JButton pesquisadoresButton = new JButton("Pesquisadores");
        pesquisadoresButton.setFont(new Font("Arial", Font.BOLD, 18));
        pesquisadoresButton.addActionListener(e -> tabbedPane.setSelectedIndex(2));

        JPanel buttonsPanel = new JPanel();
        buttonsPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridy = 1;
        buttonsPanel.add(projetosButton, gbc);
        gbc.gridy = 2;
        buttonsPanel.add(pesquisadoresButton, gbc);

        add(messagePanel, BorderLayout.NORTH);
        add(buttonsPanel, BorderLayout.SOUTH);
    }

    public void atualizarEstatisticas() {
        String stats = String.format(
                "Estatísticas do Sistema:\n\n" +
                        "Total de Pesquisadores:\n" +
                        "  - Juniors: %d\n" +
                        "  - Seniors: %d\n" +
                        "Projetos:\n" +
                        "  - Concluídos: %d\n" +
                        "  - Percentual de Desenvolvimento: %.2f%%\n" +
                        "  - Percentual de Pesquisa Científica: %.2f%%\n",
                sistema.getTotalPesquisadoresJunior(),
                sistema.getTotalPesquisadoresSenior(),
                sistema.getTotalProjetosConcluidos(),
                sistema.getPercentualProjetosDesenvolvimento(),
                sistema.getPercentualProjetosPesquisaCientifica()
        );
        statsArea.setText(stats);
    }
}
