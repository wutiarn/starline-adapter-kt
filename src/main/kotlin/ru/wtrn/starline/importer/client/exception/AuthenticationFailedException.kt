package ru.wtrn.starline.importer.client.exception

class AuthenticationFailedException(responseBody: String?) : Exception("Starline authentication failed: $responseBody")