public class Pesquisador implements Comparable<Pesquisador> {
    private String nome;
    private String areaEspecializacao;

    public Pesquisador(String nome, String areaEspecializacao) {
        this.nome = nome;
        this.areaEspecializacao = areaEspecializacao;
    }

    public String getAreaEspecializacao() {
        return areaEspecializacao;
    }

    public void setAreaEspecializacao(String areaEspecializacao) {
        this.areaEspecializacao = areaEspecializacao;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    @Override
    public int compareTo(Pesquisador outroPesquisador) {
        return this.nome.compareTo(outroPesquisador.getNome());
    }

    @Override
    public String toString() {
        return String.format("Pesquisador:\nNome: '%s'\nArea de especializaçao: '%s'", nome, areaEspecializacao);
    }

    public static class TamanhoMatriculaException extends Exception {
        public TamanhoMatriculaException(String message) {
            super(message);
        }
    }
}
