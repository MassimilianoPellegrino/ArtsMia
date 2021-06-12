/**
 * Sample Skeleton for 'Scene.fxml' Controller Class
 */

package it.polito.tdp.artsmia;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import it.polito.tdp.artsmia.model.Model;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class FXMLController {
	
	private Model model;

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="boxLUN"
    private ChoiceBox<Integer> boxLUN; // Value injected by FXMLLoader

    @FXML // fx:id="btnCalcolaComponenteConnessa"
    private Button btnCalcolaComponenteConnessa; // Value injected by FXMLLoader

    @FXML // fx:id="btnCercaOggetti"
    private Button btnCercaOggetti; // Value injected by FXMLLoader

    @FXML // fx:id="btnAnalizzaOggetti"
    private Button btnAnalizzaOggetti; // Value injected by FXMLLoader

    @FXML // fx:id="txtObjectId"
    private TextField txtObjectId; // Value injected by FXMLLoader

    @FXML // fx:id="txtResult"
    private TextArea txtResult; // Value injected by FXMLLoader

    
    
    @FXML
    void doAnalizzaOggetti(ActionEvent event) {
    	model.creaGrafo();
    	this.txtObjectId.setEditable(true);
    	this.btnCalcolaComponenteConnessa.setDisable(false);

    }

    @FXML
    void doCalcolaComponenteConnessa(ActionEvent event) {
    	this.boxLUN.getItems().clear();
    	this.txtResult.clear();
    	int id;
    	try {
    		id = Integer.parseInt(this.txtObjectId.getText());
    		if(model.getIdMap().get(id)!=null)
    			model.componenteConnessa(id);
    		else
    			throw new NullPointerException();
    	}catch(NumberFormatException e) {
    		this.txtResult.setText("Inserire un numero");
    		return;
    	}catch(NullPointerException e) {
    		this.txtResult.setText("Oggetto inesistente");
    		return;
    	}
    	this.txtResult.setText("SOTTOGRAFO CREATO!\n"
				+ "\t#VERTICI: "+model.getSottoGrafo().vertexSet().size());
    	
    	List<Integer> size = new ArrayList<>();
    	
    	for(int i=2; i<=model.getSottoGrafo().vertexSet().size(); i++) {
    		if(i>15)
    			break;
    		size.add(i);
    	}
    	
    	this.boxLUN.setDisable(false);
    	this.btnCercaOggetti.setDisable(false);
    	this.boxLUN.getItems().addAll(size);
    }

    @FXML
    void doCercaOggetti(ActionEvent event) {

    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
    	this.txtObjectId.setEditable(false);
    	this.btnCalcolaComponenteConnessa.setDisable(true);
    	this.boxLUN.setDisable(true);
    	this.btnCercaOggetti.setDisable(true);
        assert boxLUN != null : "fx:id=\"boxLUN\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnCalcolaComponenteConnessa != null : "fx:id=\"btnCalcolaComponenteConnessa\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnCercaOggetti != null : "fx:id=\"btnCercaOggetti\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnAnalizzaOggetti != null : "fx:id=\"btnAnalizzaOggetti\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtObjectId != null : "fx:id=\"txtObjectId\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Scene.fxml'.";

    }
    
    public void setModel(Model model) {
    	this.model = model;
    }
}
