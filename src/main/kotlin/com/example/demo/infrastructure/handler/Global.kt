package com.example.demo.infrastructure.handler

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.authorization.AuthorizationDeniedException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class GlobalHandlerException {
    @ExceptionHandler(IllegalArgumentException::class)
    fun handleBadRequest(ex: IllegalArgumentException): ResponseEntity<Map<String, String>> {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
            .body(mapOf(
                "error" to "Datos inválidos",
                "message" to (ex.message ?: "Error desconocido")
            ))
    }

    @ExceptionHandler(AuthorizationDeniedException::class)
    fun handleAuthorizationDenied(ex: AuthorizationDeniedException): ResponseEntity<Map<String, String>> {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
            .body(mapOf(
                "error" to "Acceso denegado",
                "message" to (ex.message ?: "No has iniciado sesión")
            ))
    }

    @ExceptionHandler(AccessDeniedException::class)
    fun handleAccessDenied(ex: AccessDeniedException): ResponseEntity<Map<String, String>> {
        return ResponseEntity.status(HttpStatus.FORBIDDEN)
            .body(mapOf(
                    "error" to "Acceso denegado",
                    "message" to "No tienes los permisos necesarios para realizar esta acción.",
                    "details" to (ex.message ?: "Sin detalles adicionales")
            ))
    }

    @ExceptionHandler(NoSuchElementException::class)
    fun handleNotFound(ex: NoSuchElementException): ResponseEntity<Map<String, String>> {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
            .body(mapOf(
                "error" to "No encontrado",
                "message" to (ex.message ?: "El recurso no existe")
            ))
    }

    @ExceptionHandler(Exception::class)
    fun handleGeneralError(ex: Exception): ResponseEntity<Map<String, String>> {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
            .body(mapOf(
                "error" to "Error interno",
                "message" to "Algo explotó en el servidor"
            ))
    }
}