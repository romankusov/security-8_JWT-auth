package org.example.jwtauth.security.filters;

import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.jwtauth.security.JwtUtil;
import org.example.jwtauth.service.SampleUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
@RequiredArgsConstructor
@Slf4j
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private SampleUserDetailsService sampleUserDetailsService;

    @Autowired
    public JwtAuthenticationFilter(SampleUserDetailsService sampleUserDetailsService) {
        this.sampleUserDetailsService = sampleUserDetailsService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) {
        try {
            String authorizationHeader = request.getHeader("Authorization");
            if (authorizationHeader == null || authorizationHeader.isBlank()) {
                filterChain.doFilter(request, response);
                return;
            }
            String jwtToken = request.getHeader("Authorization");
            String userName = JwtUtil.extractUsername(jwtToken);
            UserDetails userDetails = sampleUserDetailsService.loadUserByUsername(userName);

            if (jwtToken != null && validateToken(jwtToken, userDetails)) {
                UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities());
                token.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(token);
            }
            filterChain.doFilter(request, response);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private boolean validateToken(String token, UserDetails userDetails) {
        return JwtUtil.validateToken(token, userDetails);
    }
}
