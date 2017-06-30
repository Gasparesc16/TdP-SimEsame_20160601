package it.polito.tdp.flight.model;

public class AirportDistance implements Comparable<AirportDistance> {
	
	private Airport airport ;
	private double distance ;
	private int tratte ;
	
	public AirportDistance(Airport airport, double distance, int tratte) {
		super();
		this.airport = airport;
		this.distance = distance;
		this.setTratte(tratte) ;
	}
	public Airport getAirport() {
		return airport;
	}
	public void setAirport(Airport airport) {
		this.airport = airport;
	}
	public double getDistance() {
		return distance;
	}
	public void setDistance(double distance) {
		this.distance = distance;
	}
	public int getTratte() {
		return tratte;
	}
	public void setTratte(int tratte) {
		this.tratte = tratte;
	}
	@Override
	public int compareTo(AirportDistance o) {
		
		return Double.compare(this.distance, o.distance);
	}
	

}
