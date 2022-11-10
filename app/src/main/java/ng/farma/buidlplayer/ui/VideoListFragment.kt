package ng.farma.buidlplayer.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import ng.farma.buidlplayer.adapters.VideoListAdapter
import ng.farma.buidlplayer.databinding.FragmentVideoListBinding
import ng.farma.buidlplayer.utils.Resource
import ng.farma.buidlplayer.viewmodels.VideoListViewModel

@AndroidEntryPoint
class VideoListFragment : Fragment() {

    private var address: String? = null

    private lateinit var binding: FragmentVideoListBinding
    private val videoListAdapter = VideoListAdapter { course ->
        val destination = VideoListFragmentDirections.actionVideoListFragmentToSubVideoListFragment(
            course
        )
        findNavController().navigate(destination)
    }
    private val viewModel: VideoListViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        address = requireArguments().getString("address", null)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentVideoListBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (address == null) return
        viewModel.getCourses(address!!)

        binding.apply {
            videos.adapter = videoListAdapter
            videos.layoutManager = LinearLayoutManager(context)
        }

        viewModel.courses.observe(viewLifecycleOwner) { resource ->
            when (resource) {
                is Resource.Loading -> {
                    binding.progressBar.visibility = View.VISIBLE
                }
                is Resource.Error -> {
                    Toast.makeText(context, resource.error?.message, Toast.LENGTH_SHORT).show()
                    binding.progressBar.visibility = View.GONE
                }
                else -> {
                    if (resource.data == null) {
                        Toast.makeText(context, "Something went wrong", Toast.LENGTH_SHORT).show()
                        return@observe
                    }

                    if (resource.data.isEmpty()) {
                        binding.empty.visibility = View.VISIBLE
                    } else {
                        binding.empty.visibility = View.GONE
                    }

                    videoListAdapter.setCourses(resource.data)

                    binding.progressBar.visibility = View.GONE
                }
            }
        }

    }

}