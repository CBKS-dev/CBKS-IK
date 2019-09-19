package cbks;

import java.awt.*;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import cbks.Drawing;
import cbks.SerialClass;

import javax.swing.JButton;
import javax.swing.UIManager;
import javax.swing.JSlider;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.awt.event.ActionEvent;
import javax.swing.JSpinner;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.border.BevelBorder;

@SuppressWarnings("serial")
public class GUIFrame extends JFrame {
	
	public static BufferedReader input;
	public static OutputStream   output;
	private SerialClass serialObject;
	
	public static synchronized void writeData(String theData)
	{
		System.out.println("[SerialConfig] Sent: " + theData);
		try {
			output.write(theData.getBytes());
		} catch (IOException e) {
			System.out.println("[SerialConfig] Could not write to port");
			e.printStackTrace();
		}
	}
	
	@SuppressWarnings("unused")
	private void initComm()
	{
		try {
			serialObject = new SerialClass();
			serialObject.init();
			input  = SerialClass.input;
			output = SerialClass.output;
			InputStreamReader Ir = new InputStreamReader(System.in);
			BufferedReader    Br = new BufferedReader(Ir);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private boolean initComm(String thePort)
	{
		boolean connectionStatus = false;
		try {
			serialObject = new SerialClass();
			connectionStatus = serialObject.init(thePort) == 0? true : false;
			input  = SerialClass.input;
			output = SerialClass.output;
			//InputStreamReader Ir = new InputStreamReader(System.in);
			//BufferedReader    Br = new BufferedReader(Ir);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return connectionStatus;
	}

	
	private JPanel contentPane;
	private JButton btnConnect;
	private JButton btnDisconnect;
	
	public JSpinner spnX;
	public JSpinner spnY;
	public JSpinner spnZ;
	
	public JSlider sldX;
	public JSlider sldY;
	public JSlider sldZ;
	
	public Label lblT1;
	public Label lblT2;
	public Label lblT3;
	
	public float lerp(float a, float b, float f)
	{
		return a + f * (b - a);
	}
	
	public void updateAngleLabels(float t1, float t2)
	{
		float T1 = (float)Math.round(Math.abs(Math.toDegrees(t1)));
		float T2 = (float)Math.round(Math.abs(Math.toDegrees(t2)));
		lblT1.setText("Theta 1: "+T1+"\u00BA");
		lblT2.setText("Theta 2: "+T2+"\u00BA");
	}
	
	private void updatePositionLabels(float p1x, float p1y, float p2x, float p2y)
	{
	  lblElbow.setText("Elbow: ("+Math.round(Math.abs(p1x))+", "+Math.round(Math.abs(p1y))+")");
	  lblEEf.setText("End Effector ("+Math.round(Math.abs(p2x))+", "+Math.round(Math.abs(p2y))+")");
	}
	
	private Label lblPos;
	private Label lblEEf;
	private Label lblElbow;
	private JSlider sldSp;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
					//UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsClassicLookAndFeel");
					GUIFrame frame = new GUIFrame();
					frame.setVisible(true);

					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	
	boolean isReleased = false;
	
	public GUIFrame() {
		
		setResizable(false);
		setTitle("Javaduino Test GUI");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 420, 400);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		FlowLayout flowLayout = (FlowLayout) panel.getLayout();
		flowLayout.setVgap(0);
		flowLayout.setHgap(0);
		panel.setBounds(209, 55, 195, 135);
		contentPane.add(panel);
		
		
		
		
		Drawing canvas = new Drawing();
		canvas.view.addMouseListener(new MouseListener()
				{
					
					@Override
					public void mouseClicked(MouseEvent arg0) {
						
					}

					@Override
					public void mouseEntered(MouseEvent arg0) {
						// TODO 
						
					}

					@Override
					public void mouseExited(MouseEvent arg0) {
						// TODO 
						
					}

					@Override
					public void mousePressed(MouseEvent arg0) {
						isReleased = false;
						//float valueX = lerp(sldY.getValue(), canvas.view.getMousePosition().x, 0.5f);
						//float valueY = lerp(sldZ.getValue(), canvas.view.getMousePosition().y, 0.5f);
						//sldY.setValue((int)valueX);
						//sldZ.setValue((int)valueY);
					    sldY.setValue((int)canvas.view.getMousePosition().x);
					    sldZ.setValue((int)canvas.view.getMousePosition().y);
					}

					@Override
					public void mouseReleased(MouseEvent arg0) {
						
						isReleased = true;
						sldY.setValue(sldY.getValue());
						sldZ.setValue(sldZ.getValue());
					}
			
				});
		
		panel.add(canvas.view);
		
		
		JSpinner spnCOM = new JSpinner();
		spnCOM.setBounds(351, 278, 38, 20);
		contentPane.add(spnCOM);
		
		Label lblCOM = new Label("Port COM");
		lblCOM.setBounds(295, 277, 50, 22);
		contentPane.add(lblCOM);
		
		spnX = new JSpinner();
		spnX.addChangeListener(new ChangeListener() {
			
			@Override
			public void stateChanged(ChangeEvent e) {
				sldX.setValue((int) spnX.getValue());
				int T3 = (int) Math.toDegrees(Math.atan2(sldY.getValue(), sldX.getValue()));
				lblT3.setText("Theta 3: "+T3+"\u00BA");
			}
		});
		spnX.setBounds(165, 53, 38, 20);
		contentPane.add(spnX);
		
		spnY = new JSpinner();
		spnY.addChangeListener(new ChangeListener() {

			@Override
			public void stateChanged(ChangeEvent e) {
				sldY.setValue((int)   spnY.getValue());
				canvas.EEPosX = (int) sldY.getValue(); 
				updateAngleLabels(canvas.theta2,canvas.theta3);
				updatePositionLabels(canvas.manipulator.segments[1].a.x -96,
									 canvas.manipulator.segments[1].a.y -121,
									 canvas.manipulator.segments[1].b.x -96,
									 canvas.manipulator.segments[1].b.y -121);
				
				int T3 = (int) Math.toDegrees(Math.atan2(sldY.getValue(), sldX.getValue()));
				lblT3.setText("Theta 3: "+T3+"\u00BA");
			}
			
		});
		spnY.setBounds(165, 104, 38, 20);
		contentPane.add(spnY);
		
		spnZ = new JSpinner();
		spnZ.addChangeListener(new ChangeListener() {

			@Override
			public void stateChanged(ChangeEvent e) {
				sldZ.setValue((int)   spnZ.getValue());
				canvas.EEPosY = (int) sldZ.getValue(); 
				updateAngleLabels(canvas.theta2,canvas.theta3);
				updatePositionLabels(canvas.manipulator.segments[1].a.x -96,
									 canvas.manipulator.segments[1].a.y -121,
									 canvas.manipulator.segments[1].b.x -96,
									 canvas.manipulator.segments[1].b.y -121);
			}
			
		});
		spnZ.setBounds(165, 156, 38, 20);
		contentPane.add(spnZ);
		
		sldX = new JSlider();
		sldX.setMinimum(0);
		sldX.setMaximum(312);
		sldX.addChangeListener(new ChangeListener()
		{
			@Override
			public void stateChanged(ChangeEvent e) {
				int T3 = (int) Math.toDegrees(Math.atan2(sldY.getValue(), sldX.getValue()));
				spnX.setValue((int) sldX.getValue());
				lblT3.setText("Theta 3: "+T3+"\u00BA");
			}
			
		});
		sldX.setMinorTickSpacing(52);
		sldX.setPaintTicks(true);
		sldX.setMajorTickSpacing(104);
		sldX.setBounds(10, 50, 150, 26);
		contentPane.add(sldX);
		
		sldY = new JSlider();
		sldY.setMajorTickSpacing(104);
		sldY.setMaximum(312);
		sldY.addChangeListener(new ChangeListener()
		{
			@Override
			public void stateChanged(ChangeEvent e) {
				spnY.setValue((int)   sldY.getValue());
				canvas.EEPosX = (int) sldY.getValue();
				updateAngleLabels(canvas.theta2,canvas.theta3);
				updatePositionLabels(canvas.manipulator.segments[1].a.x -96,
									 canvas.manipulator.segments[1].a.y -121,
									 canvas.manipulator.segments[1].b.x -96,
									 canvas.manipulator.segments[1].b.y -121);
				
				int T3 = (int) Math.toDegrees(Math.atan2(sldY.getValue(), sldX.getValue()));
				spnX.setValue((int) sldX.getValue());
				lblT3.setText("Theta 3: "+T3+"\u00BA");
			}
			
		});
		sldY.setPaintTicks(true);
		sldY.setMinorTickSpacing(52);
		sldY.setBounds(10, 102, 150, 26);
		contentPane.add(sldY);
		
		sldZ = new JSlider();
		sldZ.setMaximum(312);
		sldZ.addChangeListener(new ChangeListener()
		{
			@Override
			public void stateChanged(ChangeEvent e) {
				canvas.EEPosY =(int) (spnZ.getValue());
				spnZ.setValue((int)sldZ.getValue());
				updateAngleLabels(canvas.theta2,canvas.theta3);
				updatePositionLabels(canvas.manipulator.segments[1].a.x -96,
									 canvas.manipulator.segments[1].a.y -121,
									 canvas.manipulator.segments[1].b.x -96,
									 canvas.manipulator.segments[1].b.y -121);
			}
			
		});
		sldZ.setToolTipText("");
		sldZ.setMajorTickSpacing(104);
		sldZ.setPaintTicks(true);
		sldZ.setMinorTickSpacing(52);
		sldZ.setBounds(10, 156, 150, 26);
		contentPane.add(sldZ);
		
		lblT1 = new Label("Theta 1:");
		lblT1.setBounds(209, 196, 85, 22);
		contentPane.add(lblT1);
		
		lblT2 = new Label("Theta 2:");
		lblT2.setBounds(209, 224, 85, 22);
		contentPane.add(lblT2);
		
		lblT3 = new Label("Theta 3:");
		lblT3.setBounds(209, 252, 85, 22);
		contentPane.add(lblT3);
		
		JCheckBox chckbxLed = new JCheckBox("LED 13");
		chckbxLed.setBounds(295, 311, 97, 23);
		contentPane.add(chckbxLed);
		
		Label lblStatus = new Label("Status:");
		lblStatus.setBounds(10, 340, 264, 28);
		contentPane.add(lblStatus);
		lblStatus.setText("Status: Disconnected.");
		
		btnConnect = new JButton("Connect");
		btnConnect.setBounds(10, 277, 125, 23);
		contentPane.add(btnConnect);
		btnConnect.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnDisconnect.setEnabled(true);
				lblStatus.setText("Status: Connecting...");
				if(initComm("COM"+spnCOM.getValue().toString()))
				{
					btnConnect.setEnabled(false);
					lblCOM.setEnabled(false);
					spnCOM.setEnabled(false);
					lblStatus.setText("Status: Connected.");
					
				} else {
					lblCOM.setEnabled(true);
					spnCOM.setEnabled(true);
					btnConnect.setEnabled(true);
					lblStatus.setText("Status: Disconnected.");
					btnDisconnect.setEnabled(false);
				}
				
			}
		});
		
