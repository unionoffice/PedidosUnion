package br.com.unionoffice.controller;

import java.io.File;
import java.math.BigDecimal;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import javax.swing.JOptionPane;

import com.sun.javafx.scene.control.behavior.TextAreaBehavior;
import com.sun.javafx.scene.control.skin.TextAreaSkin;

import br.com.unionoffice.dao.ProdutoDao;
import br.com.unionoffice.modelo.Cliente;
import br.com.unionoffice.modelo.Contato;
import br.com.unionoffice.modelo.Produto;
import br.com.unionoffice.util.MaskUtil;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
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
	@FXML
	private TextField tfPreco;
	@FXML
	private TextArea taDescResum;
	@FXML
	private TextArea taDescDet;
	@FXML
	private TableView<Produto> tbProduto;
	@FXML
	private TableColumn<Produto, String> codigoColumn;
	@FXML
	private TableColumn<Produto, String> descricaoColumn;

	private ProdutoDao produtoDao;
	private Produto produto;
	private List<Produto> produtos;
	private ObservableList<Produto> olProdutos;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		try {
			produtoDao = new ProdutoDao();
		} catch (Exception e) {
			e.printStackTrace();
		}

		// ************* btSalvar
		Image imagem = new Image(getClass().getResourceAsStream("/imagens/save_icon.png"));
		btSalvar.setGraphic(new ImageView(imagem));

		// ************* btExcluir
		imagem = new Image(getClass().getResourceAsStream("/imagens/icon_delete.png"));
		btExcluir.setGraphic(new ImageView(imagem));

		// ************* tfCodigo
		MaskUtil.letterField(tfCodigo);

		// ************* tfPreco
		MaskUtil.monetaryField(tfPreco);

		// ************* taDescDet
		taDescDet.addEventFilter(KeyEvent.KEY_PRESSED, (KeyEvent event) -> {
			if (event.getCode() == KeyCode.TAB) {
				TextAreaSkin skin = (TextAreaSkin) taDescDet.getSkin();
				if (skin.getBehavior() instanceof TextAreaBehavior) {
					TextAreaBehavior behavior = (TextAreaBehavior) skin.getBehavior();
					if (event.isControlDown()) {
						behavior.callAction("InsertTab");
					} else if (event.isShiftDown()) {
						behavior.callAction("TraversePrevious");
					} else {
						behavior.callAction("TraverseNext");
					}
					event.consume();
				}
			}
		});

		// ************* taDescResum
		taDescResum.addEventFilter(KeyEvent.KEY_PRESSED, (KeyEvent event) -> {
			if (event.getCode() == KeyCode.TAB) {
				TextAreaSkin skin = (TextAreaSkin) taDescResum.getSkin();
				if (skin.getBehavior() instanceof TextAreaBehavior) {
					TextAreaBehavior behavior = (TextAreaBehavior) skin.getBehavior();
					if (event.isControlDown()) {
						behavior.callAction("InsertTab");
					} else if (event.isShiftDown()) {
						behavior.callAction("TraversePrevious");
					} else {
						behavior.callAction("TraverseNext");
					}
					event.consume();
				}
			}
		});

		// ************* descricaoColumn
		descricaoColumn.setCellValueFactory(new PropertyValueFactory<Produto, String>("descResumida"));
		// ************* codigoColumn
		codigoColumn.setCellValueFactory(new PropertyValueFactory<Produto, String>("codigo"));
		// ************* popula a tabela de produtos
		populaProdutos(produtoDao.listar());

		tbProduto.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
			produto = newSelection;
			popularCampos();
		});
	}

	@FXML
	public void salvar() {
		if (tfCodigo.getText().trim().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Informe o código do produto", "Erro", JOptionPane.ERROR_MESSAGE);
		} else if (tfPreco.getText().trim().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Informe o preço do produto", "Erro", JOptionPane.ERROR_MESSAGE);
		} else if (taDescResum.getText().trim().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Informe a descrição resumida do produto", "Erro",
					JOptionPane.ERROR_MESSAGE);
		} else {
			if (produto == null) {
				produto = new Produto();
			}
			produto.setCodigo(tfCodigo.getText());
			produto.setPreco(new BigDecimal(tfPreco.getText().replace(".", "").replace(",", ".")));
			produto.setDescDetalhada(taDescDet.getText());
			produto.setDescResumida(taDescResum.getText());
			try {
				if (produto.getId() == null) {
					produtoDao.inserirProduto(produto);
				} else {
					produtoDao.alterarProduto(produto);
				}
				limparCampos();
				populaProdutos(produtoDao.listar());
			} catch (Exception e) {
				e.printStackTrace();
				JOptionPane.showMessageDialog(null, "Erro ao inserir o produto:\n" + e.getMessage(), "Erro",
						JOptionPane.ERROR_MESSAGE);
			}
		}
	}

	private void limparCampos() {
		produto = null;
		tfCodigo.clear();
		tfPreco.clear();
		taDescDet.clear();
		taDescResum.clear();
		ivFoto.setImage(null);
		tbProduto.getSelectionModel().clearSelection();
	}

	private void populaProdutos(List<Produto> lista) {
		produtos = lista;
		tbProduto.getItems().clear();
		tbProduto.setItems(olProdutos= FXCollections.observableArrayList(produtos));
	}
	
	private void popularCampos() {
		if (produto != null) {
			tfCodigo.setText(produto.getCodigo());
			tfPreco.setText(produto.getPreco().toString());
			taDescResum.setText(produto.getDescResumida());
			taDescDet.setText(produto.getDescDetalhada());			
		}
	}


	@FXML
	public void excluir() {

	}

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
