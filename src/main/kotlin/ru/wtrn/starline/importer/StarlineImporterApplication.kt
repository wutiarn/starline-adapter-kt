package ru.wtrn.starline.importer

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.boot.runApplication

@SpringBootApplication
@ConfigurationPropertiesScan
class StarlineImporterApplication

fun main(args: Array<String>) {
	runApplication<StarlineImporterApplication>(*args)
}
