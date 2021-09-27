package com.revature.objectmapper;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import org.apache.log4j.Logger;

import com.revature.util.ColumnField;
import com.revature.util.MetaModel;

public class TableCreate extends ObjectMapper {
	
	private static Logger log = Logger.getLogger(TableCreate.class);
	
	final private static TableCreate tableCreate= new TableCreate();

	public boolean createTableToDb(MetaModel m, Connection conn) {
		
		List<ColumnField> columnFields = m.getColumns();
		
		String columns = "";
		for (ColumnField cf : columnFields) {
			columns += ", " + cf.getColumnName() + " " + cf.getColumnType();
		}
		
		String sql = "DROP TABLE IF EXISTS "+m.getTableName()+" CASCADE;" +"CREATE TABLE " + m.getTableName() + "(" + m.getPrimaryKey().getColumnName() + " SERIAL PRIMARY KEY";
		sql += columns + ");";
		
		try {
			PreparedStatement pstmt;
			pstmt = conn.prepareStatement(sql);
			pstmt.execute();
			
			log.info("Succsessfully added table to the database");
			return true;
		} catch (SQLException e) {
			log.warn("Could not add table to the database");
		}
		return false;
	}
	
	public static TableCreate getInstance() {
		
		return tableCreate;
	}
}
