<?xml version="1.0" encoding="ISO-8859-1"?>

<!--++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
 !   EXEMPLO 10
 !
 !   Objetivo: Reproduzir um vídeo e permitir que o usuário 
 !             selecione o idioma do áudio, através do
 !             pressionamento das teclas vermelha e verde do
 !             controle remoto.
 !
 !   Características:
 !
 !   - sincronismo: simples (de início e término de mídias)
 !   - interação do usuário: seleção do áudio a ser exibido
 !
 !   Preparação:
 !
 !   Para executar este exemplo, é necessário ter as seguintes 
 !   mídias no subdiretório media a partir do caminho do 
 !   arquivo NCL:
 !
 !   1) arquivo de vídeo chamado video1.mpg, com uma introdução
 !      em loop
 !   2) arquivo de vídeo chamado video2.mpg, com uma abertura do 
 !      produtor do vídeo
 !   3) arquivo de vídeo chamado video4.mpg, que contém o 
 !      filme em si
 !   4) arquivo de áudio chamado audio1.mp3, com o áudio em inglês
 !   5) arquivo de áudio chamado audio2.mp3, com o áudio 
 !      em português
 !   6) arquivo de imagem chamado botao_audio1.gif, indicando
 !      a ação do botão vermelho
 !   7) arquivo de imagem chamado botao_audio2.gif, indicando
 !      a ação do botão verde
 !
 !++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++-->

<!--++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
 !  CABEÇALHO NCL:
 !  define as URIs dos esquemas da NCL
 !++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++-->

<ncl id="exemplo10" 
     xmlns="http://www.ncl.org.br/NCL3.0/EDTVProfile">

<!--++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
 !  CABEÇALHO DO PROGRAMA
 !++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++-->

<head>

<!--++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
 !  BASE DE REGIÕES:
 !  define as regiões na tela onde as mídias são apresentadas
 !++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++-->

<regionBase>
   <region id="rgTV" width="100%" height="100%">
      <region id="rgVideo1" width="100%" height="100%" zIndex="0"/>
      <region id="rgBotaoVermelho" left="3%" top="87%" 
              width="130" height="70" zIndex="1" />
      <region id="rgBotaoVerde" right="3%" top="87%"
              width="130" height="70" zIndex="1" />
   </region>
</regionBase>

<!--++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
 !  BASE DE DESCRITORES:
 !  define como as mídias são apresentadas
 !++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++-->

<descriptorBase>
   <descriptor id="dVideo1" region="rgVideo1" />
   <descriptor id="dVideo3" region="rgVideo1">
      <descriptorParam name="soundLevel" value="0" />
   </descriptor>
   <descriptor id="dAudio1" />
   <descriptor id="dBotaoVermelho" region="rgBotaoVermelho" />
   <descriptor id="dBotaoVerde" region="rgBotaoVerde" />
</descriptorBase>

<!--++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
 !  BASE DE REGRAS:
 !  definem o comportamento dos elos
 !++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++-->

<ruleBase>
   <rule id="rPt" var="idioma" comparator="eq" value="pt" />
   <rule id="rEn" var="idioma" comparator="eq" value="en" />
</ruleBase>

<!--++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
 !  BASE DE CONECTORES:
 !  definem o comportamento dos elos
 !++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++-->

<connectorBase>
   <importBase alias="connectors" documentURI="exemplo10.conn"/>
</connectorBase>

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

<media type="application/x-ginga-settings" id="nodeSettings">
   <property name="idioma" />
</media>

<!--++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
!  MÍDIAS:
!  define o local dos arquivos de mídia e as associa com
!  seus descritores
!++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++-->
<media type="video/mpeg" id="video1" src="media/rio.mpg" 
       descriptor="dVideo1" />

<media type="video/mpeg" id="video2" src="media/abertura.mpg"
       descriptor="dVideo1" />

<media type="image/gif" id="botaoVermelho" 
       src="media/botao_audio2.gif" descriptor="dBotaoVermelho"/>
<media type="image/gif" id="botaoVerde"
       src="media/botao_audio1.gif" descriptor="dBotaoVerde" />

<!--++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
!  CONTEXTO:
!  contém o vídeo do filme e do switch com os áudios
!++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++-->
<context id="contextoFilme">
   <port id="pVideo3" component="video3" />

   <media type="video/mpeg" id="video3"
          src="media/reciclagem.mpg" descriptor="dVideo3" />

<!--++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
 !  SWITCH: contexto que
 !  contém os áudios, com regra de seleção entre eles
 !  conforme o idioma
 !++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++-->

   <switch id="switchAudioIdioma">
      <bindRule rule="rPt" constituent="audio1" />
      <bindRule rule="rEn" constituent="audio2" />

      <media type="audio/mp3" id="audio1"
             src="media/audio1.mp3" descriptor="dAudio1" />
      <media type="audio/mp3" id="audio2"
             src="media/audio2.mp3" descriptor="dAudio1" />
   </switch>

   <!-- início do video3 deve disparar a reprodução do áudio -->

   <link id="lVideo3AudioStart"
         xconnector="connectors#onBeginStartN">
      <bind component="video3" role="onBegin" />
      <bind component="switchAudioIdioma" role="start" />
   </link>

   <!-- término do video3 deve parar a reprodução do áudio -->

   <link id="lVideo3AudioStop"
         xconnector="connectors#onEndStopN">
      <bind component="video3" role="onEnd" />
      <bind component="switchAudioIdioma" role="stop" />
   </link>

</context>

<!--++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
 !  ELOS
 !  define os elos que regem o sincronismo entre as mídias
 !++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++-->

<!-- início do video1 deve exibir botões -->
<link id="lVideo1Init" xconnector="connectors#onBeginStartN">
   <bind component="video1" role="onBegin" />
   <bind component="botaoVermelho" role="start" />
   <bind component="botaoVerde" role="start" />
</link>

<!-- término do video1 deve dispará-lo novamente
     (deve tocar em loop) -->
<link id="lVideo1Loop" xconnector="connectors#onEndStartN">
   <bind component="video1" role="onEnd" />
   <bind component="video1" role="start" />
</link>

<!-- define idioma inglês quando a tecla vermelha é pressionada -->
<link id="lSelectBotaoVermelhoIdioma"
      xconnector="connectors#onKeySelectionSetNStartNStopNAbortN">
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

<!-- define idioma português quando a tecla verde é pressionada -->
<link id="lSelectBotaoVerdeIdioma"
      xconnector="connectors#onKeySelectionSetNStartNStopNAbortN">
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

<!-- término do video2 deve disparar o video3 em seu contexto -->
<link id="lVideo2StartVideo3"
      xconnector="connectors#onEndStartN">
   <bind component="video2" role="onEnd" />
   <bind component="contextoFilme" interface="pVideo3"
         role="start" />
</link>

</body>

</ncl>
