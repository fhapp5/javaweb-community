package io.javaweb.community.mapper;

import io.javaweb.community.common.BaseMapper;
import io.javaweb.community.entity.CollectionEntity;
import io.javaweb.community.entity.dto.CollectionDTO;
import org.springframework.stereotype.Repository;

import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.github.miemiedev.mybatis.paginator.domain.PageList;

/**
 * Created by KevinBlandy on 2018/1/29 12:22
 */
@Repository
public interface CollectionMapper extends BaseMapper<CollectionEntity>{

    //检索
    PageList<CollectionDTO> queryCollectionsByCreateUser(CollectionDTO collectionDTO, PageBounds pageBounds)throws Exception;
}
