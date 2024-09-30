package dnai.andrew.crudapi.model

import jakarta.persistence.Entity
import jakarta.persistence.Table
import jakarta.persistence.Id
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType

/**
 * 
 * @param id 
 * @param value
 */

@Entity
@Table(name = "text")
data class Text(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,
    val value: String
)