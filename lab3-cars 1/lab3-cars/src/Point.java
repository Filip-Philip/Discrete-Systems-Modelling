import java.util.Random;

public class Point {
    private final float PROBABILITY_OF_RANDOM_SLOWDOWN = 0.1f;
    private final int MAX_SPEED = 5;
    private int type;
    private Point next;
    private boolean moved = false;
    private int speed = 0;

    public Point(){

    }

    public void setMoved(boolean moved) { this.moved = moved; }

    public void setType(int type) { this.type = type; }

    public int getType() { return type; }

    public void setNext(Point next) { this.next = next; }

    public void move() {
        Point car = this;
        for (int i = 0; i < speed; i++) car = car.next;

        if (type == 1 && !moved && car.getType() == 0) {
            type = 0;
            car.setType(1);
            moved = true;
            car.setMoved(true);
        }
    }

    public void accelerate() {
        if (speed < MAX_SPEED) speed++;
    }

    public int getDistanceToNextCar() {
        int distance = 0;
        Point car = this;
        car = car.next;
        while (car.getType() != 1) {
            car = car.next;
            distance++;
        }
        return distance;
    }

    public void slowDown() {
        if (type == 1 && getDistanceToNextCar() < speed) speed = getDistanceToNextCar();
    }

    public void randomlySlowDown() {
        Random random = new Random();
        int bound = (int)(1 / PROBABILITY_OF_RANDOM_SLOWDOWN) + 1;
        if (speed >= 1 && random.nextInt(bound) == 0) {
            speed--;
        }
    }

    public void clicked() {
        type = 1;
    }

    public void clear() {
        type = 0;
    }
}

