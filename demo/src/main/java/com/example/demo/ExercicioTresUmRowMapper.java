package com.example.demo;
import org.springframework.jdbc.core.RowMapper;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ExercicioTresUmRowMapper implements RowMapper<ExercicioTresUm> {

    @Override
    public ExercicioTresUm mapRow(ResultSet rs, int rowNum) throws SQLException {

        ExercicioTresUm extu = new ExercicioTresUm();
        
        extu.setNome(rs.getString("nome"));
        extu.setTipo_perfil(rs.getString("tipo_perfil"));
        extu.setTipo_servico(rs.getString("tipo_servico"));

        return extu;
    }
}