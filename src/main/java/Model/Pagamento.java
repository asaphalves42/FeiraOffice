package Model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Pagamento {
    private int id; // O identificador único do pagamento
    private String referencia; // A referência do pagamento
    private LocalDate data; // A data do pagamento
    private double valor; // O valor do pagamento
    private ContaCorrente contaCorrente; // A conta corrente associada ao pagamento
    private List<Encomenda> encomendas; // Lista de encomendas associadas ao pagamento
    private int feiraOffice; // O identificador da feira office associada ao pagamento

    /**
     * Construtor da classe Pagamento.
     *
     * @param id            O identificador único do pagamento.
     * @param referencia    A referência do pagamento.
     * @param data          A data do pagamento.
     * @param valor         O valor do pagamento.
     * @param contaCorrente A conta corrente associada ao pagamento.
     * @param encomendas    A lista de encomendas associadas ao pagamento.
     * @param feiraOffice   O identificador da feira office associada ao pagamento.
     */

    public Pagamento(int id, String referencia, LocalDate data, double valor, ContaCorrente contaCorrente, List<Encomenda> encomendas, int feiraOffice) {
        this.id = id;
        this.referencia = referencia;
        this.data = data;
        this.valor = valor;
        this.contaCorrente = contaCorrente;
        this.encomendas = encomendas;
        this.feiraOffice = feiraOffice;
    }

    /**
     * Obtém a conta corrente associada ao pagamento.
     *
     * @return A conta corrente associada ao pagamento.
     */
    public ContaCorrente getContaCorrente() {
        return contaCorrente;
    }

    /**
     * Define a conta corrente associada ao pagamento.
     *
     * @param contaCorrente A conta corrente associada ao pagamento.
     */
    public void setContaCorrente(ContaCorrente contaCorrente) {
        this.contaCorrente = contaCorrente;
    }

    /**
     * Obtém o identificador da feira office associada ao pagamento.
     *
     * @return O identificador da feira office associada ao pagamento.
     */
    public int getFeiraOffice() {
        return feiraOffice;
    }

    /**
     * Define o identificador da feira office associada ao pagamento.
     *
     * @param feiraOffice O identificador da feira office associada ao pagamento.
     */
    public void setFeiraOffice(int feiraOffice) {
        this.feiraOffice = feiraOffice;
    }

    /**
     * Método estático para gerar uma referência aleatória para o pagamento.
     *
     * @return A referência gerada.
     */
    public static String gerarReferencia() {
        // Gera 6 números aleatórios
        String numeros = gerarNumerosAleatorios(6);

        // Gera 3 letras aleatórias
        String letras = gerarLetrasAleatorias(3);

        // Combina os números e letras para formar a referência
        return numeros + letras;
    }

    /**
     * Método privado para gerar uma sequência de números aleatórios.
     *
     * @param quantidade A quantidade de números a gerar.
     * @return A sequência de números aleatórios.
     */
    private static String gerarNumerosAleatorios(int quantidade) {
        Random random = new Random();
        StringBuilder numeros = new StringBuilder();

        for (int i = 0; i < quantidade; i++) {
            numeros.append(random.nextInt(10)); // Gera números de 0 a 9
        }

        return numeros.toString();
    }

    /**
     * Método privado para gerar uma sequência de letras aleatórias.
     *
     * @param quantidade A quantidade de letras a gerar.
     * @return A sequência de letras aleatórias.
     */
    private static String gerarLetrasAleatorias(int quantidade) {
        Random random = new Random();
        StringBuilder letras = new StringBuilder();

        for (int i = 0; i < quantidade; i++) {
            char letra = (char) ('A' + random.nextInt(26)); // Gera letras de A a Z
            letras.append(letra);
        }

        return letras.toString();
    }

    /**
     * Obtém o valor do pagamento.
     *
     * @return O valor do pagamento.
     */
    public double getValor() {
        return valor;
    }

    /**
     * Define o valor do pagamento.
     *
     * @param valor O valor do pagamento.
     */
    public void setValor(double valor) {
        this.valor = valor;
    }

    /**
     * Obtém o identificador único do pagamento.
     *
     * @return O identificador único do pagamento.
     */
    public int getId() {
        return id;
    }

    /**
     * Define o identificador único do pagamento.
     *
     * @param id O identificador único do pagamento.
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Obtém a referência do pagamento.
     *
     * @return A referência do pagamento.
     */
    public String getReferencia() {
        return referencia = gerarReferencia();
    }

    /**
     * Define a referência do pagamento.
     *
     * @param referencia A referência do pagamento.
     */
    public void setReferencia(String referencia) {
        this.referencia = referencia;
    }

    /**
     * Obtém a data do pagamento.
     *
     * @return A data do pagamento.
     */
    public LocalDate getData() {
        return data;
    }

    /**
     * Define a data do pagamento.
     *
     * @param data A data do pagamento.
     */
    public void setData(LocalDate data) {
        this.data = data;
    }

    /**
     * Obtém a lista de encomendas associadas ao pagamento.
     *
     * @return A lista de encomendas associadas ao pagamento.
     */
    public List<Encomenda> getEncomendas() {
        return encomendas;
    }

    /**
     * Define a lista de encomendas associadas ao pagamento.
     *
     * @param encomendas A lista de encomendas associadas ao pagamento.
     */
    public void setEncomendas(List<Encomenda> encomendas) {
        this.encomendas = encomendas;
    }
}
