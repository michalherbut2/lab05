module pl.michalherbut.jp.lab05.lab05 {
    requires javafx.controls;
    requires javafx.fxml;


    opens pl.michalherbut.jp.lab05.lab05 to javafx.fxml;
    exports pl.michalherbut.jp.lab05.lab05;
}