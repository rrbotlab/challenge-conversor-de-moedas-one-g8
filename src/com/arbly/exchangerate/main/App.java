package com.arbly.exchangerate.main;

import com.arbly.exchangerate.util.Cli;

import java.io.IOException;
import java.util.Map;
import java.util.Scanner;

import static com.arbly.exchangerate.util.AnsiColors.*;

public class App {
    private SupportedCodes supportedCodes;
    private ConversionRates conversionRates;

    public void run() throws IOException {
        ExchangeRateApi api = new ExchangeRateApi();
        Scanner sc = new Scanner(System.in);
        Cli con = new Cli();

        con.clearConsole();
        System.out.println("Inicializando API...");
        this.supportedCodes =  api.supportedCodes();
        if (this.supportedCodes == null)
            System.exit(0);

        con.setup();

        while (true){
            String input = "";

            while (input.isBlank()) {
                System.out.print("["+api.getBaseCurrency()+"] >> ["+api.getTargetCurrency()+"] > ");
                input = sc.nextLine();
            }

            if (input.equalsIgnoreCase("x"))
                break;

            var args = input.split(" ");

            for (int i = 0; i < args.length; i++) {
                args[i] = args[i].toUpperCase();
            }

            switch (args[0]){
                case "h":
                case "H":
                case "help":
                    con.showCommands();
                    break;

                case "m":
                case "M":
                    printSupportedCodes();
                    break;

                case "t":
                case "T":
                    printResult(100.0, "USD", "BRL", null);

                default:
                    parseCommand(args, api);
            }
        }
        sc.close();
    }

    private void parseCommand(String[] args, ExchangeRateApi api){
        // args[] esperado
        // VALOR
        // VALOR COD_BASE
        // VALOR COD_BASE COD_DEST
        if(parseValue(args[0]) != 0){
            Double result = 0.0;
            if(args.length == 1) { // VALUE
                result = api.exchange(Double.valueOf(args[0]), api.getBaseCurrency(), api.getTargetCurrency());
                printResult(Double.valueOf(args[0]), api.getBaseCurrency(), api.getTargetCurrency(), result);
            } else if (args.length == 2) { // VALUE BASE
                if (this.supportedCodes.supportedCodesMap().containsKey(args[1])){
                    result = api.exchange(Double.valueOf(args[0]), args[1], api.getTargetCurrency());
                    printResult(Double.valueOf(args[0]), api.getBaseCurrency(), api.getTargetCurrency(), result);
                }
                else {
                    System.out.println(ANSI_RED + "Código inválido: " + args[1] + ANSI_RESET);
                }
            }else if (args.length == 3) { // VALUE BASE TARGET
                if (this.supportedCodes.supportedCodesMap().containsKey(args[1]) &&
                        this.supportedCodes.supportedCodesMap().containsKey(args[2])){
                    result = api.exchange(Double.valueOf(args[0]), args[1], args[2]);
                    printResult(Double.valueOf(args[0]), api.getBaseCurrency(), api.getTargetCurrency(), result);
                }
                else if(!this.supportedCodes.supportedCodesMap().containsKey(args[1])){
                        System.out.println(ANSI_RED + "Código inválido: " + args[1] + ANSI_RESET);
                }
                if (!this.supportedCodes.supportedCodesMap().containsKey(args[2])) {
                    System.out.println(ANSI_RED + "Código inválido: " + args[2] + ANSI_RESET);
                }
            }else { /* args.length > 3 */
                System.out.println(ANSI_RED + "Comando inválido. Digite h para ajuda." + ANSI_RESET);
            }
        } else { // args[0] !Double
            System.out.println("Comando inválido. Digite h para ajuda.");
        }
    }

    private void printResult(Double value, String base, String target, Double result){

        if (result != null) {
            System.out.printf(ANSI_CYAN + "%.2f %s = %.2f %s%n" + ANSI_RESET, value, base, result, target);
        }else
            System.out.println(ANSI_RED + "Ocorreu um erro no servidor remoto." + ANSI_RESET);
    }

    private double parseValue(String s){
        try{
            return Double.parseDouble(s);
        }catch (NumberFormatException e){
            return 0.0;
        }
    }

    private void printSupportedCodes() throws IOException {

        var i = 0;
        var j = 1;
        for (Map.Entry<String, String> entry : this.supportedCodes.supportedCodesMap().entrySet()) {
            System.out.format("%3d | %s | %-40s |%n", j, entry.getKey(), entry.getValue());
            i++;
            j++;
            if (i == 25) {
                i = 0;
                System.out.print("Continuar? (S/n) ");
                char input = (char) System.in.read();
                System.out.println();
                //var input = sc.next();
                if (input == 'n' || input == 'N')
                    break;
            }
        }
        System.out.flush();
    }
}
