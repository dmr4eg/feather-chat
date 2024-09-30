package nss.be.messages.controller

import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.servlet.view.RedirectView

@RestController
@RequestMapping("/")
class HomeController {
    @RequestMapping("/")
    fun index() = RedirectView("/swagger-ui.html")
}