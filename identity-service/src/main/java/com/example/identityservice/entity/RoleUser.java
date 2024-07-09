package com.example.identityservice.entity;

import com.example.identityservice.entity.base.PrimaryEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Entity
@Table(name = "role_user")
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RoleUser extends PrimaryEntity {

    @Column(name = "id_user")
    Long userId;

    @Column(name = "id_role")
    Long roleId;
}
