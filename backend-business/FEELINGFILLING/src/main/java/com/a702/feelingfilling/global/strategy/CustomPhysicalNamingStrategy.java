package com.a702.feelingfilling.global.strategy;

import org.hibernate.boot.model.naming.Identifier;
import org.hibernate.boot.model.naming.PhysicalNamingStrategyStandardImpl;
import org.hibernate.engine.jdbc.env.spi.JdbcEnvironment;

public class CustomPhysicalNamingStrategy extends PhysicalNamingStrategyStandardImpl {
	@Override
	public Identifier toPhysicalTableName(Identifier name, JdbcEnvironment context) {
		return new Identifier(toSnakeCase(name.getText()), name.isQuoted());
	}
	
	@Override
	public Identifier toPhysicalColumnName(Identifier name, JdbcEnvironment context) {
		return new Identifier(toSnakeCase(name.getText()), name.isQuoted());
	}
	
	private String toSnakeCase(String input) {
		return input.replaceAll("([a-z])([A-Z])", "$1_$2").toLowerCase();
	}
}