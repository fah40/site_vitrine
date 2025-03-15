package project.back.dgi.interceptor;

import java.sql.Timestamp;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import project.back.dgi.entity.User;

@Component
public class AdminInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        User user = (User) request.getSession().getAttribute("user");

        if (user == null) {
            response.sendRedirect("/"); // Redirige vers la page de connexion
            return false;
        }

        Timestamp sessionExpiration = (Timestamp) request.getSession().getAttribute("expiration");
        Timestamp currentTimestamp = new Timestamp(System.currentTimeMillis());

        if (currentTimestamp.after(sessionExpiration)) {
            request.getSession().removeAttribute("user");
            request.getSession().removeAttribute("expiration");
            response.sendRedirect("/");

            return false;
        }

        return true; // Continue l'exécution du contrôleur
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
                           ModelAndView modelAndView) throws Exception {
        // Peut être utilisé pour ajouter des attributs à la vue après exécution du contrôleur
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
            throws Exception {
        // Exécuté après la fin complète de la requête
    }
}
