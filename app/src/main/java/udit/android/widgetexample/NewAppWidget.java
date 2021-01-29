package udit.android.widgetexample;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.RemoteViews;

/**
 * Implementation of App Widget functionality.
 */
public class NewAppWidget extends AppWidgetProvider {

    private static final String RELOAD = "RELOAD";
    private static int counter = 0;
    private static boolean flag = true;

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {

        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.new_app_widget);
        // Construct an Intent which is pointing this class.
        Intent intent = new Intent(context, NewAppWidget.class);
        intent.setAction(RELOAD);
        // And this time we are sending a broadcast with getBroadcast
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, intent,
                PendingIntent.FLAG_UPDATE_CURRENT);

        views.setViewVisibility(R.id.tvWidget, View.GONE);
        views.setViewVisibility(R.id.tvWidgetSecond, View.VISIBLE);
        views.setOnClickPendingIntent(R.id.tvWidget, pendingIntent);
        views.setOnClickPendingIntent(R.id.tvWidgetSecond, pendingIntent);
        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);
        if (RELOAD.equals(intent.getAction())) {
            counter++;
            // Construct the RemoteViews object
            RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.new_app_widget);
//            views.setViewVisibility(R.id.tvWidget, View.GONE);
            if (flag) {
                views.setViewVisibility(R.id.tvWidget, View.VISIBLE);
                views.setViewVisibility(R.id.tvWidgetSecond, View.GONE);
                flag = false;
            } else {
                views.setViewVisibility(R.id.tvWidget, View.GONE);
                views.setViewVisibility(R.id.tvWidgetSecond, View.VISIBLE);
                flag = true;
            }
//            views.setTextViewText(R.id.tvWidget, Integer.toString(counter));
            // This time we dont have widgetId. Reaching our widget with that way.
            ComponentName appWidget = new ComponentName(context, NewAppWidget.class);
            AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
            // Instruct the widget manager to update the widget
            appWidgetManager.updateAppWidget(appWidget, views);
        }
    }
}

