package bots; // !! Do NOT change this package name !!

// package bots; // !! Do NOT change this package name !!
// package com.tw.paintbots;

import java.util.Objects;

import com.badlogic.gdx.math.Vector2;

import com.tw.paintbots.PlayerException;
import com.tw.paintbots.AIPlayer;
import com.tw.paintbots.GameManager;
import com.tw.paintbots.GameManager.SecretKey;
import com.tw.paintbots.PaintColor;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Random;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.ui.List;
import com.tw.paintbots.PlayerException;
import com.tw.paintbots.AIPlayer;
import com.tw.paintbots.GameManager;
import com.tw.paintbots.GameManager.SecretKey;
import com.tw.paintbots.Items.PowerUp;
import com.tw.paintbots.Items.PowerUpType;
import java.util.ArrayList;
// import bots.Items.PlayerState;
// import bots.items.PlayerState;
// package bots; // !! Do NOT change this package name !!
import com.tw.paintbots.Renderables.Canvas;
import java.util.Objects;
import java.util.Random;
import com.badlogic.gdx.math.Vector2;
import com.tw.paintbots.PlayerException;
import com.tw.paintbots.AIPlayer;
import com.tw.paintbots.GameManager;
import com.tw.paintbots.GameManager.SecretKey;
import com.tw.paintbots.Items.ItemType;

// package com.tw.paintbots; // !! Do NOT change this package name !!

// package com.tw.paintbots; // !! Do NOT change this package name !!

// package bots; // !! Do NOT change this package name !!

// package bots; // !! Do NOT change this package name !!

import java.util.Objects;

import com.badlogic.gdx.math.Vector2;

import com.tw.paintbots.PlayerException;
import com.tw.paintbots.AIPlayer;
import com.tw.paintbots.GameManager;
import com.tw.paintbots.GameManager.SecretKey;
import com.tw.paintbots.PaintColor;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Random;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.ui.List;
import com.tw.paintbots.PlayerException;
import com.tw.paintbots.AIPlayer;
import com.tw.paintbots.GameManager;
import com.tw.paintbots.GameManager.SecretKey;
import com.tw.paintbots.Items.PowerUp;
import com.tw.paintbots.Items.PowerUpType;
import java.util.ArrayList;
import com.tw.paintbots.PlayerState;
// package bots; // !! Do NOT change this package name !!
import com.tw.paintbots.Renderables.Canvas;
import java.util.Objects;
import java.util.Random;
import com.badlogic.gdx.math.Vector2;
import com.tw.paintbots.PlayerException;
import com.tw.paintbots.AIPlayer;
import com.tw.paintbots.GameManager;
import com.tw.paintbots.GameManager.SecretKey;
import com.tw.paintbots.Items.ItemType;

// =============================================================== //
class RandomBot extends AIPlayer {
  // --------------------------------------------------------------- //
  private Vector2 dir = new Vector2(-100f, -100f);

  // --------------------------------------------------------------- //
  /**
   * This method is required, so that the GameManager can set your initial
   * direction.
   */
  @Override
  public void setInitialDirection(Vector2 dir, GameManager.SecretKey key) {
    Objects.requireNonNull(key);
    this.dir = dir;
  }

  // ======================= RandomBot methods ===================== //
  // !! Please provide this constructor but do NOT use it !! //
  // !! ....... Use the initBot() method instead ........ !! //
  public RandomBot() {
    /* !! leave this blank !! */ }

  // --------------------------------------------------------------- //
  // !! Please provide this constructor but do NOT use it !! /
  // !! ....... Use the initBot() method instead ........ !! /
  public RandomBot(String name) throws PlayerException {
    /* do not change this */
    super("AI-" + name);
  }

  // ======================= AIPlayer methods ====================== //
  //@formatter:off
  // ToDo: fill in your details
  @Override public String  getBotName()   { return "Random Bot 2rt3"; }
  @Override public String  getStudent()   { return "Thomas Wilde"; }
  @Override public int     getMatrikel()  { return 123456; }
  @Override public Vector2 getDirection() { return dir; }
  //@formatter:on

