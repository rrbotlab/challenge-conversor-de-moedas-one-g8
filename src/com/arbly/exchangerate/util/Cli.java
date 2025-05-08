package com.arbly.exchangerate.util;

import static com.arbly.exchangerate.util.AnsiColors.*;

public class Cli {

    public void clearConsole(){
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    public void showAbout(){

        System.out.println(ANSI_GREEN + """
                
                
                               _                                        _      \s
                              | |                                      | |     \s
                  _____  _____| |__   __ _ _ __   __ _  ___   _ __ __ _| |_ ___\s
                 / _ \\ \\/ / __| '_ \\ / _` | '_ \\ / _` |/ _ \\ | '__/ _` | __/ _ \\
                |  __/>  < (__| | | | (_| | | | | (_| |  __/ | | | (_| | ||  __/
                 \\___/_/\\_\\___|_| |_|\\__,_|_| |_|\\__, |\\___| |_|  \\__,_|\\__\\___|
                                                  __/ |                        \s
                                                 |___/                         \s
                """ + ANSI_BLUE + """
                Challenge Conversor de Moedas
                Alura Latam & Oracle Next Education G8 - ONE
                ©2025 Ricardo G http://github.com/rrbotlab
                Version 1.0.0

                """ + ANSI_RESET);

    }

    public void showCommands(){

        System.out.println(ANSI_GREEN + """
                
                b codigo                    Definir moeda base constante. Ex: b brl
                d codigo                    Definir moeda destino constante. Ex: d usd
                c valor origem destino      Converter valor. Ex: c 150 usd brl => irá converter 150 USD para BRL
                m                           Moedas disponíveis.
                h                           Mostrar esse menu.
                v numero                    Definir valor constante. Ex: v 150
                x                           Sair
                
                """ + ANSI_RESET);

    }

    public void setup(){
        this.clearConsole();
        this.showAbout();
    }
}
