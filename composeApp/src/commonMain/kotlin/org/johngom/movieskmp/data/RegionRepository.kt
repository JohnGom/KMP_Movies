package org.johngom.movieskmp.data

interface RegionDataSource {
    suspend fun fetchRegion(): String
}

const val DEFAULT_REGION = "US"

class RegionRepository(
    private val dataSource: RegionDataSource
) {
    suspend fun fetchRegion(): String {
        return dataSource.fetchRegion()
    }
}