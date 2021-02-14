package ru.wtrn.starline.adapter.client.exception

class AuthenticationFailedException(responseBody: String?) : Exception("Starline authentication failed: $responseBody")