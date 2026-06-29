public class User {
    
    private String name;
    private String email;
    private String password;

    public User(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }
    
    public boolean login(String pass){
        return password.equals(pass);
    }

    public void submitComplaint(Complaint complaint) {
        System.out.println(name + " submitted: " + complaint.getTitle());
    }
}