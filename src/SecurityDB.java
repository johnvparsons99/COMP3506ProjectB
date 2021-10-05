public class SecurityDB extends SecurityDBBase {

    public SecurityDB(int numPlanes, int numPassengersPerPlane) {
        super(numPlanes, numPassengersPerPlane);
    }

    /* Implement all the necessary methods here */

    @Override
    public int calculateHashCode(String key) {
        return 0;
    }

    @Override
    public int size() {
        return 0;
    }

    @Override
    public String get(String passportId) {
        return null;
    }

    @Override
    public boolean remove(String passportId) {
        return false;
    }

    @Override
    public boolean addPassenger(String name, String passportId) {
        return false;
    }

    @Override
    public int count() {
        return 0;
    }

    @Override
    public int getIndex(String passportId) {
        return 0;
    }

    /* End of My Functions */

    /*
        !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
        REMOVE THE MAIN FUNCTION BEFORE SUBMITTING TO THE AUTOGRADER
        !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!

        The following main function is provided for simple debugging only

        Note: to enable assertions, you need to add the "-ea" flag to the
        VM options of SecurityDB's run configuration
     */
    public static void main(String[] args) {
        SecurityDB db = new SecurityDB(3, 2);

        // add
        db.addPassenger("Rob Bekker", "Asb23f");
        db.addPassenger("Kira Adams", "MKSD23");
        db.addPassenger("Kira Adams", "MKSD24");
        assert db.contains("Asb23f");

        // count
        assert db.count() == 3;

        // del
        db.remove("MKSD23");
        assert !db.contains("MKSD23");
        assert db.contains("Asb23f");

        // hashcodes
        assert db.calculateHashCode("Asb23f") == 1717;

        // suspicious
        db = new SecurityDB(3, 2);
        db.addPassenger("Rob Bekker", "Asb23f");
        db.addPassenger("Robert Bekker", "Asb23f");
        // Should print a warning to stderr
    }


}

/* Add any additional helper classes here */
