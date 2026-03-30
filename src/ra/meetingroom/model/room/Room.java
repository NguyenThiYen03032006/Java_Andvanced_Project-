package ra.meetingroom.model.room;

public class Room {
    private int id;
    private String name;
    private int capacity;
    private String location;
    private String description;

    public Room() {
    }

    public Room(int capacity, String description, int id, String location, String name) {
        this.capacity = capacity;
        this.description = description;
        this.id = id;
        this.location = location;
        this.name = name;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    @Override
    public String toString() {
        return "ID: " + id +
                " | Tên: " + name +
                " | Sức chứa: " + capacity +
                " | Vị trí: " + location +
                " | Mô tả: " + description;
    }
}
