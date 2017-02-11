package org.usfirst.frc.team1405.robot.Vision.pipelines;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.HashMap;

import org.opencv.core.*;
import org.opencv.core.Core.*;
import org.opencv.features2d.FeatureDetector;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.*;
import org.opencv.objdetect.*;

import edu.wpi.first.wpilibj.networktables.NetworkTable;
import edu.wpi.first.wpilibj.DriverStation;

/**
* GeneralDetectionPipeline class.
*
* <p>An OpenCV pipeline generated by GRIP.
*
* @author GRIP
*/
public class GeneralDetectionPipeline {
	
	static final String SELECTION="Selection";
	static final String OUTPUT_CONTROL="Select view";	
	static final String SOURCE="1 - Source";	
	static final String HSV_THRESHOLD="2 - HSV Threshold";	
	static final String ERODE="3 - Erode";	
	static final String DILATE="4 - Dilate";	
	static final String FIND_CONTOURS="5 - Find contours";	
	static final String FILTER_CONTOURS="6 - Filter contours";
	
	
	NetworkTable table;
	static final String HSV_THRESHOLD_HUE_LOW="HSV/Hue Low Threshold";
	static final String HSV_THRESHOLD_HUE_HIGH="HSV/Hue High Threshold";
	static final String HSV_THRESHOLD_SATURATION_HIGH="HSV/Saturation High";
	static final String HSV_THRESHOLD_SATURATION_LOW="HSV/Saturation Low";
	static final String HSV_THRESHOLD_VALUE_LOW="HSV/Value Low";
	static final String HSV_THRESHOLD_VALUE_HIGH="HSV/Value High";

	double hsvThresholdHueLow;
	double hsvThresholdHueHigh;
	double hsvThresholdSaturationLow;
	double hsvThresholdSaturationHigh;
	double hsvThresholdValueLow;
	double hsvThresholdValueHigh;
	
	static final String ERODE_BOARDER="Erode-Dilate/Erode Boarder";		
	static final String ERODE_ITERATIONS="Erode-Dilate/Erode Iterations";	
	static final String DILATE_BOARDER="Erode-Dilate/Dilate Boarder";		
	static final String DILATE_ITERATIONS="Erode-Dilate/Dilate Iterations";

	String erodeBoarder;
	double erodeIterations;
	String dilateBoarder;
	double dilateIterations;
	
	static final String ALLOWED_BOARDER_TYPES="Allowed Boarder Types";
	
	static final String BOARDER_CONSTANT="BOARDER_CONSTANT";
	static final String BOARDER_REPLECATE="BOARDER_REPLECATE";	
	static final String BOARDER_REFLECT="BOARDER_REFLECT";
	static final String BOARDER_WRAP="BOARDER_WRAP";
	static final String BOARDER_REFLECT_101="BOARDER_REFLECT_101";
	static final String BOARDER_TRANSPARENT="BOARDER_TRANSPARENT";
	static final String BOARDER_REFLECT101="BOARDER_REFLECT101";
	static final String BOARDER_DEFAULT="BOARDER_DEFAULT";
	static final String BOARDER_ISOLATED="BOARDER_ISOLATED";


	static final String FILTER_CONTOURS_MIN_AREA="Filter Contours/Min Area";	
	static final String FILTER_CONTOURS_MIN_PERIMETER="Filter Contours/Min Perimeter";
	static final String FILTER_CONTOURS_MIN_WIDTH="Filter Contours/Min Width";
	static final String FILTER_CONTOURS_MAX_WIDTH="Filter Contours/Max Width";
	static final String FILTER_CONTOURS_MIN_HEIGHT="Filter Contours/Min Height";
	static final String FILTER_CONTOURS_MAX_HEIGHT="Filter Contours/Max Height";
	static final String FILTER_CONTOURS_SOLIDITY_LOW="Filter Contours/Solidity (Low)";
	static final String FILTER_CONTOURS_SOLIDITY_HIGH="Filter Contours/Solidity (High)";
	static final String FILTER_CONTOURS_MIN_VERTICES="Filter Contours/Min Vertices";
	static final String FILTER_CONTOURS_MAX_VERTICES="Filter Contours/Max Vertices";
	static final String FILTER_CONTOURS_MIN_RATIO="Filter Contours/Min Ratio";
	static final String FILTER_CONTOURS_MAX_RATIO="Filter Contours/Max Ratio";

