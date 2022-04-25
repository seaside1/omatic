# State O-Matic Binding
The State O-Matic Binding will enable you to monitor electrical devices such as
washing machine, dryer, dishwasher, electrical car charging and so on.
The component is generic and any type of equipment can be monitored as long as
it is possible to read its power consumption.

Examples of usage: 
- Monitor your electric car when it is charging. Once the charging is done, you will get notified about the cost, consumption and time it took.
- Monitor your dishwasher, dryer or washing machine. Once they are finished you will get notified about the cost, consumption and time it took.
- Send info when the power consumption is above a certain limit for a device.

<img src="https://raw.githubusercontent.com/seaside1/omatic/master/doc/image/omatic.jpg" width=200 />

Please take a look at ThomDietrich's [post](https://community.openhab.org/t/washing-machine-state-machine/15587) in the openHAB forum about monitoring your washing machine.
The aim of this binding is to simplify the rules and do the state transitions and calculations in a java binding.
It should thus be easier to write smaller and simpler rules in DSL or Jython to handle the state Machine.
## Installation
- Use OpenHAB Marketplace for installation (Recommended) or
- Download jar file from here:  <br>
    https://github.com/seaside1/omatic/releases <br>
    Copy the jar file to the addon foler usually located under /usr/share/openhab/addons
    Restart openhab
- Add an OMatic State Machine as thing using the GUI and set its parameters (It is also possible to create a .thing file and do this manually)    



## States
<img src="https://raw.githubusercontent.com/seaside1/omatic/master/doc/image/stateomatic.png" width=200 />

The state machine has the following states: Not Started, Active, Idle, Completed.
Where the state machine is considered to be running when the state is Active or Idle.
The following transitions are possible:

### Not Started -> Active 
This will Start the state machine if the powerInput is greater than active threshold

### Active -> Idle 
If the powerInput is below the active threshold, the machine can remain idle for
a configured amount of time

### Idle -> Active
If the powerInput is greater than the active threshold, the idle timer will be reset once this happens

### Idle -> Completed
If the configurable idle timer is exceeded the state machine will jump to the completed state

### Completed -> Active
The state machine is restarted and active again. The input Power has to be greater than the active threshold.

## Supported Things
- stateMachine - This will create a state machine, where you can configure idle time and
active Threshold

## Discovery

Discovery is currently not supported. 


## Binding Configuration
 
The binding has no configuration options, all configuration is done at the Thing levels.


## Thing Configuration

| Parameter         | Description                                                                    | Config   | Default             |
| ------------------|--------------------------------------------------------------------------------|--------- | --------------------|
| Name              | The Name of the state machine i.e washingMachine, Dryer etc                    | Required | -                   |
| Power Input Item  | The name of the Input Item for getting power values                            | Required | -                   |
| Energy Input Item | The name of the Input Item for energy values                                   | Optional | -                   |
| Idle Time         | Time max time in seconds for the appliance to be idle                          | Required | 60                  |
| Static Power      | Static power in Watts, for devices that can't supply power values              | Optional | -                   |
| Timer Delay       | Delay before checking the state if no power value has been provided.           | Required | 10                  |
| Active Threshold  | Threshold for when the appliance is to be transitioned to state active         | Required | 100                 |
| Cost              | Cost for 1 kWh in your favorite  currency                                      | Required | 1.0                 |
| Date Format       | Date and time format used for started / completed                              | Required | YYYY-MM-dd HH:mm:ss |


## Channels

