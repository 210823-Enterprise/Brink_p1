
package com.revature.diyORM;

import java.sql.Connection;

import com.revature.connection.ConnectionFactory;
import com.revature.objectmapper.ObjectCache;
import com.revature.objectmapper.ObjectGetter;
import com.revature.objectmapper.ObjectRemover;
import com.revature.objectmapper.ObjectSaver;
import com.revature.objectmapper.ObjectUpdate;
import com.revature.objectmapper.TableCreate;
import com.revature.objectmapper.TableDelete;
import com.revature.objectmapper.Transactions;
import com.revature.util.MetaModel;

public class DIYORM {

	final private static DIYORM diyorm = new DIYORM();
	private final Connection conn;
	private final ObjectRemover obj_remover;
	private final ObjectSaver obj_saver;
	private final ObjectUpdate obj_update;
	private final ObjectGetter obj_get;
	private final TableCreate table_create;
	private final TableDelete table_delete;
	private final ObjectCache obj_cache;
	private final Transactions trans;

	private DIYORM() {
		conn = ConnectionFactory.getInstance().getConnection();
		obj_remover = ObjectRemover.getInstance();
		obj_saver = ObjectSaver.getInstance();
		obj_update = ObjectUpdate.getInstance();
		obj_get = ObjectGetter.getInstance();
		table_create = TableCreate.getInstance();
		table_delete = TableDelete.getInstance();
		obj_cache = ObjectCache.getInstance();
		trans = Transactions.getInstance();
	}

	// return a a static instance of this singleton class
	public static DIYORM getInstance() {
		return diyorm;
	}

	// when someone wants to delete an object from their database
	// DIYORM.deleteObjFromDB
	public boolean removeObjFromDb(Object obj) {
		return obj_remover.removeObjectFromDb(obj, conn);
	}

	// when someone wants to insert
	// DIYORM.saveObjToDB
	public boolean saveObjectToDb(Object obj) {
		return obj_saver.saveObjectToDb(obj, conn);
	}

	// when someone wants to update
	// DIYORM.updateObjectFromDb
	public boolean updateObjectFromDb(Object obj) {
		return obj_update.updateObjectFromDb(obj, conn);
	}

	// when someone wants to get something from their database
	// DIYORM.getObjectFromDb
	public Object getObjectFromDb(Object obj) {
		return obj_get.getObjectFromDb(obj, conn);
	}

	// when someone wants to get all objects from a database table
	// DIYORM.getAllObjectsFromDb
	public Object getAllObjectsFromDb(Object obj) {
		return obj_get.getAllObjectsFromDb(obj, conn);
	}

	// when someone wants to create a table to database
	// DIYORM.createTableToDb
	public boolean createTableToDb(MetaModel m) {
		return table_create.createTableToDb(m, conn);
	}

	// when someone wants to create a table to database
	// DIYORM.deleteTableFromDb
	public boolean deleteTableFromDb(MetaModel m) {
		return table_delete.deleteTableFromDb(m, conn);
	}

	public void putObjectInCache(Object obj) {
		obj_cache.putObjectInCache(obj);
	}

	public boolean cacheContains(Object obj) {
		return obj_cache.cacheContains(obj);
	}

	public boolean removeObjectFromCache(Object obj) {
		return obj_cache.removeObjectFromCache(obj);
	}

	public boolean updateObjectInCache(Object toReplace, Object toAdd) {
		return obj_cache.updateObjectInCache(toReplace, toAdd);
	}

	public boolean enableAutoCommit(Connection conn) {
		return trans.enableAutoCommit(conn);
	}

	public boolean disableAutoCommit(Connection conn) {
		return trans.disableAutoCommit(conn);
	}

	public boolean commitChanges(Connection conn) {
		return trans.commit(conn);
	}

	public boolean rollbackChanges(Connection conn) {
		return trans.rollback(conn);
	}

	public boolean returnToSavepoint(Connection conn, String name) {
		return trans.rollbackSavepoint(name, conn);
	}

	public boolean setSavepoint(Connection conn, String name) {
		return trans.Savepoint(name, conn);
	}

	public boolean releaseSavepoint(Connection conn, String name) {
		return trans.ReleaseSavePoint(name, conn);
	}

}