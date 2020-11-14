package com.huijiewei.agile.app.shop.application.port.inbound;

import com.huijiewei.agile.app.shop.application.request.ShopBrandRequest;
import com.huijiewei.agile.app.shop.application.request.ShopBrandSearchRequest;
import com.huijiewei.agile.app.shop.domain.ShopBrandEntity;
import com.huijiewei.agile.core.application.response.SearchPageResponse;

/**
 * @author huijiewei
 */

public interface ShopBrandUseCase {
    SearchPageResponse<ShopBrandEntity> search(ShopBrandSearchRequest searchRequest, Integer page, Integer size, Boolean withSearchFields);

    ShopBrandEntity loadById(Integer id);

    ShopBrandEntity create(ShopBrandRequest shopBrandRequest);

    ShopBrandEntity update(Integer id, ShopBrandRequest shopBrandRequest);

    void deleteById(Integer id);
}
