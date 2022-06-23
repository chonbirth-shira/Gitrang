package shira.chonbirth.gitrang

import android.content.Context
import androidx.room.Room
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

    @Singleton
    @Provides
    fun providesDatabase(
        @ApplicationContext content: Context
    ) = Room.databaseBuilder(
        content,
        Database::class.java,
        DATABASE_NAME
    ).createFromAsset("main_database.db").build()

    @Singleton
    @Provides
    fun providesDao(database: Database) = database.databaseDao()
}