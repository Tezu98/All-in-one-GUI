package me.tezu.generator.display;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.File;
import java.io.PrintStream;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import com.jgoodies.forms.factories.DefaultComponentFactory;

import me.tezu.generator.Generator;
import me.tezu.generator.Handler;

public class Display {

	private JFrame frmKombajn;
	private JTextField textField;
	private JTextField textField_1;
	private PrintStream con;
	private Handler handler;
	/**
	 * Launch the application.
	 */

	/**
	 * Create the application.
	 */

	/**
	 * Initialize the contents of the frame.
	 * @wbp.parser.entryPoint
	 */
	public void initialize() {
		handler = new Handler();
		frmKombajn = new JFrame();
		frmKombajn.setSize(new Dimension(800, 600));
		frmKombajn.setResizable(false);
		frmKombajn.setTitle("Kombajn");
		frmKombajn.setBounds(100, 100, 450, 300);
		frmKombajn.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmKombajn.setLocationRelativeTo(null);
		frmKombajn.getContentPane().setLayout(null);
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBounds(10, 11, 414, 239);
		frmKombajn.getContentPane().add(tabbedPane);
		
		JPanel reporter = new JPanel();
		tabbedPane.addTab("Raporter", null, reporter, null);
		reporter.setLayout(null);
		
		
		
		final JCheckBox chckbxDefaultPath = new JCheckBox("Default path?");
		chckbxDefaultPath.setBounds(284, 11, 119, 23);
		chckbxDefaultPath.setSelected(true);
		chckbxDefaultPath.addItemListener(new ItemListener() {
			
			public void itemStateChanged(ItemEvent e) {
				textField.setEnabled(e.getStateChange() == ItemEvent.DESELECTED);
				textField_1.setEnabled(e.getStateChange() == ItemEvent.DESELECTED);
				
			}
		});
		reporter.add(chckbxDefaultPath);
		
		textField = new JTextField();
		textField.setBounds(295, 60, 86, 20);
		textField.setEnabled(false);
		reporter.add(textField);
		textField.setColumns(10);
		
		textField_1 = new JTextField();
		textField_1.setBounds(295, 109, 86, 20);
		textField_1.setEnabled(false);
		reporter.add(textField_1);
		textField_1.setColumns(10);
		
		JLabel lblOldFilePath = DefaultComponentFactory.getInstance().createLabel("Old File path");
		lblOldFilePath.setBounds(294, 41, 89, 14);
		reporter.add(lblOldFilePath);
		
		JLabel lblNewFilePath = DefaultComponentFactory.getInstance().createLabel("New File Path");
		lblNewFilePath.setBounds(295, 87, 86, 14);
		reporter.add(lblNewFilePath);

		final JLabel lblPleaseFillThe = DefaultComponentFactory.getInstance().createLabel("");
		lblPleaseFillThe.setBounds(99, 73, 150, 14);
		reporter.add(lblPleaseFillThe);
		
		JButton btnStart = new JButton("START");
		btnStart.setBounds(150, 11, 89, 23);

		btnStart.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				Generator gen = new Generator();
				if(chckbxDefaultPath.isSelected()) {
					gen.start();
					lblPleaseFillThe.setText("");
					System.out.println("DONE");
				}else if(textField.getText().isEmpty() || textField_1.getText().isEmpty()) {
					lblPleaseFillThe.setText("Please fill the path!");
				}else {
					lblPleaseFillThe.setText("");
					gen.setOldFile(new File(textField.getText()));
					gen.setNewFile(new File(textField_1.getText()));
					gen.start();
				}
				
			}
		});
		reporter.add(btnStart);
		
		
		
		JPanel Eskalator = new JPanel();
		tabbedPane.addTab("Eskalator", null, Eskalator, null);
		
		JPanel delay = new JPanel();
		tabbedPane.addTab("Delay Generator", null, delay, null);
		
		JPanel console = new JPanel();
		tabbedPane.addTab("Console", null, console, null);
		
		JTextArea textArea = new JTextArea();
		textArea.setEditable(false);
		textArea.setBounds(0, 0, 409, 211);
		TextAreaOutputStream tos = new TextAreaOutputStream(textArea);
		con = new PrintStream(tos);

		console.setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane(textArea);
		scrollPane.setBounds(0, 0, 409, 211);
		console.add(scrollPane);
		
		JButton btnNewButton = new JButton();
		btnNewButton.setBounds(421, 0, 23, 23);
		btnNewButton.setIcon(new ImageIcon("i.png"));
		
		frmKombajn.getContentPane().add(btnNewButton);
		frmKombajn.setVisible(true);
	}

	public PrintStream getCon() {
		return con;
	}
	
	public void output(String s) {
		System.out.println(s);
	}
}
