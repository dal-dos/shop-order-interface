package GUIDalveerDosanjh;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.DefaultListModel;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class jfrDalveerDosanjh extends JFrame {

	private JPanel contentPane;
	private JList lstMenu;
	private JList lstSelected;
	private JButton btnOrder;
	private JCheckBox chkVIP;
	private JLabel lblNewLabel_2;
	private JLabel lblNewLabel;
	private JLabel lblNewLabel_1;
	DefaultListModel<Object> dlmRight = new DefaultListModel<>();
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					jfrDalveerDosanjh frame = new jfrDalveerDosanjh();
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
	public void createEvents() {
		DefaultListModel<Object> dlmLeft = new DefaultListModel<>();
		ArrayList<String>code=new ArrayList<>();
		String driver = "jdbc:ucanaccess://c:\\temp\\finalfood2020.accdb";
		try
		{
			Connection conn = DriverManager.getConnection(driver);
			Statement sql = conn.createStatement();
			ResultSet rs = sql.executeQuery("Select * from fooditems");
			while(rs.next()) {
				code.add(rs.getString("itemname"));
			}
			rs.close();
			Collections.sort(code);
			for(int i = 0;i < code.size();i++) {
				dlmLeft.addElement(code.get(i)); 
			}
			lstMenu.setModel(dlmLeft);
			}
		catch(Exception ex) {
			System.out.println(ex.getMessage());
		}
		lstMenu.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
					if(lstMenu.getSelectedIndex() != -1) { //to switch from left to right and if max is hit
						dlmRight.addElement(lstMenu.getSelectedValue());
						lstSelected.setModel(dlmRight);
					}
			}
		});
		lstSelected.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if(lstMenu.getSelectedIndex() != -1) { //to switch from left to right and if max is hit
					dlmRight.removeElementAt(lstSelected.getSelectedIndex());
				}
				lstSelected.setModel(dlmRight);
			}
		});
		btnOrder.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ArrayList<String>temp=new ArrayList<>();
				ArrayList<String>allData=new ArrayList<>();
				ArrayList<String>money=new ArrayList<>();
				ArrayList<String>test=new ArrayList<>();
				double total = 0;
				double netTotal = 0;
				NumberFormat numC = NumberFormat.getCurrencyInstance();
				String nameTemp;
				String anotherTemp = "";
				int lastTime = 0;
				int outTimes = 0;
				int times = 0;
				double t = 0;
				double n = 0;
				double disc = 0;
				String driver = "jdbc:ucanaccess://c:\\temp\\finalfood2020.accdb";
				Menu2022 myMenu;
				String fmt = "%-30s %20s %20s %20s \n";
				System.out.printf(fmt,"Food items ordered","Unit price","Subtotal","Net amt");
				try
				{
					for(int i = 0;i< dlmRight.getSize();i++) {
						temp.add((String)dlmRight.getElementAt(i));
					}
					
					Collections.sort(temp);
					for(int i = 0;i< dlmRight.getSize();i++) {
					
					Connection conn = DriverManager.getConnection(driver);
					Statement sql = conn.createStatement();
					ResultSet rs = sql.executeQuery("Select * from fooditems where itemname = '"+ temp.get(i) +"'");
					while(rs.next()) {
						myMenu = Menu2022.valueOf(temp.get(i));
						test.add(temp.get(i));
						allData.add(myMenu.getName()+","+"("+myMenu.getCalories()+" calories)"+","+rs.getString(1));
						money.add(rs.getString(1));
					}
					}
					String tempData[];
					for(int i = 0; i < temp.size();i++) {
						tempData = allData.get(i).split(",");
						times = 0;
						nameTemp = temp.get(i);
						for(int j = 0;j< temp.size();j++) {
							if(temp.get(j).equals(nameTemp)) {
								times = times+1;
							}
						}
						myMenu = Menu2022.valueOf(test.get(i));
						if(times > 1 && outTimes != 1) {
							if(chkVIP.isSelected()) {
								disc = (myMenu.getDiscount()/100)*Double.parseDouble(money.get(i))*times;
								total = total + Double.parseDouble(money.get(i))*times-disc;
								netTotal = netTotal + Double.parseDouble(money.get(i))*times;
								System.out.printf(fmt,myMenu.getName()+" ("+myMenu.getCalories() +") calories","x"+times+" "+numC.format(Double.parseDouble(money.get(i)))+"==>",numC.format(Double.parseDouble(money.get(i))*times),numC.format(Double.parseDouble(money.get(i))*times-disc));
							}
							else {
								total = total + Double.parseDouble(money.get(i))*times;
								netTotal = netTotal + Double.parseDouble(money.get(i))*times;
								System.out.printf(fmt,myMenu.getName()+" ("+myMenu.getCalories() +") calories","x"+times+" "+numC.format(Double.parseDouble(money.get(i)))+"==>",numC.format(Double.parseDouble(money.get(i))*times),numC.format(Double.parseDouble(money.get(i))*times));
							}
							lastTime = times;
							anotherTemp = myMenu.getName();
							outTimes = 1;
						}else if(times == 1 ) {
							if(chkVIP.isSelected()) {
								disc = (myMenu.getDiscount()/100)*Double.parseDouble(money.get(i))*times;
								total = total + Double.parseDouble(money.get(i))*times-disc;
								netTotal = netTotal + Double.parseDouble(money.get(i))*times;
								System.out.printf(fmt,myMenu.getName()+" ("+myMenu.getCalories() +") calories","x"+times+" "+numC.format(Double.parseDouble(money.get(i)))+"==>",numC.format(Double.parseDouble(money.get(i))*times),numC.format(Double.parseDouble(money.get(i))*times-disc));
							}
							else {
								total = total + Double.parseDouble(money.get(i))*times;
								netTotal = netTotal + Double.parseDouble(money.get(i))*times;
								System.out.printf(fmt,myMenu.getName()+" ("+myMenu.getCalories() +") calories","x"+times+" "+numC.format(Double.parseDouble(money.get(i)))+"==>",numC.format(Double.parseDouble(money.get(i))*times),numC.format(Double.parseDouble(money.get(i))*times));
							}
						}
						if(times == 1 || myMenu.getName() != anotherTemp && lastTime == 2) {
							outTimes = 0;
						}

					}

					System.out.printf(fmt,"Total amount:"," ",numC.format(netTotal),numC.format(total));
					System.out.println("Please pay your server: Dalveer Dosanjh");
				}
			catch(Exception ex) {
				System.out.println(ex.getMessage());
			}
			}
		});
	}
	
	public jfrDalveerDosanjh() {
		setUp();
		createEvents();
	}
	public void setUp() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 377, 456);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		lblNewLabel = new JLabel("Menu items");
		
		JScrollPane scrollPane = new JScrollPane();
		
		JScrollPane scrollPane_1 = new JScrollPane();
		
		lblNewLabel_1 = new JLabel("Selected Items");
		
		btnOrder = new JButton("Order");

		
		chkVIP = new JCheckBox("VIP Discount");
		
		lblNewLabel_2 = new JLabel("Designed by Dalveer Dosanjh");
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGap(50)
					.addComponent(lblNewLabel)
					.addPreferredGap(ComponentPlacement.RELATED, 87, Short.MAX_VALUE)
					.addComponent(lblNewLabel_1)
					.addGap(45))
				.addGroup(Alignment.TRAILING, gl_contentPane.createSequentialGroup()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addContainerGap()
							.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 132, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGap(29)
							.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
								.addComponent(lblNewLabel_2)
								.addComponent(btnOrder))))
					.addPreferredGap(ComponentPlacement.RELATED, 29, Short.MAX_VALUE)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING, false)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addComponent(chkVIP)
							.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
						.addGroup(Alignment.TRAILING, gl_contentPane.createSequentialGroup()
							.addComponent(scrollPane_1, GroupLayout.PREFERRED_SIZE, 126, GroupLayout.PREFERRED_SIZE)
							.addContainerGap())))
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblNewLabel)
						.addComponent(lblNewLabel_1))
					.addGap(18)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addComponent(scrollPane_1, GroupLayout.PREFERRED_SIZE, 250, GroupLayout.PREFERRED_SIZE)
						.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 250, GroupLayout.PREFERRED_SIZE))
					.addGap(18)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addComponent(btnOrder)
						.addComponent(chkVIP))
					.addPreferredGap(ComponentPlacement.RELATED, 36, Short.MAX_VALUE)
					.addComponent(lblNewLabel_2)
					.addGap(23))
		);
		
		lstSelected = new JList();

		scrollPane_1.setViewportView(lstSelected);
		
		lstMenu = new JList();

		scrollPane.setViewportView(lstMenu);
		contentPane.setLayout(gl_contentPane);
	}
}