  // --------------------------------------------------------------- //
  /**
   * This method is called by the GameManager in each update loop. The
   * GameManager is the only class that can call this method.
   */
  //@formatter:off
  @Override
  public void update(GameManager.SecretKey secret) {
    Objects.requireNonNull(secret); // <= keep this line
    myUpdate();                     // <= call your own method
    super.update(secret);           // <= keep this line for the animation
  }
  //@formatter:on

  // --------------------------------------------------------------- //
  /**
   * This method is called as soon as an instance of this bot for the actual
   * game is created. You can use this as a substitute for the constructor.
   */
  @Override
  public void initBot() {
    // ToDo: initialize some stuff if necessary
    // System.out.println("before");
    getinitialpixels();
    // System.out.println("after");
    try {
      reset_Pixels((float) Fixed_width);
    } catch (Exception e) {
      System.out.println(
          "\n\n\n\n\n\n\n\n\nerr " + e.getStackTrace()[0].getLineNumber());
    }
    // System.out.println("after2");
  }

  public int Back = 0;
  public double counter = 0.0;
  public Vector2 main_dir = new Vector2(0, 150);
  public boolean temp = false;

  class PIXEL {
    PIXEL parent;
    float X;
    float Y;
    float fcost = 10000;
    float hcost;
    float gcost = 10000;
    boolean Start;
    boolean goal;
    boolean solid;
    boolean checked;
    boolean open;
    boolean notsolid = true;

    public PIXEL() {

    }

    void setposition(float x, float y) {
      X = x;
      Y = y;
    }

    void setgoal() {
      Start = false;
      goal = true;
    }

    void setstart() {
      Start = true;
      goal = false;
    }

    void setsolid() {
      Start = false;
      goal = false;
      solid = true;
      notsolid = false;
    }

    void setopen() {
      open = true;
    }

    void setchecked() {
      if (Start == false && goal == false) {
        checked = true;
      }
    }
  }

  // ======================= Player methods ===================== //
  /**
   * This method is required, so that the GameManager can set your initial
   * direction.
   *
   * @param dir - the direction in which the player should look
   * @param key - the SecretKey only available to the GameManager
   */
  ArrayList<PIXEL> Pixels = new ArrayList<>();
  ArrayList<PIXEL> Light_Pixels = new ArrayList<>();
  PIXEL[][] PIXELS = new PIXEL[1][1];
  int kin = 0;

