package com.example.testcompanion

import android.content.ContentValues
import android.content.Context
import android.graphics.Color
import android.graphics.Typeface
import android.graphics.pdf.PdfDocument
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.view.View
import android.widget.Toast
import com.example.testcompanion.ConstantVariables.Constant
import com.example.testcompanion.ExtraClasses.IntegerPercentFormatter
import com.example.testcompanion.databinding.ActivityResultBinding
import com.github.mikephil.charting.animation.Easing
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.components.YAxis
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.formatter.PercentFormatter
import com.github.mikephil.charting.utils.MPPointF

class ResultActivity : AppCompatActivity() {
    private lateinit var binding: ActivityResultBinding
    private lateinit var barChart: BarChart
    private lateinit var barDataSet: BarDataSet
    private lateinit var barEntries: ArrayList<BarEntry>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)
        binding = ActivityResultBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.pieChart.setUsePercentValues(true)
        binding.pieChart.description.isEnabled = false
        binding.pieChart.setExtraOffsets(5f, 10f, 5f, 5f)
        binding.pieChart.dragDecelerationFrictionCoef = 0.95f
        binding.pieChart.isDrawHoleEnabled = true
        // on below line we are setting circle color and alpha
        binding.pieChart.setTransparentCircleColor(Color.WHITE)
        binding.pieChart.setTransparentCircleAlpha(110)

        // on  below line we are setting hole radius
        binding.pieChart.holeRadius = 58f
        binding.pieChart.transparentCircleRadius = 61f

        // on below line we are setting center text
        binding.pieChart.setDrawCenterText(true)

        // on below line we are setting
        // rotation for our pie chart
        binding.pieChart.rotationAngle = 0f

        // enable rotation of the pieChart by touch
        binding.pieChart.isRotationEnabled = true
        binding.pieChart.isHighlightPerTapEnabled = true

        // on below line we are setting animation for our pie chart
        binding.pieChart.animateY(1400, Easing.EaseInOutQuad)

        // on below line we are disabling our legend for pie chart
        binding.pieChart.legend.isEnabled = false
        binding.pieChart.setEntryLabelColor(Color.WHITE)
        binding.pieChart.setEntryLabelTextSize(12f)

        // on below line we are creating array list and
        // adding data to it to display in pie chart
        val entries: ArrayList<PieEntry> = ArrayList()
        entries.add(PieEntry(Constant.totalCorrectAnswers.toFloat()))
        entries.add(PieEntry(Constant.totalWrongAnswers.toFloat()))

        // on below line we are setting pie data set
        val dataSet = PieDataSet(entries, "Mobile OS")

        // on below line we are setting icons.
        dataSet.setDrawIcons(false)

        // on below line we are setting slice for pie
        dataSet.sliceSpace = 3f
        dataSet.iconsOffset = MPPointF(0f, 40f)
        dataSet.selectionShift = 5f

        // add a lot of colors to list
        val colors: ArrayList<Int> = ArrayList()
        colors.add(resources.getColor(R.color.green))
        colors.add(resources.getColor(R.color.red))

        // on below line we are setting colors.
        dataSet.colors = colors

        // on below line we are setting pie data set
        val data = PieData(dataSet)
        data.setValueFormatter(IntegerPercentFormatter())
        data.setValueTextSize(15f)
        data.setValueTypeface(Typeface.DEFAULT_BOLD)
        data.setValueTextColor(Color.WHITE)
        binding.pieChart.data = data

        // undo all highlights
        binding.pieChart.highlightValues(null)

        // loading chart
        binding.pieChart.invalidate()
        Toast.makeText(this,"Correct Answers${Constant.totalCorrectAnswers.toFloat()}",Toast.LENGTH_SHORT).show()
        Toast.makeText(this,"Wrong Answers${Constant.totalWrongAnswers.toFloat()}",Toast.LENGTH_SHORT).show()
        binding.totalQuestions.text = Constant.totalQuestions.toString()
        binding.correctAnswers.text = Constant.totalCorrectAnswers.toString()
        binding.wrongAnswers.text = Constant.totalWrongAnswers.toString()

