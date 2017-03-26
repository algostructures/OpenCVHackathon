
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint;
import org.opencv.core.Point;
import org.opencv.imgproc.Imgproc;
import org.opencv.imgcodecs.Imgcodecs;
public class Hello
{
   static int imgcounter = 0;
   public static void main( String[] args ) throws IOException
   {
     
      
      System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
      Mat dst = Imgcodecs.imread("images/input/custom.jpg");
      
      Mat x = new Mat();
      mat_to_image(dst);
      
      
      ArrayList<Mat> mv = new ArrayList<>();
      Core.split(dst, mv);
      Imgproc.threshold(mv.get(0), x, 180, 255, Imgproc.THRESH_BINARY);
     
      
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
   
   public static void mat_to_image(Mat mat){
       Imgcodecs.imwrite(new String("images/output/"+imgcounter+".jpg"), mat);
       try {
           BufferedImage img = ImageIO.read(new File(new String("images/output/"+imgcounter+".jpg")));
           ImageIcon icon = new ImageIcon(img);
           JLabel label = new JLabel(icon);
           JOptionPane.showMessageDialog(null, label);
        } catch (IOException e) {
           e.printStackTrace();
        }
       imgcounter++;
   }
}


