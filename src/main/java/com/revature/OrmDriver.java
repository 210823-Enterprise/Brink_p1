package com.revature;

import java.sql.Connection;
import java.sql.SQLException;

import com.revature.connection.ConnectionFactory;
import com.revature.diyORM.DIYORM;
import com.revature.dummymodels.Test;
import com.revature.util.Configuration;
import com.revature.util.MetaModel;

public class OrmDriver {

	public static void main(String[] args) {

		DIYORM orm = DIYORM.getInstance();
		Configuration cfg = new Configuration();
		cfg.addAnnotatedClass(Test.class);
		
		for (MetaModel<?> metamodel : cfg.getMetaModels()) {
            orm.createTableToDb(metamodel);
    	}

		// this is just to prove that we succesfully transformed it to a metamodel,
		// readable by our framework
		// let's iterate over all meta models that exist in the config object
		
//		try(Connection conn = ConnectionFactory.getInstance().getConnection()){
//			DIYORM orm = DIYORM.getInstance();
//			Test t = new Test(1, "c", "d", 5, true, 11.9);	
//			orm.updateObjectFromDb(t);
//			System.out.println(orm.getAllObjectsFromDb(t).toString());
//			
//		//	orm.saveObjectToDb(t);
//		} catch (SQLException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		
		// Testing "CREATE TABLE" SQL statement FINISHED
//		
//		String sql = "";
//		String columns = "";
//		for (MetaModel<?> metamodel : cfg.getMetaModels()) {
//			sql = "CREATE TABLE " + metamodel.getTableName() + "(" + metamodel.getPrimaryKey().getColumnName() + " SERIAL PRIMARY KEY";
//			List<ColumnField> columnFields = metamodel.getColumns();
//			for (ColumnField cf : columnFields) {
//				columns += ", " + cf.getColumnName() + " " + cf.getColumnType();
//			}
//		}
//		sql += columns + ");";
//		System.out.println(sql);

//		} catch (SQLException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}

	}
}