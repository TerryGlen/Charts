package net.glinsey.charts.ui.main

import android.graphics.Color
import android.graphics.Paint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.components.XAxis.XAxisPosition
import com.github.mikephil.charting.components.YAxis
import com.github.mikephil.charting.components.YAxis.AxisDependency
import com.github.mikephil.charting.data.CandleData
import com.github.mikephil.charting.data.CandleDataSet
import com.github.mikephil.charting.data.CandleEntry
import com.github.mikephil.charting.utils.ColorTemplate
import net.glinsey.charts.R
import net.glinsey.charts.databinding.FragmentMainBinding

class MainFragment : Fragment() {

    val gray = Color.parseColor("#6F767B")
    val blue = Color.parseColor("#0673A6")
    val green = Color.parseColor("#4BA553")
    val orange = Color.parseColor("#F26922")
    val red = Color.parseColor("#E3001E")
    val white = Color.parseColor("#1E1E1E")


    companion object {
        fun newInstance() = MainFragment()
    }

    private lateinit var viewModel: MainViewModel
    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupChart()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setupChart(){
        with(binding){
            chart.renderer = CustomCandleStickChartRender(chart, chart.animator, chart.viewPortHandler)
            chart.setBackgroundColor(Color.WHITE)
            chart.setMaxVisibleValueCount(0)
            chart.setPinchZoom(true)
            chart.setDrawGridBackground(true)
            chart.setDrawBorders(true)
            chart.setBorderColor(Color.BLACK)



            val xAxis: XAxis = chart.xAxis
            xAxis.position = XAxisPosition.BOTTOM
            xAxis.isEnabled = true
            xAxis.setLabelCount(9, false)
            xAxis.setDrawLabels(false)
            xAxis.setDrawGridLines(false)

            val leftAxis: YAxis = chart.axisLeft
            leftAxis.isEnabled = false

            val rightAxis: YAxis = chart.axisRight
            rightAxis.isEnabled = true
            rightAxis.setDrawGridLines(false)
            rightAxis.setLabelCount(3, false)

            chart.legend.isEnabled = false
            chart.description.isEnabled = false


            setChartData(30)
        }

    }

    fun setChartData(progress: Int){

        binding.chart.resetTracking()

       // val values = randomlyGenerateChartData(progress)
        val dummyData: List<Pair<Int, Int>> = listOf(Pair(60, 65), Pair(65, 70), Pair(70,80), Pair(80, 100), Pair(100, 85), Pair(90,100),
        Pair(100, 121), Pair(103, 123), Pair(98, 115), Pair(123, 130), Pair(130, 138), Pair(133, 141), Pair(128, 135), Pair(125, 138),
          Pair(130, 123),Pair(131, 140), Pair(130, 123), Pair(131, 140), Pair(140, 180), Pair(141, 185), Pair(186, 195), Pair(180, 190), Pair(184, 198), Pair(140, 194),
            Pair(140, 145), Pair(141, 145), Pair(131, 123), Pair(130, 140), Pair(123, 131), Pair(130, 138), Pair(125, 135), Pair(128, 141), Pair(133, 138), Pair(123, 115), Pair(98, 123), Pair(103, 121), Pair(100, 85), Pair(90,100), Pair(80, 70), Pair(70,80), Pair(65, 58), Pair(60,65)
        )


        val values = generateChartData(dummyData)

        val set1 = CandleDataSet(values, "Data Set")

        set1.setDrawIcons(false)
        set1.axisDependency = AxisDependency.RIGHT
        set1.shadowColor = Color.TRANSPARENT
        set1.shadowWidth = 0.7f
        set1.colors = listOf(gray, gray, blue, blue, blue, blue, green, green, green, green, orange, orange, orange, orange,
        green, orange, green, orange, red, red, red, red, red, red)
        set1.decreasingColor = ColorTemplate.COLOR_NONE
        set1.decreasingPaintStyle = Paint.Style.FILL
        set1.increasingColor = ColorTemplate.COLOR_NONE
        set1.increasingPaintStyle = Paint.Style.FILL
        //set1.setHighlightLineWidth(1f);

        //set1.setHighlightLineWidth(1f);
        val data = CandleData(set1)

        binding.chart.setData(data)
        binding.chart.invalidate()
        binding.chart.fitScreen()
    }

    private fun generateChartData(heartRates: List<Pair<Int, Int>>): ArrayList<CandleEntry>{
        val values = ArrayList<CandleEntry>()
        for (i in heartRates.indices){
            val open = heartRates[i].first.toFloat()
            val close = heartRates[i].second.toFloat()
            values.add(CandleEntry(i.toFloat(), open, close, open, close))
        }

        return values
    }

    private fun randomlyGenerateChartData(
        progress: Int,
    ): ArrayList<CandleEntry> {
        val values = ArrayList<CandleEntry>()
        for (i in 0 until progress) {
            val multi: Float = (progress + 1).toFloat()
            val `val` = (Math.random() * 40).toFloat() + multi
            val high = (Math.random() * 9).toFloat() + 8f
            val low = (Math.random() * 9).toFloat() + 8f
            val open = (Math.random() * 6).toFloat() + 1f
            val close = (Math.random() * 6).toFloat() + 1f
            val even = i % 2 == 0
            values.add(
                CandleEntry(
                    i.toFloat(), `val` + high,
                    `val` - low,
                    if (even) `val` + open else `val` - open,
                    if (even) `val` - close else `val` + close,
                    //resources.getDrawable(R.drawable.star)
                )
            )
        }
        return values
    }

}