package pl.michalherbut.jp.lab05.lab05;

//class Vehicle implements Runnable {
class Vehicle extends Thread {
    private static char nextLabel = 'a';
    private char label;

    public int getSize() {
        return size;
    }

    private int size;

    public char getLabel() {
        return label;
    }

    public int getWeight() {
        return weight;
    }

    private int weight;
    private Bridge bridge;

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    private int position = -1;

    public Vehicle(int size, int weight, Bridge bridge) {
        this.size = size;
        this.weight = weight;
        this.bridge = bridge;
        this.label = nextLabel;
        if (nextLabel == 'z') nextLabel = 'a'; else nextLabel++;
    }

    @Override
    public void run() {
        try {
            // Attempt to enter the bridge
            bridge.enter(this);
            // Move on the bridge
            bridge.move(this);
            // Exit the bridge
            bridge.exit(this);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}