public class HistoricoEscolar {
    private String disciplina;
    private double nota;
    private int semestre;

    public HistoricoEscolar(String disciplina, double nota, int semestre) {
        this.disciplina = disciplina;
        this.nota = nota;
        this.semestre = semestre;
    }

    public String getDisciplina() {
        return disciplina;
    }

    public double getNota() {
        return nota;
    }

    public int getSemestre() {
        return semestre;
    }

    @Override
    public String toString() {
        return String.format("Historico Escolar:\nDisciplina: '%s', nota= %.2f, semestre atual: %d", disciplina, nota, semestre);
    }
}
