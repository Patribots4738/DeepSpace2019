package wrapper;

import org.opencv.core.Mat;
import org.opencv.core.Scalar;

import edu.wpi.cscore.CvSink;
import edu.wpi.cscore.CvSource;
import edu.wpi.cscore.UsbCamera;
import edu.wpi.first.wpilibj.CameraServer;

public class Camera {

    UsbCamera cam;
    int camera = 0;
    
    CvSink input;
    CvSource output;
    
    
    Mat currentFrame;
    
    public Camera(){
        cam = CameraServer.getInstance().startAutomaticCapture();
        cam.setFPS(30);
        cam.setResolution(320, 240);

        currentFrame = new Mat();
        currentFrame.setTo(new Scalar(0,0,0));
        
        input = CameraServer.getInstance().getVideo();
        output = CameraServer.getInstance().putVideo("Video", 320, 240);
    }
    
    
    
    //FIXME: UNSAFE!!! DO NOT REPLICATE
    public void startCamera(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                while(!Thread.interrupted()){
                    camUpdate();
                }
            }
        }).start();
        
    }
    
    public void camUpdate() {
        pushMat(CameraUtils.drawOnImage(updateCapture()));
    }

    public void pushMat(Mat frame){
        output.putFrame(frame);
        frame.release();
    }
    
    public Mat updateCapture(){
        Mat frame = new Mat();
        input.grabFrame(frame);
        currentFrame.release();
        currentFrame = frame.clone();
        return frame;
    }
		
		
	
}