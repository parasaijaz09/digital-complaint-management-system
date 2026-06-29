import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;

public class UserDashboard extends JFrame {
    private JTable table;
    private DefaultTableModel model;
    private JButton btnSubmit, btnHistory, btnLogout;
    
    public UserDashboard() {
        setTitle("User Dashboard");
        setSize(800, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        initComponents();
        loadMyComplaints();
    }
    
    private void initComponents() {
        setLayout(new BorderLayout());
        
        JLabel title = new JLabel("Welcome, " + Session.getCurrentUsername(), SwingConstants.CENTER);
        title.setFont(new Font("Segoe UI", Font.BOLD, 24));
        add(title, BorderLayout.NORTH);
        
        // Table
        String[] columns = {"ID", "Title", "Category", "Priority", "Status", "Assigned To"};
        model = new DefaultTableModel(columns, 0);
        table = new JTable(model);
        add(new JScrollPane(table), BorderLayout.CENTER);
        
        // Buttons
        JPanel buttonPanel = new JPanel();
        btnSubmit = new JButton("Submit New Complaint");
        btnHistory = new JButton("Refresh History");
        btnLogout = new JButton("Logout");
        
        buttonPanel.add(btnSubmit);
        buttonPanel.add(btnHistory);
        buttonPanel.add(btnLogout);
        add(buttonPanel, BorderLayout.SOUTH);
        
        btnSubmit.addActionListener(e -> openComplaintForm());
        btnHistory.addActionListener(e -> loadMyComplaints());
        btnLogout.addActionListener(e -> logout());
    }
    
    private void loadMyComplaints() {
        model.setRowCount(0);
        int userId = Session.getCurrentUserId();
        ArrayList<Complaint> complaints = ComplaintManager.getUserComplaints(userId);
        
        complaints.forEach((c) -> {
            model.addRow(new Object[]{
                c.getId(),
                c.getTitle(),
                c.getCategory(),
                c.getPriority(),
                c.getStatus(),
                c.getAssignedTo()
            });
        });
    }
    
    private void openComplaintForm() {
        new ComplaintForm().setVisible(true);
        dispose();
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