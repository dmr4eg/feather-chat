package dnai.andrew.crudapi.repository;
import dnai.andrew.crudapi.model.Text
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository



@Repository
interface TextRepository : JpaRepository<Text, Long>