//        binding.btnGeneratePDF.setOnClickListener {
//            createPdfFromViewModern(this,binding.testView, "MyScreenContent")
//        }

        // Working on DataSet
        barDataSet = BarDataSet(barEntriesList, "Data")

        // Option 2: Set different colors for each bar
        val barColors = intArrayOf(
            Color.RED, Color.GREEN, Color.BLUE,
            Color.YELLOW, Color.CYAN, Color.MAGENTA
        )

        // Set the colors for the bars
        barDataSet.colors = barColors.toList()

        barDataSet.valueTextSize = 11f

        // Working on BarChart
        val barData = BarData(barDataSet)
        binding.barChart.data = barData
        binding.barChart.animateY(2000)
        binding.barChart.description.isEnabled = false
        binding.barChart.isDragEnabled = true
        binding.barChart.setVisibleXRangeMaximum(6f)

        // Set bar width
        barData.barWidth = 0.15f

        // X-Axis Data
        val xAxis: XAxis = binding.barChart.xAxis
        xAxis.position = XAxis.XAxisPosition.BOTTOM
        xAxis.granularity = 1f
        xAxis.isGranularityEnabled = true

        // Enable grid lines for X-axis
        xAxis.setDrawGridLines(true)

        // Set grid line color
        xAxis.gridColor = Color.LTGRAY

        // Set grid line width
        xAxis.gridLineWidth = 1f

        // Y-Axis Data
        val leftAxis: YAxis = binding.barChart.axisLeft
        leftAxis.setDrawGridLines(true)
        leftAxis.gridColor = Color.LTGRAY
        leftAxis.gridLineWidth = 1f
        leftAxis.textColor = Color.WHITE

        val rightAxis: YAxis = binding.barChart.axisRight

        // Disable right Y-axis
        rightAxis.isEnabled = false

        binding.barChart.xAxis.axisMinimum = 0f
        binding.barChart.animate()

        // Invalidate the chart to refresh
        binding.barChart.invalidate()
    }

    // ArrayList for the first set of bar entries
    private val barEntriesList: ArrayList<BarEntry>
        get() {
            // Creating a new ArrayList
            barEntries = ArrayList()

            // Adding entries to the ArrayList for the first set
            barEntries.add(BarEntry(1f, 3f))
            barEntries.add(BarEntry(2f, 5f))
            barEntries.add(BarEntry(3f, 7f))
            barEntries.add(BarEntry(4f, 1f))
            barEntries.add(BarEntry(5f, 2f))
            barEntries.add(BarEntry(6f, 6f))

            return barEntries




    }

    fun createPdfFromViewModern(context: Context, view: View, fileName: String) {
        val document = PdfDocument()
        val pageInfo = PdfDocument.PageInfo.Builder(view.width, view.height, 1).create()
        val page = document.startPage(pageInfo)

        view.draw(page.canvas)
        document.finishPage(page)

        val contentValues = ContentValues().apply {
            put(MediaStore.MediaColumns.DISPLAY_NAME, "$fileName.pdf")
            put(MediaStore.MediaColumns.MIME_TYPE, "application/pdf")
            put(MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_DOCUMENTS + "/MyAppPDFs")
            put(MediaStore.MediaColumns.IS_PENDING, 1)
        }

        val resolver = context.contentResolver
        val uri = resolver.insert(MediaStore.Files.getContentUri("external"), contentValues)

        if (uri != null) {
            resolver.openOutputStream(uri)?.use { outputStream ->
                document.writeTo(outputStream)
            }
            contentValues.clear()
            contentValues.put(MediaStore.MediaColumns.IS_PENDING, 0)
            resolver.update(uri, contentValues, null, null)

            Toast.makeText(context, "PDF saved to Documents/MyAppPDFs", Toast.LENGTH_LONG).show()
        } else {
            Toast.makeText(context, "Failed to create file", Toast.LENGTH_LONG).show()
        }

        document.close()
    }

}