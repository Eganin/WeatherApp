# WeatherApp

## :scroll: Description
This application presents dynamic weather, conveying the weather through a landscape whose details change depending on the time, weather and location at that particular moment.
This dynamic landscape repeats the day/night cycle with several layers that change depending on the phase of the day (night, sunrise, day and sunset). 
In addition, a particle generation system has been created to draw clouds and rain.
The app also displays basic weather information in four sections:
- Details: current weather.
- A graph showing the temperature as a function of time. 
- Weather Radar
- For this week : 7 day forecast

## Tech stack & Open-source libraries
- Minimum SDK level 26
- 100% Kotlin based + Coroutines.
- Hilt for dependency injection.
- Retrofit;
- Compose Navigation;
- Location Services
- UI dependency: View Pager,Constraint Layout, Swipe layout
- UI : Canvas for chart and dynamic weather section
- JetPack
  - Compose - A modern toolkit for building native Android UI.
  - ViewModel - UI related data holder, lifecycle aware.
  - Room Persistence - construct database.

## :camera_flash: Screenshots
<img src="/assets/images/weather_card.jpg" width="260">&emsp;
<img src="/assets/images/radar.jpg" width="260">&emsp;
<img src="n/assets/images/rain.jpg" width="260">&emsp;
<img src="/assets/images/snow.jpg" width="260">&emsp;
<img src="/assets/images/dynamic_list.jpg" width="260">&emsp;
<img src="/assets/images/list.jpg" width="260">
### Day Theme
<img src="/assets/images/day.jpg" width="260">
### Morning theme
<img src="/assets/images/morning.jpg" width="260">
### Evening theme
<img src="/assets/images/evening.jpg" width="260">
### Night theme
<img src="/assets/images/night.jpg" width="260">
