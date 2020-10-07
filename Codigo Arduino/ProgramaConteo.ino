
//---- Fecha 15/12/2019  3pm-8pm  ----------------
//--------- Pines de Entrada -----------
const int CierreUp = 2;
const int CierreDown = 3;

const int CaminoPunt1 = 4;
const int CaminoPunt2 = 5;
const int CaminoPunt3 = 6;
const int CaminoPunt4 = 7;
const int CaminoPunt5 = 8;
const int CaminoPunt6 = 9;

const int ManijaIzq = 10;
const int ManijaDer = 11;

//--------- Variables ------------------
int EstadoCierreUp = 0;
int EstadoAnteriorCierreUp = 1;

int EstadoCierreDown = 0;
int EstadoAnteriorCierreDown = 1;

int EstadoManijaIzq = 0;
int EstadoAnteriorManijaIzq = 1;

int EstadoManijaDer = 0;
int EstadoAnteriorManijaDer = 1;

int ConteoCamino=0;
//-------- Banderas -------------------
int b1 = 0; 
int b2 = 0;
int b3 =0;
int b4 = 0;

void setup() {
Serial.begin(9600); //9600 caja 1 /38400 caja2
//--------- Configuración de pines como Entradas o Salidas ---------------
pinMode(CierreUp,INPUT_PULLUP);
pinMode(CierreDown,INPUT_PULLUP);

pinMode(CaminoPunt1,INPUT_PULLUP);
pinMode(CaminoPunt2,INPUT_PULLUP);
pinMode(CaminoPunt3,INPUT_PULLUP);
pinMode(CaminoPunt4,INPUT_PULLUP);
pinMode(CaminoPunt5,INPUT_PULLUP);
pinMode(CaminoPunt6,INPUT_PULLUP);

pinMode(ManijaIzq,INPUT_PULLUP);
pinMode(ManijaDer,INPUT_PULLUP);

}

void loop() {
//------------------------ Programa para Cierre (CIERRE:),(RECORRIDO:),(MANIJA:),(MALLA:)-----------------------------------------------------
EstadoCierreUp = digitalRead(CierreUp);
EstadoCierreDown = digitalRead(CierreDown);

if(b1==0){
  if(EstadoCierreUp!=EstadoAnteriorCierreUp){ //Compara el estado del CierreUp con su anterior
    if(EstadoCierreUp==HIGH){
      Serial.print("CIERRE:");
      Serial.print(1); 
      Serial.println("#"); 
      delay(1000);
      b1=1;
    }
  }
EstadoAnteriorCierreUp = EstadoCierreUp;
}

if(b1==1){
  if(EstadoCierreDown!=EstadoAnteriorCierreDown){ //Compara el estado del CierreDown con su anterior
    if(EstadoCierreDown==HIGH){
      Serial.print("CIERRE:");
      Serial.print(0);  
      Serial.println("#"); 
      delay(1000);
      b1=0;
    }
  }
EstadoAnteriorCierreDown = EstadoCierreDown;
}

//------------------------ Programa para Manijas (CIERRE:),(RECORRIDO:),(MANIJA:),(MALLA:)-----------------------------------------------------
EstadoManijaIzq = digitalRead(ManijaIzq);
EstadoManijaDer = digitalRead(ManijaDer);

if(EstadoManijaIzq==LOW&&b3==0){
  Serial.print("MANIJA:");
  Serial.print(1); 
  Serial.println("#"); 
  delay(1000);
  b3=1;
}

if(EstadoManijaIzq==HIGH&&b3==1){
b3=0;
}


if(EstadoManijaDer==LOW&&b4==0){
  Serial.print("MANIJA:");
  Serial.print(0);  
  Serial.println("#"); 
  delay(1000);
  b4=1;
}

if(EstadoManijaDer==HIGH&&b4==1){
b4=0; 
}

//------------------- Programa Camino ------------------------------------------------------------------------

if(digitalRead(CaminoPunt1)==LOW&&b2==0){
  ConteoCamino=1;
  Serial.print("RECORRIDO:");
  Serial.print(ConteoCamino); 
  Serial.println("#"); 
  delay(100);
  b2=1;
}

if(digitalRead(CaminoPunt2)==LOW&&b2==1){
  ConteoCamino=2;
  Serial.print("RECORRIDO:");
  Serial.print(ConteoCamino); 
  Serial.println("#"); 
  delay(100);
  b2=2;
}

if(digitalRead(CaminoPunt3)==LOW&&b2==2){
  ConteoCamino=3;
  Serial.print("RECORRIDO:");
  Serial.print(ConteoCamino); 
  Serial.println("#"); 
  delay(100);
  b2=3;
}

if(digitalRead(CaminoPunt4)==LOW&&b2==3){
  ConteoCamino=4;
  Serial.print("RECORRIDO:");
  Serial.print(ConteoCamino); 
  Serial.println("#"); 
  delay(100);
  b2=4;
}

if(digitalRead(CaminoPunt5)==LOW&&b2==4){
  ConteoCamino=5;
  Serial.print("RECORRIDO:");
  Serial.print(ConteoCamino); 
  Serial.println("#"); 
  delay(100);
  b2=5;
}

if(digitalRead(CaminoPunt6)==LOW&&b2==5){
  ConteoCamino=6;
  Serial.print("RECORRIDO:");
  Serial.print(ConteoCamino); 
  Serial.println("#"); 
  delay(100);
  b2=0;
}








}
