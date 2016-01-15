package cpi.Interface.Support.Classes;

public class OutputDiscriptor {
	public OutputDiscriptor(String pTable,String pKey){
		table=pTable;
		key=pKey;
	}
	
	public OutputDiscriptor(){
		table=null;
		key=null;
	}
	
	public String table;
	public String key;
}