	double filterContoursMinArea;
	double filterContoursMinPerimeter;
	double filterContoursMinWidth;
	double filterContoursMaxWidth;
	double filterContoursMinHeight;
	double filterContoursMaxHeight;
	double filterContoursSolidityLow;
	double filterContoursSolidityHight;
	double filterContoursMinVertices;
	double filterContoursMaxVertices;
	double filterContoursMinRatio;
	double filterContoursMaxRatio;
	String outputID="1";
	Mat hsvThresholdInput;
	
	public GeneralDetectionPipeline(String table){
		this.table=NetworkTable.getTable(table);
		
		this.table.putString(OUTPUT_CONTROL+"/"+SELECTION, "1");
		this.table.putBoolean(OUTPUT_CONTROL+"/"+SOURCE, true);
		this.table.putBoolean(OUTPUT_CONTROL+"/"+HSV_THRESHOLD, false);
		this.table.putBoolean(OUTPUT_CONTROL+"/"+ERODE, false);
		this.table.putBoolean(OUTPUT_CONTROL+"/"+DILATE, false);
		this.table.putBoolean(OUTPUT_CONTROL+"/"+FIND_CONTOURS, false);
		this.table.putBoolean(OUTPUT_CONTROL+"/"+FILTER_CONTOURS, false);
		
		
		this.table.putNumber(HSV_THRESHOLD_HUE_LOW, hsvThresholdHueLow=this.table.getNumber(HSV_THRESHOLD_HUE_LOW, 0.0));
		this.table.putNumber(HSV_THRESHOLD_HUE_HIGH, hsvThresholdHueHigh=this.table.getNumber(HSV_THRESHOLD_HUE_HIGH, 180.0));
		this.table.putNumber(HSV_THRESHOLD_SATURATION_LOW,hsvThresholdSaturationLow= this.table.getNumber(HSV_THRESHOLD_SATURATION_LOW, 0.0));
		this.table.putNumber(HSV_THRESHOLD_SATURATION_HIGH, hsvThresholdSaturationHigh=this.table.getNumber(HSV_THRESHOLD_SATURATION_HIGH, 255.0));
		this.table.putNumber(HSV_THRESHOLD_VALUE_LOW, hsvThresholdValueLow=this.table.getNumber(HSV_THRESHOLD_VALUE_LOW, 0.0));
		this.table.putNumber(HSV_THRESHOLD_VALUE_HIGH, hsvThresholdValueHigh=this.table.getNumber(HSV_THRESHOLD_VALUE_HIGH, 255.0));

		this.table.setPersistent(HSV_THRESHOLD_HUE_LOW);
		this.table.setPersistent(HSV_THRESHOLD_HUE_HIGH);
		this.table.setPersistent(HSV_THRESHOLD_SATURATION_LOW);
		this.table.setPersistent(HSV_THRESHOLD_SATURATION_HIGH);
		this.table.setPersistent(HSV_THRESHOLD_VALUE_LOW);
		this.table.setPersistent(HSV_THRESHOLD_VALUE_HIGH);
		
		
		this.table.putString(ALLOWED_BOARDER_TYPES, "{ "+BOARDER_CONSTANT+", "+BOARDER_REPLECATE+", "+BOARDER_REPLECATE+", "+
														BOARDER_REFLECT+", "+BOARDER_WRAP+", "+BOARDER_REFLECT_101+", "+
														BOARDER_TRANSPARENT+", "+BOARDER_REFLECT101+", "+BOARDER_DEFAULT+", "+BOARDER_ISOLATED);
		this.table.putString(ERODE_BOARDER,erodeBoarder= this.table.getString(ERODE_BOARDER, BOARDER_CONSTANT));
		this.table.putNumber(ERODE_ITERATIONS, erodeIterations=this.table.getNumber(ERODE_ITERATIONS, 1));
		this.table.putString(DILATE_BOARDER, dilateBoarder=this.table.getString(DILATE_BOARDER, BOARDER_CONSTANT));
		this.table.putNumber(DILATE_ITERATIONS,dilateIterations= this.table.getNumber(ERODE_ITERATIONS, 1));
		

		this.table.setPersistent(ERODE_BOARDER);
		this.table.setPersistent(ERODE_ITERATIONS);
		this.table.setPersistent(DILATE_BOARDER);
		this.table.setPersistent(DILATE_ITERATIONS);

		this.table.putNumber(FILTER_CONTOURS_MIN_AREA,filterContoursMinArea= this.table.getNumber(FILTER_CONTOURS_MIN_AREA, 0));
		this.table.putNumber(FILTER_CONTOURS_MIN_PERIMETER,filterContoursMinPerimeter= this.table.getNumber(FILTER_CONTOURS_MIN_PERIMETER, 0));
		this.table.putNumber(FILTER_CONTOURS_MIN_WIDTH,filterContoursMinWidth= this.table.getNumber(FILTER_CONTOURS_MIN_WIDTH, 0));
		this.table.putNumber(FILTER_CONTOURS_MAX_WIDTH, filterContoursMaxWidth=this.table.getNumber(FILTER_CONTOURS_MAX_WIDTH, 1000));
		this.table.putNumber(FILTER_CONTOURS_MIN_HEIGHT,filterContoursMinHeight= this.table.getNumber(FILTER_CONTOURS_MIN_HEIGHT, 0));
		this.table.putNumber(FILTER_CONTOURS_MAX_HEIGHT, filterContoursMaxHeight=this.table.getNumber(FILTER_CONTOURS_MAX_HEIGHT, 1000));
		this.table.putNumber(FILTER_CONTOURS_SOLIDITY_LOW,filterContoursSolidityLow= this.table.getNumber(FILTER_CONTOURS_SOLIDITY_LOW, 0));
		this.table.putNumber(FILTER_CONTOURS_SOLIDITY_HIGH,filterContoursSolidityHight= this.table.getNumber(FILTER_CONTOURS_SOLIDITY_HIGH, 100));
		this.table.putNumber(FILTER_CONTOURS_MIN_VERTICES,filterContoursMinVertices= this.table.getNumber(FILTER_CONTOURS_MIN_VERTICES, 0));
		this.table.putNumber(FILTER_CONTOURS_MAX_VERTICES,filterContoursMaxVertices= this.table.getNumber(FILTER_CONTOURS_MAX_VERTICES, 1000000));
		this.table.putNumber(FILTER_CONTOURS_MIN_RATIO,filterContoursMinRatio= this.table.getNumber(FILTER_CONTOURS_MIN_RATIO, 0));
		this.table.putNumber(FILTER_CONTOURS_MAX_RATIO,filterContoursMaxRatio= this.table.getNumber(FILTER_CONTOURS_MAX_RATIO, 1000));
		

		this.table.setPersistent(FILTER_CONTOURS_MIN_AREA);
		this.table.setPersistent(FILTER_CONTOURS_MIN_PERIMETER);
		this.table.setPersistent(FILTER_CONTOURS_MIN_WIDTH);
		this.table.setPersistent(FILTER_CONTOURS_MAX_WIDTH);
		this.table.setPersistent(FILTER_CONTOURS_MIN_HEIGHT);
		this.table.setPersistent(FILTER_CONTOURS_MAX_HEIGHT);
		this.table.setPersistent(FILTER_CONTOURS_SOLIDITY_LOW);
		this.table.setPersistent(FILTER_CONTOURS_SOLIDITY_HIGH);
		this.table.setPersistent(FILTER_CONTOURS_MIN_VERTICES);
		this.table.setPersistent(FILTER_CONTOURS_MAX_VERTICES);
		this.table.setPersistent(FILTER_CONTOURS_MIN_RATIO);
		this.table.setPersistent(FILTER_CONTOURS_MAX_RATIO);
	}

