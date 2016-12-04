package cpi;

/**
 * 
 * @author Thomas Wulff
 * <br>the DeadBand class creates a dead band in analog values. This dead band prevents stick noise from
 * from affecting the analog output around the value zero.
*<br> SimpleDeadband deletes a set of values between -band and band.
* the output is scaled to eliminate a gap in the output. 
*<br> ie. the output is zero within the band and linear from zero to one starting at the band edge. 
*    <p>*................................. Output Value 
* 	<br>........................................|.1............/
* 	<br>........................................|............./
* 	<br>........................................|............/
* 	<br>........................................|.........../
* 	<br>........................................|........../
* 	<br>........................................|........./
* 	<br>........................................|......../
* 	<br>............................____________|_______/__________ input value 
* 	<br>.......................... -1....-band..|.band ............1
* 	<br>................................../.....|
* 	<br>................................./......|
* 	<br>................................/.......|
* 	<br>.............................../........|
* 	<br>............................../.........|
* 	<br>............................./..........|
* 	<br>............................/.........-1|

 */
public class DeadBand {

public static final String DEADBAND_TABLE_NAME ="/DeadBand Settings";

    private static final double DEFAULT_VALUE=0.1;
    /**
     * The constructor requires the following
     * @param table
     * table is a String value that specifies the primary network table where the dead band valye will be displayed.
     * <p>A subtable with the name "DeadBand Settings" is also created
     * @param pname
     * pname is a string value that specifies the specific key for the given dead band value
 *
     * 
     */
	public DeadBand(String table,String pname){
		band=new Set<Double>(table+DEADBAND_TABLE_NAME,pname , DEFAULT_VALUE);
	}
	/**
	 * 
	 * @param InValue
	 * The value to be  modified.
	 * 
	 * @return
	 * the modified value
	 */
    public double value(double InValue){
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
    Set<Double> band;
}


//SimpleDeadband class
/**
* SimpleDeadband deletes a set of values between -band and band.
* the output is scalled to eliminate a gap in the output. 
* ie the output is zero within the band and linear from zero to one startinfg at the band edge.
* 
* 										Output Value 
* 											| 1	           /
* 											|	          /
* 											|	         /
* 											|	        /
* 											|          /
* 											|	      /
* 									    	|	     /
* 						____________________|_______/__________ input value 
* 						       -1     -band	| band		1
* 							          /		|
* 							         /		|
* 							        /		|
* 							       /		|
* 							      /			|
* 						         /		 	|
* 						        /         -1|
* 
* 																								
*/