package com.example.demo;
import org.springframework.jdbc.core.RowMapper;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.math.BigInteger;

public class ExercicioTresTresRowMapper implements RowMapper<ExercicioTresTres> {

    @Override
    public ExercicioTresTres mapRow(ResultSet rs, int rowNum) throws SQLException {

        ExercicioTresTres extt = new ExercicioTresTres();

        extt.setId_oferecimento(new BigInteger(rs.getString("id_oferecimento")));
        extt.setCodigo_disciplina(rs.getString("codigo_disciplina"));
        extt.setNome_docente(rs.getString("nome_docente"));
        extt.setNome_aluno(rs.getString("nome_aluno"));

        return extt;
    }
}