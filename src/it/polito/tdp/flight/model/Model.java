package it.polito.tdp.flight.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import it.polito.tdp.flight.db.FlightDAO;

public class Model {
	
	private List<Airline> compagnie;
	private Map<Integer,Airline> mappaCompagnie;
	
	
	private FlightDAO dao;


	public Model() {
		
		this.dao = new FlightDAO();
		this.mappaCompagnie = new HashMap<>();
	}
	
	public List<Airline> getAllAirlines(){
		
		if(this.compagnie == null)
			this.compagnie = dao.getAllAirlines(mappaCompagnie);
		
		
		return compagnie;
		
	}
	
	
	

}
