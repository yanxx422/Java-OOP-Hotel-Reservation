package service;
import model.*;

import java.util.*;

public final class ReservationService {
    private static ReservationService reservationService;
    private static Collection <Reservation> reservationList;
    private static Map<Customer,ArrayList<Reservation>> customerToReservation;
    private Set<IRoom> roomSet;
    private ReservationService(){
        this.reservationList = new HashSet<Reservation>();
        this.roomSet = new HashSet<IRoom>();
        this.customerToReservation = new HashMap<Customer,ArrayList<Reservation>>();
    };

    public static ReservationService getInstance(){
        if (reservationService == null){
            reservationService = new ReservationService();
        }
        return reservationService;
    }

    public void addRoom(String roomNumber, Double price, RoomType enumeration){
        roomSet.add(new Room(roomNumber, price, enumeration));
    }

    public IRoom getARoom(String roomId){
        for (IRoom room:roomSet){
            if (roomId.equals(room.getRoomNumber())) {
                return room;
            }
        }
        return null;
    }

    public Collection getAllRooms(){
        return roomSet;
    }

    public Reservation reserveARoom(Customer customer, IRoom room, Date checkInDate, Date checkOutDate){
        Reservation newReservation = null;
        Collection<IRoom> available_rooms = findRooms(checkInDate, checkOutDate);
        if(!available_rooms.contains(room)){
            System.out.println("Create reservation failed, this room is not available.");
        }
        else{
            newReservation = new Reservation(customer, room, checkInDate, checkOutDate);
            reservationList.add(newReservation);
            if (customerToReservation.get(customer) != null){
                customerToReservation.get(customer).add(newReservation);
            } else{
                ArrayList<Reservation> NewReservationList = new ArrayList<>();
                NewReservationList.add(newReservation);
                customerToReservation.put(customer, NewReservationList);
            }
        }
        return newReservation;
    }

    public Collection<IRoom> findRooms(Date checkInDate, Date checkOutDate) {
        Collection<IRoom> unbookedRooms = new HashSet<>(roomSet);
        Collection<IRoom> bookedRooms = getBookedRooms();
        Collection<IRoom> available_rooms = new HashSet<>();
        HashMap<IRoom,Integer> roomStates = new HashMap<>();
        unbookedRooms.removeAll(bookedRooms);
        //first check booked rooms
        for (Reservation reservation : reservationList) {
            Date startDate = reservation.getCheckInDate();
            Date endDate = reservation.getCheckOutDate();
            if (( checkOutDate.before(startDate)) || checkInDate.after(endDate)){
                //the reservation entry that passed date range
                roomStates.put(reservation.getRoom(),1);
            } else{
                //entry didn't pass date range test
                roomStates.replace(reservation.getRoom(),0);
            }
        }
        //add booked rooms that passed range test
        roomStates.forEach((key, value) -> {
            if (value.equals(1)) {
                available_rooms.add(key);
            }
        });

        //then add all unbooked rooms to the result list
        available_rooms.addAll(unbookedRooms);
        return available_rooms;
    }



    Collection <Reservation> getAllReservation(){
        return reservationList;
    }

    public Collection <Reservation> getCustomersReservation(Customer customer){
        return customerToReservation.get(customer);
    }

    Collection <IRoom> getBookedRooms() {
        Collection <IRoom> bookedRooms = new HashSet<>();
        for (Reservation reservation: reservationList){
            bookedRooms.add(reservation.getRoom());
        }
        return bookedRooms;
    }

    public void printAllReservation(){
        for(Reservation reservation: reservationList){
            System.out.println(reservation);
        }
    }

    public void printAllRoom(){
        for(IRoom room: roomSet){
            System.out.println(room);
        }
    }
}
