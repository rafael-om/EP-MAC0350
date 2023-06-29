package com.example.demo;
import java.sql.Date;

public class OferecimentoDisciplinaDocente {

    // Dados cadastrais
    Integer id_oferecimento_dd;
    String codigo_oferecimento;
    Integer id_disciplina;
    Integer id_docente;
    Date data_inicio;
    Date data_fim;

    public OferecimentoDisciplinaDocente() {
        this.id_oferecimento_dd = -1;
    }

    public Integer getId_oferecimento_dd() {
        return id_oferecimento_dd;
    }

    public void setId_oferecimento_dd(Integer id_oferecimento_dd) {
        this.id_oferecimento_dd = id_oferecimento_dd;
    }

    public String getCodigo_oferecimento() {
        return codigo_oferecimento;
    }

    public void setCodigo_oferecimento(String codigo_oferecimento) {
        this.codigo_oferecimento = codigo_oferecimento;
    }

    public Integer getId_disciplina() {
        return id_disciplina;
    }

    public void setId_disciplina(Integer id_disciplina) {
        this.id_disciplina = id_disciplina;
    }

    public Integer getId_docente() {
        return id_docente;
    }

    public void setId_docente(Integer id_docente) {
        this.id_docente = id_docente;
    }

    public Date getData_inicio() {
        return data_inicio;
    }

    public void setData_inicio(Date data_inicio) {
        this.data_inicio = data_inicio;
    }

    public Date getData_fim() {
        return data_fim;
    }

    public void setData_fim(Date data_fim) {
        this.data_fim = data_fim;
    }
}