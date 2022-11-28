package com.tcsswiggy.app;

import com.tcsswiggy.exception.AbortOrderException;
import com.tcsswiggy.exception.InvalidInputException;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/** Application Context*/
public class App
{
    Customer customer;
    private List<Restro> restroList = new ArrayList<>();
    private List<Dish> dishList = new ArrayList<>();
    private List<Location> locationList = new ArrayList<>();

    private Set<Order> orderHistory;

    App()
    {
        Location userLocation = new Location("000",34,78);
        this.customer = new Customer("Mohan",userLocation);
    }

    void writeObjectDataJson(Object obj) throws IOException
    {

        Order order = (Order) obj;
        Set<OrderElement> tempDishList = order.getDishList();
        JSONObject jsonObject = new JSONObject();

        jsonObject.put("orderId",order.getOrderId());
        jsonObject.put("restroId",order.getRestroId());
        jsonObject.put("customerId",order.getCustomerId());

        JSONArray jsonArray = new JSONArray();

        tempDishList.forEach(orderElement -> {
            JSONObject elementJson = new JSONObject();
            elementJson.put("dishName",orderElement.getDish().getDishName());
            elementJson.put("qty",orderElement.getQty());
            jsonArray.add(elementJson);
        });

        jsonObject.put("dishList",jsonArray);

        jsonObject.put("finalOrderState",order.getFinalOrderState());

        //BufferedReader

        BufferedWriter writer = Files.newBufferedWriter(Paths.get("D:\\SOftware Dev and Training Material\\NewWinCode\\tcsnov22\\tcsswiggyapp\\data\\orderhistory.json"));
       // writer.append(jsonObject.toJSONString());
        writer.write(jsonObject.toJSONString()+"\n");

        writer.flush();

    }

    void writeObjectData(Object obj) throws IOException
    {
        FileOutputStream orderHistoryFile = new FileOutputStream("D:\\SOftware Dev and Training Material\\NewWinCode\\tcsnov22\\tcsswiggyapp\\data\\orderhistory.json");
        ObjectOutputStream orderHistoryStream = new ObjectOutputStream(orderHistoryFile);

        orderHistoryStream.writeObject((Order)obj);
        orderHistoryStream.close();
        orderHistoryFile.close();
    }

    void parseRestroData() throws IOException {
        BufferedReader restroReader = Files.newBufferedReader(Paths.get("D:\\SOftware Dev and Training Material\\NewWinCode\\tcsnov22\\tcsswiggyapp\\data\\restro.csv"));
        String line;

        for( int restroCntr = 0 ;(line = restroReader.readLine()) != null ; restroCntr++ )
        {
            String[] restroData = line.split(",");
            restroList.add(restroCntr, new Restro(restroData[0],restroData[1]));

            List<Dish> tempMenu = new ArrayList<>();
            Location tempLocation = null;

            tempMenu = dishList.stream().filter(dish->dish.getRestroId().equals(restroData[0])).collect(Collectors.toList());
            tempLocation = locationList.stream().filter(location -> location.getId().equals(restroData[0])).findFirst().get();

            restroList.get(restroCntr).setMenu(tempMenu);
            restroList.get(restroCntr).setLocation(tempLocation);



            // for( int menuCntr=0,localCntr = 0  ; (menuCntr < dishList.size()) && (dishList.get(menuCntr) != null)  ;menuCntr++ ) // Parse the whole Dish List
           // {
              //  if(  dishList.get(menuCntr).getRestroId().equals(restroData[0]) )
              //  {
               //     tempMenu.add(localCntr, dishList.get(menuCntr)) ;1
               //     localCntr++;
               // }
           // }


            
            //for( int locationCounter=0 ;  (locationCounter < locationList.size()) && (locationList.get(locationCounter) != null)  ; locationCounter++ )
            //{
               // if(locationList.get(locationCounter).getId().equals(restroData[0]))
               // {
               //     tempLocation = locationList.get(locationCounter);
              //  }
                
           // }






        }

    }

