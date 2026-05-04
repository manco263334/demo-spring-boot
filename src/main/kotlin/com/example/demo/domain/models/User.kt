package com.example.demo.domain.models

import com.example.demo.domain.value_objects.Email
import com.example.demo.domain.value_objects.Roles
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails

data class UserModel (
    var id: String?,
    var name: String,
    var email: Email,
    var role: Roles,
    @get:JvmName("getAuthPassword")
    var password: String,
    var phone: String?,
    @get:JvmName("getAuthUsername")
    var username: String?,
    var icon: String?,

    var recipes: List<String> = emptyList()
): UserDetails {
    override fun getAuthorities(): Collection<GrantedAuthority> {
        return listOf(SimpleGrantedAuthority("ROLE_${this.role.name}"))
    }

    override fun getPassword(): String {
        return this.password
    }

    override fun getUsername(): String {
        return this.email.toString()
    }

    override fun isAccountNonExpired(): Boolean {
        return true
    }

    override fun isAccountNonLocked(): Boolean {
        return true
    }

    override fun isCredentialsNonExpired(): Boolean {
        return true
    }

    override fun isEnabled(): Boolean {
        return true
    }
}