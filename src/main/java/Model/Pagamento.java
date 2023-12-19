package Model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Pagamento {
    private int id;
    private String referencia;
    private LocalDate data;
    private double valor;
    private ContaCorrente contaCorrente;
    private List<Encomenda> encomendas;
    private int feiraOffice;

    public Pagamento(int id, String referencia, LocalDate data, double valor, ContaCorrente contaCorrente, List<Encomenda> encomendas, int feiraOffice) {
        this.id = id;
        this.referencia = referencia;
        this.data = data;
        this.valor = valor;
        this.contaCorrente = contaCorrente;
        this.encomendas = encomendas;
        this.feiraOffice = feiraOffice;
    }

    public ContaCorrente getContaCorrente() {
        return contaCorrente;
    }

    public void setContaCorrente(ContaCorrente contaCorrente) {
        this.contaCorrente = contaCorrente;
    }

    public int getFeiraOffice() {
        return feiraOffice;
    }

    public void setFeiraOffice(int feiraOffice) {
        this.feiraOffice = feiraOffice;
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

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
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


    public List<Encomenda> getEncomendas() {
        return encomendas;
    }

    public void setEncomendas(List<Encomenda> encomendas) {
        this.encomendas = encomendas;
    }
}
