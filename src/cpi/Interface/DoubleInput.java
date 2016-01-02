package cpi.Interface;

import cpi.Interface.Support.Classes.OutputElement;
import cpi.Tables.IFTable;
import edu.wpi.first.wpilibj.tables.ITable;
import edu.wpi.first.wpilibj.tables.ITableListener;

public class DoubleInput implements IFTable {
	public DoubleInput(String table,String pname){
		Constructor( table,pname, "<none>",0.0);
	}
	public DoubleInput(String table,String pname,double pdefaultValue){
		Constructor( table,pname, "<none>",pdefaultValue);
	}
	public DoubleInput(String table,String pname,String outputKey){
		Constructor( table,pname, outputKey,0.0);
	}
	public DoubleInput(String table,String pname,String outputKey,double pdefaultValue){
		Constructor(table,pname, outputKey,pdefaultValue);
	}
	private void Constructor(String table,String pname,String pkey,double pdefaultValue){
		BooleanOutput.setNone();
		thisKey=pkey;
		defaultValue=pdefaultValue;
		tableName=table;
    	connectionObj=(DoubleOutput)outputTable.get(thisKey);
		name=pname;
		connectionName=null;
        defCommPrefix=inputPrefix;
        if(tableName.startsWith("/")){
        	defCommPrefix="";
//        	tableName=tableName.substring(1);
        }
		if (connectionObj!=null)connectionName=connectionObj.getName();
			inTable=new cpi.SetString(defCommPrefix+tableName+"/"+name,"Link",thisKey);
		value=pdefaultValue;
		outputList=new OutputElement(tableName+"/"+name,inTable,numberOutputDiscriptor,numberOutputDiscriptorList);
		inTable.addActionListner( new ITableListener(){
			
        	    public void valueChanged(ITable source, String key, Object pvalue, boolean isNew){
        	    	connectionObj=(DoubleOutput)outputTable.get((String )pvalue);
        			inTable.Value((String)pvalue);
        			thisKey=(String)pvalue;
        	    }
          });
		if(connectionObj==null)return;
		value=((DoubleOutput)outputTable.get(connectionObj.getName())).value;
	}
	public double Value(){
		if(connectionObj==null){
			value=defaultValue;
			if(!thisKey.equals("<none>")){

    	    	connectionObj=(DoubleOutput)outputTable.get(thisKey);
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
	public void setConnection(DoubleOutput connection){
		connectionObj=connection;
	}
	DoubleOutput connectionObj;
	String name;
	double defaultValue;
	double value;
	String connectionName;
	String pConnectionName;
	cpi.SetString inTable;
	String tableName;
	String thisKey;
	String defCommPrefix;
	OutputElement outputList;
}