	//Outputs
	private Mat hsvThresholdOutput = new Mat();
	private Mat cvErodeOutput = new Mat();
	private Mat selectedOutput = new Mat();
	private Mat cvDilateOutput = new Mat();
	Mat blankFrame= new Mat();
	private ArrayList<MatOfPoint> findContoursOutput = new ArrayList<MatOfPoint>();
	private ArrayList<MatOfPoint> filterContoursOutput = new ArrayList<MatOfPoint>();
	
	static {
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
	}

	/**
	 * This is the primary method that runs the entire pipeline and updates the outputs.
	 */
	public void process(Mat source0) {
		// Step HSV_Threshold0:
		hsvThresholdInput = source0;

		if(DriverStation.getInstance().isDisabled()){
		hsvThresholdHueLow=table.getNumber(HSV_THRESHOLD_HUE_LOW, 0);
		hsvThresholdHueHigh=table.getNumber(HSV_THRESHOLD_HUE_HIGH, 180);
		hsvThresholdSaturationLow=table.getNumber(HSV_THRESHOLD_SATURATION_LOW, 0);
		hsvThresholdSaturationHigh=table.getNumber(HSV_THRESHOLD_SATURATION_HIGH, 255);
		hsvThresholdValueLow=table.getNumber(HSV_THRESHOLD_VALUE_LOW, 0);
		hsvThresholdValueHigh=table.getNumber(HSV_THRESHOLD_VALUE_HIGH, 255);
		}
		
		double[] hsvThresholdHue = {hsvThresholdHueLow,hsvThresholdHueHigh};
		double[] hsvThresholdSaturation ={ hsvThresholdSaturationLow,hsvThresholdSaturationHigh};
		double[] hsvThresholdValue = {hsvThresholdValueLow,hsvThresholdValueHigh};
		hsvThreshold(hsvThresholdInput, hsvThresholdHue, hsvThresholdSaturation, hsvThresholdValue, hsvThresholdOutput);
		
	//  Create a blank (black) Mat		
		double[] hsvThresholdHueBlank = {hsvThresholdHueLow,hsvThresholdHueHigh};
		double[] hsvThresholdSaturationBlank ={ hsvThresholdSaturationLow,hsvThresholdSaturationHigh};
		double[] hsvThresholdValueBlank = {hsvThresholdValueLow,hsvThresholdValueHigh};

		hsvThreshold(hsvThresholdInput,hsvThresholdHueBlank,hsvThresholdSaturationBlank,hsvThresholdValueBlank,blankFrame);

		if(DriverStation.getInstance().isDisabled()){
			erodeBoarder=table.getString(ERODE_BOARDER, BOARDER_CONSTANT);
			erodeIterations=table.getNumber(ERODE_ITERATIONS, 1);
			dilateBoarder=table.getString(DILATE_BOARDER, BOARDER_CONSTANT);
			dilateIterations=table.getNumber(DILATE_ITERATIONS, 1);
		}
		
		// Step CV_erode0:
		int boarderConstant=getBoarder(erodeBoarder);
		Mat cvErodeSrc = hsvThresholdOutput;
		Mat cvErodeKernel = new Mat();
		Point cvErodeAnchor = new Point(-1, -1);
		double cvErodeIterations = erodeIterations ;
		int cvErodeBordertype = boarderConstant;
		Scalar cvErodeBordervalue = new Scalar(-1);
		cvErode(cvErodeSrc, cvErodeKernel, cvErodeAnchor, cvErodeIterations, cvErodeBordertype, cvErodeBordervalue, cvErodeOutput);

		// Step CV_dilate0:
		boarderConstant=getBoarder(dilateBoarder);
		Mat cvDilateSrc = cvErodeOutput;
		Mat cvDilateKernel = new Mat();
		Point cvDilateAnchor = new Point(-1, -1);
		double cvDilateIterations = dilateIterations;
		int cvDilateBordertype = boarderConstant;
		Scalar cvDilateBordervalue = new Scalar(-1);
		cvDilate(cvDilateSrc, cvDilateKernel, cvDilateAnchor, cvDilateIterations, cvDilateBordertype, cvDilateBordervalue, cvDilateOutput);

		// Step Find_Contours0:
		Mat findContoursInput = cvDilateOutput;
		boolean findContoursExternalOnly = false;
		findContours(findContoursInput, findContoursExternalOnly, findContoursOutput);
		
		if(DriverStation.getInstance().isDisabled()){
			 filterContoursMinArea=table.getNumber(FILTER_CONTOURS_MIN_AREA, 0);
			 filterContoursMinPerimeter= table.getNumber(FILTER_CONTOURS_MIN_PERIMETER, 0);
			 filterContoursMinWidth=table.getNumber(FILTER_CONTOURS_MIN_WIDTH, 0);
			 filterContoursMaxWidth=table.getNumber(FILTER_CONTOURS_MAX_WIDTH, 1000);
			 filterContoursMinHeight=table.getNumber(FILTER_CONTOURS_MIN_HEIGHT, 0);
			 filterContoursMaxHeight=table.getNumber(FILTER_CONTOURS_MAX_HEIGHT, 1000);
			 filterContoursSolidityLow=table.getNumber(FILTER_CONTOURS_SOLIDITY_LOW, 0);
			 filterContoursSolidityHight=table.getNumber(FILTER_CONTOURS_SOLIDITY_HIGH, 100);
			 filterContoursMinVertices= table.getNumber(FILTER_CONTOURS_MIN_VERTICES, 0);
			 filterContoursMaxVertices=table.getNumber(FILTER_CONTOURS_MAX_VERTICES, 1000000);
			 filterContoursMinRatio=table.getNumber(FILTER_CONTOURS_MIN_RATIO, 0);
			 filterContoursMaxRatio=table.getNumber(FILTER_CONTOURS_MAX_RATIO, 1000);
		}
		
		// Step Filter_Contours0:
		ArrayList<MatOfPoint> filterContoursContours = findContoursOutput;
		double[] filterContoursSolidity = {filterContoursSolidityLow, filterContoursSolidityHight};
		filterContours(filterContoursContours, filterContoursMinArea, filterContoursMinPerimeter, filterContoursMinWidth, filterContoursMaxWidth, filterContoursMinHeight, filterContoursMaxHeight, filterContoursSolidity, filterContoursMaxVertices, filterContoursMinVertices, filterContoursMinRatio, filterContoursMaxRatio, filterContoursOutput);

	}

