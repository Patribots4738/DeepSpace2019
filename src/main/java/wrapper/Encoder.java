package wrapper;

public class Encoder {
	
	public edu.wpi.first.wpilibj.Encoder encoder; //this begins the creation of the lower level encoder object, we get the raw data from it and make it more usable in this class
	double wheelRadius; //this is the radius of the wheels, in inches
	double clicksPerRotation; //and this is the encoder's clicks per rotation
	double circumference; //this is the circumference of the wheel (in inches), it's important later
	double distancePerClick; //we multiply this by the number of clicks to find distance
	boolean isInverted;
	
		//do not tinker with any of the above values, they are set in the constructor below, this bit above just establishes them as global variables
		//also please don't control+shift+f this document, it'll mess up all these lovely comments
	
	public Encoder(int port1, int port2, double wheelRadius, double clicksPerRotation, boolean isInverted) {
		encoder = new edu.wpi.first.wpilibj.Encoder(port1, port2); // this finishes creating the lower level encoder object
		
		reset();//this resets the encoder, just to be sure we are starting fresh, to look at exactly what it does, this function is from this class, just scroll down!
		
		this.wheelRadius = wheelRadius;//these two lines set these two to be real-world values, you measured them right?
		this.clicksPerRotation = clicksPerRotation; //well don't try to measure this one, but you should know it, if not look up the name of the encoders we're using, it should be easy to find
		
		circumference = (wheelRadius * 2) * Math.PI;  //these two lines are more setting to real-world values, this one here is just the formula for circumference with the wheelRadius put in
		distancePerClick = circumference / clicksPerRotation; //this divides the circumference of the wheel by the clicks per rotation, so we know how far a click is in distance (inches) 
		
		encoder.setDistancePerPulse(distancePerClick);//this tells the encoder how far a click is, we could use to find distance, but it can be finicky, so we'll try to use the raw number
		//of clicks if we can, but some functions require it
		
		this.isInverted = isInverted;
		
		
		/*
		encoder.setReverseDirection(isInverted); //because the encoders are facing opposite directions on the right and left sides, we have to tell one side's encoders to swap directions
		//in this case it's the right side, there is a reason for this, but I forget what it is, ask your lead.
		*/
	}
	
	
	public double getDistance() {
		
		double distance = encoder.get() * distancePerClick; //this multiplies the clicks to distance from above by the number of clicks the encoder has counted, giving us distance
		if(isInverted) {
			
			return -distance;
			
		}
		return distance;
		
	}
	
	public double getSpeed() {
		
		double speed = encoder.getRate();//this gets the rate of the encoder's motion, this is an example of a function that requires the setDistancePerPulse thing from the constructor
		
		if(isInverted) {
			
			return -speed;
			
		}
		
		return speed;
		
	}
	
	public double getClickCount() {
		
		double clicks = encoder.get();
		
		return clicks;
		
	}
	
	public void reset() {
		
		encoder.reset(); //this resets the encoder, essentially setting it's count of how many times it's clicked to zero
		
	}
	
}
