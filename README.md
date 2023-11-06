# Z808-Feijoada

This software is a hypothetical machine architecture based on the 8086/88 Intel family. It has an embeded code editor where you can type and test what we nicknamed "Feijoada Assembly". There is also a panel where you can view the registers values when your code is executed.

![Screenshot](https://github.com/RaideNnigth/Z808-Feijoada/assets/96885946/7bf37a3b-96e9-44c0-8cb5-779a41e4bc0e)

## Feijoada Assembly

The Feijoada Assembly has very simple commands. It is sintax-like many popular assembly languages like the Intel x86-64 and the MIPS Assembly, so if you have any experience with one of those you will be very comfortable. Here's a list of commands of the Feijoada Assembly:

![Command_Table](https://github.com/RaideNnigth/Z808-Feijoada/assets/96885946/d45b6738-d8ea-492b-bde4-9757ace7c298)

The colors of the commands represent their **type**:
- ![blue](https://craftypixels.com/placeholder-image/15x15/acb7c9/ffffff) Arithmetic
- ![orange](https://craftypixels.com/placeholder-image/15x15/ffe699/ffffff) Flow control
- ![green](https://craftypixels.com/placeholder-image/15x15/a8cf8e/ffffff) Logic
- ![red](https://craftypixels.com/placeholder-image/15x15/f4b083/ffffff) Move
- ![yellow](https://craftypixels.com/placeholder-image/15x15/ffd966/ffffff) Stack

Here are the definition for some terms used in the table:
- <> Means bit concatenation
- REG represents a Register
- ADDR is the address for a memory location
- CTE represents a numeric constant

If you want to export the memory locations you may have used, go to Arquivo > Exportar Memória. A txt file will be generated with the addresses and their respective values.
