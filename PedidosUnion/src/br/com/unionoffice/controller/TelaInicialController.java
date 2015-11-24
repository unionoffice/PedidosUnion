package br.com.unionoffice.controller;



import java.net.URL;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.stage.Stage;

public class TelaInicialController{
	private Tab tabCadCliente;

	@FXML
	private TabPane tabPane;
	
	

	@FXML
	protected void itSairClick(ActionEvent event) {
		
		Platform.exit();
	}

	@FXML
	protected void itCadClienteClick(ActionEvent event) {
		if (tabCadCliente == null) {
			tabCadCliente = new Tab();
			tabCadCliente.setText("Cadastro de Clientes");
			tabCadCliente.setClosable(true);
		}
		try {
			tabCadCliente.setContent(
					FXMLLoader.load(getClass().getResource("/br/com/unionoffice/view/tela_cad_cliente.fxml")));
			if (!tabPane.getTabs().contains(tabCadCliente)) {
				tabPane.getTabs().add(tabCadCliente);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
}
