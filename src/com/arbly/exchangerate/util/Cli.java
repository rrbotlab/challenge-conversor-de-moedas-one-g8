package com.arbly.exchangerate.util;

import static com.arbly.exchangerate.util.AnsiColors.*;

public class Cli {

    public void clearConsole(){
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    public void showAbout(){

        System.out.println(ANSI_GREEN + """
                
                                  __                                           __    \s
                  ___  _  _______/ /_  ____ _____  ____ ____       _________ _/ /____\s
                 / _ \\| |/_/ ___/ __ \\/ __ `/ __ \\/ __ `/ _ \\     / ___/ __ `/ __/ _ \\
                /  __/>  </ /__/ / / / /_/ / / / / /_/ /  __/    / /  / /_/ / /_/  __/
                \\___/_/|_|\\___/_/ /_/\\__,_/_/ /_/\\__, /\\___/    /_/   \\__,_/\\__/\\___/\s
                                                /____/                               \s
                
                """ + ANSI_BLUE + """
                Challenge Conversor de Moedas
                Alura Latam & Oracle Next Education - ONE - G8
                ©2025 Ricardo G http://github.com/rrbotlab/challenge-conversor-de-moedas-one-g8
                Version 1.0.0

                """ + ANSI_RESET + """
                Digite h para ajuda
                """);

    }

    public void showCommands(){

        System.out.println(ANSI_CYAN + """
                
                Uso: VALOR [CODIGO_BASE] [CODIGO_DESTINO]
                
                Digite apenas o valor e será realizado o cambio nas moedas definidas no prompt.
                Ex: [USD] >> [BRL] > 100 
                
                Digite o valor e UM códido de moeda e será realizado o cambio entre essa moeda e a moeda destino
                definida no prompt. O código digitado será salvo como moeda base.
                Ex: [USD] >> [BRL] > 100 eur 
                
                Digite o valor e DOIS códidos de moeda e será realizado o cambio entre essas moedas.
                Os códigos digitado serão salvos como moeda base e destino.
                Ex: [USD] >> [BRL] > 100 EUR JPY
                               
                Comandos
                -------------------------
                h     Mostrar essa ajuda
                m     Moedas disponíveis
                x     Sair
                
                """ + ANSI_RESET);

    }

    public void setup(){
        this.clearConsole();
        this.showAbout();
    }
}
