package com.example.demo;
import java.sql.Date;

public class Pessoa {

    // Dados cadastrais
    Integer id_pessoa;
    String cpf;
    String nome;
    String endereco;
    String instituicao;
    Date data_nascimento;
    
    // Dados de login
    String login_pessoa;
    String senha;

    public Pessoa() {
        this.id_pessoa = -1;
    }

    public Integer getIdPessoa() {
        return id_pessoa;
    }

    public void setIdPessoa(Integer id_pessoa) {
        this.id_pessoa = id_pessoa;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public String getInstituicao() {
        return instituicao;
    }

    public void setInstituicao(String instituicao) {
        this.instituicao = instituicao;
    }

    public Date getDataNascimento() {
        return data_nascimento;
    }

    public void setDataNascimento(Date data_nascimento) {
        this.data_nascimento = data_nascimento;
    }

    public String getLoginPessoa() {
        return login_pessoa;
    }

    public void setLoginPessoa(String login_pessoa) {
        this.login_pessoa = login_pessoa;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }
}