	/**
	 * This method is a generated getter for the output of a HSV_Threshold.
	 * @return Mat output from HSV_Threshold.
	 */
	public Mat hsvThresholdOutput() {
		return hsvThresholdOutput;
	}

	/**
	 * This method is a generated getter for the output of a CV_erode.
	 * @return Mat output from CV_erode.
	 */
	public Mat cvErodeOutput() {
		return cvErodeOutput;
	}

	/**
	 * This method is a generated getter for the output of a CV_dilate.
	 * @return Mat output from CV_dilate.
	 */
	public Mat cvDilateOutput() {
		return cvDilateOutput;
	}

	/**
	 * This method is a generated getter for the output of a Find_Contours.
	 * @return ArrayList<MatOfPoint> output from Find_Contours.
	 */
	public Mat findContoursOutput() {
		Mat mat=blankFrame;
		Imgproc.drawContours(mat, findContoursOutput, 0, new Scalar(255,0,255));
		
		return mat;
	}

	/**
	 * This method is a generated getter for the output of a Filter_Contours.
	 * @return ArrayList<MatOfPoint> output from Filter_Contours.
	 */
	public Mat filterContoursOutput() {
		Mat mat=blankFrame;
		Imgproc.drawContours(mat, findContoursOutput, 0, new Scalar(0,255,255));
		return mat;
	}



