/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cpi;

import cpi.Tables.*;
import edu.wpi.first.wpilibj.networktables.*;
import edu.wpi.first.wpilibj.tables.*;
import edu.wpi.first.wpilibj.Timer;




/**
 *
 * @author Team 1405
 */
public class SetBoolean implements IMainTables  {
    public SetBoolean(String ptableName,String pbooleanKey){
    	Constructor(ptableName,pbooleanKey,false);
    }
    public  SetBoolean(String ptableName,String pbooleanKey,boolean pDefault){
    	Constructor(ptableName,pbooleanKey,pDefault);
    }
    private void Constructor(String ptableName,String pbooleanKey,boolean pDefaultValue){
        booleanKey=pbooleanKey;
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
        cpi.Preferences.putDefault(defCommPrefix+"/"+tableName+"/"+booleanKey, Boolean.toString(pDefaultValue));
        defaultValue=cpi.Preferences. getBoolean(defCommPrefix+"/"+tableName+"/"+"booleanKey", pDefaultValue);
		oldValue=defaultValue;
		value=defaultValue;
          table.putBoolean(booleanKey, defaultValue);
          table.addTableListener(booleanKey, new ITableListener(){
        	    public void valueChanged(ITable source, String key, Object pvalue, boolean isNew){
        	    	
        	    	if(cpi.Preferences.getHardCodeState()&& !cpi.Preferences.getInitState() && !isUpdate){
        	    		if((oldValue!=(boolean)pvalue)){
        	    		table.putBoolean(booleanKey, value);
        	    		oldValue=value;
        	    		}
        	    	return;
        	    	}
        	    if(cpi.Preferences.getHardCodeState()&& !cpi.Preferences.getInitState() && !isUpdate)return;
        	    if(oldValue==(boolean)pvalue)return;
        	    value=(Boolean)pvalue;
	    		oldValue=value;
        	    cpi.Preferences.putBoolean(defCommPrefix+"/"+tableName+"/"+booleanKey, value);
	    		table.putBoolean(booleanKey, value);
	    		if(cpi.Preferences.getAutoSaveState())cpi.Preferences.save();
        	    }
          }, true);
          cpi.Preferences.addHardCodeListener(new ITableListener(){
        	  public void valueChanged(ITable source, String key, Object pvalue, boolean isNew){
        		  	isUpdate=true;
      	        	boolean tmp=cpi.Preferences. getBoolean(defCommPrefix+"/"+tableName+"/"+booleanKey, pDefaultValue);
      	        	table.putBoolean(booleanKey, tmp);
    	    		if(cpi.Preferences.getAutoSaveState())cpi.Preferences.save();
      	        	isUpdate=false;
        	  }
          });
 }
    public boolean Value(){
        return value;
    }
    public void addActionListner(ITableListener listener){
    	table.addTableListener(booleanKey,listener,true);
    }
    public void removeActionListner(ITableListener listener){
    	table.removeTableListener(listener);;
    }
    public boolean Value(boolean pvalue){
    	if(pvalue==oldValue)return value;
    	if(cpi.Preferences.getHardCodeState()&& !cpi.Preferences.getInitState() && !isUpdate)return value;
    	value=pvalue;
        table.putBoolean(booleanKey, pvalue);
	    cpi.Preferences.putBoolean(defCommPrefix+"/"+tableName+"/"+booleanKey, pvalue);
		if(cpi.Preferences.getAutoSaveState())cpi.Preferences.save();
    	return value;
    }
   boolean value;
   boolean oldValue;
   boolean defaultValue;
   boolean isUpdate=false;
   NetworkTable table;
   String booleanKey;
   String tableName;
   String prefTableName;
   String prefKey;
   String defCommPrefix;
   
   }
