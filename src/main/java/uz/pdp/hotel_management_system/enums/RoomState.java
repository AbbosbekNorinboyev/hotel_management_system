package uz.pdp.hotel_management_system.enums;

public enum RoomState {
    AVAILABLE,   // Bo‘sh, booking qilish mumkin
    RESERVED,    // Booking qilingan (CONFIRMED)
    OCCUPIED,    // Mehmon ichida (CHECKED_IN)
    DIRTY,       // Tozalash kerak
    MAINTENANCE  // Remont / yopiq
}
