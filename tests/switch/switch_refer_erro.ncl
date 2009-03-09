<?xml version="1.0" encoding="ISO-8859-1"?>


<ncl id="exemplo10" 
xmlns="http://www.ncl.org.br/NCL3.0/EDTVProfile">



   <head>


      <regionBase>
         <region id="rgTV" width="1920" height="1080">
            <region id="rgVideo1" left="200" top="168" width="320" height="240" />
            <region id="rgAudio1" left="200" top="410" width="320" />
            <region id="rgBotaoVermelho" left="200" top="420" width="25" height="25" />
            <region id="rgBotaoVerde" left="200" top="470" width="25" height="25" />
         </region>
      </regionBase>



      <descriptorBase>
         <descriptor id="dVideo1" region="rgVideo1" />

         <descriptor id="dVideo3" region="rgVideo1">
            <descriptorParam name="soundLevel" value="0" />
         </descriptor>
         <descriptor id="dAudio1" region="rgAudio1" />

         <descriptor id="dBotaoVermelho" region="rgBotaoVermelho" />
         <descriptor id="dBotaoVerde" region="rgBotaoVerde" />
      </descriptorBase>



      <ruleBase>
         <rule id="rEn" var="idioma" comparator="eq" value="en" />
         <rule id="rPt" var="idioma" comparator="eq" value="pt" />
      </ruleBase>

      <connectorBase>
         <importBase alias="connectors" documentURI="connectorBaseSwitch.ncl"/>
      </connectorBase>

   </head>

   <body>

      <port id="pInicio" component="video1" />

       <media type="application/x-ginga-settings" id="nodeSettings">
         <property name="idioma" />
       </media>

      <media type="video/mpeg" id="video1" src="media/video1.mpg" descriptor="dVideo1" />

      <media type="video/mpeg" id="video2" src="media/video2.mpg" descriptor="dVideo1" />

      <media type="image/gif" id="botaoVermelho" src="media/botao_vermelho.gif" descriptor="dBotaoVermelho" />
      <media type="image/gif" id="botaoVerde" src="media/botao_verde.gif" descriptor="dBotaoVerde" />


      <context id="contextoFilme">
         <port id="pVideo3" component="video3" />

         <media type="video/mpeg" id="video3" src="media/video3.mpg" descriptor="dVideo3" />



         <switch id="switchAudioIdioma">
            <bindRule rule="rEn" constituent="audioEn" />
            <bindRule rule="rPt" constituent="audioPt" />

            <media type="audio/mp3" id="audioEn" src="media/audioEn.mp3" descriptor="dAudio1" />
            <media type="audio/mp3" id="audioPt" src="media/audioPt.mp3" descriptor="dAudio1" />
         </switch>

   	 <switch id="switchAudioIdiomaRefer" refer="switchAudioIdioma_inexistente"/>

         <link id="lVideo3AudioStart" xconnector="connectors#onBegin1StartN">
            <bind component="video3" role="onBegin" />
            <bind component="switchAudioIdioma" role="start" />
         </link>



         <link id="lVideo3AudioStop" xconnector="connectors#onEnd1StopN">
            <bind component="video3" role="onEnd" />
            <bind component="switchAudioIdiomaRefer" role="stop" />
         </link>

       </context>
	
      <link id="lVideo1Init" xconnector="connectors#onBegin1StartN">
         <bind component="video1" role="onBegin" />
         <bind component="botaoVermelho" role="start" />
         <bind component="botaoVerde" role="start" />
      </link>

      <link id="lVideo1Loop" xconnector="connectors#onEnd1Start1">
         <bind component="video1" role="onEnd" />
         <bind component="video1" role="start" />
      </link>

      <link id="lSelectBotaoVermelhoIdioma" xconnector="connectors#onSelection1SetNStartNStopNAbortN">
         <bind component="botaoVermelho" role="onSelection">
            <bindParam name="keyCode" value="RED" />
         </bind>
         <bind component="nodeSettings" interface="idioma" role="set">
            <bindParam name="valueSet" value="en" />
         </bind>
         <bind component="botaoVerde" role="stop" />
         <bind component="botaoVermelho" role="stop" />
         <bind component="video1" role="abort" />
         <bind component="video2" role="start" />
      </link>

      <link id="lSelectBotaoVerdeIdioma" xconnector="connectors#onSelection1SetNStartNStopNAbortN">
         <bind component="botaoVerde" role="onSelection">
            <bindParam name="keyCode" value="GREEN" />
         </bind>
         <bind component="nodeSettings" interface="idioma" role="set">
            <bindParam name="valueSet" value="pt" />
         </bind>
         <bind component="botaoVerde" role="stop" />
         <bind component="botaoVermelho" role="stop" />
         <bind component="video1" role="abort" />
         <bind component="video2" role="start" />
      </link>

      <link id="lVideo2StartVideo3" xconnector="connectors#onEnd1StartN">
         <bind component="video2" role="onEnd" />
         <bind component="contextoFilme" interface="pVideo3" role="start" />
      </link>

   </body>
</ncl>
