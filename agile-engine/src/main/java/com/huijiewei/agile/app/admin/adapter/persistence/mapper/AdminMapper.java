package com.huijiewei.agile.app.admin.adapter.persistence.mapper;

import com.huijiewei.agile.app.admin.adapter.persistence.entity.Admin;
import com.huijiewei.agile.app.admin.domain.AdminEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

/**
 * @author huijiewei
 */

@SuppressWarnings("AlibabaAbstractMethodOrInterfaceMethodMustUseJavadoc")
@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface AdminMapper {
    AdminEntity toAdminEntityWithAdminGroup(Admin admin);

    @Mapping(target = "adminGroup", ignore = true)
    AdminEntity toAdminEntity(Admin admin);

    @Mapping(target = "adminGroup", ignore = true)
    Admin toAdmin(AdminEntity adminEntity);
}

