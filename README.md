This is a demo to show how to listen to change in network state in Android and how to change Wifi state.  
In case the device has no internet connection, an alert dialog will be displayed to the user where:  
* The user can enable Wifi and try to connect  
  * The Wifi woould be enabled (using WifiManager) and a progress dialog would be displayed until a connection is established.
  * The progress dialog is displayed for a limited time (in the code it's set to 3 seconds). If connection fails to be established within       the allocated time, an error dialog will be displayed prompting the user to:
    * Open Wifi settings and manually connect
    * Retry
* The user can retry to make sure if the device has internet connection
  * It will check if the device has internet connection
    * If not, the same alert dialog will be displayed
    * If yes, the dialog will be dismissed and the user interacts with the application as normal
* The user can simply cancel the dialog and nothing happens  

A broadcast receiver is used in order to determine when the connection state changes  
For a more thorough overview, make sure to check this [post](http://mobiledevhub.com/2017/11/03/android-fundamentals-network-monitoring/).   
The Java version for this repository can be found [here](https://github.com/MChehab94/Network-Handling)  