  void reset_Pixels(float width) {
    int p_index = 0;
    int index2 = 0;
    PIXELS = new PIXEL[(int) (Mng.getBoard().getWidth()
        / width)][(int) (Mng.getBoard().getWidth() / width)];
    // System.out.println(" \n\n\n\n"+PIXELS.length);
    // System.out.println(" \n\n\n\n" + (int) (999 / width));
    for (PIXEL pixel : Pixels) {
      int pos = (int) (index2 / (int) width);
      if (pixel == null) {
        // continue;
      }
      if ((Light_Pixels.size() == 0 || (pos >= Light_Pixels.size())) && false) {
        PIXEL pix = new PIXEL();
        pix.setposition(pixel.X, pixel.Y);
        Light_Pixels.add(pix);
        p_index = p_index + 1;
        kin = index2;
        PIXELS[(int) ((int) pixel.X / (int) width)][(int) ((int) pixel.Y
            / (int) width)] = pix;
      }
      if (PIXELS[(int) ((int) pixel.X / (int) width)][(int) ((int) pixel.Y
          / (int) width)] == null) {
        PIXEL pix = new PIXEL();
        pix.setposition(pixel.X, pixel.Y);
        PIXELS[(int) ((int) pixel.X / (int) width)][(int) ((int) pixel.Y
            / (int) width)] = pix;
      }
      if (pixel != null) {
        /*
         * Pixels.get(index2).X < pixel.X && Pixels.get(index2).X + width >
         * pixel.X && Pixels.get(index2).Y < pixel.Y && Pixels.get(index2).Y +
         * width > pixel.Y
         */

        if (PIXELS[(int) (pixel.X / width)][(int) (pixel.Y / width)] != null
            && PIXELS[(int) (pixel.X / width)][(int) (pixel.Y
                / width)].solid != true) {
          PIXELS[(int) (pixel.X / width)][(int) (pixel.Y / width)].solid =
              pixel.solid;
        }

        if (pixel.solid && false) {
          // int maxw =
          // (int) pixel.X / ((int) (Mng.getBoard().getWidth() / width));
          int bx = (int) (pixel.X / width);
          int by = (int) (pixel.Y / width);
          if (bx + 1 < PIXELS.length && PIXELS[bx + 1][by] == null) {
            PIXELS[bx + 1][by] = new PIXEL();
          }
          if (bx + 1 < PIXELS.length) {
            PIXELS[bx + 1][by].solid = true;
          }

          if (by + 1 < PIXELS.length && PIXELS[bx][by + 1] == null) {
            PIXELS[bx][by + 1] = new PIXEL();
          }
          if (by + 1 < PIXELS.length) {
            PIXELS[bx][by + 1].solid = true;
          }

          if (bx - 1 > 0 && PIXELS[bx - 1][by] == null) {
            PIXELS[bx - 1][by] = new PIXEL();
          }
          if (bx - 1 > 0) {
            PIXELS[bx - 1][by].solid = true;
          }

          if (by - 1 < PIXELS.length && PIXELS[bx][by - 1] == null) {
            PIXELS[bx][by - 1] = new PIXEL();
          }
          if (by - 1 < PIXELS.length) {
            PIXELS[bx][by - 1].solid = true;
          }

        }
      } else {
        // p_index = p_index + 1;
      }
      index2 = index2 + 1;
    }
    boolean j = true;
    System.err.println(Light_Pixels.size() + " " + width + " " + kin);
    int tx = 0;
    for (PIXEL[] pxs : PIXELS) {
      int ty = 0;
      for (PIXEL px : pxs) {
        // System.err.println(Light_Pixels.size());
        if (px == null) {
          System.err.println(Light_Pixels.size() + " " + tx + " " + ty);
          ty++;
          continue;
        }
        // System.err.println(px.X + " " + px.Y);
        int xs = (int) px.X;
        int ys = (int) px.Y;
        if (Mng.getBoard().getType(xs, ys).getTypeID() == 10
            && px.solid == false && j) {
          refill = px;
          j = false;
        }
        ty++;
      }
      tx++;
    }

  }

  ArrayList<PIXEL> Path = new ArrayList<>();

  void pathfinder(PIXEL _PIXEL, PIXEL target_PIXEL, float width) {
    pathfound = false;
    ArrayList<PIXEL> openList = new ArrayList<>();
    PIXEL first_PIXEL = _PIXEL;
    first_PIXEL.Start = true;
    for (PIXEL[] pxs : PIXELS) {
      for (PIXEL px : pxs) {
        if (px == first_PIXEL) {
          // break;
        }
        float target_distance =
            Math.abs(px.X - target_PIXEL.X) + Math.abs(px.Y - target_PIXEL.Y);
        float Start_distance =
            Math.abs(px.X - first_PIXEL.X) + Math.abs(px.Y - first_PIXEL.Y);
        px.Start = false;
        px.open = false;
        px.checked = false;
        px.parent = null;
        px.fcost = target_distance + Start_distance;
        px.gcost = Start_distance;
        px.goal = false;
      }
    }
    target_PIXEL.goal = true;
    _PIXEL.Start = true;
    opindx = 0;
    for (int itiration = 0; itiration < 1300; itiration++) {
      float x = _PIXEL.X / width;
      float y = _PIXEL.Y / width;
      _PIXEL.setchecked();
      openList.remove(_PIXEL);
      int x2 = (int) x;
      int y2 = (int) y;
      if (_PIXEL.Y == 900) {
        int oo = 0000;
      }
      if (y2 - 1 >= 0) {
        OPEN_PIXEL(PIXELS[x2][y2 - 1], _PIXEL, openList);
      }
      if (x2 - 1 >= 0) {
        OPEN_PIXEL(PIXELS[x2 - 1][y2], _PIXEL, openList);
      }
      if (y2 + 1 < PIXELS.length) {
        OPEN_PIXEL(PIXELS[x2][y2 + 1], _PIXEL, openList);

      }
      if (x2 + 1 < PIXELS.length) {
        OPEN_PIXEL(PIXELS[x2 + 1][y2], _PIXEL, openList);

      }
      /*
       * if (y2 + 1 < PIXELS.length && x2 + 1 < PIXELS.length) {
       * OPEN_PIXEL(PIXELS[x2 + 1][y2 + 1], _PIXEL, openList); } if (x2 - 1 >= 0
       * && y2 - 1 >= 0) { OPEN_PIXEL(PIXELS[x2 - 1][y2 - 1], _PIXEL, openList);
       * }
       */
      if (opindx >= openList.size()) {
        opindx--;
      }
      int indx = 0;
      opindx = 0;
      for (PIXEL pix : openList) {
        if (pix.fcost < _PIXEL.fcost) {
          _PIXEL = pix;
          opindx = indx;
        } else if (pix.fcost == _PIXEL.fcost
            && openList.get(opindx).gcost > pix.gcost) {
          _PIXEL = pix;
          opindx = indx;
        }
        _PIXEL = openList.get(opindx);
        indx++;
      }
      // _PIXEL = openList.get(opindx);
      if (_PIXEL == target_PIXEL) {
        path = new ArrayList<>();
        PIXEL chck = _PIXEL;
        Goalpix = _PIXEL;
        pathfound = true;
        for (int maxi = 0; maxi < 300; maxi++) {
          if (chck.parent == null) {
            break;
          }
          chck = chck.parent;
          if (chck == first_PIXEL) {
            pathfound = true;
            break;
          }
        }
        break;
      }
    }
  }

