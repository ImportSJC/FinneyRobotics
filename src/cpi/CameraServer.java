package cpi;


public class CameraServer{
	 edu.wpi.first.wpilibj.CameraServer server=edu.wpi.first.wpilibj.CameraServer.getInstance();
	public CameraServer(){}
	public  void runServer(String camX){
		server.setQuality(50);
		server.startAutomaticCapture("camX");
	}
		

}
