import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public abstract class Projeto implements Comparable<Projeto> {
    private String titulo;
    private String descricao;
    private PesquisadorSenior mentor;
    private Date dataInicio;
    private Date dataPrevistaTermino;
    private String status;
    private List<Atualizacao> atualizacoes;
    private List<Pesquisador> pesquisadores;
    private GrupoPesquisa grupoPesquisa;
    private List<Experimento> experimentos;

    protected static final SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yy");

    public Projeto(String titulo, String descricao, PesquisadorSenior mentor, String dataInicio, String dataPrevistaTermino, String status) throws DataFormatoInvalidoException {
        this.titulo = titulo;
        this.descricao = descricao;
        this.mentor = mentor;
        this.dataInicio = parseDate(dataInicio);
        this.dataPrevistaTermino = parseDate(dataPrevistaTermino);
        this.status = status;
        this.atualizacoes = new ArrayList<>();
        this.pesquisadores = new ArrayList<>();
        this.experimentos = new ArrayList<>();
        this.pesquisadores.add(mentor);
    }

    private Date parseDate(String date) throws DataFormatoInvalidoException {
        try {
            Date parsedDate = dateFormat.parse(date);
            if (!date.equals(dateFormat.format(parsedDate))) {
                throw new ParseException("Invalid date", 0);
            }

            String[] dateParts = date.split("/");
            int day = Integer.parseInt(dateParts[0]);
            int month = Integer.parseInt(dateParts[1]);

            if (day < 1 || day > 31 || month < 1 || month > 12) {
                throw new DataFormatoInvalidoException("Data no formato inválido. Use o formato dd/MM/yy.");
            }

            return parsedDate;
        } catch (ParseException e) {
            throw new DataFormatoInvalidoException("Data no formato inválido. Use o formato dd/MM/yy.");
        }
    }

    public void setData(String data) throws DataFormatoInvalidoException {
        this.dataInicio = parseDate(data);
    }

    public String formatDate(Date date) {
        return dateFormat.format(date);
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public PesquisadorSenior getMentor() {
        return mentor;
    }

    public Date getDataInicio() {
        return dataInicio;
    }

    public Date getDataPrevistaTermino() {
        return dataPrevistaTermino;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<Atualizacao> getAtualizacoes() {
        return atualizacoes;
    }

    public List<Pesquisador> getPesquisadores() {
        return pesquisadores;
    }

    public GrupoPesquisa getGrupoPesquisa() {
        return grupoPesquisa;
    }

    public List<Experimento> getExperimentos() {
        return experimentos;
    }

    public void adicionarAtualizacao(Atualizacao atualizacao) {
        this.atualizacoes.add(atualizacao);
    }

    public void adicionarPesquisador(Pesquisador pesquisador) {
        this.pesquisadores.add(pesquisador);
        if (this.pesquisadores.size() >= 3) {
            this.grupoPesquisa = new GrupoPesquisa("Grupo de Pesquisa " + this.titulo, this.pesquisadores);
        }
    }

    public void adicionarExperimento(Experimento experimento) {
        this.experimentos.add(experimento);
    }

    @Override
    public int compareTo(Projeto outroProjeto) {
        return this.titulo.compareTo(outroProjeto.getTitulo());
    }
}
