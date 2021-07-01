
package com.example.list_chars.model

import com.example.core.responses.BaseResponse
import com.example.core.responses.CharResponse
import com.example.core.responses.CharThumbResponse
import com.example.core.responses.DataResponse
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class CharacterItemMapperTest {

    private val mapper = CharMapper()

    @Test
    fun characterMapper_WithEmptyResults_ShouldReturnEmptyList() = runBlocking {
        val response = BaseResponse(
            code = 200,
            status = "Ok",
            message = "Ok",
            data = DataResponse<CharResponse>(
                offset = 0,
                limit = 0,
                total = 0,
                count = 0,
                results = emptyList()
            )
        )

        assertTrue(mapper.map(response).isNullOrEmpty())
    }

    @Test
    fun characterMapper_WithResults_ShouldReturnParsedList() = runBlocking {
        val response = BaseResponse(
            code = 200,
            status = "Ok",
            message = "Ok",
            data = DataResponse(
                offset = 0,
                limit = 0,
                total = 1,
                count = 1,
                results = listOf(
                    CharResponse(
                        id = 1011334,
                        name = "3-D Man",
                        description = "",
                        thumbnail = CharThumbResponse(
                            path = "http://i.annihil.us/u/prod/marvel/i/mg/c/e0/535fecbbb9784",
                            extension = "jpg"
                        )
                    )
                )
            )
        )

        mapper.map(response).first().run {
            assertEquals(1011334, this.id)
            assertEquals("3-D Man", this.name)
            assertEquals("", this.description)
            assertEquals(
                "https://i.annihil.us/u/prod/marvel/i/mg/c/e0/535fecbbb9784.jpg",
                this.imageUrl
            )
        }
    }
}