	/**
	 * Segment an image based on hue, saturation, and value ranges.
	 *
	 * @param input The image on which to perform the HSL threshold.
	 * @param hue The min and max hue
	 * @param sat The min and max saturation
	 * @param val The min and max value
	 * @param output The image in which to store the output.
	 */
	private void hsvThreshold(Mat input, double[] hue, double[] sat, double[] val,
	    Mat out) {
		Imgproc.cvtColor(input, out, Imgproc.COLOR_BGR2HSV);
		Core.inRange(out, new Scalar(hue[0], sat[0], val[0]),
			new Scalar(hue[1], sat[1], val[1]), out);
	}
	
	/**
	 * Expands area of lower value in an image.
	 * @param src the Image to erode.
	 * @param kernel the kernel for erosion.
	 * @param anchor the center of the kernel.
	 * @param iterations the number of times to perform the erosion.
	 * @param borderType pixel extrapolation method.
	 * @param borderValue value to be used for a constant border.
	 * @param dst Output Image.
	 */
	private void cvErode(Mat src, Mat kernel, Point anchor, double iterations,
		int borderType, Scalar borderValue, Mat dst) {
		if (kernel == null) {
			kernel = new Mat();
		}
		if (anchor == null) {
			anchor = new Point(-1,-1);
		}
		if (borderValue == null) {
			borderValue = new Scalar(-1);
		}
		Imgproc.erode(src, dst, kernel, anchor, (int)iterations, borderType, borderValue);
	}

