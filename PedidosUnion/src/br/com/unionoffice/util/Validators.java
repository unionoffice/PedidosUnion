package br.com.unionoffice.util;

import javafx.scene.control.TextField;

public class Validators {
	public static void validaDocumento(TextField textField) {
		boolean valido = true;
		String documento = textField.getText().trim().replace(".", "").replace("/", "").replace("-", "");
		if (documento.length() != 14 && documento.length() != 11) {
			valido = false;
		} else {
			if (documento.length() == 14) {
				valido = validaCnpj(documento);
			} else {
				valido = validaCpf(documento);
			}

		}

		if (valido) {
			textField.setStyle("-fx-control-inner-background: #00ff00");
		} else {
			textField.requestFocus();
			textField.setStyle("-fx-control-inner-background: #ff0000");
		}
	}

	private static boolean validaCnpj(String cnpj) {
		int dig1, dig2, soma, peso;
		if (cnpj.equals("00000000000000") || cnpj.equals("11111111111111") || cnpj.equals("22222222222222")
				|| cnpj.equals("33333333333333") || cnpj.equals("44444444444444") || cnpj.equals("55555555555555")
				|| cnpj.equals("66666666666666") || cnpj.equals("77777777777777") || cnpj.equals("88888888888888")
				|| cnpj.equals("99999999999999")) {
			return false;
		}
		String cnpjSub = cnpj.substring(0, 12);
		peso = 2;
		soma = 0;
		for (int i = 11; i >= 0; i--) {
			int caractere = cnpjSub.charAt(i) - 48;
			soma += caractere * peso++;
			if (peso == 10) {
				peso = 2;
			}
		}
		dig1 = soma % 11 <= 1 ? 0 : 11 - soma % 11;
		cnpjSub += dig1;
		peso = 2;
		soma = 0;
		for (int i = 12; i >= 0; i--) {
			int caractere = cnpjSub.charAt(i) - 48;
			soma += caractere * peso++;
			if (peso == 10) {
				peso = 2;
			}
		}
		dig2 = soma % 11 <= 1 ? 0 : 11 - soma % 11;
		cnpjSub += dig2;
		if (cnpjSub.substring(12).equals(cnpj.substring(12))) {
			return true;
		} else {
			return false;
		}
	}

	private static boolean validaCpf(String cpf) {
		if (cpf.equals("00000000000") || cpf.equals("11111111111") || cpf.equals("22222222222")
				|| cpf.equals("33333333333") || cpf.equals("44444444444") || cpf.equals("55555555555")
				|| cpf.equals("66666666666") || cpf.equals("77777777777") || cpf.equals("88888888888")
				|| cpf.equals("99999999999")) {
			return false;
		}
		int dig1, dig2, soma, peso;
		String cpfSub = cpf.substring(0, 9);
		peso = 2;
		soma = 0;
		for (int i = 8; i >= 0; i--) {
			int caractere = cpfSub.charAt(i) - 48;
			soma += caractere * peso++;

		}
		dig1 = soma % 11 <= 1 ? 0 : 11 - soma % 11;
		cpfSub += dig1;
		peso = 2;
		soma = 0;
		for (int i = 9; i >= 0; i--) {
			int caractere = cpfSub.charAt(i) - 48;
			soma += caractere * peso++;
		}
		dig2 = soma % 11 <= 1 ? 0 : 11 - soma % 11;
		cpfSub += dig2;
		if (cpfSub.substring(9).equals(cpf.substring(9))) {
			return true;
		} else {
			return false;
		}
	}
}
