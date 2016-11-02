package cpi;


public class DeadBand {
    private static final double DEFAULT_VALUE=0.1;
    
	public DeadBand(String table,String pname){
		band= DEFAULT_VALUE;
	}
	
    public double Value(double InValue){
        double OutValue=0.0;
        if(InValue > band)
        {
            OutValue= (InValue-band)/(1-band);
        }
        else if(InValue <-band){
            
            OutValue= (InValue+band)/(1-band);
        }
        else{
            OutValue=0.0;
        }
        return OutValue;
}
    double band;

}


//SimpleDeadband class
/*
* SimpleDeadband deletes a set of values between -band and band.
* the output is scalled to eliminate a gap in the output. 
* ie the output is zero within the band and linear from zero to one startinfg at the band edge.
* 
* 									Output Value 
* 										| 1	       /
* 										|	      /
* 										|	     /
* 										|	    /
* 										|          /
* 										|	  /
* 										|	 /
* 						________________________________|_______/__________ input value 
* 						       -1          /-band	| band		1
* 							          /		|
* 							         /		|
* 							        /		|
* 							       /		|
* 							      /			|
* 						             /		 	|
* 						            /                 -1|
* 
* 																								
*/