package dnai.andrew.crudapi

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.domain.EntityScan
import org.springframework.boot.runApplication
import org.springframework.context.annotation.ComponentScan
import org.springframework.data.jpa.repository.config.EnableJpaRepositories

@EnableJpaRepositories("dnai.andrew.crudapi.repository")
@EntityScan("dnai.andrew.crudapi.model")
@ComponentScan(basePackages = [
	"dnai.andrew.crudapi",
	"dnai.andrew.crudapi.controller",
	"dnai.andrew.crudapi.service",
])
@SpringBootApplication
class Application

fun main(args: Array<String>) {
	runApplication<Application>(*args)
}
