<?xml version="1.0" encoding="ISO-8859-1"?>

<ncl id="exemplo02" 
xmlns="http://www.ncl.org.br/NCL3.0/EDTVProfile">

   <head>

      <regionBase>
         <region id="rgTV" width="1920" height="1080">
            <region id="rgTitulo1" left="448" top="116" width="1024" height="40" />
            <region id="rgVideo1" left="448" top="156" width="1024" height="768" />
	    <region id="rgVideo2" left="0" top="0" width="500" height="500" />
         </region>
      </regionBase>

      <descriptorBase>
         <descriptor id="dTitulo1" region="rgTitulo1">
            <descriptorParam name="border" value="none" />
         </descriptor>
         <descriptor id="dVideo1" region="rgVideo1">
            <descriptorParam name="soundLevel" value="1" />
         </descriptor>
	 <descriptor id="dVideo2" region="rgVideo2"/>
      </descriptorBase>

      <connectorBase>
         <importBase alias="connectors" documentURI="connectorBaseComponent.ncl" />
      </connectorBase>

   </head>
   <body>

      <port id="pInicio" component="video1" />

      <media type="text/html" id="titulo1" src="media/titulo1.html" descriptor="dTitulo1" />
      <media type="video/mpeg" id="video1" src="media/video1.mpg" descriptor="dVideo1" />

      <link id="lVideo1Titulo1Start" xconnector="connectors#onBegin1StartN">
         <bind component="video1" descriptor="dVideo2" role="onBegin" />
         <bind component="titulo1" role="start" />
      </link>

      <link id="lVideo1Titulo1Stop" xconnector="connectors#onEnd1StopN">
         <bind component="video1" role="onEnd" />
         <bind component="titulo1" role="stop" />
      </link>

   </body>
</ncl>
