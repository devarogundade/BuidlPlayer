package ng.farma.buidlplayer.ui

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import dagger.hilt.android.AndroidEntryPoint
import ng.farma.buidlplayer.adapters.SectionListAdapter
import ng.farma.buidlplayer.databinding.FragmentSubVideoListBinding
import ng.farma.buidlplayer.domain.models.SubscribedCourse
import ng.farma.buidlplayer.utils.Resource
import ng.farma.buidlplayer.viewmodels.SectionListViewModel

@AndroidEntryPoint
class SubVideoListFragment : Fragment() {

    private lateinit var sectionListAdapter: SectionListAdapter
    private val viewModel: SectionListViewModel by viewModels()

    private var course: SubscribedCourse? = null

    private lateinit var binding: FragmentSubVideoListBinding
    private lateinit var previewPlayer: ExoPlayer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        course = requireArguments().getSerializable("course") as SubscribedCourse?
        sectionListAdapter =
            SectionListAdapter(course?.subscription?.viewed ?: emptyList()) { section ->
                val destination =
                    SubVideoListFragmentDirections.actionSubVideoListFragmentToPlayerFragment(
                        section
                    )
                findNavController().navigate(destination)
            }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSubVideoListBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (course == null) return
        binding.toolbar.title = course!!.course.name
        binding.toolbar.setNavigationOnClickListener {
            if (previewPlayer.isPlaying) {
                previewPlayer.stop()
            }
            findNavController().popBackStack()
        }

        viewModel.getSections(course!!.courseId)

        previewPlayer = ExoPlayer.Builder(requireContext()).build()
        binding.preview.player = previewPlayer
        val mediaItem = MediaItem.fromUri(course!!.course.preview)
        previewPlayer.addMediaItem(mediaItem)
        previewPlayer.prepare()

        binding.apply {
            sections.adapter = sectionListAdapter
            sections.layoutManager = LinearLayoutManager(context)
        }

        viewModel.sections.observe(viewLifecycleOwner) { resource ->
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
                    if (resource.data.size == 0) {
                        //
                    } else {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                            binding.courseProgress.setProgress(
                                (course!!.subscription.viewed.size / resource.data.size) * 100,
                                true
                            )
                        } else {
                            binding.courseProgress.progress =
                                (course!!.subscription.viewed.size / resource.data.size) * 100
                        }
                        sectionListAdapter.setSections(resource.data)
                    }


                    binding.progressBar.visibility = View.GONE
                }
            }
        }
    }

}