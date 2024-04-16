import javax.swing.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.event.*;
import java.awt.event.FocusEvent;
import java.awt.Font;
import java.awt.event.MouseEvent;
import javax.swing.JOptionPane;
public class Login extends JFrame implements ActionListener
{
	JPanel p1,p2;
	JTextField UserName;
	JPasswordField pass;
	JCheckBox show;
	JComboBox<String> user;
	JButton login;
	JLabel l1,l2,l3,l4,l5,error,l6;
	Connection con;
	
	Login()
	{
		MyConnection my = new MyConnection();
	    con = my.Connect();
		setTitle("Login");
		p1 = new JPanel();
		p1.setLayout(null);
		p1.setBackground(Color.gray);
		p1.setBounds(0,0,550,50);
		p2 = new JPanel();
		p2.setLayout(null);
		p2.setBackground(Color.white);
		p2.setBounds(0,52,550,300);
		l1 = new JLabel("Login");
		l1.setForeground(Color.white);
		l1.setFont(new Font("arial", Font.BOLD, 18));
		l1.setBounds(225,10,80,30);
		l2 = new JLabel("User Name:");
		l2.setFont(new Font("arial",Font.BOLD,14));
		l2.setForeground(Color.black);
		l2.setBounds(55,65,90,30);
		l3 = new JLabel("Password:");
		l3.setForeground(Color.black);
		l3.setFont(new Font("arial",Font.BOLD,14));
		l3.setBounds(56,105,90,30);
		l4 = new JLabel("User Type:");
		l4.setFont(new Font("arial",Font.BOLD,14));
		l4.setBounds(62,135,90,30);
		error = new JLabel("Username Or Password Incorrect");
		error.setFont(new Font("arial",Font.PLAIN,14));
		error.setBounds(235,174,220,20);
		l6 = new JLabel("New User Sign Up");
		l6.setFont(new Font("arial",Font.BOLD,18));
		l6.setBounds(150,210,200,30);
		l6.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		UserName = new JTextField("Username");
		UserName.setBounds(150,65,200,30);
		UserName.setFont(new Font("arial",Font.PLAIN,14));
		pass = new JPasswordField("Password");
		pass.setBounds(150,105,200,30);
		pass.setFont(new Font("arial",Font.PLAIN,14));
		String[] utype = {"---Select User---","Admin","Trainer","User"};
		user = new JComboBox<>(utype);
		user.setBounds(150,140,200,30);
		login = new JButton("LOGIN");
		login.setFont(new Font("arial",Font.BOLD,14));
		login.setBounds(150,174,80,20);
		login.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		login.addActionListener(this);
		show = new JCheckBox("Show Password");
		show.setFont(new Font("arial",Font.PLAIN,14));
		show.setBounds(350,105,150,30);
		show.addActionListener(this);
		show.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		add(p1);
		add(p2);
		p1.add(l1);
		p2.add(l2);
		p2.add(l3);
		p2.add(l4);
		p2.add(error);
		p2.add(l6);
		p2.add(UserName);
		p2.add(pass);
		p2.add(user);
		p2.add(login);
		p2.add(show);
		setSize(550,350);
		setLayout(null);
		setVisible(true);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		l6.addMouseListener(new MouseAdapter()  
		{
			public void mouseClicked(MouseEvent e)
			{
				
				NewUser N = new NewUser();
				N.setVisible(true);
				N.setLocationRelativeTo(null);
			}  
		});
		//@Deprecated
		UserName.addFocusListener(new FocusAdapter()
		{
			public void focusGained(FocusEvent f)
			{
				error.setVisible(false);
				if(UserName.getText().equals("Username"))
				{
					UserName.setText("");
				}
			}
			public void focusLost(FocusEvent f)
			{
				error.setVisible(false);
				if(UserName.getText().equals(""))
				{
					UserName.setText("Username");
				}
			}
		});
		//@Deprecated annotation 
		pass.addFocusListener(new FocusAdapter()
		{
			public void focusGained(FocusEvent fe)
			{
				error.setVisible(false);
				if(pass.getText().equals("Password"))
				{
					pass.setText("");
				}
			}
			public void focusLost(FocusEvent fe)
			{
				error.setVisible(false);
				if(pass.getText().equals(""))
				{
					pass.setText("Password");
				}
			}
		});
		
	}
	public void actionPerformed(ActionEvent e)
	{
		PreparedStatement st;
        	ResultSet rs;
        	String uname=UserName.getText();
        	String pass1=String.valueOf(pass.getPassword());
        	String User=user.getSelectedItem().toString();
		String query = "Select * from users where Username = ? and Password = ?";
		if(show.isSelected())
		{
			pass.setEchoChar((char)0);
		}
		else 
		{
			pass.setEchoChar('*');
		}
		
		if(e.getSource()==login)	
		try
		{
			st = con.prepareStatement(query);
			st.setString(1,uname);
			st.setString(2,pass1);
			rs = st.executeQuery();
			if(rs.next())
			{
				String s1 = rs.getString("user");
				if(User.equalsIgnoreCase("Admin") && s1.equalsIgnoreCase("admin"))
				{
					Admin A = new Admin();
					A.setVisible(true);
					A.setExtendedState(JFrame.MAXIMIZED_BOTH);
					this.setVisible(false);
				}
				if(User.equalsIgnoreCase("Trainer") && s1.equalsIgnoreCase("Trainer"))
				{
					Trainer T = new Trainer();
					T.setVisible(true);
					T.setExtendedState(JFrame.MAXIMIZED_BOTH);
					this.setVisible(false);
				}
				if(User.equalsIgnoreCase("User") && s1.equalsIgnoreCase("user"))
				{
					User u = new User();
					u.setVisible(true);
					u.setExtendedState(JFrame.MAXIMIZED_BOTH);
					this.setVisible(false);
				}
				
			}
			else
			{
				error.setVisible(true);
				UserName.setText("");
				pass.setText("");
			}
			if(User.equals("---Select User---"))
			{
				JOptionPane.showMessageDialog(null,"Select the user Type");
			}
			
		} catch(SQLException ex1)
		{
			Logger.getLogger(Login.class.getName()).log(Level.SEVERE,null,ex1);
		}
	}
	
	public static void main(String args[])
	{
		new Login();
	}
}	