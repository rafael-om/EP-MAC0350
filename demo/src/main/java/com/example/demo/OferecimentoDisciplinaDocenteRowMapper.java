package com.example.demo;
import org.springframework.jdbc.core.RowMapper;
import java.sql.ResultSet;
import java.sql.Date;
import java.sql.SQLException;

public class OferecimentoDisciplinaDocenteRowMapper implements RowMapper<OferecimentoDisciplinaDocente> {

    @Override
    public OferecimentoDisciplinaDocente mapRow(ResultSet rs, int rowNum) throws SQLException {

        OferecimentoDisciplinaDocente oferecimento_disciplina_docente = new OferecimentoDisciplinaDocente();
        
        oferecimento_disciplina_docente.setId_oferecimento_dd(rs.getInt("id_oferecimento_dd"));
        oferecimento_disciplina_docente.setCodigo_oferecimento(rs.getString("codigo_oferecimento"));
        oferecimento_disciplina_docente.setId_disciplina(rs.getInt("id_disciplina"));
        oferecimento_disciplina_docente.setId_docente(rs.getInt("id_docente"));
        oferecimento_disciplina_docente.setData_inicio(rs.getDate("data_inicio"));
        oferecimento_disciplina_docente.setData_fim(rs.getDate("data_fim"));

        return oferecimento_disciplina_docente;
    }
}