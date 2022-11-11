package com.tcsswiggy.app;


import com.tcsswiggy.exception.AbortOrderException;
import com.tcsswiggy.exception.InvalidInputException;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/** Application Context*/
public class App
{

    Customer customer;
    private Restro[] restroList = new Restro[10];
    private Dish[] dishList = new Dish[100];
    private Location[] locationList = new Location[10];

    App()
    {
        Location userLocation = new Location("000",34,78);
        this.customer = new Customer("Mohan",userLocation);
    }

    void parseRestroData() throws IOException {
        BufferedReader restroReader = Files.newBufferedReader(Paths.get("D:\\SOftware Dev and Training Material\\NewWinCode\\tcsnov22\\tcsswiggyapp\\data\\restro.csv"));
        String line;

        for( int restroCntr = 0 ;(line = restroReader.readLine()) != null ; restroCntr++ )
        {
            String[] restroData = line.split(",");
            restroList[restroCntr] = new Restro(restroData[0],restroData[1]);

            Dish[] tempMenu = new Dish[10];
            Location tempLocation = null;

            for( int menuCntr=0,localCntr = 0  ; (menuCntr < dishList.length) && (dishList[menuCntr] != null)  ;menuCntr++ ) // Parse the whole Dish List
            {
                if(  dishList[menuCntr].getRestroId().equals(restroData[0]) )
                {
                    tempMenu[localCntr] = dishList[menuCntr];
                    localCntr++;
                }
            }
            
            for( int locationCounter=0 ;  (locationCounter < locationList.length) && (locationList[locationCounter] != null)  ; locationCounter++ )
            {
                if(locationList[locationCounter].getId().equals(restroData[0]))
                {
                    tempLocation = locationList[locationCounter];
                }
                
            }           
            

            restroList[restroCntr].setMenu(tempMenu);
            restroList[restroCntr].setLocation(tempLocation);

        }

    }

    void parseDishData() throws IOException
    {

        BufferedReader dishReader = Files.newBufferedReader(Paths.get("D:\\SOftware Dev and Training Material\\NewWinCode\\tcsnov22\\tcsswiggyapp\\data\\dish.csv"));
        String line;

        for(  int dishCntr = 0  ;(line = dishReader.readLine()) != null; dishCntr++ )
        {
            String[] dishData = line.split(",");
            dishList[dishCntr] = new Dish(dishData[0],dishData[1],dishData[2],Integer.valueOf(dishData[3]) );
        }

    }

    void parseLocationData() throws IOException
    {
        BufferedReader locationreader = Files.newBufferedReader(Paths.get("D:\\SOftware Dev and Training Material\\NewWinCode\\tcsnov22\\tcsswiggyapp\\data\\location.csv"));

        String line;

        for( int locationCntr = 0;(line = locationreader.readLine()) != null; locationCntr++)
        {
            String[] locationData = line.split(",");
            locationList[locationCntr] = new Location(locationData[0],Float.valueOf(locationData[1]),Float.valueOf(locationData[2]));
        }
    }

    void browse() throws InvalidInputException, AbortOrderException {

       try{
            System.out.println("****************************************");
            System.out.println("Please choose Dishes from the Following Menu");

            for (int restroCntr = 0; (restroCntr < restroList.length) && (restroList[restroCntr] != null); restroCntr++) {
                System.out.println("****************************************");
                Restro tempRestro = restroList[restroCntr];
                Dish[] tempMenu = tempRestro.getMenu();
                System.out.println((restroCntr + 1) + ". " + tempRestro.getRestroname() + " Delivery Time: " + calcDelTime(this.customer.getLocation(), tempRestro.getLocation()));

                for (int menuCntr = 0; (menuCntr < tempMenu.length) && (tempMenu[menuCntr] != null); menuCntr++) {
                    System.out.println((restroCntr + 1) + "." + (menuCntr + 1) + " " + tempMenu[menuCntr].getDishName() + " " + tempMenu[menuCntr].getPrice());
                }
            }

            createOrder(null);
        }
       catch(ArrayIndexOutOfBoundsException e)
       {
           System.out.println("The Exception was caught in the Browse method try block! "+e.getMessage()+" "+e.getStackTrace());
       }
    }

