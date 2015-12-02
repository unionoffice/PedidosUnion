package br.com.unionoffice.controller;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import javax.swing.JOptionPane;

import br.com.unionoffice.dao.CidadeDao;
import br.com.unionoffice.dao.RepresentanteDao;
import br.com.unionoffice.modelo.Estado;
import br.com.unionoffice.modelo.Representante;
import br.com.unionoffice.modelo.TipoCliente;
import br.com.unionoffice.service.BuscaCep;
import br.com.unionoffice.service.CepServiceVO;
import br.com.unionoffice.util.AutoCompleteComboBoxListener;
import br.com.unionoffice.util.MaskUtil;
import br.com.unionoffice.util.Validators;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class CadastroClienteController implements Initializable {
	@FXML
	private ComboBox<TipoCliente> cbTipoCliente;
	@FXML
	private Label lbDocumento;
	@FXML
	private TextField tfDocumento;
	@FXML
	private CheckBox chkNaoInformado;
	@FXML
	private Label lbDocumento2;
	@FXML
	private TextField tfDocumento2;
	@FXML
	private Label lbNomeRazao;
	@FXML
	private TextField tfNomeRazao;
	@FXML
	private TextField tfEndereco;
	@FXML
	private TextField tfNumero;
	@FXML
	private TextField tfComplemento;
	@FXML
	private TextField tfBairro;
	@FXML
	private ComboBox<Estado> cbEstado;
	@FXML
	private ComboBox<String> cbCidade;
	@FXML
	private ComboBox<Representante> cbRepresentante;
	@FXML
	private TextField tfCep;
	@FXML
	private Button btSalvar;
	@FXML
	private Button btExcluir;
	private CidadeDao cidadeDao;
	private RepresentanteDao representanteDao;
	private AutoCompleteComboBoxListener<String> autoComplete;

	@Override
	public void initialize(URL url, ResourceBundle bundle) {
		autoComplete = new AutoCompleteComboBoxListener<String>();
		try {
			cidadeDao = new CidadeDao();
			representanteDao = new RepresentanteDao();
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Erro ao abrir a daoCidade:" + e.getMessage());
		}

		// ************* cbTipoCliente
		cbTipoCliente.getItems().setAll(TipoCliente.values());
		cbTipoCliente.valueProperty().addListener(new ChangeListener<TipoCliente>() {
			@Override
			public void changed(ObservableValue<? extends TipoCliente> arg0, TipoCliente oldValue,
					TipoCliente newValue) {
				if (newValue == TipoCliente.PESSOA_FISICA) {
					lbDocumento.setText("CPF:");
					lbDocumento2.setText("RG:");
					lbNomeRazao.setText("Nome:");
				} else {
					lbDocumento.setText("CNPJ:");
					lbDocumento2.setText("Inscrição Estadual:");
					lbNomeRazao.setText("Razão Social:");
				}
			}
		});
		// ************* tfDocumento
		MaskUtil.cpfCnpjField(tfDocumento);
		tfDocumento.setTooltip(new Tooltip("Digite apenas números"));
		tfDocumento.focusedProperty().addListener(new ChangeListener<Boolean>() {
			@Override
			public void changed(ObservableValue<? extends Boolean> arg0, Boolean oldPropertyValue,
					Boolean newPropertyValue) {
				if (oldPropertyValue) {
					Validators.validaDocumento(tfDocumento);
				}
			}
		});
		// ************* chkNaoInformado
		chkNaoInformado.selectedProperty().addListener(new ChangeListener<Boolean>() {
			@Override
			public void changed(ObservableValue<? extends Boolean> arg0, Boolean oldPropertyValue,
					Boolean newPropertyValue) {
				if (newPropertyValue) {
					tfDocumento.setDisable(true);
					tfDocumento.setVisible(false);
				} else {
					tfDocumento.setDisable(false);
					tfDocumento.setVisible(true);
				}
			}
		});
		// ************* tfCep
		MaskUtil.cepField(tfCep);
		tfCep.focusedProperty().addListener(new ChangeListener<Boolean>() {
			@Override
			public void changed(ObservableValue<? extends Boolean> arg0, Boolean oldPropertyValue,
					Boolean newPropertyValue) {
				if (oldPropertyValue) {
					CepServiceVO cep = null;
					if (tfCep.getText().length() == 8) {
						cep = BuscaCep.buscar(tfCep.getText());
					}
					if (cep != null) {
						tfEndereco.setText(cep.getTipo_logradouro() + ": " + cep.getLogradouro());
						tfBairro.setText(cep.getBairro());
						cbEstado.getSelectionModel().select((Estado) (Enum.valueOf(Estado.class, cep.getUf())));
						cbCidade.getSelectionModel().select(cep.getCidade());
					}
				}
			}
		});

		// ************* cbEstado
		cbEstado.getItems().setAll(Estado.values());
		cbEstado.valueProperty().addListener(new ChangeListener<Estado>() {
			@Override
			public void changed(ObservableValue<? extends Estado> arg0, Estado oldValue, Estado newValue) {
				try {
					cbCidade.getItems().setAll(cidadeDao.getCidades(newValue));
					autoComplete.setComboBox(cbCidade);
				} catch (SQLException e) {
					JOptionPane.showMessageDialog(null, "Erro ao buscar as cidades: " + e.getMessage());
				}

			}
		});
		// ************* cbCidade

		// ************* cbRepresentante
		try {
			cbRepresentante.getItems().setAll(representanteDao.listar());
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "Erro ao buscar os representantes: " + e.getMessage());
		}

		// ************* btSalvar
		Image imagem = new Image(getClass().getResourceAsStream("/imagens/save_icon.png"));
		btSalvar.setGraphic(new ImageView(imagem));

		// ************* btExcluir
		imagem = new Image(getClass().getResourceAsStream("/imagens/icon_delete.png"));
		btExcluir.setGraphic(new ImageView(imagem));
	}

}
