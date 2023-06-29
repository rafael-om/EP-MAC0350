package com.example.demo;
import org.springframework.jdbc.core.RowMapper;
import java.sql.ResultSet;
import java.sql.Date;
import java.sql.SQLException;

public class ServicoRowMapper implements RowMapper<Servico> {

    @Override
    public Servico mapRow(ResultSet rs, int rowNum) throws SQLException {

        Servico servico = new Servico();
        
        servico.setId_servico(rs.getInt("id_servico"));
        servico.setCodigo_servico(rs.getString("codigo_servico"));
        servico.setId_perfil(rs.getInt("id_perfil"));
        servico.setTipo_servico(rs.getString("tipo_servico"));

        return servico;
    }
}