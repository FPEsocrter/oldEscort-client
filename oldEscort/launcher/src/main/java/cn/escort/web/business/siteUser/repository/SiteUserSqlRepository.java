package cn.escort.web.business.siteUser.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;


@Repository
public class SiteUserSqlRepository {

    @PersistenceContext
    private  EntityManager entityManager;


    private void d(){
        /*String targetUsername = "user123";

        // 创建原生 SQL 查询
        String sql = "SELECT * FROM UserTable WHERE username = :username";

        // 使用 EntityManager 创建原生 SQL 查询
        Query query = entityManager.createNativeQuery(sql, User.class);
        query.setParameter("username", targetUsername);

        // 执行查询并获取结果
        List<User> users = query.getResultList();*/

    }

}
