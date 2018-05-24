package com.iamsdt.shokherschool.ui.adapter

import android.content.Context
import android.graphics.PorterDuff
import android.graphics.drawable.Drawable
import android.graphics.drawable.ShapeDrawable
import android.graphics.drawable.shapes.OvalShape
import android.support.annotation.AttrRes
import android.support.annotation.ColorInt
import android.support.v7.widget.RecyclerView
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import com.iamsdt.shokherschool.R
import com.iamsdt.shokherschool.data.model.ThemesContract
import kotlinx.android.synthetic.main.color_list_item.view.*








/**
 * Created by Shudipto Trafder on 1/18/2018.
 * at 9:12 PM
 */
class ColorAdapter(private val themeIds:ArrayList<ThemesContract>,
                   val context: Context,
                   val clickListener: ClickListener):
        RecyclerView.Adapter<ColorAdapter.ColorViewHolder>(){



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ColorViewHolder {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.color_list_item,parent,false)
        return ColorViewHolder(view)
    }

    override fun getItemCount(): Int {
        return themeIds.size
    }

    override fun onBindViewHolder(holder: ColorViewHolder, position: Int) {
        val themeId = themeIds[position]

        //exert from theme
        val primaryColor = getThemeAttr(themeId.id, R.attr.colorPrimary)
        val primaryColorDark = getThemeAttr(themeId.id, R.attr.colorPrimaryDark)
        val accentColor = getThemeAttr(themeId.id, R.attr.colorAccent)

        //set name
        holder.name.text = themeId.name
        holder.name.setTextColor(primaryColor)

        //circle of color
        holder.primaryColor.background = getDrawables(primaryColor)
        holder.primaryColorDark.background = getDrawables(primaryColorDark)
        holder.accentColor.background = getDrawables(accentColor)
    }

    /**
     * This methods for create a new drawable
     * we take a color id and show color through drawable
     *
     * @param id color id for selected color
     */

    private fun getDrawables(@ColorInt id: Int): Drawable {
        val shapeDrawable = ShapeDrawable(OvalShape())

        shapeDrawable.intrinsicWidth = floatToInt()

        shapeDrawable.intrinsicHeight = floatToInt()
        shapeDrawable.setColorFilter(id, PorterDuff.Mode.SRC_ATOP)

        return shapeDrawable
    }

    /**
     * This methods convert dp to px
     *
     * @param dp float to convert
     * @return converted value in px
     */
    private fun dpToPxConverter(dp: Float): Float {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp,
                context.resources.displayMetrics)
    }

    /**
     * this methods will convert float to int
     *
     * @return float value to int value
     */
    private fun floatToInt(): Int {
        return dpToPxConverter(32.toFloat()).toInt()
    }

    /**
     * This methods for find color id through theme
     * @param stylesID theme id of any theme
     * @param attrId color type id like color primary, accent color
     * @return given color id from theme in the basis of colorAttr
     */

    private fun getThemeAttr(stylesID: Int, @AttrRes attrId: Int): Int {

        val typedValue = context.obtainStyledAttributes(stylesID, intArrayOf(attrId))
        val colorFromTheme = typedValue.getColor(0, 0)
        typedValue.recycle()
        return colorFromTheme
    }

    inner class ColorViewHolder(viewItem:View)
        :RecyclerView.ViewHolder(viewItem), View.OnClickListener{


        val name: TextView = viewItem.color_list_name
        val primaryColor: TextView = viewItem.color_list_primary
        val primaryColorDark: TextView = viewItem.color_list_primaryDark
        val accentColor: TextView = viewItem.color_list_accent
        //layout
        private val linearLayout:LinearLayout = viewItem.color_list_linearLayout

        init {
            linearLayout.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            clickListener.onItemClick(adapterPosition)
        }
    }
}