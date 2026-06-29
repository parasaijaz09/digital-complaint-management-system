public class Session {
    private static int currentUserId;
    private static String currentUsername;
    private static String currentRole;
    
    public static void setCurrentUserId(int id) { 
        currentUserId = id; 
    }
    
    public static int getCurrentUserId() { 
        return currentUserId; 
    }
    
    public static void setCurrentUsername(String name) { 
        currentUsername = name; 
    }
    
    public static String getCurrentUsername() { 
        return currentUsername; 
    }
    
    public static void setCurrentRole(String role) { 
        currentRole = role; 
    }
    
    public static String getCurrentRole() { 
        return currentRole; 
    }
    
    public static void clearSession() {
        currentUserId = -1;
        currentUsername = null;
        currentRole = null;
    }
}