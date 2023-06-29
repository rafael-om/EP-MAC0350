package com.example.demo;
import java.sql.Date;

public class RelPessoaServico {

    // Dados cadastrais
    Integer id_pessoa_servico;
    Integer id_pessoa;
    Integer id_servico;
    Date data_uso_servico;

    public RelPessoaServico() {
        this.id_pessoa_servico = -1;
    }

    public Integer getId_pessoa_servico() {
        return id_pessoa_servico;
    }

    public void setId_pessoa_servico(Integer id_pessoa_servico) {
        this.id_pessoa_servico = id_pessoa_servico;
    }

    public Integer getId_pessoa() {
        return id_pessoa;
    }

    public void setId_pessoa(Integer id_pessoa) {
        this.id_pessoa = id_pessoa;
    }

    public Integer getId_servico() {
        return id_servico;
    }

    public void setId_servico(Integer id_servico) {
        this.id_servico = id_servico;
    }

    public Date getData_uso_servico() {
        return data_uso_servico;
    }

    public void setData_uso_servico(Date data_uso_servico) {
        this.data_uso_servico = data_uso_servico;
    }
}