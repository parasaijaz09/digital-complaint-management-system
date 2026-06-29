import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class AdminDashboard extends JFrame {
    private JTable table;
    private DefaultTableModel model;
    private JButton btnView, btnAssign, btnResolve, btnLogout;
    
    public AdminDashboard() {
        setTitle("Admin Dashboard");
        setSize(800, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        initComponents();
        loadComplaints();
    }
    
    private void initComponents() {
        setLayout(new BorderLayout());
        
        // Title
        JLabel title = new JLabel("Admin Dashboard", SwingConstants.CENTER);
        title.setFont(new Font("Segoe UI", Font.BOLD, 24));
        add(title, BorderLayout.NORTH);
        
        // Table
        String[] columns = {"ID", "Title", "Category", "Priority", "Status", "Assigned To"};
        model = new DefaultTableModel(columns, 0);
        table = new JTable(model);
        add(new JScrollPane(table), BorderLayout.CENTER);
        
        // Buttons
        JPanel buttonPanel = new JPanel();
        btnView = new JButton("View Complaints");
        btnAssign = new JButton("Assign Complaint");
        btnResolve = new JButton("Resolve Complaint");
        btnLogout = new JButton("Logout");
        
        buttonPanel.add(btnView);
        buttonPanel.add(btnAssign);
        buttonPanel.add(btnResolve);
        buttonPanel.add(btnLogout);
        add(buttonPanel, BorderLayout.SOUTH);
        
        // Button Actions
        btnView.addActionListener(e -> loadComplaints());
        btnAssign.addActionListener(e -> assignComplaint());
        btnResolve.addActionListener(e -> resolveComplaint());
        btnLogout.addActionListener(e -> logout());
    }
    
    private void loadComplaints() {
        model.setRowCount(0);
        ArrayList<Complaint> complaints = ComplaintManager.getAllComplaints();
        for (Complaint c : complaints) {
            model.addRow(new Object[]{
                c.getId(),
                c.getTitle(),
                c.getCategory(),
                c.getPriority(),
                c.getStatus(),
                c.getAssignedTo()
            });
        }
    }
    
    private void assignComplaint() {
    int selectedRow = table.getSelectedRow();
    if (selectedRow == -1) {
        JOptionPane.showMessageDialog(this, "Please select a complaint first!");
        return;
    }
    
    int complaintId = (int) model.getValueAt(selectedRow, 0);
    
    // Get all staff names from database
    ArrayList<String> staffList = getStaffNames();
    
    if (staffList.isEmpty()) {
        JOptionPane.showMessageDialog(this, "No staff members found! Please add staff first.");
        return;
    }
    
    // Show dropdown
    String staffName = (String) JOptionPane.showInputDialog(
        this,
        "Select staff member to assign:",
        "Assign Complaint",
        JOptionPane.QUESTION_MESSAGE,
        null,
        staffList.toArray(),
        staffList.get(0)
    );
    
    if (staffName != null && !staffName.isEmpty()) {
        if (ComplaintManager.assignComplaint(complaintId, staffName)) {
            JOptionPane.showMessageDialog(this, "✅ Complaint assigned to " + staffName);
            loadComplaints();
        } else {
            JOptionPane.showMessageDialog(this, "❌ Error assigning complaint!");
        }
    }
}

// Method to get all staff names from database
private ArrayList<String> getStaffNames() {
    ArrayList<String> staffNames = new ArrayList<>();
    String sql = "SELECT username FROM users WHERE role = 'staff'";
    
    try (Statement stmt = DatabaseConnection.getConnection().createStatement();
         ResultSet rs = stmt.executeQuery(sql)) {
        
        while (rs.next()) {
            staffNames.add(rs.getString("username"));
        }
    } catch (SQLException e) {
        System.out.println("Error loading staff: " + e.getMessage());
    }
    return staffNames;
}
    
    private void resolveComplaint() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a complaint first!");
            return;
        }
        
        int complaintId = (int) model.getValueAt(selectedRow, 0);
        
        int confirm = JOptionPane.showConfirmDialog(this, "Mark this complaint as Resolved?");
        if (confirm == JOptionPane.YES_OPTION) {
            if (ComplaintManager.updateComplaintStatus(complaintId, "Resolved")) {
                JOptionPane.showMessageDialog(this, "Complaint resolved!");
                loadComplaints();
            } else {
                JOptionPane.showMessageDialog(this, "Error resolving complaint!");
            }
        }
    }
    
    private void logout() {
        int confirm = JOptionPane.showConfirmDialog(this, "Are you sure you want to logout?");
        if (confirm == JOptionPane.YES_OPTION) {
            Session.clearSession();
            new LoginForm().setVisible(true);
            dispose();
        }
    }
}