# Technical details
- Java 17
- Spring boot 3
- Angular 17
- PostgreSQL

I did not spend time setting it up on Docker - please let me know if that's necessary.
Get it running very simply by:
* start PSQL DB (dump.sql added)
* run Spring Boot app in IDE
* `ng serve` in angular folder


Set up PSQL DB credentials in application.properties or use the default:
* `URL = jdbc:postgresql://localhost:5432/postgres`
* `USERNAME = postgres`
* `PASSWORD = postgres`

Swagger available at `http://localhost:8080/swagger`

# Foreword

I tried keeping the solution simplistic and avoided certain development practices which I would implement in a larger codebase like dev/test/prod profiles etc.
As the goal of this task, I created a form for users - no more, no less (but with a lot of support for maintenance or further developments). The entire app is built around that premise.

# 1. Correct all of the deficiencies in index.html

I assumed it was not expected of me to improve UI/UX and the deficiencies instead were related to bad HTML structure.
Also
* I did not add security or caching headers to .htaccess
* I did not add CAPTCHA or honeypots for defenses against automated bots
* I did not set up CDNs
* I did not add preloading or preconnect/preload/prefetch or fetch priority
* Not SEO optimized
* Did not measure metrics like FCP, LCP, CLS and so on

# 2. "Sectors" selectbox:

I identified the structure (or "business requirement") of this selectbox to be hierarchical and ordered alphabetically.
The following architectural decisions of the solution are based on that.

## 2.1. Add all the entries from the "Sectors" selectbox to database

I kept a simplistic approach to database with the structure: 

| id | name | parent_sector_id |

This keeps our database structural complexity low. 
I could have added another column or table for relationships (or even store as JSON) but I figured it's better this way.

## 2.2. Compose the "Sectors" selectbox using data from database

The API returns a parent/child JSON structure, sorted alphabetically.
Front-end renders all sectors and its children recursively, which means it supports N amount of data without requiring additional database structures or development efforts.

I did not take time to add caching as I already have experience with this as well. But if I would have, I'd have used in this case Spring's @Cacheable and Read Through strategy (would've liked to use Write Through strategy but no API endpoint would exist to update DB & cache).

# 3. Perform the following activities after the "Save" button has been pressed: 

* I did not add request throttling
* I did not set up any role based auths
* I did not review with OWASP ZAP

## 3.1. Validate all input data (all fields are mandatory)

Validation done on front-end & back-end.

## 3.2. Store all input data to the database (Name, Sectors, Agree to terms)

| id | UUID | user_name | sector_ids | agree_to_terms |

UUID is used to reference user for editing existing data.

## 3.3. Refill the form using stored data 

Response from the API is used to fill the form. Some parts of the stored data are not returned for security reasons (ID).

## 3.4. Allow the user to edit his/her own data during the session

For the duration of the visit (but until refresh) the user is assigned UUID (initially null; value assigned after receiving response from back-end) and will edit their data as they please.
Every request passes the UUID of their data during the "session".

I kept it simple for the reason being that I have built a whole system with sessions for users already in https://loim.ee and don't see the need to prove it here (if it was expected to use some sort of extra sessions management).
I would set up sessions with Spring Session in a cookie with Secure, HttpOnly and Domain + TTL and whatever else needed. I have handled sessions in Redis cache previously. Regarding security I would minimally attach UUID to the session and on updates validate that it belongs to the session.
