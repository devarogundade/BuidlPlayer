package ng.farma.buidlplayer

import android.app.Application
import android.util.Log
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

        val projectId = "f6651638454bb9d2b2e0641c03ed6a5c"
        val relayUrl = "relay.walletconnect.com"
        val serverUrl = "wss://$relayUrl?projectId=$projectId"
        val connectionType = ConnectionType.AUTOMATIC
        val appMetaData = Core.Model.AppMetaData(
            name = "Buidl Player",
            description = "Watch buidl videos with this app",
            url = "xxx",
            icons = listOf("xxx"),
            redirect = "kotlin-dapp-wc:/request"
        )

        CoreClient.initialize(
            relayServerUrl = serverUrl,
            connectionType = connectionType,
            application = this,
            metaData = appMetaData
        )

        val init = Sign.Params.Init(CoreClient)
        SignClient.initialize(init) { error -> Log.d("Application", "onCreate: $error") }
    }


}