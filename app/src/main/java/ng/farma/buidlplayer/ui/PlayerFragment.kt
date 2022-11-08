package ng.farma.buidlplayer.ui

import android.os.Bundle
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import dagger.hilt.android.AndroidEntryPoint
import ng.farma.buidlplayer.databinding.FragmentPlayerBinding
import ng.farma.buidlplayer.domain.models.CourseSection

@AndroidEntryPoint
class PlayerFragment : Fragment() {

    private lateinit var binding: FragmentPlayerBinding
    private var section: CourseSection? = null

    private lateinit var coursePlayer: ExoPlayer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        section = requireArguments().getSerializable("section") as CourseSection?
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPlayerBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (section == null) return

        binding.toolbar.title = section!!.title
        binding.toolbar.setNavigationOnClickListener {
            if (coursePlayer.isPlaying) {
                coursePlayer.stop()
            }
            findNavController().popBackStack()
        }

        coursePlayer = ExoPlayer.Builder(requireContext()).build()
        binding.preview.player = coursePlayer
        val mediaItem = MediaItem.fromUri(section!!.src)
        coursePlayer.addMediaItem(mediaItem)
        coursePlayer.prepare()
        coursePlayer.playWhenReady = true

        binding.content.text = Html.fromHtml(section!!.content)
    }

}