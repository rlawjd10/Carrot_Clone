package com.clone.karrot

import androidx.lifecycle.viewmodel.compose.viewModel
import com.clone.karrot.data.AppDatabase
import com.clone.karrot.viewmodel.RegionListViewModel

/*
val appModule = module {
    single { AppDatabase.getInstance(androidApplication()) }
    single(createdAtStart = false) { get<AppDatabase>().regionDao() }
    single { RegionRepository(get()) }

    viewModel { RegionListViewModel(get()) }
    //viewModel { RegionViewModel(get()) }
    //viewModel { (uid: String) -> UserViewModel(uid) }
    //viewModel { SetMyRegionViewModel() }
    //viewModel { UsedItemViewModel() }
}*/
