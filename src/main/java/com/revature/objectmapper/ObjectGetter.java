package com.revature.objectmapper;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.ParameterMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.revature.util.ColumnField;
import com.revature.util.IdField;
import com.revature.util.MetaModel;

public class ObjectGetter extends ObjectMapper {
	
	private static Logger log = Logger.getLogger(ObjectGetter.class);
	
	final private static ObjectGetter objectGetter = new ObjectGetter();
	final private static ObjectCache obj_cache = ObjectCache.getInstance();
	
	public Object getObjectFromDb(Object obj, Connection conn) {
		
		if (obj_cache.cacheContains(obj)) {
			log.info("Retrived an object from the cache");
			return obj;
		}
		
		MetaModel<?> model = MetaModel.of(obj.getClass()); // use this to create an instance of the object
		String primaryKey = model.getPrimaryKey().getColumnName(); // get the name of the Id column
		Class clazz = obj.getClass(); // get an instance of the object's class
		
		// get the IdField and set it to accsessible
		IdField id = model.getPrimaryKey(); 
		id.setAccsessible(true);
		
		// get the object based on the primary key
		String sql = "SELECT * FROM " + model.getTableName() + " WHERE " + primaryKey + "= " + id.get(obj);
		
		try {
			// send the statement to the DB
			PreparedStatement pstmt;
			pstmt = conn.prepareStatement(sql);

			ResultSet rs = pstmt.executeQuery();
			
			// creating lists of the column names, and types
			List<ColumnField> columns = model.getColumns();
			List<Class> types = new ArrayList<Class>();
			types.add(model.getPrimaryKey().getType()); // ad the id field to the types column because it's not annotated with @Column
			
			// create a list of the objects specified by the columns
			List<Object> params = new ArrayList<Object>();
			
			// add the type of each column to the types list
			for (ColumnField cf : columns) {
					types.add(cf.getType());
			}
			
			// move the cursor to the current row
			rs.next();
			
			// add each column in the result set to the params list
			for (int i = 1; i <= columns.size() + 1; i++) {
			
				params.add(rs.getObject(i));
				
			}
			
			// get the constructor and create a new object
			Constructor constructor = clazz.getDeclaredConstructor(types.toArray(new Class[types.size()]));
			Object returnObj = constructor.newInstance(params.toArray(new Object[params.size()]));
			
			
			obj_cache.putObjectInCache(obj);
			log.info("Succsessfully retrieved object with primary key " + id.get(obj) + " from the database.");
			return returnObj;

		} catch (SQLException e) {
			log.warn("Unable to retrieve object with primary key " +  id.get(obj) + " from the database. Error: " + e);
		} catch (NoSuchMethodException e1) {
			log.warn("Unable to retrieve object with primary key " +  id.get(obj) + " from the database. Error: " + e1);
		} catch (SecurityException e1) {
			log.warn("Unable to retrieve object with primary key " +  id.get(obj) + " from the database. Error: " + e1);
		} catch (InstantiationException e1) {
			log.warn("Unable to retrieve object with primary key " +  id.get(obj) + " from the database. Error: " + e1);
		} catch (IllegalAccessException e1) {
			log.warn("Unable to retrieve object with primary key " +  id.get(obj) + " from the database. Error: " + e1);
		} catch (IllegalArgumentException e1) {
			log.warn("Unable to retrieve object with primary key " +  id.get(obj) + " from the database. Error: " + e1);
		} catch (InvocationTargetException e1) {
			log.warn("Unable to retrieve object with primary key " +  id.get(obj) + " from the database. Error: " + e1);
		}
		
		
		return null;
	}
	
	public List<Object> getAllObjectsFromDb(Object obj, Connection conn) {
		
		MetaModel<?> model = MetaModel.of(obj.getClass()); // use this to create an instance of the object
		String primaryKey = model.getPrimaryKey().getColumnName(); // get the name of the Id column
		Class clazz = obj.getClass(); // get an instance of the object's class
		
		String sql = "SELECT * FROM " + model.getTableName();
		
		try {
			// send the statement to the DB
			PreparedStatement pstmt;
			pstmt = conn.prepareStatement(sql);

			ResultSet rs = pstmt.executeQuery();
			
			// creating lists of the column names, and types
			List<ColumnField> columns = model.getColumns();
			List<Class> types = new ArrayList<Class>();
			types.add(model.getPrimaryKey().getType()); // ad the id field to the types column because it's not annotated with @Column
			
			// create a list of the objects specified by the columns
			List<Object> params = new ArrayList<Object>();
			
			// add the type of each column to the types list
			for (ColumnField cf : columns) {
					types.add(cf.getType());
			}
			
			// get the declared constructor
			Constructor constructor = clazz.getDeclaredConstructor(types.toArray(new Class[types.size()]));
			
			List<Object> returnObjs = new ArrayList<Object>();
			
			while (rs.next()) {

				// add each column in the result set to the params list
				for (int i = 1; i <= columns.size() + 1; i++) {

					params.add(rs.getObject(i));
				}
				
				// create a new object and add it to the list to return
				Object returnObj = constructor.newInstance(params.toArray(new Object[params.size()]));	
				returnObjs.add(returnObj);
				params.clear();
			}
			
			for (Object o : returnObjs) {
				obj_cache.putObjectInCache(o);
			}
			
			log.info("Succsessfully added all objects from DB into local cache.");
			log.info("Succsessfully retrieved objects from the database.");
			return returnObjs;

		} catch (SQLException e) {
			log.warn("Unable to retrieve objects from the database. Error: " + e);
		} catch (NoSuchMethodException e1) {
			log.warn("Unable to retrieve objects from the database. Error: " + e1);
		} catch (SecurityException e1) {
			log.warn("Unable to retrieve objects from the database. Error: " + e1);
		} catch (InstantiationException e1) {
			log.warn("Unable to retrieve objects from the database. Error: " + e1);
		} catch (IllegalAccessException e1) {
			log.warn("Unable to retrieve objects from the database. Error: " + e1);
		} catch (IllegalArgumentException e1) {
			log.warn("Unable to retrieve objects from the database. Error: " + e1);
		} catch (InvocationTargetException e1) {
			log.warn("Unable to retrieve objects from the database. Error: " + e1);
		}
		
		return null;
	}
	
	
	
	public static ObjectGetter getInstance() {
		
		return objectGetter;
	}
}
