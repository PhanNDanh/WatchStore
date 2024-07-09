package com.example.identityservice.repository;

import com.example.identityservice.entity.Permission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface PermissionRepository extends JpaRepository<Permission,Long> {

    @Query(nativeQuery = true,value = """
                   SELECT p.code AS permission_code
                   FROM permission p
                   JOIN permission_permission_group ppg ON p.id = ppg.id_permission
                   JOIN role_permission_group rpg ON ppg.id_permission_group = rpg.id_permission_group
                   JOIN role_user ru ON rpg.id_role = ru.id_role
                   WHERE ru.id_user = :userId
            """)
    Set<String> getPermissionsByUserId(@Param("userId") Long userId);
}
