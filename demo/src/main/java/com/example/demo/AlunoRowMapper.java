package com.example.demo;
import org.springframework.jdbc.core.RowMapper;
import java.sql.ResultSet;
import java.sql.Date;
import java.sql.SQLException;

public class AlunoRowMapper implements RowMapper<Aluno> {

    @Override
    public Aluno mapRow(ResultSet rs, int rowNum) throws SQLException {

        Aluno aluno = new Aluno();
        
        aluno.setId_aluno(rs.getInt("id_aluno"));
        aluno.setId_pessoa(rs.getInt("id_pessoa"));
        aluno.setNota_ingresso(rs.getDouble("nota_ingresso"));
        aluno.setCurso(rs.getString("curso"));

        return aluno;
    }
}