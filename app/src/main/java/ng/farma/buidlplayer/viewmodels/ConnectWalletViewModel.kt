package ng.farma.buidlplayer.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch

class ConnectWalletViewModel : ViewModel() {

    val account = MutableLiveData<String>()

    fun logAccount(accounts: List<String>) {
        viewModelScope.launch {
            if (accounts.isNotEmpty()) {
                account.postValue(accounts.first())
            }
        }
    }

}