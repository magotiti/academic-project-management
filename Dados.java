import javax.swing.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Dados {
    private Sistema sistema;

    public Dados(Sistema sistema) {
        this.sistema = sistema;
        inicializarDados();
    }

    private void inicializarDados() {
        try {
            List<String> diplomas = new ArrayList<>();
            diplomas.add("PhD em Biologia");
            PesquisadorSenior mentor1 = new PesquisadorSenior("Dr 1", 10012345, "Biologia", 10, diplomas);
            PesquisadorSenior mentor2 = new PesquisadorSenior("Dr 2", 10023456, "Química", 15, diplomas);

            sistema.adicionarPesquisador(mentor1);
            sistema.adicionarPesquisador(mentor2);

            PesquisadorJunior pesquisadorJunior1 = new PesquisadorJunior("Pesquisador 1", 10034567, "Biologia", "Biologia", 2, mentor1);
            PesquisadorJunior pesquisadorJunior2 = new PesquisadorJunior("Pesquisador 2", 10045678, "Química", "Química", 3, mentor2);
            PesquisadorJunior pesquisadorJunior3 = new PesquisadorJunior("Pesquisador 3", 10056789, "Física", "Física", 1, mentor1);

            sistema.adicionarPesquisador(pesquisadorJunior1);
            sistema.adicionarPesquisador(pesquisadorJunior2);
            sistema.adicionarPesquisador(pesquisadorJunior3);

            ProjetoDesenvolvimento projeto1 = new ProjetoDesenvolvimento(
                    "Pesquisa A", "Descrição A", mentor1, "01/01/22", "31/12/22", "Em andamento");
            ProjetoDesenvolvimento projeto2 = new ProjetoDesenvolvimento(
                    "Pesquisa B", "Descrição B", mentor2, "01/02/22", "30/11/22", "Concluído");
            ProjetoPesquisaCientifica projeto3 = new ProjetoPesquisaCientifica(
                    "Pesquisa C", "Descrição C", mentor1, "01/03/22", "31/10/22", "Em andamento", "Biologia");
            ProjetoPesquisaCientifica projeto4 = new ProjetoPesquisaCientifica(
                    "Pesquisa D", "Descrição D", mentor2, "01/04/22", "30/09/22", "Concluído", "Química");
            ProjetoDesenvolvimento projeto5 = new ProjetoDesenvolvimento(
                    "Pesquisa E", "Descrição E", mentor1, "01/05/22", "31/08/22", "Em andamento");

            sistema.adicionarProjeto(projeto1);
            sistema.adicionarProjeto(projeto2);
            sistema.adicionarProjeto(projeto3);
            sistema.adicionarProjeto(projeto4);
            sistema.adicionarProjeto(projeto5);

            List<Pesquisador> pesquisadores = new ArrayList<>();
            pesquisadores.add(mentor1);
            pesquisadores.add(mentor2);
            pesquisadores.add(pesquisadorJunior1);
            pesquisadores.add(pesquisadorJunior2);
            pesquisadores.add(pesquisadorJunior3);

            Experimento experimento1 = new Experimento("Experimento 1", "01/03/22", "Descrição 1", projeto1, pesquisadores, 100);
            Experimento experimento2 = new Experimento("Experimento 2", "01/04/22", "Descrição 2", projeto1, pesquisadores, 150);
            Experimento experimento3 = new Experimento("Experimento 3", "01/05/22", "Descrição 3", projeto2, pesquisadores, 200);
            Experimento experimento4 = new Experimento("Experimento 4", "01/06/22", "Descrição 4", projeto2, pesquisadores, 250);
            Experimento experimento5 = new Experimento("Experimento 5", "01/07/22", "Descrição 5", projeto3, pesquisadores, 300);
            Experimento experimento6 = new Experimento("Experimento 6", "01/08/22", "Descrição 6", projeto3, pesquisadores, 350);
            Experimento experimento7 = new Experimento("Experimento 7", "01/09/22", "Descrição 7", projeto4, pesquisadores, 400);
            Experimento experimento8 = new Experimento("Experimento 8", "01/10/22", "Descrição 8", projeto4, pesquisadores, 450);
            Experimento experimento9 = new Experimento("Experimento 9", "01/11/22", "Descrição 9", projeto5, pesquisadores, 500);
            Experimento experimento10 = new Experimento("Experimento 10", "01/12/22", "Descrição 10", projeto5, pesquisadores, 550);

            projeto1.adicionarExperimento(experimento1);
            projeto1.adicionarExperimento(experimento2);
            projeto2.adicionarExperimento(experimento3);
            projeto2.adicionarExperimento(experimento4);
            projeto3.adicionarExperimento(experimento5);
            projeto3.adicionarExperimento(experimento6);
            projeto4.adicionarExperimento(experimento7);
            projeto4.adicionarExperimento(experimento8);
            projeto5.adicionarExperimento(experimento9);
            projeto5.adicionarExperimento(experimento10);

        } catch (PesquisadorSenior.TamanhoMatriculaException | IOException | DataFormatoInvalidoException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Erro ao inicializar dados: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        } catch (Experimento.HorasDedicadasInvalidasException e) {
            throw new RuntimeException(e);
        } catch (Experimento.DataFormatoInvalidoException e) {
            throw new RuntimeException(e);
        } catch (PesquisadorJunior.TamanhoMatriculaException e) {
            throw new RuntimeException(e);
        }
    }
}
