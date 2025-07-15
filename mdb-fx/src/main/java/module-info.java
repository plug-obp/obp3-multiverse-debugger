import obp3.mdb.fx.DebugView;

module obp.mdb.fx {
    requires javafx.controls;
    requires obp.fx.objectbrowser.api;
    requires obp.fx.objectbrowser;
    requires obp.mdb.semantics;
    requires obp.algos;
    requires java.desktop;
    provides obp3.fx.objectbrowser.api.ObjectView with DebugView;
    exports obp3.mdb.fx;
}