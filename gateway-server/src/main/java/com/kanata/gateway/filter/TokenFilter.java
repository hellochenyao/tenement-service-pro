package com.kanata.gateway.filter;

import com.alibaba.fastjson.JSONObject;
import com.kanata.core.dto.jwt.JwtDataDto;
import com.kanata.core.exception.InsufficientAuthException;
import com.kanata.core.util.JwtUtils;
import com.kanata.gateway.dto.ResponseDto;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 * @author chenyao
 * date 2020-08-19
 */

public class TokenFilter implements GlobalFilter, Ordered {

    @Value("${ignore.check.url}")
    private List<String> filterUrls;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        //从请求头中取得token
        String token = exchange.getRequest().getHeaders().getFirst("Authorization");
        String requestPath = exchange.getRequest().getPath().value();
        //配置某些路径不进行权限校验
        for (String filterUrl : filterUrls) {
            if (!requestPath.startsWith(filterUrl)) {
                return chain.filter(exchange);
            }
        }
        ServerHttpResponse response = exchange.getResponse();
        if (StringUtils.isEmpty(token)) {
            response.setStatusCode(HttpStatus.UNAUTHORIZED);
            ResponseDto responseDto = new ResponseDto(InsufficientAuthException.CodeOption.INVALID_AUTH_AUTHORIZATION.getSubCode(), InsufficientAuthException.CodeOption.INVALID_AUTH_AUTHORIZATION.getSubMsg());
            return authResponse(response, responseDto);
        }
        JwtDataDto jwtDataDto = JwtUtils.parseToken(token);
        if (jwtDataDto == null || jwtDataDto.getUserId() == 0) {
            //token过期
            response.setStatusCode(HttpStatus.UNAUTHORIZED);
            ResponseDto responseDto = new ResponseDto(InsufficientAuthException.CodeOption.INVALID_AUTH_TOKEN.getSubCode(), InsufficientAuthException.CodeOption.INVALID_AUTH_TOKEN.getSubMsg());
            return authResponse(response, responseDto);
        }
        return chain.filter(exchange);
    }

    @Override
    public int getOrder() {
        return 111;
    }

    private Mono<Void> authResponse(ServerHttpResponse response, ResponseDto responseDto) {
        response.getHeaders().add("Content-Type", "application/json;charset=UTF-8");
        byte[] responseByte = JSONObject.toJSONString(responseDto).getBytes(StandardCharsets.UTF_8);
        DataBuffer buffer = response.bufferFactory().wrap(responseByte);
        return response.writeWith(Flux.just(buffer));
    }
}
