package wrapper;

import org.opencv.core.Mat;
import org.opencv.core.Core;
import org.opencv.core.Point;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;

public class CameraUtils{
	
	public static Mat drawOnImage(Mat frame){
		Mat dst = frame; //This is your destination mat set it as the input and output of the draw functions
		Core.flip(frame, dst, -1);
		dst = drawCrosshair(dst);
		//dst = drawDepthLine(dst);
		return dst;
	}
	
	public static Mat drawDepthLine(Mat frame){
		Imgproc.line(frame, new Point(frame.width() * .75, frame.height() / 1.6), new Point(frame.width() , frame.height()), new Scalar(0, 0, 0, 127), 6);
		Imgproc.line(frame, new Point(frame.width() * .25, frame.height() / 1.6), new Point(0 , frame.height()), new Scalar(0, 0, 0, 127), 6);
		return frame;
	} 
	
	public static Mat drawCrosshair(Mat frame){
		Imgproc.circle(frame, new Point(frame.width() / 2, frame.height() / 2), 5, new Scalar(0, 128, 0));
		return frame;
	}
}