    void parseDishData() throws IOException
    {

        BufferedReader dishReader = Files.newBufferedReader(Paths.get("D:\\SOftware Dev and Training Material\\NewWinCode\\tcsnov22\\tcsswiggyapp\\data\\dish.csv"));
        String line;

        for(  int dishCntr = 0  ;(line = dishReader.readLine()) != null; dishCntr++ )
        {
            String[] dishData = line.split(",");
            dishList.add(dishCntr, new Dish(dishData[0],dishData[1],dishData[2],Integer.valueOf(dishData[3]) ) );
        }

    }

    void parseLocationData() throws IOException
    {
        BufferedReader locationreader = Files.newBufferedReader(Paths.get("D:\\SOftware Dev and Training Material\\NewWinCode\\tcsnov22\\tcsswiggyapp\\data\\location.csv"));

        String line;

        for( int locationCntr = 0;(line = locationreader.readLine()) != null; locationCntr++)
        {
            String[] locationData = line.split(",");
           locationList.add(locationCntr, new Location(locationData[0],Float.valueOf(locationData[1]),Float.valueOf(locationData[2])));
        }

    }

    void browse() throws InvalidInputException, AbortOrderException {

       try{
            System.out.println("****************************************");
            System.out.println("Please choose Dishes from the Following Menu");

           class Cntr{
               int value = 0;

               public int getValue() {
                   return value;
               }

               void increment()
               {
                   value++;
               }
           }

           final Cntr menuCntr = new Cntr(); // menuCntr holds reference to an Object
           final int[] restroCntr={0}; // restroCntr holds reference to an Array of Integers

           restroList.stream().forEach(restro -> { // lambda functions are supposed to operate on immutable objects

               System.out.println("****************************************");
               List<Dish> tempMenu = restro.getMenu();
               System.out.println((restroCntr[0] + 1) + ". " + restro.getRestroname() + " Delivery Time: " + calcDelTime(this.customer.getLocation(), restro.getLocation()));
               tempMenu.forEach(dish -> System.out.println((restroCntr[0] + 1) + "." + (menuCntr.getValue() + 1) + " " + dish.getDishName() + " "+dish.getPrice()));
               restroCntr[0]++;

           });

           createOrder(null);

            //for (int restroCntr = 0; (restroCntr < restroList.size()) && (restroList.get(restroCntr) != null); restroCntr++) {
             //   System.out.println("****************************************");
             //   Restro tempRestro = restroList.get(restroCntr);
             //   List<Dish> tempMenu = tempRestro.getMenu();
             //   System.out.println((restroCntr + 1) + ". " + tempRestro.getRestroname() + " Delivery Time: " + calcDelTime(this.customer.getLocation(), tempRestro.getLocation()));

               // for (int menuCntr = 0; (menuCntr < tempMenu.size()) && (tempMenu.get(menuCntr) != null); menuCntr++) {
                //    System.out.println((restroCntr + 1) + "." + (menuCntr + 1) + " " + tempMenu.get(menuCntr).getDishName() + " " + tempMenu.get(menuCntr).getPrice());
              //  }
           // }


        }
       catch(ArrayIndexOutOfBoundsException e)
       {
           System.out.println("The Exception was caught in the Browse method try block! "+e.getMessage()+" "+e.getStackTrace());
       } catch (IOException e) {
           throw new RuntimeException(e);
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

    public void createOrder(List<Restro> restroList) throws InvalidInputException, AbortOrderException, IOException {
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
        List<OrderElement> orderList = new ArrayList<>();
        int orderAmnt = 0;

        System.out.println(restroList.get((Integer.valueOf(restroId)-1)).getRestroname());

        if(Integer.valueOf(restroId) > 9)
        {
            throw new RuntimeException();
        }

        try
        {
            for (int orderCntr = 1, ordrListCntr = 0; orderCntr < orderInputData.length; orderCntr++)
            {
                String dishId = restroList.get((Integer.valueOf(restroId) - 1)).getMenu().get((Integer.valueOf(orderInputData[orderCntr]) - 1)).getDishId();
                String dishName = restroList.get((Integer.valueOf(restroId) - 1)).getMenu().get((Integer.valueOf(orderInputData[orderCntr]) - 1)).getDishName();
                Dish tempDish = restroList.get((Integer.valueOf(restroId) - 1)).getMenu().get((Integer.valueOf(orderInputData[orderCntr]) - 1));
                int dishPrice = restroList.get((Integer.valueOf(restroId) - 1)).getMenu().get((Integer.valueOf(orderInputData[orderCntr]) - 1)).getPrice();
                int dishQty = Integer.valueOf(orderInputData[++orderCntr]);

                orderAmnt += dishPrice*dishQty;

                System.out.println((ordrListCntr+1)+". "+dishName+" Qty: "+dishQty);

                orderList.add(ordrListCntr,new OrderElement(tempDish, dishQty));
                ordrListCntr++;
            }


        }catch(RuntimeException e)
        {
            System.out.println("An exception Occurred :"+e.getMessage()+" "+e.getStackTrace());
            System.out.println("Do you want to send an error report to our developers on Moon");
        }

        Order order = new Order("001",this.customer.getUsername(),restroId);
        orderList.forEach(order::setOrderElement);


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

            deliverOrder(order);
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

    void deliverOrder(Order order) throws IOException {
        writeObjectDataJson(order);
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

    void search() throws InvalidInputException, AbortOrderException, IOException {
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

        Set<Restro> probRestroSet = new HashSet<>();
        List<Restro> probRestro = new ArrayList<>();


        dishList.stream().filter(dish -> {

            Matcher matcher = searchPattern.matcher(dish.getDishName());
            return matcher.find();

        }).map(dish ->
                dish.getRestroId()).map(restroId -> restroList.stream().filter(restro ->
                restro.getRestroId().equals(restroId)).findFirst()).forEach(
                restro -> probRestroSet.add(restro.get())
        );

        probRestroSet.forEach(probRestro::add);

        final int[] restroCntr = {0};
        System.out.println("****************************************");

        probRestro.forEach(restro -> {
            System.out.println((restroCntr[0] + 1)+ ". " + restro.getRestroname() + " Delivery Time: " + calcDelTime(this.customer.getLocation(), restro.getLocation()));
            List<Dish> tempMenu = restro.getMenu();
            final int[] menuCntr = {0};
            tempMenu.forEach(dish -> {
                System.out.println((restroCntr[0] + 1) + "." + (menuCntr[0] + 1) + " " +dish.getDishName()+" "+dish.getPrice() );
                menuCntr[0]++;
            });
            System.out.println("****************************************");
            restroCntr[0]++;
        });
        createOrder(probRestro);


        //for( int i = 0,proCntr=0 ; i < dishList.size() && dishList.get(i) != null ; i++)
       // {
          //  Matcher matcher = searchPattern.matcher(dishList.get(i).getDishName());

          //  if( matcher.find() ) // conditional execution of the block based on matcher.find() result | looks like a filter operation
         //   {
          //      String restroId = dishList.get(i).getRestroId();

           //     for( Restro restro : restroList)
            //    {
              //      if (restro != null)
               //     {

                 //       if (restro.getRestroId().equals(restroId))
                   //     {
                    //        boolean restroExists = false;

                    //        for (Restro restroCheck : probRestro)
                    //        {
                     //           if (restroCheck == restro)
                     //           {
                     //               restroExists = true;
                      //          }
                      //      }

                        //    if (!restroExists)
                         //   {
                         //       probRestro.add(proCntr,restro);
                        //        proCntr++;

                       //     }
                      //  }
                   // }
               // }

          //  }
       // }

        //for(Restro restro : probRestro) // For Each Loop
       // {
          //  if(restro!=null)
         //   {
          //      System.out.println((restroCntr + 1) + ". " + restro.getRestroname() + " Delivery Time: " + calcDelTime(this.customer.getLocation(), restro.getLocation()));

           //     List<Dish> tempMenu = restro.getMenu();

               // for (int menuCntr = 0; (menuCntr < tempMenu.size()) && (tempMenu.get(menuCntr) != null); menuCntr++) {
                //    System.out.println((restroCntr + 1) + "." + (menuCntr + 1) + " " + tempMenu.get(menuCntr).getDishName() + " " + tempMenu.get(menuCntr).getPrice());
               // }

              //  System.out.println("****************************************");

           // }
           // restroCntr++;

       // }
    }



    public static void main(String[] args) throws IOException, InvalidInputException { // throws specifies exceptions that may occur

        // Create the App Context
        App mySwiggyApp = new App();

        JSONObject jsonObject = new JSONObject();

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

