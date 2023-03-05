package embedika.code;

import jakarta.servlet.http.HttpSession;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import java.util.NoSuchElementException;

import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

@ResponseBody
@ControllerAdvice
public class BaseExceptionHandler {
    @ResponseStatus(NOT_FOUND)
    @ExceptionHandler(NoSuchElementException.class)
    public RedirectView noSuchElementException(HttpSession session) {
        session.setAttribute("flash", "Car with such id is not found");
        session.setAttribute("flashType", "danger");
        return new RedirectView("redirect:/api/cars");
    }
    @ResponseStatus(INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    public ModelAndView generalException(HttpSession session) {
        session.setAttribute("flash", "Something went wrong. Please, try again");
        session.setAttribute("flashType", "danger");
        return new ModelAndView("redirect:/api");
    }
    @ExceptionHandler(value = {BindException.class})
    public ModelAndView badRequestException(BindException ex, HttpSession session) {
        BindingResult bindingResult = ex.getBindingResult();

        if (bindingResult.hasErrors()) {
            String errorMessage = bindingResult.getFieldError().getDefaultMessage();
            session.setAttribute("flash", errorMessage);
            session.setAttribute("flashType", "danger");
        }

        return new ModelAndView("redirect:/api");
    }
}
