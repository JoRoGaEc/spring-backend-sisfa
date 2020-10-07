package com.soam.springboot.backend.apirest.auth;

public class JwtConfig {
	public static final String CLAVE_SECRETA ="alguna.clave.secreta.12345678";
	
	public static final String RSA_PUBLICA="-----BEGIN PUBLIC KEY-----\r\n" + 
			"MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAt3eghnaB9JlchrM3M2w3\r\n" + 
			"VTQYxnJC+EK890L5w9Szry2rFhieweShSFUkTsq6khpB/FL+t2W6+Dy2tB47LNa1\r\n" + 
			"g2fVewTBraixJbnUQOpSEt2mGmSmUvprzMro8rHznNkvFCdV6nLstC7YOe6kZjW1\r\n" + 
			"fU+VtB6fUA6RUgtJqkfb971wLLOeGyxXnhzZYLR54FcdzxDEveMEspE3agXt4EO8\r\n" + 
			"3pzNmLjn+UwnZNuTcrKxhCZi1X/BQ7VKoZxBIMiCs1B6vVlYJZmMnM7IXy+VZk4t\r\n" + 
			"NzcIMQ3npIdgKmgLF2LMgC1RBbBiUWE1At4t9rGtkyPav+2NE6eP2YqRu29y7jI/\r\n" + 
			"/QIDAQAB\r\n" + 
			"-----END PUBLIC KEY-----";
	
	public static final String RSA_PRIVADA="-----BEGIN RSA PRIVATE KEY-----\r\n" + 
			"MIIEpQIBAAKCAQEAt3eghnaB9JlchrM3M2w3VTQYxnJC+EK890L5w9Szry2rFhie\r\n" + 
			"weShSFUkTsq6khpB/FL+t2W6+Dy2tB47LNa1g2fVewTBraixJbnUQOpSEt2mGmSm\r\n" + 
			"UvprzMro8rHznNkvFCdV6nLstC7YOe6kZjW1fU+VtB6fUA6RUgtJqkfb971wLLOe\r\n" + 
			"GyxXnhzZYLR54FcdzxDEveMEspE3agXt4EO83pzNmLjn+UwnZNuTcrKxhCZi1X/B\r\n" + 
			"Q7VKoZxBIMiCs1B6vVlYJZmMnM7IXy+VZk4tNzcIMQ3npIdgKmgLF2LMgC1RBbBi\r\n" + 
			"UWE1At4t9rGtkyPav+2NE6eP2YqRu29y7jI//QIDAQABAoIBAAYbv4Z6j50bNg9u\r\n" + 
			"DBp0MetuTpzc1Qn6/tukqTgx7sGYXafgrfh6qjwESa8t5MRUh3nlMYHdqzn0m4in\r\n" + 
			"sQSvKV/0pxDveZtUkIFXDJAUvSLPqmYaj8cXe1tYLiVWc2v3dy/Ol3e019iv7pcP\r\n" + 
			"MP4OL7q6WZujoV5C/eejZJJ8zgATiif9X5gomqG5cnELnMfOQHjJXnTQcjbLPO4Y\r\n" + 
			"Yd0qPIGtvhOCmRhvksx93p4eHwnkQVWV9rDCpS/xUTCHOleNW292THiwzlMRmPAz\r\n" + 
			"8Z+cUSsXmW2JoPnN7MDA3re4UVgpYMDOStH0IO8ijOGWp+hdEHFfaSUF3Jegd/Aw\r\n" + 
			"CyH2YykCgYEA2uKDJ9LCfiW52uYzVrEUr2Ai1QJ3vGQzuCbw9IPPbCuosrA6q7Xm\r\n" + 
			"JLGncgdoNVn1ZxPvCiXunM8ocfaga7FCMUeSNrsC+e/GwD+hia98rpHKYLkjfqGs\r\n" + 
			"ztZM0N7KAitUKE5GIhrq8qcZoiLh8wFuEBrwCXHLmBvKVsY0fXKlZZcCgYEA1pOw\r\n" + 
			"ZL0+lOouaeOeuTFADWb4WYlVVvGCXn1a51iwMFPkaZpGyaJaIiKVJPC37feXJU6r\r\n" + 
			"qtRo04gCk5UbURvm/CpAr38WVibSiAeEchKShJTouLLsOiXx944bwj9HtlSHZP/M\r\n" + 
			"YJCwlQpn29NlfiUwattyErdUJvcdb60yHz/FgYsCgYEA1g8HbP60rmF+rRDLzFBJ\r\n" + 
			"hmliJybOqjLkUYfjxXzScVsRFA88kYjGlcQXmXCc/PAJCtF3yFjoTfpZ93nV0Pv9\r\n" + 
			"W+w+lb+2XW+c045nEBvKt35kiMsGZ2T9dxok7W+z5wMwvmouNFRWyQ+aBfJPPjN7\r\n" + 
			"y8xMSzA6h3tGGi6kTrq7/ekCgYEApiwPwaQwz+Afe49z/xUd9oMUtzo8pa04efZ4\r\n" + 
			"LIspPvPpAsMgKMybIq5HdCkbN6WzHogzN4Nxfsdov2QMmJ6UHhqWp5Iy+RrtlbzE\r\n" + 
			"nBa38wbixw6BFHMIVN9vrdmVrqT9iYDzhKDorzM0xS+L/MnOGiLBz66788URvceR\r\n" + 
			"9lNLoAsCgYEAztMdhQH2PFsWyftpcLlBKv1WFzWwrA+G8R9mWficX/l/xXyj5VdS\r\n" + 
			"5CEZnD+e1sgSmc1Ch9RS15h5+GDulOCLFTRMuwOE5ff+LhQ3PPW5VP3TPwLflPj5\r\n" + 
			"cOIdcaZo+qrbXmXG0AujcjQus8TRztcEzpJVmyDblHc+87QtSLfsA2o=\r\n" + 
			"-----END RSA PRIVATE KEY-----";

}
