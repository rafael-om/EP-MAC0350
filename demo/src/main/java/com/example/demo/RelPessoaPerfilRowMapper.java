package com.example.demo;
import org.springframework.jdbc.core.RowMapper;
import java.sql.ResultSet;
import java.sql.Date;
import java.sql.SQLException;

public class RelPessoaPerfilRowMapper implements RowMapper<RelPessoaPerfil> {

    @Override
    public RelPessoaPerfil mapRow(ResultSet rs, int rowNum) throws SQLException {

        RelPessoaPerfil rel_pessoa_perfil = new RelPessoaPerfil();
        
        rel_pessoa_perfil.setId_pessoa_perfil(rs.getInt("id_pessoa_perfil"));
        rel_pessoa_perfil.setId_pessoa(rs.getInt("id_pessoa"));
        rel_pessoa_perfil.setId_perfil(rs.getInt("id_perfil"));

        return rel_pessoa_perfil;
    }
}