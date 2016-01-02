package cpi;


public class DeadBand {
    private static final double DEFAULT_VALUE=0.1;
    
	public DeadBand(String table,String pname){
		band=new SetDouble(table+DEADBAND_TABLE_NAME,pname , DEFAULT_VALUE);
		name=pname;
	}
	/*public DeadBand(java.lang.String pname,double pdeadBand){
		band=new SetDouble(DEADBAND_TABLE_NAME, pname, pdeadBand);
		band.Value(pdeadBand) ;
		name=pname;
	}*/
    public double Value(double InValue){
        double OutValue=0.0;
        if(InValue > band.Value())
        {
            OutValue= (InValue-band.Value())/(1-band.Value());
        }
        else if(InValue <-band.Value()){
            
            OutValue= (InValue+band.Value())/(1-band.Value());
        }
        else{
            OutValue=0.0;
        }
        return OutValue;
}
    private String name;
    SetDouble band;

public static final String DEADBAND_TABLE_NAME ="/DeadBand Swettings";
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