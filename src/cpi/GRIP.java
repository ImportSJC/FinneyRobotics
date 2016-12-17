package cpi;


import java.io.IOException;

/**
 * 
 * @author Thomas Wulff
 *
 *<br>The GRIP class provides a simple way for initializing the GRIP process
 */

public class GRIP {
    private final static String[] GRIP_ARGS = new String[] {
            "/usr/local/frc/JRE/bin/java", "-jar",
            "/home/lvuser/grip.jar", "/home/lvuser/project.grip" };
/**
 * This should be placed in the robotInit of the main code.    
 */
    public static void init(){
        /* Run GRIP in a new process */
        try {
            Runtime.getRuntime().exec(GRIP_ARGS);
        } catch (IOException e) {
            e.printStackTrace();
        }
    	
    }

}
