public class Staff extends User {

    public Staff(String name,String email, String password){
        super(name,email,password);
    }

    public void updateStatus(
            Complaint complaint,
            String status){

        complaint.setStatus(status);
    }
}
