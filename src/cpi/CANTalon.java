/**
 * 
 */
package cpi;

/**
 * @author Robotics
 *
 */

import java.util.Hashtable;
import java.util.Vector;

import cpi.Interface.BooleanInput;
import edu.wpi.first.wpilibj.tables.ITable;
import edu.wpi.first.wpilibj.tables.ITableListener;
import edu.wpi.first.wpilibj.Timer;

public class CANTalon extends edu.wpi.first.wpilibj.CANTalon {
	
static final String OFFLINE="OFF line";		
static final String ONLINE="ON line";		
static final String NONE="<none>";		
static final String TEST_ENABLE="Test Enable";		
static final String DIRECTION_FOREWARD="Foreward";	
static final String DIRECTION_REVERSE="Reverse";	
static final String BRAKE_ON="Braking Enabled";	
static final String BRAKE_OFF="Coasting Enabled";
static final String PERCENTVBUS="PercentVbus mode";
static final String FOLLOWER="Follower mode";
static final String VOLTAGE="Voltage mode";
static final String VOLTAGE_MAX="Max Voltage";
static final String POSITION="Position mode";
static final String POSITION_MAX="Max Position";
static final String SPEED="Speed mode";
static final String SPEED_MAX="Max Speed";
static final String CURRENT="Current mode";
static final String CURRENT_MAX="Max Current";
static final String DISABLED="Disabled mode";
static final int 	TALON_COUNT=32;
static final String SET_VALUE_MULTIPLIER="Set Value Multiplier";

static boolean 		isFirstTalonArray=true;



int 				deviceNumber;
String 				tableName;
String 				name;
double 				direction=1.0;
CANTalon 			talonInstance;
Vector<String> 		aliasList;
boolean				actionListenersAreSet=false;
boolean				isCoreTalon=false;



NetString 			talonPresent;

static CANTalon[] 	talon=new CANTalon[TALON_COUNT];
static CoreDisplay[] talonDisplay=new CoreDisplay[TALON_COUNT];
double 				setValueMultiplier=1.0;
NetBoolean 			talonTestEnable;
NetString 			talonName;
static String 		talonPrefix="CANTalon";
static String 		defaultTable=talonPrefix+"Unspecified Talons";
static NetDouble 	talonTestSpeed=new NetDouble(talonPrefix+"/Test","Test Speed",0.5);
static NetDouble 	talonTestTime=new NetDouble(talonPrefix+"/Test","Test Time",2.0);
static NetBoolean 	talonTestRun=new NetBoolean(talonPrefix+"/Test","Run timed test",false);
static BooleanInput talonRunInterface=new BooleanInput("/"+talonPrefix+"/Test","Run Test","XBox360-Pilot:A Button",false);
static double 		talonSetSpeed=0;
static Vector<CANTalon>		testList=new Vector<CANTalon>();
static Hashtable<java.lang.String,CANTalon> namedTalonMap=new Hashtable<java.lang.String,CANTalon>();
static Hashtable<java.lang.String,NamedDisplay> namedTalonDisplayMap=new Hashtable<java.lang.String,NamedDisplay>();
static Hashtable<java.lang.String,NamedDisplay> namedDisplay=new Hashtable<java.lang.String,NamedDisplay>();


//	Constructors
	 CANTalon(int deviceNumber){
		super(deviceNumber);
		constructor(null,null,deviceNumber,null);
	}
	 CANTalon(int deviceNumber, int controlPeriodMs){
		super(deviceNumber, controlPeriodMs);
		constructor(null,null,deviceNumber,null);
	}
	 CANTalon(String name,int deviceNumber,CANTalon talon){
		super(deviceNumber);
		constructor(null,name,deviceNumber,talon);
	}
	 CANTalon(String name,int deviceNumber, int controlPeriodMs){
		super(deviceNumber, controlPeriodMs);
		constructor(null,name,deviceNumber,null);
	}
	 CANTalon(String tableName,String name,int deviceNumber){
		super(deviceNumber);
		constructor(tableName,name,deviceNumber,null);
	}
	 CANTalon(String tableName,String name,int deviceNumber, int controlPeriodMs,CANTalon talon){
		super(deviceNumber, controlPeriodMs);
		constructor(tableName,name,deviceNumber,talon);
	}
//		End Constructors
	
	void constructor(String tableName,String name,int deviceNumber,CANTalon talon){
		setSafetyEnabled(true);
		super.setExpiration(1000);
		this.tableName=tableName;
		if (this.tableName==null)this.tableName=defaultTable;
		this.deviceNumber=deviceNumber;
		this.name=name;
		createTalonArray();;
		talonInstance=this;
		aliasList= new Vector<String>();
		saveAlias(this.name);
		/*talonTestRun.addActionListner(new ITableListener(){
			public void valueChanged(ITable source, String key, Object pvalue, boolean isNew){
				if ((Boolean)pvalue){
					//talonTestTimer.reset();
					//talonTestTimer.start();
				}
			}
		});*/
		
	}
	
	

	

	
	class CoreDisplay{
		

