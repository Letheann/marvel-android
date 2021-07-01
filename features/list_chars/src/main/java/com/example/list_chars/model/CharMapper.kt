package com.example.list_chars.model

import com.example.core.mapper.Mapper
import com.example.core.responses.BaseResponse
import com.example.core.responses.CharResponse

private const val IMAGE_URL_FORMAT = "%s.%s"

class CharMapper : Mapper<BaseResponse<CharResponse>, List<CharMapped>> {

    override suspend fun map(from: BaseResponse<CharResponse>) =
        from.data.results.map {
            CharMapped(
                id = it.id,
                name = it.name,
                description = it.description,
                imageUrl = IMAGE_URL_FORMAT.format(
                    it.thumbnail.path.replace("http", "https"),
                    it.thumbnail.extension
                )
            )
        }
}