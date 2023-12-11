package pl.michalherbut.jp.lab05.lab05;

import java.util.*;
import static pl.michalherbut.jp.lab05.lab05.BridgeSimulator.addThread;

public class Bridge {
    private final int length;
    private final int capacity;
    private int currentLoad = 0;
    public List<Vehicle> synchronizedList;

    public Bridge(int length, int capacity) {
        this.length = length;
        this.capacity = capacity;
        Vehicle[] bridge = new Vehicle[length];
        Arrays.fill(bridge,null);
        synchronizedList = Collections.synchronizedList(Arrays.asList(bridge));
        System.out.println(synchronizedList.get(0));
    }

    public void enter(Vehicle vehicle) throws InterruptedException {
        // Trying to enter on the bridge
        while (vehicle.getPosition() == -1){
            System.out.println("próbuję wbić, przyszłą waga: "+(currentLoad + vehicle.getWeight())+", tak czy nie: "+(currentLoad + vehicle.getWeight() <= capacity)+", wjazd: "+synchronizedList.get(0));
            if ((currentLoad + vehicle.getWeight()) <= capacity && synchronizedList.get(0) == null) {

                BridgeSimulator.vehicleQueue.remove();
                vehicle.setPosition(0);
                synchronizedList.set(0,vehicle);
                currentLoad += vehicle.getWeight();
                System.out.println("wbijam \t"+bridgeState());

                BridgeSimulator.vehicleQueue.peek().start();
                addThread(BridgeSimulator.vehicleQueue, this);
            }
            Thread.sleep(1000);
        }
    }

    public void move(Vehicle vehicle) throws InterruptedException {
        // Moving on the bridge
        while (length > vehicle.getPosition() + 1) {
            int currentPosition = vehicle.getPosition();
            int nextPosition = currentPosition+1;
            if (synchronizedList.get(nextPosition) == null) {
                synchronizedList.set(currentPosition, null);
                synchronizedList.set(nextPosition, vehicle);
                vehicle.setPosition(nextPosition);
                System.out.println("ide: "+vehicle.getLabel()+"\t"+bridgeState());
            }
            Thread.sleep(1000);
        }
    }

    public void exit(Vehicle vehicle) throws InterruptedException {
        // Exiting from the bridge
        currentLoad -= vehicle.getWeight();
        synchronizedList.set(vehicle.getPosition(), null);
        System.out.println("nara: \t"+bridgeState(vehicle.getLabel()));
    }

    // Showing bridge in terminal
    private String bridgeState(){
        StringBuilder state= new StringBuilder(queueState() + " [");
        List<Vehicle> copyOfQueue = new ArrayList<>(synchronizedList);
        for (Vehicle c : copyOfQueue) {
            if (c==null)
                state.append('.');
            else
                state.append(c.getLabel());
        }
        return state+"] .\n";
    }
    private String bridgeState(char a){
        StringBuilder state= new StringBuilder(queueState() + "[");
        List<Vehicle> copyOfQueue = new ArrayList<>(synchronizedList);
        for (Vehicle c : copyOfQueue) {
            if (c==null)
                state.append(',');
            else
                state.append(c.getLabel());
        }
        return state+"] "+a+"\n";
    }

    private String queueState(){
        StringBuilder state= new StringBuilder();
        List<Thread> copyOfQueue = new ArrayList<>(BridgeSimulator.vehicleQueue);
        for (Thread c : copyOfQueue) {
            if (c != null && c instanceof Vehicle)
                state.append(new StringBuilder(String.valueOf(c.isAlive())).reverse()).append(" ").append(((Vehicle) c).getWeight()).append(" ").append(((Vehicle) c).getLabel()).append("\n");
        }
        return "obciążenie: " + currentLoad + state.reverse();
    }

    public int getCurrentLoad() {
        return currentLoad;
    }
}