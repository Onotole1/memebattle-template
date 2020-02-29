package ru.memebattle.common.dto.report

data class ReportDto(
    val type: ReportTypeDto,
    val theme: String,
    val message: String,
    val anonymous: Boolean
)