package ufrn.com.AvaliacaoWeb.controller;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.web.bind.annotation.*;
import ufrn.com.AvaliacaoWeb.domain.Comidas;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import ufrn.com.AvaliacaoWeb.repository.ComidasRepository;
import ufrn.com.AvaliacaoWeb.service.ComidasService;
import ufrn.com.AvaliacaoWeb.service.FileStorageService;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Optional;

import static java.lang.Float.sum;

@Controller
public class ComidasController {
    private final ComidasService comidasService;
    private final FileStorageService fileStorageService;
    private final ComidasRepository comidasRepository;

    public ComidasController(ComidasService comidasService, FileStorageService fileStorageService, ComidasRepository comidasRepository) {
        this.comidasService = comidasService;
        this.fileStorageService = fileStorageService;
        this.comidasRepository = comidasRepository;
    }
    @GetMapping("/index")
    public String index(Model model, HttpServletResponse response) {
        model.addAttribute("comidas",comidasService.findAll());
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy|HH:mm:ss");
        String formattedDate = formatter.format(date);
        Cookie c = new Cookie("visita", formattedDate);
        c.setMaxAge(24 * 60 * 60);
        response.addCookie(c);
        return "index";
    }
    @GetMapping("/cadastroPage")
    public String getCadastroPage(Model model){
        Comidas comidas = new Comidas();
        model.addAttribute("comidas", comidas);
        return "cadastroPage";
    }

    @PostMapping("/salvar")
    public ModelAndView salvar(@ModelAttribute @Valid Comidas comidas, Errors errors, @RequestParam("file") MultipartFile file){
        if(errors.hasErrors()){
            ModelAndView modelAndView = new ModelAndView("admin");
            modelAndView.addObject("errors", errors);
        }
        comidas.setImageUri(file.getOriginalFilename());
        fileStorageService.save(file);

        comidasService.create(comidas);
        ModelAndView modelAndView = new ModelAndView("admin");
        modelAndView.addObject("msg", "Atualização ocorreu com sucesso");
        modelAndView.addObject("comidas", comidasService.findAll());
        return modelAndView;
    }

    @GetMapping("/admin")
    public String getAdmin(Model model){
        model.addAttribute("comidas",comidasService.findAll());
        return "admin";
    }
    @GetMapping("/deletar/{id}")
    public ModelAndView deletar(@PathVariable Long id, Model model){
        comidasService.delete(id);
        ModelAndView modelAndView = new ModelAndView("index");
        modelAndView.addObject("msg", "Remoção realizada com sucesso!");
        return modelAndView;
    }

    @GetMapping("/editar/{id}")
    public ModelAndView editar(@PathVariable Long id){
        Optional<Comidas> comidas = comidasService.findById(id);
        if(comidas.isPresent()){
            ModelAndView mv = new ModelAndView("editar");
            mv.addObject("comida", comidas.get());
            return mv;
        }else{
            return new ModelAndView("redirect:/");
        }
    }
    @GetMapping("/adicionarCarrinho/{id}")
    public ModelAndView adicionarCarrinho(@PathVariable Long id, HttpSession session){
        ArrayList<Comidas> itens = (ArrayList<Comidas>) session.getAttribute("carrinho");
        if(itens == null){
            itens = new ArrayList<>();
        }
        if(comidasService.existsById(id)){
            Optional<Comidas> comidasOptional = comidasService.findById(id);
            if(comidasOptional.isPresent()){
                Comidas comidas = comidasOptional.get();
                itens.add(comidas);
                session.setAttribute("carrinho", itens);
            }
        }
        ModelAndView mv = new ModelAndView("index");
        mv.addObject("comidas", comidasService.findAll());
        mv.addObject("qtd", itens.size());
        return mv;
    }

    @GetMapping("/verCarrinho")
    public ModelAndView verCarrinho(HttpSession session){
        if(session.getAttribute("carrinho") != null){
            ArrayList<Comidas> itens = (ArrayList<Comidas>) session.getAttribute("carrinho");
            float sum = 0;
            for (Comidas comidas: itens) {
                sum += comidas.getPreco();
            }
            ModelAndView mv = new ModelAndView("carrinho");
            mv.addObject("total", sum);
            mv.addObject("comidas", itens);
            return mv;
        }else{
            ModelAndView mv = new ModelAndView("index");
            mv.addObject("msg", "Não existem itens no Carrinho");
            return mv;
        }
    }

    @GetMapping("/finalizarCompra")
    public String finalizarCompra(HttpSession session){
        session.invalidate();
        return "redirect:/index";
    }


}

