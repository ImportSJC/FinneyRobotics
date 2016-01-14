package cpi.Interface;

import edu.wpi.first.wpilibj.tables.ITable;
import edu.wpi.first.wpilibj.tables.ITableListener;
import cpi.Interface.Support.Classes.OutputElement;
import cpi.Tables.IFTable;

public class BooleanInput implements IFTable {
	public BooleanInput(String table,String pname){
		Constructor( table,pname, "<none>",false);
	}
	public BooleanInput(String table,String pname,boolean pdefaultValue){
		Constructor( table,pname, "<none>",pdefaultValue);
	}
	public BooleanInput(String table,String pname,String outputKey){
		Constructor( table,pname, outputKey,false);
	}
	public BooleanInput(String table,String pname,String outputKey,boolean pdefaultValue){
		Constructor(table,pname, outputKey,pdefaultValue);
	}
	private void Constructor(String table,String pname,String pkey,boolean pdefaultValue){
		BooleanOutput.setNone();
		thisKey=pkey;
		defaultValue=pdefaultValue;
		tableName=table;
    	connectionObj=(BooleanOutput)outputTable.get(thisKey);
		name=pname;
		connectionName=null;
        defCommPrefix=inputPrefix;
        if(tableName.startsWith("/")){
        	defCommPrefix="";
 //       	tableName=tableName.substring(1);
        }
		if (connectionObj!=null)connectionName=connectionObj.getName();
			inTable=new cpi.SetString(defCommPrefix+tableName+"/"+name,"Link",thisKey);
		value=pdefaultValue;
		outputList=new OutputElement(tableName+"/"+name,inTable,booleanOutputDiscriptor,booleanOutputDiscriptorList);
		inTable.addActionListner( new ITableListener(){
			
        	    public void valueChanged(ITable source, String key, Object pvalue, boolean isNew){
        	    	connectionObj=(BooleanOutput)outputTable.get((String )pvalue);
        			inTable.Value((String)pvalue);
        			thisKey=(String)pvalue;
        	    }
          });
		if(connectionObj==null)return;
		value=((BooleanOutput)outputTable.get(connectionObj.getName())).value;
	}
	public boolean Value(){
		if(connectionObj==null){
			value=defaultValue;
			if(!thisKey.equals("<none>")){

    	    	connectionObj=(BooleanOutput)outputTable.get(thisKey);
			}
			if(connectionObj==null){
				thisKey="<none>";
    			inTable.Value(thisKey);
				return value;
			}
		}
		if(connectionName==null){
			connectionName=connectionObj.getName();
		}
		return connectionObj.value;
	}
	public String getConnectionName(){
		return connectionObj.getName();
	}
	public void setConnection(BooleanOutput connection){
		connectionObj=connection;
	}
	BooleanOutput connectionObj;
	String name;
	boolean defaultValue;
	Boolean value;
	String connectionName;
	String pConnectionName;
	cpi.SetString inTable;
	String tableName;
	String thisKey;
	String defCommPrefix;
	OutputElement outputList;
}
