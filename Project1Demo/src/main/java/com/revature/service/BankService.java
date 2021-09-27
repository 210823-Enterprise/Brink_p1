package com.revature.service;

import java.util.List;

import com.revature.diyORM.DIYORM;
import com.revature.models.Bank;
import com.revature.util.Configuration;
import com.revature.util.MetaModel;

public class BankService {

    private DIYORM orm = DIYORM.getInstance();
    
    public void createTable(Configuration cfg) {
    	for (MetaModel<?> metamodel : cfg.getMetaModels()) {
            orm.createTableToDb(metamodel);
    	}
    }

    public Bank getUser(Bank b) {
        return (Bank) orm.getObjectFromDb(b);
    }

    public List<Bank> getAllUsers() {
        return (List<Bank>) orm.getAllObjectsFromDb(new Bank());
    }

    public boolean insert(Bank b) {
        return orm.saveObjectToDb(b);
    }

    public boolean update(Bank b) {
        return orm.updateObjectFromDb(b);
    }

    public boolean delete(Bank b) {
        return orm.removeObjFromDb(b);
    }

}