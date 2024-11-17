public class User {
    private String firstName;
    private String lastName;
    private String contactNumber;
    private String username;
    private String password;

    public User(String firstName, String lastName, String contactNumber, String username, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.contactNumber = contactNumber;
        this.username = username;
        this.password = password;
    }

    @Override
    public String toString() {
        return firstName + "," + lastName + "," + contactNumber + "," + username + "," + password;
    }
}
