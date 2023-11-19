package com.example.ms_project

import android.util.Log
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

//ch20과 같음
class MyFirebaseMessageService : FirebaseMessagingService() {
    override fun onNewToken(p0: String) {
        super.onNewToken(p0)
        Log.d("team12","fcm token....$p0")
    }

    override fun onMessageReceived(p0: RemoteMessage) {
        super.onMessageReceived(p0)
        Log.d("team12", "fcm message...... ${p0.notification}")
        Log.d("team12", "fcm message...... ${p0.data}")
    }
}
