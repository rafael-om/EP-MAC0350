package com.example.demo;
import org.springframework.jdbc.core.RowMapper;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.math.BigInteger;

public class ExercicioTresQuatroRowMapper implements RowMapper<ExercicioTresQuatro> {

    @Override
    public ExercicioTresQuatro mapRow(ResultSet rs, int rowNum) throws SQLException {

        ExercicioTresQuatro extq = new ExercicioTresQuatro();

        extq.setNome_docente(rs.getString("nome_docente"));
        extq.setId_docente(new BigInteger(rs.getString("id_docente")));

        return extq;
    }
}