  int opindx = 0;
  PIXEL Goalpix;
  boolean pathfound;

  void OPEN_PIXEL(PIXEL pix, PIXEL _PIXEL, ArrayList<PIXEL> openList) {
    if (pix.open == false && pix.checked == false && pix.solid == false
        && pix.Start == false) {
      pix.parent = _PIXEL;
      pix.setopen();
      openList.add(pix);
    }
  }

  PIXEL refill = new PIXEL();

  void getinitialpixels() {
    Pixels = new ArrayList<>();
    for (int xs = 0; xs < Mng.getBoard().getWidth(); xs++) {
      for (int ys = 0; ys < Mng.getBoard().getHeight(); ys++) {
        PIXEL px = new PIXEL();
        px.X = xs;
        px.Y = ys;

        if (Mng.getBoard().getType(xs, ys).isPassable() == false) {
          px.solid = true;
        }
        if (Mng.getBoard().getType(xs, ys).getTypeID() == 10
            && px.solid == false) {
          refill = px;
        }
        Pixels.add(px);
      }
    }
  }

  // --------------------------------------------------------------- //
  /** This is a helper method called in the update method. */
  /** This is a helper method called in the update method. */
  int role = 0;
  int Fixed_width = 50;
  GameManager Mng = GameManager.get();
  PIXEL lastclosest;
  int lastcc = 10000;
  Vector2 realtarget = new Vector2(0, 0);
  ArrayList<PIXEL> Points = new ArrayList<>();
  int pointofpath = 0;
  ArrayList<PIXEL> path = new ArrayList<>();

  void decide() {
    int cc = 0;
    PIXEL px = Goalpix;
    if (Goalpix == null) {
      return;
    }
    path = new ArrayList<>();
    path.add(px);
    while (cc < 100) {
      if (px.parent != null) {
        px = px.parent;
        path.add(px);
        pointofpath = cc;
      }
      cc++;
    }
    pointofpath = path.size();
    // System.out.println(pointofpath);
  }

  PIXEL closest = new PIXEL();
  boolean fsit;
  boolean refilltargret;

