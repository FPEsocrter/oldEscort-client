package cn.escort.frameworkConfig.jpa.JpaBase.repository;

import cn.escort.frameworkConfig.jpa.JpaBase.domain.BaseDeletedEntry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.NoRepositoryBean;

import java.io.Serializable;
import java.util.List;

@NoRepositoryBean
public interface BaseDeletedRepository <T extends BaseDeletedEntry, ID extends Serializable> extends JpaRepository<T, ID> {

    @Query("select e from #{#entityName} e where e.deleted = 0 ")
    List<T> findAllWithLogic();


    @Query("select e from #{#entityName} e where e.id in ?1 and e.deleted = 0")
    List<T> findAllByIdWithLogic(Iterable<ID> iterable);

    @Query("select e from #{#entityName} e where e.id = ?1 and e.deleted = 0")
    T getOneWithLogic(ID id);


    @Query("select count(e) from #{#entityName} e where e.deleted = 0")
    long countWithLogic();


    default boolean existsByIdWithLogic(ID id) {
        return getOneWithLogic(id) != null;
    }

    @Modifying
    @Query(value = "update #{#entityName} e set e.deleted = 1 where e.id = ?1")
    void deleteByIdWithLogic(ID id);

}