    public double calcDelTime(Location userLocation, Location restroLocation)
    {
        double delSpeed = 5.0;
        return calcDistance(userLocation,restroLocation)/delSpeed;
    }

    public double calcDistance(Location l1, Location l2)
    {
        double result = 0.0f;

        result = Math.sqrt( (l1.getX() - l2.getX())*(l1.getX() - l2.getX()) + (l1.getY() - l2.getY())*(l1.getY() - l2.getY()) );

        return result;
    }

    public void createOrder(Restro[] restroList) throws InvalidInputException, AbortOrderException
    {
        if(restroList == null)
        {
            restroList = this.restroList;
        }

        System.out.println("****************************************");
        System.out.println("Please choose the Restro and Dishes in this format | RestroId,DishId1,Qty1,DishId2,Qty2...");

        Scanner orderInput = new Scanner(System.in);
        String orderInputString = orderInput.next();

        String[] orderInputData = orderInputString.split(",");
        String restroId = orderInputData[0];
        OrderElement[] orderList = new OrderElement[10];
        int orderAmnt = 0;

        System.out.println(restroList[(Integer.valueOf(restroId)-1)].getRestroname());

        if(Integer.valueOf(restroId) > 9)
        {
            throw new RuntimeException();
        }

        try
        {

            for (int orderCntr = 1, ordrListCntr = 0; orderCntr < orderInputData.length; orderCntr++)
            {
                String dishId = restroList[(Integer.valueOf(restroId) - 1)].getMenu()[(Integer.valueOf(orderInputData[orderCntr]) - 1)].getDishId();
                String dishName = restroList[(Integer.valueOf(restroId) - 1)].getMenu()[(Integer.valueOf(orderInputData[orderCntr]) - 1)].getDishName();
                int dishPrice = restroList[(Integer.valueOf(restroId) - 1)].getMenu()[(Integer.valueOf(orderInputData[orderCntr]) - 1)].getPrice();
                int dishQty = Integer.valueOf(orderInputData[++orderCntr]);

                orderAmnt += dishPrice*dishQty;

                System.out.println((ordrListCntr+1)+". "+dishName+" Qty: "+dishQty);

                orderList[ordrListCntr] = new OrderElement(dishId, dishQty);
                ordrListCntr++;

            }


        }catch(RuntimeException e)
        {
            System.out.println("An exception Occurred :"+e.getMessage()+" "+e.getStackTrace());
            System.out.println("Do you want to send an error report to our developers on Moon");
        }

        System.out.println("****************************************");
        System.out.println("Make the Payment of INR "+orderAmnt+"? 1 - YES | 2 - NO");
        Scanner pymntInput = new Scanner(System.in);
        int pymntInputInt =  pymntInput.nextInt();

        if( pymntInputInt == 1)
        {
            while(!makePayment(orderAmnt))
            {
                rechargeWallet(orderAmnt);
            }

            deliverOrder();
        }
        else
        {
            throw new AbortOrderException("payment");
        }
    }

    boolean makePayment(int ordrAmnt)
    {
        if(customer.getWallet().deductPayment(ordrAmnt))
        {
            System.out.println("Payment went through Successfully");
            return true;
        }
        else
        {
            System.out.println("Insufficient Wallet Balance, Please recharge");
            //rechargeWalletMakePayment(ordrAmnt);
            return false;
        }

    }

    void deliverOrder()
    {
        System.out.println("Your Order is Delivered!");
    }

    void  rechargeWallet(int ordrAmnt) throws AbortOrderException {
        System.out.println("Do you want to Recharge you Wallet? : 1-Yes | 2-No");
        Scanner rechargeInput = new Scanner(System.in);
        String confirm = rechargeInput.next();

        if(Integer.valueOf(confirm) == 1)
        {
            System.out.println("Please enter the recharge amnt: ");
            Scanner rechargeInputAmnt = new Scanner(System.in);
            int rechargeAmnt = rechargeInputAmnt.nextInt();
            customer.getWallet().updataBalance(Integer.valueOf(rechargeAmnt));
        }
        else
        {
            throw new AbortOrderException("recharge");
        }



        //makePayment(ordrAmnt);

    }

