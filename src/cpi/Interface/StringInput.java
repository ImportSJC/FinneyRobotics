package cpi.Interface;

import cpi.Interface.Support.Classes.OutputElement;
import cpi.Tables.IFTable;
import edu.wpi.first.wpilibj.tables.ITable;
import edu.wpi.first.wpilibj.tables.ITableListener;

public class StringInput implements IFTable {
	public StringInput(String table,String pname){
		Constructor( table,pname, "<none>","");
	}
	public StringInput(String table,String pname,String outputKey){
		Constructor( table,pname, outputKey,"");
	}
	public StringInput(String table,String pname,String outputKey,String pdefaultValue){
		Constructor(table,pname, outputKey,pdefaultValue);
	}
	private void Constructor(String table,String pname,String pkey,String pdefaultValue){
		BooleanOutput.setNone();
		thisKey=pkey;
		defaultValue=pdefaultValue;
		tableName=table;
    	connectionObj=(StringOutput)outputTable.get(thisKey);
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
		outputList=new OutputElement(tableName+"/"+name,inTable,stringOutputDiscriptor,stringOutputDiscriptorList);
		inTable.addActionListner( new ITableListener(){
			
        	    public void valueChanged(ITable source, String key, Object pvalue, boolean isNew){
        	    	connectionObj=(StringOutput)outputTable.get((String )pvalue);
        			inTable.Value((String)pvalue);
        			thisKey=(String)pvalue;
        	    }
          });
		if(connectionObj==null)return;
		value=((StringOutput)outputTable.get(connectionObj.getName())).value;
	}
	public String Value(){
		if(connectionObj==null){
			value=defaultValue;
			if(!thisKey.equals("<none>")){

    	    	connectionObj=(StringOutput)outputTable.get(thisKey);
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
	public void setConnection(StringOutput connection){
		connectionObj=connection;
	}
	StringOutput connectionObj;
	String name;
	String defaultValue;
	String value;
	String connectionName;
	String pConnectionName;
	cpi.SetString inTable;
	String tableName;
	String thisKey;
	String defCommPrefix;
	OutputElement outputList;
}
