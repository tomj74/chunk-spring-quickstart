package my.app;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class IceAndFireController
{
    String message = "Welcome to Westeros!";

    @RequestMapping("/hello")
    public ModelAndView showMessage(
        @RequestParam(value = "name", required = false, defaultValue = "World") String name)
    {
        ModelAndView mv = new ModelAndView("helloworld");
        mv.addObject("message", message);
        mv.addObject("name", name);
        mv.addObject("page_name", "Spring 4 MVC - Hello Chunk");
        return mv;
    }

    @RequestMapping(value={"/chunk","/"})
    public ModelAndView chunkTime()
    {
        ModelAndView mv = new ModelAndView("chunkworld");
        mv.addObject("page_name", "Chunky Time");
        return mv;
    }
}