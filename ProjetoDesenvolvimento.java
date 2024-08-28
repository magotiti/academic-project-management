import java.util.ArrayList;
import java.util.List;

public class ProjetoDesenvolvimento extends Projeto {
    private List<String> tecnologiasUtilizadas;

    public ProjetoDesenvolvimento(String titulo, String descricao, PesquisadorSenior mentor, String dataInicio, String dataPrevistaTermino, String status) throws DataFormatoInvalidoException {
        super(titulo, descricao, mentor, dataInicio, dataPrevistaTermino, status);
        this.tecnologiasUtilizadas = new ArrayList<>();
    }

    public List<String> getTecnologiasUtilizadas() {
        return tecnologiasUtilizadas;
    }

    public void adicionarTecnologiaUtilizada(String tecnologia) {
        this.tecnologiasUtilizadas.add(tecnologia);
    }

    @Override
    public String toString() {
        return String.format("Projeto de Desenvolvimento: %s \nMentor: %s\nDescrição do projeto: %s\nTecnologia utilizada: %s\nData de ínicio: %s\nStatus: %s", getTitulo(), getMentor(), getDescricao(), getTecnologiasUtilizadas(), getDataInicio(), getStatus());
    }
}
