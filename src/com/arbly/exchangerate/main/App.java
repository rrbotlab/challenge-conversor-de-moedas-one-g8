package com.arbly.exchangerate.main;

import com.arbly.exchangerate.util.AnsiColors;
import com.arbly.exchangerate.util.Cli;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;
import java.util.Scanner;

import static com.arbly.exchangerate.util.AnsiColors.*;

public class App {
    private SupportedCodes supportedCodes;

    public void run() throws IOException, InterruptedException {
        Scanner sc = new Scanner(System.in);
        ExchangeRateApi api = new ExchangeRateApi();
        Cli con = new Cli();

        con.clearConsole();
        System.out.println("Inicializando API...");
        this.supportedCodes =  api.supportedCodes();
        if (this.supportedCodes == null)
            System.exit(0);

        con.setup();

        while (true){
            String input = "";

            while (input.isEmpty()) {
                System.out.print("["+api.getBaseCurrency()+"] → ["+api.getTargetCurrency()+"] > ");
                input = sc.nextLine();
            }

            if (input.equalsIgnoreCase("x"))
                break;

            var args = input.split(" ");

            for (int i = 0; i < args.length; i++) {
                args[i] = args[i].toUpperCase();
            }

            switch (args[0]){
                case "b":
                case "B":

                case "h":
                case "H":
                case "help":
                    con.showCommands();
                    break;

                case "c":
                case "C":
                    if (parseConvert(args)){
                        var result = api.exchange(parseAmount(args[1]), args[2], args[3]);
                        if(result != null)
                            System.out.println(ANSI_GREEN + args[1] + " " + args[2] + " = " + result + " " + args[3] + ANSI_RESET);
                        else
                            System.out.println("Ocorreu um erro no servidor remoto.");
                    }
//                    System.out.println(api.updateRates("USD"));
                    break;

                case "m":
                case "M":
                    printSupportedCodes();
                    break;

//                case "x":
//                    break;

                default:
                    System.out.println("comando inválido. Digite h para ajuda.");
            }
        }
        sc.close();
    }

    private boolean parseConvert(String[] args){
        var base = this.supportedCodes.supportedCodesMap().get(args[2]);
        var target = this.supportedCodes.supportedCodesMap().get(args[3]);

        boolean b = args.length != 4 || parseAmount(args[1]) == 0;
        if (b){
            System.out.println("Uso: c valor moeda_base moeda_destino");
            System.out.println("Ex.: c 150 usd brl => irá converter 150 USD para BRL");
            return false;
        }

        if ( base == null ) {
            System.out.println("Código inválido: " + args[2]);
            return false;
        }
        else if (target == null) {
            System.out.println("Código inválido: " + args[3]);
            return false;
        }

        return true;
    }

    private double parseAmount(String s){
        try{
            return Double.parseDouble(s);
        }catch (NumberFormatException e){
            return 0.0;
        }
    }

    private void printSupportedCodes() throws IOException {
        Scanner sc = new Scanner(System.in);

        var i = 0;
//        String[] codes;
        //final Object[][] table = new String[this.supportedCodes.supportedCodesMap().size()][];
        for (Map.Entry<String, String> entry : this.supportedCodes.supportedCodesMap().entrySet()) {
            System.out.format("| %s | %-40s |%n", entry.getKey(), entry.getValue());
            i++;
            if (i == 25) {
                i = 0;
                System.out.print("Continuar? (S/n) ");
                char input = (char) System.in.read();
                //var input = sc.next();
                if (input == 'n' || input == 'N')
                    break;
            }
        }
        System.out.flush();

    }
}
