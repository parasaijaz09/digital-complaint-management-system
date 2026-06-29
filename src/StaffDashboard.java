import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;

public class StaffDashboard extends JFrame {
    private JTable table;
    private DefaultTableModel model;
    private JButton btnViewAssigned, btnMarkProgress, btnResolve, btnLogout;
    
    public StaffDashboard() {
        setTitle("Staff Dashboard - " + Session.getCurrentUsername());
        setSize(800, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        initComponents();
        loadAssignedComplaints();
    }
    
    private void initComponents() {
        setLayout(new BorderLayout());
        
        JLabel title = new JLabel("Staff Dashboard", SwingConstants.CENTER);
        title.setFont(new Font("Segoe UI", Font.BOLD, 24));
        add(title, BorderLayout.NORTH);
        
        // Table
        String[] columns = {"ID", "Title", "Category", "Priority", "Status"};
        model = new DefaultTableModel(columns, 0);
        table = new JTable(model);
        add(new JScrollPane(table), BorderLayout.CENTER);
        
        // Buttons
        JPanel buttonPanel = new JPanel();
        btnViewAssigned = new JButton("View Assigned");
        btnMarkProgress = new JButton("Mark In Progress");
        btnResolve = new JButton("Resolve");
        btnLogout = new JButton("Logout");
        
        buttonPanel.add(btnViewAssigned);
        buttonPanel.add(btnMarkProgress);
        buttonPanel.add(btnResolve);
        buttonPanel.add(btnLogout);
        add(buttonPanel, BorderLayout.SOUTH);
        
        btnViewAssigned.addActionListener(e -> loadAssignedComplaints());
        btnMarkProgress.addActionListener(e -> markInProgress());
        btnResolve.addActionListener(e -> resolveComplaint());
        btnLogout.addActionListener(e -> logout());
    }
    
    private void loadAssignedComplaints() {
        model.setRowCount(0);
        String staffName = Session.getCurrentUsername();
        ArrayList<Complaint> complaints = ComplaintManager.getAssignedComplaints(staffName);
        
        if (complaints.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No complaints assigned to " + staffName);
        }
        
        for (Complaint c : complaints) {
            model.addRow(new Object[]{
                c.getId(),
                c.getTitle(),
                c.getCategory(),
                c.getPriority(),
                c.getStatus()
            });
        }
    }
    
    private void markInProgress() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a complaint first!");
            return;
        }
        
        int complaintId = (int) model.getValueAt(selectedRow, 0);
        
        if (ComplaintManager.updateComplaintStatus(complaintId, "In Progress")) {
            JOptionPane.showMessageDialog(this, "Status updated to In Progress!");
            loadAssignedComplaints();
        } else {
            JOptionPane.showMessageDialog(this, "Error updating status!");
        }
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
                loadAssignedComplaints();
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