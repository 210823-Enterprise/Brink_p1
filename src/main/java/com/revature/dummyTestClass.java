package com.revature;

import com.revature.dummymodels.Test;
import com.revature.util.Configuration;
import com.revature.util.MetaModel;

public class dummyTestClass {
	
	public static void main(String[]args) {
	Configuration cfg = new Configuration();
	cfg.addAnnotatedClass(Test.class);
	MetaModel<?> model = MetaModel.of(Test.class);
	
	System.out.println(model.getSimpleClassName());

	}
}
