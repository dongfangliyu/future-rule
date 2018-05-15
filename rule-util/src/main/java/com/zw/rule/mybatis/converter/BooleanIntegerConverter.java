package com.zw.rule.mybatis.converter;

import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.TypeHandler;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class BooleanIntegerConverter implements TypeHandler< Boolean > {

	@Override
	public void setParameter( PreparedStatement ps, int i, Boolean parameter, JdbcType jdbcType ) throws SQLException {
		Boolean b = parameter;
		Integer value = b ? 1 : 0;
		ps.setInt( i, value );
	}

	@Override
	public Boolean getResult( ResultSet rs, String columnName ) throws SQLException {
		Integer value = rs.getInt( columnName );
		return value == 1;
	}

	@Override
	public Boolean getResult( ResultSet rs, int columnIndex ) throws SQLException {
		Integer value = rs.getInt( columnIndex );
		return value == 1 ? Boolean.TRUE : Boolean.FALSE;
	}

	@Override
	public Boolean getResult( CallableStatement cs, int columnIndex ) throws SQLException {
		Integer value = cs.getInt( columnIndex );
		return value == 1 ? Boolean.TRUE : Boolean.FALSE;
	}

}
