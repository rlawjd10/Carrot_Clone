package com.clone.karrot.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.paging.DataSource
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.clone.karrot.data.AppDatabase
import com.clone.karrot.data.Region

const val STATE_INIT = "STATE_INIT"
const val STATE_BLANK = "STATE_BLANK"

class RegionListViewModel(
    application: Application
) : AndroidViewModel(application) {
    /*** filterText : 검색어 ***/
    var filterTextLiveData = MutableLiveData<String>("STATE_INIT")

    private var allRegionsLiveData: LiveData<PagedList<Region>>? = null

    private val config = PagedList.Config.Builder().setEnablePlaceholders(false).setPageSize(16).build()

    init {
        setRegionsInitOrWhenFilterTextChanged()
    }

    fun getRegionsLiveData() = allRegionsLiveData

    private fun setRegionsInitOrWhenFilterTextChanged() {
        allRegionsLiveData = Transformations.switchMap(filterTextLiveData) { str -> getRegionsInitOrWhenFilterTextChanged(str) }
    }

    private fun getRegionsInitOrWhenFilterTextChanged(str: String): LiveData<PagedList<Region>> =
        when(str) {
            STATE_INIT -> getRegionsAll()
            STATE_BLANK -> getRegionsFiltered(STATE_BLANK)
            else -> getRegionsFiltered(str)
        }


    private fun getRegionsAll(): LiveData<PagedList<Region>> {
        val factory: DataSource.Factory<Int, Region> =
            AppDatabase.getInstance(getApplication()).regionDao().selectAllRegions()
        return LivePagedListBuilder(factory, config).build()
    }

    private fun getRegionsFiltered(str: String): LiveData<PagedList<Region>> {
        val factory: DataSource.Factory<Int, Region> =
            AppDatabase.getInstance(getApplication()).regionDao().selectRegionsByString("%${str}%")
        return LivePagedListBuilder(factory, config).build()
    }

}