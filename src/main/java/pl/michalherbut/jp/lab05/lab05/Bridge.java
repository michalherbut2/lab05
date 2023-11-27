package pl.michalherbut.jp.lab05.lab05;

import java.util.Arrays;
import java.util.concurrent.Semaphore;

class Bridge {
    private final int length;
    private final int capacity;
    private int currentLoad = 0;

    private char[] bridge;

    public Bridge(int length, int capacity) {
        this.length = length;
        this.capacity = capacity;
        bridge = new char[length];
        Arrays.fill(bridge,'X');
    }

    public void enter(Vehicle vehicle) throws InterruptedException {
        while (vehicle.getPosition() == -1){
//            System.out.println("prubuję wbić");
            if (currentLoad + vehicle.getWeight() <= capacity && bridge[0] == 'X') {
                currentLoad += vehicle.getWeight();
                vehicle.setPosition(0);
                bridge[0] = vehicle.getLabel();
                System.out.println("wbijam \t"+bridgeState());
            }
            Thread.sleep(1000);
        }
    }

    public void move(Vehicle vehicle) throws InterruptedException {
        // Moving on the bridge, nothing to be done here
        while (length > vehicle.getPosition() + 1) {
            int currentPosition = vehicle.getPosition();
            int nextPosition = currentPosition+1;
            if (bridge[nextPosition] == 'X') {
                bridge[currentPosition] = 'X';
                bridge[nextPosition] = vehicle.getLabel();
                vehicle.setPosition(nextPosition);
                System.out.println("ide: \t"+bridgeState());

            }
            Thread.sleep(1000);
        }
    }

    public void exit(Vehicle vehicle) {
        currentLoad -= vehicle.getWeight();
        bridge[vehicle.getPosition()] = 'X';
        System.out.println("nara: \t"+bridgeState(vehicle.getLabel()));

    }
    private String bridgeState(){
        StringBuilder state= new StringBuilder(currentLoad + " [");
        for (char c:bridge) {
            state.append(c);
        }
        return state+"] .";
    }
    private String bridgeState(char a){
        StringBuilder state= new StringBuilder(currentLoad + "[");
        for (char c:bridge) {
            state.append(c);
        }
        return state+"] "+a;
    }
}