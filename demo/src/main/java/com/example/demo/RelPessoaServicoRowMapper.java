package com.example.demo;
import org.springframework.jdbc.core.RowMapper;
import java.sql.ResultSet;
import java.sql.Date;
import java.sql.SQLException;

public class RelPessoaServicoRowMapper implements RowMapper<RelPessoaServico> {

    @Override
    public RelPessoaServico mapRow(ResultSet rs, int rowNum) throws SQLException {

        RelPessoaServico rel_pessoa_servico = new RelPessoaServico();
        
        rel_pessoa_servico.setId_pessoa_servico(rs.getInt("id_pessoa_servico"));
        rel_pessoa_servico.setId_pessoa(rs.getInt("id_pessoa"));
        rel_pessoa_servico.setId_servico(rs.getInt("id_servico"));
        rel_pessoa_servico.setData_uso_servico(rs.getDate("data_uso_servico"));

        return rel_pessoa_servico;
    }
}