		int					thisDisplayDeviceNumber;
		boolean				thisIsCreateArray;
		NetDouble 			talonFirmwareVersion;
		SetString 			talonDirection ;	
		String 						talonOldDirection=DIRECTION_FOREWARD;
		NetBoolean					directionSelectForward;
		NetBoolean					directionSelectReverse;
		SetString 			talonBreakEnable;
		String 						talonOldBrake;
		NetBoolean					brakeSelectBraking;
		NetBoolean					brakeSelectCoasting;
		SetString			talonControlMode;
		String						talonOldControlMode;
		NetDouble					setValueMultiplier;
		double							oldSetValueMultiplier;
		NetBoolean					percentVbus;
		NetBoolean					follower;
		NetBoolean					voltage;
		SetDouble						voltageMax;
		double								oldVoltageMax;
		NetBoolean					position;
		SetDouble						positionMax;
		double								oldPositionMax;
		NetBoolean					speed;
		SetDouble						speedMax;
		double								oldSpeedMax;
		NetBoolean					current;
		SetDouble						currentMax;
		double								oldCurrentMax;
		NetBoolean					disabled;
		NetBoolean			testEnable;
		boolean					oldTestEnable;
		CANTalon			thisInstance;
		
		
		CoreDisplay(CANTalon talon,String table){
			String sTmp=OFFLINE;
			thisInstance=talon;
			talon.talonPresent=new NetString(table,"Status",OFFLINE);
			if(talon.GetFirmwareVersion()!=0.0)talon.talonPresent.Value(ONLINE);
				if(talon.talonPresent.Value()==ONLINE){
			talonFirmwareVersion=new NetDouble(table,"Firmware Version",talon.GetFirmwareVersion());
			talonFirmwareVersion.lock();
			testEnable=new NetBoolean(table,"Test Enable",false);
			testEnable.addActionListner(new ITableListener(){
						public void valueChanged(ITable source, String key, Object pvalue, boolean isNew){
							if(oldTestEnable==(boolean)pvalue)return;
							oldTestEnable=(boolean)pvalue;
							if((boolean)pvalue){
							if(CANTalon.testList.contains(thisInstance))return;
								testList.addElement(thisInstance);
							}else{
								testList.remove(thisInstance);
							}
						}
					});
				}
			
			talon.talonPresent.lock();
			
			
			sTmp=DIRECTION_FOREWARD;
			if(talon.direction<0)sTmp=DIRECTION_REVERSE;
			talonOldDirection=sTmp;
			talonDirection=new SetString(table,"Direction",sTmp);
			{
				directionSelectForward=new NetBoolean(table+"/Select Direction","Forward",false);
				directionSelectForward.disableOnHardCode(true);
				directionSelectReverse=new NetBoolean(table+"/Select Direction","Reverse",false);
				directionSelectReverse.disableOnHardCode(true);
			}
			sTmp=BRAKE_OFF;
			if(talon.getBrakeEnableDuringNeutral())sTmp=BRAKE_ON;
			talonOldBrake=sTmp;
			talonBreakEnable=new SetString(table,"Braking Mode",sTmp);
			{
				brakeSelectBraking=new NetBoolean(table+"/Select Neutral Braking Mode","Braking",false);
				brakeSelectBraking.disableOnHardCode(true);
				brakeSelectCoasting=new NetBoolean(table+"/Select Neutral Braking Mode","Coasting",false);
				brakeSelectCoasting.disableOnHardCode(true);
				
			}
			talonOldControlMode=PERCENTVBUS;
			talonControlMode=new SetString(table,"Control Mode",talonOldControlMode);
			{
				setValueMultiplier=new NetDouble(table,SET_VALUE_MULTIPLIER,1.0);
				setValueMultiplier.lock();
				talon.setValueMultiplier=setValueMultiplier.Value();
				percentVbus=new NetBoolean(table+"/Control Mode Settings","Set "+PERCENTVBUS,false);
				percentVbus.disableOnHardCode(true);
				follower=new NetBoolean(table+"/Control Mode Settings","Set "+FOLLOWER,false);
				follower.disableOnHardCode(true);
				voltage=new NetBoolean(table+"/Control Mode Settings","Set "+VOLTAGE,false);
				voltage.disableOnHardCode(true);
				voltageMax=new SetDouble(table+"/Control Mode Settings",VOLTAGE_MAX,12.0);
				position=new NetBoolean(table+"/Control Mode Settings","Set "+POSITION,false);
				position.disableOnHardCode(true);
				positionMax=new SetDouble(table+"/Control Mode Settings",POSITION_MAX,1000);
				speed=new NetBoolean(table+"/Control Mode Settings","Set "+SPEED,false);
				speed.disableOnHardCode(true);
				speedMax=new SetDouble(table+"/Control Mode Settings",SPEED_MAX,1000);
				current=new NetBoolean(table+"/Control Mode Settings","Set "+CURRENT,false);
				current.disableOnHardCode(true);
				currentMax=new SetDouble(table+"/Control Mode Settings",CURRENT_MAX,40);
				disabled=new NetBoolean(table+"/Control Mode Settings","Set "+DISABLED,false);
				disabled.disableOnHardCode(true);
				
			}
					
					thisDisplayDeviceNumber=talon.deviceNumber;
// This display action listeners
					talonDirection.addActionListner(new ITableListener(){
						public void valueChanged(ITable source, String key, Object pvalue, boolean isNew){
							if((String)pvalue==talonOldDirection)return;
							if((String)pvalue==DIRECTION_FOREWARD){
								//if(talon.talonPresent.Value()==ONLINE)talonInstance.reverseOutput(false,0);
								direction=1.0;
								talonOldDirection=DIRECTION_FOREWARD;
								talonDirection.Value(DIRECTION_FOREWARD);
							}
							if((String)pvalue==DIRECTION_REVERSE){
								//if(talon.talonPresent.Value()==ONLINE)talonInstance.reverseOutput(true,0);
								direction=-1.0;
								talonOldDirection=DIRECTION_REVERSE;
								talonDirection.Value(DIRECTION_REVERSE);
							}
						}
					});
					
					
					
					directionSelectForward.addActionListner(new ITableListener(){
						public void valueChanged(ITable source, String key, Object pvalue, boolean isNew){
							if(talonOldControlMode!=DIRECTION_FOREWARD){
								talonDirection.Value(DIRECTION_FOREWARD);
								directionSelectForward.Value(false);
							}
						}
					});
					
					directionSelectReverse.addActionListner(new ITableListener(){
						public void valueChanged(ITable source, String key, Object pvalue, boolean isNew){
							if(talonOldControlMode!=DIRECTION_REVERSE){
								talonDirection.Value(DIRECTION_REVERSE);
								directionSelectReverse.Value(false);
							}
						}
					});	
					
				talonBreakEnable.addActionListner(new ITableListener(){
						public void valueChanged(ITable source, String key, Object pvalue, boolean isNew){
							if((String)pvalue==talonOldBrake)return;
							if((String)pvalue==BRAKE_ON){
								if(talon.talonPresent.Value()==ONLINE)enableBrakeMode(true,0);
								talonOldBrake=BRAKE_ON;
							}
							if((String)pvalue==BRAKE_OFF){
								if(talon.talonPresent.Value()==ONLINE)enableBrakeMode(false,0);
								talonOldBrake=BRAKE_OFF;
								}
							}
				});
					brakeSelectBraking.addActionListner(new ITableListener(){
						public void valueChanged(ITable source, String key, Object pvalue, boolean isNew){
							if(talonOldControlMode!=BRAKE_ON){
								talonBreakEnable.Value(BRAKE_ON);
								brakeSelectBraking.Value(false);
							}
						}
					});
					
					brakeSelectCoasting.addActionListner(new ITableListener(){
						public void valueChanged(ITable source, String key, Object pvalue, boolean isNew){
							if(talonOldControlMode!=BRAKE_OFF){
								talonBreakEnable.Value(BRAKE_OFF);
								brakeSelectCoasting.Value(false);
							}
						}
					});	

				talonControlMode.addActionListner(new ITableListener(){
					public void valueChanged(ITable source, String key, Object pvalue, boolean isNew){
						if((String)pvalue==talonOldControlMode)return;
						switch((String)pvalue){
						case PERCENTVBUS:
							if(talon.talonPresent.Value()==ONLINE)changeControlMode(edu.wpi.first.wpilibj.CANTalon.TalonControlMode.PercentVbus,0);
							talonOldControlMode=PERCENTVBUS;
							talonControlMode.Value(talonOldControlMode);
							setValueMultiplier.unlock();
							setValueMultiplier.Value(1.0);
							setValueMultiplier.lock();
							break;
						case FOLLOWER:
							if(talon.talonPresent.Value()==ONLINE)changeControlMode(edu.wpi.first.wpilibj.CANTalon.TalonControlMode.Follower,0);
							talonOldControlMode=FOLLOWER;
							talonControlMode.Value(talonOldControlMode);
							break;
						case VOLTAGE:
							if(talon.talonPresent.Value()==ONLINE)changeControlMode(edu.wpi.first.wpilibj.CANTalon.TalonControlMode.Voltage,0);
							talonOldControlMode=VOLTAGE;
							talonControlMode.Value(talonOldControlMode);
							setValueMultiplier.unlock();
							setValueMultiplier.Value(voltageMax.Value());
							setValueMultiplier.lock();
							break;
						case POSITION:
							if(talon.talonPresent.Value()==ONLINE)changeControlMode(edu.wpi.first.wpilibj.CANTalon.TalonControlMode.Position,0);
							talonOldControlMode=POSITION;
							talonControlMode.Value(talonOldControlMode);
							setValueMultiplier.unlock();
							setValueMultiplier.Value(positionMax.Value());
							setValueMultiplier.lock();
							break;
						case SPEED:
							if(talon.talonPresent.Value()==ONLINE)changeControlMode(edu.wpi.first.wpilibj.CANTalon.TalonControlMode.Speed,0);
							talonOldControlMode=SPEED;
							talonControlMode.Value(talonOldControlMode);
							setValueMultiplier.unlock();
							setValueMultiplier.Value(speedMax.Value());
							setValueMultiplier.lock();
							break;
						case CURRENT:
							if(talon.talonPresent.Value()==ONLINE)changeControlMode(edu.wpi.first.wpilibj.CANTalon.TalonControlMode.Current,0);
							talonOldControlMode=CURRENT;
							talonControlMode.Value(talonOldControlMode);
							setValueMultiplier.unlock();
							setValueMultiplier.Value(currentMax.Value());
							setValueMultiplier.lock();
							break;
						case DISABLED:
							if(talon.talonPresent.Value()==ONLINE)changeControlMode(edu.wpi.first.wpilibj.CANTalon.TalonControlMode.Disabled,0);
							talonOldControlMode=DISABLED;
							talonControlMode.Value(talonOldControlMode);
							break;
						}
					}
			});
				
				setValueMultiplier.addActionListner(new ITableListener(){
					public void valueChanged(ITable source, String key, Object pvalue, boolean isNew){
						if(oldSetValueMultiplier==(double) pvalue)return;
						talon.setValueMultiplier=(double)pvalue;
						oldSetValueMultiplier=(double)pvalue;
					}});
				
				percentVbus.addActionListner(new ITableListener(){
						public void valueChanged(ITable source, String key, Object pvalue, boolean isNew){
							if(talonOldControlMode!=PERCENTVBUS){
								talonControlMode.Value(PERCENTVBUS);
								percentVbus.Value(false);
							}
						}
					});
				
				follower.addActionListner(new ITableListener(){
						public void valueChanged(ITable source, String key, Object pvalue, boolean isNew){
							if(talonOldControlMode!=FOLLOWER){
								talonControlMode.Value(FOLLOWER);
								follower.Value(false);
							}
						}
					});
				
				voltage.addActionListner(new ITableListener(){
						public void valueChanged(ITable source, String key, Object pvalue, boolean isNew){
							if(talonOldControlMode!=VOLTAGE){
								talonControlMode.Value(VOLTAGE);
								voltage.Value(false);
							}
						}
					});
				voltageMax.addActionListner(new ITableListener(){
					public void valueChanged(ITable source, String key, Object pvalue, boolean isNew){
						if(oldVoltageMax==(double) pvalue)return;
						voltageMax.Value((double)pvalue);
						if(talonControlMode.Value()==VOLTAGE){
							setValueMultiplier.unlock();
							setValueMultiplier.Value((double)pvalue);
							setValueMultiplier.lock();
						}
						oldVoltageMax=(double)pvalue;
					}});
				
				position.addActionListner(new ITableListener(){
						public void valueChanged(ITable source, String key, Object pvalue, boolean isNew){
							if(talonOldControlMode!=POSITION){
								talonControlMode.Value(POSITION);
								position.Value(false);
							}
						}
					});
				positionMax.addActionListner(new ITableListener(){
					public void valueChanged(ITable source, String key, Object pvalue, boolean isNew){
						if(oldPositionMax==(double) pvalue)return;
						positionMax.Value((double)pvalue);
						if(talonControlMode.Value()==POSITION){
							setValueMultiplier.unlock();
							setValueMultiplier.Value((double)pvalue);
							setValueMultiplier.lock();
						}
						oldVoltageMax=(double)pvalue;
					}});
				
				speed.addActionListner(new ITableListener(){
						public void valueChanged(ITable source, String key, Object pvalue, boolean isNew){
							if(talonOldControlMode!=SPEED){
								talonControlMode.Value(SPEED);
								percentVbus.Value(false);
							}
						}
					});
				speedMax.addActionListner(new ITableListener(){
					public void valueChanged(ITable source, String key, Object pvalue, boolean isNew){
						if(oldVoltageMax==(double) pvalue)return;
						speedMax.Value((double)pvalue);
						if(talonControlMode.Value()==SPEED){
							setValueMultiplier.unlock();
							setValueMultiplier.Value((double)pvalue);
							setValueMultiplier.lock();
						}
						oldVoltageMax=(double)pvalue;
					}});
				
				current.addActionListner(new ITableListener(){
						public void valueChanged(ITable source, String key, Object pvalue, boolean isNew){
							if(talonOldControlMode!=CURRENT){
								talonControlMode.Value(CURRENT);
								speed.Value(false);
							}
						}
					});
				currentMax.addActionListner(new ITableListener(){
					public void valueChanged(ITable source, String key, Object pvalue, boolean isNew){
						if(oldVoltageMax==(double) pvalue)return;
						currentMax.Value((double)pvalue);
						if(talonControlMode.Value()==CURRENT){
							setValueMultiplier.unlock();
							setValueMultiplier.Value((double)pvalue);
							setValueMultiplier.lock();
						}
						oldVoltageMax=(double)pvalue;
					}});
				
				disabled.addActionListner(new ITableListener(){
						public void valueChanged(ITable source, String key, Object pvalue, boolean isNew){
							if(talonOldControlMode!=DISABLED){
								talonControlMode.Value(DISABLED);
								disabled.Value(false);
							}
						}
					});
// END This display action listeners
				
				
				}
	}
	
//**************************************************************************************************************************	
	
