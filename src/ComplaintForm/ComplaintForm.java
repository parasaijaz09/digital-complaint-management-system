import javax.swing.*;
import java.awt.*;

public class ComplaintForm extends JFrame {
    private JComboBox<String> cmbCategory, cmbPriority;
    private JTextField txtTitle;
    private JTextArea txtDescription;
    private JButton btnSubmit, btnBack;
    
    public ComplaintForm() {
        setTitle("Submit New Complaint");
        setSize(500, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        initComponents();
    }
    
    private void initComponents() {
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        
        // Title
        gbc.gridx = 0; gbc.gridy = 0;
        add(new JLabel("Title:"), gbc);
        gbc.gridx = 1;
        txtTitle = new JTextField(20);
        add(txtTitle, gbc);
        
        // Category
        gbc.gridx = 0; gbc.gridy = 1;
        add(new JLabel("Category:"), gbc);
        gbc.gridx = 1;
        String[] categories = {"Electricity", "Maintenance", "Administration", "IT", "Other"};
        cmbCategory = new JComboBox<>(categories);
        add(cmbCategory, gbc);
        
        // Priority
        gbc.gridx = 0; gbc.gridy = 2;
        add(new JLabel("Priority:"), gbc);
        gbc.gridx = 1;
        String[] priorities = {"Low", "Normal", "High", "Urgent"};
        cmbPriority = new JComboBox<>(priorities);
        add(cmbPriority, gbc);
        
        // Description
        gbc.gridx = 0; gbc.gridy = 3;
        add(new JLabel("Description:"), gbc);
        gbc.gridx = 1;
        txtDescription = new JTextArea(5, 20);
        txtDescription.setLineWrap(true);
        add(new JScrollPane(txtDescription), gbc);
        
        // Buttons
        gbc.gridx = 0; gbc.gridy = 4;
        btnSubmit = new JButton("Submit");
        add(btnSubmit, gbc);
        gbc.gridx = 1;
        btnBack = new JButton("Back");
        add(btnBack, gbc);
        
        btnSubmit.addActionListener(e -> submitComplaint());
        btnBack.addActionListener(e -> {
            new UserDashboard().setVisible(true);
            dispose();
        });
    }
    
    private void submitComplaint() {
        String title = txtTitle.getText().trim();
        String category = (String) cmbCategory.getSelectedItem();
        String priority = (String) cmbPriority.getSelectedItem();
        String description = txtDescription.getText().trim();
        
        if (title.isEmpty() || description.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill all fields!");
            return;
        }
        
        Complaint complaint = new Complaint(0, title, category, priority, description);
        int userId = Session.getCurrentUserId();
        
        if (ComplaintManager.addComplaint(complaint, userId)) {
            JOptionPane.showMessageDialog(this, "Complaint submitted successfully!");
            new UserDashboard().setVisible(true);
            dispose();
        } else {
            JOptionPane.showMessageDialog(this, "Error submitting complaint!");
        }
    }
}