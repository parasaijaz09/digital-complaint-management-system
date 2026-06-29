import javax.swing.*;
import java.awt.*;
import java.sql.*;     

public class LoginForm extends JFrame {

    private JTextField txtUsername;
    private JPasswordField txtPassword;
    private JButton btnLogin, btnSignUp;

    private static final Color PRIMARY_DARK = new Color(30, 60, 114);
    private static final Color PRIMARY_LIGHT = new Color(42, 82, 152);
    private static final Color BG_WHITE = new Color(250,251,252);
    private static final Color TEXT_DARK = new Color(45,55,72);
    private static final Color TEXT_GRAY = new Color(113,128,150);
    private static final Color BORDER_COLOR = new Color(226,232,240);

    public LoginForm() {
        initComponents();
    }

    private void initComponents() {

        setTitle("Complaint Management System");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(900,550);
        setLocationRelativeTo(null);
        setResizable(false);

        JPanel mainPanel = new JPanel(null);
        mainPanel.setBackground(BG_WHITE);

        // LEFT PANEL
        JPanel leftPanel = new JPanel();
        leftPanel.setLayout(null);
        leftPanel.setBackground(PRIMARY_DARK);
        leftPanel.setBounds(0,0,380,550);

        JLabel lblWelcome = new JLabel("Welcome Back!");
        lblWelcome.setBounds(50,180,300,40);
        lblWelcome.setForeground(Color.WHITE);
        lblWelcome.setFont(new Font("Segoe UI",Font.BOLD,28));
        leftPanel.add(lblWelcome);

        JLabel lblSystem = new JLabel("Complaint Management");
        lblSystem.setBounds(50,230,300,30);
        lblSystem.setForeground(Color.WHITE);
        lblSystem.setFont(new Font("Segoe UI",Font.PLAIN,20));
        leftPanel.add(lblSystem);

        JLabel lblSub = new JLabel("Submit, Track & Resolve Complaints");
        lblSub.setBounds(50,270,300,20);
        lblSub.setForeground(new Color(180,210,255));
        leftPanel.add(lblSub);

        // RIGHT PANEL
        JPanel rightPanel = new JPanel(null);
        rightPanel.setBackground(BG_WHITE);
        rightPanel.setBounds(380,0,520,550);

        JLabel title = new JLabel("Account Login");
        title.setBounds(60,80,300,35);
        title.setFont(new Font("Segoe UI",Font.BOLD,28));
        title.setForeground(TEXT_DARK);
        rightPanel.add(title);

        JLabel sub = new JLabel("Please login to continue");
        sub.setBounds(60,120,300,20);
        sub.setForeground(TEXT_GRAY);
        rightPanel.add(sub);

        JLabel lblUser = new JLabel("Username");
        lblUser.setBounds(60,180,100,20);
        rightPanel.add(lblUser);

        txtUsername = new JTextField();
        txtUsername.setBounds(60,210,400,40);
        txtUsername.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(BORDER_COLOR),
            BorderFactory.createEmptyBorder(5,10,5,10)));
        rightPanel.add(txtUsername);

        JLabel lblPass = new JLabel("Password");
        lblPass.setBounds(60,280,100,20);
        rightPanel.add(lblPass);

        txtPassword = new JPasswordField();
        txtPassword.setBounds(60,310,400,40);
        txtPassword.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(BORDER_COLOR),
            BorderFactory.createEmptyBorder(5,10,5,10)));
        rightPanel.add(txtPassword);

        btnLogin = new JButton("Login");
        btnLogin.setBounds(60,390,400,45);
        btnLogin.setBackground(PRIMARY_LIGHT);
        btnLogin.setForeground(Color.WHITE);
        btnLogin.setFocusPainted(false);
        btnLogin.addActionListener(e -> login());
        rightPanel.add(btnLogin);

        btnSignUp = new JButton("Create Account");
        btnSignUp.setBounds(160,460,180,35);
        btnSignUp.addActionListener(e -> {
            JOptionPane.showMessageDialog(this, "Sign Up Coming Soon");
        });
        rightPanel.add(btnSignUp);

        mainPanel.add(leftPanel);
        mainPanel.add(rightPanel);
        setContentPane(mainPanel);
    }
    
    private void login() {
        String username = txtUsername.getText();
        String password = new String(txtPassword.getPassword());
        
        String sql = "SELECT * FROM users WHERE username = ? AND password = ?";
        
        try (PreparedStatement pstmt = DatabaseConnection.getConnection().prepareStatement(sql)) {
            pstmt.setString(1, username);
            pstmt.setString(2, password);
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                String role = rs.getString("role");
                int userId = rs.getInt("id");
                
                Session.setCurrentUserId(userId);
                Session.setCurrentUsername(username);
                Session.setCurrentRole(role);
                
                if (role.equals("user")) {
                    new UserDashboard().setVisible(true);
                    dispose();
                } else if (role.equals("admin")) {
                    new AdminDashboard().setVisible(true);
                    dispose();
                } else if (role.equals("staff")) {
                    new StaffDashboard().setVisible(true);
                    dispose();
                }
            } else {
                JOptionPane.showMessageDialog(this, "Invalid Username or Password!");
            }
            
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Database Error: " + e.getMessage());
        }
    }
    
    public static void main(String args[]) {
        SwingUtilities.invokeLater(() -> {
            new LoginForm().setVisible(true);
        });
    }
}