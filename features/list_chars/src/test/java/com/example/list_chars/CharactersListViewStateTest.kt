package com.example.list_chars

import org.junit.Assert
import org.junit.Test

class CharactersListViewStateTest {

    lateinit var state: CharsListViewState

    @Test
    fun setStateAsLoaded_ShouldBeSettled() {
        state = CharsListViewState.Load
        Assert.assertTrue(state.load())
        Assert.assertFalse(state.loading())
        Assert.assertFalse(state.loadingFinish())
        Assert.assertFalse(state.error())
    }

    @Test
    fun setStateAsLoading_ShouldBeSettled() {
        state = CharsListViewState.Loading
        Assert.assertTrue(state.loading())
        Assert.assertFalse(state.load())
        Assert.assertFalse(state.loadingFinish())
        Assert.assertFalse(state.error())
    }

    @Test
    fun setStateAsAddLoading_ShouldBeSettled() {
        state = CharsListViewState.LoadingFinish
        Assert.assertTrue(state.loadingFinish())
        Assert.assertFalse(state.load())
        Assert.assertFalse(state.loading())
        Assert.assertFalse(state.error())
    }
    
    @Test
    fun setStateAsAddError_ShouldBeSettled() {
        state = CharsListViewState.Error
        Assert.assertTrue(state.error())
        Assert.assertFalse(state.load())
        Assert.assertFalse(state.loading())
        Assert.assertFalse(state.loadingFinish())
  
    }
    
}
