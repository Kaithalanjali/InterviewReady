package com.conceptcoding.uberlld;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
/*
Write code for low level design of a restaurant food ordering and rating system, similar to food delivery apps like Zomato, Swiggy, Door Dash, Uber Eats etc.

There will be food items like 'Veg Burger', 'Veg Spring Roll', 'Ice Cream' etc.
And there will be restaurants from where you can order these food items.

Same food item can be ordered from multiple restaurants. e.g. you can order 'food-1' 'veg burger' from burger king as well as from McDonald's.

Users can order food, rate orders, fetch restaurants with most rating and fetch restaurants with most rating for a particular food item e.g. restaurants which have the most rating for 'veg burger'.

You can practice this question in both Java and Python
Your solution should implement below methods:

Method : init(Helper05 helper)
- Use this method to initialize your instance variables
- use helper's methods for printing logs else logs will not be visible.

Method : orderFood(String orderId, String restaurantId, String foodItemId)
- Orders food item from a restaurant.
- for now lets assume for that only a single food item is purchased in one order.
- orderId, restaurantId, foodItemId will all be valid and available.
- PARAMETER : restaurantId is restaurant from where food is being ordered.
- PARAMETER : foodItemId is food item which is being ordered

Method : rateOrder(String orderId, int rating)
- Customers can rate their order.
- when you are giving rating an order e.g giving 4 stars to an order, then it means you are assigning 4 stars to both the food item in that restaurant as well as 4 stars to the overall restaurant ranting.
- PARAMETER : orderId is order which will be rated by customer, orderId will always be valid i.e. order will always be created for an orderId before rateOrder() is called.
- PARAMETER : rating ranges from 1 to 5 stars in increasing order, 1 being the worst and 5 being the best rating.

Method : List[String] getTopRestaurantsByFood(String foodItemId)
- Fetches a list of top 20 restaurants based on strategy
- unrated restaurants will be at the bottom of list.
- restaurants will be sorted on the basis of strategy
- restaurants are sorted in descending order on average ratings of the food item and then based on restaurant id lexicographically.
- e.g. veg burger is rated 4.3 in restaurant-4 and 4.6 in restaurant-6 then
we will return ['restaurant-6', 'restaurant-4']
- PARAMETER : foodItemId is food item for which restaurants need to be fetched.

Method : List[String] getTopRatedRestaurants()
- returns top 20 most rated restaurants ids sorted in descending order of their ratings.
- if two restaurants have the same rating then they will be ordered lexicographically by their restaurantId.
- Here we are talking about restaurant's overall rating and NOT food item's rating.
- e.g. restaurant-2 is rated 4.6 while restaurant-3 is rated 4.2 and restaurant-5 is rated 4.4 and restaurant-6 is rated 4.6,
we will return ['restaurant-2','restaurant-6', 'restaurant-5', 'restaurant-3']
- even though restaurant-2 and restaurant-6 have same rating , restaurant-6 came later because it is lexicographically greater than restaurant-2

Note :
- There will be at max 50 food items, at max 10,000 restaurants,
    and each restaurant can sell at max 25 food items
- Average ratings are rounded down to 1 decimal point,
    i.e. 4.05, 4.08, 4.11, 4.12, 4.14 all become 4.1
    and 4.15, 4.19, 4.22, 4.24 all become 4.2
- For Python, use round(rating, 1) function to round rating to 1 decimal point.
- For Java, use the formula (double)((int)((rating+0.05)*10))/10.0 to round rating
 */
public class RestaurantSolution implements Q05RestaurantRatingInterface {
    private Helper05 helper;

    public RestaurantSolution(){}

    class Order{
        String restaurantId;
        String foodId;

        Order(String r, String f){
            restaurantId=r;
            foodId=f;
        }
    }

    class RatingStat{
        int sum=0;
        int count=0;

        void add(int r){
            sum+=r;
            count++;
        }

        double avg(){
            if(count==0){
                return 0.0;
            }
            double val= (double)sum/count;
//            return Math.floor(val * 10) / 10.0;
            return  (double)((int)((val+0.05)*10))/10.0;
        }
    }

    Map<String, Order> orders = new HashMap<>();

    Map<String, RatingStat> restaurantStats  = new HashMap<>();

    Map<String, Map<String, RatingStat>> foodRestauranStats = new HashMap<>();

    /**
     * Initialize the restaurant rating system
     * Time Complexity: O(1)
     * Space Complexity: O(1)
     */
    /**
     * Initialize the restaurant rating system
     * Time Complexity: O(1)
     * Space Complexity: O(1)
     */
    public void init(Helper05 helper){
        this.helper=helper;
        // helper.println("restaurant rating module initialized");
    }