    void search() throws InvalidInputException, AbortOrderException {
        System.out.println("****************************************");
        Scanner searchInput = new Scanner(System.in);
        String searchString = searchInput.next();

        StringBuilder searchRegEx= new StringBuilder();

        for(char c: searchString.toCharArray())
        {
            String cObj = String.valueOf(c);
            searchRegEx.append("[");
            searchRegEx.append(cObj.toLowerCase());
            searchRegEx.append(cObj.toUpperCase());
            searchRegEx.append("]");
        }

        Pattern searchPattern = Pattern.compile(searchRegEx.toString());
        System.out.println("The search Regular Expression we compiled is: "+searchPattern.toString());

        Restro[] probRestro = new Restro[10];

        for( int i = 0,proCntr=0 ; i < dishList.length && dishList[i] != null ; i++)
        {
            Matcher matcher = searchPattern.matcher(dishList[i].getDishName());

            if( matcher.find() )
            {
                String restroId = dishList[i].getRestroId();

                for( Restro restro : restroList)
                {
                    if (restro != null)
                    {

                        if (restro.getRestroId().equals(restroId))
                        {
                            boolean restroExists = false;

                            for (Restro restroCheck : probRestro)
                            {
                                if (restroCheck == restro)
                                {
                                    restroExists = true;
                                }
                            }

                            if (!restroExists)
                            {
                                probRestro[proCntr] = restro;
                                proCntr++;

                            }
                        }
                    }
                }

            }
        }

        int restroCntr = 0;
        System.out.println("****************************************");

        for(Restro restro : probRestro) // For Each Loop
        {
            if(restro!=null)
            {
                System.out.println((restroCntr + 1) + ". " + restro.getRestroname() + " Delivery Time: " + calcDelTime(this.customer.getLocation(), restro.getLocation()));

                Dish[] tempMenu = restro.getMenu();

                for (int menuCntr = 0; (menuCntr < tempMenu.length) && (tempMenu[menuCntr] != null); menuCntr++) {
                    System.out.println((restroCntr + 1) + "." + (menuCntr + 1) + " " + tempMenu[menuCntr].getDishName() + " " + tempMenu[menuCntr].getPrice());
                }

                System.out.println("****************************************");

            }
            restroCntr++;

        }


        createOrder(probRestro);

    }



    public static void main(String[] args) throws IOException, InvalidInputException { // throws specifies exceptions that may occur

        // Create the App Context
        App mySwiggyApp = new App();

        // Parse all Files
        mySwiggyApp.parseLocationData();
        mySwiggyApp.parseDishData();
        mySwiggyApp.parseRestroData();

        //App UI

        System.out.println("****************************************");
        System.out.println("Welcome to the TCS Swiggy App "+mySwiggyApp.customer.getUsername());
        System.out.println("How would you like to Order ? 1 - From the Menu | 2 - Search for Dish");

        try
        {
            Scanner ui_1 = new Scanner(System.in);
            int intui_1 = ui_1.nextInt();

            if (intui_1 == 1) {
                mySwiggyApp.browse();
            } else if (intui_1 == 2) {
                mySwiggyApp.search();
            } else {
                System.out.println("invalid input");
            }
        }
        catch(ArrayIndexOutOfBoundsException e)
        {
            System.out.println("The Exception was caught in the MAIN METHOD! "+e.getMessage());
        }
        catch (AbortOrderException e)
        {
            switch (e.getReason())
            {
                case "recharge" : cancelOrder();break;
                case "payment" : cancelOrder();break;
                default: cancelOrder();break;
            }
        }


    }

    private static void cancelOrder() {

        //Save Order Details to a File
        System.out.println("Your Order is Cancelled");

    }
}

