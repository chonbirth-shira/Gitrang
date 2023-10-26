package shira.chonbirth.gitrang

import android.content.Context
import androidx.room.Room
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import shira.chonbirth.gitrang.data.Database
import shira.chonbirth.gitrang.util.Constants.DATABASE_NAME
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    val MIGRATION_1_2: Migration = object : Migration(1, 2) {
        override fun migrate(db: SupportSQLiteDatabase) {
            // Since we didn't alter the table, there's nothing else to do here.
        }
    }
    val MIGRATION_2_3: Migration = object : Migration(2, 3) {
        override fun migrate(db: SupportSQLiteDatabase) {
            // Since we didn't alter the table, there's nothing else to do here.
//            db.execSQL()
        }
    }

    @Singleton
    @Provides
    fun providesDatabase(
        @ApplicationContext content: Context
    ) = Room.databaseBuilder(
        content,
        Database::class.java,
        DATABASE_NAME
//    ).createFromAsset("data.sql").build()
    ).createFromAsset("main_database.db").addMigrations(MIGRATION_1_2, MIGRATION_2_3).build()

    @Singleton
    @Provides
    fun providesDao(database: Database) = database.databaseDao()
}