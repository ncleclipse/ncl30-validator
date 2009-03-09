<?xml version="1.0" encoding="ISO-8859-1"?>

<!--++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
     EXEMPLO 02

     Objetivo: Reproduzir um título e um vídeo numa região da tela, sincronizados.

     Características:

     - sincronismo: simples (de início e término de uma mídia)
     - interação do usuário: nenhuma

     Preparação:

     Para executar este exemplo, é necessário ter as seguintes mídias no subdiretório
     mídia a partir do caminho do arquivo NCL:

     1) arquivo HTML com o título, chamado titulo1.html
     2) arquivo de vídeo chamado video3.mpg

 !++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++-->

<!--++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
 !  CABEÇALHO NCL:
 !  define as URIs dos esquemas da NCL
 !++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++-->

<ncl id="exemplo02" xmlns="http://www.ncl.org.br/NCL3.0/EDTVProfile">

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
            <region id="rgTitulo1" left="25%" top="5%" width="50%" height="15%" />
            <region id="rgVideo1" left="25%" top="25%" width="50%" height="50%" />
         </region>
      </regionBase>

      <!--++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
       !  BASE DE DESCRITORES:
       !  define como as mídias são apresentadas
       !++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++-->

      <descriptorBase>
         <descriptor id="dTitulo1" region="rgTitulo1">
            <descriptorParam name="scroll" value="none" />
         </descriptor>
         <descriptor id="dVideo1" region="rgVideo1">
            <descriptorParam name="soundLevel" value="1" />
         </descriptor>
      </descriptorBase>

      <!--++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
       !  BASE DE CONECTORES:
       !  definem o comportamento dos elos
       !++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++-->

      <connectorBase>
         <importBase alias="connectors" documentURI="exemplo02.conn" />
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

      <port id="pInicio" component="titulo1" />

      <!--++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
       !  MÍDIAS:
       !  define o local dos arquivos de mídia e as associa com seus descritores
       !++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++-->

      <media type="image/png" id="titulo1" src="media/titulo1.png" descriptor="dTitulo1" />
      <media type="video/mpeg" id="video1" src="media/video3.mpg" descriptor="dVideo1" />

      <!--++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
       !  ELOS
       !  define os elos que regem o sincronismo entre as mídias
       !++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++-->

      <!-- video1 deve ser iniciado simultaneamente a titulo1 -->

      <link id="lVideo1Titulo1Start" xconnector="connectors#onBeginStart">
         <bind component="titulo1" role="onBegin" />
         <bind component="video1" role="start" />
      </link>

      <!-- titulo1 deve ser terminado simultaneamente a video1 -->

      <link id="lVideo1Titulo1Stop" xconnector="connectors#onEndStop">
         <bind component="video1" role="onEnd" />
         <bind component="titulo1" role="stop" />
      </link>

   </body>
</ncl>
