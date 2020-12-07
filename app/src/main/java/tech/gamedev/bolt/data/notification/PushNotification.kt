package tech.gamedev.bolt.data.notification

data class PushNotification(
    val data: NotificationData,
    val to: String
)