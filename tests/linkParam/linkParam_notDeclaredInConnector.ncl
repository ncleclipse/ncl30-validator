<?xml version="1.0" encoding="ISO-8859-1"?>
<!-- Generated by NCL Eclipse -->
<ncl id="tmp" xmlns="http://www.ncl.org.br/NCL3.0/EDTVProfile">
	<head>
		<connectorBase>
			<causalConnector id="connTmp">
				<connectorParam name="tmp1"/>
				<simpleCondition role="role1"/>
				<simpleAction role="action1" max="1"/>
			</causalConnector>
		</connectorBase>
		<regionBase>
			<region id="rg01" width="100%" height="100%"/>
		</regionBase>
	</head>

	<body>
		<media id="as" src="media/ginga11.png"/>
		<media id="as1" src="media/teste.html"/>
		
		<link xconnector="connTmp">
			<linkParam name="aaa" value="1"/>
			<bind role="role1" component="as">
				<bindParam name="sa" value="sa"/>
			</bind>
			<bind role="action1" component="as"/>
		</link>
	</body>
</ncl>