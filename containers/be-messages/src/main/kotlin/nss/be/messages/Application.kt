package nss.be.messages

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.domain.EntityScan
import org.springframework.boot.runApplication
import org.springframework.context.annotation.ComponentScan
import org.springframework.data.jpa.repository.config.EnableJpaRepositories
import org.springframework.kafka.annotation.EnableKafka

@EnableKafka
@EnableJpaRepositories("nss.be.messages.repository")
@EntityScan("nss.be.messages.model")
@ComponentScan(basePackages = [
	"nss.be.messages",
	"nss.be.messages.controller",
	"nss.be.messages.service",
])
@SpringBootApplication
class Application

fun main(args: Array<String>) {
	runApplication<Application>(*args)
}
