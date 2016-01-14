/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package cpi;

/**
 *
 * @author Robotics
 */


import java.util.Hashtable;
import java.util.Vector;
import java.util.Timer;
import java.util.TimerTask;
import edu.wpi.first.wpilibj.DriverStation;

import edu.wpi.first.wpilibj.tables.*;

import java.util.Enumeration;

public class Preferences {

    
    static Preferences preferences;
    static public String header="";
    static boolean isFirstGlobalPrefixCall=true;
    
    static NetBoolean isHardCode=new NetBoolean("","Using HardCode",true);
    static NetBoolean isAutoSave=new NetBoolean("/cpi.Preferences","Auto Save On",true);
    static NetBoolean isHardCodeToPref=new NetBoolean("/cpi.Preferences","Copy Hard Code to Preferences",false);;
    static NetBoolean isDirty=new NetBoolean("/cpi.Preferences","Are there any unsaved settings?",false);
    static NetBoolean isSave=new NetBoolean("/cpi.Preferences","Save all Preferences",false);
    static NetBoolean isClean=new NetBoolean("/cpi.Preferences","Clean and save Preferences",false);
    static NetBoolean isRemoveAll=new NetBoolean("/cpi.Preferences","Delete all Preferences",false);
    static NetBoolean isRefreshKeylist=new NetBoolean("/cpi.Preferences","Refresh Key List",false);
    static NetBoolean isPrintKeys=new NetBoolean("/cpi.Preferences","Display all Preference keys on console",false);
    static NetString keylist =new NetString(cpi.Tables.Constants.CPI_PREFERENCES,cpi.Tables.Constants.KEY_LIST,"");
    static boolean firstInstance=true;
    static boolean isTimerRunning=false;
    static Hashtable<java.lang.String,java.lang.String> preferenceRefreshMap=new Hashtable<java.lang.String,java.lang.String>();
    static Vector<String> activeKeyList=new Vector<String>();
    
    static Timer timer=new Timer();
    
    Preferences(){
        
    }
    public static Preferences getInstance(){
    	initialize();
        return preferences;
    }
    
