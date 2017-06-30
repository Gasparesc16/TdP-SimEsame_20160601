package it.polito.tdp.flight;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import it.polito.tdp.flight.model.Airline;
import it.polito.tdp.flight.model.Airport;
import it.polito.tdp.flight.model.AirportDistance;
import it.polito.tdp.flight.model.Model;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;

public class FlightController {
	
	private Model model;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private ComboBox<Airline> boxAirline;

    @FXML
    private ComboBox<Airport> boxAirport;

    @FXML
    private TextArea txtResult;
    

    /**
	 * @param model the model to set
	 */
	public void setModel(Model model) {
		this.model = model;
		
		this.boxAirline.getItems().addAll(model.getAllAirlines());
	}

	@FXML
    void doRaggiungibili(ActionEvent event) {
		
		txtResult.clear();
		
		Airline airline = boxAirline.getValue() ;
    	Airport start = boxAirport.getValue() ;
    	if(airline==null || start==null) {
    		txtResult.appendText("Selezionare compagnia e aeroporto\n") ;
    		return ;
    	}
    	
    	List<AirportDistance> list = model.getDestinations(airline, start) ;
    	
    	txtResult.clear();
    	txtResult.appendText("Distanze da "+start.getName()+"\n");
    	for(AirportDistance ad: list)
    		txtResult.appendText(String.format("%s (%.2f km) - %d steps\n", 
    				ad.getAirport().getName(), ad.getDistance(), ad.getTratte()));
		
		
		
		
		
		
		
		

    }

    @FXML
    void doServiti(ActionEvent event) {
    	
    	txtResult.clear();
		
		
		Airline compagnia = boxAirline.getValue();
		
		if(compagnia == null){
			
			txtResult.setText("Selezionare una compagnia aerea dal menù!");
			return;
			
			
		}
		
		
		model.creaGrafo(compagnia);
		txtResult.appendText("Il grafo é stato creato!\n");
		
		
		txtResult.appendText("\n");
		
		
		List<Airport> raggiungibili = model.getRaggiungibili(compagnia) ;
    	boxAirport.getItems().clear();
    	boxAirport.getItems().addAll(raggiungibili);
		
		txtResult.appendText("Elenco raggiungibili:\n");
		for(Airport a: raggiungibili)
			txtResult.appendText(a.toString() + "\n");

    }

    @FXML
    void initialize() {
        assert boxAirline != null : "fx:id=\"boxAirline\" was not injected: check your FXML file 'Flight.fxml'.";
        assert boxAirport != null : "fx:id=\"boxAirport\" was not injected: check your FXML file 'Flight.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Flight.fxml'.";

    }
}
