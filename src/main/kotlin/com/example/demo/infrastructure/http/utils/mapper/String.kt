package com.example.demo.infrastructure.http.utils.mapper

import kotlin.contracts.ExperimentalContracts
import kotlin.contracts.contract

@OptIn(ExperimentalContracts::class)
fun String?.isNeitherNullNorBlank(): Boolean {
    contract {
        returns(true) implies (this@isNeitherNullNorBlank != null)
    }

    return !this.isNullOrBlank()
}