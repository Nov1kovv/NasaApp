package com.example.nasaapp.data.model.detailed

import com.google.gson.annotations.SerializedName

data class ItemDetailedInfoDto(
    @SerializedName("EXIF:CreateDate")
    val exifCreateDate: String,
    @SerializedName("File:FilePermissions")
    val fileFilePermissions: String,
    @SerializedName("File:FileName")
    val fileFileName: String,
    @SerializedName("AVAIL:Album")
    val availAlbum: List<String>,
    @SerializedName("XMP:DateCreated")
    val xmpDateCreated: String,
    @SerializedName("EXIF:ComponentsConfiguration")
    val exifComponentsConfiguration: String,
    @SerializedName("JFIF:YResolution")
    val jfifYresolution: Long,
    @SerializedName("AVAIL:DateCreated")
    val availDateCreated: String,
    @SerializedName("File:BitsPerSample")
    val fileBitsPerSample: Long,
    @SerializedName("JFIF:ResolutionUnit")
    val jfifResolutionUnit: String,
    @SerializedName("EXIF:ResolutionUnit")
    val exifResolutionUnit: String,
    @SerializedName("EXIF:ImageDescription")
    val exifImageDescription: String,
    @SerializedName("File:YCbCrSubSampling")
    val fileYcbCrSubSampling: String,
    @SerializedName("AVAIL:Keywords")
    val availKeywords: List<String>,
    @SerializedName("EXIF:ColorSpace")
    val exifColorSpace: String,
    @SerializedName("IPTC:ApplicationRecordVersion")
    val iptcApplicationRecordVersion: Long,
    @SerializedName("XMP:Source")
    val xmpSource: String,
    @SerializedName("ExifTool:ExifToolVersion")
    val exifToolExifToolVersion: Double,
    @SerializedName("XMP:XMPToolkit")
    val xmpXmptoolkit: String,
    @SerializedName("File:EncodingProcess")
    val fileEncodingProcess: String,
    @SerializedName("AVAIL:Location")
    val availLocation: String,
    @SerializedName("AVAIL:Owner")
    val availOwner: String,
    @SerializedName("EXIF:FlashpixVersion")
    val exifFlashpixVersion: String,
    @SerializedName("XMP:Createdate")
    val xmpCreatedate: String,
    @SerializedName("AVAIL:NASAID")
    val availNasaid: String,
    @SerializedName("EXIF:ExifVersion")
    val exifExifVersion: String,
    @SerializedName("EXIF:YCbCrPositioning")
    val exifYcbCrPositioning: String,
    @SerializedName("File:CurrentIPTCDigest")
    val fileCurrentIptcdigest: String,
    @SerializedName("File:FileTypeExtension")
    val fileFileTypeExtension: String,
    @SerializedName("File:FileSize")
    val fileFileSize: String,
    @SerializedName("File:ImageWidth")
    val fileImageWidth: Long,
    @SerializedName("EXIF:YResolution")
    val exifYresolution: Long,
    @SerializedName("AVAIL:Description")
    val availDescription: String,
    @SerializedName("AVAIL:MediaType")
    val availMediaType: String,
    @SerializedName("Composite:Megapixels")
    val compositeMegapixels: Double,
    @SerializedName("File:ExifByteOrder")
    val fileExifByteOrder: String,
    @SerializedName("File:Directory")
    val fileDirectory: String,
    @SerializedName("JFIF:XResolution")
    val jfifXresolution: Long,
    @SerializedName("AVAIL:Description508")
    val availDescription508: String,
    @SerializedName("AVAIL:Center")
    val availCenter: String,
    @SerializedName("XMP:ImageDescription")
    val xmpImageDescription: String,
    @SerializedName("File:FileModifyDate")
    val fileFileModifyDate: String,
    @SerializedName("File:MIMEType")
    val fileMimetype: String,
    @SerializedName("XMP:Description")
    val xmpDescription: String,
    @SerializedName("AVAIL:SecondaryCreator")
    val availSecondaryCreator: String,
    @SerializedName("JFIF:JFIFVersion")
    val jfifJfifversion: Double,
    @SerializedName("File:FileInodeChangeDate")
    val fileFileInodeChangeDate: String,
    @SerializedName("XMP:Title")
    val xmpTitle: String,
    @SerializedName("SourceFile")
    val sourceFile: String,
    @SerializedName("File:ImageHeight")
    val fileImageHeight: Long,
    @SerializedName("XMP:Nasa_id")
    val xmpNasaId: String,
    @SerializedName("XMP:CreateDate")
    val xmpCreateDate: String,
    @SerializedName("AVAIL:Title")
    val availTitle: String,
    @SerializedName("File:ColorComponents")
    val fileColorComponents: Long,
    @SerializedName("AVAIL:Photographer")
    val availPhotographer: String,
    @SerializedName("IPTC:Keywords")
    val iptcKeywords: List<String>,
    @SerializedName("XMP:Credit")
    val xmpCredit: String,
    @SerializedName("File:FileAccessDate")
    val fileFileAccessDate: String,
    @SerializedName("EXIF:XResolution")
    val exifXresolution: Long,
    @SerializedName("File:FileType")
    val fileFileType: String,
    @SerializedName("Composite:ImageSize")
    val compositeImageSize: String,
)