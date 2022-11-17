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
            chart.setBackgroundColor(Color.WHITE)
            chart.setMaxVisibleValueCount(0)
            chart.setPinchZoom(true)
            chart.setDrawGridBackground(true)


            val xAxis: XAxis = chart.xAxis
            xAxis.position = XAxisPosition.BOTTOM
            xAxis.setDrawGridLines(false)

            val leftAxis: YAxis = chart.axisLeft
            leftAxis.setEnabled(false);
            leftAxis.setLabelCount(7, false)
            leftAxis.setDrawGridLines(false)
            leftAxis.setDrawAxisLine(false)

            val rightAxis: YAxis = chart.axisRight
            rightAxis.isEnabled = false
            chart.legend.isEnabled = false

            setChartData(30)
        }

    }

    fun setChartData(progress: Int){

        binding.chart.resetTracking()

       // val values = randomlyGenerateChartData(progress)
        val dummyData: List<Pair<Int, Int>> = listOf(Pair(60, 65), Pair(65, 70), Pair(70,80), Pair(80, 100), Pair(100, 85), Pair(90,100),
        Pair(100, 121), Pair(103, 123), Pair(98, 115), Pair(123, 130), Pair(130, 138), Pair(133, 141), Pair(128, 135), Pair(125, 138),
            )


        val values = generateChartData(dummyData)

        val set1 = CandleDataSet(values, "Data Set")

        set1.setDrawIcons(false)
        set1.axisDependency = AxisDependency.LEFT
//        set1.setColor(Color.rgb(80, 80, 80));
        //        set1.setColor(Color.rgb(80, 80, 80));
        set1.shadowColor = Color.TRANSPARENT
        set1.shadowWidth = 0.7f
        val orange = Color.rgb(255,165,0)
        set1.colors = listOf(Color.GRAY, Color.GRAY, Color.BLUE, Color.BLUE, Color.BLUE, Color.BLUE, Color.GREEN, Color.GREEN, Color.GREEN, Color.GREEN, orange, orange, orange, orange)
        set1.decreasingColor = ColorTemplate.COLOR_NONE
        set1.decreasingPaintStyle = Paint.Style.FILL
        set1.increasingColor = ColorTemplate.COLOR_NONE
        set1.increasingPaintStyle = Paint.Style.FILL
        set1.neutralColor = Color.BLUE
        //set1.setHighlightLineWidth(1f);

        //set1.setHighlightLineWidth(1f);
        val data = CandleData(set1)

        binding.chart.setData(data)
        binding.chart.invalidate()
    }

    private fun generateChartData(heartRates: List<Pair<Int, Int>>): ArrayList<CandleEntry>{
        val values = ArrayList<CandleEntry>()
        //(60, 90, 80, 100, 120, 45, 80, 100, 120, 30, 56)
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