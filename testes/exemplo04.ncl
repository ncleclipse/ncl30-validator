<?xml version="1.0" encoding="ISO-8859-1"?>

<!--++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
 !     EXEMPLO 04
 !
 !     Objetivo: Reproduzir um vídeo numa região da tela,
 !     sincronizando com trechos
 !     de legenda.
 !
 !     Características:
 !
 !     - sincronismo: simples (de início e término de mídias)
 !     - interação do usuário: nenhuma
 !
 !     Preparação:
 !
 !     Para executar este exemplo, é necessário ter as seguintes 
 !     mídias no subdiretório
 !     mídia a partir do caminho do arquivo NCL:
 !
 !     1) arquivo de vídeo chamado paubrasil.mpg
 !     2) 3 arquivos HTML com a legenda, chamados legenda01.html, 
 !     legenda02.html e legenda03.html
 !++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++-->

<!--++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
 !  CABEÇALHO NCL:
 !  define as URIs dos esquemas da NCL
 !++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++-->

<ncl id="exemplo04" 
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
   <region id="rgVideo1" left="20%" top="22%" 
           width="31%" height="32%" />
   <region id="rgLegenda" left="20%" top="58%" 
           width="23%" height="7%" />
</regionBase>

<!--++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
 !  BASE DE DESCRITORES:
 !  define como as mídias são apresentadas
 !++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++-->

<descriptorBase>
   <descriptor id="dVideo1" region="rgVideo1" />
   <descriptor id="dLegenda" region="rgLegenda">
      <descriptorParam name="border" value="none" />
   </descriptor>
</descriptorBase>

<!--++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
 !  BASE DE CONECTORES:
 !  definem o comportamento dos elos
 !++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++-->

<connectorBase>
   <importBase alias="connectors" documentURI="exemplo04.conn"/>
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

<media type="video/mpeg" id="video1" src="media/rio.mpg"
       descriptor="dVideo1">
   <!-- âncoras no vídeo que devem ser sincronizadas 
        com a legenda -->
   <area id="aVideoLegenda01" begin="1s" end="3s"/>
   <area id="aVideoLegenda02" begin="16s" end="19s"/>
   <area id="aVideoLegenda03" begin="19s" end="21s"/>
</media>

<media type="text/html" id="legenda01" src="media/legenda01.html"
       descriptor="dLegenda" />
<media type="text/html" id="legenda02" src="media/legenda02.html" 
       descriptor="dLegenda" />
<media type="text/html" id="legenda03" src="media/legenda03.html" 
       descriptor="dLegenda" />

<!--++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
 !  ELOS
 !  define os elos que regem o sincronismo entre as mídias
 !++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++-->

<!-- início da legenda 01 -->

<link id="lLegenda01_start" xconnector="connectors#onBeginStart">
   <bind component="video1" interface="aVideoLegenda01" 
         role="onBegin" />
   <bind component="legenda01" role="start" />
</link>

<!-- término da legenda 01 -->

<link id="lLegenda01_stop" xconnector="connectors#onEndStop">
   <bind component="video1" interface="aVideoLegenda01" 
         role="onEnd" />
   <bind component="legenda01" role="stop" />
</link>

<!-- início da legenda 02 -->

<link id="lLegenda02_start" xconnector="connectors#onBeginStart">
   <bind component="video1" interface="aVideoLegenda02" 
         role="onBegin" />
   <bind component="legenda02" role="start" />
</link>

<!-- término da legenda 02 -->

<link id="lLegenda02_stop" xconnector="connectors#onEndStop">
   <bind component="video1" interface="aVideoLegenda02" 
         role="onEnd" />
   <bind component="legenda02" role="stop" />
</link>

<!-- início da legenda 03 -->

<link id="lLegenda03_start" xconnector="connectors#onBeginStart">
   <bind component="video1" interface="aVideoLegenda03" 
         role="onBegin" />
   <bind component="legenda03" role="start" />
</link>

<!-- término da legenda 03 -->

<link id="lLegenda03_stop" xconnector="connectors#onEndStop">
   <bind component="video1" interface="aVideoLegenda03" 
         role="onEnd" />
   <bind component="legenda03" role="stop" />
</link>

</body>

</ncl>
