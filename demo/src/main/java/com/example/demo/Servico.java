package com.example.demo;
import java.sql.Date;

public class Servico {

    // Dados cadastrais
    Integer id_servico;
    String codigo_servico;
    Integer id_perfil;
    String tipo_servico;

    public Servico() {
        this.id_servico = -1;
    }

    public Integer getId_servico() {
        return this.id_servico;
    }

    public void setId_servico(Integer id_servico) {
        this.id_servico = id_servico;
    }

    public String getCodigo_servico() {
        return codigo_servico;
    }

    public void setCodigo_servico(String codigo_servico) {
        this.codigo_servico = codigo_servico;
    }

    public Integer getId_perfil() {
        return id_perfil;
    }

    public void setId_perfil(Integer id_perfil) {
        this.id_perfil = id_perfil;
    }

    public String getTipo_servico() {
        return tipo_servico;
    }

    public void setTipo_servico(String tipo_servico) {
        this.tipo_servico = tipo_servico;
    }
}