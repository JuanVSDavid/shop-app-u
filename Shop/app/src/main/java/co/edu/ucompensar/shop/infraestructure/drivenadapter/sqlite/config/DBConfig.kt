package co.edu.ucompensar.shop.infraestructure.drivenadapter.sqlite.config

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import co.edu.ucompensar.shop.infraestructure.drivenadapter.sqlite.entity.ProductEntity
import co.edu.ucompensar.shop.infraestructure.drivenadapter.sqlite.entity.UserEntity
import co.edu.ucompensar.shop.infraestructure.drivenadapter.sqlite.repository.ProductDao
import co.edu.ucompensar.shop.infraestructure.drivenadapter.sqlite.repository.UserDao

@Database(
    entities = [UserEntity::class, ProductEntity::class],
    version = 4,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun userDao(): UserDao
    abstract fun productDao(): ProductDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "shop_database"
                )
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}