package cbks;

import java.awt.Graphics2D;

class IKManipulator {

	  IKSegment[] segments = new IKSegment[2];
	  IKVector base;
	  IKVector pos = new IKVector();
	  
	  float A1 = 115; // Millimeters
	  float A2 = 197; // Millimeters
	  
	  float scale = 3.7f;
	  
	  IKManipulator(float x, float y) {
	    base = new IKVector(x, y);
	    segments[0] = new IKSegment(x, y, A1/scale);
	    segments[1] = new IKSegment(segments[0], A2/scale);
	  }
	  
	  void setPos(float x, float y)
	  {
	    pos.x = x;
	    pos.y = y;
	  }

	  void update() {
	    int total = segments.length;
	    IKSegment end = segments[total-1];
	    end.follow(pos.x, pos.y);
	    end.update();

	    for (int i = total-2; i>=0; i--) {
	      segments[i].follow(segments[i+1]);
	      segments[i].update();
	    }

	    segments[0].setA(base);

	    for (int i = 1; i < total; i++) {
	      segments[i].setA(segments[i-1].b);
	    }
	  }

	  void show(Graphics2D g) {
	    for (IKSegment s : segments) {
	      s.show(g);
	    }
	  }
	  
	}
