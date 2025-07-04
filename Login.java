package ngomanagement3;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Image;
import java.sql.*;
import java.sql.ResultSet;

import javax.swing.*;



public class Login extends JFrame implements ActionListener {
    JTextField textField;
    JPasswordField jPasswordField;
    JButton b1, b2, signupBtn;
    JComboBox<String> userTypeBox;

    Login() {
        

        
        JLabel titleLabel = new JLabel("WELCOME TO SEVA SANKALP!!", JLabel.CENTER);
        titleLabel.setBounds(0, 10, 400, 40);
        titleLabel.setFont(new Font("Scripter", Font.BOLD, 22));
        titleLabel.setForeground(new Color(102, 255, 178));
        add(titleLabel);

        JLabel userLabel = new JLabel("Login as:");
        userLabel.setBounds(50, 60, 100, 30);
        userLabel.setFont(new Font("Tahoma", Font.BOLD, 16));
        userLabel.setForeground(new Color(102, 255, 178));
        add(userLabel);
        
        

        userTypeBox = new JComboBox<>(new String[]{"Admin", "Volunteer"});
        userTypeBox.setBounds(160, 60, 180, 30);
        userTypeBox.setBackground(new Color(25, 25, 25));
        userTypeBox.setForeground(new Color(102, 255, 178));
        add(userTypeBox);

        JLabel namelabel = new JLabel("Username:");
        namelabel.setBounds(50, 105, 100, 30);
        namelabel.setFont(new Font("Tahoma", Font.BOLD, 16));
        namelabel.setForeground(new Color(102, 255, 178));
        add(namelabel);

        textField = new JTextField();
        textField.setBounds(160, 105, 180, 30);
        textField.setFont(new Font("Tahoma", Font.PLAIN, 15));
        textField.setBackground(new Color(25, 25, 25));
        textField.setForeground(new Color(102, 255, 178));
        textField.setCaretColor(new Color(102, 255, 178));
        textField.setBorder(BorderFactory.createLineBorder(new Color(102, 255, 178), 1));
        add(textField);

        
        JLabel password = new JLabel("Password:");
        password.setBounds(50, 150, 100, 30);
        password.setFont(new Font("Tahoma", Font.BOLD, 16));
        password.setForeground(new Color(102, 255, 178));
        add(password);

        jPasswordField = new JPasswordField();
        jPasswordField.setBounds(160, 150, 180, 30);
        jPasswordField.setFont(new Font("Tahoma", Font.PLAIN, 15));
        jPasswordField.setBackground(new Color(25, 25, 25));
        jPasswordField.setForeground(new Color(102, 255, 178));
        jPasswordField.setCaretColor(new Color(102, 255, 178));
        jPasswordField.setBorder(BorderFactory.createLineBorder(new Color(102, 255, 178), 1));
        add(jPasswordField);

        b1 = new JButton("Login");
        b1.setBounds(70, 210, 120, 35);
        b1.setFont(new Font("Segoe UI", Font.BOLD, 16));
        b1.setBackground(new Color(102, 255, 178));
        b1.setForeground(Color.BLACK);
        b1.setFocusPainted(false);
        b1.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
        b1.addActionListener(this);
        add(b1);

        b2 = new JButton("Cancel");
        b2.setBounds(220, 210, 120, 35);
        b2.setFont(new Font("Segoe UI", Font.BOLD, 16));
        b2.setBackground(Color.BLACK);
        b2.setForeground(new Color(102, 255, 178));
        b2.setFocusPainted(false);
        b2.setBorder(BorderFactory.createLineBorder(new Color(102, 255, 178), 1));
        b2.addActionListener(this);
        add(b2);

        // If you want to allow volunteers to sign up, you can implement a Signup class for volunteers
        signupBtn = new JButton("Sign Up");
        signupBtn.setBounds(145, 260, 120, 30);
        signupBtn.setFont(new Font("Segoe UI", Font.BOLD, 14));
        signupBtn.setBackground(new Color(25, 25, 25));
        signupBtn.setForeground(new Color(102, 255, 178));
        signupBtn.setBorder(BorderFactory.createLineBorder(new Color(102, 255, 178), 1));
        signupBtn.addActionListener(e -> {
            setVisible(false);
            new Signup(); // You should implement this class for volunteer registration
        });
        add(signupBtn);

        
        getContentPane().setBackground(Color.BLACK);
        setTitle("NGO Volunteer & Event Management - Login");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(420, 350);
        setLocation(400,270);
        setLayout(null);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == b1) {
            String userType = (String) userTypeBox.getSelectedItem();
            String user = textField.getText();
            String pass = jPasswordField.getText();

            try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/ngo_management", "root", "root")) {
                String q;
                if ("Admin".equals(userType)) {
                    q = "SELECT * FROM admin WHERE username = ? AND password = ?";
                } else {
                    q = "SELECT * FROM volunteer WHERE volunteer_name = ? AND password = ?";
                }
                PreparedStatement pstmt = conn.prepareStatement(q);
                pstmt.setString(1, user);
                pstmt.setString(2, pass);
                ResultSet rs = pstmt.executeQuery();

                if (rs.next()) {
                    setVisible(false);
                    if ("Admin".equals(userType)) {
                        new NGOmain(true).setVisible(true); // admin dashboard
                    } else {
                        new NGOmain(false).setVisible(true); // volunteer dashboard
                    }
                } else {
                    JOptionPane.showMessageDialog(this, "Invalid Username or Password", "Login Failed", JOptionPane.ERROR_MESSAGE);
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        } else {
            System.exit(0);
        }
    }

    public static void main(String[] args) {
        new Login();
    }
}

