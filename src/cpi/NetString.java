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
public class NetString  implements IMainTables {
    public NetString(String ptableName,String pbooleanKey){
    	Constructor(ptableName,pbooleanKey,"","Net.String");
    }
    public  NetString(String ptableName,String pbooleanKey,String pDefault){
    	Constructor(ptableName,pbooleanKey,pDefault,"Net.String");
    }
    public NetString(String ptableName,String pbooleanKey,ID id){
    	Constructor(ptableName,pbooleanKey,"",id.Value());
    }
    public  NetString(String ptableName,String pbooleanKey,String pDefault,ID id){
    	Constructor(ptableName,pbooleanKey,pDefault,id.Value());
    }
    private void Constructor(String ptableName,String pStringKey,String pDefaultValue,String id){
    	pId=id;
    	stringKey=pStringKey;
      defaultValue=pDefaultValue;
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
          table.putString(stringKey, defaultValue);
         // table.putString(stringKey+".ID", id);
          table.addTableListener(stringKey, new ITableListener(){
        	    public void valueChanged(ITable source, String key, Object pvalue, boolean isNew){
            	    	if((isLocked&&(pvalue!=null))||(disableOnHardCode&&Preferences.getHardCodeState())){
            	    		if((oldValue!=(String)pvalue)){
            	    		table.putString(stringKey, value);
            	    		oldValue=value;
            	    		return;
            	    		}
            	    	}
                	    	if((isLocked&&(pvalue!=null))||(disableOnHardCode&&Preferences.getHardCodeState()))return;
            	    		if((oldValue==(String)pvalue))return;
        	    value=(String)pvalue;
	    		table.putString(stringKey, value);
	    		oldValue=value;
        	    }
          }, true);
 }
    public String Value(){
        return value;
    }
    public String Value(String pvalue){
    	if(isLocked||(disableOnHardCode&&Preferences.getHardCodeState()))return value;
    	if(pvalue==oldValue)return value;
        value = pvalue;
		table.putString(stringKey, value);
        return value;
    }
    public void addActionListner(ITableListener listener){
    	table.addTableListener(stringKey,listener,true);
    }
    public void removeActionListner(ITableListener listener){
    	table.removeTableListener(listener);;
    }

    public void lock(){
    	isLocked=true;	
    }
    public void unlock(){
    	isLocked=false;	
    }
    public void disableOnHardCode(boolean disable){
    	disableOnHardCode=disable;
    }
    boolean disableOnHardCode;
    String value="";
    String oldValue="";
   String defaultValue;
   NetworkTable table;
   String stringKey;
   String tableName;
   String pId;
   String defCommPrefix;
   boolean isLocked=false;
}
