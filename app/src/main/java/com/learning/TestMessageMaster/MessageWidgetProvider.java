package com.learning.TestMessageMaster;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.os.Bundle;
import android.widget.RemoteViews;
import com.google.assistant.appactions.widgets.AppActionsWidgetExtension;

public class MessageWidgetProvider extends AppWidgetProvider {

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId, String messageText, String recipient) {
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.widget_layout);
        views.setTextViewText(R.id.widget_message, messageText);
        views.setTextViewText(R.id.widget_recipient,recipient);
        Bundle optionsBundle = appWidgetManager.getAppWidgetOptions(appWidgetId);
        String bii =
                optionsBundle.getString(AppActionsWidgetExtension.EXTRA_APP_ACTIONS_BII); // "actions.intent.CREATE_TAXI_RESERVATION"
        Bundle params =
                optionsBundle.getBundle(AppActionsWidgetExtension.EXTRA_APP_ACTIONS_PARAMS);
        AppActionsWidgetExtension appActionsWidgetExtension = AppActionsWidgetExtension.newBuilder(appWidgetManager)
                .setResponseSpeech("Here's your message"+ messageText)  // TTS to be played back to the user
                .setResponseText("The new message just added is "+ messageText)  // Response text to be displayed in Assistant
                .build();

        // Update widget with TTS
        appActionsWidgetExtension.updateWidget(appWidgetId);

        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // Perform this loop for each App Widget that belongs to this provider
        //have to use shared preference
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId, "No new message","No Recipient");
        }
    }

    public static void updateTextWidgets(Context context, String messageText, String recipient) {
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
        int[] appWidgetIds = appWidgetManager.getAppWidgetIds(new ComponentName(context, MessageWidgetProvider.class));
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId, messageText,recipient);
        }
    }
}
