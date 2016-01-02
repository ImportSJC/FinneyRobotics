package cpi.auto.old;
// Our goal with this class is to design a generalized autonomous class that can be used for future robot software design
public  class Autonomous_Reference {
	
	public static void setMode(String modeName){
		// Use the joystick controller to set the mode as in the 2015 competition robot
	}
	public static class Modes{
		public static void add(Mode obj){ // Add a new autonomous mode. Example add("Three Tote Pickup"), Add("Grab Barrels")
			
		}
		
	}
	
	private class SuperElement{
		private double value;
		private double testValue;
		private double outputLink;//TODO Change this to an appropriate object
		private double inputLink;//TODO Change this to an appropriate object
		private String condition;
		private String nextState;
		private boolean isTrue(){
			switch(condition){
			case "<":
				if(testValue<inputLink)return true;
				break;
			case ">":
				if(testValue>inputLink)return true;
				break;
			case "<=":
				if(testValue<=inputLink)return true;
				break;
			case ">=":
				if(testValue>=inputLink)return true;
				break;
			case "=":
				if(testValue==inputLink)return true;
				break;
			case "true":
				return true;
			}
			return false;
		}
		
	}
		public class Mode{// Create a new autonomous mode
			public Mode(String name,Element elements[]){// Mode constructor
				
			}
			
			public void add(String stateName,Object stateElement){// Add a state to the mode
				
			}
			
		}
		
		public class Element extends SuperElement { // Elements added to mode to create the state form
			public Element(double value, double output, double input,String condition,String nextState){//TODO output and input must match SuperElement - class needs work
				super.value=value;
				super.inputLink=input;
				super.outputLink=output;
				// ...
			}
			
		}

		// Control conditions
		public static final String LT="<";
		public static final String GT=">";
		public static final String LTE="<=";
		public static final String GTE=">=";
		public static final String EQUALS="=";
		public static final String TRUE="true";
		
	public void init(){ // Make all the modes in this section
		
		
	}
	public void autoInit(){// Initialize the selected autonomous mode
		
	}
	public void autonomous(){// Run the selected autonomous mode
		
	}

}
