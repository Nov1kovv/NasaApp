package com.example.nasaapp.ui.details

//SubComponents - компонент с зависимостью от другого компонента
//Добавляет свои
//Живёт, пока живёт родитель например, активити, фрагмент
// Нужен чтобы делить зависимости по слоям

// TODO: понять почему subcomponent плохо
// TODO: Реализовать feature api-impl архитектуру с помощью Игоря в самом конце
//
//@Component
//interface AppComponent { - родитель сабкомпонента
//    fun mainComponent(): MainComponent.Factory
//}

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


