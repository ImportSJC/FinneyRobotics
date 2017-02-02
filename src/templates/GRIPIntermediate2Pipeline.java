package templates;


import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;

public class GRIPIntermediate2Pipeline {
	Mat mat;
	public void process (Mat source0){
		mat=source0;
		Imgproc.rectangle(mat, new Point(50, 50), new Point(200, 200),
				
			
				new Scalar(255, 255, 255), 5);
	}
	public Mat getMat(){
		return mat;
	}
}
