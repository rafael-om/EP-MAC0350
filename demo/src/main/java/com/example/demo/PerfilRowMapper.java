package com.example.demo;
import org.springframework.jdbc.core.RowMapper;
import java.sql.ResultSet;
import java.sql.Date;
import java.sql.SQLException;

public class PerfilRowMapper implements RowMapper<Perfil> {

    @Override
    public Perfil mapRow(ResultSet rs, int rowNum) throws SQLException {

        Perfil perfil = new Perfil();
        
        perfil.setId_perfil(rs.getInt("id_perfil"));
        perfil.setCodigo_perfil(rs.getString("codigo_perfil"));
        perfil.setTipo_perfil(rs.getString("tipo_perfil"));

        return perfil;
    }
}