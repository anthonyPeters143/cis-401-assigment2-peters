/**
 * Class: SsnNode, Used to hold values as nodes within LinkedList database. Will store firstName, lastName, and ssn
 * values.
 *
 * @author Anthony Peters
 */
public class SsnNode {
    private String firstName, lastName, ssn;

    /**
     * Method: SsnNode constructor, Augment-constructor that needs firstName, lastName, and ssn string inputs. Will
     * create string variables for firstName, lastName, and ssn values.
     *
     * @param firstNameInput String, First name to be stored
     * @param lastNameInput String, Last name to be stored
     * @param ssnInput String, Ssn value to be stored
     */
    SsnNode(String firstNameInput, String lastNameInput, String ssnInput) {
        firstName = firstNameInput;
        lastName = lastNameInput;
        ssn = ssnInput;
    }

    /**
     * Method: getFirstName, Returns String value of firstName parameter
     *
     * @return String, First name value of SsnNode
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Method: getLastName, Returns String value of lastName parameter
     *
     * @return String, Last name value of SsnNode
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Method: getSsn, Returns String value of ssn parameter
     *
     * @return String, Ssn value of SsnNode
     */
    public String getSsn() {
        return ssn;
    }
}
