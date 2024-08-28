import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public class PesquisadorTela extends JPanel {
    private Sistema sistema;
    private DefaultListModel<Pesquisador> pesquisadorListModel;
    private JList<Pesquisador> pesquisadorList;
    private InicioTela inicioTela;

    public PesquisadorTela(Sistema sistema, InicioTela inicioTela) {
        this.sistema = sistema;
        this.inicioTela = inicioTela;
        setLayout(new BorderLayout());

        JTextField searchField = new JTextField();
        JButton searchButton = new JButton("Buscar");

        JPanel searchPanel = new JPanel(new BorderLayout());
        searchPanel.add(new JLabel("Buscar Pesquisador:"), BorderLayout.WEST);
        searchPanel.add(searchField, BorderLayout.CENTER);
        searchPanel.add(searchButton, BorderLayout.EAST);

        add(searchPanel, BorderLayout.NORTH);

        pesquisadorListModel = new DefaultListModel<>();
        pesquisadorList = new JList<>(pesquisadorListModel);
        pesquisadorList.setCellRenderer(new PesquisadorCellRenderer());
        add(new JScrollPane(pesquisadorList), BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new GridLayout(1, 3));
        JButton addButton = new JButton("Adicionar Pesquisador");
        JButton editButton = new JButton("Editar Pesquisador");
        JButton deleteButton = new JButton("Excluir Pesquisador");
        buttonPanel.add(addButton);
        buttonPanel.add(editButton);
        buttonPanel.add(deleteButton);
        add(buttonPanel, BorderLayout.SOUTH);

        addButton.addActionListener(e -> adicionarPesquisador(pesquisadorListModel));
        editButton.addActionListener(e -> editarPesquisador(pesquisadorList.getSelectedValue()));
        deleteButton.addActionListener(e -> excluirPesquisador(pesquisadorList.getSelectedValue(), pesquisadorListModel));

        pesquisadorList.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                Pesquisador pesquisador = pesquisadorList.getSelectedValue();
                if (pesquisador != null) {
                    mostrarDetalhesPesquisador(pesquisador);
                }
            }
        });

        searchButton.addActionListener(e -> {
            String termo = searchField.getText().toLowerCase().trim();
            List<Pesquisador> resultados;
            if (termo.isEmpty()) {
                resultados = sistema.getPesquisadores();
            } else {
                resultados = sistema.pesquisar(termo).stream()
                        .filter(result -> result instanceof Pesquisador)
                        .map(result -> (Pesquisador) result)
                        .collect(Collectors.toList());
            }

            pesquisadorListModel.clear();
            for (Pesquisador pesquisador : resultados) {
                pesquisadorListModel.addElement(pesquisador);
            }
        });

        pesquisadorListModel.addAll(sistema.getPesquisadores());
    }

    private void mostrarDetalhesPesquisador(Pesquisador pesquisador) {
        JFrame frame = new JFrame("Detalhes do Pesquisador: " + pesquisador.getNome());
        frame.setSize(600, 400);

        JTextArea detailsArea = new JTextArea();
        detailsArea.setEditable(false);
        detailsArea.setFont(new Font("Arial", Font.PLAIN, 16));
        detailsArea.setLineWrap(true);
        detailsArea.setWrapStyleWord(true);

        StringBuilder details = new StringBuilder();
        details.append("Nome: ").append(pesquisador.getNome()).append("\n");
        details.append("Área de Especialização: ").append(pesquisador.getAreaEspecializacao()).append("\n");

        if (pesquisador instanceof PesquisadorSenior) {
            PesquisadorSenior senior = (PesquisadorSenior) pesquisador;
            details.append("Anos de Experiência: ").append(senior.getTempoExperiencia()).append("\n");
            details.append("Diplomas: ").append(String.join(", ", senior.getDiplomas())).append("\n");
        } else if (pesquisador instanceof PesquisadorJunior) {
            PesquisadorJunior junior = (PesquisadorJunior) pesquisador;
            details.append("Curso: ").append(junior.getCurso()).append("\n");
            details.append("Semestre: ").append(junior.getSemestre()).append("\n");
            details.append("Orientador: ").append(junior.getOrientador().getNome()).append("\n");
        }

        detailsArea.setText(details.toString());
        frame.add(new JScrollPane(detailsArea), BorderLayout.CENTER);
        frame.setVisible(true);
    }

    private void adicionarPesquisador(DefaultListModel<Pesquisador> pesquisadorListModel) {
        JTextField nomeField = new JTextField();
        JTextField matriculaField = new JTextField();
        JTextField areaEspecializacaoField = new JTextField();
        JComboBox<String> tipoPesquisadorComboBox = new JComboBox<>(new String[]{"Pesquisador Senior", "Pesquisador Junior"});
        JTextField campoExtra1Field = new JTextField();
        JTextField campoExtra2Field = new JTextField();
        JTextField campoExtra3Field = new JTextField();

        JPanel panel = new JPanel(new GridLayout(0, 1));
        panel.add(new JLabel("Nome:"));
        panel.add(nomeField);
        panel.add(new JLabel("Matrícula:"));
        panel.add(matriculaField);
        panel.add(new JLabel("Área de Especialização:"));
        panel.add(areaEspecializacaoField);
        panel.add(new JLabel("Tipo de Pesquisador:"));
        panel.add(tipoPesquisadorComboBox);
        panel.add(new JLabel("Campo Extra 1:"));
        panel.add(campoExtra1Field);
        panel.add(new JLabel("Campo Extra 2:"));
        panel.add(campoExtra2Field);
        panel.add(new JLabel("Campo Extra 3:"));
        panel.add(campoExtra3Field);

        tipoPesquisadorComboBox.addActionListener(e -> {
            if (tipoPesquisadorComboBox.getSelectedItem().equals("Pesquisador Senior")) {
                campoExtra1Field.setText("Anos de Experiência");
                campoExtra2Field.setText("Diplomas");
                campoExtra3Field.setVisible(false);
            } else {
                campoExtra1Field.setText("Curso");
                campoExtra2Field.setText("Semestre");
                campoExtra3Field.setVisible(true);
                campoExtra3Field.setText("Orientador");
            }
        });

        int result = JOptionPane.showConfirmDialog(null, panel, "Adicionar Pesquisador", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if (result == JOptionPane.OK_OPTION) {
            try {
                Pesquisador novoPesquisador;
                if (tipoPesquisadorComboBox.getSelectedItem().equals("Pesquisador Senior")) {
                    List<String> diplomas = List.of(campoExtra2Field.getText().split(","));
                    novoPesquisador = new PesquisadorSenior(nomeField.getText(), Integer.parseInt(matriculaField.getText()), areaEspecializacaoField.getText(), Integer.parseInt(campoExtra1Field.getText()), diplomas);
                } else {
                    Pesquisador orientador = sistema.getPesquisadores().stream()
                            .filter(p -> p.getNome().equals(campoExtra3Field.getText()))
                            .findFirst()
                            .orElseThrow(() -> new IOException("Orientador não encontrado"));

                    if (!(orientador instanceof PesquisadorSenior)) {
                        throw new IOException("O orientador deve ser um Pesquisador Senior.");
                    }

                    novoPesquisador = new PesquisadorJunior(nomeField.getText(), Integer.parseInt(matriculaField.getText()), areaEspecializacaoField.getText(), campoExtra1Field.getText(), Integer.parseInt(campoExtra2Field.getText()), (PesquisadorSenior) orientador);
                }

                sistema.adicionarPesquisador(novoPesquisador);
                pesquisadorListModel.addElement(novoPesquisador);
            } catch (IOException | PesquisadorSenior.TamanhoMatriculaException e) {
                JOptionPane.showMessageDialog(null, "Erro ao adicionar pesquisador: " + e.getClass(), "Erro", JOptionPane.ERROR_MESSAGE);
            } catch (PesquisadorJunior.TamanhoMatriculaException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private void editarPesquisador(Pesquisador pesquisador) {
        if (pesquisador == null) {
            JOptionPane.showMessageDialog(null, "Selecione um pesquisador para editar.", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }

        JTextField nomeField = new JTextField(pesquisador.getNome());
        JTextField areaEspecializacaoField = new JTextField(pesquisador.getAreaEspecializacao());

        JPanel panel = new JPanel(new GridLayout(0, 1));
        panel.add(new JLabel("Nome:"));
        panel.add(nomeField);
        panel.add(new JLabel("Área de Especialização:"));
        panel.add(areaEspecializacaoField);

        int result = JOptionPane.showConfirmDialog(null, panel, "Editar Pesquisador", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if (result == JOptionPane.OK_OPTION) {
            pesquisador.setNome(nomeField.getText());
            pesquisador.setAreaEspecializacao(areaEspecializacaoField.getText());
        }
    }

    private void excluirPesquisador(Pesquisador pesquisador, DefaultListModel<Pesquisador> pesquisadorListModel) {
        if (pesquisador == null) {
            JOptionPane.showMessageDialog(null, "Selecione um pesquisador para excluir.", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int result = JOptionPane.showConfirmDialog(null, "Tem certeza que deseja excluir o pesquisador?", "Confirmar Exclusão", JOptionPane.YES_NO_OPTION);
        if (result == JOptionPane.YES_OPTION) {
            sistema.removerPesquisador(pesquisador);
            pesquisadorListModel.removeElement(pesquisador);
        }
    }

    private class PesquisadorCellRenderer extends JTextArea implements ListCellRenderer<Pesquisador> {
        public PesquisadorCellRenderer() {
            setLineWrap(true);
            setWrapStyleWord(true);
        }

        @Override
        public Component getListCellRendererComponent(JList<? extends Pesquisador> list, Pesquisador value, int index, boolean isSelected, boolean cellHasFocus) {
            setText(String.format("Nome: %s\nÁrea de Especialização: %s",
                    value.getNome(), value.getAreaEspecializacao()));
            setFont(new Font("Arial", Font.PLAIN, 16));
            setBackground(isSelected ? list.getSelectionBackground() : list.getBackground());
            setForeground(isSelected ? list.getSelectionForeground() : list.getForeground());
            return this;
        }
    }
}
