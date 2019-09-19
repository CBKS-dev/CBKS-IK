package cbks;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.Timer;


public class Drawing
{
    JLabel view;
    BufferedImage surface;
    Timer timer;
    
    private int width  = 191;
    private int height = 131;
    
    protected float frameRate = 1;
    private int delay = (int)(1000/frameRate);
    
    public int EEPosX = 90;
    public int EEPosY = 90;
    
    IKManipulator manipulator;
    
    public float theta2 = 0;
    public float theta3 = 0;

    public Drawing()
    {
        surface = new BufferedImage(width,height,BufferedImage.TYPE_INT_RGB);
        view = new JLabel(new ImageIcon(surface));
        setup();
    }
    
    public void setup()
    {
        Graphics2D g = (Graphics2D)surface.getGraphics();
        g.setColor(Color.WHITE);
        g.fillRect(0,0,600,400);
        g.setColor(Color.BLACK);
        
        manipulator = new IKManipulator(width/2, height-10);
    	theta2 = manipulator.segments[0].angle;
    	theta3 = manipulator.segments[1].angle;
    	
        // Keep this until I figured out if it's painted on load or not.
        g.drawLine(0, 0, 10, 10);
        g.dispose();
        
        ActionListener listener = new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                draw();
            }
        };
        
        this.setFrameRate(1000);
        timer = new Timer(delay, listener);
        timer.start();
    }
    
    public void draw()
    {
    	try {
    	Graphics2D g = (Graphics2D)surface.getGraphics();
    	
    	g.setColor(Color.WHITE);
    	g.fillRect(0,0,600,400);
    	g.setColor(Color.BLACK);
    	g.setStroke(new BasicStroke(4.0f));
    	
    	// Crosshair
    	g.setColor(Color.ORANGE);
    	g.setStroke(new BasicStroke(2.0f));
    	g.draw(new Ellipse2D.Double(EEPosX-6, EEPosY-6, 12, 12));
    	g.draw(new Line2D.Double(EEPosX, EEPosY-10, EEPosX, EEPosY+10));
    	g.draw(new Line2D.Double(EEPosX-10, EEPosY, EEPosX+10, EEPosY));
    	
    	//g.draw(new Line2D.Double(131/2, 131/2, view.getMousePosition().x, view.getMousePosition().y));
    	//g.draw(new Ellipse2D.Double(131/2 - 5/2, 131/2 - 5/2, 5, 5));
    	
    	manipulator.update(); 
    	theta2 = manipulator.segments[0].angle;
    	theta3 = manipulator.segments[1].angle;
    	manipulator.show(g); 
    	manipulator.setPos(EEPosX, EEPosY); 
    	
    	
    	g.dispose();
    	view.repaint();
    	
    	this.wait(10);
    	
    	
    	} catch(Exception ex)
    	{
    		// The error mostly will be the mouse going off the JLabel of the BufferedImage...
    	}
    }
    
	public void setFrameRate(float theFrameRate)
	{
		this.frameRate = theFrameRate;
		this.delay = (int)(1000/frameRate);
	}
	
}