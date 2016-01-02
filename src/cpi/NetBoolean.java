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
public class NetBoolean implements IMainTables  {
    public NetBoolean(String ptableName,String pbooleanKey){
    	Constructor(ptableName,pbooleanKey,false, "Net.Boolean");
    }
    public  NetBoolean(String ptableName,String pbooleanKey,boolean pDefault){
    	Constructor(ptableName,pbooleanKey,pDefault, "Net.Boolean");
    }
    public NetBoolean(String ptableName,String pbooleanKey,ID id){
    	Constructor(ptableName,pbooleanKey,false,id.Value());
    }
    public  NetBoolean(String ptableName,String pbooleanKey,boolean pDefault,ID id){
    	Constructor(ptableName,pbooleanKey,pDefault,id.Value());
    }
    private void Constructor(String ptableName,String pbooleanKey,boolean pDefaultValue,String id){
    	pId=id;
        booleanKey=pbooleanKey;
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
          table.putBoolean(booleanKey, defaultValue);
         // table.putString(booleanKey+".ID", id);
          table.addTableListener(booleanKey, new ITableListener(){
        	    public void valueChanged(ITable source, String key, Object pvalue, boolean isNew){
        	    	if((isLocked||(disableOnHardCode&&Preferences.getHardCodeState()))&&(oldValue!=(Boolean)pvalue)){
        	    		if((oldValue!=(boolean)pvalue)){
        	    		table.putBoolean(booleanKey, value);
        	    		oldValue=value;
        	    		return;
        	    		}
        	    	}
        	    	if((isLocked&&(pvalue!=null))||(disableOnHardCode&&Preferences.getHardCodeState()))return;
    	    		if((oldValue==(boolean)pvalue))return;
        	    value=(Boolean) pvalue;
	    		table.putBoolean(booleanKey, value);
	    		oldValue=value;
        	    }
          }, true);
 }
    public boolean Value(){
        return value;
    }
    public boolean Value(boolean pvalue){
    	if(pvalue==oldValue)return value;
        value = pvalue;
		table.putBoolean(booleanKey, value);
        return value;
    }
    public void addActionListner(ITableListener listener){
    	table.addTableListener(booleanKey,listener,true);
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
    boolean value;
    boolean oldValue;
   boolean defaultValue;
   NetworkTable table;
   String booleanKey;
   String tableName;
   String pId;
   String defCommPrefix;
   boolean isLocked=false;
   String prefix="Utilitiea/";
   cpi.NetString hcDestination;
}
