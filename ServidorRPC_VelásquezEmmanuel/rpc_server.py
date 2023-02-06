# -*- coding: utf-8 -*-
#IMPORTACION DE LIBRERIAS
import pika, sys, time, Adafruit_DHT, threading, ssl
import RPi.GPIO as GPIO
from datetime import datetime

# Configuracion del tipo de sensor DHT
sensor = Adafruit_DHT.DHT11

#CONFIGURACIONES INICIALES
GPIO.setwarnings(False)
GPIO.setmode(GPIO.BOARD)
GPIO.cleanup()

#FOCOS
GPIO.setup(3, GPIO.OUT)
GPIO.setup(5, GPIO.OUT)
GPIO.setup(7, GPIO.OUT)
GPIO.setup(11, GPIO.OUT)
GPIO.setup(13, GPIO.OUT)
foco1 = GPIO.PWM(3, 100)
foco2 = GPIO.PWM(5, 100)
foco3 = GPIO.PWM(7, 100)
foco4 = GPIO.PWM(11, 100)
foco5 = GPIO.PWM(13, 100)

#SERVO MOTOR
GPIO.setup(21,GPIO.OUT)
p = GPIO.PWM(21,50)
p.start(6.0)

#VENTILADOR
GPIO.setup(40, GPIO.OUT)

#HUMO
GPIO.setup(8, GPIO.IN) #Indicamos que el pin 4 será de entrada
GPIO.setup(37, GPIO.OUT)#Indicamos que el pin 7 será de salida88

#SENSORES DE ALARMA

#io.setmode(io.BOARD)

GPIO.setup(10, GPIO.IN, pull_up_down=GPIO.PUD_UP)
GPIO.setup(36, GPIO.IN, pull_up_down=GPIO.PUD_UP)
GPIO.output(37,False)

gneral = False

def alarmaGeneral(n):
    while True:
        while gneral:
            if(gneral==False):
                break
            else:
                GPIO.output(37, True)
                time.sleep(0.5)
                GPIO.output(37,False)
                time.sleep(0.5)
                

def humo(n):
    if n == "HUMO":
        if GPIO.input(8):
            return str(datetime.now())+"+SE HA DETECTADO GASES+ACTIVAR"
        return ""
    
def intruso(n):
    global gneral
    if n == "ACTIVAR ALARMA":
        for i in range (1, 5):
            GPIO.output(37, True)
            time.sleep(0.5)
            GPIO.output(37,False)
            time.sleep(0.5)
        return "ALARMA ACTIVA"
    if n == "DESACTIVAR ALARMA":
        gneral = False
        GPIO.output(37,False)
        GPIO.output(37,False)
        gneral = False
        return "ALARMA DESACTIVA"
    
def alarmaLadron(n):
    global gneral
    flag1 = False
    flag2 = False
    if n == "ACTIVA ALARMA":
        if gneral == True:
            gneral = True
        if(GPIO.input(10)==1):
            flag1 = True
        if(GPIO.input(36)==1):
            flag2 = True
        if flag1 == True or flag2 == True:
            for i in range (1, 5):
                GPIO.output(37, True)
                time.sleep(0.5)
                GPIO.output(37,False)
                time.sleep(0.5)
            gneral = True
            if(flag1 == True):
                return "Puerta+"+str(datetime.now())+"+ALARMA ACTIVADA"
            if(flag2 == True):
                return "Ventana+"+str(datetime.now())+"+ALARMA ACTIVADA"        
        else:
            return "ALARMA NO ACTIVADA"
    
    
def alarma(n):
    flag1 = False
    flag2 = False
    if n == "ACTIVAR ALARMA":
        if GPIO.input(10)==0:
            flag1 = True
        if GPIO.input(36)==0:
            flag2 = True
        if flag1==True and flag2==True:
            p.start(6.0)
            return "ALARMA ACTIVADA LADRON"
        else:
            flag1 = False
            flag2 = False
            return "ALARMA NO ACTIVADA LADRON"
        
def garage(n):
    if n == "ABRIR GARAGE":
        p.ChangeDutyCycle(10.5)
        return "GARAGE ABIERTO"
    if n == "CERRAR GARAGE":
        p.ChangeDutyCycle(6.0)
        return "CERRAR ABIERTO"

def temperatura(n, tipoT):
    now = datetime.now()
    humedad, temperatura = Adafruit_DHT.read_retry(sensor, 23)
    if n == "TEMPERATURA" and tipoT=='Celsius':
        return str(datetime.now())+"+"+str('Temperatura:{0:0.1f}°+Humedad:{1:0.1f}%'.format(temperatura, humedad))
    if n == "TEMPERATURA" and tipoT=='Fahrenheit':
        temp = (temperatura*1.8)+32
        return str(datetime.now())+"+"+str('Temperatura:{0:0.1f}°+Humedad:{1:0.1f}%'.format(temp, humedad))
    if n == "TEMPERATURA" and tipoT=='Kelvin':
        temp = temperatura + 273.15
        return str(datetime.now())+"+"+str('Temperatura:{0:0.1f}°+Humedad:{1:0.1f}%'.format(temp, humedad))
    if n == "TEMPERATURA" and tipoT=='Rankine':
        temp = ((9*temperatura)/5) + 491.67
        return str(datetime.now())+"+"+str('Temperatura:{0:0.1f}°+Humedad:{1:0.1f}%'.format(temp, humedad))
    
def regulacion(n, r):
    if n == "FOCO1":
        foco1.start(r)
        return "FOCO 1 REGULADO AL"+str(r)
    if n == "FOCO2":
        foco2.start(r)
        return "FOCO 2 REGULADO AL"+str(r)
    if n == "FOCO3":
        foco3.start(r)
        return "FOCO 3 REGULADO AL"+str(r)
    if n == "FOCO4":
        foco4.start(r)
        return "FOCO 4 REGULADO AL"+str(r)
    if n == "FOCO5":
        foco5.start(r)
        return "FOCO 5 REGULADO AL"+str(r)
    
