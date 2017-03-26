package ai;

import main.Ball;
import main.GameState;
import main.Platform;
import main.Item;

import java.util.Arrays;
import java.util.Hashtable;

public class readData {

  public readData() {

  }

  private Platform p;
  private boolean right = true;//platform on right hand side

  /**
   * Input a ball and a list of platform, then return a x-velocity
   *
   * @param b  Ball
   * @param ps Platform list
   * @return x-velocity
   */
  public int AI(Ball b, Platform[] ps, Hashtable database, GameState game) {
    double dx;
    double dy;
    double vy;
    Item[] items = game.getItems();
    Item item = null;
    for (int i = 0; i < items.length; i++) {
      if (items[i].getType() == 3 && Math.abs(b.getX()-items[i].x) < 300 && Math.abs(b.getY()-items[i].y) < 300 && Math.abs(b.getDy()) > 50) {
        item = items[i];
        break;
      }
    }
    if (item != null) {
      p = new Platform(item.x, item.y, 10, 10);
    } else {
      SelectPlatform sp = new SelectPlatform(game);
      p = sp.select(b, ps);
    }

    if (p == null) return 0;

    dx = p.x - b.getX();//x-distance between ball and platform
    if (dx < -(p.width) / 2) {
      right = false;
      dx = -(dx + p.width);
    }
    dy = Math.abs(b.getY() - p.getY());//y-distance between ball and platform
    vy = b.getDy();


    if ((!(p instanceof main.MovingHorizontallyPlatform)) && vy >= -10 && b.getX() > p.x + 10 && b.getX() < p.x + p.width - 10 && b.getY() < p.y) {
      double currentDx = b.getDx();
      double isb = 0.0;
      if (currentDx > isb) {
        return 1;
      } else if (currentDx < isb) {
        return -1;
      } else {
        return 0;
      }
    }


    //Score function is symmetric and domain connected.
    //Straight line between 2 points are inside the domain.
    //Therefore, we can learn int(10) case and interpolate non-int result(between two int) with convex convernation.
    int dx_int = (int) (dx - (dx % 10));
    double dx_dec = (dx % 10) / 10;
    //double vector_x = (dx_dec - 0.5) / Math.cos(45);
    int dy_int = (int) (dy - (dy % 10));
    double dy_dec = (dy % 10) / 10;
    //double vector_y = (dy_dec - 0.5) / Math.cos(45);
    int vy_int = (int) Math.floor(vy);
    double vy_dec = (10 * vy - 10 * vy_int) / 10;

    //Temp method
    //if (vy_dec > 0.5) vy_int++;

    double result1 = 0.0;
    double result2 = 0.0;
    double result3 = 0.0;
    double result4 = 0.0;
    double result5 = 0.0;
    double result6 = 0.0;
    double result7 = 0.0;
    double result8 = 0.0;
    if(database.get(dx_int + "," + dy_int + "," + vy_int) != null) result1 = (double) database.get(dx_int + "," + dy_int + "," + vy_int);
    if(database.get((dx_int+10) + "," + dy_int + "," + vy_int) != null)	result2 = (double) database.get((dx_int+10) + "," + dy_int + "," + vy_int);
    if(database.get((dx_int+10) + "," + (dy_int+10) + "," + vy_int) != null) result3 = (double) database.get((dx_int+10) + "," + (dy_int+10) + "," + vy_int);
    if(database.get(dx_int + "," + (dy_int+10) + "," + vy_int) != null)	result4 = (double) database.get(dx_int + "," + (dy_int+10) + "," + vy_int);
    if(database.get(dx_int + "," + dy_int + "," + (vy_int+1)) != null) result5 = (double) database.get(dx_int + "," + dy_int + "," + (vy_int+1));
    if(database.get((dx_int+10) + "," + dy_int + "," + (vy_int+1)) != null)	result6 = (double) database.get((dx_int+10) + "," + dy_int + "," + (vy_int+1));
    if(database.get((dx_int+10) + "," + (dy_int+10) + "," + (vy_int+1)) != null) result7 = (double) database.get((dx_int+10) + "," + (dy_int+10) + "," + (vy_int+1));
    if(database.get(dx_int + "," + (dy_int+10) + "," + (vy_int+1)) != null)	result8 = (double) database.get(dx_int + "," + (dy_int+10) + "," + (vy_int+1));

    double result = ((100-10*10*vy_dec/3)*(result1+result2+result3+result4)/4 +
            (100-10*10*(1-vy_dec)/3)*(result5+result6+result7+result8)/4 +
            (100-1*10*dy_dec/3)*(result1+result2+result5+result6)/4 +
            (100-1*10*(10-dy_dec)/3)*(result3+result4+result7+result8)/4 +
            (100-10*1*dx_dec/3)*(result1+result4+result5+result8)/4 +
            (100-10*1*(10-dx_dec)/3)*(result2+result3+result6+result7)/4)/(600-100);
    System.out.println(((100-10*10*vy_dec/3)+(100-10*10*(1-vy_dec)/3)+(100-1*10*dy_dec/3)+(100-1*10*(10-dy_dec)/3)+(100-10*1*dx_dec/3)+(100-10*1*(10-dx_dec)/3))/500);
    System.out.println("Result: " +result);
    //if (result == 0 && dx > 300) result = 60.0;
    if (!right) result = -result;
    if(vy<0) result = -result;
    int isb = (int) Math.round(result);
    double currentDx = b.getDx();

    if (currentDx > isb) {
      return -1;//LEFT
    } else if (currentDx < isb) {
      return 1;//RIGHT
    } else {
      return 0;//NO_MOVE
    }
	  
  }

}
