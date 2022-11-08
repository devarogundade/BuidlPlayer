package ng.farma.buidlplayer.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import dagger.hilt.android.AndroidEntryPoint
import ng.farma.buidlplayer.R
import ng.farma.buidlplayer.databinding.FragmentSubVideoListBinding

@AndroidEntryPoint
class SubVideoListFragment : Fragment() {

    private lateinit var binding: FragmentSubVideoListBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_sub_video_list, container, false)
    }

}