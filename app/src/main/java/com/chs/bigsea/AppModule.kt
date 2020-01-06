package com.chs.bigsea

import com.chs.bigsea.an.AnViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

/**
 *  @author chs
 *  date: 2020-01-04 17:36
 *  des:
 */

val appViewModel = module {
    viewModel { AnViewModel() }
}


val appModule = listOf(appViewModel)