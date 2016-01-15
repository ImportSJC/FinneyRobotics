package cpi.Tables;

import java.util.Vector;

import cpi.ID;
import cpi.NetBoolean;
import cpi.NetString;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.tables.ITable;
import edu.wpi.first.wpilibj.tables.ITableListener;

public class CPrefTable {
	public CPrefTable(){
		isHardCode=new NetBoolean("cpi.Preferences","Using HardCode",false,new ID("Preferences.Boolean"));
		isDirty=new NetBoolean("cpi.Preferences","Have some Prefferences changed?",false,new ID("Preferences.Boolean"));
		isSave=new NetBoolean("cpi.Preferences","Save Preferences",false,new ID("Preferences.Boolean"));
		isRemoveAll=new NetBoolean("cpi.Preferences","Delet all Preferences",false);
		isRefreshKeylist=new NetBoolean("cpi.Preferences","Refresh Key List",false,new ID("Preferences.Boolean"));
		keylist=new NetString("cpi.Preferences","Refresh Key List","",new ID("Preferences.String"));


		isHardCode.addActionListner(new ITableListener(){
    	    public void valueChanged(ITable source, String key, Object pvalue, boolean isNew){
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
      
		isRefreshKeylist.addActionListner(new ITableListener(){
    	    public void valueChanged(ITable source, String key, Object pvalue, boolean isNew){
    	    	if(!isRefreshKeylist.Value())return;
    	    	keylist.Value("");
    	    	Vector newKeyVector=getKeys();
    	    	int i=0;
    	    	while(i<newKeyVector.size()){
    	    		keylist.Value(keylist.Value()+(String)newKeyVector.get(i));
    	    		i++;
    	    	}
    	    	isRefreshKeylist.Value(false);
    	    }
      });
	}

    
    public static void save(){
    	isDirty.Value(false);
        edu.wpi.first.wpilibj.Preferences.getInstance().save();
        isSave.Value(false);
    }
    

    public static void removeAll(){
        // remove all keys
        Vector vtemp=edu.wpi.first.wpilibj.Preferences.getInstance().getKeys();
        if (vtemp.size()<=0)return;
        int i=0;
        System.out.println("Removing "+vtemp.size()+" elements\n");
        while(i<vtemp.size()){
        	System.out.println("Removing "+vtemp.elementAt(i));
            edu.wpi.first.wpilibj.Preferences.getInstance().remove((String)vtemp.elementAt(i));
            i++;
            Timer.delay(.01);
        }
         edu.wpi.first.wpilibj.Preferences.getInstance().save();

     	isDirty.Value(false);

        System.out.println("\nEnd removal\n");
    }
    
    public static Vector getKeys(){
        return edu.wpi.first.wpilibj.Preferences.getInstance().getKeys();
    }


    public static NetBoolean isHardCode;
    public static NetBoolean isDirty;
    public static NetBoolean isSave;
    public static NetBoolean isRemoveAll;
    public static NetBoolean isRefreshKeylist;
    public static NetString  keylist;
	
}