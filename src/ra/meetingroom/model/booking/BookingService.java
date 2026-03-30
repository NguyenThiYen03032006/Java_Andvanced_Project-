package ra.meetingroom.model.booking;

public class BookingService {
    private int id;
    private int bookingId;   // FK -> bookings
    private int serviceId;   // FK -> services
    private int quantity;    // số lượng (vd: 10 chai nước)
    private double price;    // snapshot giá tại thời điểm đặt
    public BookingService() {
    }

    public BookingService(int bookingId, int id, double price, int quantity, int serviceId) {
        this.bookingId = bookingId;
        this.id = id;
        this.price = price;
        this.quantity = quantity;
        this.serviceId = serviceId;
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

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getServiceId() {
        return serviceId;
    }

    public void setServiceId(int serviceId) {
        this.serviceId = serviceId;
    }

}
