package lcu.backend.server.filter;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Collection;

@Component
@Order(1)
public class LoginFilter implements Filter {
    @Override
    public void doFilter(ServletRequest servletRequest,
                         ServletResponse servletResponse,
                         FilterChain filterChain)
            throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        if ("OPTIONS".equalsIgnoreCase(request.getMethod())) {
            filterChain.doFilter(servletRequest, servletResponse);
            return;
        }
        String path = request.getServletPath();
        HttpSession session = request.getSession(false);
        String user = session != null ? (String) session.getAttribute("username") : null;
        if (path.startsWith("/session") || path.equals("/help-requests/countries") || user != null) {
            filterChain.doFilter(servletRequest, servletResponse);
            appendPartitionedCookie(response);
            return;
        }
        response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
    }

    private void appendPartitionedCookie(HttpServletResponse response) {
        Collection<String> cookieHeaders = response.getHeaders(HttpHeaders.SET_COOKIE);
        if (cookieHeaders != null && !cookieHeaders.isEmpty()) {
            boolean first = true;
            for (String cookie : cookieHeaders) {
                if (cookie.contains("LCU_SESSION_COOKIE") && !cookie.contains("Partitioned")) {
                    if (!cookie.contains("SameSite"))
                        cookie += "; SameSite=None";
                    cookie += "; Partitioned";
                }

                if (first) {
                    response.setHeader(HttpHeaders.SET_COOKIE, cookie);
                    first = false;
                } else response.addHeader(HttpHeaders.SET_COOKIE, cookie);
            }
        }
    }
}