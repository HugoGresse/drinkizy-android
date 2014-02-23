package fr.drinkizy.listbar.adapter;

public class CondensedListBarItem {
	private String title;
    private int icon;
    private String description;
     
    public CondensedListBarItem(String title, String description, int icon){
        this.title = title;
        this.description = description;
        this.icon = icon;
    }
     
    public String getTitle(){
        return this.title;
    }
    
    public String getDescription(){
        return this.description;
    }
    
    public int getIcon(){
        return this.icon;
    }
     
    public void setTitle(String title){
        this.title = title;
    }
    
    public void setDescription(String description){
        this.description = description;
    }
     
    public void setIcon(int icon){
        this.icon = icon;
    }
     
}
