package ra.meetingroom.model.room;

public class Equipment {
    private int id;
    private String name;
    private int totalQuantity;
    private int availableQuantity;
    private String status;

    public Equipment() {
    }

    public Equipment(int availableQuantity, int id, String name, String status, int totalQuantity) {
        this.availableQuantity = availableQuantity;
        this.id = id;
        this.name = name;
        this.status = status;
        this.totalQuantity = totalQuantity;
    }

    public int getAvailableQuantity() {
        return availableQuantity;
    }

    public void setAvailableQuantity(int availableQuantity) {
        this.availableQuantity = availableQuantity;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getTotalQuantity() {
        return totalQuantity;
    }

    public void setTotalQuantity(int totalQuantity) {
        this.totalQuantity = totalQuantity;
    }
}