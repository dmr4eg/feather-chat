package nss.be.messages.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.data.redis.serializer.GenericToStringSerializer
import org.springframework.data.redis.serializer.StringRedisSerializer

@Configuration
class RedisConfig {
    @Bean
    fun redisConnectionFactory(): LettuceConnectionFactory {
        // This assumes Redis is running locally on the default port (6379)
        return LettuceConnectionFactory("localhost", 6379)
    }

    @Bean
    fun redisTemplate(): RedisTemplate<String, Any> {
        val template = RedisTemplate<String, Any>()
        template.setConnectionFactory(redisConnectionFactory())

        // Use String serialization for keys and generic object serialization for values
        template.keySerializer = StringRedisSerializer()
        template.valueSerializer = GenericToStringSerializer(Any::class.java)
        return template
    }
}
