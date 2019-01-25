### Azure IoT Central Java SDK Documentation

#### Prerequisites

Java SE 8

#### Targets

Java 8 supported platforms,
Android (API > 24)

#### Install

There are two ways to get the .jar libraries for the Azure IoT Central device SDK:
* Include the project as a dependency in your project if your project is a Maven project.
* Download the source code and build on your machine

### Get Azure IoT Central SDK for Java from Maven (as a dependency)
_This is the recommended method of including the Azure IoTCentral SDK in your project, however this method will only work if your project is a Maven project and if you have gone through the setup described above_


* Navigate to http://search.maven.org, search for **com.microsoft.azure.sdk.iotcentral** and take note of the latest version number (or the version number of whichever version of the sdk you desire to use).
* In your main pom.xml file, add the Azure IoT Device SDK as a dependency using your desired version as follows:
```xml
	<dependency>
		<groupId>com.microsoft.azure.sdk.iotcentral</groupId>
		<artifactId>iotcentral-device-client</artifactId>
		<version>1.0.0</version>
		<!--This is the current version number as of the writing of this document. Yours may be different.-->
	</dependency>
```

### Build Azure IoTCentral device SDK for Java from the source code
* Get a copy of the **Azure IoTCentral SDK for Java** from GitHub (current repo). You should fetch a copy of the source from the **master** branch of the GitHub repository: <https://github.com/Azure/iotc_connect>
```
	git clone https://https://github.com/Azure/iotc_connect.git
```
* When you have obtained a copy of the source, you can build the SDK for Java.
* Java SDK is contained in the "JAVA_SDK" folder

* Open a command prompt and use the following commands:
```
	cd JAVA_SDK
	mvn install
```
The compiled JAR file with all dependencies bundled in can then be found at:
```
{IoTCentral SDK for Java root}/device/iot-device-client/target/iotcentral-device-client-{version}-with-dependencies.jar
```
When you're ready to use the Java device SDK in your own project, include this JAR file in your project.

#### Usage

```
import com.microsoft.azure.sdk.iotcentral.IoTCClient;
import com.microsoft.azure.sdk.iotcentral.device.enums.IOTC_CONNECT;

IoTCClient client=new IoTCClient(deviceId,scopeId, credType, credentials);
```
*deviceId*   : Device ID
*scopeId*    : IoT Central Scope ID
*credType*   : IOTC_CONNECT => `IOTC_CONNECT.SYMM_KEY` or `IOTC_CONNECT_X509_CERT`
*credentials*  : SAS key or x509 Certificate



##### SetLogging
Change the logging level
```
client.SetLogging(logLevel);
```

*logLevel*   : (default value is `IOTC_LOGGING.DISABLED`)
```
class IOTLogLevel:
  IOTC_LOGGING.DISABLED,
  IOTC_LOGGING.API_ONLY,
  IOTC_LOGGING.ALL
```

*i.e.* => client.SetLogging(IOTC_LOGGING.API_ONLY)

##### SetGlobalEndpoint
Change the service endpoint URL
```
client.SetGlobalEndpoint(url)
```

*url*    : URL for service endpoint. (default value is `global.azure-devices-provisioning.net`)

\**call this before connect*

##### Connect
connect device client

```
client.Connect()
```

##### SendTelemetry
send telemetry

```
client.SendTelemetry(payload,[callback])
```

*payload*  : A payload object. It can be a POJO, a map or a JSON string.

i.e. `client.SendTelemetry(new Sample(24))`
where Sample is a class with public properties.

##### SendState
send device state

```
client.SendState(payload)
```

*payload*  : A payload object. It can be a POJO, a map or a JSON string.

i.e. `client.SendState(new Flag("on"))`
where Flag is a class with public properties.

##### SendProperty
send a property

```
client.SendProperty(payload)
```

*payload*  : A payload object. It can be a POJO, a map or a JSON string.

i.e. `client.SendState(new Property("value"))`
where Property is a class with public properties.`

##### Disconnect
Disconnects device client

```
client.Disconnect([callback])
```

i.e. `client.Disconnect()`

##### on
set event callback to listen events

`ConnectionStatus` : connection status has changed
`MessageSent`      : message was sent
`Command`          : a command received from Azure IoT Central
`SettingsUpdated`  : device settings were updated

i.e.
```
client.on(event, callback)
```
*event*  : IOTC_EVENTS type
```
IOTC_EVENTS.ConnectionStatus, 
IOTC_EVENTS.MessageSent,
IOTC_EVENTS.Command,
IOTC_EVENTS.SettingsUpdated
```
*callback* : IoTCentralCallback type

```
client.on(IOTC_EVENTS.SettingsUpdated, new IoTCCallback() {
            @Override
            public void Exec(Object result) {
                System.out.println("Setting: " + result);
            }
        });
```
Command special behavior:

*after receiveing commands, it's possible to send operation progress back to the application as a property. Use the "Command" class for easy implementation*

```
client.on(IOTC_EVENTS.Command, new IoTCCallback() {

            @Override
            public void Exec(Object result) {
                Command cmd = null;
                if (result instanceof Command) {
                    cmd = (Command) result;
                }
                try {
                    client.SendProperty(cmd.getResponseObject("Executed"), null);
                } catch (IoTCentralException e) {
                    e.printStackTrace();
                }
            }
        });
```
*getResponseObject(value-to-send) returns a compatible object to be used by SendProperty*


##### callback info class

`iotc` client callbacks are instances of `IoTCCallback` or its derivates.
Must override "Exec" method.