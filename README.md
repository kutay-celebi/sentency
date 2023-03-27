# Setup

## Prerequisites

1. JDK 11
2. Gradle
3. Node > v16
4. Docker

## Preparation

1. [Creating google service account](#creating-google-service-account).
2. [Creating google OAuth Credentials](#creating-google-oauth-client).
3. [Subscribing WordsAPI on RapidAPI](#subscribe-words-api-on-rapidapi).

# Local Development

## Overview of Environment Variables

| Variable                       | Description                                                                                                    |
|--------------------------------|----------------------------------------------------------------------------------------------------------------|
| DATABASE_DB-KIND               | Database type. [See also.](https://quarkus.io/guides/all-config#quarkus-datasource_quarkus.datasource.db-kind) |
| DATABASE_USERNAME              | Database username                                                                                              |
| DATABASE_PASSWORD              | Database password                                                                                              |
| DATABASE_URL                   | Database vertx url. [See also.](https://quarkus.io/guides/hibernate-reactive#hr-getting-started)               |
| GOOGLE_PROJECT-ID              | Google project id                                                                                              |
| GOOGLE_APPLICATION_CREDENTIALS | Google service accounts credential file path. [See also](#creating-google-service-account)                     |
| GOOGLE_CLIENT-ID               | For google login. [See also](#creating-google-oauth-client)                                                    |
| WORDS_API_RAPID_API_URI        | Rapid api base uri.                                                                                            |
| WORDS_API_RAPID_API_HOST       | Rapid api host.                                                                                                |
| WORDS_API_RAPID_API_KEY        | Rapid api key.                                                                                                 |

## Creating Google Service Account

1. Open [Google Console](https://console.cloud.google.com)
2. Create Project.
3. Enable [Cloud Translation API](https://console.cloud.google.com/apis/library/translate.googleapis.com) for project.
4. Create service account.
5. Add `Cloud Translation API User` permission to service account for using Google Cloud Translation Api
6. Create service account key. Copy downloaded json file to project path.
7. Give the absolute path of this json file to `GOOGLE_APPLICICATION_CREDENTIALS` as an environment variable.

## Creating Google OAuth Client

1. Go to [https://console.cloud.google.com/apis/credentials?project=PROJECT_NAME](#creating-google-oauth-client)
2. Create OAuth Client ID.
3. Adjust OAuth consent screen.
4. Adjust client ID to `GOOGLE_CLIENT-ID` as an environment variable.

## Subscribe Words API on RapidApi

1. Go to [WordsApi](https://rapidapi.com/dpventures/api/wordsapi)
2. Subscribe API.
3. Set host and key as environment variables.
