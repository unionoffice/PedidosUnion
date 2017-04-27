package br.com.unionoffice.controller;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;

public class TelaInicialController implements Initializable {
	private Tab tabCadCliente;
	private Tab tabCadProduto;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {				
	}

	@FXML
	private TabPane tabPane;

	@FXML
	protected void itSairClick(ActionEvent event) {
		Platform.exit();
		System.exit(0);
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

	@FXML
	protected void itCadProdutoClick(ActionEvent event) {
		if (tabCadProduto == null) {
			tabCadProduto = new Tab();
			tabCadProduto.setText("Cadastro de Produtos");
			tabCadProduto.setClosable(true);
		}
		try {
			tabCadProduto.setContent(
					FXMLLoader.load(getClass().getResource("/br/com/unionoffice/view/tela_cad_produto.fxml")));
			if (!tabPane.getTabs().contains(tabCadProduto)) {
				tabPane.getTabs().add(tabCadProduto);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
