package com.clone.karrot.data

import androidx.paging.DataSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface RegionDao {
   /* //데이터 전부 가져오는 함수
    @Query("SELECT * FROM regions ORDER BY name")
    fun getAll() : List<Region>

    //충돌이 발생하는 경우 Room에 실행할 작업을 알려준다.
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(region: List<Region>)*/

    @Query("SELECT * FROM regions ORDER BY name")
    fun selectAllRegions(): DataSource.Factory<Int, Region>

    @Query("SELECT * FROM regions WHERE name LIKE :str ORDER BY name")
    fun selectRegionsByString(str: String): DataSource.Factory<Int, Region>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertRegions(regions: List<Region>)

    @Query("SELECT * FROM regions WHERE id = :regionId")
    fun selectRegionById(regionId: List<Long>): List<Region>
}