	/**
	 * Expands area of higher value in an image.
	 * @param src the Image to dilate.
	 * @param kernel the kernel for dilation.
	 * @param anchor the center of the kernel.
	 * @param iterations the number of times to perform the dilation.
	 * @param borderType pixel extrapolation method.
	 * @param borderValue value to be used for a constant border.
	 * @param dst Output Image.
	 */
	private void cvDilate(Mat src, Mat kernel, Point anchor, double iterations,
	int borderType, Scalar borderValue, Mat dst) {
		if (kernel == null) {
			kernel = new Mat();
		}
		if (anchor == null) {
			anchor = new Point(-1,-1);
		}
		if (borderValue == null){
			borderValue = new Scalar(-1);
		}
		Imgproc.dilate(src, dst, kernel, anchor, (int)iterations, borderType, borderValue);
	}

	/**
	 * Sets the values of pixels in a binary image to their distance to the nearest black pixel.
	 * @param input The image on which to perform the Distance Transform.
	 * @param type The Transform.
	 * @param maskSize the size of the mask.
	 * @param output The image in which to store the output.
	 */
	private void findContours(Mat input, boolean externalOnly,
		List<MatOfPoint> contours) {
		Mat hierarchy = new Mat();
		contours.clear();
		int mode;
		if (externalOnly) {
			mode = Imgproc.RETR_EXTERNAL;
		}
		else {
			mode = Imgproc.RETR_LIST;
		}
		int method = Imgproc.CHAIN_APPROX_SIMPLE;
		Imgproc.findContours(input, contours, hierarchy, mode, method);
	}


	/**
	 * Filters out contours that do not meet certain criteria.
	 * @param inputContours is the input list of contours
	 * @param output is the the output list of contours
	 * @param minArea is the minimum area of a contour that will be kept
	 * @param minPerimeter is the minimum perimeter of a contour that will be kept
	 * @param minWidth minimum width of a contour
	 * @param maxWidth maximum width
	 * @param minHeight minimum height
	 * @param maxHeight maximimum height
	 * @param Solidity the minimum and maximum solidity of a contour
	 * @param minVertexCount minimum vertex Count of the contours
	 * @param maxVertexCount maximum vertex Count
	 * @param minRatio minimum ratio of width to height
	 * @param maxRatio maximum ratio of width to height
	 */
	private void filterContours(List<MatOfPoint> inputContours, double minArea,
		double minPerimeter, double minWidth, double maxWidth, double minHeight, double
		maxHeight, double[] solidity, double maxVertexCount, double minVertexCount, double
		minRatio, double maxRatio, List<MatOfPoint> output) {
		final MatOfInt hull = new MatOfInt();
		output.clear();
		//operation
		for (int i = 0; i < inputContours.size(); i++) {
			final MatOfPoint contour = inputContours.get(i);
			final Rect bb = Imgproc.boundingRect(contour);
			if (bb.width < minWidth || bb.width > maxWidth) continue;
			if (bb.height < minHeight || bb.height > maxHeight) continue;
			final double area = Imgproc.contourArea(contour);
			if (area < minArea) continue;
			if (Imgproc.arcLength(new MatOfPoint2f(contour.toArray()), true) < minPerimeter) continue;
			Imgproc.convexHull(contour, hull);
			MatOfPoint mopHull = new MatOfPoint();
			mopHull.create((int) hull.size().height, 1, CvType.CV_32SC2);
			for (int j = 0; j < hull.size().height; j++) {
				int index = (int)hull.get(j, 0)[0];
				double[] point = new double[] { contour.get(index, 0)[0], contour.get(index, 0)[1]};
				mopHull.put(j, 0, point);
			}
			final double solid = 100 * area / Imgproc.contourArea(mopHull);
			if (solid < solidity[0] || solid > solidity[1]) continue;
			if (contour.rows() < minVertexCount || contour.rows() > maxVertexCount)	continue;
			final double ratio = bb.width / (double)bb.height;
			if (ratio < minRatio || ratio > maxRatio) continue;
			output.add(contour);
		}
	}
	
