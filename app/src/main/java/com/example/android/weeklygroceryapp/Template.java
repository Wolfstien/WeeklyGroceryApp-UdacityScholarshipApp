package com.example.android.weeklygroceryapp;

import java.util.ArrayList;
import java.util.List;

public class Template {
    public List<TemplateItem>Template;
    public List<TemplateItem> getTemplate() {
        return Template;
    }
    public List<String>Categories;
    public TemplateItem newItem;

    public class TemplateItem{
        public String Name;
        public String Category;
        public Float Price;

        public String getName() {
            return Name;
        }
        public Float getPrice() {
            return Price;
        }
        public String getCategory() {
            return Category;
        }

        public TemplateItem(String Category, String ItemName, float ItemPrice){
            this.Name = ItemName;
            this.Price = ItemPrice;
            this.Category = Category;
        }
    }

    public Template(){
        Template = new ArrayList<TemplateItem>();
        Template.add(new TemplateItem("Liquids","Water",20));
        Template.add(new TemplateItem("Liquids","Milk",40));
        Template.add(new TemplateItem("Liquids","Honey",80));
        Template.add(new TemplateItem("Liquids","Frooti",10));
        Template.add(new TemplateItem("Liquids","Coconut Water",45));
        Template.add(new TemplateItem("Nuts","Badam",100));
        Template.add(new TemplateItem("Nuts","Pista",60));
        Template.add(new TemplateItem("Nuts","Cashew",80));
        Template.add(new TemplateItem("Nuts","Walnut",80));
        Template.add(new TemplateItem("Nuts","Groundnut",40));
        Template.add(new TemplateItem("Alcohol","Beer",120));
        Template.add(new TemplateItem("Alcohol","Wishkey",800));
        Template.add(new TemplateItem("Alcohol","Red Wine",140));
        Template.add(new TemplateItem("Alcohol","Sake",8000));
        Template.add(new TemplateItem("Alcohol","Gin",700));
        Template.add(new TemplateItem("Seafood","Crab",200));
        Template.add(new TemplateItem("Seafood","Shark",180));
        Template.add(new TemplateItem("Seafood","Squid",300));
        Template.add(new TemplateItem("Seafood","Oysters",280));
        Template.add(new TemplateItem("Seafood","Sting Ray",2000));
        Template.add(new TemplateItem("Meats","Chicken",100));
        Template.add(new TemplateItem("Meats","Lamb",480));
        Template.add(new TemplateItem("Meats","Duck",360));
        Template.add(new TemplateItem("Meats","Snake",900));
        Template.add(new TemplateItem("Meats","Turkey",600));
    }

    public List<String> getCategories() {
        return Categories;
    }

    public TemplateItem getNewItem() {
        return newItem;
    }

    public void setTemplate(List<TemplateItem> template) {
        Template = template;
    }

    public void setCategories() {
        Categories = new ArrayList<>();
        Categories.add("Liquids");
        Categories.add("Nuts");
        Categories.add("Alcohol");
        Categories.add("Seafood");
        Categories.add("Meats");
    }

    public void setNewItem(TemplateItem newItem) {
        this.newItem = newItem;
    }
}
