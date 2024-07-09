package com.example.identityservice.entity;

import com.example.identityservice.entity.base.PrimaryEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Entity
@Table(name = "role_permission_group")
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RolePermissionGroup extends PrimaryEntity {

    @Column(name = "id_role")
    Long roleId;

    @Column(name = "id_permission_group")
    Long permissionGroupId;
}
