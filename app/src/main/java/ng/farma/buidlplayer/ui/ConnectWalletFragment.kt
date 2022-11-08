package ng.farma.buidlplayer.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.walletconnect.android.CoreClient
import com.walletconnect.sign.client.Sign
import com.walletconnect.sign.client.SignClient
import ng.farma.buidlplayer.databinding.FragmentConnectWalletBinding

class ConnectWalletFragment : Fragment() {

    private lateinit var binding: FragmentConnectWalletBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentConnectWalletBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        SignClient.setDappDelegate(dappDelegate)
//        binding.connectWallet.setOnClickListener { connectToWallet() }
        binding.connectWallet.setOnClickListener { login("0XBE25761AE1F024A991ED081532B3C89DF7F28D02") }
    }

    private fun connectToWallet() {
        val namespace = Chains.ETHEREUM_MAIN.chainNamespace
        val chains = listOf(Chains.ETHEREUM_MAIN.chainId)
        val methods = Chains.ETHEREUM_MAIN.methods
        val events = Chains.ETHEREUM_MAIN.events
        val namespaces =
            mapOf(namespace to Sign.Model.Namespace.Proposal(chains, methods, events, null))
        val pairing = CoreClient.Pairing.create() ?: return
        val connectParams = Sign.Params.Connect(namespaces, pairing)

        SignClient.connect(connectParams, onSuccess = {}, onError = { error ->
            Log.d("TAG", "connectToWallet: $error")
        })
    }

    private fun login(address: String) {
        val destination =
            ConnectWalletFragmentDirections.actionConnectWalletFragmentToVideoListFragment(address)
        findNavController().navigate(destination)
    }

    private val dappDelegate = object : SignClient.DappDelegate {
        override fun onSessionApproved(approvedSession: Sign.Model.ApprovedSession) {
            // Triggered when Dapp receives the session approval from wallet
            if (approvedSession.accounts.isEmpty()) return
            login(approvedSession.accounts[0])
        }

        override fun onSessionRejected(rejectedSession: Sign.Model.RejectedSession) {
            // Triggered when Dapp receives the session rejection from wallet
            Log.d("TAG", "onSessionRejected: $rejectedSession")
        }

        override fun onSessionUpdate(updatedSession: Sign.Model.UpdatedSession) = Unit
        override fun onSessionExtend(session: Sign.Model.Session) = Unit
        override fun onSessionEvent(sessionEvent: Sign.Model.SessionEvent) = Unit
        override fun onSessionDelete(deletedSession: Sign.Model.DeletedSession) = Unit
        override fun onSessionRequestResponse(response: Sign.Model.SessionRequestResponse) = Unit
        override fun onConnectionStateChange(state: Sign.Model.ConnectionState) = Unit

        override fun onError(error: Sign.Model.Error) {
            Log.d("TAG", "onError: $error")
        }
    }

    enum class Chains(
        val chainName: String,
        val chainNamespace: String,
        val chainReference: String,
//        @DrawableRes val icon: Int,
        val methods: List<String>,
        val events: List<String>,
        val order: Int,
        val chainId: String = "$chainNamespace:$chainReference"
    ) {

        ETHEREUM_MAIN(
            chainName = "Ethereum",
            chainNamespace = Info.Eth.chain,
            chainReference = "1",
//            icon = R.drawable.logo,
            methods = Info.Eth.defaultMethods,
            events = Info.Eth.defaultEvents,
            order = 1
        )
    }

    sealed class Info {
        abstract val chain: String
        abstract val defaultEvents: List<String>
        abstract val defaultMethods: List<String>

        object Eth : Info() {
            override val chain = "eip155"
            override val defaultEvents: List<String> = listOf("chainChanged", "accountChanged")
            override val defaultMethods: List<String> = listOf(
                "eth_sendTransaction",
                "personal_sign",
                "eth_sign",
                "eth_signTypedData"
            )
        }

        object Cosmos : Info() {
            override val chain = "cosmos"
            override val defaultEvents: List<String> = listOf("chainChanged", "accountChanged")
            override val defaultMethods: List<String> = listOf(
                "cosmos_signDirect",
                "cosmos_signAmino"
            )
        }
    }

}