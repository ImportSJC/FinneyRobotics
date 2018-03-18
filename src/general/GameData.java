package general;

import edu.wpi.first.wpilibj.DriverStation;

public class GameData {
	protected String gameData;
	
	public GameData() {
	}
	
	protected boolean isGameDataValid() {
		return this.isGameDataValid(gameData);
	}
	
	protected boolean isGameDataValid(String data) {
		return (data != null) && (data != "");
	}
	
	protected String getGameData() {
		if (isGameDataValid(gameData))
			return gameData;
		
		return updateGameData( fetchGameData() );
	}
	
	protected String fetchGameData() {
		if (isGameDataValid(gameData))
			return gameData;
		
		return updateGameData( getGameSpecificMessage() );
	}

	protected String updateGameData(String data) {
		if (isGameDataValid(data))
			this.gameData = data;
		
		return data;
	}
	
	protected String getGameSpecificMessage(){
		return DriverStation.getInstance().getGameSpecificMessage();
	}
}
