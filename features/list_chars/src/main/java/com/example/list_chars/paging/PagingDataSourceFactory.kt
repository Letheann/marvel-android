package com.example.list_chars.paging

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.example.list_chars.model.CharMapped

class PagingDataSourceFactory(
    private val providerDataSource: PagingDataSource
) : DataSource.Factory<Int, CharMapped>() {

    var sourceData = MutableLiveData<PagingDataSource>()

    override fun create(): DataSource<Int, CharMapped> {
        val dataSource = providerDataSource
        sourceData.postValue(dataSource)
        return dataSource
    }

    fun retry() {
        if (sourceData.value?.retryGet() != null) {
            sourceData.value?.retryGet()
        } else {
            create()
        }
    }
}

