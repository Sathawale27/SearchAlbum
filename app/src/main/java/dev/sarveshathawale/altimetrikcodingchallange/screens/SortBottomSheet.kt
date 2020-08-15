package dev.sarveshathawale.altimetrikcodingchallange.screens

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.fragment.app.DialogFragment
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dev.sarveshathawale.altimetrikcodingchallange.R
import kotlinx.android.synthetic.main.bottom_sheet_sorting.*

class SortBottomSheet : BottomSheetDialogFragment() {

    private var onActionSelectionListener: OnActionSelectionListener? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        onActionSelectionListener = context as OnActionSelectionListener
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(DialogFragment.STYLE_NORMAL, R.style.SortBottomSheetDialog)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.bottom_sheet_sorting, container, false)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val bottomSheetDialog = super.onCreateDialog(savedInstanceState)
        bottomSheetDialog.setOnShowListener {
            val bottomSheet: FrameLayout =
                bottomSheetDialog.findViewById(com.google.android.material.R.id.design_bottom_sheet)
            bottomSheet.let {
                BottomSheetBehavior.from(bottomSheet).isHideable = false
            }
        }
        return bottomSheetDialog
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        isCancelable = true

        tvByCollectionName.setOnClickListener {
            onActionSelectionListener?.onSortSelection(SortOptions.COLLECTION_NAME)
            dismiss()
        }

        tvByTrackName.setOnClickListener {
            onActionSelectionListener?.onSortSelection(SortOptions.TRACK_NAME)
            dismiss()
        }

        tvByArtistName.setOnClickListener {
            onActionSelectionListener?.onSortSelection(SortOptions.ARTIST_NAME)
            dismiss()
        }
    }

    interface OnActionSelectionListener {
        fun onSortSelection(sortOptions: SortOptions)
    }

    enum class SortOptions {
        COLLECTION_NAME,
        TRACK_NAME,
        ARTIST_NAME
    }
}