    /**
     * Order food from a restaurant
     * Time Complexity: O(1) average
     *   - HashMap put: O(1) average
     *   - HashMap putIfAbsent: O(1) average
     *   - computeIfAbsent: O(1) average (creates nested HashMap if needed)
     * Space Complexity: O(1) - creates one Order and at most two RatingStat objects
     */
    public void orderFood(String orderId, String restaurantId, String foodItemId) {
        orders.put(orderId, new Order(restaurantId, foodItemId));

        restaurantStats.putIfAbsent(restaurantId, new RatingStat());
        foodRestauranStats.computeIfAbsent(foodItemId,k->new HashMap<>()).putIfAbsent(restaurantId, new RatingStat());
    }

    /**
     * when you(customer) are rating an order e.g giving 4 stars to an orders
     * then it means you are assigning 4 stars to both the food item
     * in that restaurant as well as 4 stars to the overall restaurant rating.
     * - rating ranges from 1 to 5, 5 is best, 1 is worst
     *
     * Time Complexity: O(1) average
     *   - HashMap get: O(1) average for each lookup
     *   - add() method updates: O(1)
     * Space Complexity: O(1) - only updates existing RatingStat objects
     */
    public void rateOrder(String orderId, int rating) {
        Order order = orders.get(orderId);
        if(order!=null){
            restaurantStats.get(order.restaurantId).add(rating);
            foodRestauranStats.get(order.foodId).get(order.restaurantId).add(rating);
        }
    }

    /**
     * - Fetches a list of top 20 restaurants
     * - unrated restaurants will be at the bottom of list.
     * - restaurants are sorted in descending order on average ratings
     * of the food item and then based on restaurant id lexicographically
     * - ratings are rounded down to 1 decimal point,
     *  i.e. 4.05, 4.08, 4.11, 4.12, 4.14 all become 4.1,
     *    4.15, 4.19, 4.22, 4.24 all become 4.2
     * - e.g. 'food-item-1':  veg burger is rated 4.3 in restaurant-4
     * and 4.6 in restaurant-6 then we will return ['restaurant-6', 'restaurant-4']
     *
     * Time Complexity: O(R log R) where R = number of restaurants serving this food
     *   - HashMap get: O(1) average
     *   - Creating list from keySet: O(R)
     *   - Sorting: O(R log R) - comparison-based sort
     *   - Each comparison involves avg() calculation: O(1)
     *   - subList: O(1) - creates view, no copying
     * Space Complexity: O(R) for the list of restaurant IDs
     */
    public List<String> getTopRestaurantsByFood(String foodItemId) {
        Map<String, RatingStat> restaurantRatings = foodRestauranStats.get(foodItemId);

        List<String> list = new ArrayList<>(restaurantRatings.keySet());

        list.sort((r1,r2)->{
            double rating1 = restaurantRatings.get(r1).avg();
            double rating2 = restaurantRatings.get(r2).avg();
            if(rating1!=rating2){
                return Double.compare(rating2,rating1);
            }
            return r1.compareTo(r2);
        });

        if(list.size()>20){
            return list.subList(0,20);
        }
        return list;
    }

    /**
     * - Here we are talking about restaurant's overall rating and NOT food item's rating.
     *
     * Time Complexity: O(R log R) where R = total number of restaurants
     *   - Creating list from keySet: O(R)
     *   - Sorting: O(R log R) - comparison-based sort
     *   - Each comparison involves avg() calculation: O(1)
     *   - HashMap get in comparator: O(1) average
     *   - subList: O(1) - creates view, no copying
     * Space Complexity: O(R) for the list of restaurant IDs
     */
    public List<String> getTopRatedRestaurants() {
        List<String> list = new ArrayList<>(restaurantStats.keySet());
        list.sort((r1,r2)->{
            double rating1 = restaurantStats.get(r1).avg();
            double rating2 = restaurantStats.get(r2).avg();

            if(rating1!=rating2){
                return Double.compare(rating2,rating1);
            }

            return  r1.compareTo(r2);
        });
        if(list.size()>20){
            return list.subList(0,20);
        }
        return list;
    }
}

// uncomment below code in case you are using your local ide like intellij, eclipse etc and
// comment it back again back when you are pasting completed solution in the online CodeZym editor.
// if you don't comment it back, you will get "java.lang.AssertionError: java.lang.LinkageError"
// This will help avoid unwanted compilation errors and get method autocomplete in your local code editor.

 interface Q05RestaurantRatingInterface {
 void init(Helper05 helper);
 void orderFood(String orderId, String restaurantId, String foodItemId);
 void rateOrder(String orderId, int rating);
 List<String> getTopRestaurantsByFood(String foodItemId);
 List<String> getTopRatedRestaurants();
 }

 class Helper05 {
 void print(String s){System.out.print(s);}
 void println(String s){System.out.println(s);}
 }

