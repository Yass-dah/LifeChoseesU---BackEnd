package lcu.backend.server.config;

import org.springframework.boot.web.servlet.ServletContextInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import jakarta.servlet.ServletContext;
import jakarta.servlet.SessionCookieConfig;

@Configuration
public class CookieConfig {
    @Bean
    public ServletContextInitializer servletContextInitializer() {
        return new ServletContextInitializer() {
            @Override
            public void onStartup(ServletContext servletContext) {
                SessionCookieConfig cookieConfig = servletContext.getSessionCookieConfig();
                cookieConfig.setName("LCU_SESSION_COOKIE");
                cookieConfig.setHttpOnly(true);
                cookieConfig.setSecure(true);
                cookieConfig.setAttribute("SameSite", "None");
                cookieConfig.setAttribute("Partitioned", "");
            }
        };
    }
}