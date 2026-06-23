package org.ukrida.hmifukridamobile.data.model

data class ApiResponse<T>(
    val status: String,
    val data: T?,
    val message: String?
)