
import java.sql.*;
import java.sql.Date;
import java.util.*;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class EastsideUncommons{
    static final String DB_URL = "jdbc:oracle:thin:@edgar1.cse.lehigh.edu:1521:cse241";

    public static void main(String[] args) {
        Scanner scnr = new Scanner(System.in);
        Connection conn = null;
        do {
            try {
                System.out.print("Enter Oracle UserID: ");
                String username = scnr.nextLine();

                System.out.print("Enter Oracle Password: ");
                String password = scnr.nextLine();

                conn = DriverManager.getConnection(DB_URL, username, password);
                userType(conn, scnr);
            } catch (SQLException se) {
                se.printStackTrace();
                System.out.println("[Error]: Could not connect, please re-enter login data " + se);
            } finally {
                try {
                    if(conn!= null){
                        conn.close();
                    }
                } catch (SQLException e) {
                    System.out.println("[Error] Failed to close the connection: " + e.getMessage());
                }
            }
        } while (conn == null);

        if (scnr != null) {
            scnr.close();
        }
    }

    public static void userType(Connection conn, Scanner scnr){
        int userInput;
        do{
            System.out.println("Please choose the type of user you are.");
            System.out.println("1. Property Manager");
            System.out.println("2. Tenant");
            System.out.println("3. Company Manager");
            System.out.println("4. Finanical Manager");
            System.out.println("5. Exit EastsideUncommons Terminal.");

            try{
                userInput = scnr.nextInt();

                if(userInput < 1 || userInput > 5){
                    System.out.println("Please input a number from 1 to 5...");
                    continue;
                }
                    switch(userInput){
                        case 1:
                            System.out.println("Property Manager!!");
                            propertyManager(conn, scnr);
                            break;
                        case 2:
                            System.out.println("Tenant");
                            tenant(conn, scnr);
                            break;
                        case 3:
                            System.out.println("Company Manager");
                            companyManager(conn, scnr);
                            break;
                        case 4:
                            System.out.println("Financial Manager");
                            financialManager(conn, scnr);
                            break;
                        case 5:
                            System.out.println("Exited.");
                            return;
                    }
            }
            catch(InputMismatchException e){
                System.out.println("It must be an integer!" + e.getMessage());
                scnr.next();
            }
        } while(true);
    }


    // public static void testing(Connection conn){
    //     try(Statement stmt = conn.createStatement()){
    //         // SQL command to create the Visits table
    //         // SQL command to create the Payment table
    //     String sql = "CREATE TABLE Payment (" +
    //     "payment_id varchar(7) not null, " +
    //     "amount numeric(7,2), " +
    //     "transaction_date date, " +
    //     "type_new varchar(10) check (type_new in ('Credit','Debit', 'Crypto', 'Cash')), " +
    //     "lease_id varchar(7) not null, " +
    //     "primary key (payment_id), " +
    //     "foreign key (lease_id) references Lease(lease_id) on delete set null" +
    //     ")";

    //     // Execute the SQL command
    //     stmt.executeUpdate(sql);
    //     System.out.println("Table 'Visit' created successfully.");
    //     } catch(SQLException e){
    //         System.out.println(e.getMessage());
    //     }
    
    // }


    public static void testing(Connection conn){
        // // Inside your JDBC connection code
        // String[] insertQueries = {
           


        //     //... include all other insert statements
        // };



        // for(String sql: insertQueries){
        //     try(PreparedStatement pstmt = conn.prepareStatement(sql)){
        //         pstmt.executeUpdate();
        //     } catch(SQLException e){
        //         System.out.println("Could not make pStmt " + e.getMessage());
        //     }
        // }

        // System.out.println("Data inserted successfully.");

        try (Statement stmt = conn.createStatement()) {
            String sql = "ALTER TABLE Propspective_Tenant ADD DOB DATE DEFAULT TO_DATE('2000-01-01', 'YYYY-MM-DD')";
            stmt.executeUpdate(sql);
            System.out.println("Column added successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("SQL operation failed: " + e.getMessage());
        }

    }





    public static void propertyManager(Connection conn, Scanner scnr){
        int userInput;
        do{
            System.out.println("Please choose your operations");
            System.out.println("1. Record visit data");
            System.out.println("2. Record lease data");
            System.out.println("3. Record move out (set move out date also) or renew lease");
            System.out.println("4. Add a person and pet to lease");
            System.out.println("5. Exit PropertManager Terminal");
    
            try{
                userInput = scnr.nextInt();
                if(userInput < 1 || userInput > 5){
                    System.out.println("Please input a number between 1 to 5");
                    continue;
                }
                switch(userInput){
                    case 1:
                        visit(conn, scnr);
                        break;
                    case 2:
                        lease(conn, scnr);
                        break;
                    case 3:
                        
                        System.out.print("Would you like to renew your lease or move out (1 or 2): ");
                        int userChoice = 0;
                        try{
                            userChoice = scnr.nextInt();
                            if(userChoice == 1){
                                renewLease(conn, scnr);
                                break;
                            } else if (userChoice == 2){
                                moveOut(conn, scnr);
                                break;
                            } else{
                                System.out.println("Next time please input a number between 1 and 2");
                                continue;
                            }
                        } catch(InputMismatchException e){
                            System.out.println("Please input an integer " + e.getMessage());
                            scnr.next();
                        }
                        
                    case 4:
                        System.out.println("Did not have time to finish!");
                        break;
                    case 5:
                        System.out.println("Exited terminal");
                        return;
                }
            } catch(InputMismatchException e){
                System.out.println("Please input an integer.");
                scnr.next(); 
            }
        } while(true);
       

    }

    public static void tenant(Connection conn, Scanner scnr){
        int userInput;
        do{
            System.out.println("Welcome to the Tenant Terminal.");
            System.out.println("1. Check your payment status.");
            System.out.println("2. Make rental payment");
            System.out.println("3. Update personal data");
            System.out.println("4. Exit the Tenant Menu.");

            try{
                userInput = scnr.nextInt();
                if(userInput < 1 || userInput > 4){
                    System.out.println("Please input a number between 1 to 4");
                    continue;
                }

                switch(userInput){
                    case 1:
                        checkPaymentStatus(conn, scnr);
                        break;
                    case 2:
                        payRent(conn, scnr);
                        break;
                    case 3:
                        updatePersonalData(conn, scnr);
                        break;
                    case 4:
                        System.out.println("Exiting Tenant Menu");
                        return;
                }
            } catch(InputMismatchException e){
                System.out.println("Please input an integer");
                scnr.next();
            }

        } while(true);
    }


    public static void companyManager(Connection conn, Scanner scnr){
        int userInput;
        do{
            System.out.println("Welcome to the Company Terminal.");
            System.out.println("1. Add property (including adding amentities and apartments).");
            System.out.println("2. Exit Terminal.");
            try{
                userInput = scnr.nextInt();
                if(userInput < 1 || userInput > 2){
                    System.out.println("Please type in between 1 to 3");
                    continue;
                }

                switch(userInput){
                    case 1:
                        addProperties(conn, scnr);
                        break;
                    case 2:
                        System.out.println("Exiting Company Manager Terminal.");
                        return;
                }
            } catch(InputMismatchException e){
                System.out.println("InputMismatchExceptioon " + e.getMessage());
                scnr.next();
            }
        } while (true);
    }


    public static void financialManager(Connection conn, Scanner scnr){
        int userInput = 0;
        System.out.println("The Financial Terminal (read-based) to see all the different tables");
        do{
            System.out.println("Welcome to the Financial Terminal");
            System.out.println("1. View Properties");
            System.out.println("2. View Apartments");
            System.out.println("3. View Tenants ");
            System.out.println("4. View Payments");
            System.out.println("5. View Leases");
            System.out.println("6. View Propspective Tenants");
            System.out.println("7. View Visits ");
            System.out.println("8. Exit Terminals");

            try{
                userInput = scnr.nextInt();
                if(userInput < 1 || userInput > 8){
                    System.out.println("Please input between 1 to 7");
                }

                switch(userInput){
                    case 1:
                        checkProperty(conn, scnr);
                        break;
                    case 2:
                        checkApartment(conn, scnr);
                        break;
                    case 3:
                        checkTenant(conn, scnr);
                        break;
                    case 4:
                        checkPayment(conn, scnr);
                        break;
                    case 5:
                        checkLease(conn, scnr);
                        break;
                    case 6:
                        checkPropspectiveTenant(conn, scnr);
                        break;
                    case 7:
                        checkVisits(conn, scnr);
                        break;
                    case 8:
                        System.out.println("Exiting terminal.");
                        return;
                }
            } catch(InputMismatchException e){
                System.out.println("Please input an integer " + e.getMessage());
                scnr.next();
            }
        } while (true);

    }


    public static void checkProperty(Connection conn, Scanner scnr){
        try(Statement showPropertyStmt = conn.createStatement()){
            String propertyQuery  = "select * from Property";
            try(ResultSet propertyResult = showPropertyStmt.executeQuery(propertyQuery)){
                while(propertyResult.next()){
                    System.out.println("Property ID: " + propertyResult.getString("property_id") 
                    + " Address " + propertyResult.getString("address"));
                }
            } catch(SQLException e){
                System.out.println("Could not make propertyResult statement");
            }
        } catch(SQLException e){
            System.out.println("Could not make showPropertyStmt" + e.getMessage());
        }
    }


    public static void checkApartment(Connection conn, Scanner scnr){
        try(Statement showApartmentStmt = conn.createStatement()){
            String propertyQuery  = "select * from Apartment";
            try(ResultSet showApartmentResult = showApartmentStmt.executeQuery(propertyQuery)){
                while(showApartmentResult.next()){
                    System.out.println("Apartment ID: " + showApartmentResult.getString("apt_id") 
                    + " Apt Num:  " + showApartmentResult.getString("apt_num") 
                    + " Apt Size " + showApartmentResult.getDouble("apt_size") + " Bedrooms: " + showApartmentResult.getInt("bedrooms") 
                    + " Bathrooms: " + showApartmentResult.getInt("bathrooms") + "Property ID: " + showApartmentResult.getString("property_id"));
                }
            } catch(SQLException e){
                System.out.println("Could not make propertyResult statement");
            }
        } catch(SQLException e){
            System.out.println("Could not make showPropertyStmt" + e.getMessage());
        }
    }


    public static void checkTenant(Connection conn, Scanner scnr){
        try(Statement showPropertyStmt = conn.createStatement()){
            String propertyQuery  = "select * from Tenant";
            try(ResultSet propertyResult = showPropertyStmt.executeQuery(propertyQuery)){
                while(propertyResult.next()){
                    System.out.println("Tenant ID: " + propertyResult.getString("tenant_id") 
                    + " Name " + propertyResult.getString("name") + " Date of Birth: " + propertyResult.getDate("DOB"));
                }
            } catch(SQLException e){
                System.out.println("Could not make propertyResult statement");
            }
        } catch(SQLException e){
            System.out.println("Could not make showPropertyStmt" + e.getMessage());
        }
    }



    public static void checkLease(Connection conn, Scanner scnr){
        try(Statement showApartmentStmt = conn.createStatement()){
            String propertyQuery  = "select * from Lease";
            try(ResultSet showApartmentResult = showApartmentStmt.executeQuery(propertyQuery)){
                while(showApartmentResult.next()){
                    System.out.println("Lease ID: " + showApartmentResult.getString("lease_id") 
                    + " Start Date:  " + showApartmentResult.getDate("start_date") 
                    + " End Date:  " + showApartmentResult.getDate("end_date") + " Rate: " + showApartmentResult.getDouble("rate") 
                    + " Security Deposit: " + showApartmentResult.getDouble("security_deposit") + " Apt ID: " + showApartmentResult.getString("apt_id") + " Tenant ID: " + showApartmentResult.getString("tenant_id"));
                }
            } catch(SQLException e){
                System.out.println("Could not make propertyResult statement");
            }
        } catch(SQLException e){
            System.out.println("Could not make showPropertyStmt" + e.getMessage());
        }
    }


    public static void checkPayment(Connection conn, Scanner scnr){
        try(Statement showApartmentStmt = conn.createStatement()){
            String propertyQuery  = "select * from Payment";
            try(ResultSet showApartmentResult = showApartmentStmt.executeQuery(propertyQuery)){
                while(showApartmentResult.next()){
                    System.out.println("Payment ID: " + showApartmentResult.getString("payment_id") 
                    + " Amount:  " + showApartmentResult.getDouble("amount") 
                    + " Transaction Date " + showApartmentResult.getDate("transaction_date") + " Payment Type: " + showApartmentResult.getString("type_new") 
                    + " Lease ID: " + showApartmentResult.getString("lease_id"));
                }
            } catch(SQLException e){
                System.out.println("Could not make propertyResult statement");
            }
        } catch(SQLException e){
            System.out.println("Could not make showPropertyStmt" + e.getMessage());
        }
    }


    public static void checkPropspectiveTenant(Connection conn, Scanner scnr){
        try(Statement showPropertyStmt = conn.createStatement()){
            String propertyQuery  = "select * from Propspective_Tenant";
            try(ResultSet propertyResult = showPropertyStmt.executeQuery(propertyQuery)){
                while(propertyResult.next()){
                    System.out.println("Propspective Tenant ID: " + propertyResult.getString("props_id") 
                    + " Name " + propertyResult.getString("name") + " Date of Birth: " + propertyResult.getDate("DOB"));
                }
            } catch(SQLException e){
                System.out.println("Could not make propertyResult statement");
            }
        } catch(SQLException e){
            System.out.println("Could not make showPropertyStmt" + e.getMessage());
        }
    }


    public static void checkVisits(Connection conn, Scanner scnr){
        try(Statement showPropertyStmt = conn.createStatement()){
            String propertyQuery  = "select * from Visit";
            try(ResultSet propertyResult = showPropertyStmt.executeQuery(propertyQuery)){
                while(propertyResult.next()){
                    System.out.println("Visit ID: " + propertyResult.getString("visit_id") 
                    + " Apt ID: " + propertyResult.getString("apt_id") + " Visit Date: " + propertyResult.getDate("visit_date") + " Prospective Tenant ID: " + 
                    propertyResult.getString("props_id"));
                }
            } catch(SQLException e){
                System.out.println("Could not make propertyResult statement");
            }
        } catch(SQLException e){
            System.out.println("Could not make showPropertyStmt" + e.getMessage());
        }
    }



    





    public static void addProperties(Connection conn, Scanner scnr){
        String address = "";
        String property_id = "";
        // ArrayList<String> amenities_IDs = new ArrayList<>();
        do{
            System.out.println("We will add a property");
            System.out.println("Randomly generate a property ID");
            property_id = generatePaymentID(conn);
            System.out.println("Please enter the address of the property: ");
            try{
                 // Use nextLine() instead of next() to read the full line of input
                address = scnr.nextLine(); // This reads the rest of the current line. Often used to consume the leftover newline character from previous input
                address = scnr.nextLine(); // This reads the actual address input from the user
                String addPropertyQuery = "insert into Property (property_id, address) values (?, ?)";
                try(PreparedStatement addPropertyPrepStmt = conn.prepareStatement(addPropertyQuery)){
                    addPropertyPrepStmt.setString(1, property_id);
                    addPropertyPrepStmt.setString(2, address); 

                   int insertedRow = addPropertyPrepStmt.executeUpdate();

                    if(insertedRow > 0){
                        System.out.println("Let us see the property you added");
                        String viewPropertyQuery = "select * from Property where property_id = ?";
                        try(PreparedStatement seePropertyPrepStmt = conn.prepareStatement(viewPropertyQuery)){
                            seePropertyPrepStmt.setString(1, property_id);
                            try(ResultSet seePropertyResult = seePropertyPrepStmt.executeQuery()){
                                while(seePropertyResult.next()){
                                    System.out.println("Property ID: " + seePropertyResult.getString("property_id") + 
                                    " Address: " + seePropertyResult.getString("address"));
                                }
                            } catch(SQLException e){
                                System.out.println("Can't make seePropertyResult " + e.getMessage());
                            }
                        } catch(SQLException e){
                            System.out.println("Can't seePropertyPrepStmt " + e.getMessage());
                        }

                        System.out.println("Now let us add amenities to your property!");
                        
                            
                        /**I get the list of amenitites you want to add to poprerty insert it and view it after  */
                        addAmenitiesToProperty(conn, scnr, property_id);

                        /**Now let us add apartments to the property */
                        addApartments(conn, scnr, property_id);
                        /**Ask if you want to add another property */
                        String userChoice = "";
                        System.out.print("Would you like to add another property? (yes/no) ");

                        try{
                            userChoice = scnr.nextLine();
                            System.out.println("");
                            if(userChoice.equals("yes")){
                                System.out.println("Okay let's add another property!");
                                continue;
                            } else if (userChoice.equals("no")){
                                System.out.println("No worries have a good day!");
                                break;
                            } else{
                                System.out.println("I am assuiming you said no (please type better next time).");
                                break;
                            }
                        } catch(InputMismatchException e){
                            System.out.println("You did not input a string" + e.getMessage());
                            scnr.next();
                        }
                              
                    } else{
                        System.out.println("No rows were inserted.");
                        break;
                    }
                } catch(SQLException e){
                    System.out.println("Could not make addPropertyPrepStmt " + e.getMessage());
                }
            } catch(InputMismatchException e){
                System.out.println("InputMismatchException " + e.getMessage());
                scnr.next();
            }

        } while (true);
        
    }

    /**Helper method */
    public static void addAmenitiesToProperty(Connection conn, Scanner scnr, String property_id){
        ArrayList<String> amenities_IDs = new ArrayList<>();
        int userInput = 0;
        System.out.print("How many amenities would you like to add: ");
                try{
                    userInput = scnr.nextInt();
                    System.out.println("");
                    String amenity_name = "";
                    String amenity_type = "Private";
                    String charge_choice = "";
                    int cost = 0;
                    for(int i = 0; i < userInput; i++){
                        /*This is where we generate a random Amenities ID */
                        try{
                            System.out.println("Creating Amenity ID... ");
                            String amenitiy_id = generateAmenitiesID(conn);
                            amenities_IDs.add(amenitiy_id);
                            System.out.print("Please input the name of the Ameniity: ");
                            amenity_name = scnr.next();
                            System.out.println("");
                            System.out.print("Please input the type of Amenity ('Common'/ 'Private'): ");
                            amenity_type = scnr.next();
                            System.out.println("");
                            if(amenity_type.equals("Common") || amenity_type.equals("Private")){
                                System.out.print("Please input the charge_choice ('Included'/'Chargeable'): ");
                                try{
                                    charge_choice = scnr.next();
                                    System.out.println("");
                                    if(charge_choice.equals("Chargeable")){
                                        /**That means that this is chargeable let us add a fee */
                                        /* Set the cost */
                                        try{
                                            System.out.print("Please input the cost: ");
                                            cost = scnr.nextInt();
                                            System.out.println("");

                                            String insertAmenityQuery = "insert into Amenities (amenity_id, name, type, charge_choice, cost) values (?, ?, ?, ?, ?)";
                                            try(PreparedStatement insertAmenityPrepStmt = conn.prepareStatement(insertAmenityQuery)){
                                                insertAmenityPrepStmt.setString(1,amenities_IDs.get(i));
                                                insertAmenityPrepStmt.setString(2, amenity_name);
                                                insertAmenityPrepStmt.setString(3,amenity_type);
                                                insertAmenityPrepStmt.setString(4, charge_choice);
                                                insertAmenityPrepStmt.setDouble(5, cost);

                                                int insertAmenityRowsTwo = insertAmenityPrepStmt.executeUpdate();
                                                if(insertAmenityRowsTwo > 0){
                                                    /**View your amenity */
                                                    String viewAmenityQuery = "select * from Amenities where amenity_id = ?";
                                                    try(PreparedStatement viewAmenitiesPrepStmt = conn.prepareStatement(viewAmenityQuery)){
                                                        try(ResultSet viewAmenitiesResult = viewAmenitiesPrepStmt.executeQuery()){
                                                            while(viewAmenitiesResult.next()){
                                                                System.out.println("Amenity ID: " + viewAmenitiesResult.getString("amenity_id") + 
                                                                " Name: " + viewAmenitiesResult.getString("name") + " Type: " + viewAmenitiesResult.getString("type") + 
                                                                " Charge_Choice: " + viewAmenitiesResult.getString("charge_choice") +
                                                                " Cost: " + viewAmenitiesResult.getDouble("cost"));
                                                            }
                                                        
                                                        } catch(SQLException e){
                                                            System.out.println("Could not make viewAmenitiesResult " + e.getMessage());
                                                        }
                                                    } catch(SQLException e){
                                                        System.out.println("Could not make viewAmenitiesPrepStmt" + e.getMessage());
                                                    }
                                                } else{
                                                    System.out.println("No rows were submitted sorry.");
                                                    break;
                                                }

                                            } catch(SQLException e){
                                                System.out.println("Could not make insertAmenityPrepStmt " + e.getMessage());
                                            }

                                        } catch(InputMismatchException e){
                                            System.out.println("Please input a integer");
                                        }
                                        
                                    } else{
                                        /**Just for ease of purpose */
                                        charge_choice = "Included";
                                        String insertAmenityQuery = "insert into Amenities (amenity_id, name, type, charge_choice, cost) values (?, ?, ?, ?, ?)";
                                        try(PreparedStatement insertAmenityPrepStmt = conn.prepareStatement(insertAmenityQuery)){
                                            insertAmenityPrepStmt.setString(1,amenities_IDs.get(i));
                                            insertAmenityPrepStmt.setString(2, amenity_name);
                                            insertAmenityPrepStmt.setString(3,amenity_type);
                                            insertAmenityPrepStmt.setString(4, charge_choice);
                                            insertAmenityPrepStmt.setDouble(5, cost);

                                            int insertAmenityRows = insertAmenityPrepStmt.executeUpdate();
                                            if(insertAmenityRows > 0){
                                                /**View your amenity */
                                                String viewAmenityQuery = "select * from Amenities where amenity_id = ?";
                                                try(PreparedStatement viewAmenitiesPrepStmt = conn.prepareStatement(viewAmenityQuery)){
                                                    viewAmenitiesPrepStmt.setString(1, amenities_IDs.get(i));
                                                    try(ResultSet viewAmenitiesResult = viewAmenitiesPrepStmt.executeQuery()){
                                                        while(viewAmenitiesResult.next()){
                                                            System.out.println("Amenity ID: " + viewAmenitiesResult.getString("amenity_id") + 
                                                            " Name: " + viewAmenitiesResult.getString("name") + " Type: " + viewAmenitiesResult.getString("type") + 
                                                            " Charge_Choice: " + viewAmenitiesResult.getString("charge_choice") +
                                                            " Cost: " + viewAmenitiesResult.getDouble("cost"));
                                                        }
                                                        break;
                                                    } catch(SQLException e){
                                                        System.out.println("Could not make viewAmenitiesResult " + e.getMessage());
                                                    }
                                                } catch(SQLException e){
                                                    System.out.println("Could not make viewAmenitiesPrepStmt" + e.getMessage());
                                                }
                                            } else{
                                                System.out.println("No rows were submitted sorry.");
                                                break;
                                            }

                                        } catch(SQLException e){
                                            System.out.println("Could not make insertAmenityPrepStmt " + e.getMessage());
                                        }
                                    }
                                } catch(InputMismatchException e){
                                    System.out.println("Please input a string." + e.getMessage());
                                }
                            } else{ 
                                System.out.println("Please input whether it is ('Common'/'Private')!");
                                continue;
                            }

                        } catch(InputMismatchException e){
                            System.out.println("Please input a string " + e.getMessage());
                        }
                        System.out.println("");
                    }
                } catch(InputMismatchException e){
                    System.out.println("You did not input type a value of integer " + e.getMessage());
                }

                /**View the Amenities you added and add to the junction table */

                for(int i = 0; i<amenities_IDs.size(); i++){
                    System.out.println("We will now add these amenities to the Property you added");
                    String insertAmenitiesQuery = "insert into Property_Amenities (amenity_id, property_id) values (?, ?)";
                    try(PreparedStatement insertAmenitiesPrepStmt = conn.prepareStatement(insertAmenitiesQuery)){
                        insertAmenitiesPrepStmt.setString(1, amenities_IDs.get(i));
                        insertAmenitiesPrepStmt.setString(2, property_id);
                        
                        int insertedAmenitiesRows = insertAmenitiesPrepStmt.executeUpdate();
                        
                        /** We can now view the code */
                        if(insertedAmenitiesRows > 0){
                            /**We create a join statement with Amenitties Table and Property_Amenitites to show the names of the Amenitites based on property_id inputted */
                            String viewAmenitiesQuery = "select A.amenity_id, A.name, A.type, A.charge_choice, A.cost from Amenities A join Property_Amenities PA on A.amenity_id = PA.amenity_id where PA.property_id = ?";
                            try(PreparedStatement viewPropertyPrepStmt = conn.prepareStatement(viewAmenitiesQuery)){
                                viewPropertyPrepStmt.setString(1, property_id);
                                try(ResultSet viewPropertyResult = viewPropertyPrepStmt.executeQuery()){
                                    while(viewPropertyResult.next()){
                                        System.out.println("Amenity ID: " + viewPropertyResult.getString("amenity_id") + " Name: " + viewPropertyResult.getString("name") + " Type:  " 
                                        + viewPropertyResult.getString("type") + " Charge Choice: " + viewPropertyResult.getString("charge_choice") + " Cost: " + viewPropertyResult.getDouble("cost"));
                                    }
                                } catch(SQLException e){
                                    System.out.println("Could not make viewPropertyResult " + e.getMessage());
                                }
                            } catch(SQLException e){
                                System.out.println("Could not make viewPropertyPrepStmt" + e.getMessage());
                            }
                        }
                    } catch(SQLException e){
                        System.out.println("Could not make insertAmenitiesPrepStmt " + e.getMessage());
                    }
                }
    }



    public static void addApartments(Connection conn, Scanner scnr, String property_id){
        /*Now we all add apartments to the property */
        int userInput = 0;
        ArrayList<String> apt_IDs = new ArrayList<>();
        String apt_num = "";
        double apt_size = 0;
        int bedroom = 0;
        int bathroom = 0;
        Random random = new Random();
        System.out.println("Now we will add apartments to the Property!");

        System.out.print("How many apartments would you like to add.");

        try{    
            userInput = scnr.nextInt();

            for(int i = 0; i<userInput; i++){
                String newAptID = generateApartmentID(conn);
                apt_IDs.add(newAptID);
                /**Apartment Num sort of unique if it falls under a different property we can still have Apt 1 , Apt 2 etc */
                /**Choose random 4 digit numebr for apt num **/

                apt_num = String.valueOf(random.nextInt(10000 - 1000) + 1000);
               
                /**Randomize Apt size (between 500ft to 2500ft), bedroom (I am only keeping between 1 and 6 bedrooms), bathroom(I am only keeping between 1-6) */
                /*https://stackoverflow.com/questions/363681/how-do-i-generate-random-integers-within-a-specific-range-in-java (just to double check) */
                apt_size = 500 + (random.nextDouble() * 2000);
                bedroom = 1 + random.nextInt(6);
                bathroom = 1 + random.nextInt(6);

                String insertAptQuery = "insert into Apartment (apt_id, apt_num, apt_size, bedrooms, bathrooms, property_id) values (?, ?, ?, ?, ?, ?)";
                try(PreparedStatement addApartmentToPropertyPreparedStatement = conn.prepareStatement(insertAptQuery)){
                    addApartmentToPropertyPreparedStatement.setString(1, apt_IDs.get(i));
                    addApartmentToPropertyPreparedStatement.setString(2, apt_num);
                    addApartmentToPropertyPreparedStatement.setDouble(3, apt_size);
                    addApartmentToPropertyPreparedStatement.setInt(4, bedroom);
                    addApartmentToPropertyPreparedStatement.setInt(5, bathroom);
                    addApartmentToPropertyPreparedStatement.setString(6, property_id);

                    int insertedRow = addApartmentToPropertyPreparedStatement.executeUpdate();

                    if(insertedRow > 0){
                        System.out.println("Added an apartment to property.");
                    } else{
                        System.out.println("Nothing was added.");
                    }
                } catch(SQLException e){
                    System.out.println("Could not make the addApartmentToPropertyPreparedStatement "+ e.getMessage());
                }
                
            }

            /** Now we will see the apartments we created */
            String checkAptQuery = "select * from Apartment where property_id = ?";
            try(PreparedStatement checkAptPrepStmt = conn.prepareStatement(checkAptQuery)){
                checkAptPrepStmt.setString(1,property_id);
                try(ResultSet checkAptResults = checkAptPrepStmt.executeQuery()){
                    while(checkAptResults.next()){
                        System.out.println("Apartment ID: " + checkAptResults.getString("apt_id") + " Apt Num: " + 
                        checkAptResults.getString("apt_num") + " Apt Size: " + 
                         checkAptResults.getDouble("apt_size") + " Bedrooms: " + 
                         checkAptResults.getInt("bedrooms") + " Bathrooms: " + checkAptResults.getInt("bathrooms"));
                    }
                }
            } catch(SQLException e){
                System.out.println("Could not make checkAptPrepStmt " + e.getMessage());
            }
        } catch(InputMismatchException e){
            System.out.println("Please input an integer! " + e.getMessage());
        }


    }

    public static String generatePropertyID(Connection conn){
        Random random = new Random();
        String property_id;
        int randomNumberToConvert = random.nextInt(10000000 - 1000000) + 1000000;
        randomNumberToConvert = Math.abs(randomNumberToConvert);
        boolean isLeaseUnique = false;
        do{
            property_id = String.format("%d", randomNumberToConvert);
            /**Make sure it doesn't already exist in the Lease table */
            String checkLeaseQuery = "select count(*) from Property where property_id = ?";
            try(PreparedStatement checkLeasePrepStmt = conn.prepareStatement(checkLeaseQuery)){
                checkLeasePrepStmt.setString(1, property_id);
                try(ResultSet checkLeaseResult = checkLeasePrepStmt.executeQuery()){
                    checkLeaseResult.next();
                    if(checkLeaseResult.getInt(1) == 0){
                        isLeaseUnique = true;
                    }
                } catch(SQLException e){
                    System.out.println("Could not make the checkLeaseResult " + e.getMessage());
                }
            } catch(SQLException e){    
                System.out.println("Could not make the checkLeasePrepStmt " + e.getMessage());
            }
        } while(!isLeaseUnique);

        return property_id;
    }


    public static String generateAmenitiesID(Connection conn){
        Random random = new Random();
        String amenity_id;
        int randomNumberToConvert = random.nextInt(10000000 - 1000000) + 1000000;
        randomNumberToConvert = Math.abs(randomNumberToConvert);
        boolean isLeaseUnique = false;
        do{
            amenity_id = String.format("%d", randomNumberToConvert);
            /**Make sure it doesn't already exist in the Lease table */
            String checkLeaseQuery = "select count(*) from Amenities where amenity_id = ?";
            try(PreparedStatement checkLeasePrepStmt = conn.prepareStatement(checkLeaseQuery)){
                checkLeasePrepStmt.setString(1, amenity_id);
                try(ResultSet checkLeaseResult = checkLeasePrepStmt.executeQuery()){
                    checkLeaseResult.next();
                    if(checkLeaseResult.getInt(1) == 0){
                        isLeaseUnique = true;
                    }
                } catch(SQLException e){
                    System.out.println("Could not make the checkLeaseResult " + e.getMessage());
                }
            } catch(SQLException e){    
                System.out.println("Could not make the checkLeasePrepStmt " + e.getMessage());
            }
        } while(!isLeaseUnique);

        return amenity_id;
    }

    public static String generateApartmentID(Connection conn){
        Random random = new Random();
        String apt_id;
        int randomNumberToConvert = random.nextInt(10000000 - 1000000) + 1000000;
        randomNumberToConvert = Math.abs(randomNumberToConvert);
        boolean isLeaseUnique = false;
        do{
            apt_id = String.format("%d", randomNumberToConvert);
            /**Make sure it doesn't already exist in the Lease table */
            String checkLeaseQuery = "select count(*) from Apartment where apt_id = ?";
            try(PreparedStatement checkLeasePrepStmt = conn.prepareStatement(checkLeaseQuery)){
                checkLeasePrepStmt.setString(1, apt_id);
                try(ResultSet checkLeaseResult = checkLeasePrepStmt.executeQuery()){
                    checkLeaseResult.next();
                    if(checkLeaseResult.getInt(1) == 0){
                        isLeaseUnique = true;
                    }
                } catch(SQLException e){
                    System.out.println("Could not make the checkLeaseResult " + e.getMessage());
                }
            } catch(SQLException e){    
                System.out.println("Could not make the checkLeasePrepStmt " + e.getMessage());
            }
        } while(!isLeaseUnique);

        return apt_id;
    }

    




    



    public static void checkPaymentStatus(Connection conn, Scanner scnr){
        /**Entering Tenant ID to verify the user is a tenant */
        String tenant_id = "";
        String lease_id = "";
        System.out.println("Please enter your TenantID: ");
        try{
            tenant_id = scnr.next();

            /**verified tenant_ID */
            String checkTenantQuery = "select count(*) from Tenant where tenant_id = ?";
            try(PreparedStatement checkTenantPrepStmt = conn.prepareStatement(checkTenantQuery)){
                checkTenantPrepStmt.setString(1, tenant_id);
                try(ResultSet checkTenantResult = checkTenantPrepStmt.executeQuery()){
                    if(checkTenantResult.next() && checkTenantResult.getInt(1) > 0){
                        System.out.println("Verified Tenant ID.");
                    }
                } catch(SQLException e){
                    System.out.println("Could not make checkTenantResult " + e.getMessage());
                }
            } catch(SQLException e){
                System.out.println("Could not make checkTenantPrepStmt" + e.getMessage());
            }

            /**Now verify there was payment done to the lease */
            System.out.println("Now let us check your payments you made so far");
            String grabLeaseIDQuery = "select lease_id from Lease where tenant_id = ?";
            try(PreparedStatement grabLeaseIDQueryPrepStmt = conn.prepareStatement(grabLeaseIDQuery)){
                grabLeaseIDQueryPrepStmt.setString(1, tenant_id);
                try(ResultSet grabLeaseIDQueryResult = grabLeaseIDQueryPrepStmt.executeQuery()){
                    while(grabLeaseIDQueryResult.next()){
                        /**This is a one to one relationship */
                        lease_id = grabLeaseIDQueryResult.getString("lease_id");
                    }
                } catch(SQLException e){
                    System.out.println("Could not make the grabLeaseIDQueryResult"+ e.getMessage());
                }
            } catch(SQLException e){
                System.out.println("Could not make grabLeaseIDQueryPrepStmt" + e.getMessage());
            }

            String checkPaymentQuery = "select * from Payment where lease_id = ?";
            
            try(PreparedStatement checkPaymentPrepStmt = conn.prepareStatement(checkPaymentQuery)){
                checkPaymentPrepStmt.setString(1, lease_id);
                try(ResultSet checkPaymentResult = checkPaymentPrepStmt.executeQuery()){
                    while(checkPaymentResult.next()){
                        System.out.println("Payment ID: " + checkPaymentResult.getString("payment_id") + " Amount " + checkPaymentResult.getDouble("amount") 
                        + " Transaction Date: " + checkPaymentResult.getDate("transaction_date") + " Payment Type " + checkPaymentResult.getString("type_new") + 
                        " Lease ID " + checkPaymentResult.getString("lease_id"));
                    }
                } catch(SQLException e){
                    System.out.println("Couldn't make the checkPaymentResult " + e.getMessage());
                }
            } catch(SQLException e){
                System.out.println("Could not make " + e.getMessage());
            }

            /**Now verified tenant ID can I continue code here */
        } catch(InputMismatchException e){
            System.out.println("Please enter a string input" + e.getMessage());
        } 
    }

    public static void payRent(Connection conn, Scanner scnr){
        String tenant_id = "";
        String lease_id = "";
        LocalDate currentDate = LocalDate.now();
        double rate = 0;
        System.out.print("Please input your tenant ID: ");
        /**We will quickly verify the Tenant ID through Lease take two birds with one stone*/
        try{
            tenant_id = scnr.next();
            System.out.println("");
            String checkTenantIDQuery = "select count(*) from Lease where tenant_id = ?";
            try(PreparedStatement checkTenantPrepStmt = conn.prepareStatement(checkTenantIDQuery)){
                checkTenantPrepStmt.setString(1, tenant_id);
                try(ResultSet checkTenantResults = checkTenantPrepStmt.executeQuery()){
                    if(checkTenantResults.next() && checkTenantResults.getInt(1) > 0){
                        System.out.println("Verified Tenant ID.");

                        String grabRateQuery = "select rate, lease_id from Lease where tenant_id = ?";
                        try(PreparedStatement grabRatePrepStmt = conn.prepareStatement(grabRateQuery)){
                            grabRatePrepStmt.setString(1, tenant_id);
                            try(ResultSet grabRatePrepResult = grabRatePrepStmt.executeQuery()){
                                while(grabRatePrepResult.next()){
                                    rate = grabRatePrepResult.getDouble("rate");
                                    lease_id = grabRatePrepResult.getString("lease_id");
                                }
                            } catch(SQLException e){
                                System.out.println("Could not make grabRatePrepResult " + e.getMessage());
                            }
                        } catch(SQLException e){
                            System.out.println("Could not make the grabRatePrepStmt");
                        }
                         /**Now we can pay rent */
                        System.out.println("Your current rate for rent is $" + rate);
                        System.out.println("Let us check the date you have to pay rent: ");


                        /**Now let us verify if the user has a payment */

                        System.out.println("Let us verify if you ever made a payment before.");

                        String checkPaymentQuery = "select count(*) from Payment where lease_id = ?";

                        try(PreparedStatement checkPaymentPrepStmt = conn.prepareStatement(checkPaymentQuery)){
                            checkPaymentPrepStmt.setString(1, lease_id);
                            try(ResultSet checkPaymentResult = checkPaymentPrepStmt.executeQuery()){
                                if(checkPaymentResult.next() && checkPaymentResult.getInt(1) > 0){
                                    /** payment secured otherwise */
                                                /**Once verified let us get the rate of the the lease*/
                                        /**https://docs.oracle.com/javase/tutorial/datetime/iso/period.html (Duration stuff) */
                                        /**Is there no transaction date on the Payment Table  matching the monthly rent date 
                                         * Check if it is less than currentDate that means they are late in payment
                                         * However, if it is after the currentDate (3 days < more) then they have to time to pay no need to pay now
                                         * If it is equal within the range of three days before or equal they can pay 
                                         * 
                                         */
                                        String checkLastPaymentDateQuery = "select max(transaction_date) as last_payment_date from Payment where lease_id = ?";
                                        try(PreparedStatement checkLastPaymentDatePrepStmt = conn.prepareStatement(checkLastPaymentDateQuery)){
                                            checkLastPaymentDatePrepStmt.setString(1, lease_id);
                                            try(ResultSet checkLastPaymentDateResult = checkLastPaymentDatePrepStmt.executeQuery()){
                                                LocalDate lastPaymentDate = currentDate;
                                                if(checkLastPaymentDateResult.next()){
                                                    lastPaymentDate = checkLastPaymentDateResult.getDate("last_payment_date").toLocalDate();
                                                    /**In case anyhting happens */
                                                    if(lastPaymentDate != null){
                                                        int lastPaymentMonth = lastPaymentDate.getMonthValue();
                                                        int lastPaymentYear = lastPaymentDate.getYear();
                                                        int currentMonth = currentDate.getMonthValue();
                                                        int currentYear = currentDate.getYear();

                                                        /** just simple check to see if the user paid this month */
                                                        boolean ifRentPaid = (lastPaymentMonth == currentMonth) && (lastPaymentYear == currentYear);

                                                        if(ifRentPaid){
                                                            System.out.println("You already paid rent for this month");
                                                        } else{
                                                            System.out.println("It is time to pay rent. ");
                                                            paymentProcess(conn, scnr, rate, currentDate, lease_id);
                                                        }
                                                    } else{
                                                        /**payment is null */
                                                        System.out.println("No payment records have been found a bit concerning... Charging late fee");
                                                        double evenMoreLateFee = rate + 100;
                                                        paymentProcess(conn, scnr, evenMoreLateFee, currentDate, lease_id);
                                                    }
                                                } else{
                                                    /**This I need to figure out */
                                                    System.out.println("Where in the world is your payment. Charging late free");
                                                    double evenMoreLateFeeTwo = rate + 200;
                                                    paymentProcess(conn, scnr, evenMoreLateFeeTwo, currentDate, lease_id);
                                                }
                                            } catch(SQLException e){
                                                System.out.println("Could not make the checkLastPaymentDateResult " + e.getMessage());
                                            }

                                        } catch(SQLException e){
                                            System.out.println("Could not make the checkLastPaymentDatePrepStmt " + e.getMessage());
                                        }

                                } else{
                                    /** Begin your payment process */
                                    System.out.println("We have no record of you paying anything please process to payment");
                                    paymentProcess(conn, scnr, rate, currentDate, lease_id);
                                }
                            } catch(SQLException e){
                                System.out.println("Could not make checkPaymentResults  " + e.getMessage());
                            }
                        } catch(SQLException e){
                            System.out.println("Could not make checkPaymentPrepStmt " + e.getMessage());
                        }
                    }
                } catch(SQLException e){
                    System.out.println("Could not make checkTenantResults " + e.getMessage());
                }
            } catch (SQLException e){
                System.out.println("Could not make the checkTenantPrepStmt " + e.getMessage());
            }

            


        } catch(InputMismatchException e){
            System.out.println("InputMismatchException " + e.getMessage());
        }

    }

    public static void paymentProcess(Connection conn, Scanner scnr, double rate, LocalDate currentDate, String lease_id){
        String paymentMethod = "";
        System.out.print("How would you like to pay (Debit / Credit / Cash / Crypto): ");
        try{    
            paymentMethod = scnr.next();
            System.out.println("");
            if(paymentMethod.equals("Credit") || paymentMethod.equals("Debit") || paymentMethod.equals("Cash") || paymentMethod.equals("Crypto")){
                String insertPaymentQuery = "insert into Payment (payment_id, amount, transaction_date, type_new, lease_id) values (?, ?, ?, ?, ?)";
                String payment_id = generatePaymentID(conn);
                rate = -1 * rate;
                try(PreparedStatement payPreparedStatement = conn.prepareStatement(insertPaymentQuery)){
                    payPreparedStatement.setString(1, payment_id);
                    /** to represent a */
                    payPreparedStatement.setDouble(2, rate);
                    payPreparedStatement.setDate(3, Date.valueOf(currentDate));
                    payPreparedStatement.setString(4, paymentMethod);
                    payPreparedStatement.setString(5, lease_id);

                    int checkInsertedRow = payPreparedStatement.executeUpdate();

                    if(checkInsertedRow > 0){
                        String checkPaymentQuery = "select * from Payment where payment_id = ?";
                        try(PreparedStatement showPaymentTransactionPreparedStatement = conn.prepareStatement(checkPaymentQuery)){
                            showPaymentTransactionPreparedStatement.setString(1, payment_id);
                            try(ResultSet showPaymentResult = showPaymentTransactionPreparedStatement.executeQuery()){
                                while(showPaymentResult.next()){
                                    System.out.println("Payment ID: "+ showPaymentResult.getString("payment_id") + " Amount: " + showPaymentResult.getDouble("amount")
                                     + " Transaction Date: " + showPaymentResult.getDate("transaction_date") + " Payment Method: " + showPaymentResult.getString("type_new") + " Lease ID: " + showPaymentResult.getString("lease_id"));
                                }
                            } catch(SQLException e){
                                System.out.println("Could not make showPaymentResult" + e.getMessage());
                            }
                        } catch(SQLException e){
                            System.out.println("Could not make showPaymentTransactionPreparedStatement " + e.getMessage());
                        }
                    } else{
                        System.out.println("No rows has been inserted.");
                    }
                } catch(SQLException e){
                    System.out.println("Could not make the payPreparedStatement");
                }
            } else{
                System.out.println("Not a valid payment method.");
            }
        } catch(InputMismatchException e){
            System.out.println("Please input something");
        }
    }


    public static void updatePersonalData(Connection conn, Scanner scnr){
        String tenant_id = "";
        int userInput = 0;
        System.out.print("Please enter your tenant ID: ");
        try{
            tenant_id = scnr.next();
            /*Verify tenant ID */
            String checkTenantQuery = "select count(*) from Tenant where tenant_id = ?";
            try(PreparedStatement checkTenantPrepStmt = conn.prepareStatement(checkTenantQuery)){
                checkTenantPrepStmt.setString(1, tenant_id);
                try(ResultSet checkTenantResult = checkTenantPrepStmt.executeQuery()){
                    if(checkTenantResult.next() && checkTenantResult.getInt(1) > 0){
                        System.out.println("Tenant ID verified");
                    }
                } catch(SQLException e){
                    System.out.println("Could not make checkTenantResult" + e.getMessage());
                }
            } catch(SQLException e){
                System.out.println("Could not make checkTenantPrepStmt" + e.getMessage());
            }

            do{
                System.out.println("What info would you like to update: ");
                System.out.println("1. Name");
                System.out.println("2. DOB");
                System.out.println("3. Exit.");


                try{
                    userInput = scnr.nextInt();
                    if(userInput < 1 || userInput > 3){
                        System.out.println("Please type a number between 1 and 3");
                        continue;
                    }

                    switch(userInput){
                        case 1:
                            updateName(conn, scnr, tenant_id);
                            break;
                        case 2: 
                            updateDOB(conn, scnr, tenant_id);
                            break;
                        case 3: 
                            System.out.println("Okay, exited");
                            return;

                    }
                } catch(InputMismatchException e){
                    System.out.println("The input has to be an integer " + e.getMessage());
                    scnr.next();
                }

            } while(true);


        } catch(InputMismatchException e){
            System.out.println("InputMismatchException " + e.getMessage());
        }

    }


    public static void updateName(Connection conn, Scanner scnr, String tenant_id){
        String name = "";
        System.out.println("Updating name...");
        System.out.print("Please input your name: ");

        try{
            name = scnr.next();
            String updateNameQuery = "update tenant set name = ? where tenant_id = ?";
            try(PreparedStatement updateNamePrepStmt = conn.prepareStatement(updateNameQuery)){
                updateNamePrepStmt.setString(1, name);
                updateNamePrepStmt.setString(2, tenant_id);
                
                int updatedRows = updateNamePrepStmt.executeUpdate();

                if(updatedRows > 0){
                    System.out.println("The name has been updated.");
                } else{
                    System.out.println("The name has not been updated.");
                }
            } catch(SQLException e){
                System.out.println("Could not make updateNamePrepStmt " + e.getMessage());
            }

            System.out.println("Now we will see if the name updated.");
            String checkNameUpdatedQuery = "select * from Tenant where tenant_id = ?";
            try(PreparedStatement checkIfNameUpdatedPrepStmt = conn.prepareStatement(checkNameUpdatedQuery)){
                checkIfNameUpdatedPrepStmt.setString(1, tenant_id);
                try(ResultSet checkIfNameUpdatedResult = checkIfNameUpdatedPrepStmt.executeQuery()){
                    while(checkIfNameUpdatedResult.next()){
                        System.out.println("Tenant ID: " + checkIfNameUpdatedResult.getString("tenant_id") + " Name " 
                        + checkIfNameUpdatedResult.getString("name") + " Date of Birth: " 
                        + checkIfNameUpdatedResult.getDate("DOB"));
                    }
                } catch(SQLException e){
                    System.out.println("Could not make checkIfNameUpdatedResult " + e.getMessage());
                }
            } catch(SQLException e){
                System.out.println("Could not make checkIfNameUpdatedPrepStmt " + e.getMessage());
            }

        } catch(InputMismatchException e){
            System.out.println("Did not type no name." + e.getMessage());
        }

    }

    public static void updateDOB(Connection conn, Scanner scnr, String tenant_id){
        String date_of_birth = "";
        LocalDate formattedDOB = LocalDate.now();
        System.out.println("Updating Date of Birth...");
        System.out.print("Please input your Date of Birth (YYYY/MM/DD): ");

        try{
            date_of_birth = scnr.next();

            try{
                 formattedDOB = LocalDate.parse(date_of_birth, DateTimeFormatter.ofPattern("yyyy-MM-dd"));

                    String updateDOBQuery = "update tenant set DOB = ? where tenant_id = ?";
                    try(PreparedStatement updateDOBPrepStmt = conn.prepareStatement(updateDOBQuery)){
                        updateDOBPrepStmt.setDate(1, Date.valueOf(formattedDOB));
                        updateDOBPrepStmt.setString(2, tenant_id);
                        
                        int updatedRows = updateDOBPrepStmt.executeUpdate();

                        if(updatedRows > 0){
                            System.out.println("The DOB has been updated.");
                        } else{
                            System.out.println("The DOB has not been updated.");
                        }
                    } catch(SQLException e){
                        System.out.println("Could not make updateDOBPrepStmt " + e.getMessage());
                    }

                    System.out.println("Now we will see if the DOB updated.");
                    String checkDOBUpdatedQuery = "select * from Tenant where tenant_id = ?";
                    try(PreparedStatement checkIfDOBUpdatedPrepStmt = conn.prepareStatement(checkDOBUpdatedQuery)){
                        checkIfDOBUpdatedPrepStmt.setString(1, tenant_id);
                        try(ResultSet checkIfDOBUpdateResult = checkIfDOBUpdatedPrepStmt.executeQuery()){
                            while(checkIfDOBUpdateResult.next()){
                                System.out.println("Tenant ID: " + checkIfDOBUpdateResult.getString("tenant_id") + " Name " 
                                + checkIfDOBUpdateResult.getString("name") + " Date of Birth: " 
                                + checkIfDOBUpdateResult.getDate("DOB"));
                            }
                        } catch(SQLException e){
                            System.out.println("Could not make checkIfDOBUpdateResult " + e.getMessage());
                        }
                    } catch(SQLException e){
                        System.out.println("Could not make checkIfDOBUpdatedPrepStmt " + e.getMessage());
                    }
                } catch(Exception e){
                    System.out.println("Your date is not formatted correctly.");
                }

        } catch(InputMismatchException e){
            System.out.println("Did not type no name." + e.getMessage());
        }
    }

    /*Record visit data for (Property Manager)*/
    public static void visit(Connection conn, Scanner scnr){
        String userProspectiveID = "";

        System.out.print("Please enter the Prospective Tenant ID: ");
        try{
            userProspectiveID = scnr.next();
            System.out.println("");
            System.out.println(userProspectiveID);
            /**Check if the propspective tenant exist first */
            /**We are tracking count because it just has to be greater than 0 for us to know it exists */
            String checkQuery = "select count(*) from Propspective_Tenant where props_id = ?";

            /**Just making sure I can put statements in try blocks 
            https://stackoverflow.com/questions/8066501/how-should-i-use-try-with-resources-with-jdbc */
            try(PreparedStatement prepStmt = conn.prepareStatement(checkQuery)){
                prepStmt.setString(1, userProspectiveID);
                try(ResultSet result = prepStmt.executeQuery()){
                    // checking if there is a next AND if the value of result based (1) i.e the count(*) is greater than 0
                    // only then we know for sure it exists
                    if(result.next() && result.getInt(1) > 0){
                        // we then want to check if this record exist visit table or not and display all the visits if so
                        checkQuery = "select count(*) from Visit where props_id = ?";

                        try(PreparedStatement prepStmt2 = conn.prepareStatement(checkQuery)){
                            prepStmt2.setString(1, userProspectiveID);
                            try(ResultSet result2 = prepStmt2.executeQuery()){
                                if(result2.next() && result2.getInt(1) > 0){
                                    // we just display it to the terminal of all their visits
                                    String showAllVisitsQuery = "select * from Visit where props_id = ?";

                                    try(PreparedStatement showAllVisitsPrepStmt = conn.prepareStatement(showAllVisitsQuery)){
                                        showAllVisitsPrepStmt.setString(1, userProspectiveID);
                                        try(ResultSet showAllVisitsResult = showAllVisitsPrepStmt.executeQuery()){
                                            while(showAllVisitsResult.next()){
                                                // Display the results of all the visits scheduled with this props_id
                                                System.out.println("Visit ID " + showAllVisitsResult.getString("visit_id") + " Apt_ID: " + showAllVisitsResult.getString("apt_id") 
                                                + " Date: " + showAllVisitsResult.getDate("visit_date") + "Prospective Tenant ID: " + showAllVisitsResult.getString("props_id"));
                                            }
                                        } catch(SQLException e){
                                            System.out.println("Could not execute the showAllVisitsResult"+ e.getMessage());
                                        }
                                    } catch(SQLException e){
                                        System.out.println("Could not make an showAllVisitPrepStmt"+ e.getMessage());
                                    }
                                } else{
                                    // we will create a visit row to insert into 
                                    System.out.println("This user doesn't have a recorded visit let us make one!");

                                    /**Visit(visit_id, props_id, visit_date, apt_id)*/
                                    try{
                                        String visit_id = generateRandomVisitID(conn);
                                        /**https://www.javatpoint.com/java-get-current-date (How to get the current date) */
                                        /**https://www.javatpoint.com/java-sql-date  (for ease of formatting)* (both of these go hand in hand)*/
                                        /**FIX THIS WE WANT TO MAKE SURE THEY CAN SCEDULE A VISIT ---------------------------------------- */
                                        LocalDate currentDate = LocalDate.now();
                                        Date visit_date = Date.valueOf(currentDate);
                                        /**Ask for the apt they would like to visit*/
                                        System.out.print("Please input the apt you would like to visit (provide the apt_id): ");
                                        
                                        String apt_id = scnr.next();
                                        System.out.println("");

                                        /**Check if it exists */
                                        String checkApt_IDQuery = "select count(*) from Apartment where apt_id = ?";

                                        try(PreparedStatement prepAptStmt = conn.prepareStatement(checkApt_IDQuery)){
                                            prepAptStmt.setString(1, apt_id);
                                            try(ResultSet checkAptResult  = prepAptStmt.executeQuery()){
                                                if(checkAptResult.next() && checkAptResult.getInt(1) > 0){
                                                    /**Now we proceed to make the insertion statement to the visit table */
                                                    /**https://mkyong.com/jdbc/jdbc-preparestatement-example-insert-a-record/ (quick refresher how to do that) */
                                                    String insertIntoVisitQuery = "insert into Visit (visit_id, apt_id, visit_date, props_id) values (?, ?, ?, ?)";
                                                    try(PreparedStatement insertIntoVisitStmt = conn.prepareStatement(insertIntoVisitQuery)){
                                                        insertIntoVisitStmt.setString(1, visit_id);
                                                        insertIntoVisitStmt.setString(2, apt_id);
                                                        insertIntoVisitStmt.setDate(3, visit_date);
                                                        insertIntoVisitStmt.setString(4, userProspectiveID);
                                                        /**https://www.enterprisedb.com/docs/jdbc_connector/latest/06_executing_sql_commands_with_executeUpdate()/ (ExecuteUpdate)
                                                            wanted more information about it
                                                         */
                                                         /**https://stackoverflow.com/questions/32480738/error-in-executeupdate-statement-in-java-netbeans ".executeUpdate() returns an integer if 
                                                         it is sucessful or not" (this is how I will error check) */
                                                         /**"An int to how many rows were affected , so if 0 ROWS were affected it didnt do anything!! 
                                                         https://learn.microsoft.com/en-us/sql/connect/jdbc/reference/executeupdate-method-java-lang-string-sqlserverstatement?view=sql-server-ver16" */
                                                        int visitRow = insertIntoVisitStmt.executeUpdate();
                                                        if(visitRow > 0){
                                                            System.out.println("Successfully inserted visit row to the Visit Table");
                                                            String checkVisitQuery = "select * from Visit where visit_id = ?";
                                                            try(PreparedStatement checkVisitStmt = conn.prepareStatement(checkVisitQuery)){
                                                                checkVisitStmt.setString(1,visit_id);
                                                                try(ResultSet checkVisitResult = checkVisitStmt.executeQuery()){
                                                                    while(checkVisitResult.next()){
                                                                        System.out.println("Visit ID: " + checkVisitResult.getString("visit_id") + " Apt ID: " 
                                                                        + checkVisitResult.getString("apt_id") + " Visit Date: " + checkVisitResult.getDate("visit_date") + " Propspective Tenant ID: " + checkVisitResult.getString("props_id"));
                                                                    }
                                                                } catch(SQLException e){
                                                                    System.out.println("Could not make checkVisitResult" + e.getMessage());
                                                                }
                                                            } catch(SQLException e){
                                                                System.out.println("Could not make checkVisitStmt " + e.getMessage());
                                                            }
                                                        } else{
                                                            System.out.println("Did not sucessfully insert visit row to the Visit Table");
                                                        }


                                                    } catch(SQLException e){
                                                        System.out.println("Failed to make insert to visit preparedStatement" + e.getMessage());
                                                    }
                                                } else{
                                                    System.out.println("Apartment ID doesn't exist sorry.");
                                                }
                                            } catch(SQLException e){
                                                System.out.println("Failed to make checkAptResult" + e.getMessage());
                                            }
                                        } catch(SQLException e){
                                            System.out.println("Failed to make checkAptID prepared statement" + e.getMessage());
                                        }


                                    } catch(SQLException e){
                                        System.out.println("Failed to record visit data");
                                    }
                                }
                            } catch(SQLException e){
                                System.out.println("Failed to grab the query from the Visit's table"+ e.getMessage());
                            }
                        } catch(SQLException e){
                            System.out.println("Failed to make preparedStatement for second check query" + e.getMessage());
                        }
                    } else{
                        System.out.println("The Propspective Tenant is not found. No ability to record visit data.");
                    }
                }catch(SQLException e){
                    System.out.println("Failed to grab to grab the query from the Propspective_Tenant's table based on the given propspectiveID" + e.getMessage());
                }
            } catch(SQLException e){
                System.out.println("Failed to make prepareStatement. " + e.getMessage());
            }

        } catch(InputMismatchException e){
            System.out.println("You did not enter a proper String value for the ID" + e.getMessage());
            scnr.next();
        }
    
    }


    public static String generateRandomVisitID(Connection conn) throws SQLException{
        Random random = new Random();
        String visit_id;
        // https://stackoverflow.com/questions/5271598/java-generate-random-number-between-two-given-values
        // quick refresher on how to use generate random between 1,000,000 (7 digits) *included* - 10,000,000(8 digits) *excluded*
        int randomNumberToConvert = random.nextInt(10000000 - 1000000) + 1000000;
        // we need to make sure it is unique otherwise it will be problem (had to add SQLException error forgot to do that)
        randomNumberToConvert = Math.abs(randomNumberToConvert);
        boolean isVisitID_Unique = false;
        do{
            /**
                I found the String.format() easiest to use needed a quick recap 
                https://www.educative.io/answers/how-to-convert-an-integer-to-a-string-in-java?utm_campaign=interview_prep&utm_source=google&utm_medium=ppc&utm_content=pmax&utm_term=&eid=5082902844932096&utm_term=&utm_campaign=%5BNew-Oct+23%5D+Performance+Max+-+Coding+Interview+Patterns&utm_source=adwords&utm_medium=ppc&hsa_acc=5451446008&hsa_cam=20684486602&hsa_grp=&hsa_ad=&hsa_src=x&hsa_tgt=&hsa_kw=&hsa_mt=&hsa_net=adwords&hsa_ver=3&gad_source=1&gclid=CjwKCAiA04arBhAkEiwAuNOsInJbYqvBE7TO_cHX7TeOJCt_By4w-eGBHkuThoV1DqLtBiIyypUHWBoCU-MQAvD_BwE   
             */
             // here is our randomly generated varchar(7) visit_id
                visit_id = String.format("%d", randomNumberToConvert);
                /**But check the table to make sure if this visit_id already exist and we can't return this visit_id until it is unique */
                /**Remember we use count(*) because it won't return error, rather it will always display something */
                String checkVisitID_query = "select count(*) from Visit where visit_id = ?";
                try(PreparedStatement prepStmt = conn.prepareStatement(checkVisitID_query)){
                    prepStmt.setString(1, visit_id);
                    try(ResultSet result = prepStmt.executeQuery()){
                        result.next();
                        if(result.getInt(1) == 0){
                            isVisitID_Unique = true; // the loop will stop since it stops when it is false
                        }
                    }
                } 
        } while (!isVisitID_Unique);

        return visit_id;
    }


    /**Record lease data */


    public static void lease(Connection conn, Scanner scnr){
        /**Let first set up if the user just wants to view the lease details */
        /**Similar setup to Visit */
        int userChoice;

        // We should if this person is a tenant then we just double check to see their lease details
        // Or the person is a perspective tenant who will add their name to the lease if an apt is available (probably create a apt on the spot just for simplicity)
        System.out.print("Do you want to view a tenants lease details press (1) or add a propspective tenant to lease press (2): ");

        try{
            userChoice = scnr.nextInt();
            System.out.println("");
            if(userChoice == 1){
                    String userTenantID = "";
                    /**Let's see if there is a tenant associated with the Lease and just display that first */
                    System.out.println("Please enter the user's tenant ID: ");
                    try{
                        userTenantID = scnr.next();
                        System.out.println("");
                        String checkTenantQuery = "select count(*) from Lease where tenant_id = ?";
                        try(PreparedStatement prepStmt = conn.prepareStatement(checkTenantQuery)){
                            prepStmt.setString(1, userTenantID);
                            try(ResultSet result = prepStmt.executeQuery()){
                                /**Tenant Exist just display the data*/
                                if(result.next() && result.getInt(1) > 0){
                                    String showLeaseDetailsQuery = "select * from Lease where tenant_id = ?";
                                    try(PreparedStatement showLeaseDetailsStmt = conn.prepareStatement(showLeaseDetailsQuery)){
                                        showLeaseDetailsStmt.setString(1, userTenantID);
                                        try(ResultSet showLeaseResult = showLeaseDetailsStmt.executeQuery()){
                                            while(showLeaseResult.next()){
                                                System.out.println("Lease_ID: " + showLeaseResult.getString("lease_id") + " Start Date: " + showLeaseResult.getDate("start_date") + " End Date: " + showLeaseResult.getDate("end_date") + 
                                                " Rate: " + showLeaseResult.getInt("rate") + " Security Deposit " + showLeaseResult.getInt("security_deposit") + " Apt ID: " +
                                                showLeaseResult.getString("apt_id") + " Tenant ID: " + showLeaseResult.getString("tenant_id"));
                                            }
                                        } catch(SQLException e){
                                            System.out.println("Failed to execute showLeaseDetails" + e.getMessage());
                                        }
                                    } catch(SQLException e){
                                        System.out.println("Failed to make prepareStatement for showing Lease details "+ e.getMessage());
                                    }
                                } else{
                                    System.out.println("You don't have a lease associated with your TenantID (doesn't exist)");
                                }
                            } catch(SQLException e){
                                System.out.println("Couldn't make the ResultSet for checkingTenantQuery" + e.getMessage());
                            }
                        } catch(SQLException e){
                            System.out.println("Could not make the the checkTenant Prepared Statement" + e.getMessage());
                        }
                    } catch(InputMismatchException e){
                        System.out.println("Error or nothing was typed " + e.getMessage());
                    }
            } 
            
            else if (userChoice == 2){
                String userProspectiveID = "";
                System.out.print("Please input the user's propspective tenant ID: ");
                try{
                    /**Verify this is the propspective tenant ID */
                    // System.out.println("Would the propspective tenant like to sign the lease on?");
                    userProspectiveID = scnr.next();
                    System.out.println("");
                    String checkPropspectiveQuery  = "select count(*) from Propspective_Tenant where props_id = ?";
                    try(PreparedStatement checkPropspectivePrepStmt = conn.prepareStatement(checkPropspectiveQuery)){
                        checkPropspectivePrepStmt.setString(1, userProspectiveID);
                        try(ResultSet checkPropspectiveResult  = checkPropspectivePrepStmt.executeQuery()){
                            /** this means the propspective ID exist */
                            if(checkPropspectiveResult.next() && checkPropspectiveResult.getInt(1) > 0){
                                System.out.print("Please input the apartment the Propspective Tenant is interested in: ");
                                String apt_id = scnr.next();
                                System.out.println("");
                                /**We need to check this is the apartment */
                                String checkAptExistQuery = "select count(*) from Apartment where apt_id = ?";
                                try(PreparedStatement checkAptExistPrepStmt = conn.prepareStatement(checkAptExistQuery)){
                                    checkAptExistPrepStmt.setString(1, apt_id);
                                    try(ResultSet checkAptExistResult = checkAptExistPrepStmt.executeQuery()){
                                        if(checkAptExistResult.next() && checkAptExistResult.getInt(1) > 0){
                                            /**We then ask if they are ready to accept the apartment by changing their deny to 
                                             * accept */
                                            System.out.print("Is the Propspective Tenant ready to accept their apt? (yes/no): ");
                                            String confirm = scnr.next();
                                            System.out.println("");
                                            /**That means the user said yes */
                                            if("yes".equalsIgnoreCase(confirm)){
                                                /**Update the apt the Prospective Tenant interested in */
                                                System.out.println("Updating value of interested Apartment to the Prospective Tenant row.");
                                                String updateAptInPTQuery = "update Propspective_Tenant set apt_id = ? where props_id = ?";
                                                try(PreparedStatement updateAptInPTPrepStmt = conn.prepareStatement(updateAptInPTQuery)){
                                                    updateAptInPTPrepStmt.setString(1, apt_id);
                                                    updateAptInPTPrepStmt.setString(2, userProspectiveID);

                                                    int updateAptInPTRow = updateAptInPTPrepStmt.executeUpdate();
                                                    if(updateAptInPTRow > 0){
                                                        addToLease(conn, userProspectiveID, apt_id, scnr);
                                                    } else{
                                                        System.out.println("Was not able to update the apartment to the Prospective Tenant Table");
                                                    }
                                                } catch(SQLException e){
                                                    System.out.println("Could not make updateAptInPTPrepStmt "+ e.getMessage());
                                                }
                                                
                                            } else{
                                                System.out.println("Okay no worries have a great day!");
                                            }
                                            /**grab the important details from Propspective Tenant before deleting */
                                            /**Then deleting the row from Propspective_Tenant  */
                                            /**Add the details to the lease add that row and also details to the tenant row */
                                        } else{
                                            System.out.println("It seems like this apartment doesn't exist.");
                                        
                                        }
                                    } catch(SQLException e){
                                        System.out.println("Failed to make checkAptExistResult statement");
                                    }
                                } catch(SQLException e){
                                    System.out.println("Failed to make the checkAptExistPrepStatement");
                                }
                                
                            }   
                        } catch(SQLException e){
                            System.out.println("Failed to make checkProspectiveResult" + e.getMessage());
                        }
                    } catch(SQLException e){
                        System.out.println("Failed to make the checkProspectivePrepStmt" + e.getMessage());
                    }


                    /**We might need to make a new apartment row on the spot lol if the apartment doesn't exist */
                    /**We want to add amenities to their lease if they ask */
                } catch(InputMismatchException e){
                    System.out.println("Please input " + e.getMessage());
                }
            } else{
                System.out.println("Please input 1 or 2");
            }
        } catch(InputMismatchException e){
            System.out.println("Please input an integer. " + e.getMessage());
        }
        
    }

    /**Double check if we actually need the  */
    // public static void creatApartment(Connection conn) throws SQLException{
    //         /**We will generate apt_id similar to how we did the generation for the visit id */
    //         Scanner scnr = new Scanner(System.in);
    //         Random random = new Random();
    //         int randomNumberToConvert = random.nextInt(10000000 - 1000000) + 1000000;
    //         boolean isAptID_Unique = false;
    //         /**Because we are randomly generating another apt_id ourselves we have to do this check */
    //         String apt_id;
    //         do{
    //             apt_id = String.format("%d", randomNumberToConvert);
    //             String aptCheckIDQuery = "select count(*) from Apartment where apt_id = ?";
    //             try(PreparedStatement aptCheckIDPrepStmt = conn.prepareStatement(aptCheckIDQuery)){
    //                 aptCheckIDPrepStmt.setString(1, apt_id);
    //                 try(ResultSet aptCheckIDResult = aptCheckIDPrepStmt.executeQuery()){
    //                     aptCheckIDResult.next();
    //                     if(aptCheckIDResult.getInt(1) == 0){
    //                         isAptID_Unique = true;
    //                     }
    //                 }   
    //             }

    //         } while(!isAptID_Unique);


    // }

    /**I am not going to throws SQLException here too big of a function need to keep track of all the catches! */
    public static void addToLease(Connection conn, String userProspectiveID, String apt_id, Scanner scnr){
        /**Update Status to accept */
        String updateStatusQuery = "update Propspective_Tenant SET status = 'Accept' where props_id = ? and apt_id = ?";
        String lease_id ="";
        String name = "";
        LocalDate currentDate = LocalDate.now();
        LocalDate startDate = LocalDate.now();
        LocalDate endDate = LocalDate.now();
        LocalDate date_of_birth = LocalDate.now();
        String paymentType = "Debit";

        try(PreparedStatement updateStatusStmt = conn.prepareStatement(updateStatusQuery)){
            updateStatusStmt.setString(1, userProspectiveID);
            updateStatusStmt.setString(2, apt_id);

            int row = updateStatusStmt.executeUpdate();

            if(row > 0){
                String viewRecentQuery = "select * from Propspective_Tenant where props_id = ? and apt_id = ?";
                try(PreparedStatement verifyStmt = conn.prepareStatement(viewRecentQuery)){
                    verifyStmt.setString(1,userProspectiveID);
                    verifyStmt.setString(2,apt_id);
                    try(ResultSet verifyStmtResult = verifyStmt.executeQuery()){
                        while(verifyStmtResult.next()){
                            name = verifyStmtResult.getString("name");
                            date_of_birth = verifyStmtResult.getDate("DOB").toLocalDate();
                            System.out.println("Prop_ID: " + verifyStmtResult.getString("props_id") + " Name: "+ verifyStmtResult.getString("name") 
                            + "Date of Birth: " + verifyStmtResult.getDate("DOB") + " Status " + verifyStmtResult.getString("status") + " Apartment ID: " + verifyStmtResult.getString("apt_id"));
                        }
                    } catch(SQLException e){
                        System.out.println("Could not make a veirfyStmt result " + e.getMessage());
                    }


                } catch(SQLException e){
                    System.out.println("Can't prepare verifyStmt query " + e.getMessage());
                }

                    /**Make sure the row was deleted */
                       System.out.println("Now it is time to add to the Lease and Tenant.");
                       /**Insert into Lease and Tenant */
                       lease_id = generateLeaseID(conn);
                       /**What if we use the same propspective ID for the tenant ID so we can track it easier idk  */
                       /**The start date has to be greater than the current date */
                       do{
                            System.out.print("Please input the date you would like to start (lease will end one year later) (YYYY-MM-DD): ");
                            String dateInput = scnr.next();
                            System.out.println("");
                            try{ 
                                startDate = LocalDate.parse(dateInput); 
                                /**Local Date is awesome it also has a method called beforeDate 
                                 * so I can check if it before the current date they have to input again */
                                if(startDate.isAfter(currentDate) || startDate.isEqual(currentDate)){
                                        /**There is also a plusYear method for LocalDate classso we can add a year */
                                        endDate = startDate.plusYears(1);
                                        break;
                                } else{
                                    System.out.println("The date has to be either the current date or after");
                                    continue;
                                }
                            /*https://docs.oracle.com/javase/8/docs/api/java/time/format/DateTimeParseException.html */
                            } catch(DateTimeParseException e){
                                System.out.println("Invalid date format (YYYY-MM-DD)" + e.getMessage());
                                continue;
                            }
                       } while(true);

                       /**Make this person a tenant */

                       System.out.println("We will now make the propestive tenant (" + userProspectiveID + ") a tenant now!");

                       String insertToTenantQuery = "insert into Tenant (tenant_id, name, DOB) values (?, ?, ?)";
                       try(PreparedStatement insertToTenantPrepStmt = conn.prepareStatement(insertToTenantQuery)){
                            insertToTenantPrepStmt.setString(1, userProspectiveID);
                            insertToTenantPrepStmt.setString(2, name);
                            insertToTenantPrepStmt.setDate(3, Date.valueOf(date_of_birth));

                            int insertToTenantRow = insertToTenantPrepStmt.executeUpdate();

                            if(insertToTenantRow > 0){
                                /**Let us view if it exists */
                                    String viewTenantQuery = "select * from Tenant where tenant_id = ?";
                                        try(PreparedStatement viewTenantPrepStmt = conn.prepareStatement(viewTenantQuery)){
                                        viewTenantPrepStmt.setString(1, userProspectiveID);
                                        try(ResultSet viewTenantResult = viewTenantPrepStmt.executeQuery()){
                                            while(viewTenantResult.next()){
                                                System.out.println("Tenant ID: " + viewTenantResult.getString("tenant_id") + " Name: " + viewTenantResult.getString("name") 
                                                +  " Date Of Birth: " + viewTenantResult.getDate("DOB"));
                                            }
                                        } catch(SQLException e){
                                            System.out.println("Could not make viewTenantResults " + e.getMessage());
                                        }
                                } catch(SQLException e){
                                    System.out.println("Could not make viewTenantPrepStmt " + e.getMessage());
                                }

                                /** Now we insert into Lease */

                                String insertToLeaseQuery  = "insert into Lease (lease_id, start_date, end_date, rate, security_deposit, apt_id, tenant_id) values (?, ?, ?, ?, ?, ?, ?)";

                                try(PreparedStatement insertToLeasePrepStmt = conn.prepareStatement(insertToLeaseQuery)){
                                        insertToLeasePrepStmt.setString(1, lease_id);
                                        insertToLeasePrepStmt.setDate(2, Date.valueOf(startDate));
                                        insertToLeasePrepStmt.setDate(3, Date.valueOf(endDate));
                                        insertToLeasePrepStmt.setDouble(4, 590.00);
                                        insertToLeasePrepStmt.setDouble(5, 590.00);
                                        insertToLeasePrepStmt.setString(6, apt_id);
                                        insertToLeasePrepStmt.setString(7,userProspectiveID);


                                        int insertToLeaseRow = insertToLeasePrepStmt.executeUpdate();

                                        if(insertToLeaseRow > 0){
                                                  /** View the Lease to confirm*/
                                                String viewLeaseQuery = "select * from Lease where lease_id = ?";

                                                try(PreparedStatement viewLeasePrepStmt = conn.prepareStatement(viewLeaseQuery)){
                                                    viewLeasePrepStmt.setString(1, lease_id);
                                                    try(ResultSet viewLeaseResult = viewLeasePrepStmt.executeQuery()){
                                                        while(viewLeaseResult.next()){
                                                            System.out.println("Lease ID: " + viewLeaseResult.getString("lease_id") + " Start Date: : " + viewLeaseResult.getDate("start_date") +  " End Date: " 
                                                            + viewLeaseResult.getDate("end_date") + " Rate " + viewLeaseResult.getDouble("rate") + " Security Deposit " + 
                                                            viewLeaseResult.getDouble("security_deposit") + " Apartment ID: " +  
                                                            viewLeaseResult.getString("apt_id") + " Tenant ID: " + viewLeaseResult.getString("tenant_id"));
                                                        }
                                                    } catch(SQLException e){
                                                        System.out.println("Could not make viewLeaseResults " + e.getMessage());
                                                    }
                                                } catch(SQLException e){
                                                    System.out.println("Could not make viewLeasePrepStmt " + e.getMessage());
                                                }
                                                /** Now insert into Payment */

                                                   /**They have to make a payment */
                                                String paymentID = generatePaymentID(conn);
                                                System.out.println("Let us secure your payment with your security deposit ");

                                                do{
                                                        System.out.print("Please tell us your payment method (Credit / Debit / Cash / Crypto) *Case sensitive*: ");

                                                        try{
                                                            paymentType = scnr.next();
                                                            System.out.println("");
                                                            if(paymentType.equals("Credit") || paymentType.equals("Debit") || paymentType.equals("Cash") || paymentType.equals("Crypto")){
                                                                break;
                                                            } else{
                                                                System.out.println("Please input the CORRECT payment method");
                                                                continue;
                                                            }
                                                        } catch(InputMismatchException e){
                                                            System.out.println("Please type a String input " + e.getMessage());
                                                        }
                                                } while(true);



                                                String insertLeaseSDQuery = "insert into Payment (payment_id, amount, transaction_date, type_new, lease_id) values (?, ?, ?, ?, ?)";

                                                try(PreparedStatement insertLeaseSDPrepStmt = conn.prepareStatement(insertLeaseSDQuery)){
                                                     insertLeaseSDPrepStmt.setString(1, paymentID);
                                                     insertLeaseSDPrepStmt.setDouble(2, -590.00);
                                                     insertLeaseSDPrepStmt.setDate(3, Date.valueOf(currentDate));
                                                     insertLeaseSDPrepStmt.setString(4, paymentType);
                                                     insertLeaseSDPrepStmt.setString(5,lease_id);
                         
                                                     int insertLeaseSDRows = insertLeaseSDPrepStmt.executeUpdate();
                         
                                                     if(insertLeaseSDRows > 0){
                                                         System.out.println("Insert into Payment sucessful.");
                                                        /**View Payment */
                                                        String viewPaymentQuery = "select * from Payment where payment_id = ?";

                                                        try(PreparedStatement viewPaymentPrepStmt = conn.prepareStatement(viewPaymentQuery)){
                                                                viewPaymentPrepStmt.setString(1, paymentID);
                                                                try(ResultSet viewPaymentResult = viewPaymentPrepStmt.executeQuery()){
                                                                    while(viewPaymentResult.next()){
                                                                        System.out.println("Payment ID: " + viewPaymentResult.getString("payment_id") + " Amount: "
                                                                        + viewPaymentResult.getDouble("amount") + " Transaction Date: " + viewPaymentResult.getDate("transaction_date") +  " Payment Type " + 
                                                                        viewPaymentResult.getString("type_new") +  " Lease ID: " + viewPaymentResult.getString("lease_id"));
                                                                    }
                                                                }
                                                        } catch(SQLException e){
                                                            System.out.println("Could not make viewPaymentPrepStmt " + e.getMessage());
                                                        }

                                                     } else{
                                                         System.out.println("Insert into Payment NOT sucessful.");
                                                     }
                         
                                                } catch(SQLException e){
                                                    System.out.println("Could not make insertLeaseSDPrepStmt " + e.getMessage());
                                                }


                                        } else{
                                            System.out.println("Did not insert into Lease properly");
                                        }
                                } catch(SQLException e){
                                    System.out.println("Could not make insertToLeasePrepStmt " + e.getMessage());
                                }
                            } else{
                                System.out.println("No rows has been updated");
                            }
                       } catch(SQLException e){
                           System.out.println("Couldn't make insertToTenantPrepStmt " + e.getMessage());
                       }

                       




            } else{
                System.out.println("No update could be made please make sure you inputed the right userProspectiveID and apt_id");
            }

        } catch(SQLException e){
            System.out.println("Failed to make updateStatusStmt." + e.getMessage());
        }
    }

    public static String generateLeaseID(Connection conn){
        Random random = new Random();
        String lease_id;
        int randomNumberToConvert = random.nextInt(10000000 - 1000000) + 1000000;
        randomNumberToConvert = Math.abs(randomNumberToConvert);
        boolean isLeaseUnique = false;
        do{
            lease_id = String.format("%d", randomNumberToConvert);
            /**Make sure it doesn't already exist in the Lease table */
            String checkLeaseQuery = "select count(*) from Lease where lease_id = ?";
            try(PreparedStatement checkLeasePrepStmt = conn.prepareStatement(checkLeaseQuery)){
                checkLeasePrepStmt.setString(1, lease_id);
                try(ResultSet checkLeaseResult = checkLeasePrepStmt.executeQuery()){
                    checkLeaseResult.next();
                    if(checkLeaseResult.getInt(1) == 0){
                        isLeaseUnique = true;
                    }
                } catch(SQLException e){
                    System.out.println("Could not make the checkLeaseResult " + e.getMessage());
                }
            } catch(SQLException e){    
                System.out.println("Could not make the checkLeasePrepStmt " + e.getMessage());
            }
        } while(!isLeaseUnique);

        return lease_id;
    }



    public static String generatePetID(Connection conn){
        Random random = new Random();
        String pet_id;
        int randomNumberToConvert = random.nextInt(10000000 - 1000000) + 1000000;
        randomNumberToConvert = Math.abs(randomNumberToConvert);
        boolean isLeaseUnique = false;
        do{
            pet_id = String.format("%d", randomNumberToConvert);
            /**Make sure it doesn't already exist in the Lease table */
            String checkLeaseQuery = "select count(*) from Pet where pet_id = ?";
            try(PreparedStatement checkLeasePrepStmt = conn.prepareStatement(checkLeaseQuery)){
                checkLeasePrepStmt.setString(1, pet_id);
                try(ResultSet checkLeaseResult = checkLeasePrepStmt.executeQuery()){
                    checkLeaseResult.next();
                    if(checkLeaseResult.getInt(1) == 0){
                        isLeaseUnique = true;
                    }
                } catch(SQLException e){
                    System.out.println("Could not make the checkLeaseResult " + e.getMessage());
                }
            } catch(SQLException e){    
                System.out.println("Could not make the checkLeasePrepStmt " + e.getMessage());
            }
        } while(!isLeaseUnique);

        return pet_id;
    }



    public static String generateTenantID(Connection conn){
        Random random = new Random();
        String tenant_id;
        int randomNumberToConvert = random.nextInt(10000000 - 1000000) + 1000000;
        randomNumberToConvert = Math.abs(randomNumberToConvert);
        boolean isLeaseUnique = false;
        do{
            tenant_id = String.format("%d", randomNumberToConvert);
            /**Make sure it doesn't already exist in the Lease table */
            String checkLeaseQuery = "select count(*) from Tenant where tenant_id = ?";
            try(PreparedStatement checkLeasePrepStmt = conn.prepareStatement(checkLeaseQuery)){
                checkLeasePrepStmt.setString(1, tenant_id);
                try(ResultSet checkLeaseResult = checkLeasePrepStmt.executeQuery()){
                    checkLeaseResult.next();
                    if(checkLeaseResult.getInt(1) == 0){
                        isLeaseUnique = true;
                    }
                } catch(SQLException e){
                    System.out.println("Could not make the checkLeaseResult " + e.getMessage());
                }
            } catch(SQLException e){    
                System.out.println("Could not make the checkLeasePrepStmt " + e.getMessage());
            }
        } while(!isLeaseUnique);

        return tenant_id;
    }


    public static void renewLease(Connection conn, Scanner scnr){
        String lease_id = "";
        String payment_id = "";
        ArrayList<String> tenant_IDs = new ArrayList<>();
        LocalDate endDate = LocalDate.now();
        LocalDate newStartDate = LocalDate.now();
        LocalDate currentDate = LocalDate.now();
        double rate = 0;
        double security_deposit = 0;
        String apt_id = "";
        /** grab all the teants associated with the Lease store that value and then create a new Lease with those values */
        System.out.print("To begin the process of renewing Lease we first need to retrieve the original Lease ID: ");

        try{
            lease_id = scnr.next();
            System.out.println("");
            String checkLeaseIDQuery = "select count(*) from Lease where lease_id = ?";
            try(PreparedStatement checkLeasePrepStmt = conn.prepareStatement(checkLeaseIDQuery)){
                checkLeasePrepStmt.setString(1, lease_id);
                try(ResultSet checkLeaseResult = checkLeasePrepStmt.executeQuery()){
                    /** That means the lease_id already exists */
                    if(checkLeaseResult.next() && checkLeaseResult.getInt(1) > 0){
                        String grabLeaseDataQuery = "select * from Lease where lease_id = ?";
                        try(PreparedStatement grabLeaseDataPrepStmt = conn.prepareStatement(grabLeaseDataQuery)){
                            grabLeaseDataPrepStmt.setString(1, lease_id);
                            try(ResultSet grabLeaseDataResult = grabLeaseDataPrepStmt.executeQuery()){
                                while(grabLeaseDataResult.next()){
                                    /** Now we have all the tenant_IDs  */
                                    endDate = grabLeaseDataResult.getDate("end_date").toLocalDate();
                                    rate = grabLeaseDataResult.getDouble("rate");
                                    apt_id = grabLeaseDataResult.getString("apt_id");
                                    security_deposit = grabLeaseDataResult.getDouble("security_deposit");
                                    tenant_IDs.add(grabLeaseDataResult.getString("tenant_id"));
                                } 

                            } catch(SQLException e){
                                System.out.println("Could not make the grabLeaseDataResult" + e.getMessage());
                            }
                        } catch(SQLException e){
                            System.out.println("Could not make the grabLeaseDataPrepStmt" + e.getMessage());
                        }

                        /** Unless the endDate is in the current month and year you are only then you can renew your lease */
                        if(endDate.getMonth() == currentDate.getMonth() && endDate.getYear() == currentDate.getYear()){
                            payment_id = generatePaymentID(conn);
                            String paymentType = "";
                            System.out.println("Now that you will renew your lease we need to set the payment for your security deposit");
                            do{
                                System.out.print("How would you like to pay (Credit, Debit, Cash, or Crypto) *Case Sensitive*: ");
                                paymentType = scnr.next();
                                System.out.println("");
                                if(paymentType.equals("Credit") || paymentType.equals("Debit") || paymentType.equals("Cash") || paymentType.equals("Crypto") ){
                                    /** Insert payment row into Payment */
                                        security_deposit = -1 * security_deposit;
                                        String insertPaymentQuery = "insert into Payment (payment_id, amount, transaction_date, type_new, lease_id) values (?, ?, ?, ?, ?)";
                                        try(PreparedStatement insertPaymentPrepStmt = conn.prepareStatement(insertPaymentQuery)){
                                            insertPaymentPrepStmt.setString(1, payment_id);
                                            insertPaymentPrepStmt.setDouble(2, security_deposit);
                                            /** making payment today */
                                            insertPaymentPrepStmt.setDate(3, Date.valueOf(currentDate));
                                            insertPaymentPrepStmt.setString(4, paymentType);
                                            insertPaymentPrepStmt.setString(5,lease_id);

                                            int insertPaymenttRow = insertPaymentPrepStmt.executeUpdate();

                                            if(insertPaymenttRow > 0){
                                                System.out.println("Rows have been updated.");
                                            } else{
                                                System.out.println("No rows have been updated.");
                                            }
                                            break;

                                        } catch(SQLException e){
                                            System.out.println("Could not make insertPaymentPrepStmt " + e.getMessage());
                                        }
                                } else{
                                    System.out.println("Please type the right input type");
                                    continue;
                                }
                                
                            } while (true);

                            /**We can view the payment */
                            String viewPaymentQuery  =  "select * from Payment where payment_id = ?";
                            try(PreparedStatement viewPaymentPrepStmt = conn.prepareStatement(viewPaymentQuery)){
                                viewPaymentPrepStmt.setString(1, payment_id);
                                try(ResultSet viewPaymentResult = viewPaymentPrepStmt.executeQuery()){
                                    while(viewPaymentResult.next()){
                                        System.out.println("Payment ID: " + viewPaymentResult.getString("payment_id") 
                                        + " Amount: " + viewPaymentResult.getDouble("amount") + " Transaction Date: " + viewPaymentResult.getDate("transaction_date") 
                                        + " Payment Type: " + viewPaymentResult.getString("type_new") +  " Lease ID: " + viewPaymentResult.getString("lease_id"));
                                    }
                                } catch(SQLException e){
                                    System.out.println("Could not make viewPaymentResult "+ e.getMessage());
                                }
                            } catch(SQLException e){
                                System.out.println("Could not make viewPaymentPrepStmt " + e.getMessage());
                            }

                            /**Now we delete the old lease and create new lease */
                            // String deleteLeaseQuery = "delete from Lease where lease_id = ?";
                            // try(PreparedStatement deleteLeasePrepStmt = conn.prepareStatement(deleteLeaseQuery)){
                            //     deleteLeasePrepStmt.setString(1, lease_id);

                            //     int deletedRow = deleteLeasePrepStmt.executeUpdate();

                            //     if(deletedRow > 0){
                            //         System.out.println("The lease row has sucessfully been deleted");
                            //     } else{
                            //         System.out.println("The lease row has not been deleted.");
                            //     }
                            
                            // } catch(SQLException e){
                            //     System.out.println("The deleteLeasePrepStmt did not work " + e.getMessage());
                            // }

                            /**Now insert the new lease */

                            System.out.println("Your new lease will begin a month from your endDate from your old lease");
                            String newLeaseID = generateLeaseID(conn);

                            for(int i =0; i<tenant_IDs.size(); i++){
                                String insertLeaseQuery = "insert into Lease (lease_id, start_date, end_date, rate, security_deposit, apt_id, tenant_id) values (?, ?, ?, ?)";
                                newStartDate = endDate.plusMonths(1);
                                /**Lease will end one year from now */
                                LocalDate newEndDate = newStartDate.plusYears(1);
                                try(PreparedStatement createLeasePrepStmt = conn.prepareStatement(insertLeaseQuery)){
                                    createLeasePrepStmt.setString(1, lease_id);
                                    createLeasePrepStmt.setDate(2, Date.valueOf(newStartDate));
                                    createLeasePrepStmt.setDate(3, Date.valueOf(newEndDate));
                                    createLeasePrepStmt.setDouble(4, rate);
                                    createLeasePrepStmt.setDouble(5, security_deposit);
                                    createLeasePrepStmt.setString(6, apt_id);
                                    createLeasePrepStmt.setString(7,tenant_IDs.get(i));

                                    int insertNewLeaseRow = createLeasePrepStmt.executeUpdate();

                                    if(insertNewLeaseRow > 0){
                                        System.out.println("Inserted to Lease.");
                                    } else{
                                        System.out.println("Nothing was inserted.");
                                    }

                                } catch(SQLException e){
                                    System.out.println("Could not make createLeasePrepStmt");
                                }
                            }

                            /**We can view the newLease if it is there */ 
                            System.out.println("Let us view the new Lease. ");                          
                            String viewNewLeaseQuery = "select * from Lease where lease_id = ?";
                            try(PreparedStatement viewNewLeasePrepStmt = conn.prepareStatement(viewNewLeaseQuery)){
                                viewNewLeasePrepStmt.setString(1, newLeaseID);
                                try(ResultSet viewNewLeaseResults = viewNewLeasePrepStmt.executeQuery()){
                                    while(viewNewLeaseResults.next()){
                                        System.out.println("Lease ID " + viewNewLeaseResults.getString("lease_id") + " Start Date " + viewNewLeaseResults.getDate("start_date") 
                                        + " End Date " + viewNewLeaseResults.getDate("end_date") + " Rate: " + viewNewLeaseResults.getDouble("rate") + " Security Deposit " 
                                        + viewNewLeaseResults.getDouble("security_deposit") + " Apt ID: " + viewNewLeaseResults.getString("apt_id") + 
                                        " Tenant ID: " + viewNewLeaseResults.getString("tenant_id"));
                                    }
                                } catch(SQLException e){
                                    System.out.println("Could not make the viewNewLeaseResults " + e.getMessage());
                                }
                            } catch(SQLException e){
                                System.out.println("viewNewLeasePrepStmt not made " + e.getMessage());
                            }

                            
                            
                        } else{
                            System.out.println("Please try to renew your lease within the month of the currentDate. ");
                        }

                        
                        
                    }
                } catch(SQLException e){
                    System.out.println("Could not make checkLeaseResult " + e.getMessage());
                }

            } catch(SQLException e){
                System.out.println("Could not make the checkLeasePrepStmt " + e.getMessage());
            }

        } catch(InputMismatchException e){
            System.out.println("You did not input the proper string " + e.getMessage());
        }

    }

    public static void moveOut(Connection conn, Scanner scnr){
        String lease_id = "";
        List<String> tenant_IDs = new ArrayList<>();
        int i = 0;
        LocalDate currentDate = LocalDate.now();
        LocalDate returnDate = LocalDate.now();
        double security_deposit = 0;
        /**We have to delete their row from the lease and 
         * I guess tenant because they don't live in that apartment anymore */
        /**We need to also make sure that the current date (today is equal to end_date or anything after it for this to work out) */
        System.out.print("To begin the process of moving out we first need to retrieve the Lease ID: ");
        try{
            lease_id = scnr.next();
            System.out.println("");
            String checkLeaseIDQuery = "select count(*) from Lease where lease_id = ?";
            try(PreparedStatement checkLeasePrepStmt = conn.prepareStatement(checkLeaseIDQuery)){
                checkLeasePrepStmt.setString(1, lease_id);
                try(ResultSet checkLeaseResult = checkLeasePrepStmt.executeQuery()){
                    /**This means the query exist  we can grab the other info like the tenantID associated so we can take them all out*/
                    if(checkLeaseResult.next() && checkLeaseResult.getInt(1) > 0){
                        String grabLeaseDataQuery = "select tenant_id from Lease where lease_id = ?";
                        try(PreparedStatement grabLeaseDataPrepStmt = conn.prepareStatement(grabLeaseDataQuery)){
                            grabLeaseDataPrepStmt.setString(1, lease_id);
                            try(ResultSet grabLeaseDataResult = grabLeaseDataPrepStmt.executeQuery()){
                                while(grabLeaseDataResult.next()){
                                    /** Now we have all the tenant_IDs  */
                                    tenant_IDs.add(grabLeaseDataResult.getString("tenant_id"));
                                }
                                /**Now we have to delete from the Lease table  and then delete the associated tenant from the tenant table*/
                                

                            } catch(SQLException e){
                                System.out.println("Could not make the grabLeaseDataResult" + e.getMessage());
                            }
                        } catch(SQLException e){
                            System.out.println("Could not make the grabLeaseDataPrepStmt" + e.getMessage());
                        }


                        System.out.println("Let's check your end date for your move out and set your return date.");
                        returnDate = setMoveOutDate(conn, scnr, lease_id);

                        /**has to be between the currentDate but no more than 7 days after */
                        if(!returnDate.isBefore(currentDate) && !returnDate.isAfter(currentDate.plusDays(7))){
                            System.out.println("Nice the move-out date checks out we will begin the move-out process");
                            System.out.println("We will process your secure deposit back to you");
                            String grabSecureDepositQuery = "select security_deposit from Lease where lease_id = ?";
                            try(PreparedStatement checkSecureDepositPrepStmt = conn.prepareStatement(grabSecureDepositQuery)){
                                checkSecureDepositPrepStmt.setString(1, lease_id);
                                try(ResultSet checkSecureDepositResult = checkSecureDepositPrepStmt.executeQuery()){
                                    if(checkSecureDepositResult.next()){
                                        security_deposit = checkSecureDepositResult.getDouble("security_deposit");
                                    }
                                } catch(SQLException e){
                                    System.out.println("Could not make the checkSecureDepositResult Stmt");
                                }
                            } catch(SQLException e){
                                System.out.println("Could not make the checkSecureDepositPrepStmt " + e.getMessage());
                            }
                            
                            String payment_id = generatePaymentID(conn);
                            System.out.println("The owner will send it to via Debit (note \"+\" means giving money back to you");

                            /**For simplicity purposes adding means lease owner is giving back to you! */
                            String insertPaymentQuery = "insert into Payment (payment_id, amount, transaction_date, type_new, lease_id) values (?, ?, ?, ?, ?)";

                            try(PreparedStatement paymentPrepStmt = conn.prepareStatement(insertPaymentQuery)){
                                paymentPrepStmt.setString(1, payment_id);
                                paymentPrepStmt.setDouble(2, security_deposit);
                                paymentPrepStmt.setDate(3, Date.valueOf(currentDate));
                                paymentPrepStmt.setString(4,"Debit");
                                paymentPrepStmt.setString(5,lease_id);

                                int insertRows = paymentPrepStmt.executeUpdate();

                                if(insertRows > 0){
                                    System.out.println("");
                                } else{
                                    System.out.println(" 0 Rows inserted in the Payment table");
                                }

                            } catch(SQLException e){
                                System.out.println("Could not make the paymentPrepStmt " + e.getMessage());
                            }

                            System.out.println("Here is the payment transaction just to make sure you see the proof.");
                            String checkPaymentQuery = "select * from Payment where lease_id = ?";
                            try(PreparedStatement checkPaymentPrepStmt = conn.prepareStatement(checkPaymentQuery)){
                                checkPaymentPrepStmt.setString(1, lease_id);
                                try(ResultSet checkPaymentResult = checkPaymentPrepStmt.executeQuery()){
                                    /**Proof of Transaction */
                                    while(checkPaymentResult.next()){
                                        System.out.println("Payment ID: " + checkPaymentResult.getString("payment_id") + " Amount " + checkPaymentResult.getDouble("amount") 
                                        + " Transaction Date: " + checkPaymentResult.getDate("transaction_date") + " Payment Type " + checkPaymentResult.getString("type_new") + 
                                        " Lease ID " + checkPaymentResult.getString("lease_id"));
                                    }
                                } catch(SQLException e){
                                    System.out.println("Couldn't make a checkPaymentResult" + e.getMessage());
                                }
                            } catch(SQLException e){
                                System.out.println("Could not make the checkPaymentPrepStmt" + e.getMessage());
                            }

                            /**Now we can remove them from the Lease for good once we get the payment
                             * We could also remove them as tenants until they register to be a potential tenant again
                            */

                            System.out.println("Now we are deleting you from the Lease");
                            String deleteLeaseQuery = "delete from Lease where lease_id = ?";
                            try(PreparedStatement deleteLeasePrepStmt = conn.prepareStatement(deleteLeaseQuery)){
                                deleteLeasePrepStmt.setString(1, lease_id);

                                int deletedRow = deleteLeasePrepStmt.executeUpdate();

                                if(deletedRow > 0){
                                    System.out.println("The lease row has sucessfully been deleted");
                                } else{
                                    System.out.println("The lease row has not been deleted.");
                                }
                            
                            } catch(SQLException e){
                                System.out.println("The deleteLeasePrepStmt did not work " + e.getMessage());
                            }

                            System.out.println("Now we are deleting tenants from the Tenant table (please feel free to register as a propspective tenant again");
                            String deleteTenantQuery = "";

                            /**There might be multiple tenants associated so we need to loop through and delete */
                            for(i = 0; i<tenant_IDs.size(); i++){
                                deleteTenantQuery = "delete * from Tenant where tenant_id = ?";
                                try(PreparedStatement deleteTenantPrepStmt = conn.prepareStatement(deleteTenantQuery)){
                                    deleteTenantPrepStmt.setString(1, tenant_IDs.get(i));
                                    int deletedTenantRow = deleteTenantPrepStmt.executeUpdate();
                                    if(deletedTenantRow > 0){
                                        System.out.println("Tenant " + tenant_IDs.get(i) + " was deleted.");
                                    } else{
                                        System.out.println("Nothing was deleted");
                                    }
                                } catch(SQLException e){
                                    System.out.println("Could not do deleteTenantPrepStmt" + e.getMessage());
                                }
                                
                            }

                        } else{
                            System.out.println("You must move out by today... you should have moved out a while ago."); 
                            System.out.println("You are not getting that security deposit back...");
                            System.out.println("Now we are deleting you from the Lease");
                            /**Delete them now no payment back */
                            String deleteLeaseQuery = "delete from Lease where lease_id = ?";
                            try(PreparedStatement deleteLeasePrepStmt = conn.prepareStatement(deleteLeaseQuery)){
                                deleteLeasePrepStmt.setString(1, lease_id);

                                int deletedRow = deleteLeasePrepStmt.executeUpdate();

                                if(deletedRow > 0){
                                    System.out.println("The lease row has sucessfully been deleted");
                                } else{
                                    System.out.println("The lease row has not been deleted.");
                                }
                            
                            } catch(SQLException e){
                                System.out.println("The deleteLeasePrepStmt did not work " + e.getMessage());
                            }

                            System.out.println("Now we are deleting tenants from the Tenant table (please feel free to register as a propspective tenant again");
                            String deleteTenantQuery = "";
                            /**There might be multiple tenants associated so we need to loop through and delete */
                            for(i = 0; i<tenant_IDs.size(); i++){
                                deleteTenantQuery = "delete from Tenant where tenant_id = ?";
                                try(PreparedStatement deleteTenantPrepStmt = conn.prepareStatement(deleteTenantQuery)){
                                    deleteTenantPrepStmt.setString(1, tenant_IDs.get(i));
                                    int deletedTenantRow = deleteTenantPrepStmt.executeUpdate();
                                    if(deletedTenantRow > 0){
                                        System.out.println("Tenant " + tenant_IDs.get(i) + " was deleted.");
                                    } else{
                                        System.out.println("Nothing was deleted");
                                    }
                                } catch(SQLException e){
                                    System.out.println("Could not do deleteTenantPrepStmt" + e.getMessage());
                                }
                            }
                        }
                        
                    }
                } catch(SQLException e){
                    System.out.println("Could not make the checkLeaseResult " + e.getMessage());
                }
            } catch (SQLException e){
                System.out.println("Could not prepare the checkLeasePrepStmt" + e.getMessage());
            }
        } catch(InputMismatchException e){
            System.out.println("Invalid input type" + e.getMessage());
        }
        
    }

    public static String generatePaymentID(Connection conn){
        Random random = new Random();
        String payment_id;
        int randomNumberToConvert = random.nextInt(10000000 - 1000000) + 1000000;
        randomNumberToConvert = Math.abs(randomNumberToConvert);
        boolean isPaymentUnique = false;
        do{
            payment_id = String.format("%d", randomNumberToConvert);
            /**Make sure it doesn't already exist in the Lease table */
            String checkPaymentQuery = "select count(*) from Payment where payment_id = ?";
            try(PreparedStatement checkPaymentPrepStmt = conn.prepareStatement(checkPaymentQuery)){
                checkPaymentPrepStmt.setString(1, payment_id);
                try(ResultSet checkPaymentResults = checkPaymentPrepStmt.executeQuery()){
                    checkPaymentResults.next();
                    if(checkPaymentResults.getInt(1) == 0){
                        isPaymentUnique = true;
                    }
                } catch(SQLException e){
                    System.out.println("Could not make the checkPaymentResult " + e.getMessage());
                }
            } catch(SQLException e){    
                System.out.println("Could not make the checkPaymentPrepStmt " + e.getMessage());
            }
        } while(!isPaymentUnique);

        return payment_id;
    }

    /**We need to verify that the moveOutDate is good (meaning it is after the lease ends) */
    /**I am just going to send back the currentDate plus One  */
    public static LocalDate setMoveOutDate(Connection conn, Scanner scnr, String lease_id){
        LocalDate currentDate = LocalDate.now();
        String checkEndDateQuery = "select end_date from Lease where lease_id = ?";
        LocalDate endDate = LocalDate.now();
        LocalDate returnDate = LocalDate.now();
        try(PreparedStatement grabEndDatePrepStmt = conn.prepareStatement(checkEndDateQuery)){
            grabEndDatePrepStmt.setString(1, lease_id);
            try(ResultSet grabEndDateResult = grabEndDatePrepStmt.executeQuery()){
                if(grabEndDateResult.next()){
                    endDate = grabEndDateResult.getDate("end_date").toLocalDate();
                    /** https://www.geeksforgeeks.org/localdatetime-tolocaldate-method-in-java-with-examples/*/
                }
                /**We also have to make sure it doesn't equal to null  and it is equal to or less than the currentDate*/
                /**Make sure it is within the same month as the lease is ending */
                if(endDate != null && endDate.isEqual(currentDate) || (endDate != null && endDate.isBefore(currentDate) && endDate.getMonth() == currentDate.getMonth())){
                    /**Set your move out date */
                    System.out.println("Looks like you checked out! We can set your move out date!");
                    System.out.println("Please enter the date you want to move out by (must be within a week since your end date)");

                    String dateInput = scnr.nextLine();
                    try{
                        returnDate = LocalDate.parse(dateInput);
                        if(returnDate != null && returnDate.isEqual(currentDate) || (returnDate != null && returnDate.isBefore(currentDate) && returnDate.getMonth() == currentDate.getMonth())){
                            return returnDate;
                        } else{
                            System.out.println("Did not input the right return date has to be within the same month of the current month");
                        }
                        
                    } catch(DateTimeParseException e){
                        System.out.println("The move-out-date you entered is not properly formatted " + e.getMessage());
                    }

                }else{
                    System.out.println("You can't move out yet");
                    return currentDate.minusYears(20);
                }
            } catch(SQLException e){
                System.out.println("Could not make the grabEndDateResult " + e.getMessage());
            }
        } catch(SQLException e){
            System.out.println("Couldn't make the grabEndDatePrepStmt" + e.getMessage());
        }

        return currentDate.minusYears(20);
    }
}