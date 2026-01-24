package utils;

import app.binpacking.App;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TextField;

public class UIUtils {

    public static void enforceNumeric(TextField field) {
        field
            .textProperty()
            .addListener(
                    (_, _, newValue) -> {
                        if (!newValue.matches("\\d*")) {
                            field.setText(newValue.replaceAll("\\D", ""));
                        }
                    }
            );
    }

    public static <T> T loadComponent(String component) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(component + ".fxml"));
            fxmlLoader.load();
            return fxmlLoader.getRoot();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }
}
