package tech.gamedev.bolt.data.notification

import com.squareup.okhttp.ResponseBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST
import tech.gamedev.bolt.data.Constants.CONTENT_TYPE
import tech.gamedev.bolt.data.Constants.SERVER_KEY

interface NotificationAPI {

    @Headers("Authorization: key=$SERVER_KEY", "Content-Type:$CONTENT_TYPE")
    @POST("fcm/send")
    suspend fun postNotification(
        @Body notification: PushNotification
    ): Response<ResponseBody>
}