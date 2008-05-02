<?xml version="1.0" encoding="ISO-8859-1"?>

<ncl id="exemplo04" 
xmlns="http://www.ncl.org.br/NCL3.0/EDTVProfile">

   <head>

      <regionBase>
         <region id="rgVideo1" left="200" top="168" width="320" height="240" />
         <region id="rgLegenda" left="200" top="450" width="240" height="50" />
      </regionBase>

      <descriptorBase>
         <descriptor id="dVideo1" region="rgVideo1" />
         <descriptor id="dLegenda" region="rgLegenda">
            <descriptorParam name="border" value="none" />
         </descriptor>
      </descriptorBase>

      <connectorBase>
         <importBase alias="connectors" documentURI="connectorBaseInterfaceArea.ncl"/>
      </connectorBase>

   </head>

   <body>

      <port id="pInicio" component="video1" />

      <media type="video/mpeg" id="video1" src="media/video1.mpg" descriptor="dVideo1">
         <area id="aVideoLegenda01" begin="5s" end="9s"/>
         <area id="aVideoLegenda02" begin="10s" end="14s"/>
         <area id="aVideoLegenda03" begin="15s" end="19s"/>
      </media>

      <media type="text/html" id="legenda01" src="media/legenda01.html" descriptor="dLegenda" />
      <media type="text/html" id="legenda02" src="media/legenda02.html" descriptor="dLegenda" />
      <media type="text/html" id="legenda03" src="media/legenda03.html" descriptor="dLegenda" />

      <link id="lLegenda01_start" xconnector="connectors#onBegin1StartN">
         <bind component="video1" interface="aVideoLegenda01" role="onBegin" />
         <bind component="legenda01" role="start" />
      </link>


      <link id="lLegenda01_stop" xconnector="connectors#onEnd1StopN">
         <bind component="video1" interface="aVideoLegenda01" role="onEnd" />
         <bind component="legenda01" role="stop" />
      </link>

      <link id="lLegenda02_start" xconnector="connectors#onBegin1StartN">
         <bind component="video1" interface="aVideoLegenda02" role="onBegin" />
         <bind component="legenda02" role="start" />
      </link>


      <link id="lLegenda02_stop" xconnector="connectors#onEnd1StopN">
         <bind component="video1" interface="aVideoLegenda02" role="onEnd" />
         <bind component="legenda02" role="stop" />
      </link>


      <link id="lLegenda03_start" xconnector="connectors#onBegin1StartN">
         <bind component="video1" interface="aVideoLegenda03" role="onBegin" />
         <bind component="legenda03" role="start" />
      </link>

      <link id="lLegenda03_stop" xconnector="connectors#onEnd1StopN">
         <bind component="video1" interface="aVideoLegenda03" role="onEnd" />
         <bind component="legenda03" role="stop" />
      </link>

   </body>
</ncl>
