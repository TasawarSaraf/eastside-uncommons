# Database Management System for Eastside Uncommons Rental Firm

Designed and implemented "Eastside Uncommons," a command-line interface project for managing a fictional rental-unit firm's relational database, using JDBC and OracleSQL for database operations and ensuring compatibility with project testing environments. Employed ER diagrams for database design, facilitating efficient data management and user interface interaction.


## Required Features
The following functionality is completed:

#### Database Design and Implementation
- [x] Design the database using Entity-Relationship (ER) diagrams.
- [x] Implement the database schema using OracleSQL.
- [x] Ensure the database supports queries for apartments by location, cost, and amenities.

#### Application Development
- [x] Develop the application using JDBC for database connectivity.
- [x] Create command-line interfaces for different user roles (Property Manager, Tenant, Company Manager, Financial Manager).
- [x] Implement user authentication and session management.

#### Feature Implementation for Various User Roles
###### Property Manager
- [x] Record visit data for prospective tenants.
- [x] Record lease data and manage lease agreements.
- [x] Handle move-outs and decide on security deposit refunds.
- [x] Add individuals or pets to existing leases.

###### Tenant
- [x] Check payment status and view the amount due.
- [x] Make rental payments through various payment methods.
- [x] Update personal data.

###### Company Manager
- [x] Add new properties with details (location, amenities, apartments).
- [x] Enable automatic data generation for apartments within properties.

###### Financial Manager
- [x] Access a read-only interface for aggregate data collection.
- [x] Produce financial reports.

##### User Interface and Usability
- [x] Ensure the interface is free of SQL jargon and user-friendly.
- [x] Implement at least two distinctly different interfaces, aiming for three for a perfect score.
- [x] Design interfaces to display relevant data to users based on their role without needing to remember information.

##### Security and Data Integrity
- [x] Implement mechanisms to prevent unauthorized updates during lease periods.
- [x] Secure user data, especially in authentication and payment processes.


## How to run (First Way)
1. Within the EastSideUncommons/ directory, run the makefile with "make"
2. There will now be a jar file compiled, which we can run with `java -jar EastsideUncommons.jar`
3. Use this app!

## How to run (Second Way)
1. EastsideUncommons.jar is already provided so you can just run `java -jar EastsideUncommons.jar`
2. Use this app!

# License

Copyright [2023] [Tasawar Saraf]

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.



