import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class Experimento {
    private String nome;
    private String descricao;
    private Projeto projetoAssociado;
    private Date data;
    private List<Pesquisador> pesquisadores;
    private int horasDedicadas;

    protected static final SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yy");

    public Experimento(String nome, String data, String descricao, Projeto projetoAssociado, List<Pesquisador> pesquisadores, int horasDedicadas) throws DataFormatoInvalidoException, HorasDedicadasInvalidasException {
        this.nome = nome;
        this.descricao = descricao;
        this.projetoAssociado = projetoAssociado;
        this.data = parseDate(data);
        this.pesquisadores = pesquisadores;
        this.horasDedicadas = horasDedicadas;

        if (horasDedicadas <= 0) {
            throw new HorasDedicadasInvalidasException("Horas dedicadas devem ser maiores que zero.");
        }
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
        this.data = parseDate(data);
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Projeto getProjetoAssociado() {
        return projetoAssociado;
    }

    public Date getData() {
        return data;
    }

    public List<Pesquisador> getPesquisadores() {
        return pesquisadores;
    }

    public int getHorasDedicadas() {
        return horasDedicadas;
    }

    public void setHorasDedicadas(int horasDedicadas) throws HorasDedicadasInvalidasException {
        if (horasDedicadas <= 0) {
            throw new HorasDedicadasInvalidasException("Horas dedicadas devem ser maiores que zero.");
        }
        this.horasDedicadas = horasDedicadas;
    }

    public String formatDate() {
        return dateFormat.format(this.data);
    }

    public static class DataFormatoInvalidoException extends Exception {
        public DataFormatoInvalidoException(String message) {
            super(message);
        }
    }

    public static class HorasDedicadasInvalidasException extends Exception {
        public HorasDedicadasInvalidasException(String message) {
            super(message);
        }
    }
}
