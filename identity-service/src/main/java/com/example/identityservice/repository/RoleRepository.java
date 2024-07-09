package com.example.identityservice.repository;

import com.example.identityservice.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface RoleRepository extends JpaRepository<Role,Long> {

    @Query(nativeQuery = true,value = """
                   SELECT r.code AS role_code
                   FROM role r
                   JOIN role_user ru ON ru.id_role = r.id
                   WHERE ru.id_user = :userId
            """)
    Set<String> getRolesByUserId(@Param("userId") Long userId);

}
