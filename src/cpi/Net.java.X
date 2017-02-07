package cpi;
import cpi.tools.SetBase;
/**
 * 
 * @author Thomas Wulff
 *
 *
 * @param <Type>
 * <br>Type is the generic type parameter for this class. 
 * <br>Type can be one of the following:
 * <br>Boolean
 * <br>Double
 * <br>String
 * <br>Boolean[]
 * <br>Double[]
 * <br>String[]
 * 
 */
public class  Net <Type> extends SetBase <Type> {
	/**
	 * Net extends SetBase.
	 * Net can share data of type Type with other devices such as the driver Station laptop through the NetworkTables Class.
	 * 
	 * @param table
	 * table identifies the subtable name of the master robot tabele neme specified in class cpi.Set.TITLE .
	 * <br>eg. If TITLE = "Robot" and table = "Name1", the full table path name is "Robot/Name1"
	 * Slashes(/) may be used to define tables of subtables.
	 * @param key
	 * key is the final key identifier of type Type. Do not use / (slash) as part of key
	 * @param initialValue
	 * initialValue is the initial value of the set parameter with type Type.
	 */
	public Net(String table,String key,Type initialValue){
		super(table,key,initialValue,false);
	}

}
