package com.example.demo;
import org.springframework.jdbc.core.RowMapper;
import java.sql.ResultSet;
import java.sql.Date;
import java.sql.SQLException;

public class DisciplinaRowMapper implements RowMapper<Disciplina> {

    @Override
    public Disciplina mapRow(ResultSet rs, int rowNum) throws SQLException {

        Disciplina disciplina = new Disciplina();
        
        disciplina.setId_disciplina(rs.getInt("id_disciplina"));
        disciplina.setCodigo_disciplina(rs.getString("codigo_disciplina"));
        disciplina.setNome(rs.getString("nome"));
        disciplina.setEmenta(rs.getString("ementa"));
        disciplina.setData_criacao(rs.getDate("data_criacao"));

        return disciplina;
    }
}