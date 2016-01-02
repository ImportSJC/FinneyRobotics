package cpi.Interface;

import cpi.Interface.Support.Classes.OutputDiscriptor;

public class StringOutput implements cpi.Tables.IFTable {
	public StringOutput(String table,String pname){
		Constructor(table,pname,"");
	}
	public StringOutput(String table,String pname,String pinitialValue){
		Constructor(table,pname,pinitialValue);
	}
	
	private void Constructor(String table,String pname,String pinitialValue){
		setNone();
		value=pinitialValue;
		name=pname;
		tableName=table;
		prefix=tableName.replace("/", "-")+": ";
		outTable=new cpi.NetString(outputPrefix+"Boolean/"+tableName,name,prefix+name);
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
		stringOutputDiscriptorList.add(new OutputDiscriptor(tableName+"/"+name,prefix+name) );
		stringOutputDiscriptor.table=tmp.table;
		stringOutputDiscriptor.key=tmp.key;
			
		}else{
			OutputDiscriptor tmp=new OutputDiscriptor(table,name);
			stringOutputDiscriptorList.add(new OutputDiscriptor(tableName,name) );
			stringOutputDiscriptor.table=tmp.table;
			stringOutputDiscriptor.key=tmp.key;
		}
		cpi.Interface.Support.Classes.OutputElement.generateAllElements();
		stringOutputDiscriptor.table=null;
		stringOutputDiscriptor.key=null;
	}

	String value;
	public String Value(String pvalue){
		value=pvalue;
		System.out.println("StringOutput - value = "+value);
		return value;
	}
	public String getName(){
		return prefix+name;
	}
	
	public static void setNone(){
		
	}

	static StringOutput none= new StringOutput("<none>","<none>");
	String prefix;
	String name;
	String tableName;
	cpi.NetString outTable;
	static cpi.NetString noneTable= new cpi.NetString(outputPrefix2, "<none>","<none>");
	static boolean isOnce=true;
}
