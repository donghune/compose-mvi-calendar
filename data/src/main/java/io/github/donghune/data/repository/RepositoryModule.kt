package io.github.donghune.data.repository

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.github.donghune.domain.repository.DayRecordRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindDayRecordRepository(
        dayRecordRepository: DayRecordRepositoryImpl
    ): DayRecordRepository
}