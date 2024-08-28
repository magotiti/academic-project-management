import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public class ProjetoTela extends JPanel {
    private Sistema sistema;
    private DefaultListModel<Projeto> projetoListModel;
    private JList<Projeto> projetoList;
    private InicioTela inicioTela;

    public ProjetoTela(Sistema sistema, InicioTela inicioTela) {
        this.sistema = sistema;
        this.inicioTela = inicioTela;
        setLayout(new BorderLayout());

        JTextField searchField = new JTextField();
        JButton searchButton = new JButton("Buscar");

        JPanel searchPanel = new JPanel(new BorderLayout());
        searchPanel.add(new JLabel("Buscar Projeto:"), BorderLayout.WEST);
        searchPanel.add(searchField, BorderLayout.CENTER);
        searchPanel.add(searchButton, BorderLayout.EAST);

        add(searchPanel, BorderLayout.NORTH);

        projetoListModel = new DefaultListModel<>();
        projetoList = new JList<>(projetoListModel);
        projetoList.setCellRenderer(new ProjetoCellRenderer());
        add(new JScrollPane(projetoList), BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new GridLayout(1, 4));
        JButton addButton = new JButton("Adicionar Projeto");
        JButton editButton = new JButton("Editar Projeto");
        JButton deleteButton = new JButton("Excluir Projeto");
        JButton addPesquisadorButton = new JButton("Adicionar Pesquisador");
        buttonPanel.add(addButton);
        buttonPanel.add(editButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(addPesquisadorButton);
        add(buttonPanel, BorderLayout.SOUTH);

        addButton.addActionListener(e -> {
            adicionarProjeto(projetoListModel);
            inicioTela.atualizarEstatisticas();
        });
        editButton.addActionListener(e -> {
            Projeto projeto = projetoList.getSelectedValue();
            if (projeto != null) {
                editarProjeto(projeto);
                inicioTela.atualizarEstatisticas();
            } else {
                JOptionPane.showMessageDialog(null, "Selecione um projeto para editar.", "Erro", JOptionPane.ERROR_MESSAGE);
            }
        });
        deleteButton.addActionListener(e -> {
            Projeto projeto = projetoList.getSelectedValue();
            if (projeto != null) {
                excluirProjeto(projeto, projetoListModel);
                inicioTela.atualizarEstatisticas();
            } else {
                JOptionPane.showMessageDialog(null, "Selecione um projeto para excluir.", "Erro", JOptionPane.ERROR_MESSAGE);
            }
        });
        addPesquisadorButton.addActionListener(e -> {
            Projeto projeto = projetoList.getSelectedValue();
            if (projeto != null) {
                adicionarPesquisadorAoProjeto(projeto);
            } else {
                JOptionPane.showMessageDialog(null, "Selecione um projeto para adicionar um pesquisador.", "Erro", JOptionPane.ERROR_MESSAGE);
            }
        });

        projetoList.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                Projeto projeto = projetoList.getSelectedValue();
                if (projeto != null) {
                    mostrarDetalhesProjeto(projeto);
                }
            }
        });

        searchButton.addActionListener(e -> {
            String termo = searchField.getText().toLowerCase().trim();
            List<Projeto> resultados;
            if (termo.isEmpty()) {
                resultados = sistema.getProjetos();
            } else {
                resultados = sistema.pesquisar(termo).stream()
                        .filter(result -> result instanceof Projeto)
                        .map(result -> (Projeto) result)
                        .collect(Collectors.toList());
            }

            projetoListModel.clear();
            for (Projeto projeto : resultados) {
                projetoListModel.addElement(projeto);
            }
        });

        projetoListModel.addAll(sistema.getProjetos());
    }

    private void mostrarDetalhesProjeto(Projeto projeto) {
        JFrame frame = new JFrame("Detalhes do Projeto: " + projeto.getTitulo());
        frame.setSize(600, 400);

        JTextArea detailsArea = new JTextArea();
        detailsArea.setEditable(false);
        detailsArea.setFont(new Font("Arial", Font.PLAIN, 16));
        detailsArea.setLineWrap(true);
        detailsArea.setWrapStyleWord(true);

        StringBuilder details = new StringBuilder();
        details.append("Título: ").append(projeto.getTitulo()).append("\n");
        details.append("Descrição: ").append(projeto.getDescricao()).append("\n");
        details.append("Mentor: ").append(projeto.getMentor().getNome()).append("\n");
        details.append("Data Início: ").append(projeto.formatDate(projeto.getDataInicio())).append("\n");
        details.append("Data Prevista Término: ").append(projeto.formatDate(projeto.getDataPrevistaTermino())).append("\n");
        details.append("Status: ").append(projeto.getStatus()).append("\n");

        if (projeto instanceof ProjetoDesenvolvimento) {
            ProjetoDesenvolvimento desenvolvimento = (ProjetoDesenvolvimento) projeto;
            details.append("Tipo: Projeto de Desenvolvimento\n");
            details.append("Tecnologia Utilizada: ").append(String.join(", ", desenvolvimento.getTecnologiasUtilizadas())).append("\n");
        } else if (projeto instanceof ProjetoPesquisaCientifica) {
            ProjetoPesquisaCientifica pesquisaCientifica = (ProjetoPesquisaCientifica) projeto;
            details.append("Tipo: Projeto de Pesquisa Científica\n");
            details.append("Área de Pesquisa: ").append(pesquisaCientifica.getAreaPesquisa()).append("\n");
        }

        details.append("\nPesquisadores:\n");
        for (Pesquisador pesquisador : projeto.getPesquisadores()) {
            details.append(pesquisador.getNome()).append("\n");
        }

        details.append("\nExperimentos:\n");
        for (Experimento experimento : projeto.getExperimentos()) {
            details.append(experimento.getNome()).append("\n");
        }

        detailsArea.setText(details.toString());

        DefaultListModel<Experimento> experimentoListModel = new DefaultListModel<>();
        JList<Experimento> experimentoList = new JList<>(experimentoListModel);
        experimentoList.setCellRenderer(new ExperimentoCellRenderer());
        for (Experimento experimento : projeto.getExperimentos()) {
            experimentoListModel.addElement(experimento);
        }

        JPanel buttonPanel = new JPanel(new GridLayout(1, 3));
        JButton addExperimentoButton = new JButton("Adicionar Experimento");
        JButton editExperimentoButton = new JButton("Editar Experimento");
        JButton deleteExperimentoButton = new JButton("Excluir Experimento");
        buttonPanel.add(addExperimentoButton);
        buttonPanel.add(editExperimentoButton);
        buttonPanel.add(deleteExperimentoButton);

        addExperimentoButton.addActionListener(e -> {
            adicionarExperimento(projeto);
            inicioTela.atualizarEstatisticas();
        });
        editExperimentoButton.addActionListener(e -> {
            Experimento experimento = experimentoList.getSelectedValue();
            if (experimento != null) {
                editarExperimento(experimento);
                inicioTela.atualizarEstatisticas();
            } else {
                JOptionPane.showMessageDialog(null, "Selecione um experimento para editar.", "Erro", JOptionPane.ERROR_MESSAGE);
            }
        });
        deleteExperimentoButton.addActionListener(e -> {
            Experimento experimento = experimentoList.getSelectedValue();
            if (experimento != null) {
                excluirExperimento(experimento, experimentoListModel, projeto);
                inicioTela.atualizarEstatisticas();
            } else {
                JOptionPane.showMessageDialog(null, "Selecione um experimento para excluir.", "Erro", JOptionPane.ERROR_MESSAGE);
            }
        });

        experimentoList.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                Experimento experimento = experimentoList.getSelectedValue();
                if (experimento != null) {
                    mostrarDetalhesExperimento(experimento);
                }
            }
        });

        frame.add(new JScrollPane(detailsArea), BorderLayout.CENTER);
        frame.add(new JScrollPane(experimentoList), BorderLayout.EAST);
        frame.add(buttonPanel, BorderLayout.SOUTH);
        frame.setVisible(true);
    }

    private void adicionarProjeto(DefaultListModel<Projeto> projetoListModel) {
        JTextField tituloField = new JTextField();
        JTextField descricaoField = new JTextField();
        JTextField mentorField = new JTextField();
        JTextField dataInicioField = new JTextField();
        JTextField dataPrevistaTerminoField = new JTextField();
        JComboBox<String> statusComboBox = new JComboBox<>(new String[]{"Em andamento", "Concluído"});
        JComboBox<String> tipoProjetoComboBox = new JComboBox<>(new String[]{"Projeto de Desenvolvimento", "Projeto de Pesquisa Científica"});
        JTextField campoExtraField = new JTextField();

        JPanel panel = new JPanel(new GridLayout(0, 1));
        panel.add(new JLabel("Título:"));
        panel.add(tituloField);
        panel.add(new JLabel("Descrição:"));
        panel.add(descricaoField);
        panel.add(new JLabel("Mentor:"));
        panel.add(mentorField);
        panel.add(new JLabel("Data Início (dd/MM/yyyy):"));
        panel.add(dataInicioField);
        panel.add(new JLabel("Data Prevista Término (dd/MM/yyyy):"));
        panel.add(dataPrevistaTerminoField);
        panel.add(new JLabel("Status:"));
        panel.add(statusComboBox);
        panel.add(new JLabel("Tipo de Projeto:"));
        panel.add(tipoProjetoComboBox);
        panel.add(new JLabel("Tecnologia Utilizada / Área de Pesquisa:"));
        panel.add(campoExtraField);

        tipoProjetoComboBox.addActionListener(e -> {
            if (tipoProjetoComboBox.getSelectedItem().equals("Projeto de Desenvolvimento")) {
                campoExtraField.setText("Tecnologia Utilizada");
            } else {
                campoExtraField.setText("Área de Pesquisa");
            }
        });

        int result = JOptionPane.showConfirmDialog(null, panel, "Adicionar Projeto", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if (result == JOptionPane.OK_OPTION) {
            try {
                Pesquisador mentor = sistema.getPesquisadores().stream()
                        .filter(p -> p.getNome().equals(mentorField.getText()))
                        .findFirst()
                        .orElseThrow(() -> new IOException("Mentor não encontrado"));

                if (!(mentor instanceof PesquisadorSenior)) {
                    throw new IOException("O mentor deve ser um Pesquisador Senior.");
                }

                PesquisadorSenior mentorSenior = (PesquisadorSenior) mentor;

                Projeto novoProjeto;
                if (tipoProjetoComboBox.getSelectedItem().equals("Projeto de Desenvolvimento")) {
                    novoProjeto = new ProjetoDesenvolvimento(
                            tituloField.getText(),
                            descricaoField.getText(),
                            mentorSenior,
                            dataInicioField.getText(),
                            dataPrevistaTerminoField.getText(),
                            statusComboBox.getSelectedItem().toString()
                    );
                    ((ProjetoDesenvolvimento) novoProjeto).adicionarTecnologiaUtilizada(campoExtraField.getText());
                } else {
                    novoProjeto = new ProjetoPesquisaCientifica(
                            tituloField.getText(),
                            descricaoField.getText(),
                            mentorSenior,
                            dataInicioField.getText(),
                            dataPrevistaTerminoField.getText(),
                            statusComboBox.getSelectedItem().toString(),
                            campoExtraField.getText()
                    );
                }

                sistema.adicionarProjeto(novoProjeto);
                projetoListModel.addElement(novoProjeto);
            } catch (IOException | DataFormatoInvalidoException e) {
                JOptionPane.showMessageDialog(null, "Erro ao adicionar projeto: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void editarProjeto(Projeto projeto) {
        if (projeto == null) {
            JOptionPane.showMessageDialog(null, "Selecione um projeto para editar.", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }

        JTextField tituloField = new JTextField(projeto.getTitulo());
        JTextField descricaoField = new JTextField(projeto.getDescricao());
        JComboBox<String> statusComboBox = new JComboBox<>(new String[]{"Em andamento", "Concluído"});
        statusComboBox.setSelectedItem(projeto.getStatus());

        JPanel panel = new JPanel(new GridLayout(0, 1));
        panel.add(new JLabel("Título:"));
        panel.add(tituloField);
        panel.add(new JLabel("Descrição:"));
        panel.add(descricaoField);
        panel.add(new JLabel("Status:"));
        panel.add(statusComboBox);

        int result = JOptionPane.showConfirmDialog(null, panel, "Editar Projeto", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if (result == JOptionPane.OK_OPTION) {
            projeto.setTitulo(tituloField.getText());
            projeto.setDescricao(descricaoField.getText());
            projeto.setStatus(statusComboBox.getSelectedItem().toString());
        }
    }

    private void excluirProjeto(Projeto projeto, DefaultListModel<Projeto> projetoListModel) {
        if (projeto == null) {
            JOptionPane.showMessageDialog(null, "Selecione um projeto para excluir.", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int result = JOptionPane.showConfirmDialog(null, "Tem certeza que deseja excluir o projeto?", "Confirmar Exclusão", JOptionPane.YES_NO_OPTION);
        if (result == JOptionPane.YES_OPTION) {
            sistema.removerProjeto(projeto);
            projetoListModel.removeElement(projeto);
        }
    }

    private void adicionarPesquisadorAoProjeto(Projeto projeto) {
        if (projeto == null) {
            JOptionPane.showMessageDialog(null, "Selecione um projeto para adicionar um pesquisador.", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }

        JTextField nomePesquisadorField = new JTextField();

        JPanel panel = new JPanel(new GridLayout(0, 1));
        panel.add(new JLabel("Nome do Pesquisador:"));
        panel.add(nomePesquisadorField);

        int result = JOptionPane.showConfirmDialog(null, panel, "Adicionar Pesquisador ao Projeto", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if (result == JOptionPane.OK_OPTION) {
            try {
                Pesquisador pesquisador = sistema.getPesquisadores().stream()
                        .filter(p -> p.getNome().equals(nomePesquisadorField.getText()))
                        .findFirst()
                        .orElseThrow(() -> new IOException("Pesquisador não encontrado"));

                projeto.adicionarPesquisador(pesquisador);
                JOptionPane.showMessageDialog(null, "Pesquisador adicionado com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
            } catch (IOException e) {
                JOptionPane.showMessageDialog(null, "Erro ao adicionar pesquisador ao projeto: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void adicionarExperimento(Projeto projeto) {
        if (projeto == null) {
            JOptionPane.showMessageDialog(null, "Selecione um projeto para adicionar um experimento.", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }

        JTextField nomeField = new JTextField();
        JTextField descricaoField = new JTextField();
        JTextField dataField = new JTextField();
        JTextField horasDedicadasField = new JTextField();

        JPanel panel = new JPanel(new GridLayout(0, 1));
        panel.add(new JLabel("Nome:"));
        panel.add(nomeField);
        panel.add(new JLabel("Descrição:"));
        panel.add(descricaoField);
        panel.add(new JLabel("Data (dd/MM/yyyy):"));
        panel.add(dataField);
        panel.add(new JLabel("Horas Dedicadas:"));
        panel.add(horasDedicadasField);

        int result = JOptionPane.showConfirmDialog(null, panel, "Adicionar Experimento", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if (result == JOptionPane.OK_OPTION) {
            try {
                Experimento novoExperimento = new Experimento(
                        nomeField.getText(),
                        dataField.getText(),
                        descricaoField.getText(),
                        projeto,
                        projeto.getPesquisadores(),
                        Integer.parseInt(horasDedicadasField.getText())
                );

                projeto.adicionarExperimento(novoExperimento);
                JOptionPane.showMessageDialog(null, "Experimento adicionado com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
            } catch (Experimento.HorasDedicadasInvalidasException |
                     Experimento.DataFormatoInvalidoException e) {
                JOptionPane.showMessageDialog(null, "Erro ao adicionar experimento: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void editarExperimento(Experimento experimento) {
        if (experimento == null) {
            JOptionPane.showMessageDialog(null, "Selecione um experimento para editar.", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }

        JTextField nomeField = new JTextField(experimento.getNome());
        JTextField descricaoField = new JTextField(experimento.getDescricao());
        JTextField dataField = new JTextField(Experimento.dateFormat.format(experimento.getData()));
        JTextField horasDedicadasField = new JTextField(String.valueOf(experimento.getHorasDedicadas()));

        JPanel panel = new JPanel(new GridLayout(0, 1));
        panel.add(new JLabel("Nome:"));
        panel.add(nomeField);
        panel.add(new JLabel("Descrição:"));
        panel.add(descricaoField);
        panel.add(new JLabel("Data (dd/MM/yyyy):"));
        panel.add(dataField);
        panel.add(new JLabel("Horas Dedicadas:"));
        panel.add(horasDedicadasField);

        int result = JOptionPane.showConfirmDialog(null, panel, "Editar Experimento", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if (result == JOptionPane.OK_OPTION) {
            try {
                experimento.setNome(nomeField.getText());
                experimento.setDescricao(descricaoField.getText());
                experimento.setData(dataField.getText());
                experimento.setHorasDedicadas(Integer.parseInt(horasDedicadasField.getText()));
                JOptionPane.showMessageDialog(null, "Experimento editado com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
            } catch (Experimento.HorasDedicadasInvalidasException |
                     Experimento.DataFormatoInvalidoException e) {
                JOptionPane.showMessageDialog(null, "Erro ao editar experimento: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void excluirExperimento(Experimento experimento, DefaultListModel<Experimento> experimentoListModel, Projeto projeto) {
        if (experimento == null) {
            JOptionPane.showMessageDialog(null, "Selecione um experimento para excluir.", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int result = JOptionPane.showConfirmDialog(null, "Tem certeza que deseja excluir o experimento?", "Confirmar Exclusão", JOptionPane.YES_NO_OPTION);
        if (result == JOptionPane.YES_OPTION) {
            projeto.getExperimentos().remove(experimento);
            experimentoListModel.removeElement(experimento);
        }
    }

    private void mostrarDetalhesExperimento(Experimento experimento) {
        JFrame frame = new JFrame("Detalhes do Experimento: " + experimento.getNome());
        frame.setSize(600, 400);

        JTextArea detailsArea = new JTextArea();
        detailsArea.setEditable(false);
        detailsArea.setFont(new Font("Arial", Font.PLAIN, 16));
        detailsArea.setLineWrap(true);
        detailsArea.setWrapStyleWord(true);

        StringBuilder details = new StringBuilder();
        details.append("Nome: ").append(experimento.getNome()).append("\n");
        details.append("Descrição: ").append(experimento.getDescricao()).append("\n");
        details.append("Projeto Associado: ").append(experimento.getProjetoAssociado().getTitulo()).append("\n");
        details.append("Data: ").append(experimento.formatDate()).append("\n");
        details.append("Horas Dedicadas: ").append(experimento.getHorasDedicadas()).append("\n");

        detailsArea.setText(details.toString());

        frame.add(new JScrollPane(detailsArea), BorderLayout.CENTER);
        frame.setVisible(true);
    }

    private class ProjetoCellRenderer extends JTextArea implements ListCellRenderer<Projeto> {
        public ProjetoCellRenderer() {
            setLineWrap(true);
            setWrapStyleWord(true);
        }

        @Override
        public Component getListCellRendererComponent(JList<? extends Projeto> list, Projeto value, int index, boolean isSelected, boolean cellHasFocus) {
            setText(String.format("Título: %s\nDescrição: %s\nMentor: %s\nData Início: %s\nData Prevista Término: %s\nStatus: %s",
                    value.getTitulo(), value.getDescricao(), value.getMentor().getNome(), value.formatDate(value.getDataInicio()), value.formatDate(value.getDataPrevistaTermino()), value.getStatus()));
            setFont(new Font("Arial", Font.PLAIN, 16));
            setBackground(isSelected ? list.getSelectionBackground() : list.getBackground());
            setForeground(isSelected ? list.getSelectionForeground() : list.getForeground());
            return this;
        }
    }

    private class ExperimentoCellRenderer extends JTextArea implements ListCellRenderer<Experimento> {
        public ExperimentoCellRenderer() {
            setLineWrap(true);
            setWrapStyleWord(true);
        }

        @Override
        public Component getListCellRendererComponent(JList<? extends Experimento> list, Experimento value, int index, boolean isSelected, boolean cellHasFocus) {
            setText(String.format("Nome: %s\nDescrição: %s\nProjeto Associado: %s\nData: %s\nHoras Dedicadas: %d",
                    value.getNome(), value.getDescricao(), value.getProjetoAssociado().getTitulo(), value.formatDate(), value.getHorasDedicadas()));
            setFont(new Font("Arial", Font.PLAIN, 16));
            setBackground(isSelected ? list.getSelectionBackground() : list.getBackground());
            setForeground(isSelected ? list.getSelectionForeground() : list.getForeground());
            return this;
        }
    }
}
