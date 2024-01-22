package com.example.demo.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.example.demo.entity.phoneentry;

public class PhoneRowMapper implements RowMapper<phoneentry> {
    
    @Override
public phoneentry mapRow(ResultSet rs, int arg1) throws SQLException {
phoneentry pho = new phoneentry(rs.getString("firstname"),
rs.getString("middleinitial"),rs.getString("lastname"),
rs.getString("phonenumber"),rs.getString("localaddress"),
rs.getString("email"),rs.getString("id"));

return pho;
}

}
