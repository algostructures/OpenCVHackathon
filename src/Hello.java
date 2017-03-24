import java.awt.List;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import javax.imageio.ImageIO;


import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint;
import org.opencv.core.Point;
import org.opencv.imgproc.Imgproc;
import org.opencv.imgcodecs.Imgcodecs;
public class Hello
{
   public static void main( String[] args ) throws IOException
   {
    Imgcodecs.im  
      BufferedImage img;
      img = ImageIO.read(new File("images/custom.jpg"));;
      System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
      Mat dst = Imgcodecs.imread("images/custom.jpg");
      //BufferedImage out=new BufferedImage(img.getWidth(), height, imageType)
      Mat x=new Mat();
      Imgcodecs.imwrite("images/cuqs.jpg", dst);
      ArrayList<Mat> mv=new ArrayList<>();
      Core.split(dst, mv);
      Imgproc.threshold(mv.get(0), x, 180, 255, Imgproc.THRESH_BINARY);
      String filename = "images/cus.jpg";
      //Highgui.imwrite(filename, canny);  // write to disk
      final ArrayList<MatOfPoint> points = new ArrayList<MatOfPoint>();
      final Mat hierarchy = new Mat();
      Imgproc.findContours(x, points, hierarchy, Imgproc.RETR_TREE, Imgproc.CHAIN_APPROX_SIMPLE);
      int count = 0;
      
      for(MatOfPoint mp: points){
          Iterator<Point> It = mp.toList().iterator();
          count++;
          System.out.println("Contour : "+ count);
          
          while(It.hasNext()){
              Point p = It.next();
              System.out.println("("+p.x+","+p.y+")");
          }
      }
      
      
   }
   
   public static Mat bufferedImageToMat(BufferedImage bi) {
       System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
       Mat mat = new Mat(bi.getHeight(), bi.getWidth(), CvType.CV_8UC1);
       byte[] data = ((DataBufferByte) bi.getRaster().getDataBuffer()).getData();
       mat.put(0, 0, data);
       return mat;
    }
   
   /**  
    * Converts/writes a Mat into a BufferedImage.  
    *  
    * @param matrix Mat of type CV_8UC3 or CV_8UC1  
    * @return BufferedImage of type TYPE_3BYTE_BGR or TYPE_BYTE_GRAY  
    */  
   public static BufferedImage matToBufferedImage(Mat matrix, BufferedImage bimg)
   {
       if ( matrix != null ) { 
           int cols = matrix.cols();  
           int rows = matrix.rows();  
           int elemSize = (int)matrix.elemSize();  
           byte[] data = new byte[cols * rows * elemSize];  
           int type;  
           matrix.get(0, 0, data);  
           switch (matrix.channels()) {  
           case 1:  
               type = BufferedImage.TYPE_BYTE_GRAY;  
               break;  
           case 3:  
               type = BufferedImage.TYPE_3BYTE_BGR;  
               // bgr to rgb  
               byte b;  
               for(int i=0; i<data.length; i=i+3) {  
                   b = data[i];  
                   data[i] = data[i+2];  
                   data[i+2] = b;  
               }  
               break;  
           default:  
               return null;  
           }  

           // Reuse existing BufferedImage if possible
           if (bimg == null || bimg.getWidth() != cols || bimg.getHeight() != rows || bimg.getType() != type) {
               bimg = new BufferedImage(cols, rows, type);
           }        
           bimg.getRaster().setDataElements(0, 0, cols, rows, data);
       } else { // mat was null
           bimg = null;
       }
       return bimg;  
   }   
}


