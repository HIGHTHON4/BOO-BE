package com.example.global.security.jwt

import com.example.global.security.auth.AuthDetailsService
import com.example.global.security.jwt.exception.ExpiredJwtException
import com.example.global.security.jwt.exception.InvaildJwtException
import com.example.global.security.jwt.response.TokenResponse
import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jws
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Component
import java.util.*

@Component
class JwtTokenProvider(
    private val jwtProperties: JwtProperties,
    private val authDetailsService: AuthDetailsService
) {
    companion object {
        private const val ACCESS_KEY = "access_token"
        private const val REFRESH_KEY = "refresh_token"
    }

    fun generateToken(userId: String): TokenResponse {
        val accessToken = generateAccessToken(userId, ACCESS_KEY, jwtProperties.accessExp)
        return TokenResponse(accessToken)
    }

    private fun isRefreshToken(token: String?): Boolean {
        return REFRESH_KEY == getJws(token!!).header["typ"].toString()
    }

    private fun generateAccessToken(
        id: String,
        type: String,
        exp: Long
    ): String =
        Jwts.builder()
            .setSubject(id)
            .setHeaderParam("typ", type)
            .signWith(SignatureAlgorithm.HS256, jwtProperties.secretKey)
            .setExpiration(Date(System.currentTimeMillis() + exp * 1000))
            .setIssuedAt(Date())
            .compact()

    fun resolveToken(request: jakarta.servlet.http.HttpServletRequest): String? =
        request.getHeader(jwtProperties.header)?.also {
            if (it.startsWith(jwtProperties.prefix)) {
                return it.substring(jwtProperties.prefix.length)
            }
        }

    fun authentication(token: String): Authentication? {
        val body: Claims = getJws(token).body
        val userDetails: UserDetails = getDetails(body)
        return UsernamePasswordAuthenticationToken(userDetails, "", userDetails.authorities)
    }

    fun getClaimsToken(token: String): Claims {
        return Jwts.parser()
            .setSigningKey(jwtProperties.secretKey)
            .parseClaimsJws(token)
            .body
    }

    private fun getJws(token: String): Jws<Claims> {
        return try {
            Jwts.parser().setSigningKey(jwtProperties.secretKey).parseClaimsJws(token)
        } catch (e: ExpiredJwtException) {
            throw ExpiredJwtException
        } catch (e: Exception) {
            throw InvaildJwtException
        }
    }

    private fun getDetails(body: Claims): UserDetails {
        return authDetailsService.loadUserByUsername(body.subject)
    }
}
