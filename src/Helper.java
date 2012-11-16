import java.awt.Image;
import java.awt.Point;


public class Helper {

	  public static boolean boundingBoxCollision(Point positionOne, Image textureOne, Point positionTwo, Image textureTwo)
      {

          if (positionOne.getX() <= (positionTwo.getX() + textureTwo.getWidth(null)) && (positionOne.getY() + textureOne.getHeight(null)) < positionTwo.getY() ||
              positionOne.getX() > (positionTwo.getX() + textureTwo.getWidth(null)) && positionOne.getY() <= (positionTwo.getY() + textureTwo.getHeight(null)) ||
              (positionOne.getX() + textureOne.getWidth(null)) >= positionTwo.getX() && positionOne.getY() > (positionTwo.getY() + textureTwo.getHeight(null)) ||
              (positionOne.getX() + textureOne.getWidth(null)) < positionTwo.getX() && (positionOne.getY() + textureOne.getHeight(null)) >= positionTwo.getY()) 
          {
              return false;
          }
          else
          {
              return true;
          }

      }
}