	public Mat selectedOutput(){

		if(DriverStation.getInstance().isDisabled()){
			switch(table.getString(OUTPUT_CONTROL+"/"+SELECTION, "1")){
			
			case "1": 
			default:
				this.table.putBoolean(OUTPUT_CONTROL+"/"+SOURCE, true);
				this.table.putBoolean(OUTPUT_CONTROL+"/"+HSV_THRESHOLD, false);
				this.table.putBoolean(OUTPUT_CONTROL+"/"+ERODE, false);
				this.table.putBoolean(OUTPUT_CONTROL+"/"+DILATE, false);
				this.table.putBoolean(OUTPUT_CONTROL+"/"+FIND_CONTOURS, false);
				this.table.putBoolean(OUTPUT_CONTROL+"/"+FILTER_CONTOURS, false);
				selectedOutput=hsvThresholdInput;
				break;

			case "2":
				this.table.putBoolean(OUTPUT_CONTROL+"/"+SOURCE, false);
				this.table.putBoolean(OUTPUT_CONTROL+"/"+HSV_THRESHOLD, true);
				this.table.putBoolean(OUTPUT_CONTROL+"/"+ERODE, false);
				this.table.putBoolean(OUTPUT_CONTROL+"/"+DILATE, false);
				this.table.putBoolean(OUTPUT_CONTROL+"/"+FIND_CONTOURS, false);
				this.table.putBoolean(OUTPUT_CONTROL+"/"+FILTER_CONTOURS, false);
				selectedOutput=hsvThresholdOutput;
				break;

			case "3":
				this.table.putBoolean(OUTPUT_CONTROL+"/"+SOURCE, false);
				this.table.putBoolean(OUTPUT_CONTROL+"/"+HSV_THRESHOLD, false);
				this.table.putBoolean(OUTPUT_CONTROL+"/"+ERODE, true);
				this.table.putBoolean(OUTPUT_CONTROL+"/"+DILATE, false);
				this.table.putBoolean(OUTPUT_CONTROL+"/"+FIND_CONTOURS, false);
				this.table.putBoolean(OUTPUT_CONTROL+"/"+FILTER_CONTOURS, false);
				selectedOutput=cvErodeOutput;
				break;

			case "4":
				this.table.putBoolean(OUTPUT_CONTROL+"/"+SOURCE, false);
				this.table.putBoolean(OUTPUT_CONTROL+"/"+HSV_THRESHOLD, false);
				this.table.putBoolean(OUTPUT_CONTROL+"/"+ERODE, false);
				this.table.putBoolean(OUTPUT_CONTROL+"/"+DILATE, true);
				this.table.putBoolean(OUTPUT_CONTROL+"/"+FIND_CONTOURS, false);
				this.table.putBoolean(OUTPUT_CONTROL+"/"+FILTER_CONTOURS, false);
				selectedOutput=cvDilateOutput;
				break;
				
			case "5":
				this.table.putBoolean(OUTPUT_CONTROL+"/"+SOURCE, false);
				this.table.putBoolean(OUTPUT_CONTROL+"/"+HSV_THRESHOLD, false);
				this.table.putBoolean(OUTPUT_CONTROL+"/"+ERODE, false);
				this.table.putBoolean(OUTPUT_CONTROL+"/"+DILATE, false);
				this.table.putBoolean(OUTPUT_CONTROL+"/"+FIND_CONTOURS, true);
				this.table.putBoolean(OUTPUT_CONTROL+"/"+FILTER_CONTOURS, false);
				selectedOutput=findContoursOutput();
				break;
				
			case "6":
				this.table.putBoolean(OUTPUT_CONTROL+"/"+SOURCE, false);
				this.table.putBoolean(OUTPUT_CONTROL+"/"+HSV_THRESHOLD, false);
				this.table.putBoolean(OUTPUT_CONTROL+"/"+ERODE, false);
				this.table.putBoolean(OUTPUT_CONTROL+"/"+DILATE, false);
				this.table.putBoolean(OUTPUT_CONTROL+"/"+FIND_CONTOURS, false);
				this.table.putBoolean(OUTPUT_CONTROL+"/"+FILTER_CONTOURS, true);
				selectedOutput=filterContoursOutput();
				break;
				
			} // End of switch
			
		}
		return selectedOutput;
	}

	int getBoarder(String boarder){
		int intBoarder=0;
		
		
		switch(boarder){
		case BOARDER_CONSTANT:
			intBoarder=0;
			break;
		case BOARDER_REPLECATE:
			intBoarder=1;
			break;
		case BOARDER_REFLECT:
			intBoarder=2;
			break;
		case BOARDER_WRAP:
			intBoarder=3;
			break;
		case BOARDER_REFLECT_101:
			intBoarder=4;
			break;
		case BOARDER_TRANSPARENT:
			intBoarder=5;
			break;
		case BOARDER_REFLECT101:
			intBoarder=6;
			break;
		case BOARDER_DEFAULT:
			intBoarder=7;
			break;
		case BOARDER_ISOLATED:
			intBoarder=0;
			break;
		}
		
		return intBoarder;
	}


}

