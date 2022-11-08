package ng.farma.buidlplayer.adapters

import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import ng.farma.buidlplayer.R
import ng.farma.buidlplayer.databinding.SectionListBinding
import ng.farma.buidlplayer.domain.models.CourseSection

class SectionListAdapter(
    private val viewed: List<String>,
    private val onClick: (CourseSection) -> Unit
) : RecyclerView.Adapter<SectionListAdapter.SectionListViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SectionListViewHolder {
        return SectionListViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.section_list, parent, false)
        )
    }

    override fun onBindViewHolder(holder: SectionListViewHolder, position: Int) {
        holder.bind(diffResult.currentList[position], position)
    }

    override fun getItemCount(): Int = diffResult.currentList.size

    private val differ = object : DiffUtil.ItemCallback<CourseSection>() {
        override fun areItemsTheSame(
            oldItem: CourseSection,
            newItem: CourseSection
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: CourseSection,
            newItem: CourseSection
        ): Boolean {
            return oldItem.src == newItem.src
        }
    }

    private val diffResult = AsyncListDiffer(this, differ)

    fun setSections(sections: List<CourseSection>) {
        diffResult.submitList(sections)
    }

    inner class SectionListViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        private val binding = SectionListBinding.bind(view)

        fun bind(section: CourseSection, position: Int) {
            binding.apply {
                this.position.text = "${(position + 1)}."
                title.text = section.title
                content.text = Html.fromHtml(section.content)

                if (viewed.contains(section.sectionId)) {
                    icon.setImageResource(R.drawable.ic_baseline_arrow_forward_24)
                } else {
                    icon.setImageResource(R.drawable.ic_baseline_lock_24)
                }

            }.root.setOnClickListener {
                if (viewed.contains(section.sectionId)) {
                    onClick(section)
                }
            }
        }
    }
}