    public static void initialize(){
    	if(firstInstance){
    	isSave.lock();
	        Vector<String> vtemp=edu.wpi.first.wpilibj.Preferences.getInstance().getKeys();
	        int i=0;
	        while(i<vtemp.size()){
	        	String tmp=vtemp.elementAt(i);
	        	String value=edu.wpi.first.wpilibj.Preferences.getInstance().getString(tmp,null);
	        	preferenceRefreshMap.put(tmp, value);
	        	i++;
	        }
	
    		isHardCode.addActionListner(new ITableListener(){
        	    public void valueChanged(ITable source, String key, Object pvalue, boolean isNew){
        	    	if((boolean)pvalue){
        	        	isSave.lock();
        	        	Enumeration<String> e=cpi.Hard.Code.ConstantArray.Map.keys();
        	        	while(e.hasMoreElements()){
        	        		String tmp=e.nextElement();
        	        		putString(tmp, cpi.Hard.Code.ConstantArray.Map.get(tmp));
        	        	}
        		        Vector<String> vtemp=edu.wpi.first.wpilibj.Preferences.getInstance().getKeys();
        		        int i=0;
        		        while(i<vtemp.size()){
        		        	String tmp=vtemp.elementAt(i);
        		        	String value=edu.wpi.first.wpilibj.Preferences.getInstance().getString(tmp,null);
        		        	preferenceRefreshMap.put(tmp, value);
        		        	i++;
        		        }
        	    	}
        	    	else{

        	        	isSave.unlock();
        	        	Enumeration<String> e=preferenceRefreshMap.keys();
        	        	while(e.hasMoreElements()){
        	        		String tmp=e.nextElement();
        	        		putString(tmp, preferenceRefreshMap.get(tmp));
        	        	}
        	    	}
        	    	isHardCode.Value((Boolean) pvalue);

        	    }
          });
    		isSave.addActionListner(new ITableListener(){
        	    public void valueChanged(ITable source, String key, Object pvalue, boolean isNew){
        	    	if(!isDirty.Value())return;
        	    	save();
        	    	isDirty.Value(false);
        	    	isSave.Value(false);
        	    }
          });
    		
    		isRemoveAll.addActionListner(new ITableListener(){
        	    public void valueChanged(ITable source, String key, Object pvalue, boolean isNew){
        	    	if(!isRemoveAll.Value())return;
        	    	removeAll();
        	    	isRemoveAll.Value(false);
        	    }
          });
    		
    		isPrintKeys.addActionListner(new ITableListener(){
        	    public void valueChanged(ITable source, String key, Object pvalue, boolean isNew){
        	    	if(isPrintKeys.Value())printpkl();
        	    	isPrintKeys.Value(false);
        	    }
          });
          
    		isRefreshKeylist.addActionListner(new ITableListener(){
        	    public void valueChanged(ITable source, String key, Object pvalue, boolean isNew){
        	    	if(!isRefreshKeylist.Value())return;
        	    	keylist.Value("");
        	    	Vector newKeyVector=getKeys();
        	    	int i=0;
        	    	while(i<newKeyVector.size()){
        	    		keylist.Value(keylist.Value()+(String)newKeyVector.get(i)+"\n");
        	    		i++;
        	    	}
        	    	isRefreshKeylist.Value(false);
        	    }
          });
    		
    		isClean.addActionListner(new ITableListener(){
        	    public void valueChanged(ITable source, String key, Object pvalue, boolean isNew){
        	    	if((boolean)pvalue)clean();
        	    	isClean.Value(false);
        	    }
          });
    		isHardCodeToPref.addActionListner(new ITableListener(){
        	    public void valueChanged(ITable source, String key, Object pvalue, boolean isNew){
        	    	if((boolean)pvalue)copyHardCodeToPreferences();
        	    	isHardCodeToPref.Value(false);
        	    }
          });
        	cpi.Hard.Code.ConstantArray.initialize();
    		firstInstance=false;
    	}
    }
    
    public static boolean containsKey(String key){
    	key=fixKey(key);
    	
    	if(isHardCode.Value()){
    		return   cpi.Hard.Code.ConstantArray.Map.containsKey(key);
    	}
    	
    	
        return edu.wpi.first.wpilibj.Preferences.getInstance().containsKey(key);
    }
    
    public static boolean getBoolean(String key,boolean value){
    	key=fixKey(key);
    	
    	if(isHardCode.Value()){
    		return   Boolean.valueOf(cpi.Hard.Code.ConstantArray.Map.getOrDefault(key,String.valueOf(value) ));
    	}
    	
        return edu.wpi.first.wpilibj.Preferences.getInstance().getBoolean(key, value);
    } 
    
    public static double getDouble(String key,double value){
    	key=fixKey(key);
    	
    	if(isHardCode.Value()){
    		return   Double.valueOf(cpi.Hard.Code.ConstantArray.Map.getOrDefault(key,String.valueOf(value) ));
    	}
    	
        return edu.wpi.first.wpilibj.Preferences.getInstance().getDouble(key, value);
        
    } 
    
    public static float getFloat(String key,float value){
    	key=fixKey(key);
    	
    	if(isHardCode.Value()){
    		return   Float.valueOf(cpi.Hard.Code.ConstantArray.Map.getOrDefault(key,String.valueOf(value) ));
    	}
    	
        return edu.wpi.first.wpilibj.Preferences.getInstance().getFloat(key, value);
    } 
    
    public static long getLong(String key,long value){
    	key=fixKey(key);
    	
    	if(isHardCode.Value()){
    		return   Long.valueOf(cpi.Hard.Code.ConstantArray.Map.getOrDefault(key,String.valueOf(value) ));
    	}
    	
        return edu.wpi.first.wpilibj.Preferences.getInstance().getLong(key, value);
    }  
    
