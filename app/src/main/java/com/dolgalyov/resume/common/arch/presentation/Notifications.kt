package com.dolgalyov.resume.common.arch.presentation

interface ErrorEvent : UIEvent {
    object NoInternetEvent : ErrorEvent
    object SomethingWrongEvent : ErrorEvent
    object ApiKeyExpiredEvent : ErrorEvent
    object ActiveSubscriptionErrorEvent : ErrorEvent
    data class ErrorMessageEvent(val message: String) :
        ErrorEvent
}

interface LocationEvent : UIEvent {
    object GpsDisabledEvent : LocationEvent
    object LocationErrorEvent : LocationEvent
    object NoLocationPermissionsEvent : LocationEvent
}

object HideKeyboardEvent : UIEvent