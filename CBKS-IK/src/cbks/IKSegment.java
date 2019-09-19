package cbks;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;

import cbks.IKVector;

public class IKSegment {
	
	  IKVector a;
	  float angle = 0;
	  float len;
	  IKVector b = new IKVector();
	  
	  

	  IKSegment(float x, float y, float len_) {
	    a = new IKVector(x, y);
	    len = len_;
	    calculateB();
	  }

	  IKSegment(IKSegment parent, float len_) {
	    a = parent.b.copy();
	    len = len_;
	    calculateB();
	  }

	  void follow(IKSegment child) {
	    float targetX = child.a.x;
	    float targetY = child.a.y;
	    follow(targetX, targetY);
	  }

	  void follow(float tx, float ty) {
	    IKVector target = new IKVector(tx, ty);
	    IKVector dir = IKVector.sub(target, a);
	    angle = dir.heading();
	    dir.setMag(len);
	    dir.mult(-1);
	    a = IKVector.add(target, dir);
	  }

	  void setA(IKVector pos) {
	    a = pos.copy();
	    calculateB();
	  }

	  void calculateB() {
	    float dx = len * (float)Math.cos(angle);
	    float dy = len * (float)Math.sin(angle);
	    b.set(a.x+dx, a.y+dy);
	  }

	  void update() {
	    calculateB();
	  }
	  
	  void show(Graphics2D g)
	  {
		    g.setColor(Color.BLACK);
		    g.setStroke(new BasicStroke(5.0f));
		    g.draw(new Line2D.Double(a.x, a.y, b.x, b.y));
		    g.draw(new Ellipse2D.Double(a.x - 3,a.y - 3,6,6));
		    g.draw(new Ellipse2D.Double(b.x - 3,b.y - 3,6,6));
	  }

	}
