package cpi;


import java.io.IOException;


public class GRIP {
    private final static String[] GRIP_ARGS = new String[] {
            "/usr/local/frc/JRE/bin/java", "-jar",
            "/home/lvuser/grip.jar", "/home/lvuser/project.grip" };
    
    public static void init(){
        /* Run GRIP in a new process */
        try {
            Runtime.getRuntime().exec(GRIP_ARGS);
        } catch (IOException e) {
            e.printStackTrace();
        }
    	
    }

}
