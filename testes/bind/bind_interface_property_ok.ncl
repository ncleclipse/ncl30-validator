<?xml version="1.0" encoding="ISO-8859-1"?>

<ncl id="exemplo07" 
xmlns="http://www.ncl.org.br/NCL3.0/EDTVProfile">

   <head>

      <regionBase>
         <region id="rgVideo1" left="200" top="168" width="320" height="240" />
         <region id="rgImagem1" left="200" top="190" width="504" height="377" />
      </regionBase>

      <descriptorBase>
         <descriptor id="dVideo1" region="rgVideo1">
            <descriptorParam name="soundLevel" value="1" />
         </descriptor>
         <descriptor id="dImagem1" region="rgImagem1" />
      </descriptorBase>

      <connectorBase>
         <importBase alias="connectors" documentURI="connectorBaseInterfaceProperty.ncl"/>
      </connectorBase>

   </head>

   <body>

      <port id="pInicio" component="video1" />

      <media type="video/mpeg" id="video1" src="media/video1.mpg" descriptor="dVideo1">

         <area id="aVideo1Imagem1" begin="3s" end="8s"/>

         <property name="bounds" />

      </media>

      <media type="image/gif" id="imagem1" src="media/imagem1.gif" descriptor="dImagem1" />


      <link id="Video1Imagem1_start" xconnector="connectors#onBegin1Resize1StartN">

         <bind component="video1" interface="aVideo1Imagem1" role="onBegin" />

         <bind component="video1" interface="bounds" role="set">
            
		<bindParam name="bounds" value="200,18,160,120" />
         </bind>

         <bind component="imagem1" role="start" />
      </link>

      <link id="Video1Imagem1_stop" xconnector="connectors#onEnd1Resize1StopN">

         <bind component="video1" interface="aVideo1Imagem1" role="onEnd" />

         <bind component="video1" interface="bounds" role="set">
            <bindParam name="bounds" value="200,168,320,240" />
         </bind>

         <bind component="imagem1" role="stop" />
      </link>

   </body>
</ncl>
