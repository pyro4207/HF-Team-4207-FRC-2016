package HF2016Utility;

import com.ni.vision.NIVision;
import com.ni.vision.NIVision.Image;

import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class HFCameraPairSwitchable {
	public boolean cameraEnabled = true;
	public static int CAMERA_ONE=0;
	public static int CAMERA_TWO=2;
	
	private String cam1;
	private String cam2;
	
	public int activeCamera = CAMERA_TWO;

	CameraServer server;
	Image frame;
	int sessionFront;
	int sessionBack;
	int curSession;
	
	public HFCameraPairSwitchable(String camera1,String camera2, boolean startEnabled) {
		cameraEnabled = startEnabled;
		cam1 = camera1;
		cam2 = camera2;
		if(cameraEnabled) {
			init();
		}
	}
	public void init() {
        server = CameraServer.getInstance();
//        server.setQuality(40);
        /*  server.setSize(200);
     		server.startAutomaticCapture("cam0")
         */  

		
		if(!cameraEnabled) return;
		try {
			
			frame = NIVision.imaqCreateImage(NIVision.ImageType.IMAGE_RGB, 0);
			SmartDashboard.putString("camerab","INIT Failed 1");
			sessionFront = NIVision.IMAQdxOpenCamera(cam1, NIVision.IMAQdxCameraControlMode.CameraControlModeController);
			SmartDashboard.putString("camerab","INIT Failed 2");
			sessionBack = NIVision.IMAQdxOpenCamera(cam2, NIVision.IMAQdxCameraControlMode.CameraControlModeController);
			curSession = sessionFront;
			NIVision.IMAQdxConfigureGrab(curSession);
			
			
			//simpler camera
//			server.startAutomaticCapture(cam1);
//			server.startAutomaticCapture(cam2);
			
			SmartDashboard.putString("camera","initialized");
			SmartDashboard.putString("camerab","init success");

		} catch(Exception ex) {
			SmartDashboard.putString("camera","INIT FAILED");
			cameraEnabled = false;
		}
	}
	
	public void setEnabled(boolean newValue) {
		cameraEnabled = newValue;
	}
	
	public void displayImages() {
		if(!cameraEnabled) {
			SmartDashboard.putString("Camera", "disabled can't display");			
			return;
		}
		SmartDashboard.putString("Display", "ThatString");
		try {
			NIVision.IMAQdxGrab(curSession, frame, 1);
			server.
			server.setImage(frame);
			SmartDashboard.putString("Camera", "imageupdated");			
//	      	CameraServer.getInstance().setImage(frame);
		} catch (Exception e) {
			SmartDashboard.putString("Camera", "error");			
		}
		SmartDashboard.putString("Display", "ThisString");

	}

	public void toggleCamera() {
		if(!cameraEnabled) return;
		NIVision.IMAQdxStopAcquisition(curSession);
		if(activeCamera == CAMERA_ONE) {
			activeCamera = CAMERA_TWO;
			curSession = sessionBack;
			SmartDashboard.putString("Camera", "2");
		}
		else {
			activeCamera = CAMERA_ONE;
			curSession = sessionFront;
			SmartDashboard.putString("Camera", "0");
		}
		NIVision.IMAQdxConfigureGrab(curSession);		
	}
	
    public void setCamera(int newCamera) {
    	if(!cameraEnabled) return;
    	NIVision.IMAQdxStopAcquisition(curSession);
    	if(newCamera == CAMERA_TWO) {
    		activeCamera = CAMERA_TWO;
    		curSession = sessionBack;
    		SmartDashboard.putString("Camera", "2");
    	}
    	else {
    		activeCamera = CAMERA_ONE;
    		curSession = sessionFront;
    		SmartDashboard.putString("Camera", "0");
    	}
    	NIVision.IMAQdxConfigureGrab(curSession);
    		
    }


}
