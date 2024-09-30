package dnai.andrew.crudapi.controller;

import dnai.andrew.crudapi.model.Text;
import dnai.andrew.crudapi.service.TextService;
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1/text")
public class TextController(@Autowired private val textService: TextService) {

    @GetMapping
    fun getAllTexts(): List<Text> = textService.findAll()


    @PostMapping
    fun createText(@RequestBody text: Text): ResponseEntity<Text> {
        val savedText = textService.save(text)
        return ResponseEntity.status(HttpStatus.CREATED).body(savedText)
    }

    @GetMapping("/{id}")
    fun getTextById(@PathVariable id: Long): ResponseEntity<Text> {
        val text = textService.findById(id)
        return if (text != null) {
            ResponseEntity.ok(text)
        } else {
            ResponseEntity.notFound().build()
        }
    }
    
    @PutMapping("/{id}")
    fun updateText(@PathVariable id: Long, @RequestBody newValue: String): ResponseEntity<Text> {
        val updatedText = textService.update(id, newValue)
        return if (updatedText != null) {
            ResponseEntity.ok(updatedText)
        } else {
            ResponseEntity.notFound().build()
        }
    }

    @DeleteMapping("/{id}")
    fun deleteText(@PathVariable id: Long): ResponseEntity<Void> {
        return if (textService.findById(id) != null) {
            textService.deleteById(id)
            ResponseEntity.noContent().build()
        } else {
            ResponseEntity.notFound().build()
        }
    }


}
