#!/bin/bash



run_test(){

    local test_name="$1"
    local word1="$2"
    local word2="$3"
    local expected_output="$4"
    local expected_return_code="$5"

    echo "Test $test_name: $word1 and $word2"
    output=$(./anagram "$word1" "$word2")
    return_code=$?

    echo "$output"

    # print expected output message if provided
    if [ -n "$expected_output" ]; then
        echo "Expected output message: $expected_output"
    fi

    # compare return code if expected_return_code is provided
    if [ -n "$expected_return_code" ]; then
        if [ $return_code -eq $expected_return_code ]; then
            echo "Exited with expected return code: $expected_return_code"
        else
            echo "Exited with unexpected return code: $return_code (Expected: $expected_return_code)"
        fi
    fi

   # echo this was for readibility purposes but since maybe I am going to be evaluated on space I will take it out
}

# test 1: mary and ramy (anagrams)
run_test 1 "mary" "ramy" "Anagram" 0

# test 2: bob and lary (not anagrams)
run_test 2 "bob" "lary" "Not an anagram" 1







