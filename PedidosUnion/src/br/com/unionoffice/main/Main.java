package br.com.unionoffice.main;

import java.sql.SQLException;

import br.com.unionoffice.dao.ConnectionFactory;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class Main extends Application {

	@Override
	public void start(Stage stage) throws Exception {
		Parent root = FXMLLoader.load(getClass().getResource("/br/com/unionoffice/view/tela_inicial.fxml"));
		Scene scene = new Scene(root);
		stage.setScene(scene);
		// stage.setFullScreen(true);
		Screen screen = Screen.getPrimary();
		Rectangle2D bounds = screen.getVisualBounds();
		stage.setTitle("Sistema de Pedidos e Orçamentos Union Office");
		stage.setX(bounds.getMinX());
		stage.setY(bounds.getMinY());
		stage.setWidth(bounds.getWidth());
		stage.setHeight(bounds.getHeight());
		stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
			@Override
			public void handle(WindowEvent arg0) {
				try {
					ConnectionFactory.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}

			}
		});
		stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
			@Override
			public void handle(WindowEvent t) {
				Platform.exit();
				System.exit(0);
			}
		});

		stage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}
}
