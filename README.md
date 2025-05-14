![GitHub license](https://badgen.net/github/license/rrbotlab/challenge-conversor-de-moedas-one-g8)
![GitHub license](https://badgen.net/github/commits/rrbotlab/challenge-conversor-de-moedas-one-g8)
![GitHub license](https://badgen.net/github/last-commit/rrbotlab/challenge-conversor-de-moedas-one-g8)

# Challenge Conversor de Moedas
***


![capa](/assets/images/capa.png)

## Alura Latam & Oracle Next Education - ONE - G8
***

Desafio proposto no curso Java da Alura Latam em parceria com a 
Oracle Next Education - ONE. Grupo G8 (2025).


### Objetivo
***

Desenvolver um aplicativo em Java com _interface_ via terminal para câmbio entre moedas, consumindo 
uma API externa para obter as cotações, no caso foi utilizada [ExchangeRate-API](https://www.exchangerate-api.com/)

### Tecnologias utilizadas
***

* Java SDK 21

### Uso
***

[![asciicast](https://asciinema.org/a/DKGUQg7zQaPZEvhcX1jdDOt2C.svg)](https://asciinema.org/a/DKGUQg7zQaPZEvhcX1jdDOt2C)

### Dependências
***

* [Gson >= 2.13.1](https://mvnrepository.com/artifact/com.google.code.gson/gson/2.13.1)

### ExchangeRate-API key
***

É necessário obter uma api key no _site_ [ExchangeRate-API](https://www.exchangerate-api.com/).

A api key pode ser fornecida em tempo de inicialização da aplicação ou setada como variável de 
ambiente:

Windows PowerShell:
```
$env:EXCHANGERATE_API_KEY="SUA-API-KEY"
```

Linux:
```
export EXCHANGERATE_API_KEY="SUA-API-KEY"
```

