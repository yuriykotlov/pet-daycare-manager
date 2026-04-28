package main;

import controllers.PetsDayCareAPI;
import controllers.OwnerAPI;
import models.*;
import utils.ScannerInput;

import java.io.File;
import java.util.Scanner;

public class Driver {
    //TODO Define  objects of the PetDayCareAPI and OwnerAPI here.  They  should be declared private.
    // And other fields as per spec
    private static OwnerAPI ownerAPI;
    private static PetsDayCareAPI petsDayCareAPI;

    public static void main(String[] args) {
        new Driver();
    }

    public Driver() {
        File file = new File("Pets.xml");
        ownerAPI = new OwnerAPI(file);

        String name = ScannerInput.readNextLine("Enter a name for your Pets Day Care: ");
        int maxNumberOfPets = ScannerInput.readNextInt("Enter a maximum number of pets allowed in your Pets Day Care: ");

        petsDayCareAPI = new PetsDayCareAPI(name, maxNumberOfPets, file);

        runMenu();
    }

    public int mainMenu(){
        return ScannerInput.readNextInt("""
                
                Main Menu | Pets Day Care
                
                ### Pets ###
                [1] Edit Pet Information
                [2] View Pet Information
                
                ### Owners ###
                [1] Edit Owner Information
                [2] View Owner Information
                
                ### Pets Day Care ###
                [3] Edit Name
                [4] Show Weekly Income
                [5] Get Pets' average days per week stayed
                
                [3] Load Pets from File
                [4] Save Pets to File
                
                [0] Exit Application
                
                \n
                """);
    }

    public int editPetsMenu(){
        return ScannerInput.readNextInt("""
                
                Edit Pets Registry | Pets Day Care
                
                [1] Add a new Pet
                [2] Remove a Pet
                [3] Update a Pet
                
                [0] Back
                
                \n
                """);
    }

    public int viewPetsMenu(){
        return ScannerInput.readNextInt("""
                
                View Pets in Registry | Pets Day Care
                
                [0] Back
                
                === GENERAL ===
                [1] List all Pets
                [2] List all Pets staying more than X days
                
                ### Owners ###
                [3] List Pets owned by a specific owner
                
                ### Dogs ###
                [4] List all Dogs
                [5] List all 'Dangerous' Dogs
                [6] Find a specific dog
                
                ### Cats ###
                [7] List all Cats
                [8] List all 'Indoor' Cats
                
                ### Parrots ###
                [9] List all Parrots
                
                \n
                """);
    }

    public int editOwnersMenu(){
        return ScannerInput.readNextInt("""
                
                Edit Owners Registry | Pets Day Care
                
                [1] Add a new Owner
                [2] Remove an Owner
                [3] Update an Owner
                
                [0] Back
                
                \n
                """);
    }

    public int viewOwnersMenu(){
        return ScannerInput.readNextInt("""
                
                View Owners in Registry | Pets Day Care
                
                [0] Back
                
                === GENERAL ===
                [1] List all Owner
                [2] List all Owners (names) that start with "..."
                
                ### Filter ###
                [3] Find an Owner by name
                [4] Find an Owner by index
                
                \n
                """);
    }

    public void runMenu(){
        int option = mainMenu();
        while(option != 0){
            switch(option){
                case 1: editPets(editPetsMenu());
                case 2: viewPets(viewPetsMenu());
                case 3: editOwners(editOwnersMenu());
                case 4: viewOwners(viewOwnersMenu());
                default: System.out.println("Invalid option, try again.");
            }

            option = mainMenu();
        }
    }

    public void editPets(int option){
        while(option != 0){
            switch(option){
                case 1: AddPet();
                case 2: RemovePet();
                case 3: UpdatePet();
                default: System.out.println("Invalid option, try again.");
            }
            option = editPetsMenu();
        }
    }

    public void viewPets(int option){
        while(option != 0){
            switch(option){
                case 1: ListAllPets();
                case 2: ListAllPetsStayingMoreThanXDays();
                case 3: ListAllPetsBySpecificOwner();
                case 4: ListAllDogs();
                case 5: ListAllDangerousDogs();
                case 6: FindSpecificDog();
                case 7: ListAllCats();
                case 8: ListAllIndoorCats();
                case 9: ListAllParrots();
                default: System.out.println("Invalid option, try again.");
            }
            option = viewPetsMenu();
        }
    }

    public void editOwners(int option){
        while(option != 0){
            switch(option){
                case 1: AddOwner();
                case 2: RemoveOwner();
                case 3: UpdateOwner();
                default: System.out.println("Invalid option, try again.");
            }
            option = editOwnersMenu();
        }
    }

    public void viewOwners(int option){
        while(option != 0){
            switch(option){
                case 1: ListAllOwners();
                case 2: ListAllOwnersWithPrefix();
                case 3: FindOwnerWithName();
                case 4: FindOwnerAtIndex();
                default: System.out.println("Invalid option, try again.");
            }
            option = viewOwnersMenu();
        }
    }

    //TODO Refer to the tutors instructions for building this class and for the menu.  You are free to deviate in any way
    //     from the Driver menu that is in the tutors instructions, once you have these included:
    //     (with tests still compiling)
    //       - CRUD on PetDayCare
    //       - Search facility (for Pets and Owners)
    //       - Reports
    //       - Persistence
    // Note:  This is the ONLY class that can talk to the user i.e. have System.out.print and Scanner reads in it.

    //----------------------------------------------------------------------------
    // Private methods for displaying the menu and processing the selected options
    //----------------------------------------------------------------------------


    //------------------------------------
    // Private methods for CRUD on Dog
    //------------------------------------


    //-----------------------------------------------------------------
    //  Private methods for Search facility
    //-----------------------------------------------------------------


    //-----------------------------
    //  Private methods for Reports
    // ----------------------------


    //---------------------------------
    //  Private methods for Persistence
    // --------------------------------


}
