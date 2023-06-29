package com.example.demo;
import org.springframework.jdbc.core.RowMapper;
import java.sql.ResultSet;
import java.sql.Date;
import java.sql.SQLException;

public class DocenteRowMapper implements RowMapper<Docente> {

    @Override
    public Docente mapRow(ResultSet rs, int rowNum) throws SQLException {

        Docente docente = new Docente();
        
        docente.setId_docente(rs.getInt("id_docente"));
        docente.setId_pessoa(rs.getInt("id_pessoa"));
        docente.setEspecialidades(rs.getString("especialidades"));
        docente.setFuncoes_tecnicas(rs.getString("funcoes_tecnicas"));

        return docente;
    }
}