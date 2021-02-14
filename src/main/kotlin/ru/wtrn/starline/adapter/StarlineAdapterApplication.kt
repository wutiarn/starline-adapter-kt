package ru.wtrn.starline.adapter

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.boot.runApplication

@SpringBootApplication
@ConfigurationPropertiesScan
class StarlineAdapterApplication

fun main(args: Array<String>) {
	runApplication<StarlineAdapterApplication>(*args)
}
