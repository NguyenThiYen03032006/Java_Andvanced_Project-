package ra.meetingroom.model.booking;

public class BookingDetail {
    private int id;
    private int bookingId;
    private String type; // EQUIPMENT hoặc SERVICE
    private int itemId;
    private int quantity;

    public BookingDetail() {
    }

    public BookingDetail(int bookingId, int id, int itemId, int quantity, String type) {
        this.bookingId = bookingId;
        this.id = id;
        this.itemId = itemId;
        this.quantity = quantity;
        this.type = type;
    }

    public int getBookingId() {
        return bookingId;
    }

    public void setBookingId(int bookingId) {
        this.bookingId = bookingId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getItemId() {
        return itemId;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}