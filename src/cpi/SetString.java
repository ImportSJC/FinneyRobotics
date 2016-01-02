/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cpi;

import cpi.Tables.IMainTables;
import edu.wpi.first.wpilibj.networktables.*;
import edu.wpi.first.wpilibj.tables.*;
import edu.wpi.first.wpilibj.Timer;


/**
 *
 * @author Team 1405
 */
public class SetString  implements IMainTables {
    public SetString(String ptableName,String pbooleanKey){
    	Constructor(ptableName,pbooleanKey,"");
    }
    public  SetString(String ptableName,String pbooleanKey,String pDefault){
    	Constructor(ptableName,pbooleanKey,pDefault);
    }
    private void Constructor(String ptableName,String pstringKey,String pDefaultValue){
    	stringKey=pstringKey;
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
         defaultValue=Preferences.getString (defCommPrefix+"/"+tableName+"/"+stringKey, pDefaultValue);
        cpi.Preferences.putDefault(defCommPrefix+"/"+tableName+"/"+stringKey, pDefaultValue);
        // defaultValue=Preferences.getInstance().getString (defCommPrefix+"/"+tableName+"/"+stringKey, pDefaultValue);
		oldValue=defaultValue;
		value=defaultValue;
          table.putString(stringKey, defaultValue);
         // table.putString(stringKey+".ID", "Setting.String");
          table.addTableListener(stringKey, new ITableListener(){
        	    public void valueChanged(ITable source, String key, Object pvalue, boolean isNew){
        	    	if(cpi.Preferences.getHardCodeState()&& !cpi.Preferences.getInitState() && !isUpdate){
        	    		if((oldValue!=(String)pvalue)){
        	    		table.putString(stringKey, value);
        	    		oldValue=value;
        	    		}
        	    	return;
        	    	}
            	    if(cpi.Preferences.getHardCodeState()&& !cpi.Preferences.getInitState() && !isUpdate)return;
            	    if(oldValue==(String)pvalue)return;
        	    value=(String)pvalue;
	    		oldValue=value;
        	    Preferences.putString(defCommPrefix+"/"+tableName+"/"+stringKey, value);
	    		table.putString(stringKey, value);
	    		if(cpi.Preferences.getAutoSaveState())cpi.Preferences.save();
        	    }
          }, true);
          cpi.Preferences.addHardCodeListener(new ITableListener(){
        	  public void valueChanged(ITable source, String key, Object pvalue, boolean isNew){
        		  	isUpdate=true;
        	        String tmp=Preferences. getString(defCommPrefix+"/"+tableName+"/"+stringKey, pDefaultValue);
        	        table.putString(stringKey, tmp);
    	    		if(cpi.Preferences.getAutoSaveState())cpi.Preferences.save();
        	        isUpdate=false;
        	  }
          });
 }
    public String Value(){
        return value;
    }
    public void addActionListner(ITableListener listener){
    	table.addTableListener(stringKey,listener,true);
    }
    public void removeActionListner(ITableListener listener){
    	table.removeTableListener(listener);;
    }
    
    public String Value(String pvalue){
    	if(pvalue==oldValue)return value;
    	if(cpi.Preferences.getHardCodeState()&& !cpi.Preferences.getInitState() && !isUpdate)return value;
    	value=pvalue;
        table.putString(stringKey, pvalue);
		if(cpi.Preferences.getAutoSaveState())cpi.Preferences.save();
	    Preferences.putString(defCommPrefix+"/"+tableName+"/"+stringKey, pvalue);
		if(cpi.Preferences.getAutoSaveState())cpi.Preferences.save();
    	return value;
    }
   String value;
   String oldValue;
   String defaultValue;
   boolean isUpdate=false;
   NetworkTable table;
   String stringKey;
   String tableName;
   String prefKey;
   String defCommPrefix;
}
