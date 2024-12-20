import java.util.*;

class Flight {
    private int flightId;
    private String destination;
    private String source;
    private String date;
    private double price;
    private int availableSeats;

    public Flight(int flightId, String destination, String source, String date, double price, int availableSeats) {
        this.flightId = flightId;
        this.destination = destination;
        this.source = source;
        this.date = date;
        this.price = price;
        this.availableSeats = availableSeats;
    }

    public int getFlightId() {
        return flightId;
    }

    public String getDestination() {
        return destination;
    }

    public String getSource() {
        return source;
    }

    public String getDate() {
        return date;
    }

    public double getPrice() {
        return price;
    }

    public int getAvailableSeats() {
        return availableSeats;
    }

    public void reduceAvailableSeats() {
        if (availableSeats > 0) {
            availableSeats--;
        }
    }

    public void increaseAvailableSeats() {
        availableSeats++;
    }

    @Override
    public String toString() {
        return "Flight ID: " + flightId + ", Source: " + source + ", Destination: " + destination + 
               ", Date: " + date + ", Price: " + price + ", Available Seats: " + availableSeats;
    }
}

class User {
    private String username;
    private String password;
    private String role; // Admin or Customer

    // Constructor
    public User(String username, String password, String role) {
        this.username = username;
        this.password = password;
        this.role = role;
    }

    // Getter methods
    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getRole() {
        return role;
    }
}

class ReservationSystem {
    private List<Flight> flights = new ArrayList<>();
    private List<User> users = new ArrayList<>();
    private Map<User, List<Flight>> bookings = new HashMap<>();

    public ReservationSystem() {
        flights.add(new Flight(1, "Paris", "New York", "2024-12-25", 500, 10));
        flights.add(new Flight(2, "Tokyo", "London", "2024-12-26", 600, 5));
        flights.add(new Flight(3, "Sydney", "Los Angeles", "2024-12-27", 550, 7));

        // Allow users to add their own username and password here
        users.add(new User("admin", "admin123", "Admin"));
        users.add(new User("customer1", "password1", "Customer"));
        users.add(new User("customer2", "password2", "Customer"));
    }

    public boolean validateUser(String username, String password) {
        for (User user : users) {
            if (user.getUsername().equals(username) && user.getPassword().equals(password)) {
                return true;
            }
        }
        return false;
    }

    public User getUser(String username) {
        for (User user : users) {
            if (user.getUsername().equals(username)) {
                return user;
            }
        }
        return null;
    }

    public void displayFlights() {
        for (Flight flight : flights) {
            System.out.println(flight);
        }
    }

    public boolean bookFlight(User user, int flightId) {
        for (Flight flight : flights) {
            if (flight.getFlightId() == flightId) {
                if (flight.getAvailableSeats() > 0) {
                    flight.reduceAvailableSeats();
                    bookings.putIfAbsent(user, new ArrayList<>());
                    bookings.get(user).add(flight);
                    System.out.println("Flight booked successfully!");
                    return true;
                } else {
                    System.out.println("Sorry, no available seats for this flight.");
                    return false;
                }
            }
        }
        System.out.println("Flight not found.");
        return false;
    }

    public void viewBookings(User user) {
        List<Flight> userBookings = bookings.get(user);
        if (userBookings != null && !userBookings.isEmpty()) {
            for (Flight flight : userBookings) {
                System.out.println(flight);
            }
        } else {
            System.out.println("No bookings found for this user.");
        }
    }

    public void registerUser(String username, String password, String role) {
        users.add(new User(username, password, role));
        System.out.println("User registered successfully!");
    }

    public void searchFlightsByDate(String date) {
        boolean found = false;
        for (Flight flight : flights) {
            if (flight.getDate().equals(date)) {
                System.out.println(flight);
                found = true;
            }
        }
        if (!found) {
            System.out.println("No flights found for the given date.");
        }
    }

    public void addFlight(int flightId, String destination, String source, String date, double price, int availableSeats) {
        flights.add(new Flight(flightId, destination, source, date, price, availableSeats));
        System.out.println("Flight added successfully!");
    }

    public void removeFlight(int flightId) {
        flights.removeIf(flight -> flight.getFlightId() == flightId);
        System.out.println("Flight removed successfully!");
    }

    public void adminMenu() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("Admin Menu:");
            System.out.println("1. Add Flight");
            System.out.println("2. Remove Flight");
            System.out.println("3. View All Flights");
            System.out.println("4. Logout");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    System.out.print("Enter Flight ID: ");
                    int flightId = scanner.nextInt();
                    scanner.nextLine(); // Consume newline
                    System.out.print("Enter Destination: ");
                    String destination = scanner.nextLine();
                    System.out.print("Enter Source: ");
                    String source = scanner.nextLine();
                    System.out.print("Enter Date (YYYY-MM-DD): ");
                    String date = scanner.nextLine();
                    System.out.print("Enter Price: ");
                    double price = scanner.nextDouble();
                    System.out.print("Enter Available Seats: ");
                    int availableSeats = scanner.nextInt();
                    addFlight(flightId, destination, source, date, price, availableSeats);
                    break;

                case 2:
                    System.out.print("Enter Flight ID to remove: ");
                    int removeId = scanner.nextInt();
                    removeFlight(removeId);
                    break;

                case 3:
                    displayFlights();
                    break;

                case 4:
                    System.out.println("Logging out...");
                    return;

                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }

    public void customerMenu(User user) {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("Customer Menu:");
            System.out.println("1. View Flights");
            System.out.println("2. Book Flight");
            System.out.println("3. View Bookings");
            System.out.println("4. Search Flights by Date");
            System.out.println("5. Logout");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    displayFlights();
                    break;

                case 2:
                    System.out.print("Enter Flight ID to book: ");
                    int flightId = scanner.nextInt();
                    bookFlight(user, flightId);
                    break;

                case 3:
                    viewBookings(user);
                    break;

                case 4:
                    System.out.print("Enter Date (YYYY-MM-DD): ");
                    String date = scanner.nextLine();
                    searchFlightsByDate(date);
                    break;

                case 5:
                    System.out.println("Logging out...");
                    return;

                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }

    public void start() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("Welcome to the Flight Reservation System");
            System.out.print("Enter username: ");
            String username = scanner.nextLine();
            System.out.print("Enter password: ");
            String password = scanner.nextLine();

            if (validateUser(username, password)) {
                User user = getUser(username);
                if (user.getRole().equals("Admin")) {
                    adminMenu();
                } else {
                    customerMenu(user);
                }
            } else {
                System.out.println("Invalid credentials. Please try again.");
            }
        }
    }

    public static void main(String[] args) {
        ReservationSystem system = new ReservationSystem();
        system.start();
    }
}
