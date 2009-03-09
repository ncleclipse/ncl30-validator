<?xml version="1.0" encoding="ISO-8859-1"?>

<!--++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
 !   EXEMPLO 14
 !
 !   Objetivo: 
 !
 !   Características:
 !
 !   - sincronismo: simples (de início e término de mídias)
 !   - interação do usuário: seleção da repetição ou não de 
 !                           um nó
 !
 !   Preparação:
 !
 !   Para executar este exemplo, é necessário ter as seguintes 
 !   mídias no subdiretório media a partir do caminho do 
 !   arquivo NCL:
 !
 !   1) arquivo de vídeo chamado abertura.mpg
 !   2) arquivo de vídeo chamado passaro.mpg
 !   3) arquivo de vídeo chamado reciclagem.mpg
 !   3) arquivos de imagem dos botões: passaro.gif;
 !      reciclagem.png; e repetir.gif
 !
 !++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++-->

<!--++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
 !  CABEÇALHO NCL:
 !  define as URIs dos esquemas da NCL
 !++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++-->

<ncl id="exemplo14" 
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
   <region id="rgTV" height="100%" width="100%">
      <region id="rgVideo1" height="50%" width="50%"
              top="25%" left="25%"/>
      <region id="rgBotaoVermelho" left="25%" bottom="75%" 
              width="10%" height="10%" zIndex="1"/>
      <region id="rgBotaoVerde" left="45%" bottom="75%" 
              width="10%" height="10%" zIndex="1"/>
      <region id="rgBotaoAzul" left="65%" bottom="75%" 
              width="10%" height="10%" zIndex="1"/>
   </region>
</regionBase>

<!--++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
 !  BASE DE DESCRITORES:
 !  define como as mídias são apresentadas
 !++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++-->

<descriptorBase>
   <descriptor id="dVideo1" region="rgVideo1" />

   <descriptor id="dBotaoVermelho" region="rgBotaoVermelho" />
   <descriptor id="dBotaoVerde" region="rgBotaoVerde" />
   <descriptor id="dBotaoAzul" region="rgBotaoAzul" />
</descriptorBase>

<!--++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
 !  BASE DE CONECTORES:
 !  definem o comportamento dos elos
 !++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++-->

<connectorBase>
   <importBase alias="connBase" 
               documentURI="composerConnectorBase.conn"/>
               
   <causalConnector id="onKeySelectionAbortNStopNStartN">
      <connectorParam name="keyCode"/>
      <simpleCondition role="onSelection" key="$keyCode"/>
      <compoundAction operator="seq">
         <simpleAction role="abort"
                       max="unbounded" qualifier="par"/>
         <simpleAction role="stop"
                       max="unbounded" qualifier="par"/>
         <simpleAction role="start"
                       max="unbounded" qualifier="par"/>
      </compoundAction>
   </causalConnector>
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
 !  define o local dos arquivos de mídia e as associa com 
 !  seus descritores
 !++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++-->
<media type="video/mpeg" id="video1" src="media/abertura.mpg" 
       descriptor="dVideo1">
   <!-- propriedade que controla o loop da abertura -->
   <property name="reinicia"/>
</media>

<media type="video/mpeg" id="final1" 
       src="media/passaro.mpg" descriptor="dVideo1" />
<media type="video/mpeg" id="final2" 
       src="media/reciclagem.mpg" descriptor="dVideo1" />

<media type="image/gif" id="botaoVermelho" 
       src="media/botao_passaro.gif" descriptor="dBotaoVermelho"/>
<media type="image/png" id="botaoVerde" 
       src="media/botao_reciclagem.png" descriptor="dBotaoVerde" />
<media type="image/gif" id="botaoAzul" 
       src="media/repetir.gif" descriptor="dBotaoAzul" />

<!--++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
 !  ELOS
 !  define os elos que regem o sincronismo entre as mídias
 !++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++-->

<!-- como default, o valor de repeat é false -->
<link id="lDefaultRepeat" xconnector="connBase#onBeginSet">
   <bind component="video1" role="onBegin"/>
   <bind component="video1" interface="reinicia"
         role="set">
      <bindParam name="var" value="nao"/>
   </bind>
</link>

<!-- início do video1 deve exibir botões -->
<link id="lIniciaBotoes" xconnector="connBase#onBeginStartN">
   <bind component="video1" role="onBegin" />
   <bind component="botaoVermelho" role="start" />
   <bind component="botaoVerde" role="start" />
   <bind component="botaoAzul" role="start" />
</link>

<!-- término do video1 deve dispará-lo novamente
     (deve tocar em loop) caso reinicia=sim -->
<link id="lVideo1Loop" 
    xconnector="connBase#onEndAttNodeTestStartN">
   <bind role="onEnd" component="video1"/>
   <bind role="attNodeTest" 
         component="video1" interface="reinicia">
      <bindParam name="value" value="sim"/>
   </bind>
   <bind role="start" component="video1"/>
</link>

<!-- término do video1 deve terminar o programa
     caso reinicia=nao -->
<link id="lVideo1TerminaBotoes"
      xconnector="connBase#onEndAttNodeTestStopN">
   <bind role="onEnd" component="video1"/>
   <bind role="attNodeTest" component="video1" interface="reinicia">
      <bindParam name="value" value="nao"/>
   </bind>
   <bind role="stop" component="botaoVermelho"/>
   <bind role="stop" component="botaoVerde"/>
   <bind role="stop" component="botaoAzul"/>
</link>

<!-- pressionar o botao vermelho salta para o final1 -->
<link id="lBotaoVermelhoFinal1"
 xconnector="connBase#onKeySelectionAbortNStopNStartN">
   <bind role="onSelection" component="botaoVermelho">
      <bindParam name="keyCode" value="RED"/>
   </bind>
   <bind role="abort" component="video1"/>
   <bind role="stop" component="botaoVermelho"/>
   <bind role="stop" component="botaoVerde"/>
   <bind role="stop" component="botaoAzul"/>
   <bind role="start" component="final1"/>
</link>

<!-- pressionar o botao vermelho salta para o final2 -->
<link id="lBotaoVerdeFinal2"
 xconnector="connBase#onKeySelectionAbortNStopNStartN">
   <bind role="onSelection" component="botaoVermelho">
      <bindParam name="keyCode" value="GREEN"/>
   </bind>
   <bind role="abort" component="video1"/>
   <bind role="stop" component="botaoVermelho"/>
   <bind role="stop" component="botaoVerde"/>
   <bind role="stop" component="botaoAzul"/>
   <bind role="start" component="final2"/>
</link>

<!-- pressionar o botao azul atribui faz desaparecer
     o botao e reinicia o loop da abertura -->
<link id="lBotaoAzulReinicia"
 xconnector="connBase#onKeySelectionStopNSetN">
   <bind role="onSelection" component="botaoAzul">
      <bindParam name="keyCode" value="BLUE"/>
   </bind>
   <bind role="set" component="video1" interface="reinicia">
      <bindParam name="var" value="sim"/>
   </bind>
   <bind role="stop" component="botaoAzul"/>
</link>

</body>

</ncl>
