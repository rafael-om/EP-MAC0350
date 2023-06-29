package com.example.demo;
import org.springframework.jdbc.core.RowMapper;
import java.sql.ResultSet;
import java.sql.Date;
import java.sql.SQLException;

public class RelOferecimentoAlunoRowMapper implements RowMapper<RelOferecimentoAluno> {

    @Override
    public RelOferecimentoAluno mapRow(ResultSet rs, int rowNum) throws SQLException {

        RelOferecimentoAluno rel_oferecimento_aluno = new RelOferecimentoAluno();
        
        rel_oferecimento_aluno.setId_oferecimento_aluno(rs.getInt("id_oferecimento_aluno"));
        rel_oferecimento_aluno.setId_oferecimento_dd(rs.getInt("id_oferecimento_dd"));
        rel_oferecimento_aluno.setId_aluno(rs.getInt("id_aluno"));
        rel_oferecimento_aluno.setNota(rs.getDouble("nota"));

        return rel_oferecimento_aluno;
    }
}