import pika, sys, ssl
mensaje = "Probando 1,2,3..."
if len(sys.argv) > 1:
	mensaje = sys.argv[1]

#para saber si realmente se esta conectando el cliente
# version de ssl a usar
contexto = ssl.create_default_context(cafile="C:/certs/autcert/cacert.pem")
contexto.load_cert_chain("C:/certs/cliente/cert.pem","C:/certs/cliente/privatekey.pem")
# se crea un objeto para usar opciones ssl a usar y la direccion ip
ssl_opciones=pika.SSLOptions(contexto, "DESKTOP-70BROLM")
#conexion parametros se le dan opciones del puerto
conexion = pika.BlockingConnection(pika.ConnectionParameters(port = 5671, ssl_options = ssl_opciones))
canal = conexion.channel()
canal.queue_declare(queue="miprimerqueue")
canal.basic_publish(exchange='', routing_key='miprimerqueue', body=mensaje)
print("Acabo de enviar el mensaje: '%s'" % mensaje)
conexion.close()

"""
SI NO COINCIDE 
echo %COMPUTERNAME%
en caso de que esten en dos computadoras diferentes se deben crear los certificados en las respectivas maquinas
en servidor y cliente
cert.pem
cert.pem 
borrar esos archivos


openssl.cnf

[ client_ca_extensions ]
basicConstraints = CA:false
keyUsage = digitalSignature, keyEncipherment
extendedKeyUsage = 1.3.6.1.5.5.7.3.2

[ server_ca_extensions ]
basicConstraints = CA:false
keyUsage = keyEncipherment, digitalSignature
extendedKeyUsage = 1.3.6.1.5.5.7.3.1

"""