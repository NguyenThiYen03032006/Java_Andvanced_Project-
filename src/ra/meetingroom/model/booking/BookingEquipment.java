package ra.meetingroom.model.booking;

public class BookingEquipment {
    private int id;
    private int bookingId;    // FK -> bookings
    private int equipmentId;  // FK -> equipments
    private int quantity;     // số lượng mượn

    public BookingEquipment() {
    }

    public BookingEquipment(int bookingId, int equipmentId, int id, int quantity) {
        this.bookingId = bookingId;
        this.equipmentId = equipmentId;
        this.id = id;
        this.quantity = quantity;
    }

    public int getBookingId() {
        return bookingId;
    }

    public void setBookingId(int bookingId) {
        this.bookingId = bookingId;
    }

    public int getEquipmentId() {
        return equipmentId;
    }

    public void setEquipmentId(int equipmentId) {
        this.equipmentId = equipmentId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
