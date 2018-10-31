package pojo;

import java.io.Serializable;

public class menuDetails implements Serializable {
    private static final long serialVersionUID = 1L;

    private String MenuDesc, mealType, ItemName, Price, Energy, Protein, Carbohydrates, TotalFat, DietaryFibre, MenuItemID;

    public menuDetails(String menuDesc, String mealType, String itemName, String price, String energy, String protein,
                       String carbohydrates, String totalFat, String dietaryFibre, String menuItemID) {
        MenuDesc = menuDesc;
        this.mealType = mealType;
        ItemName = itemName;
        Price = price;
        Energy = energy;
        Protein = protein;
        Carbohydrates = carbohydrates;
        TotalFat = totalFat;
        DietaryFibre = dietaryFibre;
        MenuItemID = menuItemID;
    }

    public menuDetails(String itemName, String price, String energy, String protein, String carbohydrates, String totalFat, String dietaryFibre) {
        ItemName = itemName;
        Price = price;
        Energy = energy;
        Protein = protein;
        Carbohydrates = carbohydrates;
        TotalFat = totalFat;
        DietaryFibre = dietaryFibre;

    }

    public menuDetails() {
    }

    public menuDetails(String menuDesc, String mealType, String itemName, String price, String energy) {
        MenuDesc = menuDesc;
        this.mealType = mealType;
        ItemName = itemName;
        Price = price;
        Energy = energy;
    }

    public String getMenuDesc() {
        return MenuDesc;
    }

    public void setMenuDesc(String menuDesc) {
        MenuDesc = menuDesc;
    }

    public String getMealType() {
        return mealType;
    }

    public void setMealType(String mealType) {
        this.mealType = mealType;
    }

    public String getItemName() {
        return ItemName;
    }

    public void setItemName(String itemName) {
        ItemName = itemName;
    }

    public String getPrice() {
        return Price;
    }

    public void setPrice(String price) {
        Price = price;
    }

    public String getEnergy() {
        return Energy;
    }

    public void setEnergy(String energy) {
        Energy = energy;
    }

    public String getProtein() {
        return Protein;
    }

    public void setProtein(String protein) {
        Protein = protein;
    }

    public String getCarbohydrates() {
        return Carbohydrates;
    }

    public void setCarbohydrates(String carbohydrates) {
        Carbohydrates = carbohydrates;
    }

    public String getTotalFat() {
        return TotalFat;
    }

    public void setTotalFat(String totalFat) {
        TotalFat = totalFat;
    }

    public String getDietaryFibre() {
        return DietaryFibre;
    }

    public void setDietaryFibre(String dietaryFibre) {
        DietaryFibre = dietaryFibre;
    }

    public String getMenuItemID() {
        return MenuItemID;
    }

    public void setMenuItemID(String menuItemID) {
        MenuItemID = menuItemID;
    }

    @Override
    public String toString() {
        return "menuDetails{" +
                "MenuDesc='" + MenuDesc + '\'' +
                ", mealType='" + mealType + '\'' +
                ", ItemName='" + ItemName + '\'' +
                ", Price='" + Price + '\'' +
                ", Energy='" + Energy + '\'' +
                ", Protein='" + Protein + '\'' +
                ", Carbohydrates='" + Carbohydrates + '\'' +
                ", TotalFat='" + TotalFat + '\'' +
                ", DietaryFibre='" + DietaryFibre + '\'' +
                ", MenuItemID='" + MenuItemID + '\'' +
                '}';
    }
}
