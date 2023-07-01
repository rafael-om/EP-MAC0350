package com.example.demo;
import java.math.BigInteger;

public class ExercicioTresTres {
    BigInteger id_oferecimento;
    String codigo_disciplina;
    String nome_docente;
    String nome_aluno;

    public BigInteger getId_oferecimento() {
        return this.id_oferecimento;
    }

    public void setId_oferecimento(BigInteger id_oferecimento) {
        this.id_oferecimento = id_oferecimento;
    }

    public String getCodigo_disciplina() {
        return this.codigo_disciplina;
    }

    public void setCodigo_disciplina(String codigo_disciplina) {
        this.codigo_disciplina = codigo_disciplina;
    }

    public String getNome_docente() {
        return this.nome_docente;
    }

    public void setNome_docente(String nome_docente) {
        this.nome_docente = nome_docente;
    }

    public String getNome_aluno() {
        return this.nome_aluno;
    }

    public void setNome_aluno(String nome_aluno) {
        this.nome_aluno = nome_aluno;
    }
}