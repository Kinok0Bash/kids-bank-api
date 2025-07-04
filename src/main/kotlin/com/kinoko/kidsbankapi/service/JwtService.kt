package com.kinoko.kidsbankapi.service

import com.kinoko.kidsbankapi.entity.UserEntity
import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import io.jsonwebtoken.io.Decoders
import io.jsonwebtoken.security.Keys
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Service
import java.security.Key
import java.util.*
import java.util.function.Function

@Service
class JwtService {

    @Value("\${token.secret.key}")
    private val jwtSigningKey = ""
    private val logger: Logger = LoggerFactory.getLogger(JwtService::class.java)

    fun extractUsername(token: String): String = extractClaim(token, Claims::getSubject)

    fun <T> extractClaim(token: String, claimsResolver: Function<Claims, T>): T =
        claimsResolver.apply(extractAllClaims(token))

    fun getSingInKey(): Key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtSigningKey))

    fun generateTokens(userDetails: UserEntity): ArrayList<String> {
        logger.info("Beginning of generate token")
        val header = HashMap<String, Any>()
        header["typ"] = "JWT"
        header["alg"] = "HS256"

        logger.debug("Tokens for ${userDetails.login} has been generated")

        val tokens = ArrayList<String>()

        tokens.add(generateAccessToken(header, userDetails))
        tokens.add(generateRefreshToken(header, userDetails))

        return tokens
    }

    fun isTokenValid(token: String, userDetails: UserDetails): Boolean {
        val username = extractUsername(token)
        if (isTokenExpired(token)) throw Exception("Срок действия токена истек")
        return username == userDetails.username && !isTokenExpired(token)
    }

    private fun isTokenExpired(token: String): Boolean = extractExpiration(token).before(Date())

    private fun extractExpiration(token: String): Date = extractClaim(token, Claims::getExpiration)

    private fun generateAccessToken(header: Map<String, Any>, userDetails: UserDetails): String =
        Jwts.builder()
            .setHeader(header)
            .setSubject(userDetails.username)
            .addClaims(HashMap())
            .setIssuedAt(Date(System.currentTimeMillis()))
            .setExpiration(Date(System.currentTimeMillis() + 1000 * 60 * 30))
            .signWith(getSingInKey(), SignatureAlgorithm.HS256)
            .compact()

    private fun generateRefreshToken(header: Map<String, Any>, userDetails: UserDetails): String =
        Jwts.builder()
            .setHeader(header)
            .setSubject(userDetails.username)
            .addClaims(HashMap())
            .setIssuedAt(Date(System.currentTimeMillis()))
            .setExpiration(Date(System.currentTimeMillis() + 1000.toLong() * 60 * 60 * 24 * 30))
            .signWith(getSingInKey(), SignatureAlgorithm.HS256)
            .compact()

    private fun extractAllClaims(token: String): Claims =
        Jwts
            .parserBuilder()
            .setSigningKey(getSingInKey())
            .build()
            .parseClaimsJws(token.substring(7))!!.body

}
