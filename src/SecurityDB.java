public class SecurityDB extends SecurityDBBase {

    /* Fields */
    /** Initial HashTable size M */
    private final int INITIAL_TABLE_SIZE = 859;
    private int tableSize;

    private Passenger[] passengers;

    /* My Constructor */

    public SecurityDB(int numPlanes, int numPassengersPerPlane) {
        super(numPlanes, numPassengersPerPlane);
        this.tableSize = INITIAL_TABLE_SIZE;
        this.passengers = new Passenger[getTableSize()];
    }

    /* Implement all the necessary methods here */

    /**
     * Calculates the hash code based on the given key.
     *
     * @param key string to calculate hash code of
     * @return hash code of key
     */
    @Override
    public int calculateHashCode(String key) {
        // Hash Function
        // Cumulative Component Sum
        System.out.println("===== Hashing =====");
        int hash = 0;

        for (int i = 0; i < key.length(); i++) {
            int ascii = (int) key.charAt(i);
            System.out.println(ascii);
            hash += ascii;
        }
        System.out.println(hash);

        return hash;
    }

    public int compressHash(int hash) {
        // Compression Function
        return  hash % getTableSize();
    }

    public int linearProbe(int hash, int probe) {
        return hash + probe;
    }

    /**
     * Returns the actual size of the hashtable, including the empty buckets.
     *
     * @return the size of the hashtable
     */
    @Override
    public int size() {
        return 0;
    }

    /**
     * Finds a passenger's name by their passport ID.
     *
     * @param passportId passenger's passport ID
     * @return the name of the person if they are in the system, otherwise null
     */
    @Override
    public String get(String passportId) {
        return null;
    }

    /**
     * Removes a passenger from the system.
     *
     * @param passportId passenger's passport ID
     * @return true if the passenger was deleted, false if they could not be found
     */
    @Override
    public boolean remove(String passportId) {
        return false;
    }

    /**
     * Adds a passenger to the hashtable.
     *
     * @param name       passenger's full name
     * @param passportId passenger's passport ID
     * @return true if the passenger was added successfully, false otherwise
     */
    @Override
    public boolean addPassenger(String name, String passportId) {
        // Find Index
        int i = 0;
        int hash = calculateHashCode(passportId);

        while (i<this.getTableSize()) {

            int index = compressHash(hash + i);

            // Check if index is empty

            i++;
        }

        return false;
    }

    /**
     * Counts the number of passengers in the hashtable.
     *
     * @return the number of passengers
     */
    @Override
    public int count() {
        return 0;
    }

    /**
     * Returns the bucket index of the passenger in the hashtable.
     *
     * @param passportId passenger's passport ID
     * @return bucket index of passenger in hashtable
     */
    @Override
    public int getIndex(String passportId) {
        return 0;
    }

    /* End of Built-In Functions */

    /* Start of Helper Functions */

//    private void initializeTableSize() {
//        int minM = this.getNumPassengersPerPlane() * this.getNumPlanes();
//        int prime = minM;
//        int i = minM;
//
//        while(i < MAX_CAPACITY) {
//            i++;
//            int divisibilityCount = 0;
//            for (int j=2; j<=i/2;j++) {
//                if (i%j == 0) {
//                    divisibilityCount++;
//                }
//            }
//            if (divisibilityCount==0){
//                prime = i;
//                break;
//            }
//        }
//
//        this.initTableSize = prime;
//        this.tableSize = this.initTableSize;
//        System.out.printf("%d < %d\n", minM, prime);
//    }

    public int getInitTableSize() {
        return INITIAL_TABLE_SIZE;
    }

    public int getTableSize() {
        return tableSize;
    }

    /* End of Helper Functions */

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

        db.calculateHashCode("Asb23f");

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

class Passenger {

    /* Fields */
    String name;
    String id;
    int hash;

    int numVisits;

    public Passenger(String name, String id, int hash) {
        this.name = name;
        this.id = id;
        this.hash = hash;

        this.numVisits = 0;
    }

    public String getName() {
        return name;
    }

    public String getId() {
        return id;
    }

    public int getHash() {
        return hash;
    }

    public int getNumVisits(){
        return numVisits;
    }

    public void markVisit(){
        numVisits++;
    }

}

