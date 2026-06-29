public class Complaint {

    private int id;
    private String title;
    private String category;
    private String priority;
    private String description;
    private String status;
    private String assignedTo;
    

    public Complaint(int id,String title,String category,String priority,String description) {
        this.id=id;
        this.title=title;
        this.category=category;
        this.priority=priority;
        this.description=description;
        
        status="Pending";
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getCategory() {
        return category;
    }

    public String getPriority() {
        return priority;
    }

    public String getDescription() {
        return description;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status=status;
    }
         public String getAssignedTo(){
        return assignedTo;
    }

    public void setAssignedTo(String name){
        this.assignedTo = name;
    }
}