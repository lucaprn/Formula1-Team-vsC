package it.polito.tdp.formulaone;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import it.polito.tdp.formulaone.model.Constructor;
import it.polito.tdp.formulaone.model.Driver;
import it.polito.tdp.formulaone.model.Model;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class FormulaOneController {
	
	Model model;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private ComboBox<Constructor> boxCostruttori;

    @FXML
    private TextField textInputK;

    @FXML
    private TextArea txtResult;

    @FXML
    void doCreaGrafo(ActionEvent event) {
    	
    	try {
    		txtResult.clear();
        	Constructor c = this.boxCostruttori.getValue();
        	if(c!=null) {
    		
    		txtResult.appendText("Sto creando il grafo... \n");
    		
    		model.creaGrafo(c);
    		
    		txtResult.appendText("Grafo Creato!\nil grafo ha : "+model.getGrafo().vertexSet().size()+" e "+model.getGrafo().edgeSet().size()+" archi\n");
    		
    		Driver d = model.topDriver();
    		
    		if(d!=null) {
    			this.txtResult.appendText("Il pilota che ha gareggiato più volte è : "+d.toString()+"\n");
    		}else {
    			this.txtResult.appendText("Nessun top pilota\n");
    		}
    		
    		
    		
        	}else {
        		txtResult.setText("Errore, seleziona un costruttore!\n");
        	}
        	
        	
    	}catch(Exception e) {
    		e.printStackTrace();
    		this.txtResult.setText("Errore Database!");
    	}

    }

    @FXML
    void doTrovaDreamTeam(ActionEvent event) {
    	
    	try {
    		int max = model.getGrafo().vertexSet().size();
    		int min = 1;
    		int numero = Integer.parseInt(this.textInputK.getText());
    		if(max<=1) {
    			txtResult.appendText("Impossibile Trovare Dream team per questo Costruttore\n");
    			return;
    		}
    		if(numero>max || numero<min) {
    			txtResult.appendText(String.format("Inserisci un numero compreso tra %d e %d\n",min,max));
    			return;
    		}
    		model.dreamTeam(numero);
    		
    		if(model.getDreamTeam()==null) {
    			txtResult.appendText("Nessun Dream team trovato!\n");
    			return;
    		}
    			
    		List<Driver> team = model.getDreamTeam();
    		
    		int i =1;
    		
    		for(Driver d : team){
    			txtResult.appendText(String.format("%d) %s\n", i,d.toString()));
    			i++;
    		}
    			txtResult.appendText("Con : "+model.getPunteggio()+" punti\n");
    		
    	}catch(Exception e) {
    		e.printStackTrace();
    		txtResult.appendText("Errore nel Database!\n");
    	}
    }

    @FXML
    void initialize() {
        assert boxCostruttori != null : "fx:id=\"boxAnno\" was not injected: check your FXML file 'FormulaOne.fxml'.";
        assert textInputK != null : "fx:id=\"textInputK\" was not injected: check your FXML file 'FormulaOne.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'FormulaOne.fxml'.";

    }
    
    public void setModel(Model model){
    	this.model = model;
    	this.boxCostruttori.getItems().addAll(model.getListaConstructors());
    }
}
