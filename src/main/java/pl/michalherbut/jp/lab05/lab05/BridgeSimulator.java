package pl.michalherbut.jp.lab05.lab05;

import java.util.LinkedList;
import java.util.Queue;

public class BridgeSimulator {
    public static void main(String[] args) {
        Bridge bridge = new Bridge(5, 10);
//        Queue<Thread> vehicleQueue = new LinkedList<>();

        for (int i = 0; i < 7; i++) {
            int size = (int) (Math.random() * 5) + 1; // Random size between 1 and 5
            int weight = (int) (Math.random() * 10); // Random weight between 0 and 9

            Vehicle vehicle = new Vehicle(size, weight, bridge);
            Thread vehicleThread = new Thread(vehicle);

            // Add the thread to the queue and start it
//            vehicleQueue.add(vehicleThread);
            vehicleThread.start();
        }
    }
}