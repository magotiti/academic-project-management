import java.util.ArrayList;
import java.util.List;

public class ProjetoPesquisaCientifica extends Projeto {
    private String areaPesquisa;
    private List<String> publicacoes;

    public ProjetoPesquisaCientifica(String titulo, String descricao, PesquisadorSenior mentor, String dataInicio, String dataPrevistaTermino, String status, String areaPesquisa) throws DataFormatoInvalidoException {
        super(titulo, descricao, mentor, dataInicio, dataPrevistaTermino, status);
        this.areaPesquisa = areaPesquisa;
        this.publicacoes = new ArrayList<>();
    }

    public String getAreaPesquisa() {
        return areaPesquisa;
    }

    public void setAreaPesquisa(String areaPesquisa) {
        this.areaPesquisa = areaPesquisa;
    }

    public List<String> getPublicacoes() {
        return publicacoes;
    }

    public void adicionarPublicacao(String publicacao) {
        this.publicacoes.add(publicacao);
    }

    @Override
    public String toString() {
        return String.format("Pesquisa Científica: %s \nMentor: %s\nDescrição do projeto: %s\nÁrea de pesquisa: %s\nData de ínicio: %s\nStatus: %s", getTitulo(), getMentor(), getDescricao(), getAreaPesquisa(), getDataInicio(), getStatus());
    }
}
