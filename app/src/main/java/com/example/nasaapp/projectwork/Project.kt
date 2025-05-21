package com.example.nasaapp.projectwork

class Project {
    //SearchFragment
//1.Пользователь вводит запрос в SearchView
//2.SearchViewModel.emitSearchQuery() отправляет запрос в Subject
//3.observeQuerySubject() делает switchMap на repository.searchImages
//4.NasaRepositoryImpl.searchImages
//5.DetailNasaRemoteDataSourceImpl.searchImages
//6.NasaApiService.searchImages  GET запрос
//7.Ответ от сервера мапится в SearchItem через DtoToDomainMapper

    //DetailFragment
//1.Фрагмент получает nasaId из аргументов
//2.DetailViewModel.setNasaId(nasaId) значение уходит в nasaIdSubject
//3.В initSubscriptions()
//4.Получает metadataUrl через getMetadataUrl(nasaId)
//5.Получает ItemDetailedInfoDto по metadataUrl через getResourceInfo
//6.Получает videoLink через getVideoLink(nasaId)
//7.Результаты объединяются в пару (resourceInfo, videoUrl)
//8.Результаты мапятся через DetailedDtoToDomainMapper.map  fileInfo и videoLink публикуются в LiveData

}