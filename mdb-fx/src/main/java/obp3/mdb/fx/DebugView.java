package obp3.mdb.fx;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import obp3.fx.objectbrowser.ObjectViewContainer;
import obp3.fx.objectbrowser.api.ObjectView;
import obp3.fx.objectbrowser.api.ObjectViewFor;
import obp3.mdb.simple.domain.DebugAction;
import obp3.mdb.simple.domain.DebugConfiguration;

import java.util.List;

@ObjectViewFor(DebugConfiguration.class)
public class DebugView implements ObjectView {
    BorderPane gridPane = new BorderPane();
    List<DebugAction> actions;
    @Override
    public String getName() {
        return "DebugView";
    }

    @Override
    public Node getView() {
        return gridPane;
    }

    @Override
    public void setObject(Object object) {
        DebugConfiguration configuration = (DebugConfiguration) object;
        configuration.current.ifPresent(o -> gridPane.setCenter(new ObjectViewContainer(o).getView()));
//        gridPane.setRight(new Label(configuration.options.map(o -> o.r().toString()).orElse("no options")));
        actions = configuration.semantics.actions(configuration);
        var listView = new ListView<>(FXCollections.observableList(actions.stream().map(Object::toString).toList()));

        listView.setOnMouseClicked(new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent event) {
                if (event.getClickCount() == 2 && !listView.getSelectionModel().isEmpty()) {
//                    System.out.println("clicked on " + listView.getSelectionModel().getSelectedItem());

                    var index = listView.getSelectionModel().getSelectedIndex();

                    var targets = configuration.semantics.execute(actions.get(index), configuration);

                    setObject(targets.getFirst());
                }

            }
        });
        gridPane.setBottom(listView);
    }
}
