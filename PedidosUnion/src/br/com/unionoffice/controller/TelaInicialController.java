package br.com.unionoffice.controller;



import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;

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
