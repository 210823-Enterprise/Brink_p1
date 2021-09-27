package com.revature.objectmapper;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import org.apache.log4j.Logger;

import com.revature.util.ColumnField;
import com.revature.util.MetaModel;

public class ObjectSaver extends ObjectMapper {
	
	private static Logger log = Logger.getLogger(ObjectSaver.class);

	final private static ObjectSaver objectSaver = new ObjectSaver();
	final private static ObjectCache obj_cache = ObjectCache.getInstance();

	public boolean saveObjectToDb(Object obj, Connection conn) {

		MetaModel<?> model = MetaModel.of(obj.getClass());
		Class clazz = obj.getClass();
		List<ColumnField> columns = model.getColumns();
		String sql = "INSERT INTO " + model.getTableName() + " (";

		for (int i = 0; i < columns.size(); i++) {

			if (i == columns.size() - 1) {
				sql += columns.get(i).getColumnName() + ") VALUES (";
			} else {

				sql += columns.get(i).getColumnName() + ", ";
			}
		}

		for (int i = 0; i < columns.size(); i++) {
			try {

				Field field = clazz.getDeclaredField(columns.get(i).getName());
				field.setAccessible(true);
				
				if (i == columns.size() - 1) {
					sql += "'" + field.get(obj) + "');";

				} else {

					sql += "'" + field.get(obj) + "', ";
				}
			} catch (NoSuchFieldException e) {
				log.warn("Unable to save object to the database. Error: " + e);
			} catch (SecurityException e) {
				log.warn("Unable to save object to the database. Error: " + e);
			} catch (IllegalArgumentException e) {
				log.warn("Unable to save object to the database. Error: " + e);
			} catch (IllegalAccessException e) {
				log.warn("Unable to save object to the database. Error: " + e);
			}
		}
		
		System.out.println(sql);
		try {

		
			PreparedStatement pstmt;
			pstmt = conn.prepareStatement(sql);

			pstmt.executeUpdate();
		
			log.info("Succsessfully saved object to the database.");
			return true;

		} catch (SQLException e) {
			log.warn("Unable to save object to the database. Error: " + e);
		}

		return false;
	}

	public static ObjectSaver getInstance() {

		return objectSaver;
	}

}
