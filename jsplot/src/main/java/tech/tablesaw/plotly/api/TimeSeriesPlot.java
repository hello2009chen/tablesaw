package tech.tablesaw.plotly.api;

import tech.tablesaw.api.DateColumn;
import tech.tablesaw.api.NumberColumn;
import tech.tablesaw.api.Table;
import tech.tablesaw.plotly.Plot;
import tech.tablesaw.plotly.components.Axis;
import tech.tablesaw.plotly.components.Figure;
import tech.tablesaw.plotly.components.Layout;
import tech.tablesaw.plotly.traces.ScatterTrace;
import tech.tablesaw.table.TableSliceGroup;

import java.util.List;

public class TimeSeriesPlot {

    private static final int HEIGHT = 600;
    private static final int WIDTH = 800;

    public static void show(String title, Table table, String dateColX, String yCol, String groupCol) {

        TableSliceGroup tables = table.splitOn(table.categoricalColumn(groupCol));

        Layout layout = standardLayout(title, dateColX, yCol);

        ScatterTrace[] traces  = new ScatterTrace[tables.size()];
        for (int i = 0; i < tables.size(); i++) {
            List<Table> tableList = tables.asTableList();
            traces[i] = ScatterTrace.builder(
                    tableList.get(i).dateColumn(dateColX),
                    tableList.get(i).numberColumn(yCol))
                    .showLegend(true)
                    .name(tableList.get(i).name())
                    .mode(ScatterTrace.Mode.LINE)
                    .build();
        }
        Figure figure = new Figure(layout, traces);
        Plot.show(figure);
    }

    public static void show(String title, Table table, String dateColXName, String yColName) {

        Layout layout = standardLayout(title, dateColXName, yColName);

        ScatterTrace trace = ScatterTrace.builder(
                table.dateColumn(dateColXName),
                table.numberColumn(yColName))
                .mode(ScatterTrace.Mode.LINE)
                .build();
        Figure figure = new Figure(layout, trace);
        Plot.show(figure);
    }

    public static void show(String title, String xTitle, DateColumn xCol, String yTitle, NumberColumn yCol) {

        Layout layout = standardLayout(title, xTitle, yTitle);

        ScatterTrace trace = ScatterTrace.builder(xCol, yCol)
                .mode(ScatterTrace.Mode.LINE)
                .build();
        Figure figure = new Figure(layout, trace);
        Plot.show(figure);
    }

    private static Layout standardLayout(String title, String xTitle, String yTitle) {
        return Layout.builder()
                .title(title)
                .height(HEIGHT)
                .width(WIDTH)
                .xAxis(Axis.builder()
                        .title(xTitle)
                        .build())
                .yAxis(Axis.builder()
                        .title(yTitle)
                        .build())
                .build();
    }
}