package com.example.nasaapp.data.mapper

import com.example.nasaapp.data.model.detailed.ItemDetailedInfoDto
import com.example.nasaapp.domain.model.DetailedFileInfo

object DetailedDtoToDomainMapper {
    fun map(dto: ItemDetailedInfoDto, videoUrl: String = ""): DetailedFileInfo {
        return DetailedFileInfo(
            fileSize = dto.fileFileSize,
            fileFormat = dto.fileFileTypeExtension,
            videoUrl = encodeUrl(videoUrl)
        )
    }
    private fun encodeUrl(url: String): String {
        return url.replace(" ", "%20")
    }
}