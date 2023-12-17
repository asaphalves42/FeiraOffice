package Model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Random;

public class Pagamento {
    private int id;
    private String referencia;
    private LocalDate data;
    private ContaCorrente contaCorrente;
    private ArrayList<Encomenda> encomendas;

    public Pagamento(){

    }

    public Pagamento(int id, String referencia, LocalDate data, ContaCorrente valor, ArrayList<Encomenda> encomendas) {
        this.id = id;
        this.referencia = referencia;
        this.data = data;
        this.contaCorrente = valor;
        this.encomendas = encomendas;
    }

    public static String gerarReferencia() {
        // Gera 6 números aleatórios
        String numeros = gerarNumerosAleatorios(6);

        // Gera 3 letras aleatórias
        String letras = gerarLetrasAleatorias(3);

        // Combina os números e letras para formar a referência
        return numeros + letras;
    }

    private static String gerarNumerosAleatorios(int quantidade) {
        Random random = new Random();
        StringBuilder numeros = new StringBuilder();

        for (int i = 0; i < quantidade; i++) {
            numeros.append(random.nextInt(10)); // Gera números de 0 a 9
        }

        return numeros.toString();
    }

    private static String gerarLetrasAleatorias(int quantidade) {
        Random random = new Random();
        StringBuilder letras = new StringBuilder();

        for (int i = 0; i < quantidade; i++) {
            char letra = (char) ('A' + random.nextInt(26)); // Gera letras de A a Z
            letras.append(letra);
        }

        return letras.toString();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getReferencia() {
        return referencia = gerarReferencia();
    }

    public void setReferencia(String referencia) {
        this.referencia = referencia;
    }

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public ContaCorrente getValor() {
        return contaCorrente;
    }

    public void setValor(ContaCorrente valor) {
        this.contaCorrente = valor;
    }

    public ArrayList<Encomenda> getEncomendas() {
        return encomendas;
    }

    public void setEncomendas(ArrayList<Encomenda> encomendas) {
        this.encomendas = encomendas;
    }
}
