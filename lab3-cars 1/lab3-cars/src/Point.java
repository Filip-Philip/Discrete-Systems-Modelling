import java.util.Random;

public class Point {
    public static Integer[] types = {0, 1, 2, 3, 5};
    private final float PROBABILITY_OF_RANDOM_SLOWDOWN = 0.1f;
    private final int MAX_SPEED_TYPE_1 = 3;
    private final int MAX_SPEED_TYPE_2 = 5;
    private final int MAX_SPEED_TYPE_3 = 7;
    private int type;
    private Point nextRight;
    private Point nextLeft;
    private Point previousRight;
    private Point previousLeft;
    private boolean moved = false;
    private int speed = 0;
    private int lane;

    public Point(int lane){
        this.lane = lane;
    }

    public void setMoved(boolean moved) { this.moved = moved; }

    public void setType(int type) { this.type = type; }

    public int getType() { return type; }

    public void setNextRight(Point nextRight) { this.nextRight = nextRight; }

    public void setNextLeft(Point nextLeft) { this.nextLeft = nextLeft; }

    public void setPreviousRight(Point previousRight) { this.previousRight = previousRight; }

    public void setPreviousLeft(Point previousLeft) { this.previousLeft = previousLeft; }

    private void setSpeed(int speed) { this.speed = speed; }

    private boolean isCar() {
        return type == 1 || type == 2 || type == 3;
    }

    public void move() {
        Point car = this;
        for (int i = 0; i < speed; i++) car = car.nextRight;

        if (isCar() && !moved && car.getType() == 0) {
            car.setType(type);
            type = 0;
            moved = true;
            car.setMoved(true);
            car.setSpeed(speed);
        }
    }

    private int getMaxSpeedForType() {
        return switch (type) {
            case 1 -> MAX_SPEED_TYPE_1;
            case 2 -> MAX_SPEED_TYPE_2;
            case 3 -> MAX_SPEED_TYPE_3;
            default -> -1;
        };
    }

    public void overtake() {
        if (!moved && lane == 0 && speed < getMaxSpeedForType() && getDistanceToPreviousCar(0) >= getMaxSpeedOfPreviousCar(0)
            && getDistanceToPreviousCar(1) >= getMaxSpeedOfPreviousCar(1) && getDistanceToNextCar(1) >= speed) {
            Point car = this;
            for (int i = 0; i < speed - 1; i++) car = car.nextLeft;

            if (isCar() && car.getType() == 0) {
                car.setType(type);
                type = 0;
                moved = true;
                car.setMoved(true);
                car.setSpeed(speed + 1);
            }
        }
    }

    public void returnToLane() {
        if (!moved && lane == 1 && getDistanceToPreviousCar(0) >= getMaxSpeedOfPreviousCar(0)
                && getDistanceToPreviousCar(1) >= getMaxSpeedOfPreviousCar(1) && getDistanceToNextCar(0) >= speed) {
            Point car = this;
            for (int i = 0; i < speed - 1; i++) car = car.nextRight;

            if (isCar() && car.getType() == 0) {
                car.setType(type);
                type = 0;
                moved = true;
                car.setMoved(true);
                car.setSpeed(speed);
            }
        }
    }

    public void accelerate() {
        if (type == 1 && speed < MAX_SPEED_TYPE_1) speed++;
        else if (type == 2 && speed < MAX_SPEED_TYPE_2) speed++;
        else if (type == 3 && speed < MAX_SPEED_TYPE_3) speed++;
    }

    private int getMaxSpeedOfPreviousCar(int lane) {
        Point car = this;
        int i = 0;
        if (lane == 0) {
            car = car.previousRight;
            while (!car.isCar() && i <= MAX_SPEED_TYPE_3) {
                car = car.previousRight;
                i++;
            }
        }
        else if (lane == 1) {
            car = car.nextLeft;
            while (!car.isCar() && i <= MAX_SPEED_TYPE_3) {
                car = car.nextLeft;
                i++;
            }
        }
        return car.getMaxSpeedForType();
    }

    private int getDistanceToNextCar(int lane) {
        int distance = 0;
        Point car = this;
        int i = 0;
        if (lane == 0) {
            car = car.nextRight;
            while (!car.isCar() && i <= MAX_SPEED_TYPE_3) {
                car = car.nextRight;
                distance++;
                i++;
            }
        }
        else if (lane == 1) {
            car = car.nextLeft;
            while (!car.isCar() && i <= MAX_SPEED_TYPE_3) {
                car = car.nextLeft;
                distance++;
                i++;
            }
        }
        return distance;
    }

    private int getDistanceToPreviousCar(int lane) {
        int distance = 0;
        Point car = this;
        int i = 0;
        if (lane == 0) {
            car = car.previousRight;
            while (!car.isCar() && i <= MAX_SPEED_TYPE_3) {
                car = car.previousRight;
                distance++;
                i++;
            }
        }
        else if (lane == 1) {
            car = car.previousLeft;
            while (!car.isCar() && i <= MAX_SPEED_TYPE_3) {
                car = car.previousLeft;
                distance++;
                i++;
            }
        }
        return distance;
    }

    public void slowDown() {
        if (isCar() && getDistanceToNextCar(lane) < speed) speed = getDistanceToNextCar(lane);
    }

    public void randomlySlowDown() {
        Random random = new Random();
        int bound = (int)(1 / PROBABILITY_OF_RANDOM_SLOWDOWN) + 1;
        if (speed >= 1 && random.nextInt(bound) == 0) {
            speed--;
        }
    }

    public void clicked() {
        type = 0;
    }

    public void clear() {
        type = 0;
    }
}

