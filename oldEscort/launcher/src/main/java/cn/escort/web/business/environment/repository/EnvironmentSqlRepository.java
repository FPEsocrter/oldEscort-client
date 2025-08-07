package cn.escort.web.business.environment.repository;

import cn.escort.frameworkConfig.web.entity.Page;
import cn.escort.frameworkConfig.web.entity.SelectPage;
import cn.escort.web.business.environment.domain.dto.ListEnvironmentDto;
import cn.escort.web.business.environment.domain.vo.EnvironmentPageVo;
import cn.escort.web.business.webProxy.domain.WebProxyEnum;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.jpa.spi.NativeQueryTupleTransformer;
import org.hibernate.query.TupleTransformer;
import org.hibernate.query.sql.internal.NativeQueryImpl;
import org.hibernate.query.sql.spi.NativeQueryImplementor;
import org.hibernate.transform.AliasToBeanResultTransformer;
import org.hibernate.transform.ResultTransformer;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;

import java.lang.reflect.InvocationTargetException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;


@Repository
public class EnvironmentSqlRepository {

    @PersistenceContext
    private  EntityManager entityManager;


    public Page<EnvironmentPageVo> listPage(ListEnvironmentDto req, SelectPage selectPage) {
        Page<EnvironmentPageVo> page = new Page(selectPage);
        String sqlSelect= """
                        select
                             serial_number,
                             id,
                             name,
                             remark,
                             last_use_ip,
                             area,
                             last_open_time,
                             web_proxy 
                         """;
        String sqlWhere= " from  et_environment env where deleted=0 ";
        HashMap<String, Object> parameterMap = new HashMap<>();
        if(StringUtils.isNotBlank(req.getName())){
            sqlWhere+="and  env.name like CONCAT('%',:name,'%')  "; //:#{#req.name}  自己手动取解析
            parameterMap.put("name", req.getName());
        }

        if(StringUtils.isNotBlank(req.getRemark())){
            sqlWhere+="and env.remark like CONCAT('%',:remark,'%')  "; //:#{#req.name}  自己手动取解析
            parameterMap.put("remark", req.getRemark());
        }

        if(StringUtils.isNotBlank(req.getArea())){
            sqlWhere+="and env.area like CONCAT('%',:area,'%')  "; //:#{#req.name}  自己手动取解析
            parameterMap.put("area", req.getArea());
        }

        if(!Objects.isNull(req.getWebProxy())){
            sqlWhere+="and env.web_proxy = :webProxy "; //:#{#req.name}  自己手动取解析
            parameterMap.put("webProxy", req.getWebProxy().ordinal());
        }


        Query countQuery = entityManager.createNativeQuery("select count(1) "+sqlWhere,Long.class);
        parameterMap.forEach(countQuery::setParameter);
        Long firstResult = (Long) countQuery.getResultList().get(0);
        page.setTotal(firstResult);
        if(firstResult==0){
            return page;
        }
        String orderSql ="order by id desc";

        String selectSql= "select ROWNUM() AS row_number,t.* from( "+sqlSelect+sqlWhere+orderSql+" )t";

        //noinspection rawtypes
        NativeQueryImpl query = entityManager.createNativeQuery(selectSql).unwrap(NativeQueryImpl.class);
        //noinspection unchecked
        NativeQueryImplementor<EnvironmentPageVo> nativeQueryImplementor = query.setTupleTransformer((tuple, aliases) -> {
            EnvironmentPageVo environmentPageVo = new EnvironmentPageVo();
            environmentPageVo.setRowNumber((Long) tuple[0]);
            environmentPageVo.setSerialNumber(String.valueOf(tuple[1]));
            environmentPageVo.setId((Long) tuple[2]);
            environmentPageVo.setName(String.valueOf(tuple[3]));
            environmentPageVo.setRemark(String.valueOf(tuple[4]));
            environmentPageVo.setLastUseIp(String.valueOf(tuple[5]));
            environmentPageVo.setArea(String.valueOf(tuple[6]));
            if(Objects.nonNull(tuple[7])){
                final LocalDateTime lastOpenTime = ((Timestamp) tuple[7]).toLocalDateTime();
                environmentPageVo.setLastOpenTime(lastOpenTime);
            }
            environmentPageVo.setWebProxy(WebProxyEnum.values()[(Byte) tuple[8]]);
            return environmentPageVo;
        });

        parameterMap.forEach(nativeQueryImplementor::setParameter);
        //设置分页

        int maxResults =  selectPage.getPageSize() * selectPage.getCurrentPage();

        nativeQueryImplementor.setFirstResult(Math.max(maxResults - selectPage.getPageSize(), 0));
        nativeQueryImplementor.setMaxResults(maxResults);
        List<EnvironmentPageVo> resultList = nativeQueryImplementor.getResultList();

        page.setList(resultList);

        // 执行查询并获取结果
        return page;
    }
}
