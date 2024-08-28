import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class Sistema {
    private List<Projeto> projetos;
    private List<Pesquisador> pesquisadores;

    public Sistema() {
        this.projetos = new ArrayList<>();
        this.pesquisadores = new ArrayList<>();
    }

    // Método para adicionar um projeto com validação de mentor
    public void adicionarProjeto(Projeto projeto) throws IOException {
        if (!(projeto.getMentor() instanceof PesquisadorSenior)) {
            throw new IOException("O mentor do projeto deve ser um Pesquisador Senior.");
        }
        this.projetos.add(projeto);
    }

    // Método para adicionar um pesquisador
    public void adicionarPesquisador(Pesquisador pesquisador) {
        this.pesquisadores.add(pesquisador);
    }

    // Método para remover um projeto
    public void removerProjeto(Projeto projeto) {
        this.projetos.remove(projeto);
    }

    // Método para remover um pesquisador
    public void removerPesquisador(Pesquisador pesquisador) {
        this.pesquisadores.remove(pesquisador);
    }

    public List<Projeto> getProjetos() {
        return projetos;
    }

    public List<Pesquisador> getPesquisadores() {
        return pesquisadores;
    }

    // Método para ordenar pesquisadores alfabeticamente
    public List<Pesquisador> ordenarPesquisadores() {
        List<Pesquisador> pesquisadoresOrdenados = new ArrayList<>(pesquisadores);
        Collections.sort(pesquisadoresOrdenados);
        return pesquisadoresOrdenados;
    }

    // Método para ordenar projetos por nome
    public List<Projeto> ordenarProjetos() {
        List<Projeto> projetosOrdenados = new ArrayList<>(projetos);
        Collections.sort(projetosOrdenados);
        return projetosOrdenados;
    }

    // Método para filtrar projetos por pesquisador
    public List<Projeto> getProjetosPorPesquisador(Pesquisador pesquisador) {
        return projetos.stream()
                .filter(projeto -> projeto.getPesquisadores().contains(pesquisador))
                .collect(Collectors.toList());
    }

    // Método para obter grupos de pesquisa por pesquisador
    public List<GrupoPesquisa> getGruposPesquisaPorPesquisador(Pesquisador pesquisador) {
        return projetos.stream()
                .map(Projeto::getGrupoPesquisa)
                .filter(grupo -> grupo != null && grupo.getMembros().contains(pesquisador))
                .collect(Collectors.toList());
    }

    // Método para obter contribuições (atualizações) por pesquisador
    public List<Atualizacao> getContribuicoesPorPesquisador(Pesquisador pesquisador) {
        return projetos.stream()
                .flatMap(projeto -> projeto.getAtualizacoes().stream())
                .filter(atualizacao -> atualizacao.getAutor().equals(pesquisador))
                .collect(Collectors.toList());
    }

    // Método para pesquisa
    public List<Object> pesquisar(String termo) {
        List<Object> resultados = new ArrayList<>();

        // Pesquisar em projetos
        List<Projeto> projetosEncontrados = projetos.stream()
                .filter(projeto -> projeto.getTitulo().toLowerCase().contains(termo.toLowerCase()) ||
                        projeto.getDescricao().toLowerCase().contains(termo.toLowerCase()))
                .collect(Collectors.toList());

        // Pesquisar em pesquisadores
        List<Pesquisador> pesquisadoresEncontrados = pesquisadores.stream()
                .filter(pesquisador -> pesquisador.getNome().toLowerCase().contains(termo.toLowerCase()) ||
                        pesquisador.getAreaEspecializacao().toLowerCase().contains(termo.toLowerCase()))
                .collect(Collectors.toList());

        resultados.addAll(projetosEncontrados);
        resultados.addAll(pesquisadoresEncontrados);

        return resultados;
    }

    // Método para obter a atualização mais recente de um projeto
    public Atualizacao getAtualizacaoMaisRecente(Projeto projeto) {
        return projeto.getAtualizacoes().stream()
                .max(Comparator.comparing(Atualizacao::getData))
                .orElse(null);
    }

    // Método para somar o total de pesquisadores do tipo PesquisadorJunior
    public long getTotalPesquisadoresJunior() {
        return pesquisadores.stream()
                .filter(p -> p instanceof PesquisadorJunior)
                .count();
    }

    // Método para somar o total de pesquisadores do tipo PesquisadorSenior
    public long getTotalPesquisadoresSenior() {
        return pesquisadores.stream()
                .filter(p -> p instanceof PesquisadorSenior)
                .count();
    }

    // Método que retorna o total de projetos concluídos
    public long getTotalProjetosConcluidos() {
        return projetos.stream()
                .filter(p -> "concluído".equalsIgnoreCase(p.getStatus()))
                .count();
    }

    // Método que calcula a porcentagem de projetos de pesquisa científica
    public double getPercentualProjetosPesquisaCientifica() {
        long totalProjetos = projetos.size();
        if (totalProjetos == 0) {
            return 0;
        }

        long totalPesquisaCientifica = projetos.stream()
                .filter(p -> p instanceof ProjetoPesquisaCientifica)
                .count();

        return (double) totalPesquisaCientifica / totalProjetos * 100;
    }

    // Método que calcula a porcentagem de projetos de desenvolvimento
    public double getPercentualProjetosDesenvolvimento() {
        long totalProjetos = projetos.size();
        if (totalProjetos == 0) {
            return 0;
        }

        long totalDesenvolvimento = projetos.stream()
                .filter(p -> p instanceof ProjetoDesenvolvimento)
                .count();

        return (double) totalDesenvolvimento / totalProjetos * 100;
    }
}
