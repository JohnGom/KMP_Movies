package org.johngom.movieskmp

import org.johngom.movieskmp.data.IosRegionDataSource
import org.johngom.movieskmp.data.RegionDataSource
import org.johngom.movieskmp.data.database.getDatabaseBuilder
import org.koin.core.module.Module
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.bind
import org.koin.dsl.module
import platform.CoreLocation.CLLocationManager

actual val nativeModule: Module = module {
    single { getDatabaseBuilder() }
    factoryOf(::IosRegionDataSource) bind RegionDataSource::class
}