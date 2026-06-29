public class Admin extends User {

    public Admin(String name, String email, String password) {
        super(name, email, password);
    }

    public void assignComplaint(Complaint complaint) {
        complaint.setStatus("Assigned");
        System.out.println("Dear User : Your Complaint has been assigned : " + complaint.getTitle());
    }

    public void resolveComplaint(Complaint complaint) {
        complaint.setStatus("Resolved");
        System.out.println("Dear User : Your Complaint has been resolved : " + complaint.getTitle());
    }

    public void rejectComplaint(Complaint complaint) {
        complaint.setStatus("Rejected");
        System.out.println("Dear User : Your Complaint has been rejected : " + complaint.getTitle());
    }
}
