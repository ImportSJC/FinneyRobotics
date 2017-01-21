package cpi.tools;

import edu.wpi.first.wpilibj.networktables.*;
import edu.wpi.first.wpilibj.tables.*;
import java.util.Vector;

/**
 * 
 * @author Thomas Wulff
 *
 *
 * @param <Type>
 * Type is the generic type parameter for this class. 
 * Type can be one of the following:
 * Boolean
 * Double
 * String
 * Boolean[]
 * Double[]
 * String[]
 * 
 */
public class  SetBase <Type>{

	Type  Default;
	Object  DefaultArray[];
	String className;
	Type value;
	Type HCvalue;
	boolean isArray=false;
	boolean isLocked=false;
	boolean isTempUnlocked=false;
	boolean isFirst=true;
	protected static boolean isHardCode=false;
	boolean isPersistent;
	String tableName;
	String key;
	NetworkTable table;
	NetworkTable HCconstantsTable;
	public static final Vector<String> tablesAndKeys=new Vector<String>();
	public static final NetworkTable HCtable=NetworkTable.getTable(Constants.TITLE);
	public static final NetworkTable HCtransferTable=NetworkTable.getTable(Constants.TABLE_AND_KEYS_TABLE);
	
// Begin Listeners
	ITableListener listener=new ITableListener(){
		public void valueChanged(ITable Table, String str, Object obj, boolean bool){
			if((isHardCode&&isPersistent||isLocked)&& !isTempUnlocked){
				obj=(Object)value;
				table.putValue(key, value);
				return;
			}
			isTempUnlocked=false;
			value=(Type)obj;
		}
	};
	ITableListener HClistener=new ITableListener(){
		public void valueChanged(ITable Table, String str, Object obj, boolean bool){
			isHardCode=(boolean)obj;
		}
	};
	ITableListener HCconstantsTablelistener=new ITableListener(){
		public void valueChanged(ITable Table, String str, Object obj, boolean bool){
			obj=HCvalue;
		}
	};
	ITableListener HCtransferTablelistener=new ITableListener(){
		public void valueChanged(ITable Table, String str, Object obj, boolean bool){
			String[]list=(String[])tablesAndKeys.toArray();
			HCtransferTable.putStringArray(Constants.TABLE_AND_KEYS_KEY, list);
			
		}
	};
// End Listeners
	
// Begin Constructor
	/**
	 * SetBase can share data of type Type with other devices such as the driver Station laptop through the NetworkTables Class without restrictions when peersistence is false.
	 * 
	 * Set can share data of type Type with other devices such as the driver Station laptop through the NetworkTables Class with restrictions when peersistence is true.
	 * The SetBase values are persistant. They can be changed in OnlineViewer or custom programs and will remain set to the new values when the code is rebooted.
	 * SetBase values cannot be modified when Hard Code is set (true). Hard code values are used instead.
	 * 
	 *  
	 * @param table
	 * table identifies the subtable name of the master robot tabele neme specified in class cpi.tools.Constants.TITLE .
	 * eg. If Constants.TITLE = "Robot" and table = "Name1", the full table path name is "Robot/Name1"
	 * Slashes(/) may be used to define tables of subtables.
	 * @param key
	 * key is the final key identifier of type Type. Do not use / (slash) as part of key
	 * @param Default
	 * Default is the initial value of the set parameter with type Type.
	 * @param setPersistance
	 * setPersistance determines if persistence is enabled. setPersistance is true for the class Set and false for the Net class
	 */
	public SetBase(String table,String key,Type Default,boolean setPersistance)
	{
		if(isFirst){ // this is done once upon  the first instanciation
	        HCtable.putBoolean(Constants.HC_KEY, true);
	        HCtable.clearPersistent(Constants.HC_KEY);
	        HCtable.addTableListener(Constants.HC_KEY, HClistener, true);
	        HCtransferTable.addTableListener(Constants.TRANSFER_STATE_KEY, HCconstantsTablelistener, true);
			isFirst=false;
			HCconstants.set();
		}
			// End done once
		
		this.Default=Default;
		tableName=table;
		this.key=key;
		isPersistent=setPersistance;
		className=Default.getClass().getSimpleName();

		if(className.contains("[]")){
			isArray=true;
		}else{
			isArray=false;
		}
		this.table=NetworkTable.getTable(Constants.TITLE+"/"+tableName);
		this.table.addTableListener(this.key, listener, true);
		tempUnLock();
		value=getValue(this.key, this.Default);
		this.table.putValue(key, value);
		if(isPersistent){
			this.table.setPersistent(this.key);
			
		}
		else{
			this.table.clearPersistent(this.key);
		}
		HCconstantsTable=NetworkTable.getTable(Constants.TITLE+"/"+Constants.HC_TABLE+"/"+tableName);
		HCconstantsTable.putValue(this.key, this.Default);
		HCvalue=this.Default;
		HCconstantsTable.addTableListener(this.key, HCconstantsTablelistener, true); // Permanently locked
		tablesAndKeys.addElement(Constants.TITLE+"/"+tableName+Constants.TABLE_AND_KEYS_SEPARATOR+this.key);
	}	
	// End of Constructor	
	

/**
 * @return
 *  Returns the current value if persistence is false.
 *  Returns the current value if persistence is true and Hard Code is false.
 *  Returns the Hard Code value if persistence is true and Hard Code is true.
 *  Hard Code state may only be modified outside the robot program.
 */
	public Type Value(){
		if(isHardCode&&isPersistent)return HCvalue;
		return value;
	}	
/**
 * 
 * @param value
 * The new programmed value if persistence is false or if persistence is true and Hard Code is false.
 * If persistence is true and Hard Code is true the internal value is not changed.
 * 
 * @return
 *  Returns the current value if persistence is false.
 *  Returns the current value if persistence is true and Hard Code is false.
 *  Returns the Hard Code value if persistence is true and Hard Code is true.
 *  Hard Code state may only be modified outside the robot program.
 */
	public Type Value(Type value){
		if(isHardCode&&isPersistent)return HCvalue;
		this.value=value;
		table.putValue(key, this.value);
		return this.value;
	}
	/**
	 * 
	 * @param setLocked
	 * 
	 * Prevents changing the value outside the robot program
	 */
	public void Lock(boolean setLocked){
		isLocked=setLocked;
	}	
	/**
	 * Allows values to be changed without conditions once for each tempUnLock.
	 */
	public void tempUnLock(){
		isTempUnlocked=true;
	}
	/**
	 * Adds a table and key listener to the setting.
	 * Listeners are invoked (run) when the value changes.
	 * @param listener
	 * 
	 * An example of listener code
	 *ITableListener listener=new ITableListener(){
	 *	public void valueChanged(ITable Table, String str, Object obj, boolean bool){
	 *		isHardCode=(boolean)obj;
	 *	}
	 *};
	 */
	public void addListener(ITableListener listener){
		table.addTableListener(key, listener, true);
	}
	/**
	 * Adds a Hard Code State listener to the Hard Code state table and key.
	 * Listeners are invoked (run) when the value changes.
	 * @param listener
	 * 
	 * An example of listener code
	 *ITableListener listener=new ITableListener(){
	 *	public void valueChanged(ITable Table, String str, Object obj, boolean bool){
	 *		isHardCode=(boolean)obj;
	 *	}
	 *};
	 */
	public void addHCListener(ITableListener listener){
		HCtable.addTableListener(key, listener, true);
	}
/**
 * Used for debug
 * @return
 * Returns the current line number when invoked.
 */
	public static int lineNumber(){
		return Thread.currentThread().getStackTrace()[2].getLineNumber();
	}
	Type getValue(String key, Type defaultValue){
		Type tmpValue=Default;
		switch(className){

		case "Boolean[]":
			tmpValue=(Type)table.getBooleanArray(key, (Boolean[])defaultValue);
			break;
		case "Double[]":
			tmpValue=(Type)table.getNumberArray(key, (Double[])defaultValue);
			break;
		case "String[]":
			tmpValue=(Type)table.getStringArray(key, (String[])defaultValue);
			break;
		default:
			tmpValue=(Type)table.getValue(key, (Type)defaultValue);
		}
		return tmpValue;
	}
}