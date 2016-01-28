/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cpi;

import cpi.Tables.IMainTables;
import edu.wpi.first.wpilibj.networktables.NetworkTable;
import edu.wpi.first.wpilibj.tables.*;
import edu.wpi.first.wpilibj.Timer;



/**
 *
 * @author Team 1405
 */
public class SetDouble implements IMainTables  {
    public SetDouble(String ptableName,String pbooleanKey){
    	Constructor(ptableName,pbooleanKey,0.0);
    }
    public  SetDouble(String ptableName,String pbooleanKey,double pDefault){
    	Constructor(ptableName,pbooleanKey,pDefault);
    }
    private void Constructor(String ptableName,String pbooleanKey,double pDefaultValue){
        numberKey=pbooleanKey;
        tableName=ptableName;
        defCommPrefix=cpi.Preferences.globalPrefix();
        if(tableName.startsWith("/")){
        	tableName=tableName.substring(1);
        }
        if(tableName==""){
          	 table=NetworkTable.getTable(defCommPrefix);
           }else{
          	 table=NetworkTable.getTable(defCommPrefix+"/"+tableName);
           }
        Timer.delay(cpi.Tables.Constants.NETWORK_SET_DELAY);
        Preferences.addKey(defCommPrefix+"/"+tableName+"/"+pbooleanKey);// Work around for edu.wpi.first.wpilibj.Preferences.getInstance().getKeys().
        defaultValue=Preferences.getInstance().getDouble (defCommPrefix+"/"+tableName+"/"+pbooleanKey, pDefaultValue);
        edu.wpi.first.wpilibj.Preferences.getInstance().getString(defCommPrefix+"/"+tableName+"/"+pbooleanKey, "");
		oldValue=defaultValue;
		value=defaultValue;
          table.putNumber(numberKey, defaultValue);
         // table.putString(numberKey+".ID", "Setting.Boolean");
          table.addTableListener(numberKey, new ITableListener(){
        	    public void valueChanged(ITable source, String key, Object pvalue, boolean isNew){
        	    	if(cpi.Preferences.getHardCodeState()&& !cpi.Preferences.getInitState() && !isUpdate){
        	    		if((oldValue!=(Double)pvalue)){
        	    		table.putNumber(numberKey, value);
        	    		oldValue=value;
        	    		}
        	    	return;
        	    	}
            	    if(cpi.Preferences.getHardCodeState()&& !cpi.Preferences.getInitState() && !isUpdate)return;
            	    if(oldValue==(Double)pvalue)return;
        	    value=(Double)pvalue;
	    		oldValue=value;
        	    Preferences.getInstance().putDouble(defCommPrefix+"/"+tableName+"/"+numberKey, value);
	    		table.putNumber(numberKey, value);
	    		if(cpi.Preferences.getAutoSaveState())cpi.Preferences.save();
        	    }
          }, true);
          cpi.Preferences.addHardCodeListener(new ITableListener(){
        	  public void valueChanged(ITable source, String key, Object pvalue, boolean isNew){
        		  isUpdate=true;
        	        double tmp=Preferences.getInstance(). getDouble(defCommPrefix+"/"+tableName+"/"+numberKey, pDefaultValue);
          	        table.putNumber(numberKey, tmp);
    	    		if(cpi.Preferences.getAutoSaveState())cpi.Preferences.save();
          	      isUpdate=false;
        	  }
          });
 }
    public double Value(){
        return value;
    }
    public void addActionListner(ITableListener listener){
    	table.addTableListener(numberKey,listener,true);
    }
    public void removeActionListner(ITableListener listener){
    	table.removeTableListener(listener);;
    }
    public double Value(double pvalue){
    	if(pvalue==oldValue)return value;
    	if(cpi.Preferences.getHardCodeState()&& !cpi.Preferences.getInitState() && !isUpdate) return value;
    	value=pvalue;
        table.putNumber(numberKey, pvalue);
		if(cpi.Preferences.getAutoSaveState())cpi.Preferences.save();
	    Preferences.getInstance().putDouble(defCommPrefix+"/"+tableName+"/"+numberKey, pvalue);
		if(cpi.Preferences.getAutoSaveState())cpi.Preferences.save();
    	return value;
    }
    boolean isLocked=false;
   double value;
   double oldValue;
   double defaultValue;
   boolean isUpdate=false;
   NetworkTable table;
   String numberKey;
   String tableName;
   String prefKey;
   String defCommPrefix;
}
