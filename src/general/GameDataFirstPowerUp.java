package general;

import logging.SimpleLogger;
import logging.SimpleLogger.LogLevel;
import logging.SimpleLogger.LogSubsystem;

public class GameDataFirstPowerUp extends general.GameData {
	public enum FieldSide {
		RIGHT,
		UNKNOWN,
		LEFT,
		}
	
	public GameDataFirstPowerUp() {
	}
	
	public FieldSide getAllianceSwitch(){
        if (allianceSwitch != FieldSide.UNKNOWN)
            return allianceSwitch;
        
        fetchGameData();
        SimpleLogger.log("SWITCH SIDE DETERMINED: " + allianceSwitch, LogLevel.COMP, LogSubsystem.AUTO);
		return allianceSwitch; 
    }

	public FieldSide getScale(){ 
        if (scale != FieldSide.UNKNOWN)
            return scale;
        
        fetchGameData();
        SimpleLogger.log("SCALE SIDE DETERMINED: " + scale, LogLevel.COMP, LogSubsystem.AUTO);
        return scale; 
    }
    public FieldSide getOpponentSwitch(){ 
        if (opponentSwitch != FieldSide.UNKNOWN)
            return opponentSwitch;

        fetchGameData();
        return opponentSwitch; 
    }
    
    protected boolean isGameDataValid(String data){
        return (data != null)
			&& (data.length() == 3
                && (data.charAt(0) == 'L' || data.charAt(0) == 'R')
                && (data.charAt(1) == 'L' || data.charAt(1) == 'R')
                && (data.charAt(2) == 'L' || data.charAt(2) == 'R'));
    }

    private FieldSide allianceSwitch = FieldSide.UNKNOWN;
    private FieldSide scale          = FieldSide.UNKNOWN;
    private FieldSide opponentSwitch = FieldSide.UNKNOWN;

    protected String updateGameData(String data)
    {
    	if (isGameDataValid(data)) {
			this.gameData = data;
	        allianceSwitch  = data.charAt(0) == 'L'
	                            ? FieldSide.LEFT
	                            : FieldSide.RIGHT ; //TODO dont assume that if not left its right. make this return null if neither R or L
	        scale           = data.charAt(1) == 'L'
	                            ? FieldSide.LEFT
	                            : FieldSide.RIGHT ;
	        opponentSwitch  = data.charAt(2) == 'L'
	                            ? FieldSide.LEFT
	                            : FieldSide.RIGHT ;
    	}

    	return data;
    }
}
