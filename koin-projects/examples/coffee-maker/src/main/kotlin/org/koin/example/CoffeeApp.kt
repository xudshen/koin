package org.koin.example

import org.koin.core.KoinComponent
import org.koin.core.inject
import org.koin.dsl.koinApplication
import kotlin.system.measureTimeMillis

class CoffeeApp : KoinComponent {

    val maker: CoffeeMaker by inject()
}

val coffeeKoinApp = koinApplication {
    useLogger()
    loadModules(coffeeAppModule)
}

fun main(vararg args: String) {

    koinApplication {
        useLogger()
        loadModules(coffeeAppModule)
    }.start()

    val coffeeShop = CoffeeApp()

    val appDuration = measureTimeMillis {
        coffeeShop.maker.brew()
    }

    println("executed in $appDuration ms")
}
