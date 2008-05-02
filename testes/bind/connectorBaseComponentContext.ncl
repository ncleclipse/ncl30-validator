<?xml version="1.0" encoding="ISO-8859-1"?>

<ncl id="connectorBase" 
xmlns="http://www.ncl.org.br/NCL3.0/EDTVProfile" 
xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" 
xsi:schemaLocation="http://www.ncl.org.br/NCL3.0/EDTVProfile http://www.ncl.org.br/NCL3.0/profiles/NCL30EDTV.xsd">

<head>

<connectorBase>

<causalConnector id="onBegin1StartN">
  <simpleCondition role="onBegin"/>
  <simpleAction role="start" max="unbounded" qualifier="par"/>
</causalConnector>

<causalConnector id="onEnd1StopN">
  <simpleCondition role="onEnd"/>
  <simpleAction role="stop" max="unbounded" qualifier="par"/>
</causalConnector>

<causalConnector id="onSelection1SetN">
  <connectorParam name="keyCode" />
  <connectorParam name="var"/>
  <simpleCondition role="onSelection" key="$keyCode" />
  <simpleAction role="set" value="$var" max="unbounded" qualifier="par"/>
</causalConnector>

<causalConnector id="onEnd1Start1">
  <simpleCondition role="onEnd"/>
  <simpleAction role="start"/>
</causalConnector>

<causalConnector id="onSelection1SetNStartNStopNAbortN">
  <connectorParam name="keyCode" />
  <connectorParam name="valueSet" />
  <simpleCondition role="onSelection" key="$keyCode" />
  <compoundAction operator="seq">
    <simpleAction role="set" value="$valueSet" max="unbounded" qualifier="par"/>
    <simpleAction role="start" max="unbounded" qualifier="par"/>
    <simpleAction role="stop" max="unbounded" qualifier="par"/>
    <simpleAction role="abort" max="unbounded" qualifier="par"/>
  </compoundAction>
</causalConnector>

<causalConnector id="onEnd1StartN">
  <simpleCondition role="onEnd"/>
  <simpleAction role="start" max="unbounded" qualifier="par"/>
</causalConnector>

</connectorBase>

</head>

</ncl>
