package com.example.nasaapp.ui.details

//SubComponents - Получает зависимости от родителя
//Добавляет свои
//Живёт, пока живёт родитель например, активити, фрагмент

// Нужен чтобы делить зависимости по слоям

//@Subcomponent
//interface MainComponent {
//    fun inject(activity: MainActivity)
//
//    @Subcomponent.Factory
//    interface Factory {
//        fun create(): MainComponent
//    }
//}
//
//@Component
//interface AppComponent { - родитель сабкомпонента
//    fun mainComponent(): MainComponent.Factory
//}
//
//class MainActivity : AppCompatActivity() {
//
//    @Inject lateinit var presenter: MainPresenter
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//
//        val appComponent = (application as MyApp).appComponent
//        val mainComponent = appComponent.mainComponent().create()
//        mainComponent.inject(this)
//    }
//}


