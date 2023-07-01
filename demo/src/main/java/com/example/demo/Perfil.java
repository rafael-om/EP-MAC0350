package com.example.demo;
import java.sql.Date;

public class Perfil {

    // Dados cadastrais
    Integer id_perfil;
    String codigo_perfil;
    String tipo_perfil;

    public Perfil() {
        this.id_perfil = -1;
    }

    public Integer getId_perfil() {
        return id_perfil;
    }

    public void setId_perfil(Integer id_perfil) {
        this.id_perfil = id_perfil;
    }

    public String getCodigo_perfil() {
        return codigo_perfil;
    }

    public void setCodigo_perfil(String codigo_perfil) {
        this.codigo_perfil = codigo_perfil;
    }

    public String getTipo_perfil() {
        return tipo_perfil;
    }

    public void setTipo_perfil(String tipo_perfil) {
        this.tipo_perfil = tipo_perfil;
    }
}
