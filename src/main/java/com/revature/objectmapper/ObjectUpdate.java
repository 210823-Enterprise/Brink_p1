package com.revature.objectmapper;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.ParameterMetaData;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import org.apache.log4j.Logger;

import com.revature.util.ColumnField;
import com.revature.util.IdField;
import com.revature.util.MetaModel;

public class ObjectUpdate extends ObjectMapper {

	private static Logger log = Logger.getLogger(ObjectUpdate.class);

	final private static ObjectUpdate objectUpdate = new ObjectUpdate();
	final private static ObjectGetter objectGetter = new ObjectGetter();
	final private static ObjectCache obj_cache = ObjectCache.getInstance();

	public boolean updateObjectFromDb(Object obj, Connection conn) {

		MetaModel<?> model = MetaModel.of(obj.getClass());
		String primaryKey = model.getPrimaryKey().getColumnName(); // get the name of the Id column
		Class clazz = obj.getClass(); // get an instance of the object's class
		List<ColumnField> columns = model.getColumns(); // list of all columns

		// get the IdField and set it to accsessible
		IdField id = model.getPrimaryKey();
		id.setAccsessible(true);

		String sql = "UPDATE " + model.getTableName() + " SET ";

		try {

			for (int i = 0; i < columns.size(); i++) {
				
				// get all the column field and set it as accessible
				Field field = clazz.getDeclaredField(columns.get(i).getName());
				field.setAccessible(true);
				
				// for the last one finish the statement, otherwise add a comma
				if (i == columns.size() - 1) {
					sql += columns.get(i).getColumnName() + "= '" + field.get(obj) + "'";
				} else {
					sql += columns.get(i).getColumnName() + "= '" + field.get(obj) + "', ";
				}
			}
		} catch (NoSuchFieldException e) {
			log.warn("Could not update object with id " + id.get(obj) + " in the database. Error: " + e);
		} catch (SecurityException e) {
			log.warn("Could not update object with id " + id.get(obj) + " in the database. Error: " + e);
		} catch (IllegalArgumentException e) {
			log.warn("Could not update object with id " + id.get(obj) + " in the database. Error: " + e);
		} catch (IllegalAccessException e) {
			log.warn("Could not update object with id " + id.get(obj) + " in the database. Error: " + e);
		}
		
		// finish the statement with the WHERE clause
		sql += " WHERE " + primaryKey + "= " + id.get(obj);
		
		try {
			// execute the prepared statement
			PreparedStatement pstmt;
			pstmt = conn.prepareStatement(sql);
			pstmt.executeUpdate();
			
			Object oldObj = objectGetter.getObjectFromDb(obj, conn);
			obj_cache.updateObjectInCache(oldObj, obj);
			
			
			log.info("Succsessfully updated object with id " + id.get(obj) + " in the database.");
			return true;

		} catch (SQLException e) {
			log.warn("Could not update object with id " + id.get(obj) + " in the database.");
		}

		return false;
	}

	public static ObjectUpdate getInstance() {

		return objectUpdate;
	}
	// could make another method to update specific data for object at primary key

}