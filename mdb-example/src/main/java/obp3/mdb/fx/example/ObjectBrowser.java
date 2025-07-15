package obp3.mdb.fx.example;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import obp3.fx.objectbrowser.ObjectViewContainer;
import obp3.mdb.simple.semantics.DebugSemantics;
import soup.semantics.base.SoupSemantics;
import soup.syntax.Reader;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;

public class ObjectBrowser extends Application {
    private BorderPane root = new BorderPane();

    @Override
    public void start(Stage primaryStage) throws IOException, ParseException {
        Scene scene = new Scene(root, 600, 400);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Soup Object Browser");

        String modelPath = "../../soup-java/soup-models/alice-bob/";
        var soup = Reader.readSoup(new BufferedReader(new FileReader(modelPath + "alice-bob5.soup")));
        var sem = new SoupSemantics(soup);

        var debug = new DebugSemantics<>(sem);

        root.setCenter(new ObjectViewContainer( debug.initial().getFirst() ).getView());

        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}