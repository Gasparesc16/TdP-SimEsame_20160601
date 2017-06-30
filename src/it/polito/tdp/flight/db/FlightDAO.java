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
import it.polito.tdp.flight.model.Route;

public class FlightDAO {

	public List<Airport> getAllAirports(Map<Integer, Airport> mappaAereoporti) {
		
		String sql = "SELECT * FROM airport" ;
		
		List<Airport> list = new ArrayList<>() ;
		
		try {
			Connection conn = DBConnect.getConnection() ;

			PreparedStatement st = conn.prepareStatement(sql) ;
			
			ResultSet res = st.executeQuery() ;
			
			while(res.next()) {
				Airport a = new Airport(
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
						res.getString("tz")) ;
						
						list.add(a);
						mappaAereoporti.put(a.getAirportId(), a);
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
				"ORDER BY Airline_ID ";
		
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


	public List<Route> getAllRotte(Airline compagnia) {
		
		String sql ="select * from route " + 
				"where Airline_ID=?" ;

		List<Route> list = new ArrayList<>() ;
		
		try {
			Connection conn = DBConnect.getConnection() ;

			PreparedStatement st = conn.prepareStatement(sql) ;
			st.setInt(1, compagnia.getAirlineId());
			
			ResultSet res = st.executeQuery() ;
			
			while(res.next()) {
				list.add( new Route(
						res.getString("Airline"),
						res.getInt("Airline_ID"),
						res.getString("source_airport"),
						res.getInt("source_airport_id"),
						res.getString("destination_airport"),
						res.getInt("destination_airport_id"),
						res.getString("codeshare"),
						res.getInt("stops"),
						res.getString("equipment"))) ;
			}
			
			conn.close();
			
			return list ;
		} catch (SQLException e) {

			e.printStackTrace();
			return null ;
		}
	}


	public List<Airport> getRaggiungibili(Map<Integer, Airport> mappaAereoporti, Airline compagnia) {
		
		String sql = "select distinct AirportId from ( " + 
				"select distinct r1.Source_airport_ID as AirportId " + 
				"from route r1 " + 
				"where r1.Airline_ID=? " + 
				"union " + 
				"select distinct r2.Destination_airport_ID as AirportId " + 
				"from route r2 " + 
				"where r2.Airline_ID=? " + 
				") as ports" ;
		
		List<Airport> list = new ArrayList<>() ;
		
		try {
			Connection conn = DBConnect.getConnection() ;

			PreparedStatement st = conn.prepareStatement(sql) ;
			
			st.setInt(1, compagnia.getAirlineId());
			st.setInt(2, compagnia.getAirlineId());
			
			ResultSet res = st.executeQuery() ;
			
			while(res.next()) {
				
				Airport a = mappaAereoporti.get(res.getInt("AirportID"));
				
				if( a == null)
					throw new RuntimeException("AereoPorto inesistente!");
				
				list.add( a) ;
			}
			
			conn.close();
			
			return list ;
		} catch (SQLException e) {

			e.printStackTrace();
			return null ;
		}
	}
	
	
}
