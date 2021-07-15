
import java.util.ArrayList;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class Assignment4 extends Application {

    public static void main(String[] args) {

        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {

        MapObject map = new MapObject();
        Group root = map.getRoot();

        Scene scene = new Scene(root, 600, 500, Color.GREY);

        ArrayList<String> input = new ArrayList<>();//while we press the up button
        ArrayList<String> input2 = new ArrayList<>();//when we released the up button slow down
        scene.setOnKeyPressed((KeyEvent e) -> {
            String code = e.getCode().toString();
            if (!input.contains(code)) {
                input.add(code);
                input2.remove(code);
            }
            if (code.equals("ENTER")) {
                if (map.isRunning() == false) {
                    input.clear();
                    input2.clear();
                    map.restart(input, input2);
                }

            }
        });
        scene.setOnKeyReleased((KeyEvent e) -> {
            String code = e.getCode().toString();
            input.remove(code);
            input2.add(code);
        });

        map.Animation(input, input2);

        stage.setScene(scene);
        stage.setTitle("HUBBM");
        stage.show();

    }

}
