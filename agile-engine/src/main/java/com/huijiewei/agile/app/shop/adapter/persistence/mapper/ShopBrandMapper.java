package com.huijiewei.agile.app.shop.adapter.persistence.mapper;

import com.huijiewei.agile.app.shop.adapter.persistence.entity.ShopBrand;
import com.huijiewei.agile.app.shop.domain.ShopBrandEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

/**
 * @author huijiewei
 */

@SuppressWarnings("AlibabaAbstractMethodOrInterfaceMethodMustUseJavadoc")
@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ShopBrandMapper {
    @Mapping(target = "shopCategoryIds", ignore = true)
    ShopBrandEntity toShopBrandEntity(ShopBrand shopBrand);

    @Mapping(target = "shopCategories", ignore = true)
    ShopBrand toShopBrand(ShopBrandEntity shopBrandEntity);
}