| Channel ID         | Item Type | Description                                                          | Permissions |
|--------------------|-----------|--------------------------------------------------------------------- | ----------- |
| Energy(Mea)        | Number    | Current measured machine consumption in kWh                          | Read        |
| Energy(Est)        | Number    | Current estimated machine consumption in kWh                         | Read        |
| Cost(Mea)          | Number    | Current measured cost in relation to kWh                             | Read        |
| Cost(Est)          | Number    | Current estimated cost in relation to kWh                            | Read        |
| Power              | Number    | Current or last Power value used by the state machine                | Read        |
| Max Power          | Number    | The highest used power value                                         | Read        |
| Total Energy(Mea)  | Number    | Total measured energy (kWh)                                          | Read        |
| Total Energy(Est)  | Number    | Total estimated energy (kWh)                                         | Read        |
| Total Cost(Mea)    | Number    | Total measured cost (kWh * cost)                                     | Read        |
| Total Cost(Est)    | Number    | Total estimated cost (kWh * cost)                                    | Read        |
| Running Time       | Number    | Current machine running time in seconds                              | Read        |
| Running Time Str   | String    | Current machine running time formatted string                        | Read        |
| Tot.Running Time   | Number    | Total machine running time in seconds                                | Read        |
| Tot.Running TimeStr| String    | Total machine running time formatted string                          | Read        |
| State              | String    | Current state of the State machine                                   | Read        |
| Started            | String    | Timestamp for when the machine was started                          | Read        |
| Completed          | String    | Timestamp for when the machine was completed                        | Read        |
| Running            | Switch    | On if the state machine is running                                   | Read        |
| Disable            | Switch    | Will disable and stop the state machine                              | Write       |
| Reset Meters       | Switch    | Will reset all statistics                                            | Write       |

## Full Example

It's recommended to use OpenHAB GUI to add and configure the state machines, but this below is an example without the specification of the thing.


items/omatic.items

```
Group    gOMatic
Group    OMTestMachine               "TestMachine"                       (gOMatic)
Number   OMTestMachinePower         "Power [%.2f W]" (OMTestMachine) { channel="omatic:machine:e74a54e7:power" } 
Number   OMTestMachineEnergy1         "Energy [%.2f kWh]" (OMTestMachine) { channel="omatic:machine:e74a54e7:energy1" }
Number   OMTestMachineEnergy2         "Energy Estimated [%.2f kWh]" (OMTestMachine) { channel="omatic:machine:e74a54e7:energy2" }
Number   OMTestMachineCost1         "Cost Measured [%.2f EUR]" (OMTestMachine) { channel="omatic:machine:e74a54e7:cost1" }
Number   OMTestMachineCost2         "Cost Estimated [%.2f EUR]" (OMTestMachine) { channel="omatic:machine:e74a54e7:cost2" }
Number   OMTestMachineMaxPower        "Max Power [%.2f W]" (OMTestMachine) { channel="omatic:machine:e74a54e7:max-power" }
Number   OMTestMachineTotalEnergy1     "Total Measured Energy [%.2f kWh]" (OMTestMachine) { channel="omatic:machine:e74a54e7:total-energy1" }
Number   OMTestMachineTotalEnergy2     "Total Estimated Energy [%.2f kWh]" (OMTestMachine) { channel="omatic:machine:e74a54e7:total-energy2" }
Number   OMTestMachineTotalCost1     "Total Measured Cost [%.2f EUR]" (OMTestMachine) { channel="omatic:machine:e74a54e7:total-cost1" }
Number   OMTestMachineTotalCost2     "Total Estimated Cost [%.2f EUR]" (OMTestMachine) { channel="omatic:machine:e74a54e7:total-cost2" }
String   OMTestMachineState     "State is [MAP(omatic.map):%s]" (OMTestMachine) { channel="omatic:machine:e74a54e7:state" }
Number   OMTestMachineTime     "Running Time [%ds]" (OMTestMachine) { channel="omatic:machine:e74a54e7:time" }
String   OMTestMachineTimeStr     "Running Time [%s]" (OMTestMachine) { channel="omatic:machine:e74a54e7:time-str" }
String   OMTestMachineStarted "Started [%s]" (OMTestMachine)  { channel="omatic:machine:e74a54e7:started" }
String   OMTestMachineCompleted "Completed [%s]" (OMTestMachine) { channel="omatic:machine:e74a54e7:completed" }
Number   OMTestMachineTotalTime     "Total Running Time [%ds]" (OMTestMachine) { channel="omatic:machine:e74a54e7:total-time" }
String   OMTestMachineTotalTimeStr     "Total Running Time [%s]" (OMTestMachine) { channel="omatic:machine:e74a54e7:total-time-str" }
Switch   OMTestMachineRunning       "Running [%s]"    (OMTestMachine) { channel="omatic:machine:e74a54e7:running" }
Switch   OMTestMachineReset        "Reset [%s]"    (OMTestMachine) { channel="omatic:machine:e74a54e7:reset" }
Switch   OMTestMachineDisable        "Disable [%s]"    (OMTestMachine) { channel="omatic:machine:e74a54e7:disable" }

```

