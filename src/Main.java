import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import java.io.IOException;
import java.util.Map;
import java.util.Scanner;

public class Main {

    private static Scanner scanner = new Scanner(System.in);  // Scanner global
    private static JsonObject json;  // JsonObject global

    // funcion para logica de cambio de moneda
    public static double convertChangeMoney(Map<String, Double> moneyOne, Map<String, Double> moneyTwo) {
        // Obtener la clave (nombre de la moneda) y su valor desde moneyOne
        String currencyOne = moneyOne.keySet().iterator().next();
        double valueOne = moneyOne.get(currencyOne);  // Valor asociado a la clave

        // Obtener la clave (nombre de la moneda) y su valor desde moneyTwo
        String currencyTwo = moneyTwo.keySet().iterator().next();
        double valueTwo = moneyTwo.get(currencyTwo);  // Valor asociado a la clave

        System.out.println("Ingrese cuantos " + currencyOne + " desea convertir a " + currencyTwo);
        int valueToConvert = scanner.nextInt();


        // logica de cambio de moneda
        double translateToUSD = valueToConvert / valueOne;

        double tanslateToHopeMoney = translateToUSD * valueTwo;

        double result = tanslateToHopeMoney;

        System.out.println(valueToConvert + " " + currencyOne + " son " + result + " " + currencyTwo);
        return result;
    }

    // Método principal
    public static void main(String[] args) throws IOException {
        try {
            // Crear una instancia de ApiRequest
            ClientApi apiRequest = new ClientApi();

            // Hacer la petición
            String url = "https://v6.exchangerate-api.com/v6/0a2ea4fd3fb2615e7cc52ae7/latest/USD";
            JsonObject response = apiRequest.makeRequest(url);

            json = (JsonObject) response.get("conversion_rates");

            System.out.println("Json: "+json);

            int option = 0;

            // definicion de variables de monedas en scope global
            JsonElement usd = json.get("USD");
            JsonElement ars = json.get("ARS");
            JsonElement brl = json.get("BRL");
            JsonElement cop = json.get("COP");


            while (option != 7) {
                System.out.println("================================================");
                System.out.println("Sea bienvenido/a al Conversor de moneda =)");
                System.out.println("1). Dolar => Peso Argentino");
                System.out.println("2). Peso argentino => Dolar");
                System.out.println("3). Dolar => Real Brasileno");
                System.out.println("4). Real Brasileno => Dolar");
                System.out.println("5). Dolar => Peso Colombiano");
                System.out.println("6). Peso Colombiano => Dolar");
                System.out.println("7). Salir");
                System.out.println("Elije una opcion valida");
                System.out.println("================================================");

                option = scanner.nextInt();

                switch (option) {
                    case 1:
                        System.out.println("Ingrese cuantos Dolares Desea convertir a pesos Argentinos");
                        int value_translate = scanner.nextInt();


                        if (ars != null) {
                            double conversionRate = ars.getAsDouble();
                            double result = value_translate * conversionRate;
                            System.out.println(value_translate + " dolares son " + result + " pesos argentinos");
                        } else {
                            System.out.println("No se pudo obtener la tasa a conversion para ARS.");
                        }
                        break;

                    case 2:

                        if (usd != null && ars != null) {
                            double conversionRateUsd = usd.getAsDouble();
                            double conversionRateArs = ars.getAsDouble();


                            Map<String, Double> usdMap = Map.of("USD", conversionRateUsd);  // USD con tasa base de 1.0
                            Map<String, Double> arsMap = Map.of("ARS", conversionRateArs);  // ARS con tasa de conversión a USD

                            double result = convertChangeMoney(usdMap, arsMap);

                            System.out.println("el resultado es: "+result);

                        } else {
                            System.out.println("No se pudo obtener la tasa de conversión para estas monedas");
                        }
                        break;

                    case 3:

                        if (usd != null && brl != null) {
                            double conversionRateUsd = usd.getAsDouble();
                            double conversionRateBrl = brl.getAsDouble();


                            Map<String, Double> usdMap = Map.of("USD", conversionRateUsd);
                            Map<String, Double> brlMap = Map.of("BRL", conversionRateBrl);

                            double result = convertChangeMoney(usdMap, brlMap);

                            System.out.println("el resultado es: "+result);

                        } else {
                            System.out.println("No se pudo obtener la tasa de conversión para estas monedas");
                        }
                        break;

                    case 4:

                        if (brl != null && usd != null) {
                            double conversionRateUsd = usd.getAsDouble();
                            double conversionRateBrl = brl.getAsDouble();


                            Map<String, Double> usdMap = Map.of("USD", conversionRateUsd);
                            Map<String, Double> brlMap = Map.of("BRL", conversionRateBrl);

                            double result = convertChangeMoney(brlMap, usdMap);

                            System.out.println("el resultado es: "+result);

                        } else {
                            System.out.println("No se pudo obtener la tasa de conversión para estas monedas");
                        }
                        break;

                    case 5:

                        if (cop != null && usd != null) {
                            double conversionRateUsd = usd.getAsDouble();
                            double conversionRateCop = cop.getAsDouble();


                            Map<String, Double> usdMap = Map.of("USD", conversionRateUsd);
                            Map<String, Double> copMap = Map.of("COP", conversionRateCop);

                            double result = convertChangeMoney(usdMap, copMap);

                            System.out.println("el resultado es: "+result);

                        } else {
                            System.out.println("No se pudo obtener la tasa de conversión para estas monedas");
                        }
                        break;

                    case 6:

                        if (cop != null && usd != null) {
                            double conversionRateUsd = usd.getAsDouble();
                            double conversionRateCop = cop.getAsDouble();


                            Map<String, Double> usdMap = Map.of("USD", conversionRateUsd);
                            Map<String, Double> copMap = Map.of("COP", conversionRateCop);

                            double result = convertChangeMoney(copMap, usdMap);

                            System.out.println("el resultado es: "+result);

                        } else {
                            System.out.println("No se pudo obtener la tasa de conversión para estas monedas");
                        }
                        break;

                    case 7:
                        System.out.println("Saliendo...");
                        break;

                    default:
                        System.out.println("Opcion invalida");
                        break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
