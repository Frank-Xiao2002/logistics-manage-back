package dev.xxj.logistics.repo;

import dev.xxj.logistics.model.Good;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

/**
 * GoodRepository interface to allow CRUD and paging operations on Good objects.
 * <p>
 * GoodRepository is an interface that extends {@link JpaRepository}, which is a Spring Data interface.
 * It provides methods for CRUD operations and paging on Good objects.
 *
 * @author Frank-Xiao
 * @see JpaRepository
 * @see Good
 * @see UUID
 */
public interface GoodRepository extends JpaRepository<Good, UUID> {
    /**
     * Find a list of goods by name.
     *
     * @param name the name of the goods to find
     * @return a list of goods with the given name
     */
    List<Good> findByName(String name);
}