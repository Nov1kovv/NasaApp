package com.example.nasaapp.projectwork;

class Project {
    //SearchFragment
//1.Пользователь вводит запрос в SearchView
//2.SearchViewModel.emitSearchQuery() отправляет запрос в querySubject
//3.observeQuerySubject() делает switchMap на repository.searchImages
//4.NasaRepositoryImpl.searchImages
//5.DetailNasaRemoteDataSourceImpl.searchImages
//6.Метод NasaApiService.searchImages выполняет GET запрос к API NASA.
//7.Ответ от сервера мапится в SearchItem через DtoToDomainMapper
// 8. ViewModel обновляет UiState и фрагмент получает результат через LiveData

    //DetailFragment
//1.Фрагмент получает nasaId из аргументов
//2.DetailViewModel.setNasaId(nasaId) значение уходит в nasaIdSubject
//3.В initSubscriptions() происходит подписка на этот Subject
//4.Получает metadataUrl через getMetadataUrl(nasaId) (отдельный запрос к API)
//5.Получает ItemDetailedInfoDto по  этому url metadataUrl через getResourceInfo
//6.Получает videoLink через getVideoLink(nasaId)
//7.Результаты объединяются в пару (resourceInfo, videoUrl)
//8.Результаты мапятся через DetailedDtoToDomainMapper.map  fileInfo и videoLink отправляю в LiveData
}
