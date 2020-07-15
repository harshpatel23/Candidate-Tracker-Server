package com.example.candidatetracker.demo.service;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.DateTime;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.calendar.Calendar;
import com.google.api.services.calendar.CalendarScopes;
import com.google.api.services.calendar.model.Event;
import com.google.api.services.calendar.model.EventAttendee;
import com.google.api.services.calendar.model.EventDateTime;
import com.google.api.services.calendar.model.EventReminder;

import org.springframework.stereotype.Service;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.GeneralSecurityException;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;

@Service
public class CalendarService {
    private static final String APPLICATION_NAME = "Google Calendar API Java Quickstart";
    private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();
    private static final String TOKENS_DIRECTORY_PATH = "tokens";

    /**
     * Global instance of the scopes required by this quickstart.
     * If modifying these scopes, delete your previously saved tokens/ folder.
     */
    private static final List<String> SCOPES = Collections.singletonList(CalendarScopes.CALENDAR);
    private static final String CREDENTIALS_FILE_PATH = "/credentials.json";

    /**
     * Creates an authorized Credential object.
     * @param HTTP_TRANSPORT The network HTTP Transport.
     * @return An authorized Credential object.
     * @throws IOException If the credentials.json file cannot be found.
     */
    private static Credential getCredentials(final NetHttpTransport HTTP_TRANSPORT) throws IOException {
        // Load client secrets.
        InputStream in = CalendarService.class.getResourceAsStream(CREDENTIALS_FILE_PATH);
        if (in == null) {
            throw new FileNotFoundException("Resource not found: " + CREDENTIALS_FILE_PATH);
        }
        GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(JSON_FACTORY, new InputStreamReader(in));

        // Build flow and trigger user authorization request.
        GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(
                HTTP_TRANSPORT, JSON_FACTORY, clientSecrets, SCOPES)
                .setDataStoreFactory(new FileDataStoreFactory(new java.io.File(TOKENS_DIRECTORY_PATH)))
                .setAccessType("offline")
                .build();
        LocalServerReceiver receiver = new LocalServerReceiver.Builder().setPort(8888).build();
        return new AuthorizationCodeInstalledApp(flow, receiver).authorize("user");
    }

    public void createEvent(Date startDate, Date endDate, String interviewer_email, String candidate_email) throws IOException, GeneralSecurityException {
        // Build a new authorized API client service.
        System.out.println("in createEvent");

        final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
        Calendar service = new Calendar.Builder(HTTP_TRANSPORT, JSON_FACTORY, getCredentials(HTTP_TRANSPORT))
                .setApplicationName(APPLICATION_NAME)
                .build();
        System.out.println("service created");
        //Create event
        Event event = new Event()
            .setSummary("Interview Scheduled")
            // .setLocation("800 Howard St., San Francisco, CA 94103")
            .setDescription("Interview for job at Wissen through Candidate Tracker");

        DateTime startDateTime = new DateTime(startDate);
        EventDateTime start = new EventDateTime()
            .setDateTime(startDateTime)
            .setTimeZone("Asia/Kolkata");
        event.setStart(start);

        DateTime endDateTime = new DateTime(endDate);
        EventDateTime end = new EventDateTime()
            .setDateTime(endDateTime)
            .setTimeZone("Asia/Kolkata");
        event.setEnd(end);

        // String[] recurrence = new String[] {"RRULE:FREQ=DAILY;COUNT=2"};
        // event.setRecurrence(Arrays.asList(recurrence));

        EventAttendee[] attendees = new EventAttendee[] {
            new EventAttendee().setEmail(interviewer_email),
            new EventAttendee().setEmail(candidate_email),
        };
        event.setAttendees(Arrays.asList(attendees));

        EventReminder[] reminderOverrides = new EventReminder[] {
            new EventReminder().setMethod("email").setMinutes(60),
            new EventReminder().setMethod("email").setMinutes(30),
        };
        Event.Reminders reminders = new Event.Reminders()
            .setUseDefault(false)
            .setOverrides(Arrays.asList(reminderOverrides));
        event.setReminders(reminders);

        String calendarId = "primary";

        // event = service.events().insert(calendarId, event).execute();
        event = service.events().insert(calendarId, event).set("sendUpdates", "all").execute();


        System.out.println("service call made");
        System.out.printf("Event created: %s\n", event.getHtmlLink());
    }
}