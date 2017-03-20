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

public class Hello
{
   public static void main( String[] args ) throws IOException
   {
      
      BufferedImage img;
      img = ImageIO.read(new File("images/custom.jpg"));;
       
      Mat dst = bufferedImageToMat(img);
      final ArrayList<MatOfPoint> points = new ArrayList<MatOfPoint>();
      final Mat hierarchy = new Mat();
      Imgproc.findContours(dst, points, hierarchy, Imgproc.RETR_TREE, Imgproc.CHAIN_APPROX_SIMPLE);
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
}


