package ufrn.com.AvaliacaoWeb.controller;

import jakarta.validation.Valid;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import ufrn.com.AvaliacaoWeb.domain.Usuario;
import ufrn.com.AvaliacaoWeb.repository.UsuarioRepository;
import ufrn.com.AvaliacaoWeb.service.UsuarioService;

@Controller
public class SecurityController {
    private final UsuarioRepository usuarioRepository;
    private final UsuarioService usuarioService;

    public SecurityController(UsuarioRepository usuarioRepository, UsuarioService usuarioService) {
        this.usuarioRepository = usuarioRepository;
        this.usuarioService = usuarioService;
    }
    @GetMapping("/cadusuario")
    public String cadUsuario(Model model) {
        Usuario usuario = new Usuario();
        model.addAttribute("usuario", usuario);
        return "cadastroUsuario";
    }
    @PostMapping("/salvarUsuario")
    public ModelAndView salva(@ModelAttribute @Valid Usuario usuario, Errors errors) {
        if(errors.hasErrors()){
            ModelAndView modelAndView = new ModelAndView("admin");
            modelAndView.addObject("errors", errors);
        }
        usuarioService.create(usuario);
        ModelAndView modelAndView = new ModelAndView("index");
        modelAndView.addObject("msg", "Cadastrado com sucesso!");
        return modelAndView;
    }
    @GetMapping("/login")
    public String getLoginPage(){
        return "login";
    }
    @GetMapping("/redirect")
    public String redirect(Authentication authentication){
        if (authentication.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"))){
            return "redirect:/admin";
        }
        if(authentication.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_USER"))){
            return "redirect:/";
        }
        return "redirect:/login";
    }
}
