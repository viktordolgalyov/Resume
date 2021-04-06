package com.dolgalyov.resume.common.arch.presentation

interface ErrorEvent : UIEvent {
    object NoInternetEvent : ErrorEvent
    object SomethingWrongEvent : ErrorEvent
    data class ErrorMessageEvent(val message: String) : ErrorEvent
}

object HideKeyboardEvent : UIEvent