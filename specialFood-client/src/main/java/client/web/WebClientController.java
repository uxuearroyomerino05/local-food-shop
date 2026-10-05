package client.web;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import client.data.Credentials;
import client.data.Product;
import client.proxies.ISFServiceProxy;
import jakarta.servlet.http.HttpSession;

@Controller
public class WebClientController {
    
    private final ISFServiceProxy serviceProxy;
    
    public WebClientController(ISFServiceProxy serviceProxy) {
        this.serviceProxy = serviceProxy;
    }
    
    // Página de inicio
    @GetMapping("/")
    public String index() {
        return "index";
    }
    
    // Ir a página de login
    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }
    
    // Procesar login
    @PostMapping("/login")
    public String login(@RequestParam String username, 
                       @RequestParam String password,
                       HttpSession session,
                       RedirectAttributes redirectAttributes) {
        try {
            Credentials credentials = new Credentials(username, password);
            String token = serviceProxy.login(credentials);
            
            // Guardar token y usuario en sesión
            session.setAttribute("token", token);
            session.setAttribute("username", username);
            
            return "redirect:/products";
        } catch (RuntimeException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/login";
        }
    }
    
    // Mostrar todos los productos o filtrados
    @GetMapping("/products")
    public String showProducts(@RequestParam(required = false) String company,
                              @RequestParam(required = false) String searchType,
                              @RequestParam(required = false) String searchValue,
                              HttpSession session, 
                              Model model, 
                              RedirectAttributes redirectAttributes) {
        String token = (String) session.getAttribute("token");
        if (token == null) {
            redirectAttributes.addFlashAttribute("error", "Please login first");
            return "redirect:/login";
        }
        
        try {
            List<Product> products;
            
            // Si hay compañía seleccionada
            if (company != null && !company.isEmpty()) {
                if (searchValue != null && !searchValue.isEmpty() && 
                    searchType != null && !searchType.isEmpty()) {
                    // Filtro por compañía + criterio adicional
                    products = switch (searchType) {
                        case "name" -> serviceProxy.getProductsByNamefromCompany(company, searchValue);
                        case "description" -> serviceProxy.getProductsByDescriptionFromCompany(company, searchValue);
                        case "producer" -> serviceProxy.getProductsByProducerFromCompany(company, searchValue);
                        case "origin" -> serviceProxy.getProductsByOriginFromCompany(company, searchValue);
                        default -> serviceProxy.getProductsByCompany(company);
                    };
                } else {
                    // Solo filtro por compañía
                    products = serviceProxy.getProductsByCompany(company);
                }
            } 
            // Si solo hay búsqueda sin compañía
            else if (searchValue != null && !searchValue.isEmpty() && 
                     searchType != null && !searchType.isEmpty()) {
                products = switch (searchType) {
                    case "name" -> serviceProxy.searchProductsByName(searchValue);
                    case "description" -> serviceProxy.searchProductsByDescription(searchValue);
                    case "producer" -> serviceProxy.searchProductsByProducerName(searchValue);
                    case "origin" -> serviceProxy.searchProductsByProducerOrigin(searchValue);
                    default -> serviceProxy.getAllProducts();
                };
            } 
            // Sin filtros, mostrar todos
            else {
                products = serviceProxy.getAllProducts();
            }
            
            model.addAttribute("products", products);
            model.addAttribute("username", session.getAttribute("username"));
            model.addAttribute("company", company);
            model.addAttribute("searchType", searchType);
            model.addAttribute("searchValue", searchValue);
            return "products";
        } catch (RuntimeException e) {
            model.addAttribute("error", e.getMessage());
            model.addAttribute("products", List.of());
            model.addAttribute("username", session.getAttribute("username"));
            return "products";
        }
    }
    
    // Mostrar detalle de un producto
    @GetMapping("/product/{id}")
    public String showProductDetail(@PathVariable long id,
                                   HttpSession session,
                                   Model model,
                                   RedirectAttributes redirectAttributes) {
        String token = (String) session.getAttribute("token");
        if (token == null) {
            redirectAttributes.addFlashAttribute("error", "Please login first");
            return "redirect:/login";
        }
        
        try {
            Optional<Product> productOpt = serviceProxy.getProductById(id);
            if (productOpt.isPresent()) {
                model.addAttribute("product", productOpt.get());
                model.addAttribute("username", session.getAttribute("username"));
                return "product-detail";
            } else {
                redirectAttributes.addFlashAttribute("error", "Product not found");
                return "redirect:/products";
            }
        } catch (RuntimeException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/products";
        }
    }
    
    // Comprar producto
    @PostMapping("/product/buy")
    public String buyProduct(@RequestParam long productId,
                           @RequestParam int quantity,
                           HttpSession session,
                           RedirectAttributes redirectAttributes) {
        String token = (String) session.getAttribute("token");
        if (token == null) {
            redirectAttributes.addFlashAttribute("error", "Please login first");
            return "redirect:/login";
        }
        
        try {
            serviceProxy.buyProducts(token, productId, quantity);
            redirectAttributes.addFlashAttribute("success", 
                "Purchase successful! Quantity: " + quantity + " units");
            return "redirect:/product/" + productId;
        } catch (RuntimeException e) {
            redirectAttributes.addFlashAttribute("error", "Purchase failed: " + e.getMessage());
            return "redirect:/product/" + productId;
        }
    }
    
    // Logout
    @PostMapping("/logout")
    public String logout(HttpSession session, RedirectAttributes redirectAttributes) {
        String token = (String) session.getAttribute("token");
        if (token != null) {
            try {
                serviceProxy.logout(token);
            } catch (RuntimeException e) {
                // Log error but continue with logout
            }
        }
        
        session.invalidate();
        redirectAttributes.addFlashAttribute("message", "Logged out successfully");
        return "redirect:/";
    }
}