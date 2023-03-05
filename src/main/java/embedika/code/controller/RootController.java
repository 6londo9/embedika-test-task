package embedika.code.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
@RequestMapping("${base-url}")
public class RootController {
    @Operation(summary = "Show root that can add cars to database")
    @ApiResponse(responseCode = "200", description = "The page successfully loaded")
    @GetMapping
    @ResponseStatus(value = HttpStatus.OK)
    public ModelAndView root(HttpSession session) {
        ModelAndView mv = new ModelAndView("urls/index.html");
        String flash = (String) session.getAttribute("flash");
        String flashType = (String) session.getAttribute("flashType");
        if (flash != null && flashType != null) {
            mv.addObject("flash", flash);
            mv.addObject("flashType", flashType);
            session.removeAttribute("flash");
            session.removeAttribute("flashType");
        }
        return mv;
    }
}
