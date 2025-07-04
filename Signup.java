package ngomanagement3;

import java.awt.Color;
import java.awt.Font;
import java.sql.*;
import java.sql.ResultSet;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class Signup extends JFrame implements ActionListener {
	JTextField nameField, emailField, phoneField;
	JPasswordField passwordField;
	JButton signupBtn, backBtn;

	public Signup() {
		setTitle("Volunteer Sign Up");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(420, 400);
		setLocation(400, 270);
		setResizable(false);

		JPanel panel = new JPanel();
		panel.setBackground(Color.BLACK);
		panel.setLayout(null);

		JLabel title = new JLabel("VOLUNTEER SIGN UP", JLabel.CENTER);
		title.setFont(new Font("Segoe UI", Font.BOLD, 22));
		title.setForeground(new Color(102, 255, 178));
		title.setBounds(0, 10, 420, 40);
		panel.add(title);

		JLabel nameLabel = new JLabel("Name:");
		nameLabel.setBounds(50, 60, 100, 30);
		nameLabel.setFont(new Font("Tahoma", Font.BOLD, 16));
		nameLabel.setForeground(new Color(102, 255, 178));
		panel.add(nameLabel);

		nameField = new JTextField();
		nameField.setBounds(160, 60, 180, 30);
		nameField.setBackground(new Color(25, 25, 25));
		nameField.setForeground(new Color(102, 255, 178));
		nameField.setCaretColor(new Color(102, 255, 178));
		nameField.setBorder(BorderFactory.createLineBorder(new Color(102, 255, 178), 1));
		panel.add(nameField);

		JLabel emailLabel = new JLabel("Email:");
		emailLabel.setBounds(50, 105, 100, 30);
		emailLabel.setFont(new Font("Tahoma", Font.BOLD, 16));
		emailLabel.setForeground(new Color(102, 255, 178));
		panel.add(emailLabel);

		emailField = new JTextField();
		emailField.setBounds(160, 105, 180, 30);
		emailField.setBackground(new Color(25, 25, 25));
		emailField.setForeground(new Color(102, 255, 178));
		emailField.setBorder(BorderFactory.createLineBorder(new Color(102, 255, 178), 1));
		panel.add(emailField);

		JLabel phoneLabel = new JLabel("Phone:");
		phoneLabel.setBounds(50, 150, 100, 30);
		phoneLabel.setFont(new Font("Tahoma", Font.BOLD, 16));
		phoneLabel.setForeground(new Color(102, 255, 178));
		panel.add(phoneLabel);

		phoneField = new JTextField();
		phoneField.setBounds(160, 150, 180, 30);
		phoneField.setBackground(new Color(25, 25, 25));
		phoneField.setForeground(new Color(102, 255, 178));
		phoneField.setBorder(BorderFactory.createLineBorder(new Color(102, 255, 178), 1));
		panel.add(phoneField);

		JLabel passwordLabel = new JLabel("Password:");
		passwordLabel.setBounds(50, 195, 100, 30);
		passwordLabel.setFont(new Font("Tahoma", Font.BOLD, 16));
		passwordLabel.setForeground(new Color(102, 255, 178));
		panel.add(passwordLabel);

		passwordField = new JPasswordField();
		passwordField.setBounds(160, 195, 180, 30);
		passwordField.setBackground(new Color(25, 25, 25));
		passwordField.setForeground(new Color(102, 255, 178));
		passwordField.setBorder(BorderFactory.createLineBorder(new Color(102, 255, 178), 1));
		panel.add(passwordField);

		signupBtn = new JButton("Sign Up");
		signupBtn.setBounds(70, 260, 120, 35);
		signupBtn.setFont(new Font("Segoe UI", Font.BOLD, 16));
		signupBtn.setBackground(new Color(102, 255, 178));
		signupBtn.setForeground(Color.BLACK);
		signupBtn.addActionListener(this);
		panel.add(signupBtn);

		backBtn = new JButton("Back");
		backBtn.setBounds(220, 260, 120, 35);
		backBtn.setFont(new Font("Segoe UI", Font.BOLD, 16));
		backBtn.setBackground(Color.BLACK);
		backBtn.setForeground(new Color(102, 255, 178));
		backBtn.addActionListener(e -> {
			setVisible(false);
			new Login();
		});
		panel.add(backBtn);

		add(panel);
		setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String name = nameField.getText();
		String email = emailField.getText();
		String phone = phoneField.getText();
		String password = new String(passwordField.getPassword());

		if (name.isEmpty() || email.isEmpty() || phone.isEmpty() || password.isEmpty()) {
			JOptionPane.showMessageDialog(this, "All fields are required!", "Error", JOptionPane.ERROR_MESSAGE);
			return;
		}
		try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/ngo_management", "root",
				"root")) {
			String q = "INSERT INTO volunteer (volunteer_name, email, phone_number, password) VALUES (?, ?, ?, ?)";
			PreparedStatement pstmt = conn.prepareStatement(q);
			pstmt.setString(1, name);
			pstmt.setString(2, email);
			pstmt.setString(3, phone);
			pstmt.setString(4, password);
			pstmt.executeUpdate();
			JOptionPane.showMessageDialog(this, "Sign Up successful! You can now login.");
			setVisible(false);
			new Login();
		} catch (Exception ex) {
			ex.printStackTrace();
			JOptionPane.showMessageDialog(this, "Sign Up failed!", "Error", JOptionPane.ERROR_MESSAGE);
		}
	}

	public static void main(String[] args) {
		new Signup();
	}
}
