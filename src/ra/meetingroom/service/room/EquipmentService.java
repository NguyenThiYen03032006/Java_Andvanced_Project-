package ra.meetingroom.service.room;

import ra.meetingroom.dao.room.EquipmentDAO;
import ra.meetingroom.model.room.Equipment;

import java.util.List;

public class EquipmentService {

    private EquipmentDAO dao = new EquipmentDAO();

    public List<Equipment> getAll() {
        return dao.findAll();
    }
    public boolean addEquipment(Equipment e) {

        // validation
        if (e.getAvailableQuantity() > e.getTotalQuantity()) {
            System.out.println("Available không được lớn hơn total!");
            return false;
        }

        if (e.getTotalQuantity() <= 0) {
            System.out.println("Total phải > 0");
            return false;
        }

        return dao.insert(e);
    }
    public boolean updateQuantity(int id, int total, int available) {

        // validation
        if (available > total) {
            System.out.println("Available không được lớn hơn total!");
            return false;
        }

        return dao.updateQuantity(id, total, available);
    }

    public boolean delete(int id) {
        return dao.delete(id);
    }
}
