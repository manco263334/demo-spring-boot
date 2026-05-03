package com.example.demo.infrastructure.http.service

import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import io.jsonwebtoken.security.Keys
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Service
import java.security.Key
import java.util.Date
import javax.crypto.SecretKey

@Service
class JWTService {
    private final val SECRET_KEY = "s7a8yd78sagdgsa789yd97saydowaitwd7tw9qdqw7idtqwdtqw7dqwugwqgt77twqwq"

    fun getToken(user: UserDetails): String {
        return getToken(mapOf(), user)
    }

    fun getToken(extraClaims: Map<String, Any>, user: UserDetails): String {
        return Jwts
            .builder()
            .claims(extraClaims)
            .subject(user.username)
            .issuedAt(Date(System.currentTimeMillis()))
            .expiration(Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24 * 7))
            .signWith(getKey())
            .compact()
    }

    fun getUsernameFromToken(token: String): String? {
        return getClaim(token, Claims::getSubject)
    }

    fun isTokenValid(token: String, details: UserDetails): Boolean {
        val username = getUsernameFromToken(token)
        return username == details.username && !isTokenExpired(token)
    }

    fun <T>getClaim(token: String, claimsResolver: (Claims) -> T): T {
        val claims = getAllClaim(token)
        return claimsResolver(claims)
    }

    private fun getExpiration(token: String): Date {
        return getClaim(token, Claims::getExpiration)
    }

    private fun isTokenExpired(token: String): Boolean {
        return getExpiration(token).before(Date())
    }

    private fun getAllClaim(token: String): Claims {
        return Jwts
            .parser()
            .verifyWith(getKey() as SecretKey)
            .build()
            .parseSignedClaims(token)
            .payload
    }

    private fun getKey(): Key {
        val keyBytes = SECRET_KEY.toByteArray()
        return Keys.hmacShaKeyFor(keyBytes)
    }
}