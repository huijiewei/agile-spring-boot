package com.huijiewei.agile.core.adapter.persistence.repository;

import com.huijiewei.agile.core.adapter.persistence.entity.AbstractJpaEntity;

import java.util.List;

/**
 * @author huijiewei
 */

public interface BatchJpaRepository<T extends AbstractJpaEntity> {
    /**
     * 批量插入方法
     *
     * @param entities 实例列表
     */
    void batchInsert(List<T> entities);
}
