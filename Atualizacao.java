import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Atualizacao {
    private String descricao;
    private Pesquisador autor;
    private Date data;
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yy");

    public Atualizacao(String descricao, Pesquisador autor, String data) throws DataFormatoInvalidoException {
        this.descricao = descricao;
        this.autor = autor;
        this.data = parseDate(data);
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

    public String getDescricao() {
        return descricao;
    }

    public Pesquisador getAutor() {
        return autor;
    }

    public String getData() {
        return dateFormat.format(data);
    }
}
