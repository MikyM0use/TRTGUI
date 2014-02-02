package TRTChart;

import TRTjson.TRTJson;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.CategoryLabelPositions;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.chart.renderer.category.LineAndShapeRenderer;
import org.jfree.data.category.DefaultCategoryDataset;
import org.json.JSONArray;
import org.json.JSONException;

/**
 *
 * @author michele
 */
public class TRTChart {

    private String secondsToDate(int seconds) {
        Date date = new Date((long) seconds * 1000);
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM hh:mm");

        return sdf.format(date);
    }

    public JFreeChart updateLastTransChart(int days) {
        DefaultCategoryDataset datasetVolume = new DefaultCategoryDataset();
        DefaultCategoryDataset datasetPrice = new DefaultCategoryDataset();
        JSONArray jsonArray = TRTJson.getJSONArrayDataset(days);

        double minPrice = Double.MAX_VALUE, maxPrice = Double.MIN_VALUE, minVolume = Double.MAX_VALUE, maxVolume = Double.MIN_VALUE;

        try {
            for (int i =0; i < jsonArray.length(); ++i) {
                //get amount/price with date
                datasetVolume.addValue(jsonArray.getJSONObject(i).getDouble("amount"), "amount", secondsToDate(jsonArray.getJSONObject(i).getInt("date")) + "");
                datasetPrice.addValue(jsonArray.getJSONObject(i).getDouble("price"), "price", secondsToDate(jsonArray.getJSONObject(i).getInt("date")) + "");

                //use after for scaling chart
                minPrice = Math.min(minPrice, jsonArray.getJSONObject(i).getDouble("price"));
                minVolume = Math.min(minVolume, jsonArray.getJSONObject(i).getDouble("amount"));
                maxPrice = Math.max(maxPrice, jsonArray.getJSONObject(i).getDouble("price"));
                maxVolume = Math.max(maxVolume, jsonArray.getJSONObject(i).getDouble("amount"));
            }
        } catch (JSONException e) {
        }

        //init chart
        CategoryPlot plot = new CategoryPlot();


        //price
        LineAndShapeRenderer rendererPrice = new LineAndShapeRenderer();
        plot.setRangeAxis(0, new NumberAxis("Price (€)"));
        plot.getRangeAxis(0).setRange(minPrice - 3, maxPrice + 3);
        plot.setRenderer(1, rendererPrice);
        plot.setDataset(1, datasetPrice);
        plot.mapDatasetToRangeAxis(1, 0);

        //volume
        BarRenderer rendererVolume = new BarRenderer();
        plot.setRangeAxis(1, new NumberAxis("Volume (BTC)"));
        plot.getRangeAxis(1).setRange(minVolume, maxVolume + 1);
        plot.setRenderer(2, rendererVolume);
        plot.setDataset(2, datasetVolume);
        plot.mapDatasetToRangeAxis(2, 1);
        //NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
        //rangeAxis.setRange(minVolume, maxVolume+1);

        plot.setRangeGridlinesVisible(true);
        plot.setDomainGridlinesVisible(true);
        //date
        plot.setDomainAxis(new CategoryAxis("Date"));

        plot.setOrientation(PlotOrientation.VERTICAL);
        plot.getDomainAxis().setCategoryLabelPositions(CategoryLabelPositions.UP_45);

        //plot chart
        JFreeChart chart = new JFreeChart(plot);
        chart.setTitle("Last " + jsonArray.length() + " transactions (24h)");

        return chart;
    }

    
}