	class NamedDisplay{
		

		int					thisDisplayDeviceNumber;
		boolean				thisIsCreateArray;
		CANTalon			talonInstance;
		NetDouble 			talonFirmwareVersion;
		//SetDouble			deviceNumber;
		NetDouble			deviceNumber;
		int					oldDeviceNumber;
		NetString 			talonDirection ;
		ITableListener		tlTalonDirection;
		ITableListener		tlVoltageMax;
		ITableListener		tlPositionMax;
		ITableListener		tlSpeedMax;
		ITableListener		tlCurrentMax;
		String 						talonOldDirection=DIRECTION_FOREWARD;
		NetBoolean					directionSelectForward;
		NetBoolean					directionSelectReverse;
		NetString 			talonBreakEnable;
		ITableListener		tlTalonBreakEnable;
		String 						talonOldBrake;
		NetBoolean					brakeSelectBraking;
		NetBoolean					brakeSelectCoasting;
		NetString			talonControlMode;
		ITableListener		tlTalonControlMode;
		String						talonOldControlMode;
		NetDouble					setValueMultiplier;
		double							oldSetValueMultiplier;
		NetBoolean					percentVbus;
		NetBoolean					follower;
		NetBoolean					voltage;
		NetDouble						voltageMax;
		double								oldVoltageMax;
		NetBoolean					position;
		NetDouble						positionMax;
		double								oldPositionMax;
		NetBoolean					speed;
		NetDouble						speedMax;
		double								oldSpeedMax;
		NetBoolean					current;
		NetDouble						currentMax;
		double								oldCurrentMax;
		NetBoolean					disabled;
		NetBoolean			testEnable;
		ITableListener		tlTestEnable;
		boolean					oldTestEnable=false;;
		
		
		NamedDisplay(CANTalon talon,String table){
			thisDisplayDeviceNumber=talon.deviceNumber;
			talonInstance=talon;
			deviceNumber=new NetDouble(table,"Device #",thisDisplayDeviceNumber);
			deviceNumber.lock();
			oldDeviceNumber=(int)deviceNumber.Value();
			String sTmp=OFFLINE;
			if(talon.talonPresent!=null){
				if(talon.talonPresent.Value()==ONLINE){
				sTmp=ONLINE;
			talonFirmwareVersion=new NetDouble(table,"Firmware Version",talon.GetFirmwareVersion());
			talonFirmwareVersion.lock();
			testEnable=new NetBoolean(table,"Test Enable",false);
			testEnable.addActionListner(new ITableListener(){
				public void valueChanged(ITable source, String key, Object pvalue, boolean isNew){
					if((boolean)pvalue==oldTestEnable)return;
					CANTalon.talonDisplay[thisDisplayDeviceNumber].testEnable.Value((boolean)pvalue);
					oldTestEnable=(boolean)pvalue;
				}});
			tlTestEnable=new ITableListener(){
				public void valueChanged(ITable source, String key, Object pvalue, boolean isNew){
					if((boolean)pvalue==oldTestEnable)return;
					testEnable.Value((boolean)pvalue);
					oldTestEnable=(boolean)pvalue;
				}};
				CANTalon.talonDisplay[thisDisplayDeviceNumber].testEnable.addActionListner(tlTestEnable);
			
		}
			}
			talonPresent=new NetString(table,"Status",sTmp);
			talonPresent.lock();
			
			
			
			sTmp=DIRECTION_FOREWARD;
			if(talon.direction<0)sTmp=DIRECTION_REVERSE;
			talonOldDirection=sTmp;
			talonDirection=new NetString(table,"Direction",CANTalon.talonDisplay[thisDisplayDeviceNumber].talonDirection.Value());
			talonDirection.disableOnHardCode(true);
			{
				directionSelectForward=new NetBoolean(table+"/Select Direction","Forward",false);
				directionSelectForward.disableOnHardCode(true);
				directionSelectReverse=new NetBoolean(table+"/Select Direction","Reverse",false);
				directionSelectReverse.disableOnHardCode(true);
			}
			sTmp=BRAKE_OFF;
			if(talon.getBrakeEnableDuringNeutral())sTmp=BRAKE_ON;
			talonOldBrake=sTmp;
			talonBreakEnable=new NetString(table,"Braking Mode",CANTalon.talonDisplay[thisDisplayDeviceNumber].talonBreakEnable.Value());
			talonBreakEnable.disableOnHardCode(true);
			{
				brakeSelectBraking=new NetBoolean(table+"/Select Neutral Braking Mode","Braking",false);
				brakeSelectBraking.disableOnHardCode(true);
				brakeSelectCoasting=new NetBoolean(table+"/Select Neutral Braking Mode","Coasting",false);
				brakeSelectCoasting.disableOnHardCode(true);
				
			}
			talonControlMode=new NetString(table,"Control Mode",CANTalon.talonDisplay[thisDisplayDeviceNumber].talonControlMode.Value());
			talonControlMode.disableOnHardCode(true);
			{
				setValueMultiplier=new NetDouble(table,SET_VALUE_MULTIPLIER,1.0);
				setValueMultiplier.lock();
				talon.setValueMultiplier=setValueMultiplier.Value();
				percentVbus=new NetBoolean(table+"/Control Mode Settings","Set "+PERCENTVBUS,false);
				percentVbus.disableOnHardCode(true);
				follower=new NetBoolean(table+"/Control Mode Settings","Set "+FOLLOWER,false);
				follower.disableOnHardCode(true);
				voltage=new NetBoolean(table+"/Control Mode Settings","Set "+VOLTAGE,false);
				voltage.disableOnHardCode(true);
				voltageMax=new NetDouble(table+"/Control Mode Settings",VOLTAGE_MAX,12.0);
				position=new NetBoolean(table+"/Control Mode Settings","Set "+POSITION,false);
				position.disableOnHardCode(true);
				positionMax=new NetDouble(table+"/Control Mode Settings",POSITION_MAX,1000);
				speed=new NetBoolean(table+"/Control Mode Settings","Set "+SPEED,false);
				speed.disableOnHardCode(true);
				speedMax=new NetDouble(table+"/Control Mode Settings",SPEED_MAX,1000);
				current=new NetBoolean(table+"/Control Mode Settings","Set "+CURRENT,false);
				current.disableOnHardCode(true);
				currentMax=new NetDouble(table+"/Control Mode Settings",CURRENT_MAX,40);
				disabled=new NetBoolean(table+"/Control Mode Settings","Set "+DISABLED,false);
				disabled.disableOnHardCode(true);
			}
// This display action listeners
			/*
			deviceNumber.addActionListner(new ITableListener(){
				public void valueChanged(ITable source, String key, Object pvalue, boolean isNew){
					double tmp=(double)pvalue;
					if((int)tmp>=TALON_COUNT){
						tmp=TALON_COUNT-1;
						deviceNumber.Value((int)tmp);
					}
					if((int)tmp<0){
						tmp=0;
						deviceNumber.Value((int)tmp);
					}
				if(oldDeviceNumber==(int)tmp)	return;
				CANTalon.talonDisplay[thisDisplayDeviceNumber].talonDirection.removeActionListner(tlTalonDirection);
				CANTalon.talonDisplay[thisDisplayDeviceNumber].talonBreakEnable.removeActionListner(tlTalonBreakEnable);
				CANTalon.talonDisplay[thisDisplayDeviceNumber].talonControlMode.removeActionListner(tlTalonControlMode);
				CANTalon.talonDisplay[thisDisplayDeviceNumber].voltageMax.removeActionListner(tlVoltageMax);
				CANTalon.talonDisplay[thisDisplayDeviceNumber].positionMax.removeActionListner(tlPositionMax);
				CANTalon.talonDisplay[thisDisplayDeviceNumber].speedMax.removeActionListner(tlSpeedMax);
				CANTalon.talonDisplay[thisDisplayDeviceNumber].currentMax.removeActionListner(tlCurrentMax);
				if(CANTalon.talonDisplay[thisDisplayDeviceNumber].talonFirmwareVersion!=null){
					if(tlTestEnable!=null)CANTalon.talonDisplay[thisDisplayDeviceNumber].testEnable.removeActionListner(tlTestEnable);
				}
				thisDisplayDeviceNumber=(int)tmp;
				oldDeviceNumber=(int)tmp;
				getInstance(thisDisplayDeviceNumber);
				if(CANTalon.talonDisplay[thisDisplayDeviceNumber].talonFirmwareVersion!=null){
					if(talonFirmwareVersion!=null){
						talonFirmwareVersion=new NetDouble(table,"Firmware Version",CANTalon.talonDisplay[thisDisplayDeviceNumber].talonFirmwareVersion.Value());
						talonFirmwareVersion.lock();
					}
				}else{
					if(talonFirmwareVersion!=null){
						talonFirmwareVersion.unlock();
						talonFirmwareVersion.Value(0);
						talonFirmwareVersion.lock();
					}
				}
				if(CANTalon.talonDisplay[thisDisplayDeviceNumber].talonFirmwareVersion!=null){
					if(tlTestEnable==null){
						tlTestEnable=new ITableListener(){
							public void valueChanged(ITable source, String key, Object pvalue, boolean isNew){
								if((boolean)pvalue==oldTestEnable)return;
								testEnable.Value((boolean)pvalue);
								oldTestEnable=(boolean)pvalue;
							}};
						CANTalon.talonDisplay[thisDisplayDeviceNumber].testEnable.addActionListner(tlTestEnable);
					}
					
				}
				CANTalon.talonDisplay[thisDisplayDeviceNumber].talonDirection.addActionListner(tlTalonDirection);
				CANTalon.talonDisplay[thisDisplayDeviceNumber].talonBreakEnable.addActionListner(tlTalonBreakEnable);
				CANTalon.talonDisplay[thisDisplayDeviceNumber].talonControlMode.addActionListner(tlTalonControlMode);
				CANTalon.talonDisplay[thisDisplayDeviceNumber].voltageMax.addActionListner(tlVoltageMax);
				CANTalon.talonDisplay[thisDisplayDeviceNumber].positionMax.addActionListner(tlPositionMax);
				CANTalon.talonDisplay[thisDisplayDeviceNumber].speedMax.addActionListner(tlSpeedMax);
				CANTalon.talonDisplay[thisDisplayDeviceNumber].currentMax.addActionListner(tlCurrentMax);
				talonDirection.Value(CANTalon.talonDisplay[thisDisplayDeviceNumber].talonDirection.Value());
				talonBreakEnable.Value(CANTalon.talonDisplay[thisDisplayDeviceNumber].talonBreakEnable.Value());
				talonControlMode.Value(CANTalon.talonDisplay[thisDisplayDeviceNumber].talonControlMode.Value());
				voltageMax.Value(CANTalon.talonDisplay[thisDisplayDeviceNumber].voltageMax.Value());
				positionMax.Value(CANTalon.talonDisplay[thisDisplayDeviceNumber].positionMax.Value());
				speedMax.Value(CANTalon.talonDisplay[thisDisplayDeviceNumber].speedMax.Value());
				currentMax.Value(CANTalon.talonDisplay[thisDisplayDeviceNumber].currentMax.Value());
				talonInstance=CANTalon.talon[thisDisplayDeviceNumber];
				
				}});
			*/
			
					talonDirection.addActionListner(new ITableListener(){
						public void valueChanged(ITable source, String key, Object pvalue, boolean isNew){
							if((String)pvalue==talonOldDirection)return;
							if((String)pvalue==DIRECTION_FOREWARD){
								if(talon.talonPresent.Value()==ONLINE)talonInstance.reverseOutput(false,0);
								direction=1.0;
								talonOldDirection=DIRECTION_FOREWARD;
								talonDirection.Value(DIRECTION_FOREWARD);
									CANTalon.talonDisplay[thisDisplayDeviceNumber].talonDirection.Value(DIRECTION_FOREWARD);
								
							}
							if((String)pvalue==DIRECTION_REVERSE){
								if(talon.talonPresent.Value()==ONLINE)talonInstance.reverseOutput(true,0);
								direction=-1.0;
								talonOldDirection=DIRECTION_REVERSE;
								talonDirection.Value(DIRECTION_REVERSE);
									CANTalon.talonDisplay[thisDisplayDeviceNumber].talonDirection.Value(DIRECTION_REVERSE);
								
							}
						}
					});
					
					tlTalonDirection=new ITableListener(){
						public void valueChanged(ITable source, String key, Object pvalue, boolean isNew){
							if((String)pvalue==talonOldDirection)return;
							talonDirection.Value((String)pvalue);
						}};
					CANTalon.talonDisplay[thisDisplayDeviceNumber].talonDirection.addActionListner(tlTalonDirection);
					
					directionSelectForward.addActionListner(new ITableListener(){
						public void valueChanged(ITable source, String key, Object pvalue, boolean isNew){
							if(talonOldControlMode!=DIRECTION_FOREWARD){
								talonDirection.Value(DIRECTION_FOREWARD);
								directionSelectForward.Value(false);
							}
						}
					});
					
					directionSelectReverse.addActionListner(new ITableListener(){
						public void valueChanged(ITable source, String key, Object pvalue, boolean isNew){
							if(talonOldControlMode!=DIRECTION_REVERSE){
								talonDirection.Value(DIRECTION_REVERSE);
								directionSelectReverse.Value(false);
							}
						}
					});

					talonBreakEnable.addActionListner(new ITableListener(){
						public void valueChanged(ITable source, String key, Object pvalue, boolean isNew){
							if((String)pvalue==talonOldBrake)return;
							if((String)pvalue==BRAKE_ON){
								if(talon.talonPresent.Value()==ONLINE)enableBrakeMode(true,0);
								talonOldBrake=BRAKE_ON;
									CANTalon.talonDisplay[talon.deviceNumber].talonBreakEnable.Value(BRAKE_ON);
								
							}
							if((String)pvalue==BRAKE_OFF){
								if(talon.talonPresent.Value()==ONLINE)enableBrakeMode(false,0);
								talonOldBrake=BRAKE_OFF;
									CANTalon.talonDisplay[thisDisplayDeviceNumber].talonBreakEnable.Value(BRAKE_OFF);
								
								}
							}
				});	
					tlTalonBreakEnable=new ITableListener(){
						public void valueChanged(ITable source, String key, Object pvalue, boolean isNew){
							if((String)pvalue==talonOldBrake)return;
							talonBreakEnable.Value((String)pvalue);
						}};
					CANTalon.talonDisplay[thisDisplayDeviceNumber].talonBreakEnable.addActionListner(tlTalonBreakEnable);
					
					brakeSelectBraking.addActionListner(new ITableListener(){
						public void valueChanged(ITable source, String key, Object pvalue, boolean isNew){
							if(talonOldControlMode!=BRAKE_ON){
								talonBreakEnable.Value(BRAKE_ON);
								brakeSelectBraking.Value(false);
							}
						}
					});
					
					brakeSelectCoasting.addActionListner(new ITableListener(){
						public void valueChanged(ITable source, String key, Object pvalue, boolean isNew){
							if(talonOldControlMode!=BRAKE_OFF){
								talonBreakEnable.Value(BRAKE_OFF);
								brakeSelectCoasting.Value(false);
							}
						}
					});	
					
					talonControlMode.addActionListner(new ITableListener(){
						public void valueChanged(ITable source, String key, Object pvalue, boolean isNew){
							if((String)pvalue==talonOldControlMode)return;
							switch((String)pvalue){
							case PERCENTVBUS:
								if(talon.talonPresent.Value()==ONLINE)changeControlMode(edu.wpi.first.wpilibj.CANTalon.TalonControlMode.PercentVbus,0);
								talonOldControlMode=PERCENTVBUS;
								talonControlMode.Value(talonOldControlMode);
								setValueMultiplier.unlock();
								setValueMultiplier.Value(1.0);
								setValueMultiplier.lock();
								CANTalon.talonDisplay[thisDisplayDeviceNumber].talonControlMode.Value(PERCENTVBUS);
								break;
							case FOLLOWER:
								if(talon.talonPresent.Value()==ONLINE)changeControlMode(edu.wpi.first.wpilibj.CANTalon.TalonControlMode.Follower,0);
								talonOldControlMode=FOLLOWER;
								talonControlMode.Value(talonOldControlMode);
								CANTalon.talonDisplay[thisDisplayDeviceNumber].talonControlMode.Value(FOLLOWER);
								break;
							case VOLTAGE:
								if(talon.talonPresent.Value()==ONLINE)changeControlMode(edu.wpi.first.wpilibj.CANTalon.TalonControlMode.Voltage,0);
								talonOldControlMode=VOLTAGE;
								talonControlMode.Value(talonOldControlMode);
								setValueMultiplier.unlock();
								setValueMultiplier.Value(voltageMax.Value());
								setValueMultiplier.lock();
								CANTalon.talonDisplay[thisDisplayDeviceNumber].talonControlMode.Value(VOLTAGE);
								break;
							case POSITION:
								if(talon.talonPresent.Value()==ONLINE)changeControlMode(edu.wpi.first.wpilibj.CANTalon.TalonControlMode.Position,0);
								talonOldControlMode=POSITION;
								talonControlMode.Value(talonOldControlMode);
								setValueMultiplier.unlock();
								setValueMultiplier.Value(positionMax.Value());
								setValueMultiplier.lock();;
								CANTalon.talonDisplay[thisDisplayDeviceNumber].talonControlMode.Value(POSITION);
								break;
							case SPEED:
								if(talon.talonPresent.Value()==ONLINE)changeControlMode(edu.wpi.first.wpilibj.CANTalon.TalonControlMode.Speed,0);
								talonOldControlMode=SPEED;
								talonControlMode.Value(talonOldControlMode);
								setValueMultiplier.unlock();
								setValueMultiplier.Value(speedMax.Value());
								setValueMultiplier.lock();
								CANTalon.talonDisplay[thisDisplayDeviceNumber].talonControlMode.Value(SPEED);
								break;
							case CURRENT:
								if(talon.talonPresent.Value()==ONLINE)changeControlMode(edu.wpi.first.wpilibj.CANTalon.TalonControlMode.Current,0);
								talonOldControlMode=CURRENT;
								talonControlMode.Value(talonOldControlMode);
								setValueMultiplier.unlock();
								setValueMultiplier.Value(currentMax.Value());
								setValueMultiplier.lock();
								CANTalon.talonDisplay[thisDisplayDeviceNumber].talonControlMode.Value(CURRENT);
								break;
							case DISABLED:
								if(talon.talonPresent.Value()==ONLINE)changeControlMode(edu.wpi.first.wpilibj.CANTalon.TalonControlMode.Disabled,0);
								talonOldControlMode=DISABLED;
								talonControlMode.Value(talonOldControlMode);
								CANTalon.talonDisplay[thisDisplayDeviceNumber].talonControlMode.Value(DISABLED);
								break;
							}
						}
				});

					
					voltageMax.addActionListner(new ITableListener(){
						public void valueChanged(ITable source, String key, Object pvalue, boolean isNew){
							if(oldVoltageMax==(double) pvalue)return;
							CANTalon.talonDisplay[thisDisplayDeviceNumber].voltageMax.Value((double)pvalue);
							if(talonControlMode.Value()==VOLTAGE){
								setValueMultiplier.unlock();
								setValueMultiplier.Value((double)pvalue);
								setValueMultiplier.lock();
							}
							oldVoltageMax=(double)pvalue;
						}});	
					
					tlVoltageMax=new ITableListener(){
						public void valueChanged(ITable source, String key, Object pvalue, boolean isNew){
							if((double)pvalue==oldVoltageMax)return;
							voltageMax.Value((double)pvalue);
						}};
					CANTalon.talonDisplay[thisDisplayDeviceNumber].voltageMax.addActionListner(tlVoltageMax);
					
					
					positionMax.addActionListner(new ITableListener(){
						public void valueChanged(ITable source, String key, Object pvalue, boolean isNew){
							if(oldPositionMax==(double) pvalue)return;
							if(talonControlMode.Value()==POSITION){
								setValueMultiplier.unlock();
								setValueMultiplier.Value((double)pvalue);
								setValueMultiplier.lock();
							}
							CANTalon.talonDisplay[thisDisplayDeviceNumber].positionMax.Value((double)pvalue);
							oldPositionMax=(double)pvalue;
						}});	
					
					tlPositionMax=new ITableListener(){
						public void valueChanged(ITable source, String key, Object pvalue, boolean isNew){
							if((double)pvalue==oldPositionMax)return;
							positionMax.Value((double)pvalue);
						}};
					CANTalon.talonDisplay[thisDisplayDeviceNumber].positionMax.addActionListner(tlPositionMax);
					
					speedMax.addActionListner(new ITableListener(){
						public void valueChanged(ITable source, String key, Object pvalue, boolean isNew){
							if(oldSpeedMax==(double) pvalue)return;
							if(talonControlMode.Value()==SPEED){
								setValueMultiplier.unlock();
								setValueMultiplier.Value((double)pvalue);
								setValueMultiplier.lock();
							}
							CANTalon.talonDisplay[thisDisplayDeviceNumber].speedMax.Value((double)pvalue);
							oldSpeedMax=(double)pvalue;
						}});	
					
					tlSpeedMax=new ITableListener(){
						public void valueChanged(ITable source, String key, Object pvalue, boolean isNew){
							if((double)pvalue==oldSpeedMax)return;
							speedMax.Value((double)pvalue);
						}};
					CANTalon.talonDisplay[thisDisplayDeviceNumber].speedMax.addActionListner(tlSpeedMax);
						
					currentMax.addActionListner(new ITableListener(){
						public void valueChanged(ITable source, String key, Object pvalue, boolean isNew){
							if(oldCurrentMax==(double) pvalue)return;
							if(talonControlMode.Value()==CURRENT){
								setValueMultiplier.unlock();
								setValueMultiplier.Value((double)pvalue);
								setValueMultiplier.lock();
							}
							CANTalon.talonDisplay[thisDisplayDeviceNumber].currentMax.Value((double)pvalue);
							oldCurrentMax=(double)pvalue;
						}});	
					
					tlCurrentMax=new ITableListener(){
						public void valueChanged(ITable source, String key, Object pvalue, boolean isNew){
							if((double)pvalue==oldCurrentMax)return;
							currentMax.Value((double)pvalue);
						}};
					CANTalon.talonDisplay[thisDisplayDeviceNumber].currentMax.addActionListner(tlCurrentMax);
						
					
					
					tlTalonControlMode=new ITableListener(){
						public void valueChanged(ITable source, String key, Object pvalue, boolean isNew){
							if((String)pvalue==talonOldControlMode)return;
							talonControlMode.Value((String)pvalue);
						}};
						CANTalon.talonDisplay[thisDisplayDeviceNumber].talonControlMode.addActionListner(tlTalonControlMode);
					

					
					percentVbus.addActionListner(new ITableListener(){
							public void valueChanged(ITable source, String key, Object pvalue, boolean isNew){
								if(talonOldControlMode!=PERCENTVBUS){
									talonControlMode.Value(PERCENTVBUS);
									percentVbus.Value(false);
								}
							}
						});
					
					follower.addActionListner(new ITableListener(){
							public void valueChanged(ITable source, String key, Object pvalue, boolean isNew){
								if(talonOldControlMode!=FOLLOWER){
									talonControlMode.Value(FOLLOWER);
									follower.Value(false);
								}
							}
						});
					
					voltage.addActionListner(new ITableListener(){
							public void valueChanged(ITable source, String key, Object pvalue, boolean isNew){
								if(talonOldControlMode!=VOLTAGE){
									talonControlMode.Value(VOLTAGE);
									voltage.Value(false);
								}
							}
						});
					
					position.addActionListner(new ITableListener(){
							public void valueChanged(ITable source, String key, Object pvalue, boolean isNew){
								if(talonOldControlMode!=POSITION){
									talonControlMode.Value(POSITION);
									position.Value(false);
								}
							}
						});
					
					speed.addActionListner(new ITableListener(){
							public void valueChanged(ITable source, String key, Object pvalue, boolean isNew){
								if(talonOldControlMode!=SPEED){
									talonControlMode.Value(SPEED);
									speed.Value(false);
								}
							}
						});
					
					current.addActionListner(new ITableListener(){
							public void valueChanged(ITable source, String key, Object pvalue, boolean isNew){
								if(talonOldControlMode!=CURRENT){
									talonControlMode.Value(CURRENT);
									current.Value(false);
								}
							}
						});
					
					disabled.addActionListner(new ITableListener(){
							public void valueChanged(ITable source, String key, Object pvalue, boolean isNew){
								if(talonOldControlMode!=DISABLED){
									talonControlMode.Value(DISABLED);
									disabled.Value(false);
								}
							}
						});
// END This display action listeners

	
				
					
				
				
				}
	}
// 	getInstances
	static public CANTalon getInstance(int deviceNumber){
		createTalonArray();
		if(talon[deviceNumber].talonPresent==null){
			talonDisplay[deviceNumber]=CANTalon.talon[deviceNumber].new CoreDisplay(CANTalon.talon[deviceNumber], talonPrefix+"/Device # "+Integer.toString(deviceNumber));
		}
		return talon[deviceNumber];
	}
	
