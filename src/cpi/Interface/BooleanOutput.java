package cpi.Interface;

import cpi.Interface.Support.Classes.OutputDiscriptor;
public class BooleanOutput implements cpi.Tables.IFTable {
	public BooleanOutput(String table,String pname){
		Constructor(table,pname,false);
	}
	public BooleanOutput(String table,String pname,boolean pinitialValue){
		Constructor(table,pname,pinitialValue);
	}
	
	private void Constructor(String table,String pname,boolean pinitialValue){
		value=pinitialValue;
		name=pname;
		tableName=table;
		prefix=tableName.replace("/", "-");
		if(table!="<none>")prefix=prefix+":";
		outTable=new cpi.NetString(outputPrefix+"Boolean/"+tableName,name,prefix+name);
		outTable.lock();
		outputTable.put(prefix+name, this);
		if(isOnce){
		setNone();
		isOnce=false;
		}
		if(table!="<none>"){
		OutputDiscriptor tmp=new OutputDiscriptor(table+"/"+name,prefix+name);
		booleanOutputDiscriptorList.add(new OutputDiscriptor(tableName+"/"+name,prefix+name) );
		booleanOutputDiscriptor.table=tmp.table;
		booleanOutputDiscriptor.key=tmp.key;
			
		}else{
			OutputDiscriptor tmp=new OutputDiscriptor(table,name);
			booleanOutputDiscriptorList.add(new OutputDiscriptor(tableName,name) );
			booleanOutputDiscriptor.table=tmp.table;
			booleanOutputDiscriptor.key=tmp.key;
		}
		cpi.Interface.Support.Classes.OutputElement.generateAllElements();
		booleanOutputDiscriptor.table=null;
		booleanOutputDiscriptor.key=null;
	}

	boolean value;
	public boolean Value(boolean pvalue){
		value=pvalue;
		return value;
	}
	public String getName(){
		return prefix+name;
	}
	public static void setNone(){
	}
	public static BooleanOutput none= new BooleanOutput("<none>","<none>");
	String prefix;
	String name;
	String tableName;
	cpi.NetString outTable;
	static boolean isOnce=true;
}
