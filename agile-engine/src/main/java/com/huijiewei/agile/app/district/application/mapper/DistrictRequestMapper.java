package com.huijiewei.agile.app.district.application.mapper;

import com.huijiewei.agile.app.district.application.request.DistrictRequest;
import com.huijiewei.agile.app.district.domain.DistrictEntity;
import org.mapstruct.InheritConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

/**
 * @author huijiewei
 */

@Mapper
public interface DistrictRequestMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "children", ignore = true)
    @Mapping(target = "parents", ignore = true)
    DistrictEntity toDistrictEntity(DistrictRequest districtRequest);

    @InheritConfiguration
    void updateDistrictEntity(DistrictRequest districtRequest, @MappingTarget DistrictEntity districtEntity);

}
