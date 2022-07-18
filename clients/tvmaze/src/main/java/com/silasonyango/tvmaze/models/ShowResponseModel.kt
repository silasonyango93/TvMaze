package com.silasonyango.tvmaze.models

data class ShowResponseModel(
    val id: Long,
    val url: String?,
    val name: String?,
    val genres: List<String>?,
    val image: Image
) {
    data class Image(val medium: String?, val original: String?)
}
