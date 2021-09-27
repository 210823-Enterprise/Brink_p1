package com.revature.objectmapper;

import java.sql.Connection;
import java.sql.SQLException;

import org.apache.log4j.Logger;
import java.sql.Savepoint;
import java.util.HashMap;

public class Transactions {
	private static Logger log = Logger.getLogger(ObjectGetter.class);
	HashMap<String, Savepoint> savepoints = new HashMap<String, Savepoint>();
	final private static Transactions transaction = new Transactions();

	public boolean enableAutoCommit(Connection conn) {
		try {
			conn.setAutoCommit(true);
			log.info("Auto-Commiting on.");
			return true;

		} catch (SQLException e) {
			log.error("Auto-commit enabling failed.");
			return false;
		}
	}

	public boolean disableAutoCommit(Connection conn) {
		try {
			conn.setAutoCommit(false);
			log.info("Auto-Commiting off.");
			return true;

		} catch (SQLException e) {
			log.error("Auto-commit disabling failed.");
			return false;
		}
	}

	public boolean commit(Connection conn) {
		try {
			conn.commit();
			log.info("Committing Transaction.");
			return true;

		} catch (SQLException e) {
			log.error("Failed to commit.");
			return false;
		}
	}

	public boolean rollback(Connection conn) {
		try {
			conn.rollback();
			log.info("Rollback.");
			return true;

		} catch (SQLException e) {
			log.error("Failed to rollback.");
			return false;
		}
	}

	public boolean Savepoint(String name, Connection conn) {
		try {
			Savepoint save = conn.setSavepoint(name);
			savepoints.put(name, save);
			log.info("Savepoint " + save + " set.");
			return true;

		} catch (SQLException e) {
			log.error("Failed to initialize savepoint.");
			return false;
		}
	}

	public boolean ReleaseSavePoint(final String name, final Connection conn) {
		try {
			if (savepoints.containsKey(name)) {
				conn.releaseSavepoint(savepoints.get(name));
				log.info("Savepoint " + name + " removed.");
				return true;
			} else {
				log.warn("Savepoint does not exist");
				return false;
			}

		} catch (SQLException e) {
			log.error("Failed to remove savepoint.");
			return false;
		}
	}

	public boolean rollbackSavepoint(final String name, final Connection conn) {
		try {
			if (savepoints.containsKey(name)) {
				conn.rollback(savepoints.get(name));
				log.info("Rolled back to savepoint.");
				return true;
			} else {
				log.warn("Savepoint does not exist");
				return false;
			}

		} catch (SQLException e) {
			log.error("Failed to remove rollback.");
			return false;
		}
	}
	public static Transactions getInstance() {

		return transaction;
	}

}