    public static int getString(String key,int value){
    	key=fixKey(key);
    	
    	if(isHardCode.Value()){
    		return   Integer.valueOf(cpi.Hard.Code.ConstantArray.Map.getOrDefault(key,String.valueOf(value) ));
    	}
    	
       return edu.wpi.first.wpilibj.Preferences.getInstance().getInt(key, value);
    }  
    
    public static String getString(String key,String value){
    	key=fixKey(key);
    	
    	if(isHardCode.Value()){
    		return   cpi.Hard.Code.ConstantArray.Map.getOrDefault(key, value);
    	}
    	
        return edu.wpi.first.wpilibj.Preferences.getInstance().getString(key, value);
    }  
    
    public static void putBoolean(String key,boolean value){
    	key=fixKey(key);
    	if(isHardCode.Value()){
    		return   ;
    	}
        edu.wpi.first.wpilibj.Preferences.getInstance().putBoolean(key, value);
        isDirty.Value(true);
    }
    
    public static void putDouble(String key,double value){
    	key=fixKey(key);
    	if(isHardCode.Value()){
    		return   ;
    	}
        edu.wpi.first.wpilibj.Preferences.getInstance().putDouble(key, value);
        isDirty.Value(true);
        
    }
    
    public static void putFloat(String key,float value){
    	key=fixKey(key);
    	if(isHardCode.Value()){
    		return   ;
    	}
        edu.wpi.first.wpilibj.Preferences.getInstance().putFloat(key, value);
        isDirty.Value(true);
    }
    
    public static void putInt(String key,int value){
    	key=fixKey(key);
    	if(isHardCode.Value()){
    		return   ;
    	}
        edu.wpi.first.wpilibj.Preferences.getInstance().putInt(key, value);
        isDirty.Value(true);
    }
    
    public static void putLong(String key,long value){
    	key=fixKey(key);
    	if(isHardCode.Value()){
    		return   ;
    	}
        edu.wpi.first.wpilibj.Preferences.getInstance().putLong(key, value);
        isDirty.Value(true);
    }
    
    public static void putString(String key,String value){
    	key=fixKey(key);
    	if(isHardCode.Value()){
    		return   ;
    	}
        edu.wpi.first.wpilibj.Preferences.getInstance().putString(key, value);
        isDirty.Value(true);
    }
    
    public static Vector getKeys(){
        return edu.wpi.first.wpilibj.Preferences.getInstance().getKeys();
    }
   
    public static void removeAll(){
        // remove all keys
        Vector<String> vtemp=edu.wpi.first.wpilibj.Preferences.getInstance().getKeys();
        if (vtemp.size()<=0)return;
        int i=0;
        System.out.println("Removing "+vtemp.size()+" elements\n");
        while(0<vtemp.size()){
        	System.out.println("Removing "+vtemp.elementAt(0)+" ("+i+")");
            edu.wpi.first.wpilibj.Preferences.getInstance().remove((String)vtemp.elementAt(0));
            i++;
        }
         edu.wpi.first.wpilibj.Preferences.getInstance().save();

        System.out.println("\nEnd removal\n");
    }
    
    public static void remove(String key){
    	key=fixKey(key);
        if(!edu.wpi.first.wpilibj.Preferences.getInstance().containsKey(key))return;
        edu.wpi.first.wpilibj.Preferences.getInstance().remove(key);
        edu.wpi.first.wpilibj.Preferences.getInstance().save();
    }
    
    public static void save(){
    	if(isHardCode.Value())return;
    	if(isTimerRunning)return;
    	isTimerRunning=true;
    	timer.schedule(new TimerTask(){
    		public void run(){
            	isDirty.Value(false);
                edu.wpi.first.wpilibj.Preferences.getInstance().save();
                isTimerRunning=false;
    		}
    	}, 1000);
    }
    

