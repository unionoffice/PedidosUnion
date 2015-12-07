package br.com.unionoffice.controller;

import java.awt.Event;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javax.swing.JOptionPane;

import br.com.unionoffice.dao.CidadeDao;
import br.com.unionoffice.dao.ClienteDao;
import br.com.unionoffice.dao.RepresentanteDao;
import br.com.unionoffice.modelo.Cliente;
import br.com.unionoffice.modelo.Contato;
import br.com.unionoffice.modelo.Estado;
import br.com.unionoffice.modelo.Representante;
import br.com.unionoffice.modelo.TipoCliente;
import br.com.unionoffice.service.BuscaCep;
import br.com.unionoffice.service.CepServiceVO;
import br.com.unionoffice.util.AutoCompleteComboBoxListener;
import br.com.unionoffice.util.MaskUtil;
import br.com.unionoffice.util.Validators;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.control.cell.PropertyValueFactory;
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
	@FXML
	private TableView<Contato> tableContatos;
	@FXML
	private TableColumn<Contato, String> nomeContatoColumn;
	@FXML
	private TableColumn<Contato, String> telefoneContatoColumn;
	@FXML
	private TableColumn<Contato, String> emailContatoColumn;
	@FXML
	private TextField tfContato;
	@FXML
	private TextField tfTelefone;
	@FXML
	private TextField tfDepartamento;
	@FXML
	private TextField tfEmailContato;
	@FXML
	private TextField tfEmail;
	@FXML
	private TextArea taObservacoes;

	private CidadeDao cidadeDao;
	private ClienteDao clienteDao;
	private RepresentanteDao representanteDao;
	private AutoCompleteComboBoxListener<String> autoComplete;
	private Cliente cliente;
	private List<Contato> contatos = new ArrayList<Contato>();
	private ObservableList<Contato> olContatos;

	@Override
	public void initialize(URL url, ResourceBundle bundle) {
		autoComplete = new AutoCompleteComboBoxListener<String>();
		try {
			cidadeDao = new CidadeDao();
			clienteDao = new ClienteDao();
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
		// ************* nomeContatoColumn
		nomeContatoColumn.setCellValueFactory(new PropertyValueFactory<Contato, String>("nome"));
		// ************* telefoneContatoColumn
		telefoneContatoColumn.setCellValueFactory(new PropertyValueFactory<Contato, String>("telefone"));
		// ************* emailContatoColumn
		emailContatoColumn.setCellValueFactory(new PropertyValueFactory<Contato, String>("email"));
		// ************* tableContatos
		tableContatos.setItems(olContatos = FXCollections.observableArrayList(contatos));

	}

	public void salvar() {
		if (cbTipoCliente.getSelectionModel().getSelectedIndex() < 0) {
			JOptionPane.showMessageDialog(null, "Informe o tipo do cliente", "Erro", JOptionPane.ERROR_MESSAGE);
		} else if (tfDocumento.getText().trim().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Informe o CPF/CNPJ do cliente", "Erro", JOptionPane.ERROR_MESSAGE);
			tfDocumento.requestFocus();
		} else if ((tfDocumento.getText().length() > 14
				&& cbTipoCliente.getSelectionModel().getSelectedItem() == TipoCliente.PESSOA_FISICA)
				|| (tfDocumento.getText().length() <= 14
						&& cbTipoCliente.getSelectionModel().getSelectedItem() == TipoCliente.PESSOA_JURIDICA)) {
			JOptionPane.showMessageDialog(null, "Documento informado em desacordo com o tipo do cliente", "Erro",
					JOptionPane.ERROR_MESSAGE);
			tfDocumento.requestFocus();
		} else if (tfNomeRazao.getText().trim().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Informe o Nome/Razão Social do cliente", "Erro",
					JOptionPane.ERROR_MESSAGE);
			tfNomeRazao.requestFocus();
		} else if (tfCep.getText().trim().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Informe o CEP do cliente", "Erro", JOptionPane.ERROR_MESSAGE);
			tfCep.requestFocus();
		} else if (tfEndereco.getText().trim().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Informe o endereço do cliente", "Erro", JOptionPane.ERROR_MESSAGE);
			tfEndereco.requestFocus();
		} else if (tfNumero.getText().trim().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Informe o número do endereço do cliente", "Erro",
					JOptionPane.ERROR_MESSAGE);
			tfNumero.requestFocus();
		} else if (tfBairro.getText().trim().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Informe o bairro do cliente", "Erro", JOptionPane.ERROR_MESSAGE);
			tfBairro.requestFocus();
		} else if (cbEstado.getSelectionModel().getSelectedIndex() < 0) {
			JOptionPane.showMessageDialog(null, "Informe o estado do cliente", "Erro", JOptionPane.ERROR_MESSAGE);
		} else if (cbCidade.getSelectionModel().getSelectedIndex() < 0) {
			JOptionPane.showMessageDialog(null, "Informe a cidade do cliente", "Erro", JOptionPane.ERROR_MESSAGE);
		} else {
			if (cliente == null) {
				cliente = new Cliente();
			}
			cliente.setTipo(cbTipoCliente.getSelectionModel().getSelectedItem());
			if (chkNaoInformado.isSelected()) {
				cliente.setCpfCnpj(null);
			}else{
				cliente.setCpfCnpj(tfDocumento.getText());
			}
			cliente.setRgIe(tfDocumento2.getText());
			cliente.setNomeRazaoSocial(tfNomeRazao.getText().trim());
			cliente.setCep(tfCep.getText());
			cliente.setEndereco(tfEndereco.getText());
			cliente.setNumero(tfNumero.getText());
			cliente.setComplemento(tfComplemento.getText());
			cliente.setBairro(tfBairro.getText());
			cliente.setEstado(cbEstado.getSelectionModel().getSelectedItem());
			cliente.setCidade(cbCidade.getSelectionModel().getSelectedItem().toString());
			cliente.setEmail(tfEmail.getText().trim());
			cliente.setRepresentante(cbRepresentante.getSelectionModel().getSelectedItem());
			cliente.setObservacoes(taObservacoes.getText());
			cliente.setContatos(olContatos);
			try {
				clienteDao.inserirCliente(cliente);	
			} catch (Exception e) {
				JOptionPane.showMessageDialog(null, "Erro ao inserir o cliente:\n"+e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
			}
			
		}
	}

	public void addContato() {
		if (tfContato.getText().trim().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Informe o nome do contato", "Erro", JOptionPane.ERROR_MESSAGE);
			tfContato.requestFocus();
		} else {
			Contato c = new Contato();
			c.setNome(tfContato.getText().trim());
			c.setDepartamento(tfDepartamento.getText().trim());
			c.setTelefone(tfTelefone.getText().trim());
			c.setEmail(tfEmailContato.getText().trim());
			olContatos.add(c);
			limparFormContato();
		}
	}

	public void remContato() {
		int indice = tableContatos.getSelectionModel().getSelectedIndex();
		if (indice >= 0) {
			olContatos.remove(indice);
		}
	}

	private void limparFormContato() {
		tfContato.clear();		
		tfDepartamento.clear();
		tfTelefone.clear();
		tfEmailContato.clear();
	}
}
