package br.com.fausto.weathernow.di

import br.com.fausto.weathernow.data.remote.WeatherRemoteDataSourceImpl
import br.com.fausto.weathernow.data.remote.WeatherRemoteDatasource
import br.com.fausto.weathernow.data.repository.WeatherRepository
import br.com.fausto.weathernow.data.repository.WeatherRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class WeatherModule {

    @Singleton
    @Binds
    abstract fun bindWeatherRepository(
        weatherRepository: WeatherRepositoryImpl
    ): WeatherRepository

    @Singleton
    @Binds
    abstract fun bindWeatherRemoteDataSource(
        weatherRemoteDataSourceImpl: WeatherRemoteDataSourceImpl
    ): WeatherRemoteDatasource
}