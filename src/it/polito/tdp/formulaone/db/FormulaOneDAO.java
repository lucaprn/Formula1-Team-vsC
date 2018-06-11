package it.polito.tdp.formulaone.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.Year;
import java.util.ArrayList;
import java.util.List;



import it.polito.tdp.formulaone.model.Circuit;
import it.polito.tdp.formulaone.model.Constructor;
import it.polito.tdp.formulaone.model.ConstructorIDMap;
import it.polito.tdp.formulaone.model.Driver;
import it.polito.tdp.formulaone.model.DriverIDMap;
import it.polito.tdp.formulaone.model.Season;


public class FormulaOneDAO {

	public List<Integer> getAllYearsOfRace() {
		
		String sql = "SELECT year FROM races ORDER BY year" ;
		
		try {
			Connection conn = DBConnect.getConnection() ;

			PreparedStatement st = conn.prepareStatement(sql) ;
			
			ResultSet rs = st.executeQuery() ;
			
			List<Integer> list = new ArrayList<>() ;
			while(rs.next()) {
				list.add(rs.getInt("year"));
			}
			
			conn.close();
			return list ;
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("SQL Query Error");
		}
	}
	
	public List<Season> getAllSeasons() {
		
		String sql = "SELECT year, url FROM seasons ORDER BY year" ;
		
		try {
			Connection conn = DBConnect.getConnection() ;

			PreparedStatement st = conn.prepareStatement(sql) ;
			
			ResultSet rs = st.executeQuery() ;
			
			List<Season> list = new ArrayList<>() ;
			while(rs.next()) {
				list.add(new Season(Year.of(rs.getInt("year")), rs.getString("url"))) ;
			}
			
			conn.close();
			return list ;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null ;
		}
	}
	
	public List<Circuit> getAllCircuits() {

		String sql = "SELECT circuitId, name FROM circuits ORDER BY name";

		try {
			Connection conn = DBConnect.getConnection();

			PreparedStatement st = conn.prepareStatement(sql);

			ResultSet rs = st.executeQuery();

			List<Circuit> list = new ArrayList<>();
			while (rs.next()) {
				list.add(new Circuit(rs.getInt("circuitId"), rs.getString("name")));
			}

			conn.close();
			return list;
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("SQL Query Error");
		}
	}
	
	public List<Constructor> getAllConstructors(ConstructorIDMap mapConstructor) {

		String sql = "SELECT constructorId, name FROM constructors ORDER BY name";

		try {
			Connection conn = DBConnect.getConnection();

			PreparedStatement st = conn.prepareStatement(sql);

			ResultSet rs = st.executeQuery();

			List<Constructor> constructors = new ArrayList<>();
			while (rs.next()) {
				Constructor c = new Constructor(rs.getInt("constructorId"), rs.getString("name"));
				constructors.add(c);
			}

			conn.close();
			return constructors;
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("SQL Query Error");
		}
	}
	
	public List<Driver> getAllDriversFromConstructor(Constructor c,DriverIDMap map){
		List<Driver> result = new ArrayList<>();
		String sql = "SELECT DISTINCT d.* \n" + 
				"FROM qualifying as q, drivers as d\n" + 
				"WHERE q.driverId = d.driverId AND q.constructorId = ?";

		try {
			Connection conn = DBConnect.getConnection();

			PreparedStatement st = conn.prepareStatement(sql);
			
			st.setInt(1, c.getConstructorId());

			ResultSet rs = st.executeQuery();

		
			while (rs.next()) {
				Driver d = new Driver(rs.getInt("driverID"), rs.getString("driverRef"), rs.getInt("number"), rs.getString("code"), rs.getString("forename"),rs.getString("surname"),
							rs.getDate("dob").toLocalDate(), rs.getString("nationality"), rs.getString("url"));
				
				result.add(map.get(d));
			
			}

			conn.close();
			return result;
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("SQL Query Error");
		}
	}
	
	public int gareDisputate(Driver d1,Driver d2){
		int numero =0; 
		List<Driver> result = new ArrayList<>();
		String sql = "SELECT COUNT(DISTINCT gare1.raceId) as count\n" + 
				"FROM (SELECT Distinct(raceId) \n" + 
				"FROM qualifying \n" + 
				"WHERE driverId = ?\n" + 
				"ORDER BY raceId ASC) as gare1,\n" + 
				"(SELECT Distinct(raceId)\n" + 
				"FROM qualifying \n" + 
				"WHERE driverId = ?\n" + 
				"ORDER BY raceId ASC) as gare2\n" + 
				"WHERE gare1.raceId = gare2.raceId";

		try {
			Connection conn = DBConnect.getConnection();

			PreparedStatement st = conn.prepareStatement(sql);
			
			st.setInt(1,d1.getDriverId());
			st.setInt(2,d2.getDriverId());
			ResultSet rs = st.executeQuery();

		
			while (rs.next()) {
				numero = rs.getInt("count");
			
			}

			conn.close();
			return numero;
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("SQL Query Error");
		}
	}
	
	


	
}
