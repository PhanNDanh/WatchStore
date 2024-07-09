package com.example.identityservice.repository;

import com.example.identityservice.entity.PermissionGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface PermissionGroupRepository extends JpaRepository<PermissionGroup,Long> {

    @Query(nativeQuery = true,value = """
                   SELECT pg.code AS permission_group_code
                   FROM permission_group pg
                   JOIN role_permission_group rpg ON pg.id = rpg.id_permission_group
                   JOIN role_user ru ON rpg.id_role = ru.id_role
                   WHERE ru.id_user = :userId
            """)
    Set<String> getPermissionGroupsByUserId(@Param("userId") Long userId);
}