	static public CANTalon getInstance(String table,String name,int defaultDeviceNumber){
		if(!namedDisplay.containsKey(table+"/"+name)){
		getInstance(defaultDeviceNumber);
		namedDisplay.put(table+"/"+name,CANTalon.talon[defaultDeviceNumber].new NamedDisplay(CANTalon.talon[defaultDeviceNumber], table+"/"+name));
		}
		return CANTalon.namedDisplay.get(table+"/"+name).talonInstance;
	}
// 	End getInstances
	
	static void createTalonArray(){
		if(!isFirstTalonArray)return;
		isFirstTalonArray=false;
		int i=0;
		while(i<TALON_COUNT){
			System.out.println("******************************* Create Talon"+i);
			talon[i]=new CANTalon(i);
			talon[i].isCoreTalon=true;
				talon[i].setSafetyEnabled(false);	
			if(talon[i].GetFirmwareVersion()!=0){	
				talonDisplay[i]=CANTalon.talon[i].new CoreDisplay(CANTalon.talon[i], talonPrefix+"/Device # "+Integer.toString(i));			}
			
			i++;
		}
	}
	
	
	// CANTalon overrides
	void changeControlMode(CANTalon.TalonControlMode controlMode,int i){
		talonInstance.changeControlMode(controlMode);
	}
		public void changeControlMode(CANTalon.TalonControlMode controlMode){
			if(isCoreTalon){
				super.changeControlMode(controlMode);
			}else
	{
		System.out.println("changeControlMode method is disabled - use Tableviewer to set ControlMode");
	}
		/*
		switch(controlMode){
		
		case PercentVbus:
			break;
			
		case Follower:
				break;
				
		case Voltage:
				break;
				
		case Position:
				break;
				
		case Speed:
				break;
				
		case Current:
				break;
				
		case Disabled:
				break;
		}
		*/
	}
		public void enableBrakeMode(boolean brake,int i){
			talonInstance.enableBrakeMode(brake);
		}
			public void enableBrakeMode(boolean brake){
				if(isCoreTalon){
					super.enableBrakeMode(brake);
				}else
		{
					System.out.println("enableBrakeMode method is disabled - use Tableviewer to set BrakeMode");
		}
		/*
		if(brake){
			talonDisplay[deviceNumber].talonBreakEnable.Value(BRAKE_ON);
		}
		else{
			talonDisplay[deviceNumber].talonBreakEnable.Value(BRAKE_OFF);
		}
		*/
	}

