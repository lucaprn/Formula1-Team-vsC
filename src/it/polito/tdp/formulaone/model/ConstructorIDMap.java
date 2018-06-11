package it.polito.tdp.formulaone.model;

import java.util.HashMap;
import java.util.Map;

public class ConstructorIDMap {
	
	private Map<Integer,Constructor> map; 
	
	public ConstructorIDMap() {
		this.map=new HashMap();
	}
	
	public Constructor get(Constructor c) {
		Constructor old = map.get(c.getConstructorId());
		if(old==null) {
			map.put(c.getConstructorId(), c);
			return c;
		}else {
			return old;
		}
	}
	
	public void put(Constructor c) {
		this.map.put(c.getConstructorId(), c);
	}
}
