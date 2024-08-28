import java.util.List;

public class PesquisadorSenior extends Pesquisador {
    private int tempoExperiencia;
    private List<String> diplomas;
    private int matricula;

    public PesquisadorSenior(String nome, int matricula, String areaEspecializacao, int tempoExperiencia, List<String> diplomas) throws TamanhoMatriculaException {
        super(nome, areaEspecializacao);
        if (String.valueOf(matricula).length() > 8) {
            throw new TamanhoMatriculaException("O número de matrícula deve ter no máximo 8 dígitos.");
        }
        this.tempoExperiencia = tempoExperiencia;
        this.diplomas = diplomas;
        this.matricula = matricula;
    }

    public int getTempoExperiencia() {
        return tempoExperiencia;
    }

    public List<String> getDiplomas() {
        return diplomas;
    }

    public int getMatricula() {
        return matricula;
    }

    public static class TamanhoMatriculaException extends Exception {
        public TamanhoMatriculaException(String message) {
            super(message);
        }
    }
}