  public void myUpdate() {
    // ToDo: implement a method, called in each update loop
    role = 0;
    Mng = GameManager.get();
    if ((role == 0 && Mng.getElapsedTime() > counter && pathfound == false)
        || pointofpath > 50) {
      // System.err.println("finding");
      // finding a path
      ArrayList<PowerUp.Info> powerUps = Mng.getActivePowerUps();
      if (getPaintAmount() < getMaximumPaintAmount() / 2) {
        // refill = PIXELS[(450 / Mng.getBoard().getWidth())
        // * PIXELS.length][(450 / Mng.getBoard().getWidth()) * PIXELS.length];
        int x = ((int) refill.X / Fixed_width);
        int y = ((int) refill.Y / Fixed_width);
        if (PIXELS[x][y] == null) {
          return;
        }
        PIXELS[x][y].setgoal();
        int x2 = (int) getPosition().x / Fixed_width;
        int y2 = (int) getPosition().y / Fixed_width;
        PIXELS[x2][y2].Start = true;
        pathfinder(PIXELS[x2][y2], PIXELS[x][y], Fixed_width);
        lastcc = 10000;
        realtarget = new Vector2(refill.X, refill.Y);
        Points = new ArrayList<>();
        // System.out.println(pathfound);
        fsit = true;
        refilltargret = true;
        decide();
      } else if (powerUps.size() > 0
          && (getPowerUpCount() < 2 || powerUps.get(0).type.getTypeID() > 3)) {

        int x = (powerUps.get(0).position[0] / Fixed_width);
        int y = (powerUps.get(0).position[1] / Fixed_width);
        if (PIXELS[x][y] == null) {
          return;
        }
        PIXELS[x][y].setgoal();
        int x2 = (int) getPosition().x / Fixed_width;
        int y2 = (int) getPosition().y / Fixed_width;
        PIXELS[x2][y2].Start = true;
        pathfinder(PIXELS[x2][y2], PIXELS[x][y], Fixed_width);
        lastcc = 10000;
        realtarget = new Vector2(powerUps.get(0).position[0],
            powerUps.get(0).position[1]);
        Points = new ArrayList<>();
        // System.out.println(pathfound);
        refilltargret = false;
        fsit = true;
        decide();
      } else {
        role = 2;
      }
    } else if (role == 0 && Mng.getElapsedTime() > counter && pathfound
        && pathfound && pointofpath < 200) {
      // aplying path
      float cx = ((int) getPosition().x / Fixed_width) * Fixed_width;
      float cy = ((int) getPosition().y / Fixed_width) * Fixed_width;
      PIXEL px = Goalpix;
      int cc = 0;
      float shrotest = 1000000;

      int hcc = 0;
      /* */
      float distancef = Math.abs(closest.X - cx) + Math.abs(closest.Y - cy);
      if (fsit) {
        distancef = 0;
      }
      fsit = false;
      float distancef2 = 10000000;
      // closest.X = 12.53f;
      for (int pi = path.size() - 1; pi > -1; pi--) {
        PIXEL pxx = path.get(pi);
        distancef = Math.abs(pxx.X - cx) + Math.abs(pxx.Y - cy);
        if (pointofpath < path.size()) {
          distancef2 = Math.abs(path.get(pointofpath).X - cx)
              + Math.abs(path.get(pointofpath).Y - cy);
        }
        if (pointofpath > pi && distancef < shrotest
            || pointofpath > path.size() - 1) {
          // System.out.println(pointofpath); && distancef < distancef2 + 53
          closest = pxx;
          pointofpath = pi;
          shrotest = distancef;
          break;
        }
      }
      if (closest.X != 12.53f)

      {
        aplydirection(closest);
      } else {
        pathfound = false;
      }
      lastclosest = closest;
      if (closest.goal) {
        closest.X = realtarget.x;
        closest.Y = realtarget.y;
        pathfound = false;
        if (refilltargret) {
          counter += 0.5;
        }
      }
      Points.add(closest);
      // pointofpath -= hcc;
      // lastcc = hcc;
      // System.out.println(realtarget.x + " " + realtarget.y + " " + shrotest);
      counter += 0.2;
    }
    if (Mng.getElapsedTime() > counter && role == 2 && pathfound == false) {
      counter += 0.4;
      dir = getDirection().add((float) Math.random() * 50 - 25,
          (float) Math.random() * 50 - 25);
    } else if (getPaintAmount() < 250000 / 2) {

      if (Mng.getElapsedTime() > counter && Back % 29 == 0) {
        counter += 1;
        dir = (temp ? new Vector2(-1, 0) : new Vector2(1, 0));
        temp = !temp;
        Back += 1;
      } else if (Mng.getElapsedTime() > counter && Back % 23 == 0) {
        counter += 1;
        dir = new Vector2(0, 1);
        Back += 1;
      }

    }
  }

  void aplydirection(PIXEL minitarget) {
    dir = new Vector2(minitarget.X - getPosition().x,
        minitarget.Y - getPosition().y);

  }
}
