package com.clone.karrot.data

class RegionRepository(private val regionDao: RegionDao) {
    fun selectRegionById(regionId: List<Long>): List<Region> {
        return regionDao.selectRegionById(regionId)
    }
}