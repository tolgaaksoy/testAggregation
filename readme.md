# Deutsch Boerse Assignment

## How to run

Requirements:
Java 8 or higher

* Build

```shell
sh build.sh
```

* Run app

```shell
sh run.sh
```

or

```shell
java -jar testAggregation.jar
```

* note : There are some tests included in the project. You can examine in the project.

## How it works

    The application is listening ./case folder under the project directory for any new files.
    When a new file is created, the application will read the file and process the transactions.

## Example

Run:

``` text
    $ sh build.sh    
    $ sh run.sh
```

Test Case 1:

``` text
    $ cat case1.txt 
    0001_internal_deposit, Deposit, PASS
    0002_internal_deposit_4ep, Deposit, SKIP
    0003_external_deposit_4ep, Deposit, PASS
    0004_invalid_external_deposit, Deposit, FAIL
    0001_dump_in_withdrawal, Withdrawal, PASS
    0002_dump_in_withdrawal_invalid, Withdrawal, FAIL
    0003_withdrawal_internal_4ep, Withdrawal, FAIL
    0004_withdrawal_internal_4ep_rejected, Withdrawal, SKIP
    $ ./case < case1.txt
```    

*Java Console Output*

``` text
Detected new 1 files: [case1.txt]
Reading file: case1.txt
--------------------------------------------------------------------------------
Processing new file: case1.txt
Deposit: PASS = 2, SKIP = 1, FAIL = 1, 
Withdrawal: PASS = 1, SKIP = 1, FAIL = 2, 
--------------------------------------------------------------------------------
```