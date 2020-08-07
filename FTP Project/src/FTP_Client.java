import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

import sun.net.util.SocketExceptions;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.awt.Font;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.awt.Color;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JTextField;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class FTP_Client {

	private JFrame frame;
	private JTextField textField;
	
	String str;
	String msg = "Exit";
	// Socket and FTP
	static Socket socket;
	static InputStream in;
	static OutputStream out;
	static DataOutputStream dataOutputStream;
	
	static JLabel lblNewLabel_2;
	static JLabel lblNewLabel_3;
	static JButton btnNewButton_2;
	
	
	public static void main(String[] args) throws Exception {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					FTP_Client window = new FTP_Client();
					window.frame.setVisible(true);
					lblNewLabel_2.setText("IP: " + socket.getInetAddress());
					
				} catch (Exception e) {
					System.out.println("Client Exp: " + e);
				}
			}
		});
		
		socket = new Socket("127.0.0.1", 1234);
	}

	/**
	 * Create the application.
	 */
	public FTP_Client() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.getContentPane().setFont(new Font("Comic Sans MS", Font.PLAIN, 14));
		frame.getContentPane().setBackground(Color.WHITE);
		frame.setBounds(100, 100, 720, 420);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Client");
		lblNewLabel.setForeground(Color.BLACK);
		lblNewLabel.setFont(new Font("Comic Sans MS", Font.BOLD, 40));
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setBounds(0, 13, 702, 45);
		frame.getContentPane().add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("Select File:");
		lblNewLabel_1.setForeground(Color.BLACK);
		lblNewLabel_1.setFont(new Font("Comic Sans MS", Font.BOLD, 20));
		lblNewLabel_1.setBounds(28, 150, 127, 31);
		frame.getContentPane().add(lblNewLabel_1);
		
		// 'Browse File' Button function
		JButton btnNewButton = new JButton("Browse File");
		btnNewButton.setForeground(new Color(0, 0, 0));
		btnNewButton.setBackground(new Color(255, 127, 80));
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser fileChooser = new JFileChooser();
				fileChooser.showOpenDialog(btnNewButton);
				
				// getting the path of file into a string
				str = fileChooser.getSelectedFile().getAbsolutePath();
				
				// setting the file path to input field text after selection
				textField.setText(str);
				
			}
		});
		
		
		btnNewButton.setFont(new Font("Comic Sans MS", Font.BOLD, 16));
		btnNewButton.setBounds(555, 148, 135, 37);
		frame.getContentPane().add(btnNewButton);
		
		textField = new JTextField();
		textField.setFont(new Font("Comic Sans MS", Font.PLAIN, 16));
		textField.setBounds(161, 150, 386, 31);
		frame.getContentPane().add(textField);
		textField.setColumns(10);
		
		
		// 'Send File' Button function
		JButton btnNewButton_1 = new JButton("Upload File");
		btnNewButton_1.setForeground(Color.WHITE);
		btnNewButton_1.setBackground(new Color(50, 205, 50));
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// Create a file object and then we will send it
				// We will be using String str as it contains the file path in our system
				File file;
				str = textField.getText();
				file = new File(str);
				
					byte[] b = new byte[16 * 1024];
					
					try {
						in = new FileInputStream(file);	
						lblNewLabel_3.setText("File Sent!");
						lblNewLabel_3.setForeground(new Color(0, 200, 0));
					} catch (FileNotFoundException error) {
						error.printStackTrace();
					}
					
					try {
						out = socket.getOutputStream();
					} catch (IOException err) {
						err.printStackTrace();
					}
					
					try {
						int count;
						while ((count = in.read(b)) > 0) {
							out.write(b, 0, count);
						}
					} catch (IOException e1) {
						e1.printStackTrace();
					}
			}
		});
		
		
		btnNewButton_1.setFont(new Font("Comic Sans MS", Font.BOLD, 16));
		btnNewButton_1.setBounds(161, 226, 162, 37);
		frame.getContentPane().add(btnNewButton_1);
		
		// 'Delete File' button function
		btnNewButton_2 = new JButton("Delete File");
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				str = textField.getText();
				File delFile = new File(str);
				delFile.delete();
				lblNewLabel_3.setText("File Deleted!");
				lblNewLabel_3.setForeground(new Color(200, 0, 0));
			}
		});
		btnNewButton_2.setFont(new Font("Comic Sans MS", Font.BOLD, 16));
		btnNewButton_2.setForeground(Color.WHITE);
		btnNewButton_2.setBackground(Color.RED);
		btnNewButton_2.setBounds(385, 226, 162, 37);
		frame.getContentPane().add(btnNewButton_2);
		
		lblNewLabel_2 = new JLabel("IP:");
		lblNewLabel_2.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_2.setFont(new Font("Comic Sans MS", Font.PLAIN, 18));
		lblNewLabel_2.setBounds(259, 70, 176, 16);
		frame.getContentPane().add(lblNewLabel_2);
		
		lblNewLabel_3 = new JLabel("File Status");
		lblNewLabel_3.setForeground(new Color(47, 79, 79));
		lblNewLabel_3.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_3.setFont(new Font("Comic Sans MS", Font.PLAIN, 20));
		lblNewLabel_3.setBounds(273, 297, 162, 31);
		frame.getContentPane().add(lblNewLabel_3);
	}
}
