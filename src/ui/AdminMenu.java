package ui;

import api.AdminResource;
import model.RoomType;

import java.util.Scanner;
import java.util.regex.Pattern;

public class AdminMenu {
    private static Scanner scanner;
    private static String[] options;
    private static AdminResource adminresource;

    public AdminMenu(){
        scanner = new Scanner(System.in);
        options = new String[]{
                "1. See all Customers",
                "2. See all Rooms",
                "3. See all Reservations",
                "4. Add a Room",
                "5. Back to Main Menu"
        };
        adminresource = AdminResource.getInstance();
    }

    public static void printMenu(String[] options) {
        System.out.print("You are now in Administrator Mode \n");
        System.out.print("----------------------------------------------------- \n");
        for (String option : options) {
            System.out.println(option);
        }
        System.out.print("Choose your option : ");
    }

    public static void StartOptions() {
        while (true) {
            printMenu(options);
            int option_number = scanner.nextInt();
            switch (option_number) {
                case 1:
                    seeAllCustomers();
                    break;
                case 2:
                    seeAllRooms();
                    break;
                case 3:
                    seeAllReservations();
                    break;
                case 4:
                    addARoom();
                case 5:
                    MainMenu aMenu = new MainMenu();
                    aMenu.StartOptions();
                default:
                    System.out.println("Invalid Inputs! Going back to the menu.....");

        }
        }
    }

    public static void seeAllCustomers(){
        adminresource.displayAllCustomers();
    }

    public static void seeAllRooms(){
        adminresource.displayAllRooms();
    }

    public static void seeAllReservations(){
        adminresource.displayAllReservations();
    }

    public static void addARoom(){
        boolean finished = false;
        while(!finished) {
            String roomPrice = "";
            String roomNumber = "";
            RoomType roomtype = null;

            boolean finished_roomNumber = false;
            boolean finished_price = false;
            boolean finihsed_roomType = false;
            boolean finished_choice = false;

            String roomNumberRegex = "[0-9]+";
            String priceRegex = ("\\d+\\.\\d+");

            System.out.println("Enter room number");
            while(!finished_roomNumber){
                roomNumber = scanner.next();
                if (!Pattern.compile(roomNumberRegex).matcher(roomNumber).matches()){
                    System.out.println("Room number is not valid, please enter numbers only.");
                } else{
                    finished_roomNumber = true;
                }
            }

            System.out.println("Enter price per night, including decimal point");
            while(!finished_price){
                roomPrice = scanner.next();
                if (!Pattern.compile(priceRegex).matcher(roomPrice.toString()).matches()){
                    System.out.println("Price is not valid, please enter numbers with decimal point.");
                } else{
                    finished_price = true;
                }
            }


            System.out.println("Enter room type: 1 for single bed, 2 for double bed");
            while(!finihsed_roomType){
                switch (scanner.next()) {
                    case "1":
                        roomtype = RoomType.SINGLE;
                        finihsed_roomType = true;
                        break;
                    case "2":
                        roomtype = RoomType.DOUBLE;
                        finihsed_roomType = true;
                        break;
                    default:
                        System.out.println("Invalid inputs! Enter valid room type: 1 for single bed, 2 for double bed");
                        finihsed_roomType = false;

                }
            }

            adminresource.addRoom(roomNumber,Double.parseDouble(roomPrice),roomtype);

            System.out.println("Would you like to add another room y/n");
            while(!finished_choice){
                switch (scanner.next().charAt(0)) {
                    case 'Y':
                    case 'y':
                        finished = false;
                        finished_choice = true;
                        break;
                    case 'N':
                    case 'n':
                        finished = true;
                        finished_choice = true;
                        break;
                    default:
                        System.out.println("Invalid inputs! Please enter y or n");
                        finished_choice = false;
                }
            }
        }
    }

}
