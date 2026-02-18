# Railway Network Support System
A console-based Java application to support a railway system. This project was designed and developed as part of the **Data Strucutres and Algorithms** course. Ths system allows the creation and management of railway lines, stations and train schedules.

## Overview
This project implements a **Railway Network Support System**, where a railway network is composed of multiple **lines**, each containing an **ordered sequence of stations** and multiple **daily schedules** per line.
Such coursework:
- Models a real world railway network using appropriate **abstract data types**
- Applies **algorithmic reasoning** and **data structure design**
- Analyzes time and space **complexity** of operations
- Ensure **data persistence** across executions using **Java serialization**

### Features
- Insert lines (`IL <line> <station1> station2> <...>`)
- Remove lines (`RL <line>`)
- List the stations of a line (`CL <line>`)
- List lines that a station is part of (`CE <line>`)
- Insert lines' daily schedules (trains) (`IH <line> <trainNumber> <station1 time1> <station2 time2> <...>`)
- Remove lines' daily schedules (`RH <line> <departureStation> <arrivalStation>`)
- Consult lines' schedules (`CH <line> <departureStation>`)
- List trains by station (`LC <station>`)
- Get best route between two stations (`MH <line> <startStation> <endStation> <expectedArrival>`)

## To Run
- Clone the repo `git repo <url>`
- Open it in Intellij IDEA (or a similar IDEA)
- Run the `Main` Class
- Interact using console commands (the assignment is given for details)
