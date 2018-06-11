package it.polito.tdp.formulaone.db;

import java.util.ArrayList;
import java.util.List;

import it.polito.tdp.formulaone.model.Constructor;
import it.polito.tdp.formulaone.model.ConstructorIDMap;
import it.polito.tdp.formulaone.model.Driver;
import it.polito.tdp.formulaone.model.DriverIDMap;
import it.polito.tdp.formulaone.model.Model;

public class TestDAO {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Constructor cd = new Constructor(4,"alfa");
		
		FormulaOneDAO dao = new FormulaOneDAO();
		
		ConstructorIDMap map = new ConstructorIDMap();
		
		DriverIDMap mapd = new DriverIDMap();
		
		Model model = new Model();
		
		List<Constructor> constructors = new ArrayList<>(dao.getAllConstructors(map));

		System.out.println("Tutti i costruttori : ");
		int i =1;
		for(Constructor c : constructors) {
			System.out.println(i+") "+c);
			i++;
		}
		
		List<Driver> drivers = dao.getAllDriversFromConstructor(cd, mapd);

		System.out.println("Tutti i piloti per costruttore : ");
		int j =1;
		for(Driver d : drivers) {
			System.out.println(j+") "+d);
			j++;
		}
	
}
}