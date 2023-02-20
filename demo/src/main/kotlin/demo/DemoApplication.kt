package demo

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.data.jdbc.repository.query.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.stereotype.Service
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.ResponseBody
import java.util.*

@SpringBootApplication
class DemoApplication

fun main(args: Array<String>) {
	runApplication<DemoApplication>(*args)
}

interface ContactRepository : CrudRepository<Contact, String>{
	@Query("select * from contacts")
	fun findContact(): List<Contact>

}

@Service
class ContactService(val db: ContactRepository) {

	fun findContacts(): List<Contact> = db.findContact()

	fun post(contact: Contact):Contact = db.save(contact)

	fun findOneContact(id: String): Optional<Contact> = db.findById(id)

	fun deleteOne(id: String): Unit = db.deleteById(id)
}

@RestController
class MessageResource(val service: ContactService) {
	@GetMapping("/")
	fun index(): List<Contact> = service.findContacts()

	@GetMapping("/{id}")
	fun getById(@PathVariable id:String): Optional<Contact> = service.findOneContact(id)


	@PostMapping("/")
	fun post(@RequestBody contact: Contact):ResponseEntity<Contact> {
		val newContact = service.post(contact)
		val statusCode = HttpStatus.CREATED
		return ResponseEntity(newContact, statusCode)
	}

	@DeleteMapping("/{id}")
	@ResponseBody
	fun deleteById(@PathVariable id:String): ResponseEntity<Unit> {
		val statusCode = HttpStatus.NO_CONTENT
		return ResponseEntity(service.deleteOne(id), statusCode)
	}
}

@Table("CONTACTS")
data class Contact(@Id val id: String?, val name: String, val phoneNo: String, val address: String)

data class Response(val message: String)

