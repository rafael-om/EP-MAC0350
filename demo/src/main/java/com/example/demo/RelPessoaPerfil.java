package com.example.demo;
import java.sql.Date;
import java.math.BigInteger;

public class RelPessoaPerfil {

    // Dados cadastrais
    Integer id_pessoa_perfil;
    Integer id_pessoa;
    Integer id_perfil;

    public RelPessoaPerfil() {
        this.id_pessoa_perfil = -1;
    }

    public Integer getId_pessoa_perfil() {
        return id_pessoa_perfil;
    }

    public void setId_pessoa_perfil(Integer id_pessoa_perfil) {
        this.id_pessoa_perfil = id_pessoa_perfil;
    }

    public Integer getId_pessoa() {
        return id_pessoa;
    }

    public void setId_pessoa(Integer id_pessoa) {
        this.id_pessoa = id_pessoa;
    }

    public Integer getId_perfil() {
        return id_perfil;
    }

    public void setId_perfil(Integer id_perfil) {
        this.id_perfil = id_perfil;
    }
}