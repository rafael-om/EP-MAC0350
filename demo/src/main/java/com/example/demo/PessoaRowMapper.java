package com.example.demo;
import org.springframework.jdbc.core.RowMapper;
import java.sql.ResultSet;
import java.sql.Date;
import java.sql.SQLException;

public class PessoaRowMapper implements RowMapper<Pessoa> {

    @Override
    public Pessoa mapRow(ResultSet rs, int rowNum) throws SQLException {

        Pessoa pessoa = new Pessoa();
        
        pessoa.setId_pessoa(rs.getInt("id_pessoa"));
        pessoa.setCpf(rs.getString("cpf"));
        pessoa.setNome(rs.getString("nome"));
        pessoa.setEndereco(rs.getString("endereco"));
        pessoa.setInstituicao(rs.getString("instituicao"));
        pessoa.setData_nascimento(rs.getDate("data_nascimento"));
        pessoa.setLogin_pessoa(rs.getString("login_pessoa"));
        pessoa.setSenha(rs.getString("senha"));

        return pessoa;
    }
}