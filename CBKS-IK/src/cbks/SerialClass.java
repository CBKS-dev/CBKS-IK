package cbks;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import gnu.io.CommPortIdentifier;
import gnu.io.SerialPort;
import gnu.io.SerialPortEvent;
import gnu.io.SerialPortEventListener;
import java.util.Enumeration;

public class SerialClass implements SerialPortEventListener {
	
	public SerialPort serialPort;
	
	/* Preferred port */
	private static final String[] PORT_NAMES = {
			"/dev/tty.usbserial-A9007UX1", // Mac OS X
			"/dev/ttyUSB0",				   // Linux
			"COM5", 					   // Windows
	};
	
	
	public static BufferedReader input;
	public static OutputStream   output;
	
	/* Time waiting for the port to be open, in millisconds*/
	public static final int TIMEOUT = 100;
	
	/* Default baudrate for the COM port */
	public static final int BAUD_RATE = 115200;
	
	public void init()
	{
		CommPortIdentifier portId = null;
		
		@SuppressWarnings("unchecked")
		Enumeration<CommPortIdentifier> ports = CommPortIdentifier.getPortIdentifiers();
		
		// Find instances of serial ports that matches PORT_NAMES
		while(ports.hasMoreElements())
		{
			CommPortIdentifier currentPortID = (CommPortIdentifier) ports.nextElement();
			for(String portName : PORT_NAMES)
			{
				if(currentPortID.getName().equals(portName))
				{
					portId = currentPortID;
					break;
				}
			}
		}
		
		if(portId == null)
		{
			System.out.println("[SerialConfig] Could not find "+PORT_NAMES[2]+" port");
			return;
		}
		
		try 
		{
			// Open the Serial Port, using the Class Name for the appName.
			serialPort = (SerialPort) portId.open(this.getClass().getName(), TIMEOUT);
			
			// Set the parameters for the port
			serialPort.setSerialPortParams(BAUD_RATE, SerialPort.DATABITS_8,
										   SerialPort.STOPBITS_1, SerialPort.PARITY_NONE);
			
			// Open the streams
			input  = new BufferedReader(new InputStreamReader(serialPort.getInputStream()));
			output = serialPort.getOutputStream();
			
			char ch = 1;
			output.write(ch);
			
			// Event listeners
			serialPort.addEventListener(this);
			serialPort.notifyOnDataAvailable(true);
		} catch (Exception ex) {
			System.err.println(ex.toString());
		}
		
	}
	
	public int init(String thePort)
	{
		CommPortIdentifier portId = null;
		
		@SuppressWarnings("unchecked")
		Enumeration<CommPortIdentifier> ports = CommPortIdentifier.getPortIdentifiers();
		
		// Find instances of serial ports that matches PORT_NAMES
		while(ports.hasMoreElements())
		{
			CommPortIdentifier currentPortID = (CommPortIdentifier) ports.nextElement();
				if(currentPortID.getName().equals(thePort))
				{
					portId = currentPortID;
					System.out.println("[SerialConfig] Found port "+thePort);
					break;
				}
		}
		
		if(portId == null)
		{
			System.out.println("[SerialConfig] Could not find port "+thePort);
			return 1;
		}
		
		try 
		{
			// Open the Serial Port, using the Class Name for the appName.
			System.out.println("[SerialConfig] Opening port "+thePort+" ...");
			serialPort = (SerialPort) portId.open(this.getClass().getName(), TIMEOUT);
			System.out.println("[SerialConfig] Port "+thePort+" opened successfully.");
			
			// Set the parameters for the port
			System.out.println("[SerialConfig] Configuring port "+thePort+" parameters ...");
			serialPort.setSerialPortParams(BAUD_RATE, SerialPort.DATABITS_8,
										   SerialPort.STOPBITS_1, SerialPort.PARITY_NONE);
			
			System.out.println("[SerialConfig] Port "+thePort+" parameters configured successfully.");
			
			// Open the streams
			input  = new BufferedReader(new InputStreamReader(serialPort.getInputStream()));
			output = serialPort.getOutputStream();
			
			char ch = 1;
			output.write(ch);
			
			// Event listeners
			serialPort.addEventListener(this);
			serialPort.notifyOnDataAvailable(true);
		} catch (Exception ex) {
			System.err.println(ex.toString());
		}
		return 0;
	}
	
	public synchronized void close()
	{
		if(serialPort != null)
		{
			serialPort.removeEventListener();
			serialPort.close();
		}
	}

	@Override
	// Inhereted abstract method
	public synchronized void serialEvent(SerialPortEvent theEvent) {

		
		
		if(theEvent.getEventType() == SerialPortEvent.DATA_AVAILABLE)
		{
			String inputLine;
			try {
				inputLine = input.readLine().trim();
				System.out.println(inputLine);
			} catch (IOException e) {
				//System.err.println(e.toString());
				//e.printStackTrace();
			}
			
		}
		
	}
	
	public static synchronized void writeData(String theData)
	{
		System.out.println("[SerialConfig] Sent: "+ theData);
		try {
			output.write(theData.getBytes());
		} catch (IOException e) {
			System.out.println("[SerialConfig] Could not write to port");
			e.printStackTrace();
		}
	}
}
