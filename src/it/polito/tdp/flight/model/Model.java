package it.polito.tdp.flight.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jgrapht.GraphPath;
import org.jgrapht.Graphs;
import org.jgrapht.WeightedGraph;
import org.jgrapht.alg.DijkstraShortestPath;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleDirectedWeightedGraph;

import com.javadocmd.simplelatlng.LatLng;
import com.javadocmd.simplelatlng.LatLngTool;
import com.javadocmd.simplelatlng.util.LengthUnit;

import it.polito.tdp.flight.db.FlightDAO;

public class Model {
	
	private List<Airline> compagnie;
	private Map<Integer,Airline> mappaCompagnie;
	
	private List<Airport> aereoporti;
	private Map<Integer,Airport> mappaAereoporti;
	
	
	private FlightDAO dao;
	private WeightedGraph<Airport,DefaultWeightedEdge> grafo;


	public Model() {
		
		this.dao = new FlightDAO();
		this.mappaCompagnie = new HashMap<>();
		this.mappaAereoporti= new HashMap<>();
	}
	
	public List<Airline> getAllAirlines(){
		
		if(this.compagnie == null)
			this.compagnie = dao.getAllAirlines(mappaCompagnie);
		
		
		return compagnie;
		
	}
	
	public List<Airport> getAllAirports(){
		
		if(this.aereoporti == null)
			this.aereoporti = dao.getAllAirports(mappaAereoporti);
		
		
		return aereoporti;
		
	}

	public void creaGrafo(Airline compagnia) {
		
		
		this.grafo = new SimpleDirectedWeightedGraph<Airport,DefaultWeightedEdge>(DefaultWeightedEdge.class);
		
		Graphs.addAllVertices(grafo, this.getAllAirports());
		
		List<Route> routes = dao.getAllRotte(compagnia);

		for (Route r : routes) {
			if (r.getSourceAirportId() != 0 && r.getDestinationAirportId() != 0) {
				Airport a1 = mappaAereoporti.get(r.getSourceAirportId());
				Airport a2 = mappaAereoporti.get(r.getDestinationAirportId());

				if (a1 != null && a2 != null) {

					LatLng c1 = new LatLng(a1.getLatitude(), a1.getLongitude());
					LatLng c2 = new LatLng(a2.getLatitude(), a2.getLongitude());
					double distance = LatLngTool.distance(c1, c2, LengthUnit.KILOMETER);

					Graphs.addEdge(grafo, a1, a2, distance);
					
				}
			}
		}
		
	}

	public List<Airport> getRaggiungibili(Airline compagnia) {
	/*	
		List<Route> rotte = dao.getAllRotte(compagnia);
		
		List<Airport> raggiungibili = new ArrayList<>();
		
		for(Route r: rotte){
			
			if (r.getSourceAirportId() != 0 && r.getDestinationAirportId() != 0) {
				
				Airport a1 = mappaAereoporti.get(r.getSourceAirportId());
				Airport a2 = mappaAereoporti.get(r.getDestinationAirportId());

				if (a1 != null && a2 != null) {
					
					
					
					raggiungibili.add(a1);
					raggiungibili.add(a2);
					
					}
				}
			}
			
		
		
		
		return raggiungibili;
		
		*/
		
		return dao.getRaggiungibili(mappaAereoporti, compagnia);
	}

	public List<AirportDistance> getDestinations(Airline compagnia, Airport start) {
		
		List<AirportDistance> list = new ArrayList<>();

		for (Airport end : dao.getRaggiungibili(mappaAereoporti, compagnia)) {
			DijkstraShortestPath<Airport, DefaultWeightedEdge> dsp = new DijkstraShortestPath<>(grafo, start, end);
			GraphPath<Airport, DefaultWeightedEdge> p = dsp.getPath();
			if (p != null) {
				list.add(new AirportDistance(end, p.getWeight(), p.getEdgeList().size()));
			}
		}

		Collections.sort(list);

		return list;
	}

	
	

}
