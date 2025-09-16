#!/bin/bash


# Function to run a test case
run_test() {
    local test_name="$1"
    local input="$2"
    local expected_output="$3"
    local expected_return_code="$4"


    echo "Test: $test_name"
    echo "Please input three numbers:"
    output=$(echo "$input" | ./divisible)
    
    return_code=$?

    # Handle return code 255 as -1
    if [ $return_code -eq 255 ]; then
        return_code=-1
    fi
 

    filtered_output=$(echo "$output" | sed 's/Please input three numbers: //')
    echo "$filtered_output"


    echo "Expected output message: $expected_output"

    if [ $return_code -eq $expected_return_code ]; then
        echo "Exited with expected return code: $expected_return_code"
    else
        echo "Exited with unexpected return code: $return_code (Expected: $expected_return_code)"
    fi


}

# Test 1: not divisible & not increasing
run_test "1: not divisible & not increasing" "10 5 7" "Not Divisible & Not increasing" 3

# Test 2: divisible & not increasing
run_test "2: divisible & not increasing" "5 20 10" "Divisible and Not increasing" 2

