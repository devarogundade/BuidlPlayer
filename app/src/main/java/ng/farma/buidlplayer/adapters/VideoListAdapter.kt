package ng.farma.buidlplayer.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import ng.farma.buidlplayer.R
import ng.farma.buidlplayer.databinding.CourseListBinding
import ng.farma.buidlplayer.domain.models.SubscribedCourse

class VideoListAdapter : RecyclerView.Adapter<VideoListAdapter.VideoListViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VideoListViewHolder {
        return VideoListViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.course_list, parent, false)
        )
    }

    override fun onBindViewHolder(holder: VideoListViewHolder, position: Int) {
        holder.bind(diffResult.currentList[position])
    }

    override fun getItemCount(): Int = diffResult.currentList.size

    private val differ = object : DiffUtil.ItemCallback<SubscribedCourse>() {
        override fun areItemsTheSame(
            oldItem: SubscribedCourse,
            newItem: SubscribedCourse
        ): Boolean {
            return oldItem.courseId == newItem.courseId
        }

        override fun areContentsTheSame(
            oldItem: SubscribedCourse,
            newItem: SubscribedCourse
        ): Boolean {
            return oldItem == newItem
        }
    }

    private val diffResult = AsyncListDiffer(this, differ)

    fun setCourses(courses: List<SubscribedCourse>) {
        diffResult.submitList(courses)
    }

    inner class VideoListViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        private val binding = CourseListBinding.bind(view)
        private val requestManager = Glide.with(view)

        fun bind(course: SubscribedCourse) {
            binding.apply {
                name.text = course.course.name
                description.text = course.course.description
                requestManager.load(course.course.photo)
                    .into(photo)
            }.root.setOnClickListener {
            }
        }
    }
}