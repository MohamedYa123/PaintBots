
// package com.tw.paintbots; // !! Do NOT change this package name !!

//package com.tw.paintbots; // !! Do NOT change this package name !!

package bots; // !! Do NOT change this package name !!

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
  private Vector2 dir = new Vector2(-1.0f, 0.0f);

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
  @Override public String  getBotName()   { return "KING"; }
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
  class Node {
    Node parent;
    int X;
    int Y;
    int fcost;
    int hcost;
    boolean Start;
    boolean goal;
    boolean solid;
    boolean open;
    boolean checked;
    int gcost;
    boolean refill;
    int colorcost;
    Vector2 actualtarget;
    float reward = -10000000;
    boolean ispower = false;

    public Node(int x, int y) {
      X = x;
      Y = y;
      actualtarget = new Vector2(x, y);
    }

    boolean setasgoal() {
      if (!solid) {

        goal = true;
        Start = false;
      }
      return !solid;
    }

    void setasstart() {
      Start = true;
      goal = false;
    }

    boolean originalsolid = false;

    void setassolid() {
      Start = false;
      originalsolid = true;
      goal = false;
      solid = true;
    }

    void setnotsolid() {
      solid = false;
    }

    void setasopen() {
      open = true;
    }

    void setrefill() {
      refill = true;
      // solid = false;
    }

    void setaschecked() {
      if (Start == false && goal == false) {
        checked = true;
      }
    }

  }

  class myminiboard {
    int maxwidth;
    int maxheight;
    Node[][] nodes = new Node[maxwidth][maxheight];
    Node currentnode;
    Node startNode;
    Node goalnode;
    ArrayList<Node> openList = new ArrayList<>();
    ArrayList<Node> checkedList = new ArrayList<>();
    boolean goalreached = false;
    int pointsnum = 50;
    GameManager mgr;

    myminiboard(int maxwidth2, int maxheight2) {
      maxwidth = maxwidth2;
      maxheight = maxheight2;
      nodes = new Node[maxwidth][maxheight];
      path = new ArrayList<>();
    }

    void setnewnodes() {
      int newwidth = maxwidth / pointsnum;
      int newheigth = maxheight / pointsnum;
      Node[][] nodesn = new Node[newwidth][newheigth];
      if (maxwidth % pointsnum != 0) {
        nodesn = new Node[newwidth + 1][newheigth + 1];
      } else {
        nodesn = new Node[newwidth][newheigth];
      }
      // int w = 0;
      // int h = 0;

      for (int w = 0; w < maxwidth; w++) {

        for (int h = 0; h < maxheight; h++) {
          int wk = w / pointsnum;
          int hk = h / pointsnum;

          if (w == maxwidth - 1 && h == maxheight - 1) {
            w = w;
          }
          Node nodesd = nodesn[w / pointsnum][h / pointsnum];
          if (nodesd == null) {
            nodesn[w / pointsnum][h / pointsnum] = new Node(w, h);
            nodesd = nodesn[w / pointsnum][h / pointsnum];
          }


          if (nodes[w][h].originalsolid) {
            nodesn[w / pointsnum][h / pointsnum].solid = true;


            if (w / pointsnum + 1<newwidth ){
            if (nodesn[w / pointsnum + 1][h / pointsnum] == null) {
              nodesn[w / pointsnum + 1][h / pointsnum] =
                  new Node(w + pointsnum, h);
            }
            nodesn[w / pointsnum + 1][h / pointsnum].solid = true;
          }


          if (h / pointsnum + 1<newheigth ){
            if (nodesn[w / pointsnum][h / pointsnum + 1] == null) {
              nodesn[w / pointsnum][h / pointsnum + 1] =
                  new Node(w, h + pointsnum);
            }
            nodesn[w / pointsnum][h / pointsnum + 1].solid = true;
          }


          if (h / pointsnum - 1>0 ){
            if (nodesn[w / pointsnum][h / pointsnum - 1] == null) {
              nodesn[w / pointsnum][h / pointsnum - 1] =
                  new Node(w, h - pointsnum);
            }
            nodesn[w / pointsnum][h / pointsnum - 1].solid = true;
          }


          if (w / pointsnum - 1>0 ){
            if (nodesn[w / pointsnum - 1][h / pointsnum] == null) {
              nodesn[w / pointsnum - 1][h / pointsnum] =
                  new Node(w - pointsnum, h);
            }
            nodesn[w / pointsnum - 1][h / pointsnum].solid = true;
          }


            if (wk - 1 > 0) {
              // nodesn[wk - 1][hk].solid = true;
            }
            if (hk - 1 > 0) {
              // nodesn[wk][hk - 1].solid = true;
            }
          }
          if (nodes[w][h].refill) {
            nodesn[w / pointsnum][h / pointsnum].setrefill();
            nodesn[w / pointsnum][h / pointsnum].actualtarget =
                new Vector2(w, h);
          }
          // w++;
        }
        // h++;
      }
      nodes = nodesn;
    }

    boolean wassolid;

    void setstartnode(int x, int y) {
      nodes[x][y].setasstart();
      wassolid = nodes[x][y].solid;
      startNode = nodes[x][y];
      currentnode = nodes[x][y];
    }

    void setgoalnode(int x, int y) {
      nodes[x][y].setasgoal();
      goalnode = nodes[x][y];
    }

    void setsolidnode(int x, int y) {
      nodes[x][y].setassolid();

    }

    void getcost(Node node) {
      int xdistance = Math.abs(node.X - startNode.X);
      int ydistance = Math.abs(node.Y - startNode.Y);
      node.gcost = xdistance + ydistance;
      xdistance = Math.abs(node.X - goalnode.X);
      ydistance = Math.abs(node.Y - goalnode.Y);
      node.hcost = xdistance + ydistance;
      // PaintColor pc = mCanvas.getColor(node.X, node.Y);
      // PaintColor mpc = getState().paint_color;
      // int pcost = 0;
      // if (pc == mpc) {
      // pcost = +100;
      // } else if (pc != mpc.NONE) {
      // pcost = -100;
      // }
      node.fcost = node.gcost + node.hcost;// + pcost;
    }

    void freegetcost(Node node) {
      int xdistance = Math.abs(node.X - startNode.X);
      int ydistance = Math.abs(node.Y - startNode.Y);
      node.gcost = -xdistance - ydistance;
      xdistance = Math.abs(node.X - goalnode.X);
      ydistance = Math.abs(node.Y - goalnode.Y);
      node.hcost = xdistance + ydistance;
      PaintColor pc = mCanvas.getColor(node.X, node.Y);
      PaintColor mpc = getState().paint_color;
      int pcost = 2;
      for (int iq = 0; iq < 3; iq++) {
        float gg = Math.abs(node.X - (int) mgr.getPlayerState(iq).pos.x);// (int)
                                                                         // mgr.getPlayerState(iq).pos.x;
        float gg2 = Math.abs(node.Y - (int) mgr.getPlayerState(iq).pos.y);
        pcost -= Math.abs(node.X - (int) mgr.getPlayerState(iq).pos.x)

            + Math.abs(node.Y - (int) mgr.getPlayerState(iq).pos.y);
      }
      if (pc == mpc) {
        pcost *= 20;
      } else if (pc != PaintColor.NONE) {
        pcost /= 2;
      }
      if (node.gcost < 500) {
        // pcost = 50;
      }
      node.fcost = pcost + node.gcost;
    }

    // Board mBoard;
    Canvas mCanvas;

    void setcostonnodes() {
      int x = 0;
      int y = 0;
      // mBoard = mgr.getBoard();
      mCanvas = mgr.getCanvas();
      while (x < maxwidth / pointsnum && y < maxheight / pointsnum) {
        getcost(nodes[x][y]);
        x++;
        if (x == maxwidth / pointsnum) {
          x = 0;
          y++;
        }
      }
    }

    void freesetcostonnodes() {
      int x = 0;
      int y = 0;
      // mBoard = mgr.getBoard();
      mCanvas = mgr.getCanvas();
      while (x < maxwidth / pointsnum && y < maxheight / pointsnum) {
        freegetcost(nodes[x][y]);
        if (nodes[x][y].X > 0 / pointsnum && !nodes[x - 1][y].solid
            && nodes[x][y].X < maxwidth - pointsnum) {
          int kcost = nodes[x - 1][y].fcost;
          int kcost2 = nodes[x][y].fcost;
          // nodes[x][y].fcost = kcost - kcost2;
          // nodes[x - 1][y].fcost = kcost - kcost2;
        } else {
          nodes[x][y].fcost += 2000;
        }
        if (nodes[x][y].Y > 0 / pointsnum && !nodes[x][y - 1].solid
            && nodes[x][y].Y < maxheight - pointsnum) {
          int kcost = nodes[x][y - 1].fcost;
          int kcost2 = nodes[x][y].fcost;
          // nodes[x][y].fcost = kcost - kcost2;
          // nodes[x][y - 1].fcost = kcost - kcost2;
        } else {
          nodes[x][y].fcost += 2000;
        }
        x++;
        if (x == maxwidth / pointsnum) {
          x = 0;
          y++;
        }
      }
    }

    int bestfcost = 90000;
    int bestindx = 0;

    void search() {
      if (goalreached ) {
        return;
      }
      int x = currentnode.X;
      int y = currentnode.Y;
      currentnode.setaschecked();
      // checkedList.add(currentnode);
      openList.remove(currentnode);
      // int i = openList.size();
      // open the up node
      int newwidth = maxwidth / pointsnum;
      int newheigth = maxheight / pointsnum;
      // try {
      if (y / pointsnum - 1 >= 0) {
        openNode(nodes[x / pointsnum][y / pointsnum - 1]);
        // minicheck(i);

      }

      if (x / pointsnum - 1 >= 0) {
        openNode(nodes[x / pointsnum - 1][y / pointsnum]);
        // minicheck(i);

      }
      if (y / pointsnum + 1 < newheigth) {
        openNode(nodes[x / pointsnum][y / pointsnum + 1]);
        // minicheck(i);

      }
      if (x / pointsnum + 1 < newwidth) {
        openNode(nodes[x / pointsnum + 1][y / pointsnum]);
        // minicheck(i);

      }
      bestindx = 0;
      for (int ij = 0; ij < openList.size(); ij++) {
        // Node n = openList.get(ij);
        // try {
        minicheck(ij);
        // } catch (Exception e) {
        // e.getMessage();
        // }
      }
      if(openList.size()==0){
        int jhhh=1;
        while(openList.size()==0 && x / pointsnum - jhhh>0){
        openNode(nodes[x / pointsnum - jhhh][y / pointsnum-jhhh]);
          jhhh++;
      }
        minicheck(0);
      }
      currentnode = best;

      // }// catch (Exception ex) {
      // int agh = ex.getStackTrace()[0].getLineNumber();
      // int ir = 500;
      // }
      // trackpath();
      if (currentnode == goalnode) {
        goalreached = true;
        trackpath();
      }
    }

    Node best;

    void minicheck(int i) {
      if (i >= openList.size() || openList.get(i).checked) {
        return;
      }
      // try {
      if (openList.get(i).fcost < bestfcost ) {
        bestindx = i;
        bestfcost = openList.get(i).fcost;
      } else if (openList.get(i).fcost == bestfcost) {
        if (openList.get(i).gcost < openList.get(bestindx).gcost) {
          bestindx = i;
        } else if (openList.get(i).reward > openList.get(bestindx).reward) {
          if (!(goalnode.ispower) || goalnode.refill ||true ) {
            bestindx = i;
          }
        }
      }
      best = openList.get(bestindx);
      // } catch (Exception e) {
      // e.getMessage();
      // }
    }

    int step = 0;

    void autosearch() {
      // setcostonnodes();
      bestindx = 0;
      bestfcost = 90000;
      int depth = 750;
      step = 0;
      openList.clear();
      goalreached = false;
      if (startNode.solid) {
        depth = 750;
      }
      while (step < depth && !goalreached) {
        try {
          if (startNode.solid) {
            // System.out.println(step);
          }
          search();
          if(step%10==0){
         //   System.out.println(openList.size()+" ");
          }
        } catch (Exception e) {
          // System.out.print("\r\n" + openList.size() + " " + e.getMessage()
          // + "err line" + e.getStackTrace()[0].getLineNumber());
        }

        step++;
      }
    }

    ArrayList<Node> path;

    void trackpath() {
      Node current = goalnode;
      // path.add(current);
      path.clear();
      while (current != startNode) {
        path.add(current);
        current = current.parent;
      }
      // int yu = 0;
    }

    void openNode(Node node) {
      if (node.open == false && node.checked == false && node.solid == false) {
        node.setasopen();
        node.parent = currentnode;
        openList.add(node);
      }
    }
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
    Vector2 pos = getPosition();

    // random = new Random(seed);

    mgr = GameManager.get();
    int ls = mgr.getBoard().getHeight() * mgr.getBoard().getWidth();
    refills = new Vector2[ls];
    breaks = new Vector2[ls];
    // breaks = new Vector2[5000];
    ItemType[] cells = new ItemType[ls];
    int bri = 0;
    int bri2 = 0;
    int mycolor = 0;
    mycolor = getPaintColor().getColorID() + 1;
    final int jcolor = 10 + mycolor;
    final int jcolor2 = 20 + mycolor;
    myboard = new myminiboard(mgr.getBoard().getWidth(), mgr.getBoard().getHeight());
    mainrefill = new Vector2(0, 0);
    int yt = 10000;
    int indx = 0;
    for (int u1 = 0; u1 < mgr.getBoard().getWidth(); u1++) {
      for (int u2 = 0; u2 < mgr.getBoard().getHeight(); u2++) {

        ItemType it = mgr.getBoard().getType(u1, u2);
        int y = it.getTypeID();
        Node nd = new Node(u1, u2);
        myboard.nodes[u1][u2] = nd;
        if (y == 2 || y == 1 || (y > 20 && y != jcolor2) || !it.isPassable()) {
          breaks[bri] = new Vector2(u1, u2);
          myboard.nodes[u1][u2].setassolid();
          bri++;
        } else if (y == 10 || y == jcolor) {
          refills[bri2] = new Vector2(u1, u2);
          if (u2 < yt) {
            myboard.nodes[u1][u2].setrefill();
            mainrefill.x = u1;
            mainrefill.y = u2;
            // yt = u2;
            // nd.setrefill();
            bri2++;
          }
        }
        cells[u1 + u2 * mgr.getBoard().getWidth()] = it;

        indx++;
      }
    }
    // mainrefill.x /= bri2;
    // mainrefill.y /= bri2;
    // myboard.nodes[(int) mainrefill.x][(int) mainrefill.y].setrefill();
    try {
      myboard.setnewnodes();
    } catch (Exception ex) {
      ex.getMessage();
    }
    Vector2[] breaks2 = new Vector2[bri];
    for (int j = 0; j < bri; j++) {
      breaks2[j] = breaks[j];
    }
    breaks = breaks2;
    Vector2[] refills2 = new Vector2[bri2];
    for (int j = 0; j < bri2; j++) {
      refills2[j] = refills[j];
    }
    mgr = GameManager.get();
    myboard.mgr = mgr;
    refills = refills2;
  }

  Vector2[] breaks;
  Vector2[] refills;
  GameManager mgr;
  myminiboard myboard;
  int pointsnum = 50;
  boolean change = true;
  Random r = new Random();
  int myid;
  Vector2 target;
  // ItemType[] cells;
  Vector2 mainrefill;
  int frame;
  boolean Imthere = false;
  int await = -1;
  Node htarget = new Node(0, 0);
  float levelcase;
  int calcindx = 0;
  int sdivide = 4;
  boolean targetchanged = false;
  boolean first = true;
  ArrayList<PlayerState> states;// = new ArrayList<>();
  PlayerState mPlayerState;// = getState();
  ArrayList<PowerUp.Info> powersme;// = mgr.getActivePowerUps();
  Canvas mCanvas;// = mgr.getCanvas();
  PaintColor myColor;// = mPlayerState.paint_color;
  int indxofpath = 10000;
  Vector2 targetacuered;

  void resetboard() {
    for (Node[] nodef : myboard.nodes) {
      for (Node node : nodef) {
        node.Start = false;
        node.open = false;
        node.checked = false;
        node.goal = false;
      }
    }
    // targetacuered = null;
    Imthere = false;
    myboard.goalreached = false;
    myboard.step = 0;
    myboard.bestfcost = 90000;
    myboard.bestindx = 0;
    myboard.openList.clear();
    myboard.path.clear();
    myboard.checkedList.clear();
    // target = null;
    indxofpath = 10000;
  }

  Vector2 calc(Vector2 pos) {
    Vector2 opos = getPosition();
    Vector2 mydir = new Vector2(pos.x - opos.x, pos.y - opos.y);
    return mydir;
  }

  // --------------------------------------------------------------- //
  /** This is a helper method called in the update method. */
  public void myUpdate() {
    // ToDo: implement a method, called in each update loop
    if (await > 0 && myboard.path.size() > 0 && myboard.path.get(0).refill) {
      aplyrefill();
      await--;
      // frame = frame;
    } else if (await > 0) {
      await--;
      return;

    }

    if (frame % 1 == 0 && frame % 7 != 0) {// && await <= 0
      // && (targetacuered == target || first || myboard.path.size() == 0)) {
      try {
        if (calcindx == 0) {

        }
        if (calcindx == 0) {

        }

        calcandselect();
        if (calcindx == 0) {
          // System.out.println("awww");
          float errk = 0;
          float iifw = 1;
          if (htarget.refill) {
            iifw = 0;
          }
          // System.out.println("Reward : " + htarget.reward);
          if (myboard.path.size() > 0 && myboard.path.get(0) == htarget) {
            targetchanged = false;
          } else {
            targetchanged = true;
            // oldtarget = htarget;
          }
          if (target != null) {
            errk = Math.abs(htarget.X - target.x) + Math.abs(htarget.Y - target.y);
          }

          if (targetacuered == target || ((htarget.ispower || htarget.refill)
              && targetchanged)) {
            indxofpath = 10000;
            Imthere = false;
            myboard.path.clear();
            target = new Vector2(htarget.X, htarget.Y);
          } else {
            // targetchanged = false;
          }
        } else {
          // targetchanged = false;
        }
      } catch (Exception e) {
        int k = 90;
      }
    }
    if (frame % 7 == 0 && frame > 0 && (targetchanged) && indxofpath > 500
        && await <= 0) {

      resetboard();
      if (frame % 7 == 0) {
        int yiu = 9;
      }
      myboard.setstartnode((int) getPosition().x / pointsnum,
          (int) getPosition().y / pointsnum);
      myboard.setgoalnode(htarget.X / pointsnum,
          htarget.Y / pointsnum);
      myboard.setcostonnodes();
      // myboard.step = 0;
      try {
        if (myboard.startNode.solid) {
          int i = 0;
          myboard.setstartnode((int) getPosition().x / pointsnum,
              (int) getPosition().y / pointsnum);
        }
        myboard.autosearch();
        // targetchanged = false;
        target = new Vector2(htarget.X, htarget.Y);
      } catch (Exception ex) {
        ex.getMessage();
      }
    }

    if (frame % 5 == 0 && frame > 0 && await <= 0
        && !(myboard.path == null || myboard.path.size() <= 0)) {
      // Vector2 vlc = new Vector2();
      // System.out.println("reached " + myboard.startNode.solid + " goal "
      // + myboard.goalreached + " " + myboard.path.size());
      Imthere = false;
      Vector2 vlc = new Vector2();
      try {
        vlc = new Vector2(myboard.path.get(0).X, myboard.path.get(0).Y);
      } catch (Exception ex) {
        vlc = vlc;
      }
      float errbest = 1000000000;
      for (int it = myboard.path.size() - 1; it > 0; it--) {
        if (myboard.path.get(it) == null) {
          float stop = 353;
        }
        vlc = new Vector2((float) myboard.path.get(it).X,
            (float) myboard.path.get(it).Y);
        float err = Math.abs(vlc.x - getPosition().x)
            + Math.abs(vlc.y - getPosition().y);

        if (err < errbest && (indxofpath > it)
            || indxofpath > myboard.path.size() - 1) {
          if (err > pointsnum) {
            // frame = -10;
          }
          vlc = new Vector2((float) myboard.path.get(it).X,
              (float) myboard.path.get(it).Y);
          errbest = err;
          indxofpath = it;
          break;
        }
      }
      float err = Math.abs(target.x - getPosition().x)
          + Math.abs(target.y - getPosition().y);
      if (frame % 1 == 0) {

      }
      if (err < pointsnum * 2 && indxofpath < 2 || myboard.path.size() == 1) {
        boolean h = true;// myboard.nodes[(int) (getPosition().x + target.x)
        // / (2 * pointsnum)][(int) (getPosition().y + target.y)
        // / (2 * pointsnum)].solid;
        if (myboard.path.get(0) == null) {
          float stop = 353;
        }

        vlc = myboard.path.get(0).actualtarget;
        // frame = -20;
        if (err < pointsnum * 2) {
          // if (!htarget.refill) {
          await = 15;
          // }
          if (myboard.path.get(0).refill) {
            await = 20;
            // refilnode = myboard.path.get(0);
          }
          // frame++;
          change = true;
          Imthere = true;

          // frame = -20;
          indxofpath = 10000;
          if (err < pointsnum) {
            change = true;
            Imthere = true;
            await = 5;
            // frame = -5;

          }

          targetacuered = myboard.path.get(0).actualtarget;

        }
        // System.out.println("Target aquired");
      }
      // if (target != null) {
      dir = calc(vlc);
      // myUpdate();
      // System.out.print("target " + target + " " + vlc + " Pos : "
      // + getPosition() + " dir " + dir + " err " + err + " " + targetchanged
      // + "Target acured " + targetacuered + "\r\n");
    }
    frame++;
  }

  void aplyrefill() {
    if (await % 1 == 0) {
      // Vector2 re = refilnode.actualtarget;
      // System.out.println(getPosition() + " " + dir);
      if (await % 2 == 0) {
        // dir = calc(new Vector2(500, 370));
      } else {
        // dir = calc(new Vector2(500, 320));
      }
      // System.out.println(getPosition() + " " + dir);
    }
  }

  void calcandselect() {
    states = new ArrayList<>();
    mPlayerState = getState();
    powersme = mgr.getActivePowerUps();
    mCanvas = mgr.getCanvas();
    myColor = mPlayerState.paint_color;
    levelcase = (float) Math.pow(1 - (float) (mPlayerState.paint_amount)
        / (float) mPlayerState.max_paint_amount, 2);
    for (int i = 0; i < 4; i++) {
      if (i != mPlayerState.player_id && mgr.getPlayerState(i).is_active) {
        states.add(mgr.getPlayerState(i));
      }
    }
    if (htarget.ispower) {
      float kk = 0;
    }
    calcsinglenode(htarget);
    int widthl = myboard.nodes.length / sdivide;
    int firsti = calcindx * widthl;
    int last = (calcindx + 1) * widthl;
    calcindx++;
    if (calcindx > sdivide - 1) {
      calcindx = 0;
    }

    for (int counter = firsti; counter < last; counter++) {
      Node[] nodes2 = myboard.nodes[counter];
      for (Node node : nodes2) {
        calcsinglenode(node);
        if ((node.reward > htarget.reward) || first
            || (htarget.actualtarget == targetacuered)) {
          htarget = node;
          // targetchanged = true;
          first = false;
        }
      }
    }
  }

  // targetacuered != node.actualtarget
  // &&// = new ArrayList<>();
  // = getState();
  void calcsinglenode(Node node) {
    // ArrayList<PlayerState> states = new ArrayList<>();
    // PlayerState mPlayerState = getState();
    // ArrayList<PowerUp.Info> powersme = mgr.getActivePowerUps();
    // Canvas mCanvas = mgr.getCanvas();
    // PaintColor myColor = mPlayerState.paint_color;
    // levelcase = (float) Math.pow(1 - (float) (mPlayerState.paint_amount)
    // / (float) mPlayerState.max_paint_amount, 1);
    // for (int i = 0; i < 4; i++) {
    // if (i != mPlayerState.player_id && mgr.getPlayerState(i).is_active) {
    // states.add(mgr.getPlayerState(i));
    // }
    // }
    float reward = 0;
    float totaldiff = 0;
    if (node.solid) {// || !(node.X < myboard.maxwidth && node.X > 0
      // && node.Y < myboard.maxheight && node.Y > 0)) {
      return;
    }
    // getting location
    for (PlayerState ps : states) {
      totaldiff += Math.abs(node.X - ps.pos.x) + Math.abs(node.Y - ps.pos.y);
    }
    totaldiff /= 100f;
    // getting color , mycolor means low reward none positve reward ,
    // other extrapositive
    float mydiff = Math.abs(node.X - getPosition().x)
        + Math.abs(node.Y - getPosition().y) / 100;
    PaintColor pColor = mCanvas.getColor(node.X, node.Y);
    float colorreward = 0;

    if (pColor == myColor) {

      colorreward += (levelcase) * 20;
    } else if (pColor == PaintColor.NONE) {
      colorreward += (1 - levelcase) * 80;
    } else {
      colorreward += (1 - levelcase) * 160;
    }
    // check neghbour Nodes
    int idy = node.Y / pointsnum;
    int idx = node.Y / pointsnum;
    float minicolorreward = 0;
    for (int tg = 1; tg < 7; tg++) {
      if (idx + tg < myboard.nodes.length) {
        Node nn = myboard.nodes[idx + tg][idy];
        PaintColor tp = mCanvas.getColor(nn.X, nn.Y);
        if (tp == myColor) {

          minicolorreward += (levelcase) * 2;
        } else if (tp == PaintColor.NONE) {
          minicolorreward += (1 - levelcase) * 8;
        } else {
          minicolorreward += (1 - levelcase) * 16;
        }
      }
      if (idy + tg < myboard.nodes.length) {
        Node nn = myboard.nodes[idx][idy + tg];
        PaintColor tp = mCanvas.getColor(nn.X, nn.Y);
        if (tp == myColor) {

          minicolorreward += (levelcase) * 2;
        } else if (tp == PaintColor.NONE) {
          minicolorreward += (1 - levelcase) * 8;
        } else {
          minicolorreward += (1 - levelcase) * 16;
        }
      }
      if (idx - tg > -1) {
        Node nn = myboard.nodes[idx - tg][idy];
        PaintColor tp = mCanvas.getColor(nn.X, nn.Y);
        if (tp == myColor) {

          minicolorreward += (levelcase) * 2;
        } else if (tp == PaintColor.NONE) {
          minicolorreward += (1 - levelcase) * 8;
        } else {
          minicolorreward += (1 - levelcase) * 16;
        }
      }
      if (idy - tg > -1) {
        Node nn = myboard.nodes[idx][idy - tg];
        PaintColor tp = mCanvas.getColor(nn.X, nn.Y);
        if (tp == myColor) {

          minicolorreward += (levelcase) * 2;
        } else if (tp == PaintColor.NONE) {
          minicolorreward += (1 - levelcase) * 8;
        } else {
          minicolorreward += (1 - levelcase) * 16;
        }
      }
    }
    // check if it's powersome || refill
    float exrrareward = 0;
    if (node.refill) {
      if (levelcase > 0.7) {
        exrrareward += levelcase * 9000;
      }
    } else {
      for (PowerUp.Info pu : powersme) {
        Node punode = myboard.nodes[pu.position[0] / pointsnum][pu.position[1]
            / pointsnum];
        if (punode.X == node.X && punode.Y == node.Y) {
          if (getPowerUps().size() <= 2) {
            boolean padd = true;
            for (PowerUpType info : getPowerUps()) {
              if (info.getTypeID() == pu.type.getTypeID()
                  || (pu.type.getTypeID() < 4 && getPowerUps().size() >= 2)) {
                padd = false;
                break;
              }
            }
            node.ispower = true;
            if (padd) {
              exrrareward += calcpower(pu.type.getTypeID(), false);
              node.actualtarget = new Vector2(pu.position[0], pu.position[1]);
              break;
            } else {
              exrrareward += calcpower(pu.type.getTypeID(), true);
              node.actualtarget = new Vector2(pu.position[0], pu.position[1]);
            }
          }
          break;
        }
      }
    }
    reward = returnreward(colorreward, minicolorreward, exrrareward, mydiff,
        totaldiff, 0f);
    // colorreward + minicolorreward * 0 + exrrareward + mydiff * +0.002f
    // + totaldiff;
    // /
    // 5
    // +
    node.reward = reward;
  }

  float returnreward(float colorreward, float minicolorreward,
      float exrrareward, float mydiff, float totaldiff, float oldtargetdiff) {
    float rw = colorreward + minicolorreward / 2 + exrrareward
        + mydiff - 0.5f + totaldiff;
    return rw;
  }

  float calcpower(int id, boolean sizecase) {
    float reward = 2800;
    switch (id) {
      case 4:
        reward += levelcase * 9000;
        break;
      case 3:
      case 2:
      case 1:
      case 0:
        if (sizecase) {
          reward -= 3000;
        }

    }
    return reward;
  }

}
