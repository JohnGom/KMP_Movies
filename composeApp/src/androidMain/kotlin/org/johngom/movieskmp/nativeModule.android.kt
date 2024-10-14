package org.johngom.movieskmp

import android.location.Geocoder
import com.google.android.gms.location.LocationServices
import org.johngom.movieskmp.data.AndroidRegionDataSource
import org.johngom.movieskmp.data.RegionDataSource
import org.johngom.movieskmp.data.database.getDatabaseBuilder
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.Module
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.bind
import org.koin.dsl.module

actual val nativeModule: Module = module {
    single { getDatabaseBuilder(get()) }
    factory { Geocoder(get()) }
    factory { LocationServices.getFusedLocationProviderClient(androidContext()) }
    factoryOf(::AndroidRegionDataSource) bind RegionDataSource::class
}