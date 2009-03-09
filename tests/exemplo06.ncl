<?xml version="1.0" encoding="ISO-8859-1"?>

<!--++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
 ! EXEMPLO 06
 !
 ! Objetivo: Reproduzir um vídeo em loop, até que o usuário
 ! pressione a tecla verde para mover para o próximo
 ! vídeo.
 !
 ! Características:
 !
 ! - sincronismo: simples (de início e término de mídias)
 ! - interação do usuário: interrupção do vídeo para disparar
 !                         outro
 !
 ! Preparação:
 !
 ! Para executar este exemplo, é necessário ter as seguintes 
 ! mídias no subdiretório media a partir do caminho do 
 ! arquivo NCL:
 !
 ! 1) arquivo de vídeo chamado abertura.mpg, com uma introdução 
 !    em loop
 ! 2) arquivo de vídeo chamado paubrasil.mpg
 ! 3) arquivo de imagem chamado botao_verde.gif, indicando a 
 !    ação do botão verde
 !
 !++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++-->

 <!--++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
 ! CABEÇALHO NCL:
 ! define as URIs dos esquemas da NCL
 !++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++-->

<ncl id="exemplo06" 
     xmlns="http://www.ncl.org.br/NCL3.0/EDTVProfile">

<!--++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
 ! CABEÇALHO DO PROGRAMA
 !++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++-->

<head>

<!--++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
 ! BASE DE REGIÕES:
 ! define as regiões na tela onde as mídias são apresentadas
 !++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++-->

<regionBase>
   <region id="rgTV" width="100%" height="100%">
      <region id="rgVideo1" width="100%" height="100%" zIndex="0"/>
      <region id="rgBotaoVerde" left="3%" top="87%" width="87" 
              height="70" zIndex="1"/>
   </region>
</regionBase>

<!--++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
 ! BASE DE DESCRITORES:
 ! define como as mídias são apresentadas
 !++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++-->

<descriptorBase>
   <descriptor id="dVideo1" region="rgVideo1" />
   <descriptor id="dBotaoVerde" region="rgBotaoVerde" />
</descriptorBase>

<!--++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
 ! BASE DE CONECTORES:
 ! definem o comportamento dos elos
 !++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++-->

<connectorBase>
   <importBase alias="connectors" documentURI="exemplo06.conn"/>
</connectorBase>

</head>

<!--++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
 ! CORPO DO PROGRAMA:
 ! define as mídias e estrutura do programa
 !++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++-->

<body>

<!--++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
 ! PONTO DE ENTRADA:
 ! indica o componente onde o programa inicia
 !++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++-->

<port id="pInicio" component="video1" />

<!--++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
 ! MÍDIAS:
 ! define o local dos arquivos de mídia e as associa com seus 
 ! descritores
 !++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++-->

<media type="video/mpeg" id="video1" src="media/abertura.mpg" 
       descriptor="dVideo1" />

<media type="video/mpeg" id="video2" src="media/paubrasil.mpg" 
       descriptor="dVideo1" />

<media type="image/png" id="botaoVerde" 
       src="media/botao_verde.png" descriptor="dBotaoVerde" />

<!--++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
 ! ELOS
 ! define os elos que regem o sincronismo entre as mídias
 !++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++-->

<!-- início do video1 deve exibir botões -->
<link id="lVideo1Init" xconnector="connectors#onBeginStart">
   <bind component="video1" role="onBegin" />
   <bind component="botaoVerde" role="start" />
</link>

<!-- término do video1 deve dispará-lo novamente 
     (deve tocar em loop) -->
<link id="lVideo1Loop" xconnector="connectors#onEndStart">
   <bind component="video1" role="onEnd" />
   <bind component="video1" role="start" />
</link>

<!-- cancela a exibição do video1 e dispara a exibição 
     do video2 quando a tecla verde é pressionada -->
<link id="lSelectBotaoVerde" 
      xconnector="connectors#onKeySelectionStartNStopNAbortN">
   <bind component="botaoVerde" role="onSelection">
      <bindParam name="keyCode" value="GREEN" />
   </bind>
   <bind component="botaoVerde" role="stop" />
   <bind component="video1" role="abort" />
   <bind component="video2" role="start" />
</link>

</body>
</ncl>
 