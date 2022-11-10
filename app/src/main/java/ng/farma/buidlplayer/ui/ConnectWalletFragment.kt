package ng.farma.buidlplayer.ui

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.walletconnect.android.CoreClient
import com.walletconnect.sign.client.Sign
import com.walletconnect.sign.client.SignClient
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ng.farma.buidlplayer.databinding.FragmentConnectWalletBinding
import ng.farma.buidlplayer.viewmodels.ConnectWalletViewModel

class ConnectWalletFragment : Fragment() {

    private lateinit var binding: FragmentConnectWalletBinding
    private val viewModel: ConnectWalletViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentConnectWalletBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.connectWallet.setOnClickListener {
            binding.progressBar.visibility = View.VISIBLE
            connectToWallet()
        }

        viewModel.account.observe(viewLifecycleOwner) {
            login(it.replace("eip155:1:", ""))
        }
    }

    private fun connectToWallet() {
        SignClient.setDappDelegate(object : SignClient.DappDelegate {
            override fun onSessionApproved(approvedSession: Sign.Model.ApprovedSession) {
                // Triggered when Dapp receives the session approval from wallet
                viewModel.logAccount(approvedSession.accounts)
                Log.d("TAG", "onSessionApproved: ${approvedSession.accounts}")
            }

            override fun onSessionRejected(rejectedSession: Sign.Model.RejectedSession) {
                // Triggered when Dapp receives the session rejection from wallet
                Log.d("TAG", "onSessionRejected: $rejectedSession")
            }

            override fun onSessionUpdate(updatedSession: Sign.Model.UpdatedSession) = Unit
            override fun onSessionExtend(session: Sign.Model.Session) = Unit
            override fun onSessionEvent(sessionEvent: Sign.Model.SessionEvent) = Unit
            override fun onSessionDelete(deletedSession: Sign.Model.DeletedSession) = Unit
            override fun onSessionRequestResponse(response: Sign.Model.SessionRequestResponse) =
                Unit

            override fun onConnectionStateChange(state: Sign.Model.ConnectionState) = Unit

            override fun onError(error: Sign.Model.Error) {
                Log.d("TAG", "onError: $error")
            }
        })

        val namespace = "eip155"
        val chains = listOf("eip155:1")
        val methods = listOf(
            "eth_sendTransaction",
            "personal_sign",
            "eth_sign",
            "eth_signTypedData"
        )
        val events = listOf("chainChanged", "accountChanged")
        val namespaces =
            mapOf(namespace to Sign.Model.Namespace.Proposal(chains, methods, events, null))
        val pairing = CoreClient.Pairing.create() ?: return
        val connectParams = Sign.Params.Connect(namespaces, pairing)

        SignClient.connect(connectParams, onSuccess = {
            Log.d("TAG", "connectToWallet: success $connectParams")
            val intent: Intent = Uri.parse(connectParams.pairing.uri).let {
                Intent(Intent.ACTION_VIEW, it)
            }
            startActivity(intent)
        }, onError = { error ->
            Log.d("TAG", "connectToWallet: $error")
        })
    }

    private fun login(address: String) {
        val destination =
            ConnectWalletFragmentDirections.actionConnectWalletFragmentToVideoListFragment(address)
        findNavController().navigate(destination)
    }

}