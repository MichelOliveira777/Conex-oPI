import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ConectaPI {
    private String url = "jdbc:mysql://localhost:3306/estoque";
    private String user = "root";
    private String password = "michel2003";

    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url, user, password);
    }

    public void criarCamisa(Camisa camisa) {
        String sql = "INSERT INTO camisas (Nome_time, Temporada, Tipo, Tamanho, Preco, Quantidade) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection connection = getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setString(1, camisa.getNomeTime());
            preparedStatement.setInt(2, camisa.getTemporada());
            preparedStatement.setString(3, camisa.getTipo());
            preparedStatement.setString(4, camisa.getTamanho());
            preparedStatement.setDouble(5, camisa.getPreco());
            preparedStatement.setInt(6, camisa.getQuantidade());

            int rowsAffected = preparedStatement.executeUpdate();
            System.out.println("Camisa criada! Linhas afetadas: " + rowsAffected);
        } catch (SQLException e) {
            System.out.println("Erro ao criar camisa: " + e.getMessage());
        }
    }

    public void atualizarCamisa(Camisa camisa) {
        String sql = "UPDATE camisas SET tipo = ?, tamanho = ?, preco = ?, quantidade = ? WHERE nome_time = ? AND temporada = ?";

        try (Connection connection = getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setString(1, camisa.getTipo());
            preparedStatement.setString(2, camisa.getTamanho());
            preparedStatement.setDouble(3, camisa.getPreco());
            preparedStatement.setInt(4, camisa.getQuantidade());
            preparedStatement.setString(5, camisa.getNomeTime());
            preparedStatement.setInt(6, camisa.getTemporada());

            int rowsAffected = preparedStatement.executeUpdate();
            System.out.println("Camisa atualizada! Linhas afetadas: " + rowsAffected);
        } catch (SQLException e) {
            System.out.println("Erro ao atualizar camisa: " + e.getMessage());
        }
    }

    public void consultarCamisas() {
        String sql = "SELECT * FROM camisas";

        try (Connection connection = getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
                ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                System.out.println("Camisa: " + resultSet.getString("nome_time") +
                        ", Temporada: " + resultSet.getInt("temporada") +
                        ", Tipo: " + resultSet.getString("tipo") +
                        ", Tamanho: " + resultSet.getString("tamanho") +
                        ", Preço: " + resultSet.getDouble("preco") +
                        ", Quantidade: " + resultSet.getInt("quantidade"));
            }
        } catch (SQLException e) {
            System.out.println("Erro ao consultar camisas: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        ConectaPI conectaPI = new ConectaPI();

        // Criar uma nova camisa
        Camisa novaCamisa = new Camisa("Flamengo", 2020, "Titular", "P", 666.66, 100);
        conectaPI.criarCamisa(novaCamisa);

        // Atualizar a camisa
        novaCamisa.setTipo("Reserva"); // Altera o tipo para Reserva
        conectaPI.atualizarCamisa(novaCamisa);

        // Consultar camisas
        conectaPI.consultarCamisas();
    }
}

class Camisa {
    private String nomeTime;
    private int temporada;
    private String tipo;
    private String tamanho;
    private double preco;
    private int quantidade;

    // Construtor
    public Camisa(String nomeTime, int temporada, String tipo, String tamanho, double preco, int quantidade) {
        this.nomeTime = nomeTime;
        this.temporada = temporada;
        this.tipo = tipo;
        this.tamanho = tamanho;
        this.preco = preco;
        this.quantidade = quantidade;
    }

    // Getters e Setters
    public String getNomeTime() {
        return nomeTime;
    }

    public void setNomeTime(String nomeTime) {
        this.nomeTime = nomeTime;
    }

    public int getTemporada() {
        return temporada;
    }

    public void setTemporada(int temporada) {
        this.temporada = temporada;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getTamanho() {
        return tamanho;
    }

    public void setTamanho(String tamanho) {
        this.tamanho = tamanho;
    }

    public double getPreco() {
        return preco;
    }

    public void setPreco(double preco) {
        this.preco = preco;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }
}
