public class Point {

	public Point nNeighbor;
	public Point wNeighbor;
	public Point eNeighbor;
	public Point sNeighbor;
	public float nVel;
	public float eVel;
	public float wVel;
	public float sVel;
	public float pressure;
	private final float cSquared = 0.5f;
	public static Integer []types ={0,1,2};
	public int type;
	private int sinInput = 0;
	private int sinInputStepChange = 30;

	public Point() {
		clear();
		type = 0;
	}

	public void clicked() {
		pressure = 1;
	}
	
	public void clear() {
		// TODO: clear velocity and pressure
		nVel = 0;
		eVel = 0;
		wVel = 0;
		sVel = 0;
		pressure = 0;
	}

	public void updateVelocity() {
		// TODO: velocity update
		if (type == 0) {
			nVel -= nNeighbor.pressure - pressure;
			sVel -= sNeighbor.pressure - pressure;
			eVel -= eNeighbor.pressure - pressure;
			wVel -= wNeighbor.pressure - pressure;
		}
	}

	public void updatePresure() {
		// TODO: pressure update
		if (type == 0) {
			pressure -= cSquared * (nVel + sVel + eVel + wVel);
		}
		else if (type == 2) {
			double radians = Math.toRadians(sinInput);
			pressure = (float) (Math.sin(radians));
			updateSinInput();
		}
	}

	public float getPressure() {
		return pressure;
	}

	private void updateSinInput() {
		sinInput += sinInputStepChange;
	}
}