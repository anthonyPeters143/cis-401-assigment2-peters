public class ssnNode {
    private String firstName, lastName, ssn;

    // Constructor
    ssnNode(String firstNameInput, String lastNameInput, String ssnInput) {
        firstName = firstNameInput;
        lastName = lastNameInput;
        ssn = ssnInput;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getSsn() {
        return ssn;
    }
}
