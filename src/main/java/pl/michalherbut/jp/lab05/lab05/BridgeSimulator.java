package pl.michalherbut.jp.lab05.lab05;

import java.util.LinkedList;
import java.util.Queue;

public class BridgeSimulator {

    static Queue<Thread> vehicleQueue;

    public static int bridgeLength=5;
    public static int bridgeCapacity=10;
    static Bridge bridge = new Bridge(bridgeLength, bridgeCapacity);;

    public static void main(String[] args) {
        vehicleQueue = new LinkedList<>();

        for (int i = 0; i < 7; i++)
            addThread(vehicleQueue, bridge);

        Thread thread = vehicleQueue.peek();
        thread.start();

    }

    public static void addThread(Queue vehicleQueue, Bridge bridge) {
        int size = (int) (Math.random() * 5) + 1; // Random size between 1 and 5
        int weight = (int) (Math.random() * 10); // Random weight between 0 and 9

        Vehicle vehicle = new Vehicle(size, weight, bridge);
        vehicleQueue.add(vehicle);
    }
}