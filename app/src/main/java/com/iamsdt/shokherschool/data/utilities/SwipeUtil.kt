/*
 * Copyright {2017} {Shudipto Trafder}
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.iamsdt.shokherschool.data.utilities

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.PorterDuff
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.support.v4.content.ContextCompat
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.helper.ItemTouchHelper
import com.iamsdt.shokherschool.R


/**
 * Created by Shudipto Trafder on 11/7/2017.
 * at 9:36 PM
 */
abstract class SwipeUtil(dragDirs: Int,
                         swipeDirs: Int,
                         private val context: Context) :
        ItemTouchHelper.SimpleCallback(dragDirs, swipeDirs) {

    private var background: Drawable? = null
    private var deleteIcon: Drawable? = null

    private var xMarkMargin: Int = 0

    private var initiated: Boolean = false

    var leftColorCode: Int = 0
    var leftSwipeLabel: String? = null



    private fun init() {
        background = ColorDrawable()
        xMarkMargin = context.resources.getDimension(R.dimen.ic_clear_margin).toInt()
        deleteIcon = ContextCompat.getDrawable(context, android.R.drawable.ic_menu_delete)
        if (deleteIcon != null) {
            deleteIcon!!.setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_ATOP)
        }
        initiated = true
    }

    override fun onMove(recyclerView: RecyclerView,
                        viewHolder: RecyclerView.ViewHolder,
                        target: RecyclerView.ViewHolder): Boolean {
        return false
    }

    abstract override fun onSwiped(viewHolder: RecyclerView.ViewHolder,
                                   direction: Int)


    override fun onChildDraw(c: Canvas,
                             recyclerView: RecyclerView,
                             viewHolder: RecyclerView.ViewHolder,
                             dX: Float,
                             dY: Float,
                             actionState: Int,
                             isCurrentlyActive: Boolean) {

        val itemView = viewHolder.itemView
        if (!initiated) {
            init()
        }

        val itemHeight = itemView.bottom - itemView.top

        //Setting Swipe Background
        (background as ColorDrawable).color = leftColorCode
        background!!.setBounds(itemView.right + dX.toInt(), itemView.top, itemView.right, itemView.bottom)
        background!!.draw(c)

        val intrinsicWidth = deleteIcon!!.intrinsicWidth
        val intrinsicHeight = deleteIcon!!.intrinsicWidth

        val xMarkLeft = itemView.right - xMarkMargin - intrinsicWidth
        val xMarkRight = itemView.right - xMarkMargin
        val xMarkTop = itemView.top + (itemHeight - intrinsicHeight) / 2
        val xMarkBottom = xMarkTop + intrinsicHeight


        //Setting Swipe Icon
        deleteIcon!!.setBounds(xMarkLeft, xMarkTop + 16, xMarkRight, xMarkBottom)
        deleteIcon!!.draw(c)

        //Setting Swipe Text
        val paint = Paint()
        paint.color = Color.WHITE
        paint.textSize = 48f
        paint.textAlign = Paint.Align.CENTER
        c.drawText(leftSwipeLabel!!, (xMarkLeft + 40).toFloat(), (xMarkTop + 10).toFloat(), paint)


        super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
    }
}
