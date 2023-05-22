package com.puresoftware.bottomnavigationappbar.Weggler.SwipeCardView

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.view.MotionEvent
import android.view.View
import android.view.View.OnTouchListener
import android.widget.Button
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.ItemTouchHelper.ACTION_STATE_SWIPE
import androidx.recyclerview.widget.RecyclerView
import com.puresoftware.bottomnavigationappbar.Weggler.Adapter.ItemProductDetailAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder

class SwipeController : ItemTouchHelper.Callback() {

    private var swipeBack : Boolean = false
    // 현재 버튼 상태
    private var buttonShowedState : ButtonsState = ButtonsState.GONE
    private val buttonWidth : Float = 300F
    private var buttonInstance : RectF? = null //동적 버튼
    // 액션 이벤트
    private var buttonActions : SwipeControllerActions? = null
    fun setButtonActionListener(listener:SwipeControllerActions){
        this.buttonActions = listener
    }
    // 현재 선택 뷰 홀더 정보
    private var currentItemViewHolder : ViewHolder? = null


    override fun getMovementFlags(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder
    ): Int {
        val swipeFlags = ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
        return makeMovementFlags(0,swipeFlags) //좌우 스와이프만 허용
    }

    override fun onMove(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        target: RecyclerView.ViewHolder
    ): Boolean {
        return false
    }

    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {}

    override fun convertToAbsoluteDirection(flags: Int, layoutDirection: Int): Int {
        // 스와이프 시 버튼을 나타내는 동작 설정
        if (swipeBack){
            swipeBack = buttonShowedState != ButtonsState.GONE
            return  0
        }
        return super.convertToAbsoluteDirection(flags, layoutDirection)
    }

    override fun onChildDraw(
        c: Canvas,
        recyclerView: RecyclerView,
        viewHolder: ViewHolder,
        dX: Float,
        dY: Float,
        actionState: Int,
        isCurrentlyActive: Boolean
    ) {
        // 스와이프 상태일 때 적용
        if (actionState==ACTION_STATE_SWIPE){
            if (buttonShowedState != ButtonsState.GONE){
                // 버튼이 보여지는 상태
                var dx = dX
                // 스와이프 중인 뷰를 고정 시킴
                if (buttonShowedState==ButtonsState.RIGHT_VISIBLE)
                    dx = Math.min(dX,-buttonWidth)
                super.onChildDraw(c, recyclerView, viewHolder,
                    dx, dY, actionState, isCurrentlyActive)
            }else{
                setTouchListener(c,recyclerView, viewHolder,
                    dX, dY, actionState, isCurrentlyActive)
            }
        }
        if (buttonShowedState == ButtonsState.GONE) {// 버튼 off
            super.onChildDraw(c, recyclerView, viewHolder,
                dX, dY, actionState, isCurrentlyActive)
        }
        currentItemViewHolder = viewHolder // 현재 관리 중인 뷰 홀더 설정
    }

    private fun setTouchListener(
        c: Canvas,
        recyclerView: RecyclerView,
        viewHolder: ViewHolder,
        dX: Float,
        dY: Float,
        actionState: Int,
        isCurrentlyActive: Boolean
    ){
        recyclerView.setOnTouchListener(object :OnTouchListener{
            override fun onTouch(p0: View?, p1: MotionEvent?): Boolean {
                p1?.let{event ->
                    swipeBack = event.action == MotionEvent.ACTION_CANCEL
                            || event.action == MotionEvent.ACTION_UP

                    // 얼마나 많이 드래그 했는지 확인
                    // 좌,우 스와이프 상태 확인
                    if (swipeBack){
                        if (dX < -buttonWidth)
                            buttonShowedState = ButtonsState.RIGHT_VISIBLE

                        if (buttonShowedState!=ButtonsState.GONE){
                            setTouchDownListener(c,recyclerView, viewHolder,
                                dX, dY, actionState, isCurrentlyActive)
                            setItemClickable(recyclerView,false)
                        }
                    }
                }
                return false
            }

        })
    }

    private fun setTouchDownListener(
        c: Canvas, recyclerView: RecyclerView,
        viewHolder: ViewHolder,dX: Float,dY: Float,
        actionState: Int,isCurrentlyActive: Boolean
    ){
        recyclerView.setOnTouchListener(object : OnTouchListener{
            override fun onTouch(p0: View?, p1: MotionEvent?): Boolean {
                p1?.let{event ->
                    if (event.action == MotionEvent.ACTION_DOWN){
                        if (event.action==MotionEvent.ACTION_DOWN){
                            setTouchUpListener(c,recyclerView, viewHolder,
                                dX, dY, actionState, isCurrentlyActive)
                        }
                    }
                }
                return false
            }

        })
    }

    private fun setTouchUpListener(
        c: Canvas, recyclerView: RecyclerView,
        viewHolder: ViewHolder,dX: Float,dY: Float,
        actionState: Int,isCurrentlyActive: Boolean
    ){
        recyclerView.setOnTouchListener(object : OnTouchListener{
            override fun onTouch(p0: View?, p1: MotionEvent?): Boolean {
                p1?.let{event ->
                    if (event.action == MotionEvent.ACTION_UP){
                        this@SwipeController.onChildDraw(c,recyclerView, viewHolder, 0F, dY, actionState, isCurrentlyActive)
                        // 터치 리스너 재정의
                        recyclerView.setOnTouchListener(object : OnTouchListener{
                            override fun onTouch(p0: View?, p1: MotionEvent?): Boolean {
                                return false
                            }
                        })
                        setItemClickable(recyclerView,true)
                        swipeBack = false

                        if (buttonActions != null && buttonInstance != null
                            && buttonInstance!!.contains(event.x,event.y)){
                            // 선택 된 값이 null이 아닐 때 실행
                            // right -> right 클릭 이벤트
                            // lift -> left 클릭 이벤트
                            if (buttonShowedState == ButtonsState.RIGHT_VISIBLE) {
                                buttonActions?.onRightClicked(viewHolder.absoluteAdapterPosition)
                            }
                        }
                        buttonShowedState = ButtonsState.GONE
                        currentItemViewHolder = null
                    }
                }
                return false
            }

        })
    }
    private fun setItemClickable(recyclerView: RecyclerView,boolean: Boolean){
        for (i in 0 until recyclerView.childCount){
            recyclerView.getChildAt(i).isClickable = boolean
        }
    }

    private fun drawButtons(c : Canvas, viewHolder: ViewHolder){
        // 버튼 정보 등록
        val buttonWidthWithoutPadding = buttonWidth - 20
        val corners = 16F
        val itemView = viewHolder.itemView
        val p = Paint()

        // 삭제 버튼 생성
        val rightButton = RectF(itemView.right-buttonWidthWithoutPadding,
        itemView.top.toFloat(),itemView.right.toFloat(),itemView.bottom.toFloat())
        p.color = Color.RED
        c.drawRoundRect(rightButton,corners,corners,p)
        drawText(c,rightButton,p)
    }
    private fun drawText(c:Canvas,button:RectF,paint: Paint){
        val textSize = 60F
        paint.color = Color.WHITE
        paint.isAntiAlias = true
        paint.textSize = textSize
        val textWidth: Float = paint.measureText("DELETE")
        // 중앙 위치에 텍스트 그리기
        c.drawText("DELETE",button.centerX()-(textWidth/2),button.centerY()+(textSize/2),paint)
    }

    fun onDraw(c:Canvas){
        currentItemViewHolder?.let { drawButtons(c,it) }
    }

}