import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Scanner;


public class roles {
	
	private static Scanner keyboard = new Scanner(System.in).useLocale(Locale.US);
	private int role_id;
	private String role_name;
	private String description;
	private int alignment;
	private int category;
	private int attribute;
	private int ability;
	private int unique;
		
	public roles(int initRole_id, String initRole_name, String initDescription, 
			int initAlignment,
			int initCategory,
			int initAttribute,
			int initAbility,
			int initUnique){
		this.role_id = initRole_id;
		this.role_name = initRole_name;
		this.description = initDescription;
		this.alignment = initAlignment;
		this.category = initCategory;
		this.attribute = initAttribute;
		this.ability = initAbility;
		this.unique = initUnique;
	}
	
	public void showRoles(){
		System.out.printf("Role ID: %d\nName: %s\nDescription: %s\nAlignment: %s\nCategory: %s\nAttribute: %s\nAbility: %d\nUnique: %s", role_id, role_name, description, checkAlignment(alignment), checkCategory(category), checkAttribute(attribute), ability, checkUnique(unique));
	}
	
	public void setName(String name){
		role_name = name;
	}
	
	public void setDescription(String desc){
		description = desc;
	}
	
	public void setAlignment(int align){
		alignment = align;
	}
	
	public void setCategory(int cat){
		category = cat;
	}
	
	public void setAttribute(int atr){
		attribute = atr;
	}
	
	public void setAbility(int abil){
		ability = abil;
	}

	public void setUnique(int uni){
		unique = uni;
	}
	
	public static String edit(int num){
		return Integer.toString(num);
	}
	
	public String writeRoles(){
		String n = "\n";
		String inlineText = edit(role_id)+n+role_name+n+description+n+edit(alignment)+n+edit(category)+n+edit(attribute)+n+edit(ability)+n+edit(unique);
		
		return inlineText.replaceAll("\n", System.getProperty("line.separator"));
	}
	
	public static String checkAlignment(int alignment){
		if(alignment == 1){
			return "Town";
		}else if(alignment == 2){
			return "Mafia";
		}else{
			return "Neutral";
		}
	}
	
	public static String checkCategory(int category){
		String stringCategory = "";
		switch(category){
			case 1:	stringCategory = "Investigative";
						break;
			case 2:	stringCategory = "Killing";
						break;
			case 3:	stringCategory = "Support";
						break;
			case 4:	stringCategory = "Protective";
						break;
			case 5:	stringCategory = "Deception";
						break;
			case 6:	stringCategory = "Evil";
						break;
		}
		return stringCategory;
	}
	
	public static String checkAttribute(int attribute){
		String stringAttribute = "";
		switch(attribute){
		case 1:
			stringAttribute = "Roleblock immunity";
			break;
		case 2:
			stringAttribute = "Night immunity";
			break;
		case 3:
			stringAttribute = "Detection immunity";
			break;
		case 4:
			stringAttribute = "none";
			break;
			
		}
		return stringAttribute;
	}
	
	public static String checkUnique(int unique){
		if(unique == 1){
			return "Yes";
		}else{
			return "No";
		}
	}
	
	public static void applyChanges(ArrayList<roles> role_list){
		try{
			PrintWriter writer = new PrintWriter("roles_list.txt", "UTF-8");
			for(roles r:role_list){
				writer.println(r.writeRoles());
			}
		    writer.close();
		} catch (IOException e) {
			
		}
	}
	
	public static ArrayList<roles> addRole(ArrayList<roles> role_list){
		roles tempRole = new roles(role_list.size()+1, "", "", 1, 1, 1, 1, 1);
		
		System.out.print("Name: ");
		tempRole.setName(keyboard.next());
		System.out.print("\nDescription: ");
		keyboard.nextLine();
		tempRole.setDescription(keyboard.nextLine());
		System.out.print("\nAlignment: ");
		tempRole.setAlignment(keyboard.nextInt());
		System.out.print("\nCategory: ");
		tempRole.setCategory(keyboard.nextInt());
		System.out.print("\nAttribute: ");
		tempRole.setAttribute(keyboard.nextInt());
		System.out.print("\nAbility: ");
		tempRole.setAbility(keyboard.nextInt());
		System.out.print("\nUnique: ");
		tempRole.setUnique(keyboard.nextInt());
		
		role_list.add(tempRole);
		
		return role_list;
	}
	
	public static void printRoleInfo(ArrayList<roles> role_list){
		int chosenId;
		System.out.print("Enter role id: ");
		chosenId = keyboard.nextInt();
		if (chosenId > 0 && chosenId <= role_list.size()){
			role_list.get(chosenId-1).showRoles();
		} else {
			System.out.println("Entered id does not exist. Please try again.");
		}
	}
	
	public static ArrayList<roles> editRoleDescription(ArrayList<roles> role_list){
		
		int chosenId;
		String newDescription;
		System.out.print("Enter role id to edit: ");
		chosenId = keyboard.nextInt();
		if (chosenId > 0 && chosenId <= role_list.size()){
			System.out.print("Enter new description: ");
			keyboard.nextLine();
			newDescription = keyboard.nextLine();
			role_list.get(chosenId-1).setDescription(newDescription);
			role_list.get(chosenId-1).showRoles();
			
		} else {
			System.out.println("Entered id does not exist. Please try again.");
		}
		
		return role_list;
	}
	
	public static void main(String[] args){
		//read file
		File fileName = new File( "roles_list.txt" );
		ArrayList<roles> role_list = new 	ArrayList<roles>();
		Scanner fileReader = null;
		try {
			fileReader = new Scanner(fileName).useLocale(Locale.US);
			String text = "";
			//read lines by 8 for each role
			while(fileReader.hasNextLine()){
				String[] oneR = new String[8];
				for(int i = 0; i < 8; i++){
					text = fileReader.nextLine();
					oneR[i] = text;
				}
				
				role_list.add(new roles(Integer.parseInt(oneR[0]),oneR[1],oneR[2],Integer.parseInt(oneR[3]),Integer.parseInt(oneR[4]),Integer.parseInt(oneR[5]),Integer.parseInt(oneR[6]),Integer.parseInt(oneR[7])));
			}		
			
		} catch (FileNotFoundException e) {
			System.out.println( "role_list.txt does not exist." );
		}
		
		int userChoice = 9;
		while(userChoice != 0){
			System.out.println("\n1: show Roles\n2: add role\n3: Apply changes\n4: Print role info\n5: Edit role description\n0: Exit");
			System.out.println("\nWhat do you want to do?");
			userChoice = keyboard.nextInt();
			switch(userChoice){
			case 1:
				for(roles r:role_list){
					r.showRoles();
					System.out.println("\n");
				}
				break;
			case 2:
				role_list = addRole(role_list);
				System.out.println("Debug: role added.");
				break;
			case 3:
				applyChanges(role_list);
				System.out.println("Debug: changes applied.");
				break;
			case 4:
				printRoleInfo(role_list);
				break;
			case 5:
				role_list = editRoleDescription(role_list);
				break;
			}
			
		}
		
		
		
	}
}
