package it.polito.tdp.flight.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import it.polito.tdp.flight.model.Airline;
import it.polito.tdp.flight.model.Airport;

public class FlightDAO {

	public List<Airport> getAllAirports() {
		
		String sql = "SELECT * FROM airport" ;
		
		List<Airport> list = new ArrayList<>() ;
		
		try {
			Connection conn = DBConnect.getConnection() ;

			PreparedStatement st = conn.prepareStatement(sql) ;
			
			ResultSet res = st.executeQuery() ;
			
			while(res.next()) {
				list.add( new Airport(
						res.getInt("Airport_ID"),
						res.getString("name"),
						res.getString("city"),
						res.getString("country"),
						res.getString("IATA_FAA"),
						res.getString("ICAO"),
						res.getDouble("Latitude"),
						res.getDouble("Longitude"),
						res.getFloat("timezone"),
						res.getString("dst"),
						res.getString("tz"))) ;
			}
			
			conn.close();
			
			return list ;
		} catch (SQLException e) {

			e.printStackTrace();
			return null ;
		}
	}
	
	
	public List<Airline> getAllAirlines(Map<Integer, Airline> mappaCompagnie) {
		
		String sql = 
				"SELECT Airline_ID,Name,Alias,IATA,ICAO,Callsign,Country,Active " +
				"FROM airline " +
				"ORDER BY Name ";
		
		List<Airline> list = new ArrayList<>() ;
		
		try {
			Connection conn = DBConnect.getConnection() ;

			PreparedStatement st = conn.prepareStatement(sql) ;
			
			ResultSet res = st.executeQuery() ;
			
			while(res.next()) {
				
				Airline compagnia = new Airline(res.getInt("Airline_ID"),res.getString("Name"),res.getString("Alias"),res.getString("IATA"),res.getString("ICAO"),
						res.getString("Callsign"),res.getString("Callsign"),res.getString("Active"));
				list.add(compagnia);
				mappaCompagnie.put(compagnia.getAirlineId(), compagnia);
				
			}
			
			conn.close();
			
			return list ;
		} catch (SQLException e) {

			e.printStackTrace();
			return null ;
		}
	}
	
	
}
