package br.com.unionoffice.util;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;

public abstract class MaskUtil {

	/**
	 * Monta a mascara para Data (dd/MM/yyyy).
	 *
	 * @param textField
	 *            TextField
	 */
	public static void dateField(final TextField textField) {
		maxField(textField, 10);

		textField.lengthProperty().addListener(new ChangeListener<Number>() {
			@Override
			public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
				if (newValue.intValue() < 11) {
					String value = textField.getText();
					value = value.replaceAll("[^0-9]", "");
					value = value.replaceFirst("(\\d{2})(\\d)", "$1/$2");
					value = value.replaceFirst("(\\d{2})\\/(\\d{2})(\\d)", "$1/$2/$3");
					textField.setText(value);
					positionCaret(textField);
				}
			}
		});
	}

	/**
	 * Campo que aceita somente numericos.
	 *
	 * @param textField
	 *            TextField
	 */
	public static void numericField(final TextField textField) {
		textField.lengthProperty().addListener(new ChangeListener<Number>() {
			@Override
			public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
				if (newValue.intValue() > oldValue.intValue()) {
					char ch = textField.getText().charAt(oldValue.intValue());
					if (!(ch >= '0' && ch <= '9')) {
						textField.setText(textField.getText().substring(0, textField.getText().length() - 1));
					}
				}
			}
		});
	}

	/**
	 * Campo que aceita somente letras e ponto.
	 *
	 * @param textField
	 *            TextField
	 */
	public static void letterField(final TextField textField) {
		textField.textProperty().addListener((ov, oldValue, newValue) -> {
			textField.setText(newValue.toUpperCase());
			if (newValue.length() > oldValue.length()) {
				char ch = textField.getText().charAt(oldValue.length());
				if (!(ch >= 'A' && ch <= 'Z') && ch != '.') {
					textField.setText(textField.getText().substring(0, textField.getText().length() - 1));
				}
			}

		});
	}

	/**
	 * Monta a mascara para Moeda.
	 *
	 * @param textField
	 *            TextField
	 */
	public static void monetaryField(final TextField textField) {
		textField.setAlignment(Pos.CENTER_RIGHT);
		textField.lengthProperty().addListener(new ChangeListener<Number>() {
			@Override
			public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
				String value = textField.getText();
				value = value.replaceAll("[^0-9]", "");
				value = value.replaceAll("([0-9]{1})([0-9]{14})$", "$1.$2");
				value = value.replaceAll("([0-9]{1})([0-9]{11})$", "$1.$2");
				value = value.replaceAll("([0-9]{1})([0-9]{8})$", "$1.$2");
				value = value.replaceAll("([0-9]{1})([0-9]{5})$", "$1.$2");
				value = value.replaceAll("([0-9]{1})([0-9]{2})$", "$1,$2");
				textField.setText(value);
				positionCaret(textField);

				textField.textProperty().addListener(new ChangeListener<String>() {
					@Override
					public void changed(ObservableValue<? extends String> observableValue, String oldValue,
							String newValue) {
						if (newValue.length() > 17)
							textField.setText(oldValue);
					}
				});
			}
		});

		textField.focusedProperty().addListener(new ChangeListener<Boolean>() {
			@Override
			public void changed(ObservableValue<? extends Boolean> observableValue, Boolean aBoolean,
					Boolean fieldChange) {
				if (!fieldChange) {
					final int length = textField.getText().length();
					if (length > 0 && length < 3) {
						textField.setText(textField.getText() + "00");
					}
				}
			}
		});
	}

	/**
	 * Monta as mascara para CPF/CNPJ. A mascara eh exibida somente apos o campo
	 * perder o foco.
	 *
	 * @param textField
	 *            TextField
	 */
	public static void cpfCnpjField(final TextField textField) {

		textField.setOnKeyTyped(new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent event) {
				if (!Character.isDigit(event.getCharacter().charAt(0))) {
					event.consume();
				}
			}
		});

		textField.focusedProperty().addListener(new ChangeListener<Boolean>() {

			@Override
			public void changed(ObservableValue<? extends Boolean> observableValue, Boolean aBoolean,
					Boolean fieldChange) {
				String value = textField.getText();
				if (!fieldChange) {
					if (textField.getText().length() == 11) {
						value = value.replaceAll("[^0-9]", "");
						value = value.replaceFirst("([0-9]{3})([0-9]{3})([0-9]{3})([0-9]{2})$", "$1.$2.$3-$4");
					}
					if (textField.getText().length() == 14) {
						value = value.replaceAll("[^0-9]", "");
						value = value.replaceFirst("([0-9]{2})([0-9]{3})([0-9]{3})([0-9]{4})([0-9]{2})$",
								"$1.$2.$3/$4-$5");
					}
				}
				textField.setText(value);
				if (textField.getText() != value) {
					textField.setText("");
					textField.insertText(0, value);
				}

			}
		});

		maxField(textField, 18);
	}

	/**
	 * Monta a mascara para os campos CNPJ.
	 *
	 * @param textField
	 *            TextField
	 */
	public static void cnpjField(final TextField textField) {
		maxField(textField, 18);

		textField.lengthProperty().addListener(new ChangeListener<Number>() {
			@Override
			public void changed(ObservableValue<? extends Number> observableValue, Number number, Number number2) {
				String value = textField.getText();
				value = value.replaceAll("[^0-9]", "");
				value = value.replaceFirst("(\\d{2})(\\d)", "$1.$2");
				value = value.replaceFirst("(\\d{2})\\.(\\d{3})(\\d)", "$1.$2.$3");
				value = value.replaceFirst("\\.(\\d{3})(\\d)", ".$1/$2");
				value = value.replaceFirst("(\\d{4})(\\d)", "$1-$2");
				textField.setText(value);
				positionCaret(textField);
			}
		});

	}

	public static void cepField(TextField textField) {
		maxField(textField, 8);
		numericField(textField);
	}

	/**
	 * Devido ao incremento dos caracteres das mascaras eh necessario que o
	 * cursor sempre se posicione no final da string.
	 *
	 * @param textField
	 *            TextField
	 */
	private static void positionCaret(final TextField textField) {
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				// Posiciona o cursor sempre a direita.
				textField.positionCaret(textField.getText().length());
			}
		});
	}

	/**
	 * @param textField
	 *            TextField.
	 * @param length
	 *            Tamanho do campo.
	 */
	private static void maxField(final TextField textField, final Integer length) {
		textField.textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observableValue, String oldValue, String newValue) {
				if (newValue.length() > length)
					textField.setText(oldValue);
			}
		});
	}

}