def encendidoFocos(n):
    if n == "ENCENDER TODOS LOS FOCOS":
        foco1.start(100)
        foco2.start(100)
        foco3.start(100)
        foco4.start(100)
        foco5.start(100)
        return "SE ENCENDIERON TODOS LOS FOCOS"
    if n == "APAGAR TODOS LOS FOCOS":
        foco1.start(0)
        foco2.start(0)
        foco3.start(0)
        foco4.start(0)
        foco5.start(0)
        return "SE APAGARON TODOS LOS FOCOS"
    if n == "ENCENDER FOCO HABITACIÓN 1":
        foco1.start(100)
        return "FOCO 1 ENCENDIDO"
    if n == "ENCENDER FOCO HABITACIÓN 2":
        foco2.start(100)
        return "FOCO 2 ENCENDIDO"
    if n == "ENCENDER FOCO HABITACIÓN 3":
        foco3.start(100)
        return "FOCO 3 ENCENDIDO"
    if n == "ENCENDER FOCO HABITACIÓN 4":
        foco4.start(100)
        return "FOCO 4 ENCENDIDO"
    if n == "ENCENDER FOCO HABITACIÓN 5":
        foco5.start(100)
        return "FOCO 5 ENCENDIDO"
    if n == "APAGAR FOCO HABITACIÓN 1":
        foco1.start(0)
        return "FOCO 1 APAGADO"
    if n == "APAGAR FOCO HABITACIÓN 2":
        foco2.start(0)
        return "FOCO 2 APAGADO"
    if n == "APAGAR FOCO HABITACIÓN 3":
        foco3.start(0)
        return "FOCO 3 APAGADO"
    if n == "APAGAR FOCO HABITACIÓN 4":
        foco4.start(0)
        return "FOCO 4 APAGADO"
    if n == "APAGAR FOCO HABITACIÓN 5":
        foco5.start(0)
        return "FOCO 5 APAGADO"

def repuestas(ch, method, props, body):
    n = str(body)
    x = n.split('-')
    if(x[0]=='1'):
        n = str(x[1])
        response = encendidoFocos(n)
    if(x[0]=='2'):
        n = str(x[1])
        response = garage(n)
    if(x[0]=='3'):
        n = str(x[1])
        response = alarmaLadron(n)
    if(x[0]=='4'):
        n = str(x[1])
        tipoT = str(x[2])
        response = temperatura(n, tipoT)
    if(x[0]=='5'):
        n = str(x[1])
        response = humo(n)
    if(x[0]=='6'):
        n = str(x[1])
        response = alarma(n)
    if(x[0]=='7'):
        n = str(x[1])
        r = int(x[2])
        response = regulacion(n, r)
    if(x[0]=='8'):
        n = str(x[1])
        response = intruso(n)
        
    ch.basic_publish(exchange='',
                     routing_key=props.reply_to,
                     properties=pika.BasicProperties(correlation_id = \
                                                         props.correlation_id),
                     body=str(response))
    ch.basic_ack(delivery_tag=method.delivery_tag)
    
def proceso(usr, clave, ip, puerto, vh, q):
    contexto = ssl.create_default_context(cafile="/home/pi/Desktop/Proyecto/certsProyecto/testca/ca_certificate.pem")
    contexto.load_cert_chain("/home/pi/Desktop/Proyecto/certsProyecto/client/client_certificate.pem","/home/pi/Desktop/Proyecto/certsProyecto/client/private_key.pem")
    ssl_opciones=pika.SSLOptions(contexto, "raspberrypi")
    
    credenciales = pika.PlainCredentials(usr,clave)
    parametros = pika.ConnectionParameters(ip,puerto,vh,credenciales, ssl_options = ssl_opciones)
    connection = pika.BlockingConnection(parametros)
    channel = connection.channel()
    channel.queue_declare(queue=q)
    channel.basic_qos(prefetch_count=1)
    channel.basic_consume(queue=q, on_message_callback=repuestas)
    print(" [x] Esperando repuestas de '"+q+"'")
    channel.start_consuming()
    
t1 = threading.Thread(target=proceso, args=('paralelo', 'uaz2019', '192.168.1.10', 5671, 'computacion', 'focos',))
t2 = threading.Thread(target=proceso, args=('paralelo', 'uaz2019', '192.168.1.10', 5671, 'computacion', 'garage',))
t3 = threading.Thread(target=proceso, args=('paralelo', 'uaz2019', '192.168.1.10', 5671, 'computacion', 'alarmaLadron',))
t4 = threading.Thread(target=proceso, args=('paralelo', 'uaz2019', '192.168.1.10', 5671, 'computacion', 'temperatura',))
t5 = threading.Thread(target=proceso, args=('paralelo', 'uaz2019', '192.168.1.10', 5671, 'computacion', 'humo',))
t6 = threading.Thread(target=proceso, args=('paralelo', 'uaz2019', '192.168.1.10', 5671, 'computacion', 'alarma',))
t7 = threading.Thread(target=proceso, args=('paralelo', 'uaz2019', '192.168.1.10', 5671, 'computacion', 'regulacion',))
t8 = threading.Thread(target=proceso, args=('paralelo', 'uaz2019', '192.168.1.10', 5671, 'computacion', 'intruso',))
t9 = threading.Thread(target=alarmaGeneral, args=("f",))

t1.start()
t2.start()
t3.start()
t4.start()
t5.start()
t6.start()
t7.start()
t8.start()
t9.start()