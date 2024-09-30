package dnai.andrew.crudapi.service

import dnai.andrew.crudapi.repository.TextRepository
import org.springframework.stereotype.Service
import dnai.andrew.crudapi.model.Text

@Service
class TextService(private val textRepository: TextRepository) {
    fun findAll(): List<Text> = textRepository.findAll()

    fun findById(id: Long): Text? = textRepository.findById(id).orElse(null)

    fun save(text: Text): Text = textRepository.save(text)

    fun update(id: Long, newValue: String): Text? {
        val text = textRepository.findById(id).orElse(null)
        text?.let {
            val updatedText = it.copy(value = newValue)
            return textRepository.save(updatedText)
        }
        return null
    }

    fun deleteById(id: Long) = textRepository.deleteById(id)
}