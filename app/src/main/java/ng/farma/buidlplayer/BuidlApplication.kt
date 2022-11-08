package ng.farma.buidlplayer

import android.app.Application
import android.util.Log
import com.google.firebase.FirebaseApp
import com.walletconnect.android.Core
import com.walletconnect.android.CoreClient
import com.walletconnect.android.relay.ConnectionType
import com.walletconnect.sign.client.Sign
import com.walletconnect.sign.client.SignClient
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class BuidlApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        val projectId = "15bf4c2557d71eaa602a7f8e0a7ee219"
        val relayUrl = "relay.walletconnect.com"
        val serverUrl = "wss://$relayUrl?projectId=$projectId"
        val connectionType = ConnectionType.AUTOMATIC
        val appMetaData = Core.Model.AppMetaData(
            name = "Buidl Player",
            description = "Watch buidl videos with this app",
            url = "https://buidl.netlify.app",
            icons = listOf("https://buidl.netlify.app/images/logo.png"),
            redirect = "kotlin-dapp-wc:/request"
        )

        CoreClient.initialize(
            relayServerUrl = serverUrl,
            connectionType = connectionType,
            application = this,
            metaData = appMetaData
        )

        val init = Sign.Params.Init(core = CoreClient)
        SignClient.initialize(init) { error -> Log.d("Application", "onCreate: $error") }
    }


}