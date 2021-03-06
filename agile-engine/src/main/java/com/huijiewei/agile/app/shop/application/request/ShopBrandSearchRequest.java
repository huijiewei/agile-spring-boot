package com.huijiewei.agile.app.shop.application.request;

import com.huijiewei.agile.core.application.request.AbstractSearchRequest;
import com.huijiewei.agile.core.application.request.KeywordSearchField;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author huijiewei
 */

@EqualsAndHashCode(callSuper = true)
@Data
public class ShopBrandSearchRequest extends AbstractSearchRequest {
    private String name;

    public ShopBrandSearchRequest() {
        this.addSearchField(new KeywordSearchField().field("name").label("品牌名称"));
    }
}