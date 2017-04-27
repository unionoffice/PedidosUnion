package br.com.unionoffice.controller;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

import br.com.unionoffice.util.MaskUtil;
import br.com.unionoffice.util.Validators;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;

public class CadastroProdutoController implements Initializable {
	FileChooser fileChooser;
	File fileImagem;
	Image imagem;

	@FXML
	private TextField tfCodigo;
	@FXML
	private ImageView ivFoto;
	@FXML
	private Button btSalvar;
	@FXML
	private Button btExcluir;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// ************* btSalvar
		Image imagem = new Image(getClass().getResourceAsStream("/imagens/save_icon.png"));
		btSalvar.setGraphic(new ImageView(imagem));

		// ************* btExcluir
		imagem = new Image(getClass().getResourceAsStream("/imagens/icon_delete.png"));
		btExcluir.setGraphic(new ImageView(imagem));

		// ************* tfCodigo
		MaskUtil.letterField(tfCodigo);				

	}

	@FXML
	public void salvar() {

	}

	@FXML
	public void excluir() {

	}

	@FXML
	public void foto() {
		if (fileChooser == null) {
			fileChooser = new FileChooser();
			fileChooser.setTitle("Escolha a foto");
			fileChooser.getExtensionFilters().addAll(new ExtensionFilter("Arquivos de Imagem", "*.png", "*.jpg"));
			fileChooser.setInitialDirectory(new File(System.getProperty("user.home"), "desktop"));
		}
		fileImagem = fileChooser.showOpenDialog(null);
		if (fileImagem != null) {
			exibirImagem(fileImagem);
		}
	}

	private void exibirImagem(File file) {
		imagem = new Image(file.toURI().toString());
		ivFoto.setImage(imagem);
	}

}