rules/omatic.rules
```
rule "TestMachineCompleted"
when 
    Item OMTestMachineRunning changed from ON to OFF
then
    val String result = "Tests Machine" + "\n" 
                    + "\nStarted at: " + OMTestMachineStarted.state.toString 
                    + "\nFinished at: "+ OMTestMachineCompleted.state.toString 
                    + "\nRunningTime: " + OMTestMachineTimeStr.state.toString 
                    + "\nConsumption: " + Math::round((OMTestMachineEnergy1.state as DecimalType).doubleValue) + " kWh" 
                    + "\nConsumption2: " + Math::round((OMTestMachineEnergy2.state as DecimalType).doubleValue) + " kWh" 
                    + "\nTotal: " + Math::round((OMTestMachineTotalEnergy1.state as DecimalType).doubleValue)  + " kWh" 
                    + "\nTotal2: " + Math::round((OMTestMachineTotalEnergy2.state as DecimalType).doubleValue) + " kWh" 
                    + "\nTotal Time: " + OMTestMachineTotalTimeStr.state.toString + "\n"
    logInfo("OMATIC", result)
                
    //NotifyTestMachine.sendCommand(result) // Doing mqtt notification 
    //NotifySlack.sendCommand(result) //Example sending notification to slack
end
```
transform/omatic.map
```
NOT_STARTED=Not Started
IDLE=Idle
ACTIVE=Active
COMPLETE=Complete
-=Unknown
NULL=Unknown
```

sitemaps/omatic.sitemap

```
sitemap omatic label="State O-Matic Binding" {
    Frame {
         Group item=gOMatic
    }
}
```

## Getting Started
In order to successfully monitor your electrical appliance, you should measure the power consumption while it is running.
Take a look at tools like Grafana to see spike in power and where it is generally at.
Select a good active-threshold value in watts and also select a reasonable idle time in seconds. If it's not working
and the state machine is completed when it should, adjust the values.

Below is an example what it looks like running a washing Machine:

<img src="https://raw.githubusercontent.com/seaside1/omatic/master/doc/image/washingpower.png" width=400 />

Configuration for this state machine is Active Threshold: 45 and idle time: 120

## Power values
The binding is dependent on receiving power values, that is done by specifying an item name in the 
configuration for the Power values. The Item must be either a Numeric item or a Switch item (for static power consumption monitoring).

## Measured Energy vs Estimated Energy
The binding can estimate the energy used by the state machine. This is an estimation that can be improved. Basically it will 
take all received power values, calculate and average and use that in combination with the duration to estimate energy consumption.
The other option is to use Measured Energy, which works in the same way as power. You send the energy input to the channel "Energy Input"
the binding will take the last recieved value as a starting energy when the state machine is started, it will then check when the state machine
is finished what the consumption is and takes the difference between the two.

## Roadmap

* More statistics, Average Cost, average consumption, average power usage etc
* Better energy estimate calculations

## Changelog
#### BETA1
- Fixed energy quantity type
### Alpha10
- Fixed parsing of quantity types by using openhab core classes
### Alpha9
- Fixed one more potential deadlock
- Fixed quantity types
### Alpha7
- Fixed type of total-running-time, change to Number
### Alpha6
- Fixed Quantity Type parsing
### Alpha5
- Fixed synchronization issues deadlock
- Fixed Number:Energy and Number:Power

## Resources
https://github.com/seaside1/omatic/releases/download/org.openhab.binding.omatic-3.x.x-BETA1/org.openhab.binding.omatic-3.x.x-BETA1.jar
