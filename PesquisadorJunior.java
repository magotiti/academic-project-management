import java.util.ArrayList;
import java.util.List;

public class PesquisadorJunior extends Pesquisador {
    private String curso;
    private int semestre;
    private PesquisadorSenior orientador;
    private List<HistoricoEscolar> historicoEscolar;
    private int matricula;

    public PesquisadorJunior(String nome, int matricula, String areaEspecializacao, String curso, int semestre, PesquisadorSenior orientador) throws TamanhoMatriculaException {
        super(nome, areaEspecializacao);
        if (String.valueOf(matricula).length() > 8) {
            throw new TamanhoMatriculaException("O número de matrícula deve ter no máximo 8 dígitos.");
        }
        this.curso = curso;
        this.semestre = semestre;
        this.orientador = orientador;
        this.matricula = matricula;
        this.historicoEscolar = new ArrayList<>();
    }

    public int getMatricula() {
        return matricula;
    }

    public String getCurso() {
        return curso;
    }

    public int getSemestre() {
        return semestre;
    }

    public void setCurso(String curso) {
        this.curso = curso;
    }

    public PesquisadorSenior getOrientador() {
        return orientador;
    }

    public void setOrientador(PesquisadorSenior orientador) {
        this.orientador = orientador;
    }

    public List<HistoricoEscolar> getHistoricoEscolar() {
        return new ArrayList<>(historicoEscolar);
    }

    public void adicionarHistoricoEscolar(HistoricoEscolar historico) {
        this.historicoEscolar.add(historico);
    }

    public static class TamanhoMatriculaException extends Exception {
        public TamanhoMatriculaException(String message) {
            super(message);
        }
    }
}
