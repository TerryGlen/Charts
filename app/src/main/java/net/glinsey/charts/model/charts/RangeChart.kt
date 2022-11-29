package net.glinsey.charts.model.charts

import android.content.Context
import android.graphics.Paint
import android.graphics.Paint.Style
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import com.github.mikephil.charting.charts.CandleStickChart
import com.github.mikephil.charting.data.*
import com.github.mikephil.charting.interfaces.dataprovider.CandleDataProvider
import com.github.mikephil.charting.interfaces.datasets.ICandleDataSet
import com.github.mikephil.charting.utils.ColorTemplate

class RangeChart(context: Context, attributeSet: AttributeSet) : CandleStickChart(context, attributeSet), CandleDataProvider {

    override fun init() {
        super.init()
        mRenderer = RangeChartRender(this, mAnimator, mViewPortHandler)
        xAxis.spaceMin = 0.5f
        xAxis.spaceMax = 0.5f
    }

    fun setData(data: RangeData?) {
        super.setData(data)
    }
}

class RangeData : CandleData {
    constructor(dataSets: List<ICandleDataSet?>?) : super(dataSets) {}
    constructor(vararg dataSets: ICandleDataSet?) : super(*dataSets) {}
}

class RangeDataSet(yVals: List<CandleEntry>, label: String) : CandleDataSet(yVals, label) {

    init {
        this.mIncreasingColor = ColorTemplate.COLOR_NONE
        this.mDecreasingColor = ColorTemplate.COLOR_NONE
        this.decreasingPaintStyle = Style.FILL
        this.increasingPaintStyle = Style.FILL
    }

    override fun getEntryIndex(e: CandleEntry): Int {
         return super.getEntryIndex(e)
     }

 }


class RangeEntry : CandleEntry {
    /**
     * Constructor.
     *
     * @param x The value on the x-axis
     * @param open The open value
     * @param close The close value
     */
    constructor(x: Float, open: Float, close: Float) : super(x, open, close, open, close){
        high = open
        low = close
        this.open = open
        this.close = close
    }

    /**
     * Constructor.
     *
     * @param x The value on the x-axis
     * @param open
     * @param close
     * @param data Spot for additional data this Entry represents
     */
    constructor(
        x: Float,  open: Float, close: Float,
        data: Any?
    ) : super(x, open, close, open, close, data) {
        high = open
        low = close
        this.open = open
        this.close = close
    }

    /**
     * Constructor.
     *
     * @param x The value on the x-axis
     * @param shadowH The (shadow) high value
     * @param shadowL The (shadow) low value
     * @param open
     * @param close
     * @param icon Icon image
     */
    constructor(
        x: Float,  open: Float, close: Float,
        icon: Drawable?
    ) : super(x, open, close, open, close, icon) {
        high = open
        low = close
        this.open = open
        this.close = close
    }

    /**
     * Constructor.
     *
     * @param x The value on the x-axis
     * @param shadowH The (shadow) high value
     * @param shadowL The (shadow) low value
     * @param open
     * @param close
     * @param icon Icon image
     * @param data Spot for additional data this Entry represents
     */
    constructor(
        x: Float, shadowH: Float, shadowL: Float, open: Float, close: Float,
        icon: Drawable?, data: Any?
    ) : super(x, open, close, open, close, icon, data) {
        high = shadowH
        low = shadowL
        this.open = open
        this.close = close
    }


}