		void reverseOutput(boolean flip,int i){
			talonInstance.reverseOutput(flip);
			}
				public void reverseOutput(boolean flip){
					if(isCoreTalon){
						super.reverseOutput(flip);
					}else
			{
		System.out.println("reverseOutput method is disabled - use Tableviewer to set reverseOutput");
			}
		/*
		if(flip){
			talonDisplay[deviceNumber].talonDirection.Value(DIRECTION_REVERSE);
		}
		else{
			talonDisplay[deviceNumber].talonDirection.Value(DIRECTION_FOREWARD);
		}
		*/
	}
	
	public void set(double outputValue){
		if(isCoreTalon){
		super.set(outputValue*direction*setValueMultiplier);
		System.out.println("Talon["+deviceNumber+"],  Speed= "+outputValue*direction*setValueMultiplier);
		}else
		{
			talonInstance.set(outputValue);
		}
	}
	

	// END CANTalon overrides
	
	
	public static void robotInit(){
		createTalonArray();
	}
	
	
	public static void disabledInit(){
		talonTestRun.lock();
		talonSetSpeed=0.0;	
		executeTest();
	}
	public static void testInit(){
			talonTestRun.unlock();
	}
	public static void testPeriodic(){
		if(talonRunInterface.Value()){
			talonSetSpeed=talonTestSpeed.Value();
		}else{
			talonSetSpeed=0.0;
		}
			
		executeTest();
	}
	static void executeTest(){
		int i=0;
		while(i<CANTalon.testList.size()){
			CANTalon.testList.get(i).set(talonSetSpeed);
			i++;
		}
	}
		
	boolean isAlias(String alias){
		if(alias==null)return false;
	return false;		
	}

	void saveAlias(String alias){
		if(alias==null)return;
		aliasList.addElement(alias);
		talon[deviceNumber].aliasList.addElement(alias);
		
	}
	void removeAlias(String alias){
		if(alias==null)return;
		aliasList.remove(alias);
		talon[deviceNumber].aliasList.remove(alias);
	}


}
