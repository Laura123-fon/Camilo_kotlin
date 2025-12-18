package com.example.app_definida.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.app_definida.data.model.CartProduct
import kotlinx.coroutines.flow.Flow

@Dao
interface CartProductDao {
    @Query("SELECT * FROM cart_products")
    fun getAll(): Flow<List<CartProduct>>

    @Query("SELECT * FROM cart_products WHERE id = :id")
    suspend fun getById(id: Long): CartProduct?

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(cartProduct: CartProduct)

    @Update
    suspend fun update(cartProduct: CartProduct)

    @Delete
    suspend fun delete(cartProduct: CartProduct)

    @Query("DELETE FROM cart_products")
    suspend fun deleteAll()
}
