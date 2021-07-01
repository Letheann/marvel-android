package com.example.list_chars.model

import org.junit.Assert
import org.junit.Test

class CharacterItemTest {

    @Test
    fun createCharacterItem_ShouldAddCorrectAttributes() {
        val id = 131231L
        val name = "A.I.M"
        val description = "AIM is a terrorist organization bent on destroying the world."
        val imageUrl = "http://i.annihil.us/u/prod/marvel/i/mg/c/e0/535fecbbb9784.jpg"

        val character = CharMapped(
            id = id,
            name = name,
            description = description,
            imageUrl = imageUrl
        )

        Assert.assertEquals(id, character.id)
        Assert.assertEquals(name, character.name)
        Assert.assertEquals(description, character.description)
        Assert.assertEquals(imageUrl, character.imageUrl)
    }
}
