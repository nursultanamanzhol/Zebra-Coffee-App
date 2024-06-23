package com.example.zebracoffee.data.mapper

import com.example.zebracoffee.data.modelDto.UpdatedUserInfo
import com.example.zebracoffee.data.modelDto.UserDto
import javax.inject.Inject

class UserInfoMapper @Inject constructor() {

    fun mapEntityToDbModel(userDto: UpdatedUserInfo): UpdatedUserInfo =
        UpdatedUserInfo(
            pk = userDto.pk,
            avatar_image = userDto.avatar_image,
            birth_date = userDto.birth_date,
            created_at = userDto.created_at,
            discount_percentage = userDto.discount_percentage,
            name = userDto.name,
            phone = userDto.phone,
            ref_code = userDto.ref_code,
            status = userDto.status,
            zc_balance = userDto.zc_balance
        )

    fun mapDbModelToEntity(userInfo: UpdatedUserInfo): UserDto =
        UserDto(
            pk = userInfo.pk,
            avatar_image = userInfo.avatar_image,
            birth_date = userInfo.birth_date,
            created_at = userInfo.created_at,
            discount_percentage = userInfo.discount_percentage,
            name = userInfo.name,
            phone = userInfo.phone,
            ref_code = userInfo.ref_code,
            status = userInfo.status,
            zc_balance = userInfo.zc_balance
        )
}
