import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class Point {
	private ArrayList<Point> neighbors;
	private int currentState;
	private int nextState;
	private int numStates = 6;
	private final ArrayList<Integer> STATES_FOR_STAYING_ALIVE = new ArrayList<Integer>(Arrays.asList(2,3));
	private final ArrayList<Integer> STATES_FOR_BECOMING_ALIVE = new ArrayList<Integer>(Arrays.asList(3));
	private final Random random = new Random();

	public Point() {
		currentState = 0;
		nextState = 0;
		neighbors = new ArrayList<Point>();
	}

	public void clicked() {
		currentState=(++currentState)%numStates;	
	}
	
	public int getState() {
		return currentState;
	}

	public void setState(int s) {
		currentState = s;
	}

	public void calculateNewState(boolean rain) {
		if (rain) {
			if (currentState > 0) {
				nextState = currentState - 1;
			}
			else if (neighbors.isEmpty()) {
				nextState = currentState;
			}
			else if (currentState == 0 && neighbors.get(0).getState() > 0) {
				nextState = 6;
			}
		}
		else {
			if (this.getState() == 0 && STATES_FOR_BECOMING_ALIVE.contains(neighborsAlive()) ||
					this.getState() == 1 && STATES_FOR_STAYING_ALIVE.contains(neighborsAlive())) {
				nextState = 1;
			} else {
				nextState = 0;
			}
		}
	}

	public void changeState() {
		currentState = nextState;
	}
	
	public void addNeighbor(Point nei) {
		neighbors.add(nei);
	}

	public int neighborsAlive() {
		int counter = 0;
		for(Point neighbor : neighbors) {
			counter += neighbor.getState();
		}
		return counter;
	}

	public void drop() {
		if (random.nextInt(20) == 0) {
			setState(6);
		}
	}
}
