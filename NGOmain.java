package ngomanagement3;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

import java.sql.*;

public class NGOmain extends JFrame {
	static final String DB_URL = "jdbc:mysql://localhost:3306/ngo_management";
	static final String USER = "root";
	static final String PASS = "root";
	Connection conn;
	boolean isAdmin;

	// Declare UI elements
	JComboBox<String> volunteerBox, eventBox, assignmentBox, feedbackBox;
	JButton volunteerGo, eventGo, assignmentGo, feedbackGo, reportBtn;

	public NGOmain() {

	}

	public NGOmain(boolean isAdmin) {
		this.isAdmin = isAdmin;

		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			conn = DriverManager.getConnection(DB_URL, USER, PASS);
		} catch (Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(this, "Database connection failed!");
			System.exit(1);
		}

		setTitle("NGO Volunteer & Event Management System");
		setSize(1000, 700);
		setLocation(200, 100);
		setLayout(null);
		getContentPane().setBackground(Color.BLACK);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// Title Label (Main Title)
		JLabel titleLabel = new JLabel("WELCOME TO SEVA SANKALP!!", JLabel.CENTER);
		titleLabel.setBounds(95, 65, 800, 50);
		titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 38));
		titleLabel.setForeground(new Color(0, 255, 255)); // Cyan
		add(titleLabel);

		// Description Header
		JLabel header = new JLabel("NGO Volunteer & Event Management System", JLabel.CENTER);
		header.setFont(new Font("Segoe UI", Font.BOLD, 24));
		header.setForeground(Color.CYAN);
		header.setBounds(95, 115, 800, 40);
		add(header);

		int yPos = 220;

		if (isAdmin) {
			// Volunteer Section
			JLabel volunteerLabel = new JLabel("Volunteer:");
			volunteerLabel.setBounds(300, yPos, 120, 30);
			volunteerLabel.setFont(new Font("Tahoma", Font.BOLD, 16));
			volunteerLabel.setForeground(new Color(102, 255, 178));
			add(volunteerLabel);

			volunteerBox = new JComboBox<>(
					new String[] { "Register Volunteer", "Show Volunteers", "Update Volunteer", "Delete Volunteer" });
			volunteerBox.setBounds(420, yPos, 220, 30);
			volunteerBox.setBackground(new Color(25, 25, 25));
			volunteerBox.setForeground(new Color(102, 255, 178));
			add(volunteerBox);

			volunteerGo = new JButton("Go");
			volunteerGo.setBounds(660, yPos, 80, 30);
			volunteerGo.setFont(new Font("Segoe UI", Font.BOLD, 14));
			volunteerGo.setBackground(new Color(102, 255, 178));
			volunteerGo.setForeground(Color.BLACK);
			add(volunteerGo);

			volunteerGo.addActionListener(e -> {
				int idx = volunteerBox.getSelectedIndex();
				switch (idx) {
				case 0:
					new InsertVolunteerListener().actionPerformed(null);
					break;
				case 1:
					displayTableData("SELECT * FROM volunteer", "Volunteers");
					break;
				case 2:
					new UpdateVolunteerListener().actionPerformed(null);
					break;
				case 3:
					new DeleteVolunteerListener().actionPerformed(null);
					break;
				}
			});

			yPos += 50;

			// Event Section
			JLabel eventLabel = new JLabel("Event:");
			eventLabel.setBounds(300, yPos, 120, 30);
			eventLabel.setFont(new Font("Tahoma", Font.BOLD, 16));
			eventLabel.setForeground(new Color(102, 255, 178));
			add(eventLabel);

			eventBox = new JComboBox<>(new String[] { "Create Event", "Show Events", "Update Event", "Delete Event" });
			eventBox.setBounds(420, yPos, 220, 30);
			eventBox.setBackground(new Color(25, 25, 25));
			eventBox.setForeground(new Color(102, 255, 178));
			add(eventBox);

			eventGo = new JButton("Go");
			eventGo.setBounds(660, yPos, 80, 30);
			eventGo.setFont(new Font("Segoe UI", Font.BOLD, 14));
			eventGo.setBackground(new Color(102, 255, 178));
			eventGo.setForeground(Color.BLACK);
			add(eventGo);

			eventGo.addActionListener(e -> {
				int idx = eventBox.getSelectedIndex();
				switch (idx) {
				case 0:
					new InsertEventListener().actionPerformed(null);
					break;
				case 1:
					displayTableData("SELECT * FROM event", "Events");
					break;
				case 2:
					new UpdateEventListener().actionPerformed(null);
					break;
				case 3:
					new DeleteEventListener().actionPerformed(null);
					break;
				}
			});

			yPos += 50;

			// Assignment Section
			JLabel assignmentLabel = new JLabel("Role Assignment:");
			assignmentLabel.setBounds(300, yPos, 120, 30);
			assignmentLabel.setFont(new Font("Tahoma", Font.BOLD, 16));
			assignmentLabel.setForeground(new Color(102, 255, 178));
			add(assignmentLabel);

			assignmentBox = new JComboBox<>(
					new String[] { "Assign Volunteer", "Show Assignments", "Update Assignment", "Delete Assignment" });
			assignmentBox.setBounds(420, yPos, 220, 30);
			assignmentBox.setBackground(new Color(25, 25, 25));
			assignmentBox.setForeground(new Color(102, 255, 178));
			add(assignmentBox);

			assignmentGo = new JButton("Go");
			assignmentGo.setBounds(660, yPos, 80, 30);
			assignmentGo.setFont(new Font("Segoe UI", Font.BOLD, 14));
			assignmentGo.setBackground(new Color(102, 255, 178));
			assignmentGo.setForeground(Color.BLACK);
			add(assignmentGo);

			assignmentGo.addActionListener(e -> {
				int idx = assignmentBox.getSelectedIndex();
				switch (idx) {
				case 0:
					new AssignVolunteerListener().actionPerformed(null);
					break;
				case 1:
					displayTableData("SELECT * FROM assignment", "Assignments");
					break;
				case 2:
					new UpdateAssignmentListener().actionPerformed(null);
					break;
				case 3:
					new DeleteAssignmentListener().actionPerformed(null);
					break;
				}
			});

			yPos += 50;

			// Feedback Section
			JLabel feedbackLabel = new JLabel("Feedback:");
			feedbackLabel.setBounds(300, yPos, 120, 30);
			feedbackLabel.setFont(new Font("Tahoma", Font.BOLD, 16));
			feedbackLabel.setForeground(new Color(102, 255, 178));
			add(feedbackLabel);

			feedbackBox = new JComboBox<>(new String[] { "Volunteer Feedback", "View Feedback" });
			feedbackBox.setBounds(420, yPos, 220, 30);
			feedbackBox.setBackground(new Color(25, 25, 25));
			feedbackBox.setForeground(new Color(102, 255, 178));
			add(feedbackBox);

			feedbackGo = new JButton("Go");
			feedbackGo.setBounds(660, yPos, 80, 30);
			feedbackGo.setFont(new Font("Segoe UI", Font.BOLD, 14));
			feedbackGo.setBackground(new Color(102, 255, 178));
			feedbackGo.setForeground(Color.BLACK);
			add(feedbackGo);

			feedbackGo.addActionListener(e -> {
				int idx = feedbackBox.getSelectedIndex();
				switch (idx) {
				case 0:
					new InsertFeedbackListener().actionPerformed(null);
					break;
				case 1:
					displayTableData("SELECT * FROM feedback", "Feedback");
					break;
				}
			});

			yPos += 50;

			// Report Button
			reportBtn = new JButton("Generate Event Report");
			reportBtn.setBounds(420, yPos, 220, 35);
			reportBtn.setFont(new Font("Segoe UI", Font.BOLD, 15));
			reportBtn.setBackground(new Color(102, 255, 178));
			reportBtn.setForeground(Color.BLACK);
			add(reportBtn);

			reportBtn.addActionListener(new GenerateEventReportListener());

		} else {
			// Regular User (Volunteer) View

			// Event Section
			JLabel eventLabel = new JLabel("Event:");
			eventLabel.setBounds(300, yPos, 120, 30);
			eventLabel.setFont(new Font("Tahoma", Font.BOLD, 16));
			eventLabel.setForeground(new Color(102, 255, 178));
			add(eventLabel);

			eventBox = new JComboBox<>(new String[] { "Show Events" });
			eventBox.setBounds(420, yPos, 220, 30);
			eventBox.setBackground(new Color(25, 25, 25));
			eventBox.setForeground(new Color(102, 255, 178));
			add(eventBox);

			eventGo = new JButton("Go");
			eventGo.setBounds(660, yPos, 80, 30);
			eventGo.setFont(new Font("Segoe UI", Font.BOLD, 14));
			eventGo.setBackground(new Color(102, 255, 178));
			eventGo.setForeground(Color.BLACK);
			add(eventGo);

			eventGo.addActionListener(e -> displayTableData("SELECT * FROM event", "Events"));

			yPos += 50;

			// Feedback Section
			JLabel feedbackLabel = new JLabel("Feedback:");
			feedbackLabel.setBounds(300, yPos, 120, 30);
			feedbackLabel.setFont(new Font("Tahoma", Font.BOLD, 16));
			feedbackLabel.setForeground(new Color(102, 255, 178));
			add(feedbackLabel);

			feedbackBox = new JComboBox<>(new String[] { "Volunteer Feedback", "View Feedback" });
			feedbackBox.setBounds(420, yPos, 220, 30);
			feedbackBox.setBackground(new Color(25, 25, 25));
			feedbackBox.setForeground(new Color(102, 255, 178));
			add(feedbackBox);

			feedbackGo = new JButton("Go");
			feedbackGo.setBounds(660, yPos, 80, 30);
			feedbackGo.setFont(new Font("Segoe UI", Font.BOLD, 14));
			feedbackGo.setBackground(new Color(102, 255, 178));
			feedbackGo.setForeground(Color.BLACK);
			add(feedbackGo);

			feedbackGo.addActionListener(e -> {
				int idx = feedbackBox.getSelectedIndex();
				switch (idx) {
				case 0:
					new InsertFeedbackListener().actionPerformed(null);
					break;
				case 1:
					displayTableData("SELECT * FROM feedback", "Feedback");
					break;
				}
			});
		}

		setVisible(true);

	}

	// Volunteer CRUD
	class InsertVolunteerListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			String name = JOptionPane.showInputDialog("Enter Volunteer Name:");
			String email = JOptionPane.showInputDialog("Enter Volunteer Email:");
			String phone = JOptionPane.showInputDialog("Enter Volunteer Phone:");
			if (name != null && email != null && phone != null) {
				try {
					String sql = "INSERT INTO volunteer (volunteer_name, email, phone_number) VALUES (?, ?, ?)";
					PreparedStatement pstmt = conn.prepareStatement(sql);
					pstmt.setString(1, name);
					pstmt.setString(2, email);
					pstmt.setString(3, phone);
					pstmt.executeUpdate();
					JOptionPane.showMessageDialog(null, "Volunteer registered successfully.");
				} catch (SQLException ex) {
					ex.printStackTrace();
					JOptionPane.showMessageDialog(null, "Error registering volunteer.");
				}
			}
		}
	}

	class UpdateVolunteerListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			String id = JOptionPane.showInputDialog("Enter Volunteer ID to update:");
			if (id == null)
				return;
			String name = JOptionPane.showInputDialog("Enter new name:");
			String email = JOptionPane.showInputDialog("Enter new email:");
			String phone = JOptionPane.showInputDialog("Enter new phone:");
			if (name != null && email != null && phone != null) {
				try {
					String sql = "UPDATE volunteer SET volunteer_name=?, email=?, phone_number=? WHERE volunteer_id=?";
					PreparedStatement pstmt = conn.prepareStatement(sql);
					pstmt.setString(1, name);
					pstmt.setString(2, email);
					pstmt.setString(3, phone);
					pstmt.setInt(4, Integer.parseInt(id));
					int rows = pstmt.executeUpdate();
					if (rows > 0)
						JOptionPane.showMessageDialog(null, "Volunteer updated successfully.");
					else
						JOptionPane.showMessageDialog(null, "Volunteer ID not found.");
				} catch (Exception ex) {
					ex.printStackTrace();
					JOptionPane.showMessageDialog(null, "Error updating volunteer.");
				}
			}
		}
	}

	class DeleteVolunteerListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			String id = JOptionPane.showInputDialog("Enter Volunteer ID to delete:");
			if (id == null)
				return;
			try {
				String sql = "DELETE FROM volunteer WHERE volunteer_id=?";
				PreparedStatement pstmt = conn.prepareStatement(sql);
				pstmt.setInt(1, Integer.parseInt(id));
				int rows = pstmt.executeUpdate();
				if (rows > 0)
					JOptionPane.showMessageDialog(null, "Volunteer deleted successfully.");
				else
					JOptionPane.showMessageDialog(null, "Volunteer ID not found.");
			} catch (Exception ex) {
				ex.printStackTrace();
				JOptionPane.showMessageDialog(null, "Error deleting volunteer.");
			}
		}
	}

	// Event CRUD
	class InsertEventListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			String name = JOptionPane.showInputDialog("Enter Event Name:");
			String date = JOptionPane.showInputDialog("Enter Event Date (YYYY-MM-DD):");
			String location = JOptionPane.showInputDialog("Enter Event Location:");
			if (name != null && date != null && location != null) {
				try {
					String sql = "INSERT INTO event (event_name, event_date, location) VALUES (?, ?, ?)";
					PreparedStatement pstmt = conn.prepareStatement(sql);
					pstmt.setString(1, name);
					pstmt.setString(2, date);
					pstmt.setString(3, location);
					pstmt.executeUpdate();
					JOptionPane.showMessageDialog(null, "Event created successfully.");
				} catch (SQLException ex) {
					ex.printStackTrace();
					JOptionPane.showMessageDialog(null, "Error creating event.");
				}
			}
		}
	}

	class UpdateEventListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			String id = JOptionPane.showInputDialog("Enter Event ID to update:");
			if (id == null)
				return;
			String name = JOptionPane.showInputDialog("Enter new event name:");
			String date = JOptionPane.showInputDialog("Enter new event date (YYYY-MM-DD):");
			String location = JOptionPane.showInputDialog("Enter new location:");
			if (name != null && date != null && location != null) {
				try {
					String sql = "UPDATE event SET event_name=?, event_date=?, location=? WHERE event_id=?";
					PreparedStatement pstmt = conn.prepareStatement(sql);
					pstmt.setString(1, name);
					pstmt.setString(2, date);
					pstmt.setString(3, location);
					pstmt.setInt(4, Integer.parseInt(id));
					int rows = pstmt.executeUpdate();
					if (rows > 0)
						JOptionPane.showMessageDialog(null, "Event updated successfully.");
					else
						JOptionPane.showMessageDialog(null, "Event ID not found.");
				} catch (Exception ex) {
					ex.printStackTrace();
					JOptionPane.showMessageDialog(null, "Error updating event.");
				}
			}
		}
	}

	class DeleteEventListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			String id = JOptionPane.showInputDialog("Enter Event ID to delete:");
			if (id == null)
				return;
			try {
				String sql = "DELETE FROM event WHERE event_id=?";
				PreparedStatement pstmt = conn.prepareStatement(sql);
				pstmt.setInt(1, Integer.parseInt(id));
				int rows = pstmt.executeUpdate();
				if (rows > 0)
					JOptionPane.showMessageDialog(null, "Event deleted successfully.");
				else
					JOptionPane.showMessageDialog(null, "Event ID not found.");
			} catch (Exception ex) {
				ex.printStackTrace();
				JOptionPane.showMessageDialog(null, "Error deleting event.");
			}
		}
	}

	// Assignment CRUD
	class AssignVolunteerListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			String volunteerIdStr = JOptionPane.showInputDialog("Enter Volunteer ID:");
			String eventIdStr = JOptionPane.showInputDialog("Enter Event ID:");
			String role = JOptionPane.showInputDialog("Enter Assigned Role:");
			try {
				int volunteerId = Integer.parseInt(volunteerIdStr);
				int eventId = Integer.parseInt(eventIdStr);
				String sql = "INSERT INTO assignment (volunteer_id, event_id, assigned_role) VALUES (?, ?, ?)";
				PreparedStatement pstmt = conn.prepareStatement(sql);
				pstmt.setInt(1, volunteerId);
				pstmt.setInt(2, eventId);
				pstmt.setString(3, role);
				pstmt.executeUpdate();
				JOptionPane.showMessageDialog(null, "Volunteer assigned successfully.");
			} catch (SQLException | NumberFormatException ex) {
				ex.printStackTrace();
				JOptionPane.showMessageDialog(null, "Error assigning volunteer.");
			}
		}
	}

	class UpdateAssignmentListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			String id = JOptionPane.showInputDialog("Enter Assignment ID to update:");
			if (id == null)
				return;
			String volunteerIdStr = JOptionPane.showInputDialog("Enter new Volunteer ID:");
			String eventIdStr = JOptionPane.showInputDialog("Enter new Event ID:");
			String role = JOptionPane.showInputDialog("Enter new Assigned Role:");
			try {
				int volunteerId = Integer.parseInt(volunteerIdStr);
				int eventId = Integer.parseInt(eventIdStr);
				String sql = "UPDATE assignment SET volunteer_id=?, event_id=?, assigned_role=? WHERE assignment_id=?";
				PreparedStatement pstmt = conn.prepareStatement(sql);
				pstmt.setInt(1, volunteerId);
				pstmt.setInt(2, eventId);
				pstmt.setString(3, role);
				pstmt.setInt(4, Integer.parseInt(id));
				int rows = pstmt.executeUpdate();
				if (rows > 0)
					JOptionPane.showMessageDialog(null, "Assignment updated successfully.");
				else
					JOptionPane.showMessageDialog(null, "Assignment ID not found.");
			} catch (Exception ex) {
				ex.printStackTrace();
				JOptionPane.showMessageDialog(null, "Error updating assignment.");
			}
		}
	}

	class DeleteAssignmentListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			String id = JOptionPane.showInputDialog("Enter Assignment ID to delete:");
			if (id == null)
				return;
			try {
				String sql = "DELETE FROM assignment WHERE assignment_id=?";
				PreparedStatement pstmt = conn.prepareStatement(sql);
				pstmt.setInt(1, Integer.parseInt(id));
				int rows = pstmt.executeUpdate();
				if (rows > 0)
					JOptionPane.showMessageDialog(null, "Assignment deleted successfully.");
				else
					JOptionPane.showMessageDialog(null, "Assignment ID not found.");
			} catch (Exception ex) {
				ex.printStackTrace();
				JOptionPane.showMessageDialog(null, "Error deleting assignment.");
			}
		}
	}

	class InsertFeedbackListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			String volunteerIdStr = JOptionPane.showInputDialog("Enter Volunteer ID:");
			String eventIdStr = JOptionPane.showInputDialog("Enter Event ID:");
			String ratingStr = JOptionPane.showInputDialog("Enter Rating (1-5):");
			String feedback = JOptionPane.showInputDialog("Enter Feedback:");
			try {
				int volunteerId = Integer.parseInt(volunteerIdStr);
				int eventId = Integer.parseInt(eventIdStr);
				int rating = Integer.parseInt(ratingStr);
				if (rating < 1 || rating > 5) {
					JOptionPane.showMessageDialog(null, "Rating should be between 1 and 5.");
					return;
				}
				String sql = "INSERT INTO feedback (volunteer_id, event_id, rating, feedback) VALUES (?, ?, ?, ?)";
				PreparedStatement pstmt = conn.prepareStatement(sql);
				pstmt.setInt(1, volunteerId);
				pstmt.setInt(2, eventId);
				pstmt.setInt(3, rating);
				pstmt.setString(4, feedback);
				pstmt.executeUpdate();
				JOptionPane.showMessageDialog(null, "Feedback inserted successfully.");
			} catch (SQLException | NumberFormatException ex) {
				ex.printStackTrace();
				JOptionPane.showMessageDialog(null, "Error inserting feedback.");
			}
		}
	}

	// Generate Event Report
	class GenerateEventReportListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			String eventIdStr = JOptionPane.showInputDialog("Enter Event ID:");
			if (eventIdStr == null || eventIdStr.trim().isEmpty())
				return;

			try {
				int eventId = Integer.parseInt(eventIdStr);

				String query = """
						SELECT e.event_name, e.event_date, e.location,
						       v.volunteer_id, v.volunteer_name, a.assigned_role
						FROM event e
						LEFT JOIN assignment a ON e.event_id = a.event_id
						LEFT JOIN volunteer v ON a.volunteer_id = v.volunteer_id
						WHERE e.event_id = ?
						""";

				PreparedStatement stmt = conn.prepareStatement(query);
				stmt.setInt(1, eventId);
				ResultSet rs = stmt.executeQuery();

				// Prepare report StringBuilder
				StringBuilder report = new StringBuilder();
				boolean eventFound = false;
				boolean hasVolunteers = false;

				// Loop over results
				while (rs.next()) {
					if (!eventFound) {
						// First row: collect event details
						String eventName = rs.getString("event_name");
						String eventDate = rs.getString("event_date");
						String location = rs.getString("location");

						report.append("--- EVENT REPORT ---\n\n");
						report.append("Event ID: ").append(eventId).append("\n");
						report.append("Name: ").append(eventName).append("\n");
						report.append("Date: ").append(eventDate).append("\n");
						report.append("Location: ").append(location).append("\n\n");
						report.append("Volunteers Assigned:\n");

						eventFound = true;
					}

					String volunteerName = rs.getString("volunteer_name");
					if (volunteerName != null) {
						int volunteerId = rs.getInt("volunteer_id");
						String role = rs.getString("assigned_role");

						report.append("Volunteer ID: ").append(volunteerId).append("\n");
						report.append("Name: ").append(volunteerName).append("\n");
						report.append("Role: ").append(role).append("\n");
						report.append("_____________________\n");

						hasVolunteers = true;
					}
				}

				// No event found case
				if (!eventFound) {
					JOptionPane.showMessageDialog(null, "Event not found.");
					return;
				}

				// No volunteers
				if (!hasVolunteers) {
					report.append("No volunteers assigned to this event.");
				}

				// Display report
				showReport(report.toString());

			} catch (SQLException | NumberFormatException ex) {
				ex.printStackTrace();
				JOptionPane.showMessageDialog(null, "Error generating report.");
			}
		}

		private void showReport(String report) {
			JTextArea textArea = new JTextArea(report);
			textArea.setEditable(false);
			textArea.setBackground(Color.BLACK);
			textArea.setForeground(new Color(102, 255, 178));
			textArea.setFont(new Font("Monospaced", Font.PLAIN, 14));

			JScrollPane scrollPane = new JScrollPane(textArea);
			scrollPane.setSize(new Dimension(550, 400));

			JOptionPane.showMessageDialog(null, scrollPane, "Event Report", JOptionPane.INFORMATION_MESSAGE);
		}
	}

	// Utility: Display table data
	private void displayTableData(String query, String title) {
		JFrame tableFrame = new JFrame(title);
		tableFrame.setSize(800, 400);
		tableFrame.setLocationRelativeTo(null);
		tableFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		tableFrame.setLayout(null);

		JPanel bg = new JPanel();
		bg.setBackground(Color.BLACK);
		bg.setBounds(0, 0, 800, 400);
		bg.setLayout(null);
		tableFrame.add(bg);

		JLabel titleLabel = new JLabel(title);
		titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
		titleLabel.setForeground(new Color(102, 255, 178));
		titleLabel.setBounds(300, 10, 400, 40);
		bg.add(titleLabel);

		JTable table = new JTable();
		DefaultTableModel model = new DefaultTableModel();
		table.setModel(model);

		// Color changes only:
		table.setBackground(new Color(20, 20, 20)); // very dark background
		table.setForeground(new Color(102, 255, 178)); // neon green text
		table.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		table.setSelectionBackground(new Color(102, 255, 178)); // neon green selection
		table.setSelectionForeground(Color.BLACK); // black text when selected

		// Table header color
		JTableHeader header = table.getTableHeader();
		header.setBackground(new Color(0, 128, 128)); // teal header
		header.setForeground(Color.WHITE); // white header text
		header.setFont(new Font("Segoe UI", Font.BOLD, 15));

		JScrollPane scrollPane = new JScrollPane(table);
		scrollPane.setBounds(50, 70, 700, 220);
		scrollPane.getViewport().setBackground(Color.BLACK); // scroll background
		bg.add(scrollPane);

		// Load data
		try {
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			ResultSetMetaData meta = rs.getMetaData();
			int columnCount = meta.getColumnCount();

			// Set column names
			String[] columnNames = new String[columnCount];
			for (int i = 1; i <= columnCount; i++) {
				columnNames[i - 1] = meta.getColumnName(i);
			}
			model.setColumnIdentifiers(columnNames);

			// Add rows
			while (rs.next()) {
				Object[] rowData = new Object[columnCount];
				for (int i = 1; i <= columnCount; i++) {
					rowData[i - 1] = rs.getObject(i);
				}
				model.addRow(rowData);
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
			JOptionPane.showMessageDialog(null, "Error displaying data.");
		}

		tableFrame.setVisible(true);
	}

	public static void main(String[] args) {

		SwingUtilities.invokeLater(() -> new NGOmain(true).setVisible(true)); // admin mode for testing
	}
}
