<?xml version="1.0" encoding="ISO-8859-1"?>
<ncl id="exemplo13" xmlns="http://www.ncl.org.br/NCL3.0/BDTVProfile">

<!--++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
 !  CABEÇALHO DO PROGRAMA
 !++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++-->

   <head>
   
      <!--++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
       !  BASE DE REGIÕES:
       !  define as regiões na tela onde as mídias são apresentadas
       !++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++-->		
      <regionBase>
         <region id="rgVideo1" left="200" top="168" width="320" height="240" />
         <region id="rgVideo2" left="250" top="118" width="320" height="240" />
         <region id="rgTexto1" left="200" top="410" width="320" height="120" />
         <region id="rgBotaoVermelho" left="200" top="420" width="25" height="25" />
         <region id="rgBotaoVerde" left="200" top="470" width="25" height="25" />
      </regionBase>

      <!--++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
       !  BASE DE DESCRITORES:
       !  define como as mídias são apresentadas
       !++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++-->

      <descriptorBase>
         <descriptor id="dVideo1" region="rgVideo1" />
         <descriptor id="dVideo2" region="rgVideo2" />

         <descriptor id="dTexto1" region="rgTexto1" />

         <descriptor id="dBotaoVermelho" region="rgBotaoVermelho" />
         <descriptor id="dBotaoVerde" region="rgBotaoVerde" />
      </descriptorBase>

      <!--++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
       !  BASE DE CONECTORES:
       !  definem o comportamento dos elos
       !++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++-->

      <connectorBase>
         <importBase alias="connectors" documentURI="connectorBase.ncl"/>
      </connectorBase>
      <importedDocumentBase>
      	<importNCL alias="importdoc" documentURI="cyclicDependency.ncl"/>
      </importedDocumentBase>

   </head>

   <!--++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    !  CORPO DO PROGRAMA:
    !   define as mídias e estrutura do programa
    !++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++-->

   <body>

      <!--++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
       !  PONTO DE ENTRADA:
       !  indica o componente onde o programa inicia
       !++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++-->

      <port id="pInicio" component="video1" />

      <!--++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
       !  MÍDIAS:
       !  define o local dos arquivos de mídia e as associa com seus descritores
       !++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++-->
      <media type="video" id="video1" src="media/video1.mpg" descriptor="dVideo1" />

      <media type="video" id="videoPrincipal" src="media/video_principal.mpg" descriptor="dVideo1" />

      <media type="image" id="botaoVermelho" src="media/botao_vermelho.gif" descriptor="dBotaoVermelho" />
      <media type="image" id="botaoVerde" src="media/botao_verde.gif" descriptor="dBotaoVerde" />

      <!--++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
      !  CONTEXTOS:
      !  os documentos básico e avançado
      !++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++-->
      <context id="ctxBasico">
         <port id="pBasico" component="videoBasico" />

         <media id="videoBasico" refer="videoPrincipal">
            <!-- âncoras no vídeo que devem ser sincronizadas com a legenda -->
            <area id="aVideoBasicoTexto1" begin="1s" end="3s" />
            <area id="aVideoBasicoTexto2" begin="5s" end="15s" />
            <area id="aVideoBasicoTexto3" begin="16s" end="21s" />
            <area id="aVideoBasicoVideo1" begin="2s" end="4s" />
            <area id="aVideoBasicoVideo2" begin="8s" end="10s" />
            <area id="aVideoBasicoVideo3" begin="13s" end="16s" />
         </media>
         <media id="textoBasico1" type="text" src="media/basico1.html" descriptor="dTexto1" />
         <media id="textoBasico2" type="text" src="media/basico2.html" descriptor="dTexto1" />
         <media id="textoBasico3" type="text" src="media/basico3.html" descriptor="dTexto1" />

         <media id="videoBasico1" type="video" src="media/video_basico1.mpg" descriptor="dVideo2" />
         <media id="videoBasico2" type="video" src="media/video_basico2.mpg" descriptor="dVideo2" />
         <media id="videoBasico3" type="video" src="media/video_basico3.mpg" descriptor="dVideo2" />

         <!-- links para sincronizar os textos com o vídeo -->
         <link id="lBasicoTexto1Start" xconnector="connectors#onBegin1StartN">
            <bind component="videoBasico" interface="aVideoBasicoTexto1" role="onBegin" />
            <bind component="textoBasico1" role="start" />
         </link>
         <link id="lBasicoTexto2Start" xconnector="connectors#onBegin1StartN">
            <bind component="videoBasico" interface="aVideoBasicoTexto2" role="onBegin" />
            <bind component="textoBasico2" role="start" />
         </link>
         <link id="lBasicoTexto3Start" xconnector="connectors#onBegin1StartN">
            <bind component="videoBasico" interface="aVideoBasicoTexto3" role="onBegin" />
            <bind component="textoBasico3" role="start" />
         </link>

         <link id="lBasicoTexto1End" xconnector="connectors#onEnd1StopN">
            <bind component="videoBasico" interface="aVideoBasicoTexto1" role="onEnd" />
            <bind component="textoBasico1" role="stop" />
         </link>
         <link id="lBasicoTexto2End" xconnector="connectors#onEnd1StopN">
            <bind component="videoBasico" interface="aVideoBasicoTexto2" role="onEnd" />
            <bind component="textoBasico2" role="stop" />
         </link>
         <link id="lBasicoTexto3End" xconnector="connectors#onEnd1StopN">
            <bind component="videoBasico" interface="aVideoBasicoTexto3" role="onEnd" />
            <bind component="textoBasico3" role="stop" />
         </link>

         <!-- links para sincronizar os vídeos auxiliares com o vídeo principal -->
         <link id="lBasicoVideo1Start" xconnector="connectors#onBegin1PauseNStartN">
            <bind component="videoBasico" interface="aVideoBasicoVideo1" role="onBegin" />
            <bind component="videoBasico" role="pause" />
            <bind component="videoBasico1" role="start" />
         </link>
         <link id="lBasicoVideo2Start" xconnector="connectors#onBegin1PauseNStartN">
            <bind component="videoBasico" interface="aVideoBasicoVideo2" role="onBegin" />
            <bind component="videoBasico" role="pause" />
            <bind component="videoBasico2" role="start" />
         </link>
         <link id="lBasicoVideo3Start" xconnector="connectors#onBegin1PauseNStartN">
            <bind component="videoBasico" interface="aVideoBasicoVideo3" role="onBegin" />
            <bind component="videoBasico" role="pause" />
            <bind component="videoBasico3" role="start" />
         </link>

         <link id="lBasicoVideo1End" xconnector="connectors#onEnd1ResumeN">
            <bind component="videoBasico1" role="onEnd" />
            <bind component="videoBasico" role="resume" />
         </link>
         <link id="lBasicoVideo2End" xconnector="connectors#onEnd1ResumeN">
            <bind component="videoBasico2" role="onEnd" />
            <bind component="videoBasico" role="resume" />
         </link>
         <link id="lBasicoVideo3End" xconnector="connectors#onEnd1ResumeN">
            <bind component="videoBasico3" role="onEnd" />
            <bind component="videoBasico" role="resume" />
         </link>
      </context>

      <context id="ctxAvancado">
         <port id="pAvancado" component="videoAvancado" />

         <media id="videoAvancado" refer="videoPrincipal">
            <!-- âncoras no vídeo que devem ser sincronizadas com a legenda -->
            <area id="aVideoAvancadoTexto1" begin="5s" end="9s"/>
            <area id="aVideoAvancadoTexto2" begin="10s" end="14s"/>
            <area id="aVideoAvancadoTexto3" begin="15s" end="19s"/>
         </media>
         <media id="textoAvancado1" type="text" src="media/avancado1.html" descriptor="dTexto1" />
         <media id="textoAvancado2" type="text" src="media/avancado2.html" descriptor="dTexto1" />
         <media id="textoAvancado3" type="text" src="media/avancado3.html" descriptor="dTexto1" />

         <!-- links para sincronizar os textos com o vídeo -->
         <link id="lAvancadoTexto1Start" xconnector="connectors#onBegin1StartN">
            <bind component="videoAvancado" interface="aVideoAvancadoTexto1" role="onBegin" />
            <bind component="textoAvancado1" role="start" />
         </link>
         <link id="lAvancadoTexto2Start" xconnector="connectors#onBegin1StartN">
            <bind component="videoAvancado" interface="aVideoAvancadoTexto2" role="onBegin" />
            <bind component="textoAvancado2" role="start" />
         </link>
         <link id="lAvancadoTexto3Start" xconnector="connectors#onBegin1StartN">
            <bind component="videoAvancado" interface="aVideoAvancadoTexto3" role="onBegin" />
            <bind component="textoAvancado3" role="start" />
         </link>

         <link id="lAvancadoTexto1End" xconnector="connectors#onEnd1StopN">
            <bind component="videoAvancado" interface="aVideoAvancadoTexto1" role="onEnd" />
            <bind component="textoAvancado1" role="stop" />
         </link>
         <link id="lAvancadoTexto2End" xconnector="connectors#onEnd1StopN">
            <bind component="videoAvancado" interface="aVideoAvancadoTexto2" role="onEnd" />
            <bind component="textoAvancado2" role="stop" />
         </link>
         <link id="lAvancadoTexto3End" xconnector="connectors#onEnd1StopN">
            <bind component="videoAvancado" interface="aVideoAvancadoTexto3" role="onEnd" />
            <bind component="textoAvancado3" role="stop" />
         </link>
      </context>


      <!--++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
       !  ELOS
       !  define os elos que regem o sincronismo entre as mídias
       !++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++-->

      <!-- início do video1 deve exibir botões -->
      <link id="lVideo1Init" xconnector="connectors#onBegin1StartN">
         <bind component="video1" role="onBegin" />
         <bind component="botaoVermelho" role="start" />
         <bind component="botaoVerde" role="start" />
      </link>

      <!-- término do video1 deve dispará-lo novamente (deve tocar em loop) -->
      <link id="lVideo1Loop" xconnector="connectors#onEnd1StartN">
         <bind component="video1" role="onEnd" />
         <bind component="video1" role="start" />
      </link>

      <!-- define nível básico quando a tecla vermelha é pressionada -->
      <link id="lSelectBotaoVermelho" xconnector="connectors#onSelection1SetStartStopDelay">
         <bind component="botaoVermelho" role="onSelection">
            <bindParam name="keyCode" value="RED" />
         </bind>
         <bind component="botaoVerde" role="stop" />
         <bind component="botaoVermelho" role="dstop" />
         <bind component="video1" role="dabort" />
         <bind component="ctxBasico" interface="pBasico" role="dstart" />
      </link>

      <!-- define nível avançado quando a tecla verde é pressionada -->
      <link id="lSelectBotaoVerde" xconnector="connectors#onSelection1SetStartStopDelay">
         <bind component="botaoVerde" role="onSelection">
            <bindParam name="keyCode" value="GREEN" />
         </bind>
         <bind component="botaoVerde" role="dstop" />
         <bind component="botaoVermelho" role="stop" />
         <bind component="video1" role="dabort" />
         <bind component="ctxAvancado" interface="pAvancado" role="dstart" />
      </link>

   </body>
</ncl>

