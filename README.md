<div align="center">

[![Windows](https://github.com/yuriykotlov/pet-daycare-manager/actions/workflows/windows-build.yaml/badge.svg?branch=main)](https://github.com/yuriykotlov/pet-daycare-manager/actions/workflows/windows-build.yaml)
[![MacOS](https://github.com/yuriykotlov/pet-daycare-manager/actions/workflows/macos-build.yaml/badge.svg?branch=main)](https://github.com/yuriykotlov/pet-daycare-manager/actions/workflows/macos-build.yaml)
[![Linux](https://github.com/yuriykotlov/pet-daycare-manager/actions/workflows/linux-build.yaml/badge.svg?branch=main)](https://github.com/yuriykotlov/pet-daycare-manager/actions/workflows/linux-build.yaml)

</div>

# Java Pet Daycare Manager

> This is a Pet Daycare Manager where you can initialise your own Pet Daycare and manage or view pets and their owners' entries and more, with a saving and loading system. Written in Java, it was my final project for my Programming Fundamentals module in my first year of college.

### Managing pets
> You can register or remove pets from the registry and manage attributes like their attendance, age, their owner, weight and other unique variables for each type of pet like a cat's favourite toy or a parrot's wing span.

### Managing owners
> Like pets, you can add or remove owners from the registry, and manage their name and phone number.

## Prerequisites

-   **Java 17+**
-   **Maven 3.10+**
-   **Git**

## Building (Windows, MacOS, Linux)

> Clone the project:

```bash
# SSH
git clone git@github.com:yuriykotlov/pet-daycare-manager.git

# HTTPS
git clone https://github.com/yuriykotlov/pet-daycare-manager.git
```

> Build the project:

```bash
cd pet-daycare-manager

mvn clean package
```

## Running

```bash
java -jar target/pet-daycare-manager-1.0.0.jar
```