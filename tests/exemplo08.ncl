<?xml version="1.0" encoding="ISO-8859-1"?>

<!--++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
 !   EXEMPLO 08
 !
 !   Objetivo: Reproduzir um vídeo e permitir que o usuário 
 !             alterne entre dois vídeos, numa mesma região da 
 !             tela, através do pressionamento das teclas
 !             vermelha e verde do controle remoto.
 !
 !   Características:
 !
 !   - sincronismo: ponto de alternância entre os vídeos
 !   - interação do usuário: seleção do vídeo a ser exibido
 !
 !   Preparação:
 !
 !   Para executar este exemplo, é necessário ter as seguintes
 !   mídias no subdiretório media a partir do caminho do 
 !   arquivo NCL:
 !
 !   1) arquivo de vídeo chamado abertura.mpg
 !   2) arquivo de vídeo chamado reciclagem.mpg
 !   3) arquivo de vídeo chamado passaro.mpg
 !   4) arquivo de imagem chamado botao_passaro.gif, indicando 
 !      a ação do botão vermelho
 !   5) arquivo de imagem chamado botao_reciclagem.png,
 !      indicando a ação do botão verde
 !
 !++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++-->

<!--++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
 !  CABEÇALHO NCL:
 !  define as URIs dos esquemas da NCL
 !++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++-->

<ncl id="exemplo08" 
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
   <region id="rgVideo1" width="50%" height="50%" 
           top="25%" left="25%" zIndex="0"/>
   <region id="rgBotaoVermelho" left="3%" top="87%" 
           width="87" height="70" zIndex="2" />
   <region id="rgBotaoVerde" right="3%" top="87%" 
           width="87" height="70" zIndex="2" />
</regionBase>

<!--++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
 !  BASE DE DESCRITORES:
 !  define como as mídias são apresentadas
 !++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++-->

<descriptorBase>
   <descriptor id="dVideo1" region="rgVideo1" />

   <descriptor id="dVideo2" region="rgVideo1">
      <descriptorParam name="visible" value="false" />
      <descriptorParam name="soundLevel" value="0"/>
   </descriptor>

   <descriptor id="dBotaoVermelho" region="rgBotaoVermelho" />

   <descriptor id="dBotaoVerde" region="rgBotaoVerde" />
</descriptorBase>

<!--++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
 !  BASE DE CONECTORES:
 !  definem o comportamento dos elos
 !++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++-->

<connectorBase>
   <importBase alias="connectors" documentURI="exemplo08.conn"/>
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

<!--++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
 !  MÍDIAS:
 !  define o local dos arquivos de mídia e as associa com seus 
 !  descritores
 !++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++-->

<media type="video/mpeg" id="video1" src="media/abertura.mpg"
       descriptor="dVideo1"> 
   <property name="visible" />
   <property name="soundLevel" />
</media>

<media type="video/mpeg" id="video3" src="media/reiclagem.mpg"
       descriptor="dVideo2">
   <property name="visible" />
   <property name="soundLevel" />
</media>

<media type="video/mpeg" id="video2" src="media/passaro.mpg"
       descriptor="dVideo2">
   <property name="visible" />
   <property name="soundLevel" />
</media>

<media type="image/png" id="botaoVermelho" 
       src="media/botao_passaro.gif"
       descriptor="dBotaoVermelho" />
<media type="image/png" id="botaoVerde" 
       src="media/botao_reciclagem.png" 
       descriptor="dBotaoVerde" />

<!--++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
!  ELOS
!  define os elos que regem o sincronismo entre as mídias
!++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++-->

<!-- início do video1 deve disparar a exibição do video2, 
     video3 e das imagens dos botões -->

<link id="lVideo_Botoes_start"
      xconnector="connectors#onBeginStartN">
   <bind component="video1" role="onBegin" />
   <bind component="video2" role="start" />
   <bind component="video3" role="start" />
   <bind component="botaoVermelho" role="start" />
   <bind component="botaoVerde" role="start" />
</link>


<!-- término do video1 deve terminar a exibição das imagens 
     dos botões -->

<link id="lVideo_Botoes_stop"
      xconnector="connectors#onEndStopN">
   <bind component="video1" role="onEnd" />
   <bind component="video2" role="stop" />
   <bind component="video3" role="stop" />
   <bind component="botaoVerde" role="stop" />
   <bind component="botaoVermelho" role="stop" />
</link>


<!-- alterna para vídeo 2 caso a tecla vermelha seja acionada -->

<link id="lSelecionaVideo2"
      xconnector="connectors#onKeySelectionSetN">
   <bind component="botaoVermelho" role="onSelection">
      <bindParam name="keyCode" value="RED"/>
   </bind>
   <bind component="video1" interface="visible" role="set">
 	  <bindParam name="valueSet" value="false"/>
   </bind>
   <bind component="video1" interface="soundLevel" role="set">
 	  <bindParam name="valueSet" value="0"/>
   </bind>
   <bind component="video3" interface="visible" role="set">
 	  <bindParam name="valueSet" value="false"/>
   </bind>
   <bind component="video3" interface="soundLevel" role="set">
 	  <bindParam name="valueSet" value="0"/>
   </bind>
   <bind component="video2" interface="visible" role="set">
      <bindParam name="valueSet" value="true"/>
   </bind>
   <bind component="video2" interface="soundLevel" role="set">
 	  <bindParam name="valueSet" value="1"/>
   </bind>
</link>

<!-- alterna para vídeo 3 caso a tecla verde seja acionada -->

<link id="lSelecionaVideo1"
      xconnector="connectors#onKeySelectionSetN">
   <bind component="botaoVerde" role="onSelection">
      <bindParam name="keyCode" value="GREEN"/>
   </bind>
   <bind component="video1" interface="visible" role="set">
 	  <bindParam name="valueSet" value="false"/>
   </bind>
   <bind component="video1" interface="soundLevel" role="set">
 	  <bindParam name="valueSet" value="0"/>
   </bind>
   <bind component="video2" interface="visible" role="set">
 	  <bindParam name="valueSet" value="false"/>
   </bind>
   <bind component="video2" interface="soundLevel" role="set">
 	  <bindParam name="valueSet" value="0"/>
   </bind>
   <bind component="video3" interface="visible" role="set">
      <bindParam name="valueSet" value="true"/>
   </bind>
   <bind component="video3" interface="soundLevel" role="set">
 	  <bindParam name="valueSet" value="1"/>
   </bind>
</link>

</body>

</ncl>
