package pl.michalherbut.jp.lab05.lab05;

import javafx.application.Platform;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class BridgeController {
    public HBox carQueue;
    public ImageView bridgeStart;
    public HBox bridge;
    public ImageView bridgeEnd;
    private final int CELL_SIZE = 90;
    public Label capacityField;
    public Label currentLoadField;
    public Label lengthField;

    public void initialize(){
        bridgeStart.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("bridge.png"))));
        bridgeEnd.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("bridge.png"))));
        lengthField.setText("Długość mostu: "+ BridgeSimulator.bridgeLength);
        capacityField.setText("Maksymalne obciążenie mostu: "+ BridgeSimulator.bridgeCapacity);
        currentLoadField.setText("Aktualne obciążenie mostu: 0");

        // Start the simulation
        startSimulation();
    }

    private void startSimulation() {
        BridgeSimulator.main(new String[2]);
        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

        // Schedule a task to update the GUI every second
        scheduler.scheduleAtFixedRate(() -> {
            updateBridgeGUI();
            updateCarQueueGUI();
            updateInfo();
        }, 0, 1, TimeUnit.SECONDS);
    }

    private void updateInfo() {
        Platform.runLater(() -> {
            // Update UI
            currentLoadField.setText("Aktualne obciążenie mostu: "+BridgeSimulator.bridge.getCurrentLoad());
        });
    }

    // Update the GUI to reflect the state of the bridge
    private void updateBridgeGUI() {
        Platform.runLater(() -> {
            bridge.getChildren().clear();
            List<Vehicle> vehicleQueueCopy = new ArrayList<>(BridgeSimulator.bridge.synchronizedList);
            for (Vehicle vehicle : vehicleQueueCopy) {
                StackPane car;
                if (vehicle == null)
                    car = createRoad();
                else
                    car = createCar(vehicle);
                bridge.getChildren().add(car);
            }
        });
    }

    // Update the GUI to reflect the car queue
    private void updateCarQueueGUI() {
        Platform.runLater(() -> {
            carQueue.getChildren().clear();
            List<Thread> vehicleQueueCopy = new ArrayList<>(BridgeSimulator.vehicleQueue);
            Collections.reverse(vehicleQueueCopy);

            for (Thread vehicleThread : vehicleQueueCopy) {
                Vehicle vehicle = (Vehicle) vehicleThread;
                StackPane car = createCar(vehicle);
                carQueue.getChildren().add(car);
            }
        });
    }

    private StackPane createCar(Vehicle vehicle) {

        StackPane carPane = new StackPane();
        carPane.setMinSize(CELL_SIZE, CELL_SIZE);

        // Create ImageView with the car image
        ImageView imageView = new ImageView(new Image(Objects.requireNonNull(getClass().getResourceAsStream("car.jpg"))));
        imageView.setFitWidth(CELL_SIZE);
        imageView.setFitHeight(CELL_SIZE);

        // Create Label with the car label text
        Label carLabel = new Label(vehicle.getLabel()+" "+vehicle.getWeight());
        carLabel.setStyle("-fx-font-size: 20; -fx-text-fill: red;");

        // Add both ImageView and Label to the StackPane
        carPane.getChildren().addAll(imageView, carLabel);

        return carPane;
    }

    private StackPane createRoad() {
        StackPane carPane = new StackPane();
        carPane.setMinSize(CELL_SIZE, CELL_SIZE);

        // Create ImageView with the road image
        ImageView imageView = new ImageView(new Image(Objects.requireNonNull(getClass().getResourceAsStream("road.jpg"))));
        imageView.setFitWidth(CELL_SIZE);
        imageView.setFitHeight(CELL_SIZE);
        carPane.getChildren().addAll(imageView);

        return carPane;
    }
}
