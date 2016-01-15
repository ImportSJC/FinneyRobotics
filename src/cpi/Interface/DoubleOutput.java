package cpi.Interface;

import cpi.Interface.Support.Classes.OutputDiscriptor;

public class DoubleOutput implements cpi.Tables.IFTable {
	public DoubleOutput(String table,String pname){
		Constructor(table,pname,0.0);
	}
	public DoubleOutput(String table,String pname,double pinitialValue){
		Constructor(table,pname,pinitialValue);
	}
	
	private void Constructor(String table,String pname,double pinitialValue){
		setNone();
		value=pinitialValue;
		name=pname;
		tableName=table;
		prefix=tableName.replace("/", "-")+": ";
		outTable=new cpi.NetString(outputPrefix+"Double/"+tableName,name,prefix+name);
		outTable.lock();
		if(!outputTable.containsKey("<none>")){
			outputTable.put("<none>", null);
		}
		outputTable.put(prefix+name, this);
		if(isOnce){
		setNone();
		isOnce=false;
		}
		if(table!="<none>"){
		OutputDiscriptor tmp=new OutputDiscriptor(table+"/"+name,prefix+name);
		numberOutputDiscriptorList.add(new OutputDiscriptor(tableName+"/"+name,prefix+name) );
		numberOutputDiscriptor.table=tmp.table;
		numberOutputDiscriptor.key=tmp.key;
			
		}else{
			OutputDiscriptor tmp=new OutputDiscriptor(table,name);
			numberOutputDiscriptorList.add(new OutputDiscriptor(tableName,name) );
			numberOutputDiscriptor.table=tmp.table;
		}
		cpi.Interface.Support.Classes.OutputElement.generateAllElements();
		numberOutputDiscriptor.table=null;
		numberOutputDiscriptor.key=null;
	}

	double value;
	public double Value(double pvalue){
		value=pvalue;
		return value;
	}
	public String getName(){
		return prefix+name;
	}
	
	public static void setNone(){
	}

	static DoubleOutput none= new DoubleOutput("<none>","<none>");
	String prefix;
	String name;
	String tableName;
	cpi.NetString outTable;
	static cpi.NetString noneTable= new cpi.NetString(outputPrefix2, "<none>","<none>");
	static boolean isOnce=true;
}

