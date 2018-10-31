package Utility;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import pojo.menuDetails;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import static Utility.commonConstants.SRC_OPERATIONS_DATA_CSV_FILE_PATH;


public class ReadCSV {


    public ArrayList<menuDetails> csvQueryDropDownList() {

        ArrayList<menuDetails> strList = new ArrayList<>();
        String line;
        String cvsSplitBy = ",";
        try (BufferedReader br = new BufferedReader(new FileReader(SRC_OPERATIONS_DATA_CSV_FILE_PATH))) {

            while ((line = br.readLine()) != null) {
                // use comma as separator
                String[] food = line.split(cvsSplitBy);
                System.out.println("food:" + Arrays.toString(food));

                menuDetails menuDesc = new menuDetails(food[0], food[1], food[2],
                        food[3], food[4], food[5], food[6], food[7], food[8], food[9]);

                strList.add(menuDesc);
            }
        } catch (IOException e) {
            System.out.println("exception:" + e);
        }
        return strList;
    }



    public ObservableList<menuDetails> csvqueryLoadTableData(String Food, String Beverage) {
        float Tenergy=0,TProtien=0,TCarbohydrate=0,TFat=0,TFibre=0,TPrice=0;
        // ArrayList<String> strList = new ArrayList<String>();
        ObservableList<menuDetails> strList = FXCollections.observableArrayList();
        String csvFile = SRC_OPERATIONS_DATA_CSV_FILE_PATH;
        //BufferedReader br = null;
        String line = "";
        String cvsSplitBy = ",";
        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {

            while ((line = br.readLine()) != null) {
                // use comma as separator
                String[] food = line.split(cvsSplitBy);
                if(Food.equals(food[2]) || food[2].equals(Beverage)) {
                    //strList.add(food[2]);
                    menuDetails ID=new menuDetails(food[2],food[3],food[4],food[5],food[6],food[7],food[8]);
                    strList.add(ID);
                    System.out.println("Country [MealType= " + food[2] + " , Meal Name=" + food[4] + " , Meal Name=" + food[5] + " , Meal Name=" + food[6] + " , Meal Name=" + food[7] + " , Meal Name=" + food[8] + " , Meal Name=" + food[3] + "]");
                    // System.out.println("Country [MealType= " + food[2] + " , Meal Name=" + food[5] + "]");
                    Tenergy=Float.valueOf(food[4])+Tenergy;
                    TProtien=Float.valueOf(food[5])+TProtien;
                    TCarbohydrate=Float.valueOf(food[6])+TCarbohydrate;
                    TFat=Float.valueOf(food[7])+TFat;
                    TFibre=Float.valueOf(food[8])+TFibre;
                    TPrice=Float.valueOf(food[3])+TPrice;
                }


            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return strList;
    }
}
