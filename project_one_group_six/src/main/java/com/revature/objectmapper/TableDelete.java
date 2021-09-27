package com.revature.objectmapper;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.apache.log4j.Logger;

import com.revature.util.MetaModel;

public class TableDelete extends ObjectMapper {
	
	private static Logger log = Logger.getLogger(TableDelete.class);

	final private static TableDelete tableDelete = new TableDelete();

	public boolean deleteTableFromDb(MetaModel m, Connection conn) {

		String sql = "DROP TABLE IF EXISTS " + m.getTableName() + " CASCADE;";

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

	public static TableDelete getInstance() {

		return tableDelete;
	}
}
