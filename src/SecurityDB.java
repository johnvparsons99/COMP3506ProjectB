

public class SecurityDB extends SecurityDBBase {

    /* Fields */
    /** Initial HashTable size M */
    private final int INITIAL_TABLE_SIZE = 859; //4;
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
        // System.out.println("===== Hashing =====");
        int hash = 0;
        int asciiSum = 0;

        for (int i = 0; i < key.length(); i++) {
            int ascii = (int) key.charAt(i);
            // System.out.println(ascii);
            asciiSum += ascii;
            hash += (1 + asciiSum);
        }
        // System.out.printf("HASH(%s): %d\n", key, hash);

        return hash;
    }

    public int compressHash(int hash) {
        // Compression Function
        return  hash % getTableSize();
    }

    /**
     * Returns the actual size of the hashtable, including the empty buckets.
     *
     * @return the size of the hashtable
     */
    @Override
    public int size() {
        return this.tableSize;
    }

    /**
     * Finds a passenger's name by their passport ID.
     *
     * @param passportId passenger's passport ID
     * @return the name of the person if they are in the system, otherwise null
     */
    @Override
    public String get(String passportId) {

        int index = getIndex(passportId);

        if (index < 0) {
            // Not found
            return null;
        } else {
            return passengers[index].getName();
        }
    }

    /**
     * Removes a passenger from the system.
     *
     * @param passportId passenger's passport ID
     * @return true if the passenger was deleted, false if they could not be found
     */
    @Override
    public boolean remove(String passportId) {

        int index = getIndex(passportId);

        if (index < 0) {
            // Not Found
            return false;
        } else {
            passengers[index].delete();
            return true;
        }
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
        // System.out.println("===== Adding =====");
        int hash = calculateHashCode(passportId);

        while (i<this.getTableSize()) {
            int index = compressHash(hash + i);
            // System.out.printf("INDEX: %d\n", index);

            // Check if index is empty
            if (passengers[index] == null || passengers[index].getId().equals("")) {
                passengers[index] = new Passenger(name, passportId, hash);
                // System.out.println("Success");
                return true;
            } else { //
                // Could be just a collision
                if (!passengers[index].getId().equals(passportId)) {
                    i++;
                    // System.out.println("Collision");
                } else { // Same ID so either imposter or repeat visitor
                    if (!passengers[index].getName().equals(name)) {
                        // Passenger has same ID but different name
                        // Security Alert
                        System.err.print("Suspicious behaviour");
                        return false;

                    } else if (passengers[index].getName().equals(name)) {
                        // Passenger has same ID and same name
                        // Check maximum visits
                        // System.out.println("Same Name");
                        passengers[index].markVisit();
                        if (passengers[index].getNumVisits() <= 5) {
                            // System.out.printf("Visit %d\n", passengers[index].numVisits);
                            return true;
                        } else {
                            System.err.print("Suspicious behaviour");
                            return false;
                        }
                    }
                }
            }
        }

        // If we've exited the loop then we've run out of space
        // If not at MAX_SIZE of 1021 then set to MAX_SIZE
        // Else table is full and at max size so can't add more passengers
        if (this.tableSize < MAX_CAPACITY) {
            // System.out.println("Resize");
            return resizeTable(name, passportId);
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
        int count = 0;

        for(int i=0; i<tableSize;i++) {
            if(passengers[i] != null) {
                count++;
            }
        }

        return count;
    }

    /**
     * Returns the bucket index of the passenger in the hashtable.
     *
     * @param passportId passenger's passport ID
     * @return bucket index of passenger in hashtable
     */
    @Override
    public int getIndex(String passportId) {
        // Find Index
        // System.out.println("===== Getting=====");
        int i = 0;
        int hash = calculateHashCode(passportId);

        while (i<this.getTableSize()) {
            int index = compressHash(hash + i);

            // Check if index is empty
            if (passengers[index] == null) {
                // Passenger Does not Exist
                return -1;
            } else {
                if (passengers[index].getId().equals(passportId)) {
                    // Found Passenger
                    return index;
                } else {
                    i++;
                }
            }
        }
        return -1;
    }

    /* End of Built-In Functions */

    /* Start of Helper Functions */

    public int getInitTableSize() {
        return INITIAL_TABLE_SIZE;
    }

    public int getTableSize() {
        return tableSize;
    }

    private void copyPassenger(Passenger passenger) {

        String passportId = passenger.getId();
        String name = passenger.getName();
        int numVisits = passenger.getNumVisits();

        // Find Index
        int i = 0;
        // System.out.println("===== Copying =====");
        int hash = calculateHashCode(passportId);

        while (i<this.getTableSize()) {
            int index = compressHash(hash + i);

            // Check if index is empty
            if (passengers[index] == null || passengers[index].getId().equals("")) {
                passengers[index] = new Passenger(name, passportId, hash);
                passengers[index].setNumVisits(numVisits);
                return;
            }
            i++;
        }
    }

    private boolean resizeTable(String name, String passportId){
        // Make New table
        Passenger[] oldTable = passengers;
        this.passengers = new Passenger[MAX_CAPACITY];
        int oldTableSize = tableSize;
        tableSize = MAX_CAPACITY;

        for(int i=0; i<oldTableSize; i++) {
            copyPassenger(oldTable[i]);
        }

        return addPassenger(name, passportId);
    }

    private void visualize(){
        for(int i=0; i<tableSize; i++) {
            System.out.print("|");
            if (passengers[i] != null) {
                if (passengers[i].getId().equals("")) {
                    System.out.print("000000");
                } else {
                    System.out.print(passengers[i].getId());
                }
            } else {
                System.out.print("      ");
            }
        }
        System.out.println("|");
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
//    public static void main(String[] args) {
//        System.out.println("===== Hello =====");
//        SecurityDB db = new SecurityDB(3, 2);
//
//        // add
//        db.addPassenger("Rob Bekker", "Asb23f");
//
//        // Same Name
//        //System.out.println("+++++ Same Name +++++");
//        db.addPassenger("Kira Adams", "MKSD23");
//        db.addPassenger("Kira Adams", "MKSD24");
//        db.addPassenger("Jess Smith", "MKSD2d");
//        //System.out.println("+++++ +++++ +++++");
//
//        // Collision
//        //System.out.println("+++++ Collision +++++");
//        db.visualize();
//        db.addPassenger("Rick Woodsmith", "MKSD42");
//        db.addPassenger("Ryan Holly", "SKDM24");
//        db.addPassenger("Reid Richards", "DSMK24");
//        db.addPassenger("Riley McDonald", "KSMD42");
//        //System.out.println("+++++ +++++ +++++");
//        db.visualize();
//
//        db.addPassenger("Jonah Simms", "Xfv67s");
//        db.addPassenger("David Lou", "BiD28q");
//        db.addPassenger("Sandra Lou", "rKl08r");
//        db.visualize();
//
//        System.out.println("===== DONE ADDING =====");
//        System.out.printf("Found: %s\n", db.get("SKDM24"));
//
//        db.remove("SKDM24");
//        db.visualize();
//        System.out.printf("Found: %s\n", db.get("SKDM24"));
//
//        assert db.contains("Asb23f");
//
//        System.out.println(db.calculateHashCode("Asb23f"));
//
//        // count
//        assert db.count() == 3;
//
//        // del
//        // db.remove("MKSD23");
//        assert !db.contains("MKSD23");
//        assert db.contains("Asb23f");
//
//        // hashcodes
//        assert db.calculateHashCode("Asb23f") == 1717;
//
//        // suspicious
////        db = new SecurityDB(3, 2);
////        db.addPassenger("Rob Bekker", "Asb23f");
////        db.addPassenger("Robert Bekker", "Asb23f");
//        // Should print a warning to stderr
//
//        System.out.println("===== END =====");
//    }
}

/* Add any additional helper classes here */

class Passenger {

    /* Fields */
    private String name;
    private String id;
    private int hash;

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

    public void setNumVisits(int numVisits) {this.numVisits = numVisits;}

    public void markVisit(){
        numVisits++;
    }

    public void delete() {
        this.name = "";
        this.id = "";
        this.hash = 0;
        this.numVisits = 0;
    }

}

