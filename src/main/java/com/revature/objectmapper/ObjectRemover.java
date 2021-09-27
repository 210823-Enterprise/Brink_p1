package com.revature.objectmapper;

import java.sql.Connection;
import java.sql.ParameterMetaData;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.apache.log4j.Logger;

import com.revature.util.IdField;
import com.revature.util.MetaModel;

public class ObjectRemover extends ObjectMapper {

	private static Logger log = Logger.getLogger(ObjectGetter.class);

	final private static ObjectRemover objectRemover = new ObjectRemover();
	final private static ObjectCache obj_cache = ObjectCache.getInstance();

	public boolean removeObjectFromDb(Object obj, Connection conn) {

		MetaModel<?> model = MetaModel.of(obj.getClass()); // use this to create an instance of the object
		String primaryKey = model.getPrimaryKey().getColumnName(); // get the name of the Id column
		
		// get the IdField and set it to accessible
		IdField id = model.getPrimaryKey();
		id.setAccsessible(true);

		// get the object based on the primary key
		String sql = "DELETE FROM " + model.getTableName() + " where " + primaryKey + "= " + id.get(obj);

		try {
			PreparedStatement pstmt;
			pstmt = conn.prepareStatement(sql);

			pstmt.executeUpdate();
			
			
			log.info("Successfully deleted object with id " + id.get(obj) + " from the database.");
			log.info("Successfully deleted object with id " + id.get(obj) + " from local cache.");
			obj_cache.removeObjectFromCache(obj);
			return true;

		} catch (SQLException e) {
			log.warn("Could not delete object with id " + id.get(obj) + " from the database. Error: " + e);
		}

		return false;
	}

	public static ObjectRemover getInstance() {

		return objectRemover;
	}

}
