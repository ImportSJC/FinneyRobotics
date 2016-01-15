package cpi.Interface.Support.Classes;

import cpi.SetString;
import cpi.NetBoolean;
import cpi.Interface.Support.Classes.OutputDiscriptor;
import cpi.Tables.IFTable;
import edu.wpi.first.wpilibj.tables.*;

import java.util.Vector;

public class OutputElement implements IFTable {
	public OutputElement(String pTable,SetString pInputLink,OutputDiscriptor pOutputInterfaceDiscriptor , Vector<OutputDiscriptor> pOutputDiscriptorList){
		table=pTable;
		outputDiscriptor=pOutputInterfaceDiscriptor;
		outputInterfaceName=pOutputInterfaceDiscriptor.key;
		link=pInputLink;
		if(pOutputDiscriptorList==null){
			setInputLink=new NetBoolean(table+"/"+outputDiscriptor.table,"Set Link",false);
		setInputLink.addActionListner( new ITableListener(){
    	    public void valueChanged(ITable source, String key, Object pvalue, boolean isNew){
    	    	if((boolean)pvalue){
    	    		link.Value(outputInterfaceName);
    	    		setInputLink.Value(false);
    	    	}
    	    }
      });
		}
		
		if(pOutputDiscriptorList==null)return;
		IFTable.allElements.addElement(this);
		int i=0;
		while(i<pOutputDiscriptorList.size()){
//			outputDiscriptorList.add(new OutputDiscriptor(table+"Available Output Interfaces","<none>") );
			new OutputElement(table,link,pOutputDiscriptorList.elementAt(i),null);
			i++;
		}
			
	}
	public void generateElement(){
		if((outputDiscriptor.table==null))return;
		new OutputElement(table,link,outputDiscriptor,null);
	}
	public static void generateAllElements(){
		int i=0;
		while(i<IFTable.allElements.size()){
			IFTable.allElements.elementAt(i).generateElement();
			i++;
		}
	}
	String table;
	NetBoolean setInputLink;
	SetString link;
	String outputInterfaceName;
	OutputDiscriptor outputDiscriptor;
	Vector<OutputDiscriptor> outputDiscriptorList;
}
