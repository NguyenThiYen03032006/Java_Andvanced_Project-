package ra.meetingroom.model.booking;

public class BookingEquipment {
    private int id;
    private int bookingId;    // FK -> bookings
    private int equipmentId;  // FK -> equipments
    private int quantity;     // số lượng mượn


}
