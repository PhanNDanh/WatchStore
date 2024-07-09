package com.example.identityservice.constant;

public enum RoleUser {
    ADMIN(1, "Chủ tịch"),
    MANAGE(2, "Giám đốc"),
    LEADER(3, "Trưởng phòng"),
    SECRETARY(4, "Thư ký"),
    STAFF(5, "Nhân viên");

    private final int id;
    private final String description;

    RoleUser(int id, String description) {
        this.id = id;
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }
}
