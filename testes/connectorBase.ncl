<?xml version="1.0" encoding="ISO-8859-1"?>

<ncl id="connectorBase" 
xmlns="http://www.ncl.org.br/NCL3.0/BDTVProfile">

<head>

<connectorBase>
<causalConnector id="onBegin1StartN">
  <simpleCondition role="onBegin"/>
  <simpleAction role="start" max="unbounded" qualifier="par"/>
</causalConnector>

<causalConnector id="onEnd1Start1">
  <simpleCondition role="onEnd"/>
  <simpleAction role="start"/>
</causalConnector>

<causalConnector id="onSelection1SetNStartNStopNAbortNDelay">
  <connectorParam name="keyCode" />
  <connectorParam name="valueSet" />
  <simpleCondition role="onSelection" key="$keyCode" />
  <compoundAction operator="seq">
    <simpleAction role="set" value="$valueSet" max="unbounded" qualifier="par"/>
    <simpleAction role="stop" max="unbounded" qualifier="par"/>
    <simpleAction role="dstart" actionType="start" eventType="presentation" delay="0.5s" max="unbounded" qualifier="par"/>
    <simpleAction role="dstop"  actionType="stop"  eventType="presentation" delay="0.5s" max="unbounded" qualifier="par"/>
    <simpleAction role="dabort" actionType="abort"  eventType="presentation" delay="0.5s" max="unbounded" qualifier="par"/>
  </compoundAction>
</causalConnector>

<causalConnector id="onEnd1StopN">
  <simpleCondition role="onEnd"/>
  <simpleAction role="stop" max="unbounded" qualifier="par"/>
</causalConnector>

<causalConnector id="onBegin1PauseNStartN">
  <simpleCondition role="onBegin"/>
  <compoundAction operator="seq">
    <simpleAction role="pause" max="unbounded" qualifier="par"/>
    <simpleAction role="start" max="unbounded" qualifier="par"/>
  </compoundAction>
</causalConnector>

<causalConnector id="onEnd1ResumeN">
  <simpleCondition role="onEnd"/>
  <simpleAction role="resume" max="unbounded" qualifier="par"/>
</causalConnector>

<causalConnector id="onEnd1StartN">
  <simpleCondition role="onEnd"/>
  <simpleAction role="start" max="unbounded" qualifier="par"/>
</causalConnector>

<causalConnector id="onSelection1SetStartStopDelay">
  <connectorParam name="keyCode" />
  <connectorParam name="valueSet" />
  <simpleCondition role="onSelection" key="$keyCode" />
  <compoundAction operator="seq">
    <simpleAction role="set" value="$valueSet" max="unbounded" qualifier="par"/>
    <simpleAction role="start" actionType="start" eventType="presentation" delay="0.5s" max="unbounded" qualifier="par"/>
    <simpleAction role="stop"  actionType="stop"  eventType="presentation" delay="0.5s" max="unbounded" qualifier="par"/>
  </compoundAction>
</causalConnector>

</connectorBase>

</head>

</ncl>
