<?xml version="1.0" encoding="ISO-8859-1"?>
<ncl id="exemplo01" 
xmlns="http://www.ncl.org.br/NCL3.0/EDTVProfile">
   <head>
      <regionBase>
         <region id="rgVideo1" left="200" top="168" width="320" height="240" />
      </regionBase>
      <descriptorBase>
         <descriptor id="dVideo1" region="rgVideo1" />
      </descriptorBase>

   </head>

   <body>

      <port id="pInicio" component="video1"/>

      <media type="video/mpeg" id="video1" src="media/video1.mpg" descriptor="d_inexistente"/>

   </body>
</ncl>

