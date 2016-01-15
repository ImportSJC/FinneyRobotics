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
public class NetDouble implements IMainTables  {
    public NetDouble(String ptableName,String pbooleanKey){
    	Constructor(ptableName,pbooleanKey,0.0,"Net.Number");
    }
    public  NetDouble(String ptableName,String pbooleanKey,double pDefault){
    	Constructor(ptableName,pbooleanKey,pDefault,"Net.Number");
    }
        public NetDouble(String ptableName,String pbooleanKey,ID id){
        	Constructor(ptableName,pbooleanKey,0.0,id.Value());
        }
        public  NetDouble(String ptableName,String pbooleanKey,double pDefault,ID id){
        	Constructor(ptableName,pbooleanKey,pDefault,id.Value());
    }
    private void Constructor(String ptableName,String pbooleanKey,double pDefaultValue,String id){
    	pId=id;
    	sensitivity=0.05;
    	numberKey=pbooleanKey;
      defaultValue=pDefaultValue;
        tableName=ptableName;
        defCommPrefix=dCommPrefix;
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
          table.putNumber(numberKey, defaultValue);
          //table.putString(numberKey+".ID", id);
          table.addTableListener(numberKey, new ITableListener(){
        	    public void valueChanged(ITable source, String key, Object pvalue, boolean isNew){
        	    	if((isLocked&&(oldValue!=(double)pvalue))||(disableOnHardCode&&Preferences.getHardCodeState())){
        	    		if((oldValue!=(double)pvalue)){
        	    		table.putNumber(numberKey, value);
        	    		oldValue=value;
        	    		return;
        	    		}
        	    	}
        	    	if((isLocked&&(pvalue!=null))||(disableOnHardCode&&Preferences.getHardCodeState()))return;
    	    		if((oldValue==(double)pvalue))return;
        	    value=(Double)pvalue;
	    		table.putNumber(numberKey, value);
	    		oldValue=value;
        	    }
          }, true);
 }
    public double Value(){
        return value;
    }
    public double Value(double pvalue){
    	double abs=pvalue-value;if(abs<0.0)abs=-abs;
    	
        if(abs>sensitivity)table.putNumber(numberKey, pvalue);
        value = pvalue;
        return value;
    }
    public void setSensitivity(double pvalue){
    	sensitivity=pvalue;
    	if(sensitivity<0.0)sensitivity=-sensitivity;
    }
    
    public double getSensitivity(){
    	return sensitivity;
    }
    public void addActionListner(ITableListener listener){
    	table.addTableListener(numberKey,listener,true);
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
    double value;
    double oldValue;
   double defaultValue;
   double sensitivity;
   NetworkTable table;
   String numberKey;
   String tableName;
   String pId;
   String defCommPrefix;
   boolean isLocked=false;
}
