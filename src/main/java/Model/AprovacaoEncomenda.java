package Model;

import java.time.LocalDate;

public class AprovacaoEncomenda {
    private int id;
    private int estado;
    private LocalDate data;
    private Utilizador idUtilizador;

    public AprovacaoEncomenda(int id, int estado, LocalDate data, Utilizador idUtilizador) {
        this.id = id;
        this.estado = estado;
        this.data = data;
        this.idUtilizador = idUtilizador;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getEstado() {
        return estado;
    }

    public void setEstado(int estado) {
        this.estado = estado;
    }

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public Utilizador getIdUtilizador() {
        return idUtilizador;
    }

    public void setIdUtilizador(Utilizador idUtilizador) {
        this.idUtilizador = idUtilizador;
    }
}
