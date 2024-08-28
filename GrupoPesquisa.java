import java.util.List;

public class GrupoPesquisa {
    private String nome;
    private List<Pesquisador> membros;

    public GrupoPesquisa(String nome, List<Pesquisador> membros) {
        this.nome = nome;
        this.membros = membros;
    }

    public String getNome() {
        return nome;
    }

    public List<Pesquisador> getMembros() {
        return membros;
    }
    }
