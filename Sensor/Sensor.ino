#include <Ultrasonic.h>
float x=3.1415*(5.5*5.5)*14, y, total = 0, percent;
float cmMsec;
long microsec;
#define pino_trigger 8
#define pino_echo 9

Ultrasonic ultrasonic(pino_trigger, pino_echo);
void setup(){
Serial.begin(9600);
Serial.println("Leitura do sensor...");
Serial.println();
}
void loop()
{
microsec = ultrasonic.timing();
cmMsec = ultrasonic.convert(microsec, Ultrasonic::CM);
y = 3.1415 * (5.5*5.5) * (16 - cmMsec);
percent = (y*100)/x;
total = 17.59*percent/100;
if(total < 0)
  total = 0;
Serial.println(total);
delay(2000);
}
