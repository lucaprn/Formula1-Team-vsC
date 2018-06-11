package it.polito.tdp.formulaone.model;

import java.util.HashMap;
import java.util.Map;

public class DriverIDMap {
private Map<Integer,Driver> map; 
	
	public DriverIDMap() {
		this.map=new HashMap();
	}
	
	public Driver get(Driver c) {
		Driver old = map.get(c.getDriverId());
		if(old==null) {
			map.put(c.getDriverId(), c);
			return c;
		}else {
			return old;
		}
	}
	
	public void put(Driver c) {
		this.map.put(c.getDriverId(), c);
	}
}
