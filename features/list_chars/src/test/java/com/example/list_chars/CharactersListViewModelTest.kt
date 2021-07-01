

package com.example.list_chars

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.example.core.network.StatesRequest
import com.example.list_chars.paging.PagingDataSource
import com.example.list_chars.paging.PagingDataSourceFactory
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class CharactersListViewModelTest {

    @ExperimentalCoroutinesApi
    @get:Rule
    var coroutinesTestRule = CoroutineRule()

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @MockK(relaxed = true)
    lateinit var factoryPagingSource: PagingDataSourceFactory
    @MockK(relaxed = true)
    lateinit var stateObserver: Observer<CharsListViewState>

    lateinit var viewModel: CharsViewModel

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
    }

    @Test
    fun networkSuccessAdditionalCharacters_ShouldBeLoadedState() {
        val networkState = StatesRequest.SuccessRequest(
            isNew = true
        )
        val fakePageDataSource = FakeCharactersPageDataSource(networkState)
        val fakeSourceLiveData = MutableLiveData<PagingDataSource>(fakePageDataSource)
        every {
            factoryPagingSource.sourceData
        } returns fakeSourceLiveData

        viewModel = CharsViewModel(factoryPagingSource = factoryPagingSource)
        viewModel.state.observeForever(stateObserver)

        val expectedState = CharsListViewState.Load
        assertEquals(expectedState, viewModel.state.value)
        verify { stateObserver.onChanged(expectedState) }
    }

    @Test
    fun networkSuccessCharacters_ShouldBeLoadedState() {
        val networkState = StatesRequest.SuccessRequest()
        val fakePageDataSource = FakeCharactersPageDataSource(networkState)
        val fakeSourceLiveData = MutableLiveData<PagingDataSource>(fakePageDataSource)
        every {
            factoryPagingSource.sourceData
        } returns fakeSourceLiveData

        viewModel = CharsViewModel(factoryPagingSource = factoryPagingSource)
        viewModel.state.observeForever(stateObserver)

        val expectedState = CharsListViewState.Load
        assertEquals(expectedState, viewModel.state.value)
        verify { stateObserver.onChanged(expectedState) }
    }

    @Test
    fun networkLoadingCharacters_ShouldBeLoadingState() {
        val networkState = StatesRequest.Loading()
        val fakePageDataSource = FakeCharactersPageDataSource(networkState)
        val fakeSourceLiveData = MutableLiveData<PagingDataSource>(fakePageDataSource)
        every {
            factoryPagingSource.sourceData
        } returns fakeSourceLiveData

        viewModel = CharsViewModel(factoryPagingSource = factoryPagingSource)
        viewModel.state.observeForever(stateObserver)

        val expectedState = CharsListViewState.Loading
        assertEquals(expectedState, viewModel.state.value)
        verify { stateObserver.onChanged(expectedState) }
    }

    @Test
    fun networkAdditionalLoadingCharacters_ShouldBeAddLoadingState() {
        val networkState = StatesRequest.Loading(
            isNew = true
        )
        val fakePageDataSource = FakeCharactersPageDataSource(networkState)
        val fakeSourceLiveData = MutableLiveData<PagingDataSource>(fakePageDataSource)
        every {
            factoryPagingSource.sourceData
        } returns fakeSourceLiveData

        viewModel = CharsViewModel(factoryPagingSource = factoryPagingSource)
        viewModel.state.observeForever(stateObserver)

        val expectedState = CharsListViewState.LoadingFinish
        assertEquals(expectedState, viewModel.state.value)
        verify { stateObserver.onChanged(expectedState) }
    }

    @Test
    fun networkAdditionalErrorCharacters_ShouldBeAddErrorState() {
        val networkState = StatesRequest.Error(true)
        val fakePageDataSource = FakeCharactersPageDataSource(networkState)
        val fakeSourceLiveData = MutableLiveData<PagingDataSource>(fakePageDataSource)
        every {
            factoryPagingSource.sourceData
        } returns fakeSourceLiveData

        viewModel = CharsViewModel(factoryPagingSource = factoryPagingSource)
        viewModel.state.observeForever(stateObserver)

        val expectedState = CharsListViewState.Error
        assertEquals(expectedState, viewModel.state.value)
        verify { stateObserver.onChanged(expectedState) }
    }

    inner class FakeCharactersPageDataSource(
        forceNetworkState: StatesRequest
    ) : PagingDataSource(
        scope = mockk(),
        mapper = mockk(),
        marvelRepository = mockk()
    ) {
        init {
            states.postValue(forceNetworkState)
        }
    }
}
