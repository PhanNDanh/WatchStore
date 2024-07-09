package com.example.identityservice.entity;

import com.example.identityservice.entity.base.PrimaryEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Entity
@Table(name = "permission_permission_group")
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PermissionPermissionGroup extends PrimaryEntity {

    @Column(name = "id_permission")
    Long permissionId;

    @Column(name = "id_permission_group")
    Long permissionGroupId;

}
