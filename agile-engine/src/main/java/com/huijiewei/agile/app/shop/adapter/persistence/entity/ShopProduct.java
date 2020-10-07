package com.huijiewei.agile.app.shop.adapter.persistence.entity;

import com.huijiewei.agile.core.adapter.persistence.AbstractJpaEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Column;
import javax.persistence.Entity;
import java.time.LocalDateTime;

/**
 * @author huijiewei
 */
@EqualsAndHashCode(callSuper = false)
@Data
@Entity
@DynamicInsert
@DynamicUpdate
public class ShopProduct extends AbstractJpaEntity {
    private Integer shopCategoryId;
    private Integer shopBrandId;

    private String name;

    @Column(updatable = false)
    private LocalDateTime createdAt;
}