	    btnDisconnect = new JButton("Disconnect");
		btnDisconnect.setBounds(149, 277, 125, 23);
		contentPane.add(btnDisconnect);
		btnDisconnect.setEnabled(false);
		btnDisconnect.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnDisconnect.setEnabled(false);
				lblStatus.setText("Status: Disconnecting...");
				System.out.println("[SerialConfig] Disconnecting from port...");
				serialObject.close();
				lblStatus.setText("Status: Disconnected.");
				System.out.println("[SerialConfig] Disconnected.");
				btnConnect.setEnabled(true);
				lblCOM.setEnabled(true);
				spnCOM.setEnabled(true);
				
			}
		});
		
		JButton btnData = new JButton("Send data to Arduino");
		btnData.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e)
			{
				if(lblStatus.getText().equals("Status: Connected."))
				{
					//if(chckbxLed.isSelected())
					//{
					//	writeData("1");
					//} else {
					//	writeData("0");
					//}
					writeData("Y:"+sldY.getValue()+";Z:"+sldZ.getValue()+";");
				} else {
					System.err.println("[SerialConfig] Arduino must be connnected before sending data!");
				}
			}
		});
		btnData.setBounds(10, 311, 264, 23);
		contentPane.add(btnData);
		
		JLabel lblZCoordinate = new JLabel("Z coordinate");
		lblZCoordinate.setBounds(20, 139, 80, 14);
		contentPane.add(lblZCoordinate);
		
		JLabel lblYCoordinate = new JLabel("Y coordinate");
		lblYCoordinate.setBounds(20, 87, 80, 14);
		contentPane.add(lblYCoordinate);
		
		JLabel lblXCoordinate = new JLabel("X coordinate");
		lblXCoordinate.setBounds(20, 39, 80, 14);
		contentPane.add(lblXCoordinate);
		
		JLabel lblView = new JLabel("2D Representation:");
		lblView.setBounds(208, 39, 196, 14);
		contentPane.add(lblView);
		
		lblPos = new Label("Positions: (x, y)");
		lblPos.setBounds(15, 196, 85, 22);
		contentPane.add(lblPos);
		
		lblEEf = new Label("End Effector: (0, 0)");
		lblEEf.setBounds(15, 252, 188, 22);
		contentPane.add(lblEEf);
		
		lblElbow = new Label("Elbow: (0, 0)");
		lblElbow.setBounds(15, 224, 188, 22);
		contentPane.add(lblElbow);
		
		sldSp = new JSlider();
		sldSp.setBounds(282, 201, 122, 26);
		contentPane.add(sldSp);
		
		
		updateAngleLabels(canvas.theta2, canvas.theta3);
		
	}
}
