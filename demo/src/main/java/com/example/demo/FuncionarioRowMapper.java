package com.example.demo;
import org.springframework.jdbc.core.RowMapper;
import java.sql.ResultSet;
import java.sql.Date;
import java.sql.SQLException;

public class FuncionarioRowMapper implements RowMapper<Funcionario> {

    @Override
    public Funcionario mapRow(ResultSet rs, int rowNum) throws SQLException {

        Funcionario funcionario = new Funcionario();
        
        funcionario.setId_funcionario(rs.getInt("id_funcionario"));
        funcionario.setId_pessoa(rs.getInt("id_pessoa"));
        funcionario.setEspecialidades(rs.getString("especialidades"));
        funcionario.setFuncoes_tecnicas(rs.getString("funcoes_tecnicas"));

        return funcionario;
    }
}