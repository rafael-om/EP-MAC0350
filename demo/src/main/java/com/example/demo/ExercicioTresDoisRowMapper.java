package com.example.demo;
import org.springframework.jdbc.core.RowMapper;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.math.BigInteger;

public class ExercicioTresDoisRowMapper implements RowMapper<ExercicioTresDois> {

    @Override
    public ExercicioTresDois mapRow(ResultSet rs, int rowNum) throws SQLException {

        ExercicioTresDois extd = new ExercicioTresDois();

        extd.setTipo_perfil(rs.getString("tipo_perfil"));
        extd.setTotal(new BigInteger(rs.getString("total")));

        return extd;
    }
}