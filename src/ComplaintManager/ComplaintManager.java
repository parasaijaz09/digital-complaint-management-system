import java.sql.*;
import java.util.ArrayList;

public class ComplaintManager {
    
    // Add new complaint
    public static boolean addComplaint(Complaint complaint, int userId) {
        String sql = "INSERT INTO complaints (title, category, priority, description, status, userId) VALUES (?, ?, ?, ?, ?, ?)";
        
        try (PreparedStatement pstmt = DatabaseConnection.getConnection().prepareStatement(sql)) {
            pstmt.setString(1, complaint.getTitle());
            pstmt.setString(2, complaint.getCategory());
            pstmt.setString(3, complaint.getPriority());
            pstmt.setString(4, complaint.getDescription());
            pstmt.setString(5, "Pending");
            pstmt.setInt(6, userId);
            
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Error adding complaint: " + e.getMessage());
            return false;
        }
    }
    
    // Get all complaints (for Admin)
    public static ArrayList<Complaint> getAllComplaints() {
        ArrayList<Complaint> complaints = new ArrayList<>();
        String sql = "SELECT * FROM complaints ORDER BY id DESC";
        
        try (Statement stmt = DatabaseConnection.getConnection().createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                Complaint c = new Complaint(
                    rs.getInt("id"),
                    rs.getString("title"),
                    rs.getString("category"),
                    rs.getString("priority"),
                    rs.getString("description")
                );
                c.setStatus(rs.getString("status"));
                String assignedTo = rs.getString("assignedTo");
                c.setAssignedTo(assignedTo != null ? assignedTo : "Not Assigned");
                complaints.add(c);
            }
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
        return complaints;
    }
    
    // Get complaints for specific user
    public static ArrayList<Complaint> getUserComplaints(int userId) {
        ArrayList<Complaint> complaints = new ArrayList<>();
        String sql = "SELECT * FROM complaints WHERE userId = ? ORDER BY id DESC";
        
        try (PreparedStatement pstmt = DatabaseConnection.getConnection().prepareStatement(sql)) {
            pstmt.setInt(1, userId);
            ResultSet rs = pstmt.executeQuery();
            
            while (rs.next()) {
                Complaint c = new Complaint(
                    rs.getInt("id"),
                    rs.getString("title"),
                    rs.getString("category"),
                    rs.getString("priority"),
                    rs.getString("description")
                );
                c.setStatus(rs.getString("status"));
                String assignedTo = rs.getString("assignedTo");
                c.setAssignedTo(assignedTo != null ? assignedTo : "Not Assigned");
                complaints.add(c);
            }
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
        return complaints;
    }
    
    // Update complaint status
    public static boolean updateComplaintStatus(int complaintId, String status) {
        String sql = "UPDATE complaints SET status = ? WHERE id = ?";
        
        try (PreparedStatement pstmt = DatabaseConnection.getConnection().prepareStatement(sql)) {
            pstmt.setString(1, status);
            pstmt.setInt(2, complaintId);
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
            return false;
        }
    }
    
    // Assign complaint to staff
    public static boolean assignComplaint(int complaintId, String staffName) {
        String sql = "UPDATE complaints SET assignedTo = ?, status = 'Assigned' WHERE id = ?";
        
        try (PreparedStatement pstmt = DatabaseConnection.getConnection().prepareStatement(sql)) {
            pstmt.setString(1, staffName);
            pstmt.setInt(2, complaintId);
            int rows = pstmt.executeUpdate();
            return rows > 0;
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
            return false;
        }
    }
    
    // Get complaints assigned to specific staff
    public static ArrayList<Complaint> getAssignedComplaints(String staffName) {
        ArrayList<Complaint> complaints = new ArrayList<>();
        String sql = "SELECT * FROM complaints WHERE assignedTo = ?";
        
        try (PreparedStatement pstmt = DatabaseConnection.getConnection().prepareStatement(sql)) {
            pstmt.setString(1, staffName);
            ResultSet rs = pstmt.executeQuery();
            
            while (rs.next()) {
                Complaint c = new Complaint(
                    rs.getInt("id"),
                    rs.getString("title"),
                    rs.getString("category"),
                    rs.getString("priority"),
                    rs.getString("description")
                );
                c.setStatus(rs.getString("status"));
                c.setAssignedTo(rs.getString("assignedTo"));
                complaints.add(c);
            }
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
        return complaints;
    }
}