    public static void printpkl(){
    	initialize();
    	System.out.println("\n Preferences List of Keys");
        Vector<String> vtemp=edu.wpi.first.wpilibj.Preferences.getInstance().getKeys();
        System.out.println("Number of keys= "+vtemp.size()+"\n");
        int i=0;
        while(i<vtemp.size()){
        	System.out.println(vtemp.elementAt(i)+" = "+edu.wpi.first.wpilibj.Preferences.getInstance().getString(vtemp.elementAt(i), ""));
        	i++;
        }
    	System.out.println("\nEnd Preference list\n");
    }
    public static void printhckl(){
    	initialize();
    	System.out.println("\n Hard Code List of Keys");
    	cpi.Hard.Code.ConstantArray.PrintDate();
        System.out.println("Number of keys= "+cpi.Hard.Code.ConstantArray.Map.size()+"\n");
    	Enumeration<String> e=cpi.Hard.Code.ConstantArray.Map.keys();
    	int i=0;
    	while(e.hasMoreElements()){
    	      System.out.println(e.nextElement()+" ("+i+")"+" = "+cpi.Hard.Code.ConstantArray.Map.get(e.nextElement()));
    	      i++;
    	}
    	System.out.println("\nEnd Hard Code list of keys\n");
    }
    public static void printrm(){
    	initialize();
    	System.out.println("\n Refresh Mapping");
        System.out.println("Number of keys= "+preferenceRefreshMap.size()+"\n");
    	Enumeration<String> e=preferenceRefreshMap.keys();
    	int i=0;
    	while(e.hasMoreElements()){
    		String tmp=e.nextElement();
    	      System.out.println(tmp+" ("+i+")"+" = "+preferenceRefreshMap.get(tmp));
    	      i++;
    	}
    	System.out.println("\nEnd Refresh Mapping\n");
    }
    
    public static void addHardCodeListener(ITableListener updateListener){
    	isHardCode.addActionListner(updateListener);
    }
    private static String fixKey(String key){
    	String tmp=key.replace(" ", "_");
    	addToCleanList(key);
    	return tmp;
    }
    
    static void addToCleanList(String key){
    	if(!activeKeyList.contains(key))activeKeyList.addElement(key);
    }
    
    static void clean()
    {
        Vector<String> vtemp=edu.wpi.first.wpilibj.Preferences.getInstance().getKeys();
        if (vtemp.size()<=0)return;
        int i=0;
        System.out.println("\n Clean and Save\n");
        while(i<vtemp.size()){
        	if(!activeKeyList.equals(vtemp.elementAt(0))){
        	System.out.println("Removing "+vtemp.elementAt(0));
            edu.wpi.first.wpilibj.Preferences.getInstance().remove((String)vtemp.elementAt(0));
        	}
        	else{
        		i++;
        	}
        }
         edu.wpi.first.wpilibj.Preferences.getInstance().save();
         System.out.println("\n Ene Clean and Save\n");
    }
    
    public static void putDefault(String key,String defaultValue){
    	key=fixKey(key);
    	if(preferenceRefreshMap.get(key)==null){
        	preferenceRefreshMap.put(key, (String)defaultValue);
        	
    	}
    	
    }
    	
    public static void copyHardCodeToPreferences(){
    	// Get hard code key vector
    	Enumeration e=cpi.Hard.Code.ConstantArray.Map.keys();
    	// for all keys in vector get hard code value and put in preferences
        while(e.hasMoreElements()){
        	String key=(String)e.nextElement();
            System.out.println(key+"     -     "+cpi.Hard.Code.ConstantArray.Map.get(key));
            Preferences.getInstance().putString(key, cpi.Hard.Code.ConstantArray.Map.get(key));
        }
    	// Save preferences
    	save();
    	isHardCodeToPref.Value(false);
    }
    public static boolean getHardCodeState(){
    	return isHardCode.value;
    }
    public static boolean getInitState(){
    	return firstInstance;
    }
    
    static public boolean  getAutoSaveState(){
    	return isAutoSave.Value();
    }
    
    static public String globalPrefix(){
    	String tmp="Robot ("+org.usfirst.frc.team1405.robot.Robot.header+")";
    	if(isFirstGlobalPrefixCall){
    		DriverStation.reportError(tmp, false);
    		isFirstGlobalPrefixCall=false;
    	}
    	    	return tmp;
    }
}
    

