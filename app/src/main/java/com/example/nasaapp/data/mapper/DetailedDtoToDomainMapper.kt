package com.example.nasaapp.data.mapper

import com.example.nasaapp.data.model.detailed.ItemDetailedInfoDto
import com.example.nasaapp.domain.model.DetailedFileInfo

object DetailedDtoToDomainMapper {
    fun map(dto: ItemDetailedInfoDto): DetailedFileInfo {
        return DetailedFileInfo(
            fileSize = dto.fileFileSize,
            fileFormat = dto.fileFileTypeExtension
        )
    }
}