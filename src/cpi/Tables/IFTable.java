package cpi.Tables;
import edu.wpi.first.wpilibj.networktables.NetworkTable;
import edu.wpi.first.wpilibj.tables.*;
import java.util.Vector;
import cpi.Interface.Support.Classes.OutputElement;
import cpi.Interface.Support.Classes.OutputDiscriptor;


import java.util.HashMap;

public interface IFTable {
HashMap<String, Object> outputTable=new HashMap<String,Object>();
String outputPrefix="/Interface/Outputs/";
String outputPrefix2="/Interface/Outputs";
String inputPrefix="/Interface/Inputs/";
Vector<OutputElement> allElements=new Vector<OutputElement>();
OutputDiscriptor booleanOutputDiscriptor=new OutputDiscriptor();
OutputDiscriptor numberOutputDiscriptor=new OutputDiscriptor();
OutputDiscriptor stringOutputDiscriptor=new OutputDiscriptor();
Vector<OutputDiscriptor> booleanOutputDiscriptorList = new Vector<OutputDiscriptor>();
Vector<OutputDiscriptor> numberOutputDiscriptorList = new Vector<OutputDiscriptor>();
Vector<OutputDiscriptor> stringOutputDiscriptorList = new Vector<OutputDiscriptor>();
}
